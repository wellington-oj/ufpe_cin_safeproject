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

import util.SafeManager;


/**
 * @ast node
 * @declaredat java.ast:63
 */
public class ClassDecl extends ReferenceType implements Cloneable {
	/**
	 * @apilevel low-level
	 */
	public void flushCache() {
		super.flushCache();
		interfacesMethodsSignatureMap_computed = false;
		interfacesMethodsSignatureMap_value = null;
		methodsSignatureMap_computed = false;
		methodsSignatureMap_value = null;
		ancestorMethods_String_values = null;
		memberTypes_String_values = null;
		memberFieldsMap_computed = false;
		memberFieldsMap_value = null;
		memberFields_String_values = null;
		unimplementedMethods_computed = false;
		unimplementedMethods_value = null;
		hasAbstract_computed = false;
		castingConversionTo_TypeDecl_values = null;
		isString_computed = false;
		isObject_computed = false;
		instanceOf_TypeDecl_values = null;
		isCircular_visited = -1;
		isCircular_computed = false;
		isCircular_initialized = false;
		typeDescriptor_computed = false;
		typeDescriptor_value = null;
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
	public ClassDecl clone() throws CloneNotSupportedException {
		ClassDecl node = (ClassDecl)super.clone();
		node.interfacesMethodsSignatureMap_computed = false;
		node.interfacesMethodsSignatureMap_value = null;
		node.methodsSignatureMap_computed = false;
		node.methodsSignatureMap_value = null;
		node.ancestorMethods_String_values = null;
		node.memberTypes_String_values = null;
		node.memberFieldsMap_computed = false;
		node.memberFieldsMap_value = null;
		node.memberFields_String_values = null;
		node.unimplementedMethods_computed = false;
		node.unimplementedMethods_value = null;
		node.hasAbstract_computed = false;
		node.castingConversionTo_TypeDecl_values = null;
		node.isString_computed = false;
		node.isObject_computed = false;
		node.instanceOf_TypeDecl_values = null;
		node.isCircular_visited = -1;
		node.isCircular_computed = false;
		node.isCircular_initialized = false;
		node.typeDescriptor_computed = false;
		node.typeDescriptor_value = null;
		node.in$Circle(false);
		node.is$Final(false);
		return node;
	}
	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public ClassDecl copy() {
		try {
			ClassDecl node = (ClassDecl)clone();
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
	public ClassDecl fullCopy() {
		ClassDecl res = (ClassDecl)copy();
		for(int i = 0; i < getNumChildNoTransform(); i++) {
			ASTNode node = getChildNoTransform(i);
			if(node != null) node = node.fullCopy();
			res.setChild(node, i);
		}
		return res;
	}
	/**
	 * @ast method 
	 * @aspect AccessControl
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AccessControl.jrag:147
	 */
	public void accessControl() {
		super.accessControl();

		// 8.1.1.2 final Classes
		TypeDecl typeDecl = hasSuperclass() ? superclass() : null;
		if(typeDecl != null && !typeDecl.accessibleFromExtend(this))
			//if(typeDecl != null && !isCircular() && !typeDecl.accessibleFrom(this))
			error("class " + fullName() + " may not extend non accessible type " + typeDecl.fullName());

		if(hasSuperclass() && !superclass().accessibleFrom(this))
			error("a superclass must be accessible which " + superclass().name() + " is not");

		// 8.1.4
		for(int i = 0; i < getNumImplements(); i++) {
			TypeDecl decl = getImplements(i).type();
			if(!decl.isCircular() && !decl.accessibleFrom(this))
				error("class " + fullName() + " can not implement non accessible type " + decl.fullName());
		}
	}
	/**
	 * @ast method 
	 * @aspect ExceptionHandling
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:92
	 */
	public void exceptionHandling() {
		constructors();
		super.exceptionHandling();
	}
	/**
	 * @ast method 
	 * @aspect MemberMethods
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:254
	 */
	public Iterator interfacesMethodsIterator() {
		return new Iterator() {
			private Iterator outer = interfacesMethodsSignatureMap().values().iterator();
			private Iterator inner = null;
			public boolean hasNext() {
				if((inner == null || !inner.hasNext()) && outer.hasNext())
					inner = ((SimpleSet)outer.next()).iterator();
				return inner == null ? false : inner.hasNext();
			}
			public Object next() {
				return inner.next();
			}
			public void remove() { throw new UnsupportedOperationException(); }
		};
	}
	/**
	 * @ast method 
	 * @aspect Modifiers
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:94
	 */
	public void checkModifiers() {
		super.checkModifiers();
		// 8.1.1.2 final Classes
		TypeDecl typeDecl = hasSuperclass() ? superclass() : null;
		if(typeDecl != null && typeDecl.isFinal()) {
			error("class " + fullName() + " may not extend final class " + typeDecl.fullName());
		}

	}
	/**
	 * @ast method 
	 * @aspect PrettyPrint
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:70
	 */
	public void toString(StringBuffer s) {
		s.append(indent());
		getModifiers().toString(s);
		s.append("class " + name());
		if(hasSuperClassAccess()) {
			s.append(" extends ");
			getSuperClassAccess().toString(s);
		}
		if(getNumImplements() > 0) {
			s.append(" implements ");
			getImplements(0).toString(s);
			for(int i = 1; i < getNumImplements(); i++) {
				s.append(", ");
				getImplements(i).toString(s);
			}
		}
		ppBodyDecls(s);
	}
	/**
	 * @ast method 
	 * @aspect SuperClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:593
	 */
	public boolean hasSuperclass() {
		return !isObject();
	}
	/**
	 * @ast method 
	 * @aspect SuperClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:597
	 */
	public ClassDecl superclass() {
		if(isObject())
			return null;
		if(hasSuperClassAccess() && !isCircular() && getSuperClassAccess().type().isClassDecl())
			return (ClassDecl)getSuperClassAccess().type();
		return (ClassDecl)typeObject();
	}
	/**
	 * @ast method 
	 * @aspect SuperClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:612
	 */
	public Iterator interfacesIterator() {
		return new Iterator() {
			public boolean hasNext() {
				computeNextCurrent();
				return current != null;
			}
			public Object next() {
				return current;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
			private int index = 0;
			private TypeDecl current = null;
			private void computeNextCurrent() {
				current = null;
				if(isObject() || isCircular())
					return;
				while(index < getNumImplements()) {
					TypeDecl typeDecl = getImplements(index++).type();
					if(!typeDecl.isCircular() && typeDecl.isInterfaceDecl()) {
						current = typeDecl;
						return;
					}
				}
			}
		};
	}
	/**
	 * @ast method 
	 * @aspect TypeHierarchyCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:239
	 */
	public void nameCheck() {
		super.nameCheck();
		if(hasSuperClassAccess() && !getSuperClassAccess().type().isClassDecl())
			error("class may only inherit a class and not " + getSuperClassAccess().type().typeName());
		if(isObject() && hasSuperClassAccess())
			error("class Object may not have superclass");
		if(isObject() && getNumImplements() != 0)
			error("class Object may not implement interfaces");

		// 8.1.3
		if(isCircular())
			error("circular inheritance dependency in " + typeName()); 

		// 8.1.4
		HashSet set = new HashSet();
		for(int i = 0; i < getNumImplements(); i++) {
			TypeDecl decl = getImplements(i).type();
			if(!decl.isInterfaceDecl() && !decl.isUnknown())
				error("type " + fullName() + " tries to implement non interface type " + decl.fullName());
			if(set.contains(decl))
				error("type " + decl.fullName() + " mentionened multiple times in implements clause");
			set.add(decl);
		}

		for(Iterator iter = interfacesMethodsIterator(); iter.hasNext(); ) {
			MethodDecl m = (MethodDecl)iter.next();
			if(localMethodsSignature(m.signature()).isEmpty()) {
				SimpleSet s = superclass().methodsSignature(m.signature());
				for(Iterator i2 = s.iterator(); i2.hasNext(); ) {
					MethodDecl n = (MethodDecl)i2.next();
					if(n.accessibleFrom(this)) {
						interfaceMethodCompatibleWithInherited(m, n);
					}
				}
				if(s.isEmpty()) {
					for(Iterator i2 = interfacesMethodsSignature(m.signature()).iterator(); i2.hasNext(); ) {
						MethodDecl n = (MethodDecl)i2.next();
						if(!n.mayOverrideReturn(m) && !m.mayOverrideReturn(n))
							error("Xthe return type of method " + m.signature() + " in " + m.hostType().typeName() + 
									" does not match the return type of method " + n.signature() + " in " + 
									n.hostType().typeName() + " and may thus not be overriden");
					}
				}
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect TypeHierarchyCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:286
	 */
	private void interfaceMethodCompatibleWithInherited(MethodDecl m, MethodDecl n) {
		if(n.isStatic())
			error("Xa static method may not hide an instance method");
		if(!n.isAbstract() && !n.isPublic())
			error("Xoverriding access modifier error for " + m.signature() + " in " + m.hostType().typeName() + " and " + n.hostType().typeName());
		if(!n.mayOverrideReturn(m) && !m.mayOverrideReturn(m))
			error("Xthe return type of method " + m.signature() + " in " + m.hostType().typeName() + 
					" does not match the return type of method " + n.signature() + " in " + 
					n.hostType().typeName() + " and may thus not be overriden");
		if(!n.isAbstract()) {
			// n implements and overrides method m in the interface
			// may not throw more checked exceptions
			for(int i = 0; i < n.getNumException(); i++) {
				Access e = n.getException(i);
				boolean found = false;
				for(int j = 0; !found && j < m.getNumException(); j++) {
					if(e.type().instanceOf(m.getException(j).type()))
						found = true;
				}
				if(!found && e.type().isUncheckedException())
					error("X" + n.signature() + " in " + n.hostType().typeName() + " may not throw more checked exceptions than overridden method " +
							m.signature() + " in " + m.hostType().typeName());
			}
		}
	}
	/**
	 * @ast method 
	 * @aspect GenerateClassfile
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:38
	 */
	public void generateClassfile() {
		super.generateClassfile();
		String fileName = destinationPath() + File.separator + constantPoolName() + ".class";
		if(options().verbose()) System.out.println("Writing class file to " + fileName);
		try {
			ConstantPool cp = constantPool();

			// force building of constant pool
			cp.addClass(constantPoolName());
			if(hasSuperclass()) {
				cp.addClass(superclass().constantPoolName());
			}
			int numInterfaces = 0;
			for(Iterator iter = interfacesIterator(); iter.hasNext(); numInterfaces++)
				cp.addClass(((TypeDecl)iter.next()).constantPoolName());
			for(Iterator iter = bcFields().iterator(); iter.hasNext(); ) {
				FieldDeclaration field = (FieldDeclaration) iter.next();
				cp.addUtf8(field.name());
				cp.addUtf8(field.type().typeDescriptor());
				field.attributes();
			}
			if(needsEnclosing()) {
				cp.addUtf8("this$0");
				cp.addUtf8(enclosing().typeDescriptor());
				cp.addUtf8("Synthetic");
			}

			for(Iterator iter = bcMethods().iterator(); iter.hasNext(); ) {
				BodyDecl decl = (BodyDecl)iter.next();
				decl.touchMethod(cp);
			}
			if(hasClinit()) {
				cp.addUtf8("<clinit>");
				cp.addUtf8("()V");
				clinit_attributes();
			}
			attributes();


			// Actual ClassFile generation
			File dest = new File(fileName);
			File parentFile = dest.getParentFile();
			if(parentFile != null)
				parentFile.mkdirs();
			FileOutputStream f = new FileOutputStream(fileName);
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(f));
			out.writeInt(magicHeader());
			out.writeChar(minorVersion());
			out.writeChar(majorVersion());
			cp.emit(out);
			int flags = flags();
			if(isNestedType())
				flags = mangledFlags(flags);
			flags |= Modifiers.ACC_SUPER;
			out.writeChar(flags);
			out.writeChar(cp.addClass(constantPoolName()));
			out.writeChar(hasSuperclass() ? cp.addClass(superclass().constantPoolName()) : 0);
			out.writeChar(numInterfaces);
			for(Iterator iter = interfacesIterator(); iter.hasNext(); )
				out.writeChar(cp.addClass(((TypeDecl)iter.next()).constantPoolName()));
			Collection fields = bcFields();
			out.writeChar(fields.size() + (needsEnclosing() ? 1 : 0));
			for(Iterator iter = fields.iterator(); iter.hasNext(); ) {
				FieldDeclaration field = (FieldDeclaration) iter.next();
				out.writeChar(field.flags());
				out.writeChar(cp.addUtf8(field.name()));
				out.writeChar(cp.addUtf8(field.type().typeDescriptor()));
				out.writeChar(field.attributes().size());
				for(Iterator itera = field.attributes().iterator(); itera.hasNext();)
					((Attribute)itera.next()).emit(out);
			}
			if(needsEnclosing()) {
				out.writeChar(0 /*Modifiers.ACC_PRIVATE*/);
				out.writeChar(cp.addUtf8("this$0"));
				out.writeChar(cp.addUtf8(enclosing().typeDescriptor()));
				out.writeChar(1);
				new SyntheticAttribute(cp).emit(out);

			}

			Collection methods = bcMethods();
			out.writeChar(methods.size() + (hasClinit() ? 1 : 0));
			for(Iterator iter = methods.iterator(); iter.hasNext(); ) {
				BodyDecl b = (BodyDecl)iter.next();
				b.generateMethod(out, cp);
			}
			if(hasClinit()) {
				out.writeChar(Modifiers.ACC_STATIC);
				out.writeChar(cp.addUtf8("<clinit>"));
				out.writeChar(cp.addUtf8("()V"));
				out.writeChar(clinit_attributes().size());
				for(Iterator itera = clinit_attributes().iterator(); itera.hasNext();)
					((Attribute)itera.next()).emit(out);
			}
			out.writeChar(attributes().size());
			for(Iterator itera = attributes().iterator(); itera.hasNext();)
				((Attribute)itera.next()).emit(out);

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @ast method 
	 * @declaredat java.ast:1
	 */
	public ClassDecl() {
		super();

		setChild(new Opt(), 1);
		setChild(new List(), 2);
		setChild(new List(), 3);

	}
	/**
	 * @ast method 
	 * @declaredat java.ast:10
	 */
	public ClassDecl(Modifiers p0, String p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
		setChild(p0, 0);
		setID(p1);
		setChild(p2, 1);
		setChild(p3, 2);
		setChild(p4, 3);
	}
	/**
	 * @ast method 
	 * @declaredat java.ast:17
	 */
	public ClassDecl(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
		setChild(p0, 0);
		setID(p1);
		setChild(p2, 1);
		setChild(p3, 2);
		setChild(p4, 3);
	}
	
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:27
	 */
	protected int numChildren() {
		return 4;
	}
	/**
	 * @apilevel internal
	 * @ast method 
	 * @declaredat java.ast:33
	 */
	public boolean mayHaveRewrite() {
		return false;
	}
	/**
	 * Setter for Modifiers
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setModifiers(Modifiers node) {
		setChild(node, 0);
	}
	/**
	 * Getter for Modifiers
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:12
	 */
	public Modifiers getModifiers() {
		return (Modifiers)getChild(0);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:18
	 */
	public Modifiers getModifiersNoTransform() {
		return (Modifiers)getChildNoTransform(0);
	}
	/**
	 * Setter for lexeme ID
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setID(String value) {
		tokenString_ID = value;
	}
	/**
	 * @ast method 
	 * @declaredat java.ast:8
	 */
	public void setID(beaver.Symbol symbol) {
		if(symbol.value != null && !(symbol.value instanceof String))
			throw new UnsupportedOperationException("setID is only valid for String lexemes");
		tokenString_ID = (String)symbol.value;
		IDstart = symbol.getStart();
		IDend = symbol.getEnd();
	}
	/**
	 * Getter for lexeme ID
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:19
	 */
	public String getID() {
		return tokenString_ID != null ? tokenString_ID : "";
	}
	/**
	 * Setter for SuperClassAccessOpt
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setSuperClassAccessOpt(Opt<Access> opt) {
		setChild(opt, 1);
	}
	/**
	 * Does this node have a SuperClassAccess child?
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:12
	 */
	public boolean hasSuperClassAccess() {
		return getSuperClassAccessOpt().getNumChild() != 0;
	}
	/**
	 * Getter for optional child SuperClassAccess
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:19
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Access getSuperClassAccess() {
		return (Access)getSuperClassAccessOpt().getChild(0);
	}
	/**
	 * Setter for optional child SuperClassAccess
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:27
	 */
	public void setSuperClassAccess(Access node) {
		getSuperClassAccessOpt().setChild(node, 0);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:37
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Opt<Access> getSuperClassAccessOpt() {
		return (Opt<Access>)getChild(1);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:44
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Opt<Access> getSuperClassAccessOptNoTransform() {
		return (Opt<Access>)getChildNoTransform(1);
	}
	/**
	 * Setter for ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setImplementsList(List<Access> list) {
		setChild(list, 2);
	}
	/**
	 * @return number of children in ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:12
	 */
	public int getNumImplements() {
		return getImplementsList().getNumChild();
	}
	/**
	 * Getter for child in list ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:19
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Access getImplements(int i) {
		return (Access)getImplementsList().getChild(i);
	}
	/**
	 * Add element to list ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:27
	 */
	public void addImplements(Access node) {
		List<Access> list = (parent == null || state == null) ? getImplementsListNoTransform() : getImplementsList();
		list.addChild(node);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:34
	 */
	public void addImplementsNoTransform(Access node) {
		List<Access> list = getImplementsListNoTransform();
		list.addChild(node);
	}
	/**
	 * Setter for child in list ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:42
	 */
	public void setImplements(Access node, int i) {
		List<Access> list = getImplementsList();
		list.setChild(node, i);
	}
	/**
	 * Getter for Implements list.
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:50
	 */
	public List<Access> getImplementss() {
		return getImplementsList();
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:56
	 */
	public List<Access> getImplementssNoTransform() {
		return getImplementsListNoTransform();
	}
	/**
	 * Getter for list ImplementsList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:63
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<Access> getImplementsList() {
		List<Access> list = (List<Access>)getChild(2);
		list.getNumChild();
		return list;
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:72
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<Access> getImplementsListNoTransform() {
		return (List<Access>)getChildNoTransform(2);
	}
	/**
	 * Setter for BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:5
	 */
	public void setBodyDeclList(List<BodyDecl> list) {
		setChild(list, 3);
	}
	/**
	 * @return number of children in BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:12
	 */
	public int getNumBodyDecl() {
		return getBodyDeclList().getNumChild();
	}
	/**
	 * Getter for child in list BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:19
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public BodyDecl getBodyDecl(int i) {
		return (BodyDecl)getBodyDeclList().getChild(i);
	}
	/**
	 * Add element to list BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:27
	 */
	public void addBodyDecl(BodyDecl node) {
		List<BodyDecl> list = (parent == null || state == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
		list.addChild(node);
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:34
	 */
	public void addBodyDeclNoTransform(BodyDecl node) {
		List<BodyDecl> list = getBodyDeclListNoTransform();
		list.addChild(node);
	}
	/**
	 * Setter for child in list BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:42
	 */
	public void setBodyDecl(BodyDecl node, int i) {
		List<BodyDecl> list = getBodyDeclList();
		list.setChild(node, i);
	}
	/**
	 * Getter for BodyDecl list.
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:50
	 */
	public List<BodyDecl> getBodyDecls() {
		return getBodyDeclList();
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:56
	 */
	public List<BodyDecl> getBodyDeclsNoTransform() {
		return getBodyDeclListNoTransform();
	}
	/**
	 * Getter for list BodyDeclList
	 * @apilevel high-level
	 * @ast method 
	 * @declaredat java.ast:63
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<BodyDecl> getBodyDeclList() {
		List<BodyDecl> list = (List<BodyDecl>)getChild(3);
		list.getNumChild();
		return list;
	}
	/**
	 * @apilevel low-level
	 * @ast method 
	 * @declaredat java.ast:72
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public List<BodyDecl> getBodyDeclListNoTransform() {
		return (List<BodyDecl>)getChildNoTransform(3);
	}
	/**
	 * @attribute syn
	 * @aspect ConstantExpression
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:318
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Constant cast(Constant c) {
		ASTNode$State state = state();
		Constant cast_Constant_value = cast_compute(c);
		return cast_Constant_value;
	}
	/**
	 * @apilevel internal
	 */
	private Constant cast_compute(Constant c) {  return Constant.create(c.stringValue());  }
	/**
	 * @attribute syn
	 * @aspect ConstantExpression
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:380
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Constant add(Constant c1, Constant c2) {
		ASTNode$State state = state();
		Constant add_Constant_Constant_value = add_compute(c1, c2);
		return add_Constant_Constant_value;
	}
	/**
	 * @apilevel internal
	 */
	private Constant add_compute(Constant c1, Constant c2) {  return Constant.create(c1.stringValue() + c2.stringValue());  }
	/**
	 * @attribute syn
	 * @aspect ConstantExpression
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:445
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Constant questionColon(Constant cond, Constant c1, Constant c2) {
		ASTNode$State state = state();
		Constant questionColon_Constant_Constant_Constant_value = questionColon_compute(cond, c1, c2);
		return questionColon_Constant_Constant_Constant_value;
	}
	/**
	 * @apilevel internal
	 */
	private Constant questionColon_compute(Constant cond, Constant c1, Constant c2) {  return Constant.create(cond.booleanValue() ? c1.stringValue() : c2.stringValue());  }
	/**
	 * @attribute syn
	 * @aspect ConstantExpression
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:549
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean eqIsTrue(Expr left, Expr right) {
		ASTNode$State state = state();
		boolean eqIsTrue_Expr_Expr_value = eqIsTrue_compute(left, right);
		return eqIsTrue_Expr_Expr_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean eqIsTrue_compute(Expr left, Expr right) {  return isString() && left.constant().stringValue().equals(right.constant().stringValue());  }
	/**
	 * @attribute syn
	 * @aspect ErrorCheck
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ErrorCheck.jrag:30
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public int lineNumber() {
		ASTNode$State state = state();
		int lineNumber_value = lineNumber_compute();
		return lineNumber_value;
	}
	/**
	 * @apilevel internal
	 */
	private int lineNumber_compute() {  return getLine(IDstart);  }
	/**
	 * @attribute syn
	 * @aspect ConstructScope
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:22
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Collection lookupSuperConstructor() {
		ASTNode$State state = state();
		Collection lookupSuperConstructor_value = lookupSuperConstructor_compute();
		return lookupSuperConstructor_value;
	}
	/**
	 * @apilevel internal
	 */
	private Collection lookupSuperConstructor_compute() {  return hasSuperclass() ? superclass().constructors() : Collections.EMPTY_LIST;  }
	/**
	 * @attribute syn
	 * @aspect ImplicitConstructor
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:227
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean noConstructor() {
		ASTNode$State state = state();
		boolean noConstructor_value = noConstructor_compute();
		return noConstructor_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean noConstructor_compute() {
		if(!compilationUnit().fromSource())
			return false;
		for(int i = 0; i < getNumBodyDecl(); i++)
			if(getBodyDecl(i) instanceof ConstructorDecl)
				return false;
		return true;
	}
	/**
	 * @attribute syn
	 * @aspect MemberMethods
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:269
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public SimpleSet interfacesMethodsSignature(String signature) {
		ASTNode$State state = state();
		SimpleSet interfacesMethodsSignature_String_value = interfacesMethodsSignature_compute(signature);
		return interfacesMethodsSignature_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private SimpleSet interfacesMethodsSignature_compute(String signature) {
		SimpleSet set = (SimpleSet)interfacesMethodsSignatureMap().get(signature);
		if(set != null) return set;
		return SimpleSet.emptySet;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean interfacesMethodsSignatureMap_computed = false;
	/**
	 * @apilevel internal
	 */
	protected HashMap interfacesMethodsSignatureMap_value;
	/**
	 * @attribute syn
	 * @aspect MemberMethods
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:275
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public HashMap interfacesMethodsSignatureMap() {
		if(interfacesMethodsSignatureMap_computed) {
			return interfacesMethodsSignatureMap_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		interfacesMethodsSignatureMap_value = interfacesMethodsSignatureMap_compute();
		if(isFinal && num == state().boundariesCrossed) interfacesMethodsSignatureMap_computed = true;
		return interfacesMethodsSignatureMap_value;
	}
	/**
	 * @apilevel internal
	 */
	private HashMap interfacesMethodsSignatureMap_compute() {
		HashMap map = new HashMap();
		for(Iterator iter = interfacesIterator(); iter.hasNext(); ) {
			TypeDecl typeDecl = (InterfaceDecl)iter.next();
			for(Iterator i2 = typeDecl.methodsIterator(); i2.hasNext(); ) {
				MethodDecl m = (MethodDecl)i2.next();
				putSimpleSetElement(map, m.signature(), m);
			}
		}
		return map;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean methodsSignatureMap_computed = false;
	/**
	 * @apilevel internal
	 */
	protected HashMap methodsSignatureMap_value;
	/**
	 * @attribute syn
	 * @aspect MemberMethods
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:311
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public HashMap methodsSignatureMap() {
		if(methodsSignatureMap_computed) {
			return methodsSignatureMap_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		methodsSignatureMap_value = methodsSignatureMap_compute();
		if(isFinal && num == state().boundariesCrossed) methodsSignatureMap_computed = true;
		return methodsSignatureMap_value;
	}
	/**
	 * @apilevel internal
	 */
	private HashMap methodsSignatureMap_compute() {
		HashMap map = new HashMap(localMethodsSignatureMap());
		if(hasSuperclass()) {
			for(Iterator iter = superclass().methodsIterator(); iter.hasNext(); ) {
				MethodDecl m = (MethodDecl)iter.next();
				if(!m.isPrivate() && m.accessibleFrom(this) && !localMethodsSignatureMap().containsKey(m.signature()))
					putSimpleSetElement(map, m.signature(), m);
			}
		}
		for(Iterator outerIter = interfacesIterator(); outerIter.hasNext(); ) {
			TypeDecl typeDecl = (TypeDecl)outerIter.next();
			for(Iterator iter = typeDecl.methodsIterator(); iter.hasNext(); ) {
				MethodDecl m = (MethodDecl)iter.next();
				if(!m.isPrivate() && m.accessibleFrom(this) && !localMethodsSignatureMap().containsKey(m.signature()))
					if(allMethodsAbstract((SimpleSet)map.get(m.signature())))
						putSimpleSetElement(map, m.signature(), m);
			}
		}
		return map;
	}
	protected java.util.Map ancestorMethods_String_values;
	/**
	 * @attribute syn
	 * @aspect AncestorMethods
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:369
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public SimpleSet ancestorMethods(String signature) {
		Object _parameters = signature;
		if(ancestorMethods_String_values == null) ancestorMethods_String_values = new java.util.HashMap(4);
		if(ancestorMethods_String_values.containsKey(_parameters)) {
			return (SimpleSet)ancestorMethods_String_values.get(_parameters);
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		SimpleSet ancestorMethods_String_value = ancestorMethods_compute(signature);
		if(isFinal && num == state().boundariesCrossed) ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
		return ancestorMethods_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private SimpleSet ancestorMethods_compute(String signature) {
		SimpleSet set = SimpleSet.emptySet;
		if(hasSuperclass()) {
			for(Iterator iter = superclass().localMethodsSignature(signature).iterator(); iter.hasNext(); ) {
				MethodDecl m = (MethodDecl)iter.next();
				if(!m.isPrivate())
					set = set.add(m);
			}
		}
		if(set.size() != 1 || ((MethodDecl)set.iterator().next()).isAbstract()) { 
			for(Iterator iter = interfacesMethodsSignature(signature).iterator(); iter.hasNext(); ) {
				MethodDecl m = (MethodDecl)iter.next();
				set = set.add(m);
			}
		}
		if(!hasSuperclass()) return set;
		if(set.size() == 1) {
			MethodDecl m = (MethodDecl)set.iterator().next();
			if(!m.isAbstract()) {
				boolean done = true;
				for(Iterator iter = superclass().ancestorMethods(signature).iterator(); iter.hasNext(); ) {
					MethodDecl n = (MethodDecl)iter.next();
					if(n.isPrivate() || !n.accessibleFrom(m.hostType()))
						done = false;
				}
				if(done) return set;
			}
		}
		for(Iterator iter = superclass().ancestorMethods(signature).iterator(); iter.hasNext(); ) {
			MethodDecl m = (MethodDecl)iter.next();
			set = set.add(m);
		}
		return set;
	}
	protected java.util.Map memberTypes_String_values;
	/**
	 * @attribute syn
	 * @aspect TypeScopePropagation
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:410
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public SimpleSet memberTypes(String name) {
		Object _parameters = name;
		if(memberTypes_String_values == null) memberTypes_String_values = new java.util.HashMap(4);
		if(memberTypes_String_values.containsKey(_parameters)) {
			return (SimpleSet)memberTypes_String_values.get(_parameters);
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		SimpleSet memberTypes_String_value = memberTypes_compute(name);
		if(isFinal && num == state().boundariesCrossed) memberTypes_String_values.put(_parameters, memberTypes_String_value);
		return memberTypes_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private SimpleSet memberTypes_compute(String name) {
		SimpleSet set = localTypeDecls(name);
		if(!set.isEmpty()) return set;
		for(Iterator outerIter = interfacesIterator(); outerIter.hasNext(); ) {
			TypeDecl type = (TypeDecl)outerIter.next();
			for(Iterator iter = type.memberTypes(name).iterator(); iter.hasNext(); ) {
				TypeDecl decl = (TypeDecl)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this))
					set = set.add(decl);
			}
		}
		if(hasSuperclass()) {
			for(Iterator iter = superclass().memberTypes(name).iterator(); iter.hasNext(); ) {
				TypeDecl decl = (TypeDecl)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this)) {
					set = set.add(decl);
				}
			}
		}
		return set;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean memberFieldsMap_computed = false;
	/**
	 * @apilevel internal
	 */
	protected HashMap memberFieldsMap_value;
	/**
	 * @attribute syn
	 * @aspect Fields
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:274
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public HashMap memberFieldsMap() {
		if(memberFieldsMap_computed) {
			return memberFieldsMap_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		memberFieldsMap_value = memberFieldsMap_compute();
		if(isFinal && num == state().boundariesCrossed) memberFieldsMap_computed = true;
		return memberFieldsMap_value;
	}
	/**
	 * @apilevel internal
	 */
	private HashMap memberFieldsMap_compute() {
		HashMap map = new HashMap(localFieldsMap());
		if(hasSuperclass()) {
			for(Iterator iter = superclass().fieldsIterator(); iter.hasNext(); ) {
				FieldDeclaration decl = (FieldDeclaration)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this) && !localFieldsMap().containsKey(decl.name()))
					putSimpleSetElement(map, decl.name(), decl);
			}
		}
		for(Iterator outerIter = interfacesIterator(); outerIter.hasNext(); ) {
			TypeDecl type = (TypeDecl)outerIter.next();
			for(Iterator iter = type.fieldsIterator(); iter.hasNext(); ) {
				FieldDeclaration decl = (FieldDeclaration)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this) && !localFieldsMap().containsKey(decl.name()))
					putSimpleSetElement(map, decl.name(), decl);
			}
		}
		return map;
	}
	protected java.util.Map memberFields_String_values;
	/**
	 * @attribute syn
	 * @aspect Fields
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:325
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public SimpleSet memberFields(String name) {
		Object _parameters = name;
		if(memberFields_String_values == null) memberFields_String_values = new java.util.HashMap(4);
		if(memberFields_String_values.containsKey(_parameters)) {
			return (SimpleSet)memberFields_String_values.get(_parameters);
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		SimpleSet memberFields_String_value = memberFields_compute(name);
		if(isFinal && num == state().boundariesCrossed) memberFields_String_values.put(_parameters, memberFields_String_value);
		return memberFields_String_value;
	}
	/**
	 * @apilevel internal
	 */
	private SimpleSet memberFields_compute(String name) {
		SimpleSet fields = localFields(name);
		if(!fields.isEmpty())
			return fields; // this causes hiding of fields in superclass and interfaces
		if(hasSuperclass()) {
			for(Iterator iter = superclass().memberFields(name).iterator(); iter.hasNext(); ) {
				FieldDeclaration decl = (FieldDeclaration)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this))
					fields = fields.add(decl);
			}
		}
		for(Iterator outerIter = interfacesIterator(); outerIter.hasNext(); ) {
			TypeDecl type = (TypeDecl)outerIter.next();
			for(Iterator iter = type.memberFields(name).iterator(); iter.hasNext(); ) {
				FieldDeclaration decl = (FieldDeclaration)iter.next();
				if(!decl.isPrivate() && decl.accessibleFrom(this))
					fields = fields.add(decl);
			}
		}
		return fields;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean unimplementedMethods_computed = false;
	/**
	 * @apilevel internal
	 */
	protected Collection unimplementedMethods_value;
	/**
	 * @attribute syn
	 * @aspect Modifiers
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:17
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public Collection unimplementedMethods() {
		if(unimplementedMethods_computed) {
			return unimplementedMethods_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		unimplementedMethods_value = unimplementedMethods_compute();
		if(isFinal && num == state().boundariesCrossed) unimplementedMethods_computed = true;
		return unimplementedMethods_value;
	}
	/**
	 * @apilevel internal
	 */
	private Collection unimplementedMethods_compute() {
		Collection c = new ArrayList();
		for(Iterator iter = interfacesMethodsIterator(); iter.hasNext(); ) {
			MethodDecl m = (MethodDecl)iter.next();
			boolean implemented = false;
			SimpleSet set = (SimpleSet)localMethodsSignature(m.signature());
			if(set.size() == 1) {
				MethodDecl n = (MethodDecl)set.iterator().next();
				if(!n.isAbstract())
					implemented = true;
			}
			if(!implemented) {
				set = (SimpleSet)ancestorMethods(m.signature());
				for(Iterator i2 = set.iterator(); i2.hasNext(); ) {
					MethodDecl n = (MethodDecl)i2.next();
					if(!n.isAbstract())
						implemented = true;
				}
			}
			if(!implemented) {
				c.add(m);
			}
		}

		if(hasSuperclass()) {
			for(Iterator iter = superclass().unimplementedMethods().iterator(); iter.hasNext(); ) {
				MethodDecl m = (MethodDecl)iter.next();
				SimpleSet set = (SimpleSet)localMethodsSignature(m.signature());
				if(set.size() == 1) {
					MethodDecl n = (MethodDecl)set.iterator().next();
					if(n.isAbstract() || !n.overrides(m))
						c.add(m);
				}
				else
					c.add(m);
			}
		}

		for(Iterator iter = localMethodsIterator(); iter.hasNext(); ) {
			MethodDecl m = (MethodDecl)iter.next();
			if(m.isAbstract()) {
				c.add(m);
			}
		}
		return c;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean hasAbstract_computed = false;
	/**
	 * @apilevel internal
	 */
	protected boolean hasAbstract_value;
	/**
	 * @attribute syn
	 * @aspect Modifiers
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:64
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean hasAbstract() {
		if(hasAbstract_computed) {
			return hasAbstract_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		hasAbstract_value = hasAbstract_compute();
		if(isFinal && num == state().boundariesCrossed) hasAbstract_computed = true;
		return hasAbstract_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean hasAbstract_compute() {  return !unimplementedMethods().isEmpty();  }
	protected java.util.Map castingConversionTo_TypeDecl_values;
	/**
	 * @attribute syn
	 * @aspect TypeConversion
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:84
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean castingConversionTo(TypeDecl type) {
		Object _parameters = type;
		if(castingConversionTo_TypeDecl_values == null) castingConversionTo_TypeDecl_values = new java.util.HashMap(4);
		if(castingConversionTo_TypeDecl_values.containsKey(_parameters)) {
			return ((Boolean)castingConversionTo_TypeDecl_values.get(_parameters)).booleanValue();
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		boolean castingConversionTo_TypeDecl_value = castingConversionTo_compute(type);
		if(isFinal && num == state().boundariesCrossed) castingConversionTo_TypeDecl_values.put(_parameters, Boolean.valueOf(castingConversionTo_TypeDecl_value));
		return castingConversionTo_TypeDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean castingConversionTo_compute(TypeDecl type) {
		if(type.isArrayDecl()) {
			return isObject();
		}
		else if(type.isClassDecl()) {
			return this == type || instanceOf(type) || type.instanceOf(this);
		}
		else if(type.isInterfaceDecl()) {
			return !isFinal() || instanceOf(type);
		}
		else return super.castingConversionTo(type);
	}
	/**
	 * @attribute syn
	 * @aspect TypeAnalysis
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:210
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isClassDecl() {
		ASTNode$State state = state();
		boolean isClassDecl_value = isClassDecl_compute();
		return isClassDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isClassDecl_compute() {  return true;  }
	/**
	 * @apilevel internal
	 */
	protected boolean isString_computed = false;
	/**
	 * @apilevel internal
	 */
	protected boolean isString_value;
	/**
	 * @attribute syn
	 * @aspect TypeAnalysis
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:225
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isString() {
		if(isString_computed) {
			return isString_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		isString_value = isString_compute();
		if(isFinal && num == state().boundariesCrossed) isString_computed = true;
		return isString_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isString_compute() {  return fullName().equals("java.lang.String");  }
	/**
	 * @apilevel internal
	 */
	protected boolean isObject_computed = false;
	/**
	 * @apilevel internal
	 */
	protected boolean isObject_value;
	/**
	 * @attribute syn
	 * @aspect TypeAnalysis
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:228
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isObject() {
		if(isObject_computed) {
			return isObject_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		isObject_value = isObject_compute();
		if(isFinal && num == state().boundariesCrossed) isObject_computed = true;
		return isObject_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isObject_compute() {  return name().equals("Object") && packageName().equals("java.lang");  }
	protected java.util.Map instanceOf_TypeDecl_values;
	/**
	 * @attribute syn
	 * @aspect TypeWideningAndIdentity
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:409
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean instanceOf(TypeDecl type) {
		Object _parameters = type;
		if(instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new java.util.HashMap(4);
		if(instanceOf_TypeDecl_values.containsKey(_parameters)) {
			return ((Boolean)instanceOf_TypeDecl_values.get(_parameters)).booleanValue();
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
		if(isFinal && num == state().boundariesCrossed) instanceOf_TypeDecl_values.put(_parameters, Boolean.valueOf(instanceOf_TypeDecl_value));
		return instanceOf_TypeDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean instanceOf_compute(TypeDecl type) {  return type.isSupertypeOfClassDecl(this);  }
	/**
	 * @attribute syn
	 * @aspect TypeWideningAndIdentity
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:424
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isSupertypeOfClassDecl(ClassDecl type) {
		ASTNode$State state = state();
		boolean isSupertypeOfClassDecl_ClassDecl_value = isSupertypeOfClassDecl_compute(type);
		return isSupertypeOfClassDecl_ClassDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isSupertypeOfClassDecl_compute(ClassDecl type) {
		if(super.isSupertypeOfClassDecl(type))
			return true;
		return type.hasSuperclass() && type.superclass() != null && type.superclass().instanceOf(this);
	}
	/**
	 * @attribute syn
	 * @aspect TypeWideningAndIdentity
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:441
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
		ASTNode$State state = state();
		boolean isSupertypeOfInterfaceDecl_InterfaceDecl_value = isSupertypeOfInterfaceDecl_compute(type);
		return isSupertypeOfInterfaceDecl_InterfaceDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isSupertypeOfInterfaceDecl_compute(InterfaceDecl type) {  return isObject();  }
	/**
	 * @attribute syn
	 * @aspect TypeWideningAndIdentity
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:454
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isSupertypeOfArrayDecl(ArrayDecl type) {
		ASTNode$State state = state();
		boolean isSupertypeOfArrayDecl_ArrayDecl_value = isSupertypeOfArrayDecl_compute(type);
		return isSupertypeOfArrayDecl_ArrayDecl_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isSupertypeOfArrayDecl_compute(ArrayDecl type) {
		if(super.isSupertypeOfArrayDecl(type))
			return true;
		return type.hasSuperclass() && type.superclass() != null && type.superclass().instanceOf(this);
	}
	/**
	 * @attribute syn
	 * @aspect NestedTypes
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:536
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isInnerClass() {
		ASTNode$State state = state();
		boolean isInnerClass_value = isInnerClass_compute();
		return isInnerClass_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isInnerClass_compute() {  return isNestedType() && !isStatic() && enclosingType().isClassDecl();  }
	/**
	 * @apilevel internal
	 */
	protected int isCircular_visited = -1;
	/**
	 * @apilevel internal
	 */
	protected boolean isCircular_computed = false;
	/**
	 * @apilevel internal
	 */
	protected boolean isCircular_initialized = false;
	/**
	 * @apilevel internal
	 */
	protected boolean isCircular_value;
	/**
	 * @attribute syn
	 * @aspect Circularity
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:674
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean isCircular() {
		if(isCircular_computed) {
			return isCircular_value;
		}
		ASTNode$State state = state();
		if (!isCircular_initialized) {
			isCircular_initialized = true;
			isCircular_value = true;
		}
		if (!state.IN_CIRCLE) {
			state.IN_CIRCLE = true;
			int num = state.boundariesCrossed;
			boolean isFinal = this.is$Final();
			do {
				isCircular_visited = state.CIRCLE_INDEX;
				state.CHANGE = false;
				boolean new_isCircular_value = isCircular_compute();
				if (new_isCircular_value!=isCircular_value)
					state.CHANGE = true;
				isCircular_value = new_isCircular_value; 
				state.CIRCLE_INDEX++;
			} while (state.CHANGE);
			if(isFinal && num == state().boundariesCrossed) {
				isCircular_computed = true;
			}
			else {
				state.RESET_CYCLE = true;
				isCircular_compute();
				state.RESET_CYCLE = false;
				isCircular_computed = false;
				isCircular_initialized = false;
			}
			state.IN_CIRCLE = false; 
			return isCircular_value;
		}
		if(isCircular_visited != state.CIRCLE_INDEX) {
			isCircular_visited = state.CIRCLE_INDEX;
			if (state.RESET_CYCLE) {
				isCircular_computed = false;
				isCircular_initialized = false;
				isCircular_visited = -1;
				return isCircular_value;
			}
			boolean new_isCircular_value = isCircular_compute();
			if (new_isCircular_value!=isCircular_value)
				state.CHANGE = true;
			isCircular_value = new_isCircular_value; 
			return isCircular_value;
		}
		return isCircular_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean isCircular_compute() {
		if(hasSuperClassAccess()) {
			Access a = getSuperClassAccess().lastAccess();
			while(a != null) {
				if(a.type().isCircular())
					return true;
				a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access)a.qualifier() : null;
			}
		}
		for(int i = 0; i < getNumImplements(); i++) {
			Access a = getImplements(i).lastAccess();
			while(a != null) {
				if(a.type().isCircular())
					return true;
				a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access)a.qualifier() : null;
			}
		}
		return false;
	}
	/**
	 * @apilevel internal
	 */
	protected boolean typeDescriptor_computed = false;
	/**
	 * @apilevel internal
	 */
	protected String typeDescriptor_value;
	/**
	 * @attribute syn
	 * @aspect ConstantPoolNames
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/ConstantPoolNames.jrag:15
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public String typeDescriptor() {
		if(typeDescriptor_computed) {
			return typeDescriptor_value;
		}
		ASTNode$State state = state();
		int num = state.boundariesCrossed;
		boolean isFinal = this.is$Final();
		typeDescriptor_value = typeDescriptor_compute();
		if(isFinal && num == state().boundariesCrossed) typeDescriptor_computed = true;
		return typeDescriptor_value;
	}
	/**
	 * @apilevel internal
	 */
	private String typeDescriptor_compute() {  return "L" + constantPoolName() + ";";  }
	/**
	 * @attribute syn
	 * @aspect CreateBCode
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:820
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public String arrayTypeDescriptor() {
		ASTNode$State state = state();
		String arrayTypeDescriptor_value = arrayTypeDescriptor_compute();
		return arrayTypeDescriptor_value;
	}
	/**
	 * @apilevel internal
	 */
	private String arrayTypeDescriptor_compute() {  return constantPoolName();  }
	/**
	 * @attribute syn
	 * @aspect InnerClasses
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:419
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public TypeDecl superEnclosing() {
		ASTNode$State state = state();
		TypeDecl superEnclosing_value = superEnclosing_compute();
		return superEnclosing_value;
	}
	/**
	 * @apilevel internal
	 */
	private TypeDecl superEnclosing_compute() {  return superclass().enclosing();  }
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:257
	 * @apilevel internal
	 */
	public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
		if(caller == getModifiersNoTransform()) {
			return true;
		}
		return super.Define_boolean_mayBeFinal(caller, child);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:74
	 * @apilevel internal
	 */
	public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
		if(caller == getImplementsListNoTransform()) {
			int childIndex = caller.getIndexOfChild(child);
			return NameType.TYPE_NAME;
		}
		if(caller == getSuperClassAccessOptNoTransform()) {
			return NameType.TYPE_NAME;
		}
		return super.Define_NameType_nameType(caller, child);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:576
	 * @apilevel internal
	 */
	public TypeDecl Define_TypeDecl_hostType(ASTNode caller, ASTNode child) {
		if(caller == getImplementsListNoTransform()) {
			int childIndex = caller.getIndexOfChild(child);
			return hostType();
		}
		if(caller == getSuperClassAccessOptNoTransform()) {
			return hostType();
		}
		return super.Define_TypeDecl_hostType(caller, child);
	}
	/**
	 * @apilevel internal
	 */
	public ASTNode rewriteTo() {
		return super.rewriteTo();
	}
}
