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
 * @declaredat java.ast:19
 */
public class SuperConstructorAccess extends ConstructorAccess implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    decls_computed = false;
    decls_value = null;
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
  public SuperConstructorAccess clone() throws CloneNotSupportedException {
    SuperConstructorAccess node = (SuperConstructorAccess)super.clone();
    node.decls_computed = false;
    node.decls_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SuperConstructorAccess copy() {
      try {
        SuperConstructorAccess node = (SuperConstructorAccess)clone();
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
  public SuperConstructorAccess fullCopy() {
    SuperConstructorAccess res = (SuperConstructorAccess)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect TypeHierarchyCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:63
   */
  public void nameCheck() {
    super.nameCheck();
    // 8.8.5.1
    TypeDecl c = hostType();
    TypeDecl s = c.isClassDecl() && ((ClassDecl)c).hasSuperclass() ? ((ClassDecl)c).superclass() : unknownType();
    if(isQualified()) {
      if(!s.isInnerType() || s.inStaticContext())
        error("the super type " + s.typeName() + " of " + c.typeName() +
           " is not an inner class");
    
      else if(!qualifier().type().instanceOf(s.enclosingType()))
        error("The type of this primary expression, " +
                qualifier().type().typeName() + " is not enclosing the super type, " + 
                s.typeName() + ", of " + c.typeName());
    }
    if(!isQualified() && s.isInnerType()) {
      if(!c.isInnerType()) {
        error("no enclosing instance for " + s.typeName() + " when accessed in " + this);
      }
    }
    if(s.isInnerType() && hostType().instanceOf(s.enclosingType()))
      error("cannot reference this before supertype constructor has been called");
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:625
   */
  public void createBCode(CodeGeneration gen) {
    ConstructorDecl c = decl();
    
    // this
    gen.emit(Bytecode.ALOAD_0);
    
    if(c.needsEnclosing()) {
      if(hasPrevExpr() && !prevExpr().isTypeAccess()) {
        prevExpr().createBCode(gen);
        gen.emitDup();
        int index = gen.constantPool().addMethodref("java/lang/Object", "getClass", "()Ljava/lang/Class;");
        gen.emit(Bytecode.INVOKEVIRTUAL, 0).add2(index);
        gen.emitPop();
      }
      else {
        if(hostType().needsSuperEnclosing()) {
          if(hostType().needsEnclosing())
            gen.emit(Bytecode.ALOAD_2);
          else
            gen.emit(Bytecode.ALOAD_1);
        }
        else {
          emitThis(gen, superConstructorQualifier(c.hostType().enclosingType()));
        }
      }
    }

    // args
    for (int i = 0; i < getNumArg(); ++i) {
      getArg(i).createBCode(gen);
      getArg(i).type().emitCastTo(gen, decl().getParameter(i).type()); // MethodInvocationConversion
    }
    if(decl().isPrivate() && decl().hostType() != hostType()) {
      gen.emit(Bytecode.ACONST_NULL);
      decl().createAccessor().emitInvokeConstructor(gen);
    }
    else {
      decl().emitInvokeConstructor(gen);
    }
  }
  /**
   * @ast method 
   * @aspect Transformations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Transformations.jrag:149
   */
  public void transformation() {
    // this$val
    addEnclosingVariables();
    // touch accessorIndex to force creation of private constructorAccessor
    if(decl().isPrivate() && decl().hostType() != hostType()) {
      decl().createAccessor();
    }
    super.transformation();
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public SuperConstructorAccess() {
    super();

    setChild(new List(), 0);

  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public SuperConstructorAccess(String p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /**
   * @ast method 
   * @declaredat java.ast:12
   */
  public SuperConstructorAccess(beaver.Symbol p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:19
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:25
   */
  public boolean mayHaveRewrite() {
    return false;
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
   * Setter for ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setArgList(List<Expr> list) {
    setChild(list, 0);
  }
  /**
   * @return number of children in ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public int getNumArg() {
    return getArgList().getNumChild();
  }
  /**
   * Getter for child in list ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr getArg(int i) {
    return (Expr)getArgList().getChild(i);
  }
  /**
   * Add element to list ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void addArg(Expr node) {
    List<Expr> list = (parent == null || state == null) ? getArgListNoTransform() : getArgList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:34
   */
  public void addArgNoTransform(Expr node) {
    List<Expr> list = getArgListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:42
   */
  public void setArg(Expr node, int i) {
    List<Expr> list = getArgList();
    list.setChild(node, i);
  }
  /**
   * Getter for Arg list.
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:50
   */
  public List<Expr> getArgs() {
    return getArgList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:56
   */
  public List<Expr> getArgsNoTransform() {
    return getArgListNoTransform();
  }
  /**
   * Getter for list ArgList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Expr> getArgList() {
    List<Expr> list = (List<Expr>)getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Expr> getArgListNoTransform() {
    return (List<Expr>)getChildNoTransform(0);
  }
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:297
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafter(Variable v) {
      ASTNode$State state = state();
    boolean isDAafter_Variable_value = isDAafter_compute(v);
    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {  return isDAbefore(v);  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:753
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafter(Variable v) {
      ASTNode$State state = state();
    boolean isDUafter_Variable_value = isDUafter_compute(v);
    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {  return isDUbefore(v);  }
  /**
   * @apilevel internal
   */
  protected boolean decls_computed = false;
  /**
   * @apilevel internal
   */
  protected SimpleSet decls_value;
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:59
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet decls() {
    if(decls_computed) {
      return decls_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    decls_value = decls_compute();
if(isFinal && num == state().boundariesCrossed) decls_computed = true;
    return decls_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet decls_compute() {
    Collection c = hasPrevExpr() && !prevExpr().isTypeAccess() ?
      hostType().lookupSuperConstructor() : lookupSuperConstructor();
    return mostSpecificConstructor(c);
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:20
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
  private String name_compute() {  return "super";  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:51
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSuperConstructorAccess() {
      ASTNode$State state = state();
    boolean isSuperConstructorAccess_value = isSuperConstructorAccess_compute();
    return isSuperConstructorAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSuperConstructorAccess_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:96
   */
  @SuppressWarnings({"unchecked", "cast"})
  public NameType predNameType() {
      ASTNode$State state = state();
    NameType predNameType_value = predNameType_compute();
    return predNameType_value;
  }
  /**
   * @apilevel internal
   */
  private NameType predNameType_compute() {  return NameType.EXPRESSION_NAME;  }
  /**
   * @attribute inh
   * @aspect ConstructScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection lookupSuperConstructor() {
      ASTNode$State state = state();
    Collection lookupSuperConstructor_value = getParent().Define_Collection_lookupSuperConstructor(this, null);
    return lookupSuperConstructor_value;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:503
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl enclosingInstance() {
      ASTNode$State state = state();
    TypeDecl enclosingInstance_value = getParent().Define_TypeDecl_enclosingInstance(this, null);
    return enclosingInstance_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:89
   * @apilevel internal
   */
  public boolean Define_boolean_hasPackage(ASTNode caller, ASTNode child, String packageName) {
    if(caller == getArgListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().hasPackage(packageName);
    }
    return super.Define_boolean_hasPackage(caller, child, packageName);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:134
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if(caller == getArgListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().lookupVariable(name);
    }
    return super.Define_SimpleSet_lookupVariable(caller, child, name);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:131
   * @apilevel internal
   */
  public boolean Define_boolean_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
    if(caller == getArgListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    return super.Define_boolean_inExplicitConstructorInvocation(caller, child);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
