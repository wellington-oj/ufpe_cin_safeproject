package AST;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import beaver.*;
import java.util.ArrayList;
import java.util.zip.*;
import java.io.*;

/**
 * @ast node
 * @declaredat java.ast:1
 */
public class Program extends ASTNode<ASTNode> implements Cloneable {
	/**
	 * @apilevel low-level
	 */
	public void flushCache() {
		super.flushCache();
		typeObject_computed = false;
		typeObject_value = null;
		typeCloneable_computed = false;
		typeCloneable_value = null;
		typeSerializable_computed = false;
		typeSerializable_value = null;
		typeBoolean_computed = false;
		typeBoolean_value = null;
		typeByte_computed = false;
		typeByte_value = null;
		typeShort_computed = false;
		typeShort_value = null;
		typeChar_computed = false;
		typeChar_value = null;
		typeInt_computed = false;
		typeInt_value = null;
		typeLong_computed = false;
		typeLong_value = null;
		typeFloat_computed = false;
		typeFloat_value = null;
		typeDouble_computed = false;
		typeDouble_value = null;
		typeString_computed = false;
		typeString_value = null;
		typeVoid_computed = false;
		typeVoid_value = null;
		typeNull_computed = false;
		typeNull_value = null;
		unknownType_computed = false;
		unknownType_value = null;
		hasPackage_String_values = null;
		lookupType_String_String_values = null;
		unknownConstructor_computed = false;
		unknownConstructor_value = null;
	}
	/**
	 * @apilevel internal
	 */
	public void flushCollectionCache() {
		super.flushCollectionCache();
	}
	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Program clone() throws CloneNotSupportedException {
		Program node = (Program)super.clone();
		node.typeObject_computed = false;
		node.typeObject_value = null;
		node.typeCloneable_computed = false;
		node.typeCloneable_value = null;
		node.typeSerializable_computed = false;
		node.typeSerializable_value = null;
		node.typeBoolean_computed = false;
		node.typeBoolean_value = null;
		node.typeByte_computed = false;
		node.typeByte_value = null;
		node.typeShort_computed = false;
		node.typeShort_value = null;
		node.typeChar_computed = false;
		node.typeChar_value = null;
		node.typeInt_computed = false;
		node.typeInt_value = null;
		node.typeLong_computed = false;
		node.typeLong_value = null;
		node.typeFloat_computed = false;
		node.typeFloat_value = null;
		node.typeDouble_computed = false;
		node.typeDouble_value = null;
		node.typeString_computed = false;
		node.typeString_value = null;
		node.typeVoid_computed = false;
		node.typeVoid_value = null;
		node.typeNull_computed = false;
		node.typeNull_value = null;
		node.unknownType_computed = false;
		node.unknownType_value = null;
		node.hasPackage_String_values = null;
		node.lookupType_String_String_values = null;
		node.unknownConstructor_computed = false;
		node.unknownConstructor_value = null;
		node.in$Circle(false);
		node.is$Final(false);
		return node;
	}
	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Program copy() {
		try {
			Program node = (Program)clone();
			if(children != null) node.children = (ASTNode[])children.clone();
			return node;
		} catch (CloneNotSupportedException e) {
		}
		System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
		return null;
	}
	/**
	 * @apilevel low-level
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Program fullCopy() {
		Program res = (Program)copy();
		for(int i = 0; i < getNumChildNoTransform(); i++) {
			ASTNode node = getChildNoTransform(i);
			if(node != null) node = node.fullCopy();
			res.setChild(node, i);
		}
		return res;
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:22
	 */


	protected BytecodeReader bytecodeReader;
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:23
	 */
	public void initBytecodeReader(BytecodeReader r) { bytecodeReader = r; }
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:24
	 */

	protected JavaParser javaParser;
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:25
	 */
	public void initJavaParser(JavaParser p) { javaParser = p; }
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:35
	 */
	public void addSourceFile(String name) {
		sourceFiles.addSourceFile(name);

	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:41
	 */
	public Iterator compilationUnitIterator() {
		initPaths();
		return new Iterator() {
			int index = 0;
			public boolean hasNext() {
				return index < getNumCompilationUnit() || !sourceFiles.isEmpty();
			}
			public Object next() {
				if(getNumCompilationUnit() == index) {
					String typename = (String)sourceFiles.keySet().iterator().next();
					CompilationUnit u = getCompilationUnit(typename);
					if(u != null) {
						addCompilationUnit(u);
						getCompilationUnit(getNumCompilationUnit()-1);
					}
					else
						throw new Error("File " + typename + " not found");
				}
				return getCompilationUnit(index++);
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:70
	 */
	public InputStream getInputStream(String name) {
		initPaths();
		try {
			for(Iterator iter = classPath.iterator(); iter.hasNext(); ) {
				PathPart part = (PathPart)iter.next();
				if(part.selectCompilationUnit(name))
					return part.is;
			}
		}
		catch(IOException e) {
		}
		throw new Error("Could not find nested type " + name);
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:93
	 */
	public CompilationUnit getCompilationUnit(String name) {
		initPaths();
		try {
			if(sourceFiles.selectCompilationUnit(name))
				return sourceFiles.getCompilationUnit();
			PathPart sourcePart = null;
			PathPart classPart = null;
			for(Iterator iter = sourcePath.iterator(); iter.hasNext() && sourcePart == null; ) {
				PathPart part = (PathPart)iter.next();
				if(part.selectCompilationUnit(name))
					sourcePart = part;
			}
			for(Iterator iter = classPath.iterator(); iter.hasNext() && classPart == null; ) {
				PathPart part = (PathPart)iter.next();
				if(part.selectCompilationUnit(name))
					classPart = part;
			}

			if(sourcePart != null && (classPart == null || classPart.age <= sourcePart.age)) {
				CompilationUnit unit = sourcePart.getCompilationUnit();
				int index = name.lastIndexOf('.');
				if(index == -1)
					return unit;
				String pkgName = name.substring(0, index);
				if(pkgName.equals(unit.getPackageDecl()))
					return unit;
			}
			if(classPart != null) {
				CompilationUnit unit = classPart.getCompilationUnit();
				int index = name.lastIndexOf('.');
				if(index == -1)
					return unit;
				String pkgName = name.substring(0, index);
				if(pkgName.equals(unit.getPackageDecl()))
					return unit;
			}
			return null;
		}
		catch(IOException e) {
		}
		return null;
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:137
	 */
	public boolean isPackage(String name) {
		if(sourceFiles.hasPackage(name))
			return true;
		for(Iterator iter = classPath.iterator(); iter.hasNext(); ) {
			PathPart part = (PathPart)iter.next();
			if(part.hasPackage(name))
				return true;
		}
		for(Iterator iter = sourcePath.iterator(); iter.hasNext(); ) {
			PathPart part = (PathPart)iter.next();
			if(part.hasPackage(name))
				return true;
		}
		return false;
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:167
	 */


	private boolean pathsInitialized = false;
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:168
	 */

	private java.util.ArrayList classPath;
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:169
	 */

	private java.util.ArrayList sourcePath;
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:170
	 */

	private FileNamesPart sourceFiles = new FileNamesPart(this);
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:172
	 */
	public void pushClassPath(String name) {
		PathPart part = PathPart.createSourcePath(name, this);
		if(part != null) {
			sourcePath.add(part);
			System.out.println("Pushing source path " + name);
		}
		else
			throw new Error("Could not push source path " + name);
		part = PathPart.createClassPath(name, this);
		if(part != null) {
			classPath.add(part);
			System.out.println("Pushing class path " + name);
		}
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:186
	 */
	public void popClassPath() {
		if(sourcePath.size() > 0)
			sourcePath.remove(sourcePath.size()-1);
		if(classPath.size() > 0)
			classPath.remove(classPath.size()-1);
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:193
	 */
	public void initPaths() {
		if(!pathsInitialized) {
			pathsInitialized = true;

			//System.err.println("Initializing class paths");

			ArrayList classPaths = new ArrayList();
			ArrayList sourcePaths = new ArrayList();

			String[] bootclasspaths;
			if(options().hasValueForOption("-bootclasspath"))
				bootclasspaths = options().getValueForOption("-bootclasspath").split(File.pathSeparator);
			else
				bootclasspaths = System.getProperty("sun.boot.class.path").split(File.pathSeparator);
			for(int i = 0; i < bootclasspaths.length; i++) {
				classPaths.add(bootclasspaths[i]);
				//System.err.println("Adding classpath " + bootclasspaths[i]);
			}

			String[] extdirs;
			if(options().hasValueForOption("-extdirs"))
				extdirs = options().getValueForOption("-extdirs").split(File.pathSeparator);
			else
				extdirs = System.getProperty("java.ext.dirs").split(File.pathSeparator);
			for(int i = 0; i < extdirs.length; i++) {
				classPaths.add(extdirs[i]);
				//System.err.println("Adding classpath " + extdirs[i]);
			}

			String[] userClasses = null;
			if(options().hasValueForOption("-classpath"))
				userClasses = options().getValueForOption("-classpath").split(File.pathSeparator);
			else if(options().hasValueForOption("-cp"))
				userClasses = options().getValueForOption("-cp").split(File.pathSeparator);
			else {
				String s = System.getProperty("java.class.path");
				if(s != null && s.length() > 0) {
					s = s + File.pathSeparator + "."; // TODO; This should not be necessary
					userClasses = s.split(File.pathSeparator);
				}
				else
					userClasses = ".".split(File.pathSeparator);
			}
			if(!options().hasValueForOption("-sourcepath")) {
				for(int i = 0; i < userClasses.length; i++) {
					classPaths.add(userClasses[i]);
					sourcePaths.add(userClasses[i]);
					//System.err.println("Adding classpath/sourcepath " + userClasses[i]);
				}
			}
			else {
				for(int i = 0; i < userClasses.length; i++) {
					classPaths.add(userClasses[i]);
					//System.err.println("Adding classpath " + userClasses[i]);
				}
				userClasses = options().getValueForOption("-sourcepath").split(File.pathSeparator);
				for(int i = 0; i < userClasses.length; i++) {
					sourcePaths.add(userClasses[i]);
					//System.err.println("Adding sourcepath " + userClasses[i]);
				}
			}

			classPath = new ArrayList();
			sourcePath = new ArrayList();

			for(Iterator iter = classPaths.iterator(); iter.hasNext(); ) {
				String s = (String)iter.next();
				PathPart part = PathPart.createClassPath(s, this);
				if(part != null) {
					classPath.add(part);
					//System.out.println("Adding classpath " + s);
				}
				else if(options().verbose())
					System.out.println("Warning: Could not use " + s + " as class path");
			}
			for(Iterator iter = sourcePaths.iterator(); iter.hasNext(); ) {
				String s = (String)iter.next();
				PathPart part = PathPart.createSourcePath(s, this);
				if(part != null) {
					sourcePath.add(part);
					//System.out.println("Adding sourcepath " + s);
				}
				else if(options().verbose())
					System.out.println("Warning: Could not use " + s + " as source path");
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:548
	 */
	public void simpleReset() {
		lookupType_String_String_values = new HashMap();
		hasPackage_String_values = new HashMap();
		List list = new List();
		for(int i = 0; i < getNumCompilationUnit(); i++) {
			CompilationUnit unit = getCompilationUnit(i);
			if(!unit.fromSource()) {
				list.add(unit);
			}
		}
		setCompilationUnitList(list);
	}
	/**
	 * @ast method 
	 * @aspect ErrorCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ErrorCheck.jrag:208
	 */
	public void errorCheck(Collection collection) {
		for(Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
			CompilationUnit cu = (CompilationUnit)iter.next();
			if(cu.fromSource()) {
				cu.collectErrors();
				collection.addAll(cu.errors);
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect ErrorCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ErrorCheck.jrag:217
	 */
	public void errorCheck(Collection collection, Collection warn) {
		for(Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
			CompilationUnit cu = (CompilationUnit)iter.next();
			if(cu.fromSource()) {
				cu.collectErrors();
				collection.addAll(cu.errors);
				warn.addAll(cu.warnings);
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect ErrorCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ErrorCheck.jrag:238
	 */
	public boolean errorCheck() {
		Collection collection = new LinkedList();
		errorCheck(collection);
		if(collection.isEmpty())
			return false;
		System.out.println("Errors:");
		for(Iterator iter = collection.iterator(); iter.hasNext(); ) {
			String s = (String)iter.next();
			System.out.println(s);
		}
		return true;
	}
	/**
	 * @ast method 
	 * @aspect LookupFullyQualifiedTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:103
	 */


	public int classFileReadTime;
	/**
	 * @ast method 
	 * @aspect PrettyPrint
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:23
	 */
	public void toString(StringBuffer s) {
		for(Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
			CompilationUnit cu = (CompilationUnit)iter.next();
			if(cu.fromSource()) { 
				cu.toString(s);
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect PrettyPrint
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:820
	 */
	public String dumpTree() {
		StringBuffer s = new StringBuffer();
		for(Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
			CompilationUnit cu = (CompilationUnit)iter.next();
			if(cu.fromSource()) { 
				s.append(cu.dumpTree());
			}
		}
		return s.toString();
	}
	/**
	 * @ast method 
	 * @aspect PrimitiveTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrimitiveTypes.jrag:13
	 */


	private boolean initPrimTypes = false;
	/**
	 * @ast method 
	 * @aspect PrimitiveTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrimitiveTypes.jrag:15
	 */
	public void addPrimitiveTypes() {
		if(!initPrimTypes) {
			initPrimTypes = true;

			CompilationUnit u = new CompilationUnit();
			u.setPackageDecl(PRIMITIVE_PACKAGE_NAME);
			addCompilationUnit(u);

			TypeDecl classDecl = generateUnknownType();
			u.addTypeDecl(classDecl);
			TypeDecl unknown = classDecl;

			classDecl = generatePrimitiveType(new BooleanType(), "boolean", unknown);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new DoubleType(), "double", unknown);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new FloatType(), "float", classDecl);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new LongType(), "long", classDecl);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new IntType(), "int", classDecl);
			u.addTypeDecl(classDecl);
			TypeDecl intDecl = classDecl;

			classDecl = generatePrimitiveType(new ShortType(), "short", classDecl);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new ByteType(), "byte", classDecl);
			u.addTypeDecl(classDecl);

			classDecl = generatePrimitiveType(new CharType(), "char", intDecl);
			u.addTypeDecl(classDecl);

			classDecl = new NullType();
			classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
			classDecl.setID("null");
			u.addTypeDecl(classDecl);

			classDecl = new VoidType();
			classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
			classDecl.setID("void");
			u.addTypeDecl(classDecl);

		}
	}
	/**
	 * @ast method 
	 * @aspect PrimitiveTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrimitiveTypes.jrag:65
	 */
	public TypeDecl generatePrimitiveType(PrimitiveType type, String name, TypeDecl superType) {
		type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
		type.setID(name);
		if(superType != null)
			type.setSuperClassAccess(superType.createQualifiedAccess());
		return type;
	}
	/**
	 * @ast method 
	 * @aspect PrimitiveTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrimitiveTypes.jrag:73
	 */
	private TypeDecl generateUnknownType() {
		ClassDecl classDecl = new UnknownType();
		classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
		classDecl.setID("Unknown");
		MethodDecl methodDecl = new MethodDecl(
				new Modifiers(new List().add(
						new Modifier("public")
						)),
						new PrimitiveTypeAccess("Unknown"),
						"unknown",
						new List(),
						new List(),
						new Opt()
				);
		classDecl.addBodyDecl(methodDecl);
		FieldDeclaration fieldDecl = new FieldDeclaration(
				new Modifiers(new List().add(
						new Modifier("public")
						)),
						new PrimitiveTypeAccess("Unknown"),
						"unknown",
						new Opt()
				);
		classDecl.addBodyDecl(fieldDecl);   
		ConstructorDecl constrDecl = new ConstructorDecl(
				new Modifiers(new List().add(new Modifier("public"))),
				"Unknown",
				new List(),
				new List(),
				new Opt(),
				new Block()
				);
		classDecl.addBodyDecl(constrDecl);

		return classDecl;
	}
	/**
	 * @ast method 
	 * @aspect GenerateClassfile
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:11
	 */
	public void generateClassfile() {
		for(Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
			CompilationUnit cu = (CompilationUnit)iter.next();
			cu.generateClassfile();
		}
	}
	/**
	 * @ast method 
	 * @declaredat java.ast:1
	 */
	public Program() {
		super();

		setChild(new List(), 0);
		is$Final(true);

	}
	/**
	 * @ast method 
	 * @declaredat java.ast:9
	 */
	public Program(List<CompilationUnit> p0) {
		setChild(p0, 0);
		is$Final(true);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:16
	 */
	protected int numChildren() {
		return 1;
	}
	/**
	 * @apilevel internal
	 * @ast method 
	 * @declaredat java.ast:22
	 */
	public boolean mayHaveRewrite() {
		return false;
	}
	/**
	 * Setter for CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setCompilationUnitList(List<CompilationUnit> list) {
		setChild(list, 0);
	}
	/**
	 * @return number of children in CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:12
	 */
	public int getNumCompilationUnit() {
		return getCompilationUnitList().getNumChild();
	}
	/**
	 * Getter for child in list CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:19
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public CompilationUnit getCompilationUnit(int i) {
		return (CompilationUnit)getCompilationUnitList().getChild(i);
	}
	/**
	 * Add element to list CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:27
	 */
	public void addCompilationUnit(CompilationUnit node) {
		List<CompilationUnit> list = (parent == null || state == null) ? getCompilationUnitListNoTransform() : getCompilationUnitList();
		list.addChild(node);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:34
	 */
	public void addCompilationUnitNoTransform(CompilationUnit node) {
		List<CompilationUnit> list = getCompilationUnitListNoTransform();
		list.addChild(node);
	}
	/**
	 * Setter for child in list CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:42
	 */
	public void setCompilationUnit(CompilationUnit node, int i) {
		List<CompilationUnit> list = getCompilationUnitList();
		list.setChild(node, i);
	}
	/**
	 * Getter for CompilationUnit list.
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:50
	 */
	public List<CompilationUnit> getCompilationUnits() {
		return getCompilationUnitList();
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:56
	 */
	public List<CompilationUnit> getCompilationUnitsNoTransform() {
		return getCompilationUnitListNoTransform();
	}
	/**
	 * Getter for list CompilationUnitList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:63
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<CompilationUnit> refined__Program_getCompilationUnitList() {
		List<CompilationUnit> list = (List<CompilationUnit>)getChild(0);
		list.getNumChild();
		return list;
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:72
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<CompilationUnit> getCompilationUnitListNoTransform() {
		return (List<CompilationUnit>)getChildNoTransform(0);
	}
	/**
	 * @ast method 
	 * @aspect ClassPath
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ClassPath.jrag:84
	 */
	public List getCompilationUnitList() {
		initPaths();
		return refined__Program_getCompilationUnitList();
	}
	/**
	 * @apilevel internal
	 */
	protected boolean typeObject_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeObject_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:15
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeObject() {
		if(typeObject_computed) {
			return typeObject_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeObject_value = typeObject_compute();
		if(isFinal && num == state().boundariesCrossed) typeObject_computed = true;
		return typeObject_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeObject_compute() {  return lookupType("java.lang", "Object");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeCloneable_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeCloneable_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:16
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeCloneable() {
		if(typeCloneable_computed) {
			return typeCloneable_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeCloneable_value = typeCloneable_compute();
		if(isFinal && num == state().boundariesCrossed) typeCloneable_computed = true;
		return typeCloneable_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeCloneable_compute() {  return lookupType("java.lang", "Cloneable");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeSerializable_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeSerializable_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:17
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeSerializable() {
		if(typeSerializable_computed) {
			return typeSerializable_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeSerializable_value = typeSerializable_compute();
		if(isFinal && num == state().boundariesCrossed) typeSerializable_computed = true;
		return typeSerializable_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeSerializable_compute() {  return lookupType("java.io", "Serializable");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeBoolean_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeBoolean_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:22
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeBoolean() {
		if(typeBoolean_computed) {
			return typeBoolean_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeBoolean_value = typeBoolean_compute();
		if(isFinal && num == state().boundariesCrossed) typeBoolean_computed = true;
		return typeBoolean_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeBoolean_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME, "boolean");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeByte_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeByte_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:23
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeByte() {
		if(typeByte_computed) {
			return typeByte_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeByte_value = typeByte_compute();
		if(isFinal && num == state().boundariesCrossed) typeByte_computed = true;
		return typeByte_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeByte_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "byte");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeShort_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeShort_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:24
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeShort() {
		if(typeShort_computed) {
			return typeShort_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeShort_value = typeShort_compute();
		if(isFinal && num == state().boundariesCrossed) typeShort_computed = true;
		return typeShort_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeShort_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "short");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeChar_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeChar_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:25
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeChar() {
		if(typeChar_computed) {
			return typeChar_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeChar_value = typeChar_compute();
		if(isFinal && num == state().boundariesCrossed) typeChar_computed = true;
		return typeChar_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeChar_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "char");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeInt_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeInt_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:26
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeInt() {
		if(typeInt_computed) {
			return typeInt_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeInt_value = typeInt_compute();
		if(isFinal && num == state().boundariesCrossed) typeInt_computed = true;
		return typeInt_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeInt_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "int");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeLong_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeLong_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:27
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeLong() {
		if(typeLong_computed) {
			return typeLong_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeLong_value = typeLong_compute();
		if(isFinal && num == state().boundariesCrossed) typeLong_computed = true;
		return typeLong_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeLong_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "long");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeFloat_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeFloat_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:28
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeFloat() {
		if(typeFloat_computed) {
			return typeFloat_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeFloat_value = typeFloat_compute();
		if(isFinal && num == state().boundariesCrossed) typeFloat_computed = true;
		return typeFloat_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeFloat_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "float");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeDouble_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeDouble_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:29
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeDouble() {
		if(typeDouble_computed) {
			return typeDouble_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeDouble_value = typeDouble_compute();
		if(isFinal && num == state().boundariesCrossed) typeDouble_computed = true;
		return typeDouble_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeDouble_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME , "double");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeString_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeString_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:30
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeString() {
		if(typeString_computed) {
			return typeString_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeString_value = typeString_compute();
		if(isFinal && num == state().boundariesCrossed) typeString_computed = true;
		return typeString_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeString_compute() {  return lookupType("java.lang", "String");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeVoid_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeVoid_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:41
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeVoid() {
		if(typeVoid_computed) {
			return typeVoid_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeVoid_value = typeVoid_compute();
		if(isFinal && num == state().boundariesCrossed) typeVoid_computed = true;
		return typeVoid_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeVoid_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME, "void");  }
	/**
	 * @apilevel internal
	 */
	protected boolean typeNull_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl typeNull_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:43
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl typeNull() {
		if(typeNull_computed) {
			return typeNull_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeNull_value = typeNull_compute();
		if(isFinal && num == state().boundariesCrossed) typeNull_computed = true;
		return typeNull_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl typeNull_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME, "null");  }
	/**
	 * @apilevel internal
	 */
	protected boolean unknownType_computed = false;
	/**
	 * @apilevel internal
	 */
	protected TypeDecl unknownType_value;
	/**
	 * @attribute syn
	 * @aspect SpecialClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:46
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl unknownType() {
		if(unknownType_computed) {
			return unknownType_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		unknownType_value = unknownType_compute();
		if(isFinal && num == state().boundariesCrossed) unknownType_computed = true;
		return unknownType_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl unknownType_compute() {  return lookupType(PRIMITIVE_PACKAGE_NAME, "Unknown");  }
	protected java.util.Map hasPackage_String_values;
	/**
	 * @attribute syn
	 * @aspect LookupFullyQualifiedTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:77
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean hasPackage(String packageName) {
		Object _parameters = packageName;
		if(hasPackage_String_values == null) hasPackage_String_values = new java.util.HashMap(4);
		if(hasPackage_String_values.containsKey(_parameters)) {
			return ((Boolean)hasPackage_String_values.get(_parameters)).booleanValue();
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		boolean hasPackage_String_value = hasPackage_compute(packageName);
		if(isFinal && num == state().boundariesCrossed) hasPackage_String_values.put(_parameters, Boolean.valueOf(hasPackage_String_value));
		return hasPackage_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean hasPackage_compute(String packageName) {
		return isPackage(packageName);
	}
	protected java.util.Map lookupType_String_String_values;
	/**
	 * @attribute syn
	 * @aspect LookupFullyQualifiedTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:105
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl lookupType(String packageName, String typeName) {
		java.util.List _parameters = new java.util.ArrayList(2);
		_parameters.add(packageName);
		_parameters.add(typeName);
		if(lookupType_String_String_values == null) lookupType_String_String_values = new java.util.HashMap(4);
		if(lookupType_String_String_values.containsKey(_parameters)) {
			return (TypeDecl)lookupType_String_String_values.get(_parameters);
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		TypeDecl lookupType_String_String_value = lookupType_compute(packageName, typeName);
		if(isFinal && num == state().boundariesCrossed) lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
		return lookupType_String_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl lookupType_compute(String packageName, String typeName) {
		addPrimitiveTypes();
		String fullName = packageName.equals("") ? typeName : packageName + "." + typeName;
		for(int i = 0; i < getNumCompilationUnit(); i++) {
			for(int j = 0; j < getCompilationUnit(i).getNumTypeDecl(); j++) {
				TypeDecl type = getCompilationUnit(i).getTypeDecl(j);
				if(type.fullName().equals(fullName)) {
					return type;
				}
			}
		}
		CompilationUnit u = getCompilationUnit(fullName);
		if(u != null) {
			addCompilationUnit(u);
			getCompilationUnit(getNumCompilationUnit()-1);
			for(int j = 0; j < u.getNumTypeDecl(); j++) {
				if(u.getTypeDecl(j).name().equals(typeName)) {
					return u.getTypeDecl(j);
				}
			}
			//throw new Error("No type named " + typeName + " in file " + fullName + ", " + u.pathName() + ", " + u.relativeName());
		}
		return null;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean unknownConstructor_computed = false;
	/**
	 * @apilevel internal
	 */
	protected ConstructorDecl unknownConstructor_value;
	/**
	 * @attribute syn
	 * @aspect TypeAnalysis
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:245
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public ConstructorDecl unknownConstructor() {
		if(unknownConstructor_computed) {
			return unknownConstructor_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		unknownConstructor_value = unknownConstructor_compute();
		if(isFinal && num == state().boundariesCrossed) unknownConstructor_computed = true;
		return unknownConstructor_value;
	}
	/**
	 * @apilevel internal
	 */
	private ConstructorDecl unknownConstructor_compute() {
		return (ConstructorDecl)unknownType().constructors().iterator().next();
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AnonymousClasses.jrag:16
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_superType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_TypeDecl_superType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AnonymousClasses.jrag:26
	 * @apilevel internal
	 */
	public ConstructorDecl Define_ConstructorDecl_constructorDecl(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_ConstructorDecl_constructorDecl(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Arrays.jrag:19
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_componentType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return unknownType();
		}
		return getParent().Define_TypeDecl_componentType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:173
	 * @apilevel internal
	 */
	public LabeledStmt Define_LabeledStmt_lookupLabel(ASTNode caller, ASTNode child, String name) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_LabeledStmt_lookupLabel(this, caller, name);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:16
	 * @apilevel internal
	 */
	public boolean Define_boolean_isDest(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isDest(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:26
	 * @apilevel internal
	 */
	public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return true;
		}
		return getParent().Define_boolean_isSource(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:50
	 * @apilevel internal
	 */
	public boolean Define_boolean_isIncOrDec(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isIncOrDec(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:324
	 * @apilevel internal
	 */
	public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return true;
		}
		return getParent().Define_boolean_isDAbefore(this, caller, v);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:709
	 * @apilevel internal
	 */
	public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return true;
		}
		return getParent().Define_boolean_isDUbefore(this, caller, v);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:13
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeException(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType("java.lang", "Exception");
		}
		return getParent().Define_TypeDecl_typeException(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:15
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeRuntimeException(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType("java.lang", "RuntimeException");
		}
		return getParent().Define_TypeDecl_typeRuntimeException(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:17
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeError(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType("java.lang", "Error");
		}
		return getParent().Define_TypeDecl_typeError(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:19
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeNullPointerException(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType("java.lang", "NullPointerException");
		}
		return getParent().Define_TypeDecl_typeNullPointerException(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:21
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeThrowable(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType("java.lang", "Throwable");
		}
		return getParent().Define_TypeDecl_typeThrowable(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:123
	 * @apilevel internal
	 */
	public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{
				throw new Error("Operation handlesException not supported");
			}
		}
		return getParent().Define_boolean_handlesException(this, caller, exceptionType);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:15
	 * @apilevel internal
	 */
	public Collection Define_Collection_lookupConstructor(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return Collections.EMPTY_LIST;
		}
		return getParent().Define_Collection_lookupConstructor(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:24
	 * @apilevel internal
	 */
	public Collection Define_Collection_lookupSuperConstructor(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return Collections.EMPTY_LIST;
		}
		return getParent().Define_Collection_lookupSuperConstructor(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:21
	 * @apilevel internal
	 */
	public Expr Define_Expr_nestedScope(ASTNode caller, ASTNode child) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{ throw new UnsupportedOperationException(); }
		}
		return getParent().Define_Expr_nestedScope(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:33
	 * @apilevel internal
	 */
	public Collection Define_Collection_lookupMethod(ASTNode caller, ASTNode child, String name) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return Collections.EMPTY_LIST;
		}
		return getParent().Define_Collection_lookupMethod(this, caller, name);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:18
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeObject(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeObject();
		}
		return getParent().Define_TypeDecl_typeObject(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:19
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeCloneable(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeCloneable();
		}
		return getParent().Define_TypeDecl_typeCloneable(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:20
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeSerializable(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeSerializable();
		}
		return getParent().Define_TypeDecl_typeSerializable(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:31
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeBoolean(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeBoolean();
		}
		return getParent().Define_TypeDecl_typeBoolean(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:32
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeByte(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeByte();
		}
		return getParent().Define_TypeDecl_typeByte(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:33
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeShort(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeShort();
		}
		return getParent().Define_TypeDecl_typeShort(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:34
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeChar(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeChar();
		}
		return getParent().Define_TypeDecl_typeChar(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:35
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeInt(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeInt();
		}
		return getParent().Define_TypeDecl_typeInt(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:36
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeLong(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeLong();
		}
		return getParent().Define_TypeDecl_typeLong(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:37
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeFloat(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeFloat();
		}
		return getParent().Define_TypeDecl_typeFloat(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:38
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeDouble(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeDouble();
		}
		return getParent().Define_TypeDecl_typeDouble(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:39
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeString(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeString();
		}
		return getParent().Define_TypeDecl_typeString(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:42
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeVoid(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeVoid();
		}
		return getParent().Define_TypeDecl_typeVoid(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:44
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_typeNull(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeNull();
		}
		return getParent().Define_TypeDecl_typeNull(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:47
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_unknownType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return unknownType();
		}
		return getParent().Define_TypeDecl_unknownType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:81
	 * @apilevel internal
	 */
	public boolean Define_boolean_hasPackage(ASTNode caller, ASTNode child, String packageName) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return hasPackage(packageName);
		}
		return getParent().Define_boolean_hasPackage(this, caller, packageName);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:101
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_lookupType(ASTNode caller, ASTNode child, String packageName, String typeName) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return lookupType(packageName, typeName);
		}
		return getParent().Define_TypeDecl_lookupType(this, caller, packageName, typeName);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:178
	 * @apilevel internal
	 */
	public SimpleSet Define_SimpleSet_lookupType(ASTNode caller, ASTNode child, String name) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return SimpleSet.emptySet;
		}
		return getParent().Define_SimpleSet_lookupType(this, caller, name);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:24
	 * @apilevel internal
	 */
	public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return SimpleSet.emptySet;
		}
		return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:288
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBePublic(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBePublic(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:289
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeProtected(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeProtected(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:290
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBePrivate(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBePrivate(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:291
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeStatic(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeStatic(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:292
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeFinal(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:293
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeAbstract(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeAbstract(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:294
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeVolatile(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeVolatile(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:295
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeTransient(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeTransient(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:296
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeStrictfp(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeStrictfp(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:297
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeSynchronized(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeSynchronized(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:298
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeNative(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_mayBeNative(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:244
	 * @apilevel internal
	 */
	public ASTNode Define_ASTNode_enclosingBlock(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_ASTNode_enclosingBlock(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:295
	 * @apilevel internal
	 */
	public VariableScope Define_VariableScope_outerScope(ASTNode caller, ASTNode child) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{
				throw new UnsupportedOperationException("outerScope() not defined");
			}
		}
		return getParent().Define_VariableScope_outerScope(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:363
	 * @apilevel internal
	 */
	public boolean Define_boolean_insideLoop(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_insideLoop(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:370
	 * @apilevel internal
	 */
	public boolean Define_boolean_insideSwitch(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_insideSwitch(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:420
	 * @apilevel internal
	 */
	public Case Define_Case_bind(ASTNode caller, ASTNode child, Case c) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_Case_bind(this, caller, c);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:354
	 * @apilevel internal
	 */
	public String Define_String_typeDeclIndent(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return "";
		}
		return getParent().Define_String_typeDeclIndent(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:64
	 * @apilevel internal
	 */
	public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return NameType.NO_NAME;
		}
		return getParent().Define_NameType_nameType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:219
	 * @apilevel internal
	 */
	public boolean Define_boolean_isAnonymous(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isAnonymous(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:233
	 * @apilevel internal
	 */
	public Variable Define_Variable_unknownField(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return unknownType().findSingleVariable("unknown");
		}
		return getParent().Define_Variable_unknownField(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:237
	 * @apilevel internal
	 */
	public MethodDecl Define_MethodDecl_unknownMethod(ASTNode caller, ASTNode child) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{
				for(Iterator iter = unknownType().memberMethods("unknown").iterator(); iter.hasNext(); ) {
					MethodDecl m = (MethodDecl)iter.next();
					return m;
				}
				throw new Error("Could not find method unknown in type Unknown");
			}
		}
		return getParent().Define_MethodDecl_unknownMethod(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:244
	 * @apilevel internal
	 */
	public ConstructorDecl Define_ConstructorDecl_unknownConstructor(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return unknownConstructor();
		}
		return getParent().Define_ConstructorDecl_unknownConstructor(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:256
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_declType(ASTNode caller, ASTNode child) {
		if(true) {
			int i = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_TypeDecl_declType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:514
	 * @apilevel internal
	 */
	public BodyDecl Define_BodyDecl_enclosingBodyDecl(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_BodyDecl_enclosingBodyDecl(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:532
	 * @apilevel internal
	 */
	public boolean Define_boolean_isMemberType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isMemberType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:579
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_hostType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_TypeDecl_hostType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:360
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_switchType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return unknownType();
		}
		return getParent().Define_TypeDecl_switchType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:406
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_returnType(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return typeVoid();
		}
		return getParent().Define_TypeDecl_returnType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:506
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_enclosingInstance(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return null;
		}
		return getParent().Define_TypeDecl_enclosingInstance(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:14
	 * @apilevel internal
	 */
	public String Define_String_methodHost(ASTNode caller, ASTNode child) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{
				throw new Error("Needs extra equation for methodHost()");
			}
		}
		return getParent().Define_String_methodHost(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:128
	 * @apilevel internal
	 */
	public boolean Define_boolean_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_inExplicitConstructorInvocation(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:137
	 * @apilevel internal
	 */
	public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_inStaticContext(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:160
	 * @apilevel internal
	 */
	public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
		if(caller == getCompilationUnitListNoTransform()) {
			int childIndex = caller.getIndexOfChild(child);
			return true;
		}
		return getParent().Define_boolean_reportUnreachable(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:68
	 * @apilevel internal
	 */
	public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isMethodParameter(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:69
	 * @apilevel internal
	 */
	public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isConstructorParameter(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:70
	 * @apilevel internal
	 */
	public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
		if(true) {
			int childIndex = this.getIndexOfChild(caller);
			return false;
		}
		return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:47
	 * @apilevel internal
	 */
	public int Define_int_variableScopeEndLabel(ASTNode caller, ASTNode child, CodeGeneration gen) {
		if(true) { 
			int i = this.getIndexOfChild(caller);
			{
				throw new Error("variableScopeEndLabel not valid from here");
			}
		}
		return getParent().Define_int_variableScopeEndLabel(this, caller, gen);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:972
	 * @apilevel internal
	 */
	public int Define_int_condition_false_label(ASTNode caller, ASTNode child) {
		if(true) { 
			int i = this.getIndexOfChild(caller);
			{
				throw new Error("condition_false_label not implemented");
			}
		}
		return getParent().Define_int_condition_false_label(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:976
	 * @apilevel internal
	 */
	public int Define_int_condition_true_label(ASTNode caller, ASTNode child) {
		if(true) { 
			int i = this.getIndexOfChild(caller);
			{
				throw new Error("condition_true_label not implemented");
			}
		}
		return getParent().Define_int_condition_true_label(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:62
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_expectedType(ASTNode caller, ASTNode child) {
		if(caller == getCompilationUnitListNoTransform()) {
			int i = caller.getIndexOfChild(child);
			return null;
		}
		return getParent().Define_TypeDecl_expectedType(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:15
	 * @apilevel internal
	 */
	public int Define_int_localNum(ASTNode caller, ASTNode child) {
		if(true) {
			int index = this.getIndexOfChild(caller);
			return 0;
		}
		return getParent().Define_int_localNum(this, caller);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:46
	 * @apilevel internal
	 */
	public int Define_int_resultSaveLocalNum(ASTNode caller, ASTNode child) {
		if(true) { 
			int childIndex = this.getIndexOfChild(caller);
			{
				throw new Error("Unsupported operation resultSaveLocalNum");
			}
		}
		return getParent().Define_int_resultSaveLocalNum(this, caller);
	}
	/**
	 * @apilevel internal
	 */
	public ASTNode rewriteTo() {
		return super.rewriteTo();
	}
}
