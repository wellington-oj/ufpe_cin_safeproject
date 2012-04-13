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
 * @declaredat java.ast:72
 */
public class ConstructorDecl extends BodyDecl implements Cloneable, ExceptionHolder {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    accessibleFrom_TypeDecl_values = null;
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    throwsException_TypeDecl_values = null;
    name_computed = false;
    name_value = null;
    signature_computed = false;
    signature_value = null;
    sameSignature_ConstructorDecl_values = null;
    moreSpecificThan_ConstructorDecl_values = null;
    parameterDeclaration_String_values = null;
    circularThisInvocation_ConstructorDecl_values = null;
    attributes_computed = false;
    attributes_value = null;
    descName_computed = false;
    descName_value = null;
    bytecodes_ConstantPool_values = null;
    flags_computed = false;
    localNumOfFirstParameter_computed = false;
    offsetFirstEnclosingVariable_computed = false;
    handlesException_TypeDecl_values = null;
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
  public ConstructorDecl clone() throws CloneNotSupportedException {
    ConstructorDecl node = (ConstructorDecl)super.clone();
    node.accessibleFrom_TypeDecl_values = null;
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.throwsException_TypeDecl_values = null;
    node.name_computed = false;
    node.name_value = null;
    node.signature_computed = false;
    node.signature_value = null;
    node.sameSignature_ConstructorDecl_values = null;
    node.moreSpecificThan_ConstructorDecl_values = null;
    node.parameterDeclaration_String_values = null;
    node.circularThisInvocation_ConstructorDecl_values = null;
    node.attributes_computed = false;
    node.attributes_value = null;
    node.descName_computed = false;
    node.descName_value = null;
    node.bytecodes_ConstantPool_values = null;
    node.flags_computed = false;
    node.localNumOfFirstParameter_computed = false;
    node.offsetFirstEnclosingVariable_computed = false;
    node.handlesException_TypeDecl_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ConstructorDecl copy() {
      try {
        ConstructorDecl node = (ConstructorDecl)clone();
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
  public ConstructorDecl fullCopy() {
    ConstructorDecl res = (ConstructorDecl)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect ConstructorDecl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:164
   */
  public boolean applicable(List argList) {
    if(getNumParameter() != argList.getNumChild())
      return false;
    for(int i = 0; i < getNumParameter(); i++) {
      TypeDecl arg = ((Expr)argList.getChild(i)).type();
      TypeDecl parameter = getParameter(i).type();
      if(!arg.instanceOf(parameter)) {
        return false;
      }  
    }
    return true;
  }
  /**
   * Flag to indicate if this constructor is an auto-generated
   * default constructor. Default constructors are not pretty
   * printed.
   * @ast method 
   * @aspect ImplicitConstructor
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:191
   */
  

  /**
   * Flag to indicate if this constructor is an auto-generated
   * default constructor. Default constructors are not pretty
   * printed.
   */
  private boolean isDefaultConstructor = false;
  /**
   * Set the default constructor flag. Causes this constructor
   * to not be pretty printed.
   * @ast method 
   * @aspect ImplicitConstructor
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:196
   */
  public void setDefaultConstructor() {
	  isDefaultConstructor = true;
  }
  /**
   * @ast method 
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:108
   */
  public void checkModifiers() {
    super.checkModifiers();
  }
  /**
   * @ast method 
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:68
   */
  public void nameCheck() {
    super.nameCheck();
    // 8.8
    if(!hostType().name().equals(name()))
      error("constructor " + name() +" does not have the same name as the simple name of the host class " + hostType().name());
    
    // 8.8.2
    if(hostType().lookupConstructor(this) != this)
      error("constructor with signature " + signature() + " is multiply declared in type " + hostType().typeName());

    if(circularThisInvocation(this))
      error("The constructor " + signature() + " may not directly or indirectly invoke itself");
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:119
   */
  public void toString(StringBuffer s) {
    if(isDefaultConstructor()) return;
    s.append(indent());
    getModifiers().toString(s);
    s.append(name() + "(");
    if(getNumParameter() > 0) {
      getParameter(0).toString(s);
      for(int i = 1; i < getNumParameter(); i++) {
        s.append(", ");
        getParameter(i).toString(s);
      }
    }
    s.append(")");
    if(getNumException() > 0) {
      s.append(" throws ");
      getException(0).toString(s);
      for(int i = 1; i < getNumException(); i++) {
        s.append(", ");
        getException(i).toString(s);
      }
    }
    
    s.append(" {");
    if(hasConstructorInvocation()) {
      getConstructorInvocation().toString(s);
    }
    for(int i = 0; i < getBlock().getNumStmt(); i++) {
      getBlock().getStmt(i).toString(s);
    }
    s.append(indent());
    s.append("}");
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:424
   */
  public void typeCheck() {
    // 8.8.4 (8.4.4)
    TypeDecl exceptionType = typeThrowable();
    for(int i = 0; i < getNumException(); i++) {
      TypeDecl typeDecl = getException(i).type();
      if(!typeDecl.instanceOf(exceptionType))
        error(signature() + " throws non throwable type " + typeDecl.fullName());
    }
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:912
   */
  public void emitInvokeConstructor(CodeGeneration gen) {
    int size = -1;
    for(int i = 0; i < getNumParameter(); i++)
      size -= getParameter(i).type().variableSize();
    if(hostType().needsEnclosing())
      size--;
    if(hostType().needsSuperEnclosing()) {
      size--;
    }
    String classname = hostType().constantPoolName();
    String      desc = descName();
    String      name = "<init>";
    int index = gen.constantPool().addMethodref(classname, name, desc);
    gen.emit(Bytecode.INVOKESPECIAL, size).add2(index);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:111
   */
  private void generateBytecodes(CodeGeneration gen) {
    int label = gen.variableScopeLabel();
    gen.addLocalVariableEntryAtCurrentPC("this", hostType().typeDescriptor(), 0, label);
    for(int i = 0; i < getNumParameter(); i++) {
      ParameterDeclaration p = (ParameterDeclaration)getParameter(i);
      gen.addLocalVariableEntryAtCurrentPC(
        p.name(), p.type().typeDescriptor(), p.localNum(), label
      );
    }
    createBCode(gen);
    gen.emitReturn();
    gen.addVariableScopeLabel(label);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:137
   */
  public void createBCode(CodeGeneration gen) {
    try {
    boolean needsInit = true;

    if(hasConstructorInvocation()) {
      getConstructorInvocation().createBCode(gen);
      Stmt stmt = getConstructorInvocation();
      if(stmt instanceof ExprStmt) {
        ExprStmt exprStmt = (ExprStmt)stmt;
        Expr expr = exprStmt.getExpr();
        if(!expr.isSuperConstructorAccess())
          needsInit = false;

      }
    }

    if(needsEnclosing()) {
      gen.emitLoadReference(0);
      gen.emitLoadReference(1);
      String classname = hostType().constantPoolName();
      String desc = enclosing().typeDescriptor();
      String name = "this$0";
      int index = gen.constantPool().addFieldref(classname, name, desc);
      gen.emit(Bytecode.PUTFIELD, -2).add2(index);
    }

    int localIndex = offsetFirstEnclosingVariable();
    for(Iterator iter = hostType().enclosingVariables().iterator(); iter.hasNext(); ) {
        Variable v = (Variable)iter.next();
        gen.emitLoadReference(0);
        v.type().emitLoadLocal(gen, localIndex);
        String classname = hostType().constantPoolName();
        String desc = v.type().typeDescriptor();
        String name = "val$" + v.name();
        int index = gen.constantPool().addFieldref(classname, name, desc);
        gen.emit(Bytecode.PUTFIELD, -1 - v.type().variableSize()).add2(index);
        localIndex += v.type().variableSize();
    }

    if(needsInit) {
      TypeDecl typeDecl = hostType();
      for(int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
        BodyDecl b = typeDecl.getBodyDecl(i);
        if(b instanceof FieldDeclaration && b.isBytecodeField() && b.generate()) {
          FieldDeclaration f = (FieldDeclaration)b;
          if(!f.isStatic() && f.hasInit()) {
            gen.emit(Bytecode.ALOAD_0);
            f.getInit().createBCode(gen);
            f.getInit().type().emitAssignConvTo(gen, f.type()); // AssignConversion
            f.emitStoreField(gen, hostType());
          }
        }
        else if(b instanceof InstanceInitializer) {
          b.createBCode(gen);
        }
      }
    }
    gen.maxLocals = Math.max(gen.maxLocals, getBlock().localNum());
    getBlock().createBCode(gen);
    } catch (Error e) {
      System.err.println(hostType().typeName() + ": " + this);
      throw e;
    }
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:252
   */
  public void generateMethod(DataOutputStream out, ConstantPool cp) throws IOException {
    out.writeChar(flags());
    out.writeChar(cp.addUtf8("<init>"));
    out.writeChar(cp.addUtf8(descName()));
    out.writeChar(attributes().size());
    for(Iterator itera = attributes().iterator(); itera.hasNext();)
      ((Attribute)itera.next()).emit(out);
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:268
   */
  public void touchMethod(ConstantPool cp) {
    cp.addUtf8("<init>");
    cp.addUtf8(descName());
    attributes();
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:376
   */
  public boolean clear() {
    getBlock().clear();
    setBlock(new Block(new List()));
    bytecodes_ConstantPool_values = null;
    return false;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:445
   */
  

  // add val$name as parameters to the constructor
  protected boolean addEnclosingVariables = true;
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:446
   */
  public void addEnclosingVariables() {
    if(!addEnclosingVariables) return;
    addEnclosingVariables = false;
    hostType().addEnclosingVariables();
    for(Iterator iter = hostType().enclosingVariables().iterator(); iter.hasNext(); ) {
      Variable v = (Variable)iter.next();
      getParameterList().add(new ParameterDeclaration(v.type(), "val$" + v.name()));
    }
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:480
   */
  public ConstructorDecl createAccessor() {
    ConstructorDecl c = (ConstructorDecl)hostType().getAccessor(this, "constructor");
    if(c != null) return c;

    // make sure enclosing varibles are added as parameters prior to building accessor
    addEnclosingVariables();

    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = createAccessorParameters();

    List exceptionList = new List(); 
    for(int i = 0; i < getNumException(); i++)
      exceptionList.add(getException(i).type().createQualifiedAccess());
    
    // add all parameters as arguments except for the dummy parameter
    List args = new List();
    for(int i = 0; i < parameters.getNumChildNoTransform() - 1; i++)
      args.add(new VarAccess(((ParameterDeclaration)parameters.getChildNoTransform(i)).name()));
    ConstructorAccess access = new ConstructorAccess("this", args);
    access.addEnclosingVariables = false;

    c = new ConstructorDecl(
      modifiers,
      name(),
      parameters,
      exceptionList,
      new Opt(
        new ExprStmt(
          access
        )
      ),
      new Block(
        new List().add(new ReturnStmt(new Opt()))
      )
    );
    c = hostType().addConstructor(c);
    c.addEnclosingVariables = false;
    hostType().addAccessor(this, "constructor", c);
    return c;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:524
   */
  protected List createAccessorParameters() {
    List parameters = new List();
    for (int i=0; i<getNumParameter(); i++)
      parameters.add(new ParameterDeclaration(getParameter(i).type(), getParameter(i).name()));
    parameters.add(new ParameterDeclaration(createAnonymousJavaTypeDecl().createBoundAccess(), ("p" + getNumParameter())));
    return parameters;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:532
   */
  protected TypeDecl createAnonymousJavaTypeDecl() {
    ClassDecl classDecl =
      new ClassDecl(
          new Modifiers(new List().add(new Modifier("synthetic"))),
          "" + hostType().nextAnonymousIndex(),
          new Opt(),
          new List(),
          new List()
      );
    classDecl = hostType().addMemberClass(classDecl);
    hostType().addNestedType(classDecl);
    return classDecl;
  }
  /**
   * @ast method 
   * @aspect Transformations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Transformations.jrag:119
   */
  public void transformation() {
    // this$val as fields and constructor parameters
    addEnclosingVariables();
    super.transformation();
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ConstructorDecl() {
    super();

    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);

  }
  /**
   * @ast method 
   * @declaredat java.ast:10
   */
  public ConstructorDecl(Modifiers p0, String p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @ast method 
   * @declaredat java.ast:18
   */
  public ConstructorDecl(Modifiers p0, beaver.Symbol p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:29
   */
  protected int numChildren() {
    return 5;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:35
   */
  public boolean mayHaveRewrite() {
    return true;
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
  /**   * @apilevel internal   * @ast method 
   * @declaredat java.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_ID;
  /**
   * @ast method 
   * @declaredat java.ast:9
   */
  
  public int IDstart;
  /**
   * @ast method 
   * @declaredat java.ast:10
   */
  
  public int IDend;
  /**
   * @ast method 
   * @declaredat java.ast:11
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
   * @declaredat java.ast:22
   */
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * Setter for ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setParameterList(List<ParameterDeclaration> list) {
    setChild(list, 1);
  }
  /**
   * @return number of children in ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public int getNumParameter() {
    return getParameterList().getNumChild();
  }
  /**
   * Getter for child in list ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ParameterDeclaration getParameter(int i) {
    return (ParameterDeclaration)getParameterList().getChild(i);
  }
  /**
   * Add element to list ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void addParameter(ParameterDeclaration node) {
    List<ParameterDeclaration> list = (parent == null || state == null) ? getParameterListNoTransform() : getParameterList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:34
   */
  public void addParameterNoTransform(ParameterDeclaration node) {
    List<ParameterDeclaration> list = getParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:42
   */
  public void setParameter(ParameterDeclaration node, int i) {
    List<ParameterDeclaration> list = getParameterList();
    list.setChild(node, i);
  }
  /**
   * Getter for Parameter list.
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:50
   */
  public List<ParameterDeclaration> getParameters() {
    return getParameterList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:56
   */
  public List<ParameterDeclaration> getParametersNoTransform() {
    return getParameterListNoTransform();
  }
  /**
   * Getter for list ParameterList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<ParameterDeclaration> getParameterList() {
    List<ParameterDeclaration> list = (List<ParameterDeclaration>)getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<ParameterDeclaration> getParameterListNoTransform() {
    return (List<ParameterDeclaration>)getChildNoTransform(1);
  }
  /**
   * Setter for ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setExceptionList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * @return number of children in ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public int getNumException() {
    return getExceptionList().getNumChild();
  }
  /**
   * Getter for child in list ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Access getException(int i) {
    return (Access)getExceptionList().getChild(i);
  }
  /**
   * Add element to list ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void addException(Access node) {
    List<Access> list = (parent == null || state == null) ? getExceptionListNoTransform() : getExceptionList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:34
   */
  public void addExceptionNoTransform(Access node) {
    List<Access> list = getExceptionListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:42
   */
  public void setException(Access node, int i) {
    List<Access> list = getExceptionList();
    list.setChild(node, i);
  }
  /**
   * Getter for Exception list.
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:50
   */
  public List<Access> getExceptions() {
    return getExceptionList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:56
   */
  public List<Access> getExceptionsNoTransform() {
    return getExceptionListNoTransform();
  }
  /**
   * Getter for list ExceptionList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Access> getExceptionList() {
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
  public List<Access> getExceptionListNoTransform() {
    return (List<Access>)getChildNoTransform(2);
  }
  /**
   * Setter for ConstructorInvocationOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setConstructorInvocationOpt(Opt<Stmt> opt) {
    setChild(opt, 3);
  }
  /**
   * Does this node have a ConstructorInvocation child?
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public boolean hasConstructorInvocation() {
    return getConstructorInvocationOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child ConstructorInvocation
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Stmt getConstructorInvocation() {
    return (Stmt)getConstructorInvocationOpt().getChild(0);
  }
  /**
   * Setter for optional child ConstructorInvocation
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void setConstructorInvocation(Stmt node) {
    getConstructorInvocationOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Stmt> getConstructorInvocationOpt() {
    return (Opt<Stmt>)getChild(3);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Stmt> getConstructorInvocationOptNoTransform() {
    return (Opt<Stmt>)getChildNoTransform(3);
  }
  /**
   * Setter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setBlock(Block node) {
    setChild(node, 4);
  }
  /**
   * Getter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Block getBlock() {
    return (Block)getChild(4);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Block getBlockNoTransform() {
    return (Block)getChildNoTransform(4);
  }
  protected java.util.Map accessibleFrom_TypeDecl_values;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AccessControl.jrag:94
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean accessibleFrom(TypeDecl type) {
    Object _parameters = type;
    if(accessibleFrom_TypeDecl_values == null) accessibleFrom_TypeDecl_values = new java.util.HashMap(4);
    if(accessibleFrom_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)accessibleFrom_TypeDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean accessibleFrom_TypeDecl_value = accessibleFrom_compute(type);
if(isFinal && num == state().boundariesCrossed) accessibleFrom_TypeDecl_values.put(_parameters, Boolean.valueOf(accessibleFrom_TypeDecl_value));
    return accessibleFrom_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean accessibleFrom_compute(TypeDecl type) {
    if(!hostType().accessibleFrom(type))
      return false;
    else if(isPublic())
      return true;
    else if(isProtected()) {
      return true;
    }
    else if(isPrivate()) {
      return hostType().topLevelType() == type.topLevelType();
    }
    else
      return hostPackage().equals(type.hostPackage());
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:295
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if(isDAafter_Variable_values == null) isDAafter_Variable_values = new java.util.HashMap(4);
    if(isDAafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAafter_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = isDAafter_compute(v);
if(isFinal && num == state().boundariesCrossed) isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {  return getBlock().isDAafter(v) && getBlock().checkReturnDA(v);  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:751
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafter(Variable v) {
    Object _parameters = v;
    if(isDUafter_Variable_values == null) isDUafter_Variable_values = new java.util.HashMap(4);
    if(isDUafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUafter_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDUafter_Variable_value = isDUafter_compute(v);
if(isFinal && num == state().boundariesCrossed) isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {  return getBlock().isDUafter(v) && getBlock().checkReturnDU(v);  }
  protected java.util.Map throwsException_TypeDecl_values;
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:145
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean throwsException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if(throwsException_TypeDecl_values == null) throwsException_TypeDecl_values = new java.util.HashMap(4);
    if(throwsException_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)throwsException_TypeDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean throwsException_TypeDecl_value = throwsException_compute(exceptionType);
if(isFinal && num == state().boundariesCrossed) throwsException_TypeDecl_values.put(_parameters, Boolean.valueOf(throwsException_TypeDecl_value));
    return throwsException_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean throwsException_compute(TypeDecl exceptionType) {
    for(int i = 0; i < getNumException(); i++)
      if(exceptionType.instanceOf(getException(i).type()))
        return true;
    return false;
  }
  /**
   * @apilevel internal
   */
  protected boolean name_computed = false;
  /**
   * @apilevel internal
   */
  protected String name_value;
  /**
   * @attribute syn
   * @aspect ConstructorDecl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:130
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String name() {
    if(name_computed) {
      return name_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    name_value = name_compute();
if(isFinal && num == state().boundariesCrossed) name_computed = true;
    return name_value;
  }
  /**
   * @apilevel internal
   */
  private String name_compute() {  return getID();  }
  /**
   * @apilevel internal
   */
  protected boolean signature_computed = false;
  /**
   * @apilevel internal
   */
  protected String signature_value;
  /**
   * @attribute syn
   * @aspect ConstructorDecl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:132
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String signature() {
    if(signature_computed) {
      return signature_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    signature_value = signature_compute();
if(isFinal && num == state().boundariesCrossed) signature_computed = true;
    return signature_value;
  }
  /**
   * @apilevel internal
   */
  private String signature_compute() {
    StringBuffer s = new StringBuffer();
    s.append(name() + "(");
    for(int i = 0; i < getNumParameter(); i++) {
      s.append(getParameter(i));
      if(i != getNumParameter() - 1)
        s.append(", ");
    }
    s.append(")");
    return s.toString();
  }
  protected java.util.Map sameSignature_ConstructorDecl_values;
  /**
   * @attribute syn
   * @aspect ConstructorDecl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:145
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean sameSignature(ConstructorDecl c) {
    Object _parameters = c;
    if(sameSignature_ConstructorDecl_values == null) sameSignature_ConstructorDecl_values = new java.util.HashMap(4);
    if(sameSignature_ConstructorDecl_values.containsKey(_parameters)) {
      return ((Boolean)sameSignature_ConstructorDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean sameSignature_ConstructorDecl_value = sameSignature_compute(c);
if(isFinal && num == state().boundariesCrossed) sameSignature_ConstructorDecl_values.put(_parameters, Boolean.valueOf(sameSignature_ConstructorDecl_value));
    return sameSignature_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean sameSignature_compute(ConstructorDecl c) {
    if(!name().equals(c.name()))
      return false;
    if(c.getNumParameter() != getNumParameter())
      return false;
    for(int i = 0; i < getNumParameter(); i++)
      if(!c.getParameter(i).type().equals(getParameter(i).type()))
        return false;
    return true;
  }
  protected java.util.Map moreSpecificThan_ConstructorDecl_values;
  /**
   * @attribute syn
   * @aspect ConstructorDecl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:156
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean moreSpecificThan(ConstructorDecl m) {
    Object _parameters = m;
    if(moreSpecificThan_ConstructorDecl_values == null) moreSpecificThan_ConstructorDecl_values = new java.util.HashMap(4);
    if(moreSpecificThan_ConstructorDecl_values.containsKey(_parameters)) {
      return ((Boolean)moreSpecificThan_ConstructorDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean moreSpecificThan_ConstructorDecl_value = moreSpecificThan_compute(m);
if(isFinal && num == state().boundariesCrossed) moreSpecificThan_ConstructorDecl_values.put(_parameters, Boolean.valueOf(moreSpecificThan_ConstructorDecl_value));
    return moreSpecificThan_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean moreSpecificThan_compute(ConstructorDecl m) {
    for(int i = 0; i < getNumParameter(); i++) {
      if(!getParameter(i).type().instanceOf(m.getParameter(i).type()))
        return false;
    }
    return true;
  }
  /**
   * @return true if this is an auto-generated default constructor
   * @attribute syn
   * @aspect ImplicitConstructor
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:202
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDefaultConstructor() {
      ASTNode$State state = state();
    boolean isDefaultConstructor_value = isDefaultConstructor_compute();
    return isDefaultConstructor_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDefaultConstructor_compute() {  return isDefaultConstructor;  }
  protected java.util.Map parameterDeclaration_String_values;
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:105
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet parameterDeclaration(String name) {
    Object _parameters = name;
    if(parameterDeclaration_String_values == null) parameterDeclaration_String_values = new java.util.HashMap(4);
    if(parameterDeclaration_String_values.containsKey(_parameters)) {
      return (SimpleSet)parameterDeclaration_String_values.get(_parameters);
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    SimpleSet parameterDeclaration_String_value = parameterDeclaration_compute(name);
if(isFinal && num == state().boundariesCrossed) parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
    return parameterDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet parameterDeclaration_compute(String name) {
    for(int i = 0; i < getNumParameter(); i++)
      if(getParameter(i).name().equals(name))
        return (ParameterDeclaration)getParameter(i);
    return SimpleSet.emptySet;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:215
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSynthetic() {
      ASTNode$State state = state();
    boolean isSynthetic_value = isSynthetic_compute();
    return isSynthetic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSynthetic_compute() {  return getModifiers().isSynthetic();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:233
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPublic() {
      ASTNode$State state = state();
    boolean isPublic_value = isPublic_compute();
    return isPublic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPublic_compute() {  return getModifiers().isPublic();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:234
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPrivate() {
      ASTNode$State state = state();
    boolean isPrivate_value = isPrivate_compute();
    return isPrivate_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPrivate_compute() {  return getModifiers().isPrivate();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:235
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isProtected() {
      ASTNode$State state = state();
    boolean isProtected_value = isProtected_compute();
    return isProtected_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isProtected_compute() {  return getModifiers().isProtected();  }
  protected java.util.Map circularThisInvocation_ConstructorDecl_values;
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:83
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean circularThisInvocation(ConstructorDecl decl) {
    Object _parameters = decl;
    if(circularThisInvocation_ConstructorDecl_values == null) circularThisInvocation_ConstructorDecl_values = new java.util.HashMap(4);
    if(circularThisInvocation_ConstructorDecl_values.containsKey(_parameters)) {
      return ((Boolean)circularThisInvocation_ConstructorDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean circularThisInvocation_ConstructorDecl_value = circularThisInvocation_compute(decl);
if(isFinal && num == state().boundariesCrossed) circularThisInvocation_ConstructorDecl_values.put(_parameters, Boolean.valueOf(circularThisInvocation_ConstructorDecl_value));
    return circularThisInvocation_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean circularThisInvocation_compute(ConstructorDecl decl) {
    if(hasConstructorInvocation()) {
      Expr e = ((ExprStmt)getConstructorInvocation()).getExpr();
      if(e instanceof ConstructorAccess) {
        ConstructorDecl constructorDecl = ((ConstructorAccess)e).decl();
        if(constructorDecl == decl)
          return true;
        return constructorDecl.circularThisInvocation(decl);
      }
    }
    return false;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:268
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl type() {
      ASTNode$State state = state();
    TypeDecl type_value = type_compute();
    return type_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl type_compute() {  return unknownType();  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:274
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVoid() {
      ASTNode$State state = state();
    boolean isVoid_value = isVoid_compute();
    return isVoid_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVoid_compute() {  return true;  }
  /**
   * @apilevel internal
   */
  protected boolean attributes_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection attributes_value;
  /**
   * @attribute syn
   * @aspect Attributes
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Attributes.jrag:198
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection attributes() {
    if(attributes_computed) {
      return attributes_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    attributes_value = attributes_compute();
if(isFinal && num == state().boundariesCrossed) attributes_computed = true;
    return attributes_value;
  }
  /**
   * @apilevel internal
   */
  private Collection attributes_compute() {
    ArrayList l = new ArrayList();
    l.add(new CodeAttribute(bytecodes(hostType().constantPool()), null));
    l.add(new ExceptionsAttribute(bytecodes(hostType().constantPool()), this));
    if(getModifiers().isSynthetic())
      l.add(new SyntheticAttribute(hostType().constantPool()));
    return l;
  }
  /**
   * @apilevel internal
   */
  protected boolean descName_computed = false;
  /**
   * @apilevel internal
   */
  protected String descName_value;
  /**
   * @attribute syn
   * @aspect ConstantPoolNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/ConstantPoolNames.jrag:48
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String descName() {
    if(descName_computed) {
      return descName_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    descName_value = descName_compute();
if(isFinal && num == state().boundariesCrossed) descName_computed = true;
    return descName_value;
  }
  /**
   * @apilevel internal
   */
  private String descName_compute() {
    StringBuffer b = new StringBuffer();
    b.append("(");
    // this$0
    if(needsEnclosing())
      b.append(enclosing().typeDescriptor());
    if(needsSuperEnclosing())
      b.append(superEnclosing().typeDescriptor());
    // args
    for (int i=0; i<getNumParameter(); i++)
      b.append(getParameter(i).type().typeDescriptor());
    b.append(")V");
    return b.toString();
  }
  protected java.util.Map bytecodes_ConstantPool_values;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:100
   */
  @SuppressWarnings({"unchecked", "cast"})
  public CodeGeneration bytecodes(ConstantPool constantPool) {
    Object _parameters = constantPool;
    if(bytecodes_ConstantPool_values == null) bytecodes_ConstantPool_values = new java.util.HashMap(4);
    if(bytecodes_ConstantPool_values.containsKey(_parameters)) {
      return (CodeGeneration)bytecodes_ConstantPool_values.get(_parameters);
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    CodeGeneration bytecodes_ConstantPool_value = bytecodes_compute(constantPool);
if(isFinal && num == state().boundariesCrossed) bytecodes_ConstantPool_values.put(_parameters, bytecodes_ConstantPool_value);
    return bytecodes_ConstantPool_value;
  }
  /**
   * @apilevel internal
   */
  private CodeGeneration bytecodes_compute(ConstantPool constantPool) {
    CodeGeneration gen = new CodeGeneration(constantPool);
    generateBytecodes(gen);
    if(!gen.numberFormatError())
      return gen;
    gen = new CodeGeneration(constantPool, true);
    generateBytecodes(gen);
    if(!gen.numberFormatError())
      return gen;
    throw new Error("Could not generate code for " + signature() + " in " + hostType().typeName());
  }
  /**
   * @apilevel internal
   */
  protected boolean flags_computed = false;
  /**
   * @apilevel internal
   */
  protected int flags_value;
  /**
   * @attribute syn
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:54
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int flags() {
    if(flags_computed) {
      return flags_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    flags_value = flags_compute();
if(isFinal && num == state().boundariesCrossed) flags_computed = true;
    return flags_value;
  }
  /**
   * @apilevel internal
   */
  private int flags_compute() {
    int res = 0;
    if(isPublic()) res |= Modifiers.ACC_PUBLIC;
    if(isPrivate()) res |= Modifiers.ACC_PRIVATE;
    if(isProtected()) res |= Modifiers.ACC_PROTECTED;
    //if(isSynchronized()) res |= Modifiers.ACC_SYNCHRONIZED;
    //if(isStrictfp()) res |= Modifiers.ACC_STRICT;
    return res;
  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:298
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBytecodeMethod() {
      ASTNode$State state = state();
    boolean isBytecodeMethod_value = isBytecodeMethod_compute();
    return isBytecodeMethod_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBytecodeMethod_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:333
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean flush() {
      ASTNode$State state = state();
    boolean flush_value = flush_compute();
    return flush_value;
  }
  /**
   * @apilevel internal
   */
  private boolean flush_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:421
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean needsEnclosing() {
      ASTNode$State state = state();
    boolean needsEnclosing_value = needsEnclosing_compute();
    return needsEnclosing_value;
  }
  /**
   * @apilevel internal
   */
  private boolean needsEnclosing_compute() {  return hostType().needsEnclosing();  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:422
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean needsSuperEnclosing() {
      ASTNode$State state = state();
    boolean needsSuperEnclosing_value = needsSuperEnclosing_compute();
    return needsSuperEnclosing_value;
  }
  /**
   * @apilevel internal
   */
  private boolean needsSuperEnclosing_compute() {  return hostType().needsSuperEnclosing();  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:424
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl enclosing() {
      ASTNode$State state = state();
    TypeDecl enclosing_value = enclosing_compute();
    return enclosing_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl enclosing_compute() {  return hostType().enclosing();  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:425
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
  private TypeDecl superEnclosing_compute() {  return hostType().superEnclosing();  }
  /**
   * @apilevel internal
   */
  protected boolean localNumOfFirstParameter_computed = false;
  /**
   * @apilevel internal
   */
  protected int localNumOfFirstParameter_value;
  /**
   * @attribute syn
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:55
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int localNumOfFirstParameter() {
    if(localNumOfFirstParameter_computed) {
      return localNumOfFirstParameter_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    localNumOfFirstParameter_value = localNumOfFirstParameter_compute();
if(isFinal && num == state().boundariesCrossed) localNumOfFirstParameter_computed = true;
    return localNumOfFirstParameter_value;
  }
  /**
   * @apilevel internal
   */
  private int localNumOfFirstParameter_compute() {
    int i = 1;
    if(hostType().needsEnclosing())
      i++;
    if(hostType().needsSuperEnclosing())
      i++;
    return i;
  }
  /**
   * @apilevel internal
   */
  protected boolean offsetFirstEnclosingVariable_computed = false;
  /**
   * @apilevel internal
   */
  protected int offsetFirstEnclosingVariable_value;
  /**
   * @attribute syn
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:64
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int offsetFirstEnclosingVariable() {
    if(offsetFirstEnclosingVariable_computed) {
      return offsetFirstEnclosingVariable_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    offsetFirstEnclosingVariable_value = offsetFirstEnclosingVariable_compute();
if(isFinal && num == state().boundariesCrossed) offsetFirstEnclosingVariable_computed = true;
    return offsetFirstEnclosingVariable_value;
  }
  /**
   * @apilevel internal
   */
  private int offsetFirstEnclosingVariable_compute() {
    int localIndex = localNumOfFirstParameter();
    Collection vars = hostType().enclosingVariables();
    if(vars.isEmpty())
      return localIndex;
    String name = "val$" + ((Variable)vars.iterator().next()).name();
    for(int i = 0; !getParameter(i).name().equals(name); i++)
      localIndex += getParameter(i).type().variableSize();
    return localIndex;
  }
  /**
   * @attribute syn
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:75
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int localIndexOfEnclosingVariable(Variable v) {
      ASTNode$State state = state();
    int localIndexOfEnclosingVariable_Variable_value = localIndexOfEnclosingVariable_compute(v);
    return localIndexOfEnclosingVariable_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private int localIndexOfEnclosingVariable_compute(Variable v) {
    int localIndex  = offsetFirstEnclosingVariable();
    Iterator iter = hostType().enclosingVariables().iterator();
    Variable varDecl = (Variable)iter.next();
    while(varDecl != v && iter.hasNext()) {
      localIndex += varDecl.type().variableSize();
      varDecl = (Variable)iter.next();
    }
    return localIndex;
  }
  protected java.util.Map handlesException_TypeDecl_values;
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:36
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if(handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new java.util.HashMap(4);
    if(handlesException_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)handlesException_TypeDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);
if(isFinal && num == state().boundariesCrossed) handlesException_TypeDecl_values.put(_parameters, Boolean.valueOf(handlesException_TypeDecl_value));
    return handlesException_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:267
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl unknownType() {
      ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:298
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getBlockNoTransform()) {
      return hasConstructorInvocation() ? getConstructorInvocation().isDAafter(v) : isDAbefore(v);
    }
    return getParent().Define_boolean_isDAbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:754
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getBlockNoTransform()) {
      return hasConstructorInvocation() ? getConstructorInvocation().isDUafter(v) : isDUbefore(v);
    }
    return getParent().Define_boolean_isDUbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:142
   * @apilevel internal
   */
  public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if(caller == getConstructorInvocationOptNoTransform()) {
      return throwsException(exceptionType) || handlesException(exceptionType);
    }
    if(caller == getBlockNoTransform()) {
      return throwsException(exceptionType) || handlesException(exceptionType);
    }
    return getParent().Define_boolean_handlesException(this, caller, exceptionType);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:45
   * @apilevel internal
   */
  public Collection Define_Collection_lookupMethod(ASTNode caller, ASTNode child, String name) {
    if(caller == getConstructorInvocationOptNoTransform()){
    Collection c = new ArrayList();
    for(Iterator iter = lookupMethod(name).iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl)iter.next();
      if(!hostType().memberMethods(name).contains(m) || m.isStatic())
        c.add(m);
    }
    return c;
  }
    return getParent().Define_Collection_lookupMethod(this, caller, name);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:64
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return parameterDeclaration(name);
    }
    if(caller == getConstructorInvocationOptNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    if(!set.isEmpty()) return set;
    for(Iterator iter = lookupVariable(name).iterator(); iter.hasNext(); ) {
      Variable v = (Variable)iter.next();
      if(!hostType().memberFields(name).contains(v) || v.isStatic())
        set = set.add(v);
    }
    return set;
  }
    if(caller == getBlockNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    if(!set.isEmpty()) return set;
    return lookupVariable(name);
  }
    return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:280
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePublic(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBePublic(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:281
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeProtected(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeProtected(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:282
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePrivate(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBePrivate(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:242
   * @apilevel internal
   */
  public ASTNode Define_ASTNode_enclosingBlock(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return this;
    }
    return getParent().Define_ASTNode_enclosingBlock(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:117
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getConstructorInvocationOptNoTransform()) {
      return NameType.EXPRESSION_NAME;
    }
    if(caller == getExceptionListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    return getParent().Define_NameType_nameType(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:517
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_enclosingInstance(ASTNode caller, ASTNode child) {
    if(caller == getConstructorInvocationOptNoTransform()) {
      return unknownType();
    }
    return getParent().Define_TypeDecl_enclosingInstance(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:132
   * @apilevel internal
   */
  public boolean Define_boolean_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
    if(caller == getConstructorInvocationOptNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_inExplicitConstructorInvocation(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:144
   * @apilevel internal
   */
  public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
    if(caller == getConstructorInvocationOptNoTransform()) {
      return false;
    }
    if(caller == getBlockNoTransform()) {
      return false;
    }
    return getParent().Define_boolean_inStaticContext(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:32
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return !hasConstructorInvocation() ? true : getConstructorInvocation().canCompleteNormally();
    }
    if(caller == getConstructorInvocationOptNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_reachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:58
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    return getParent().Define_boolean_isMethodParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:59
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    return getParent().Define_boolean_isConstructorParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:60
   * @apilevel internal
   */
  public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:90
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return getNumParameter() == 0 ? 
    localNumOfFirstParameter() :
    getParameter(getNumParameter()-1).localNum() + getParameter(getNumParameter()-1).type().variableSize();
    }
    if(caller == getParameterListNoTransform()) {
      int index = caller.getIndexOfChild(child);
      return index == 0 ?
    localNumOfFirstParameter() :
    getParameter(index-1).localNum() + getParameter(index-1).type().variableSize();
    }
    return getParent().Define_int_localNum(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    // Declared in /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag at line 238
    if(!hasConstructorInvocation() && !hostType().isObject()) {
      state().duringLookupConstructor++;
      ASTNode result = rewriteRule0();
      state().duringLookupConstructor--;
      return result;
    }

    return super.rewriteTo();
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:238
   * @apilevel internal
   */  private ConstructorDecl rewriteRule0() {
{
      setConstructorInvocation(
        new ExprStmt(
          new SuperConstructorAccess("super", new List())
          )
        );
      return this;
    }  }
}
