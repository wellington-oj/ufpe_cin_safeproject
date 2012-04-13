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
 * @declaredat java.ast:11
 */
public abstract class Access extends Expr implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    prevExpr_computed = false;
    prevExpr_value = null;
    hasPrevExpr_computed = false;
    type_computed = false;
    type_value = null;
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
  public Access clone() throws CloneNotSupportedException {
    Access node = (Access)super.clone();
    node.prevExpr_computed = false;
    node.prevExpr_value = null;
    node.hasPrevExpr_computed = false;
    node.type_computed = false;
    node.type_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:148
   */
  public Access addArrayDims(List list) {
    Access a = this;
    for(int i = 0; i < list.getNumChildNoTransform(); i++) {
      Dims dims = (Dims)list.getChildNoTransform(i);
      Opt opt = dims.getExprOpt();
      if(opt.getNumChildNoTransform() == 1)
        a = new ArrayTypeWithSizeAccess(a, (Expr)opt.getChildNoTransform(0));
      else
        a = new ArrayTypeAccess(a);
      a.setStart(dims.start());
      a.setEnd(dims.end());
    }
    return a;
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:477
   */
  public void emitLoadLocalInNestedClass(CodeGeneration gen, Variable v) {
    if(inExplicitConstructorInvocation() && enclosingBodyDecl() instanceof ConstructorDecl) {
      ConstructorDecl c = (ConstructorDecl)enclosingBodyDecl();
      v.type().emitLoadLocal(gen, c.localIndexOfEnclosingVariable(v));
    }
    else {
      String classname = hostType().constantPoolName();
      String      desc = v.type().typeDescriptor();
      String      name = "val$" + v.name();
      int index = gen.constantPool().addFieldref(classname, name, desc);
      gen.emit(Bytecode.ALOAD_0);
      gen.emit(Bytecode.GETFIELD, v.type().variableSize() - 1).add2(index);
    }
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:577
   */
  public void emitThis(CodeGeneration gen, TypeDecl targetDecl) {
    if(targetDecl == hostType())
      gen.emit(Bytecode.ALOAD_0);
    else {
      TypeDecl enclosing = hostType();
      if(inExplicitConstructorInvocation()) {
        gen.emit(Bytecode.ALOAD_1);
        enclosing = enclosing.enclosing();
      }
      else {
        gen.emit(Bytecode.ALOAD_0);
      }
      while(enclosing != targetDecl) {
        String classname = enclosing.constantPoolName();
        enclosing = enclosing.enclosingType();
        String desc = enclosing.typeDescriptor();
        int index = gen.constantPool().addFieldref(classname, "this$0", desc);
        gen.emit(Bytecode.GETFIELD, 0).add2(index);
      }
    }
  }
  /**
   * @ast method 
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:103
   */
  protected TypeDecl superConstructorQualifier(TypeDecl targetEnclosingType) {
    TypeDecl enclosing = hostType();
    while(!enclosing.instanceOf(targetEnclosingType))
      enclosing = enclosing.enclosingType();
    return enclosing;
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public Access() {
    super();


  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:10
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:16
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:17
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr unqualifiedScope() {
      ASTNode$State state = state();
    Expr unqualifiedScope_value = unqualifiedScope_compute();
    return unqualifiedScope_value;
  }
  /**
   * @apilevel internal
   */
  private Expr unqualifiedScope_compute() {  return isQualified() ? nestedScope() : this;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:58
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isQualified() {
      ASTNode$State state = state();
    boolean isQualified_value = isQualified_compute();
    return isQualified_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isQualified_compute() {  return hasPrevExpr();  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:61
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr qualifier() {
      ASTNode$State state = state();
    Expr qualifier_value = qualifier_compute();
    return qualifier_value;
  }
  /**
   * @apilevel internal
   */
  private Expr qualifier_compute() {  return prevExpr();  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:66
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Access lastAccess() {
      ASTNode$State state = state();
    Access lastAccess_value = lastAccess_compute();
    return lastAccess_value;
  }
  /**
   * @apilevel internal
   */
  private Access lastAccess_compute() {  return this;  }
  /**
   * @apilevel internal
   */
  protected boolean prevExpr_computed = false;
  /**
   * @apilevel internal
   */
  protected Expr prevExpr_value;
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:78
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr prevExpr() {
    if(prevExpr_computed) {
      return prevExpr_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    prevExpr_value = prevExpr_compute();
if(isFinal && num == state().boundariesCrossed) prevExpr_computed = true;
    return prevExpr_value;
  }
  /**
   * @apilevel internal
   */
  private Expr prevExpr_compute() {
    if(isLeftChildOfDot()) {
      if(parentDot().isRightChildOfDot())
        return parentDot().parentDot().leftSide();
    }
    else if(isRightChildOfDot())
      return parentDot().leftSide();
    throw new Error(this + " does not have a previous expression");
  }
  /**
   * @apilevel internal
   */
  protected boolean hasPrevExpr_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean hasPrevExpr_value;
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:89
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasPrevExpr() {
    if(hasPrevExpr_computed) {
      return hasPrevExpr_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    hasPrevExpr_value = hasPrevExpr_compute();
if(isFinal && num == state().boundariesCrossed) hasPrevExpr_computed = true;
    return hasPrevExpr_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasPrevExpr_compute() {
    if(isLeftChildOfDot()) {
      if(parentDot().isRightChildOfDot())
        return true;
    }
    else if(isRightChildOfDot())
      return true;
    return false;
  }
  /**
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:56
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
  private NameType predNameType_compute() {  return NameType.NO_NAME;  }
  /**
   * @apilevel internal
   */
  protected boolean type_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl type_value;
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:278
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl type() {
    if(type_computed) {
      return type_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    type_value = type_compute();
if(isFinal && num == state().boundariesCrossed) type_computed = true;
    return type_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl type_compute() {  return unknownType();  }
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:28
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int sourceLineNumber() {
      ASTNode$State state = state();
    int sourceLineNumber_value = sourceLineNumber_compute();
    return sourceLineNumber_value;
  }
  /**
   * @apilevel internal
   */
  private int sourceLineNumber_compute() {  return findFirstSourceLineNumber();  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:18
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr nestedScope() {
      ASTNode$State state = state();
    Expr nestedScope_value = getParent().Define_Expr_nestedScope(this, null);
    return nestedScope_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:133
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl unknownType() {
      ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:230
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Variable unknownField() {
      ASTNode$State state = state();
    Variable unknownField_value = getParent().Define_Variable_unknownField(this, null);
    return unknownField_value;
  }
  /**
   * @attribute inh
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:476
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean inExplicitConstructorInvocation() {
      ASTNode$State state = state();
    boolean inExplicitConstructorInvocation_value = getParent().Define_boolean_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
