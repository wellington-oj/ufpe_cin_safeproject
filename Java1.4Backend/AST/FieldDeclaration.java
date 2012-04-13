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
 * @declaredat java.ast:77
 */
public class FieldDeclaration extends MemberDecl implements Cloneable, SimpleSet, Iterator, Variable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    accessibleFrom_TypeDecl_values = null;
    exceptions_computed = false;
    exceptions_value = null;
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    constant_computed = false;
    constant_value = null;
    attributes_computed = false;
    attributes_value = null;
    flags_computed = false;
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
  public FieldDeclaration clone() throws CloneNotSupportedException {
    FieldDeclaration node = (FieldDeclaration)super.clone();
    node.accessibleFrom_TypeDecl_values = null;
    node.exceptions_computed = false;
    node.exceptions_value = null;
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.constant_computed = false;
    node.constant_value = null;
    node.attributes_computed = false;
    node.attributes_value = null;
    node.flags_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public FieldDeclaration copy() {
      try {
        FieldDeclaration node = (FieldDeclaration)clone();
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
  public FieldDeclaration fullCopy() {
    FieldDeclaration res = (FieldDeclaration)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect BoundNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BoundNames.jrag:11
   */
  public Access createQualifiedBoundAccess() {
    if(isStatic())
      return hostType().createQualifiedAccess().qualifiesAccess(new BoundFieldAccess(this));
    else
      return new ThisAccess("this").qualifiesAccess(
        new BoundFieldAccess(this));
  }
  /**
   * @ast method 
   * @aspect BoundNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BoundNames.jrag:86
   */
  public Access createBoundFieldAccess() {
    return createQualifiedBoundAccess();
  }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:81
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:85
   */
  public boolean isSingleton() { return true; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:86
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:89
   */
  
  private FieldDeclaration iterElem;
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:90
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:91
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:92
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:93
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * @ast method 
   * @aspect DefiniteAssignment
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:177
   */
  public void definiteAssignment() {
    super.definiteAssignment();
    if(isBlank() && isFinal() && isClassVariable()) {
      boolean found = false;
      TypeDecl typeDecl = hostType();
      for(int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
        if(typeDecl.getBodyDecl(i) instanceof StaticInitializer) {
          StaticInitializer s = (StaticInitializer)typeDecl.getBodyDecl(i);
          if(s.isDAafter(this))
            found = true;
        }
        
        else if(typeDecl.getBodyDecl(i) instanceof FieldDeclaration) {
          FieldDeclaration f = (FieldDeclaration)typeDecl.getBodyDecl(i);
          if(f.isStatic() && f.isDAafter(this))
            found = true;
        }
        
      }
      if(!found)
        error("blank final class variable " + name() + " in " + hostType().typeName() + " is not definitely assigned in static initializer");

    }
    if(isBlank() && isFinal() && isInstanceVariable()) {
      TypeDecl typeDecl = hostType();
      boolean found = false;
      for(int i = 0; !found && i < typeDecl.getNumBodyDecl(); i++) {
        if(typeDecl.getBodyDecl(i) instanceof FieldDeclaration) {
          FieldDeclaration f = (FieldDeclaration)typeDecl.getBodyDecl(i);
          if(!f.isStatic() && f.isDAafter(this))
            found = true;
        }
        else if(typeDecl.getBodyDecl(i) instanceof InstanceInitializer) {
          InstanceInitializer ii = (InstanceInitializer)typeDecl.getBodyDecl(i);
          if(ii.getBlock().isDAafter(this))
            found = true;
        }
      }
      for(Iterator iter = typeDecl.constructors().iterator(); !found && iter.hasNext(); ) {
        ConstructorDecl c = (ConstructorDecl)iter.next();
        if(!c.isDAafter(this)) {
          error("blank final instance variable " + name() + " in " + hostType().typeName() + " is not definitely assigned after " + c.signature());
          }
      }
    }
    if(isBlank() && hostType().isInterfaceDecl()) {
            error("variable  " + name() + " in " + hostType().typeName() + " which is an interface must have an initializer");
    }

  }
  /**
   * @ast method 
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:112
   */
  public void checkModifiers() {
    super.checkModifiers();
    if(hostType().isInterfaceDecl()) {
      if(isProtected())
        error("an interface field may not be protected");
      if(isPrivate())
        error("an interface field may not be private");
      if(isTransient())
        error("an interface field may not be transient");
      if(isVolatile())
        error("an interface field may not be volatile");
    }
  }
  /**
   * @ast method 
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:277
   */
  public void nameCheck() {
    super.nameCheck();
    // 8.3
    for(Iterator iter = hostType().memberFields(name()).iterator(); iter.hasNext(); ) {
      Variable v = (Variable)iter.next();
      if(v != this && v.hostType() == hostType())
        error("field named " + name() + " is multiply declared in type " + hostType().typeName());
    }

  }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:86
   */
  public FieldDeclaration(Modifiers m, Access type, String name) {
    this(m, type, name, new Opt());
  }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:90
   */
  public FieldDeclaration(Modifiers m, Access type, String name, Expr init) {
    this(m, type, name, new Opt(init));
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:152
   */
  public void toString(StringBuffer s) {
    s.append(indent());
    getModifiers().toString(s);
    getTypeAccess().toString(s);
    s.append(" " + name());
    if(hasInit()) {
      s.append(" = ");
      getInit().toString(s);
    }
    s.append(";");
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:33
   */
  public void typeCheck() {
    if(hasInit()) {
      TypeDecl source = getInit().type();
      TypeDecl dest = type();
      if(!source.assignConversionTo(dest, getInit()))
        error("can not assign " + name() + " of type " + dest.typeName() +
              " a value of type " + source.typeName());
    }
  }
  /**
   * @ast method 
   * @aspect VariableDeclarationTransformation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:91
   */
  
  // when splitting a FieldDecl into multiple FieldDeclarations, provide every FieldDeclaration with a reference
  // to the original FieldDecl; if only a single FieldDeclaration results, no reference is stored
  private FieldDecl fieldDecl = null;
  /**
   * @ast method 
   * @aspect VariableDeclarationTransformation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:92
   */
  public FieldDecl getFieldDecl() {
	  return fieldDecl;
  }
  /**
   * @ast method 
   * @aspect VariableDeclarationTransformation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:95
   */
  public void setFieldDecl(FieldDecl fieldDecl) {
	  this.fieldDecl = fieldDecl;
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:678
   */
  public void emitLoadField(CodeGeneration gen, TypeDecl typeDecl) {
    if(hostType().isArrayDecl() && name().equals("length")) {
      gen.emit(Bytecode.ARRAYLENGTH);
      return;
    }
    String classname = typeDecl.constantPoolName();
    String      desc = type().typeDescriptor();
    String      name = name();
    int index = gen.constantPool().addFieldref(classname, name, desc);
    if(isStatic())
      gen.emit(Bytecode.GETSTATIC, type().variableSize()).add2(index);
    else
      gen.emit(Bytecode.GETFIELD, type().variableSize() - 1).add2(index);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:736
   */
  public void emitStoreField(CodeGeneration gen, TypeDecl typeDecl) {
    String classname = typeDecl.constantPoolName();
    String      desc = type().typeDescriptor();
    String      name = name();
    int index = gen.constantPool().addFieldref(classname, name, desc);
    if(isStatic())
      gen.emit(Bytecode.PUTSTATIC, -type().variableSize()).add2(index);
    else
      gen.emit(Bytecode.PUTFIELD, -type().variableSize() - 1).add2(index);
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:372
   */
  public boolean clear() {
    return false;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:278
   */
  public MethodDecl createAccessor(TypeDecl fieldQualifier) {
    MethodDecl m = (MethodDecl)fieldQualifier.getAccessor(this, "field_read");
    if(m != null) return m;
    
    int accessorIndex = fieldQualifier.accessorCounter++;
    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("static"));
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = new List();
    if(!isStatic())
      parameters.add(new ParameterDeclaration(fieldQualifier.createQualifiedAccess(), "that"));

    m = new MethodDecl(
      modifiers,
      type().createQualifiedAccess(),
      "get$" + name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(
            new ReturnStmt(createAccess())
          )
        )
      )
    );
    m = fieldQualifier.addMemberMethod(m);
    fieldQualifier.addAccessor(this, "field_read", m);
    return m;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:311
   */
  public MethodDecl createAccessorWrite(TypeDecl fieldQualifier) {
    MethodDecl m = (MethodDecl)fieldQualifier.getAccessor(this, "field_write");
    if(m != null) return m;

    int accessorIndex = fieldQualifier.accessorCounter++;
    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("static"));
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = new List();
    if(!isStatic())
      parameters.add(new ParameterDeclaration(fieldQualifier.createQualifiedAccess(), "that"));
    parameters.add(new ParameterDeclaration(type().createQualifiedAccess(), "value"));

    m = new MethodDecl(
      modifiers,
      type().createQualifiedAccess(),
      "set$" + name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(
            new ExprStmt(
              new AssignSimpleExpr(
                createAccess(),
                new VarAccess("value")
              )
            )
          ).add(
            new ReturnStmt(
              new Opt(
                new VarAccess("value")
              )
            )
          )
        )
      )
    );
    m = fieldQualifier.addMemberMethod(m);
    fieldQualifier.addAccessor(this, "field_write", m);
    return m;
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:356
   */
  private Access createAccess() {
    Access fieldAccess = new BoundFieldAccess(this);
    return isStatic() ? fieldAccess : new VarAccess("that").qualifiesAccess(fieldAccess);
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public FieldDeclaration() {
    super();

    setChild(new Opt(), 2);

  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public FieldDeclaration(Modifiers p0, Access p1, String p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
  /**
   * @ast method 
   * @declaredat java.ast:14
   */
  public FieldDeclaration(Modifiers p0, Access p1, beaver.Symbol p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:23
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:29
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
   * Setter for TypeAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
  /**
   * Getter for TypeAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Access getTypeAccess() {
    return (Access)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Access getTypeAccessNoTransform() {
    return (Access)getChildNoTransform(1);
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
   * Setter for InitOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setInitOpt(Opt<Expr> opt) {
    setChild(opt, 2);
  }
  /**
   * Does this node have a Init child?
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public boolean hasInit() {
    return getInitOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child Init
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr getInit() {
    return (Expr)getInitOpt().getChild(0);
  }
  /**
   * Setter for optional child Init
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void setInit(Expr node) {
    getInitOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getInitOpt() {
    return (Opt<Expr>)getChild(2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getInitOptNoTransform() {
    return (Opt<Expr>)getChildNoTransform(2);
  }
  protected java.util.Map accessibleFrom_TypeDecl_values;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AccessControl.jrag:109
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
    if(isPublic())
      return true;
    else if(isProtected()) {
      if(hostPackage().equals(type.hostPackage()))
        return true;
      if(type.withinBodyThatSubclasses(hostType()) != null)
        return true;
      return false;
    }
    else if(isPrivate())
      return hostType().topLevelType() == type.topLevelType();
    else
      return hostPackage().equals(type.hostPackage());
  }
  /**
   * @apilevel internal
   */
  protected boolean exceptions_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection exceptions_value;
  /**
   * @attribute syn
   * @aspect AnonymousClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/AnonymousClasses.jrag:168
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection exceptions() {
    if(exceptions_computed) {
      return exceptions_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    exceptions_value = exceptions_compute();
if(isFinal && num == state().boundariesCrossed) exceptions_computed = true;
    return exceptions_value;
  }
  /**
   * @apilevel internal
   */
  private Collection exceptions_compute() {
    HashSet set = new HashSet();
    if(isInstanceVariable() && hasInit()) {
      collectExceptions(set, this);
      for(Iterator iter = set.iterator(); iter.hasNext(); ) {
        TypeDecl typeDecl = (TypeDecl)iter.next();
        if(!getInit().reachedException(typeDecl))
          iter.remove();
      }
    }
    return set;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:479
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isConstant() {
      ASTNode$State state = state();
    boolean isConstant_value = isConstant_compute();
    return isConstant_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isConstant_compute() {  return isFinal() && hasInit() && getInit().isConstant() && (type() instanceof PrimitiveType || type().isString());  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:79
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int size() {
      ASTNode$State state = state();
    int size_value = size_compute();
    return size_value;
  }
  /**
   * @apilevel internal
   */
  private int size_compute() {  return 1;  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:80
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isEmpty() {
      ASTNode$State state = state();
    boolean isEmpty_value = isEmpty_compute();
    return isEmpty_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isEmpty_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:84
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean contains(Object o) {
      ASTNode$State state = state();
    boolean contains_Object_value = contains_compute(o);
    return contains_Object_value;
  }
  /**
   * @apilevel internal
   */
  private boolean contains_compute(Object o) {  return this == o;  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:314
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
  private boolean isDAafter_compute(Variable v) {
    if(v == this)
      return hasInit();
    return hasInit() ? getInit().isDAafter(v) : isDAbefore(v);
  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:770
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
  private boolean isDUafter_compute(Variable v) {
    if(v == this)
      return !hasInit();
    return hasInit() ? getInit().isDUafter(v) : isDUbefore(v);
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:214
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:237
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
  private boolean isPublic_compute() {  return getModifiers().isPublic() || hostType().isInterfaceDecl();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:238
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:239
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
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:240
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isStatic() {
      ASTNode$State state = state();
    boolean isStatic_value = isStatic_compute();
    return isStatic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isStatic_compute() {  return getModifiers().isStatic() || hostType().isInterfaceDecl();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:242
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFinal() {
      ASTNode$State state = state();
    boolean isFinal_value = isFinal_compute();
    return isFinal_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFinal_compute() {  return getModifiers().isFinal() || hostType().isInterfaceDecl();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:243
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isTransient() {
      ASTNode$State state = state();
    boolean isTransient_value = isTransient_compute();
    return isTransient_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isTransient_compute() {  return getModifiers().isTransient();  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:244
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVolatile() {
      ASTNode$State state = state();
    boolean isVolatile_value = isVolatile_compute();
    return isVolatile_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVolatile_compute() {  return getModifiers().isVolatile();  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:811
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String dumpString() {
      ASTNode$State state = state();
    String dumpString_value = dumpString_compute();
    return dumpString_value;
  }
  /**
   * @apilevel internal
   */
  private String dumpString_compute() {  return getClass().getName() + " [" + getID() + "]";  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:251
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
  private TypeDecl type_compute() {  return getTypeAccess().type();  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:273
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
  private boolean isVoid_compute() {  return type().isVoid();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:32
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isParameter() {
      ASTNode$State state = state();
    boolean isParameter_value = isParameter_compute();
    return isParameter_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isParameter_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:34
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isClassVariable() {
      ASTNode$State state = state();
    boolean isClassVariable_value = isClassVariable_compute();
    return isClassVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isClassVariable_compute() {  return isStatic() || hostType().isInterfaceDecl();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:35
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isInstanceVariable() {
      ASTNode$State state = state();
    boolean isInstanceVariable_value = isInstanceVariable_compute();
    return isInstanceVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isInstanceVariable_compute() {  return (hostType().isClassDecl() || hostType().isAnonymous() )&& !isStatic();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:36
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isMethodParameter() {
      ASTNode$State state = state();
    boolean isMethodParameter_value = isMethodParameter_compute();
    return isMethodParameter_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isMethodParameter_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isConstructorParameter() {
      ASTNode$State state = state();
    boolean isConstructorParameter_value = isConstructorParameter_compute();
    return isConstructorParameter_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isConstructorParameter_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:38
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isExceptionHandlerParameter() {
      ASTNode$State state = state();
    boolean isExceptionHandlerParameter_value = isExceptionHandlerParameter_compute();
    return isExceptionHandlerParameter_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isExceptionHandlerParameter_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:39
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isLocalVariable() {
      ASTNode$State state = state();
    boolean isLocalVariable_value = isLocalVariable_compute();
    return isLocalVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isLocalVariable_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:41
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBlank() {
      ASTNode$State state = state();
    boolean isBlank_value = isBlank_compute();
    return isBlank_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBlank_compute() {  return !hasInit();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:43
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String name() {
      ASTNode$State state = state();
    String name_value = name_compute();
    return name_value;
  }
  /**
   * @apilevel internal
   */
  private String name_compute() {  return getID();  }
  /**
   * @apilevel internal
   */
  protected boolean constant_computed = false;
  /**
   * @apilevel internal
   */
  protected Constant constant_value;
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant constant() {
    if(constant_computed) {
      return constant_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    constant_value = constant_compute();
if(isFinal && num == state().boundariesCrossed) constant_computed = true;
    return constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant constant_compute() {  return type().cast(getInit().constant());  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Attributes.jrag:183
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
    if(isStatic() && isFinal() && isConstant() && (type().isPrimitive() || type().isString()))
      l.add(new ConstantValueAttribute(hostType().constantPool(), this));
    return l;
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:76
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
    if(isStatic()) res |= Modifiers.ACC_STATIC;
    if(isFinal()) res |= Modifiers.ACC_FINAL;
    if(isVolatile()) res |= Modifiers.ACC_VOLATILE;
    if(isTransient()) res |= Modifiers.ACC_TRANSIENT;
    return res;
  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:294
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBytecodeField() {
      ASTNode$State state = state();
    boolean isBytecodeField_value = isBytecodeField_compute();
    return isBytecodeField_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBytecodeField_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:332
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
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:34
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean handlesException(TypeDecl exceptionType) {
      ASTNode$State state = state();
    boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);
    return handlesException_TypeDecl_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:39
   * @apilevel internal
   */
  public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_isSource(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:320
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getInitOptNoTransform()){
    return isDAbefore(v);
  }
    return getParent().Define_boolean_isDAbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:152
   * @apilevel internal
   */
  public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if(caller == getInitOptNoTransform()){
    if(hostType().isAnonymous())
      return true;
    if(!exceptionType.isUncheckedException())
      return true;
    for(Iterator iter = hostType().constructors().iterator(); iter.hasNext(); ) {
      ConstructorDecl decl = (ConstructorDecl)iter.next();
      if(!decl.throwsException(exceptionType))
        return false;
    }
    return true;
  }
    return getParent().Define_boolean_handlesException(this, caller, exceptionType);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:260
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePublic(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBePublic(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:261
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeProtected(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeProtected(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:262
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePrivate(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBePrivate(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:263
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeStatic(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeStatic(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:264
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeFinal(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:265
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeTransient(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeTransient(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:266
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeVolatile(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeVolatile(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:78
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getTypeAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    return getParent().Define_NameType_nameType(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:260
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_declType(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return type();
    }
    return getParent().Define_TypeDecl_declType(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:141
   * @apilevel internal
   */
  public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return isStatic() || hostType().isInterfaceDecl();
    }
    return getParent().Define_boolean_inStaticContext(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:64
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_expectedType(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return type().componentType();
    }
    return getParent().Define_TypeDecl_expectedType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
