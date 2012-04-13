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
 * @declaredat java.ast:133
 */
public abstract class Unary extends Expr implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
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
  public Unary clone() throws CloneNotSupportedException {
    Unary node = (Unary)super.clone();
    node.type_computed = false;
    node.type_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:366
   */
  public void toString(StringBuffer s) {
    s.append(printPreOp());
    getOperand().toString(s);
    s.append(printPostOp());
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:833
   */
  public void createBCode(CodeGeneration gen) {   
    super.createBCode(gen);
    emitOperation(gen);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:845
   */
  public void emitPostfix(CodeGeneration gen, int constant) {
    Expr operand = getOperand();
    while(operand instanceof ParExpr)
      operand = ((ParExpr)operand).getExpr();
    Access access = ((Access)operand).lastAccess();
    access.createAssignLoadDest(gen);
    if(needsPush())
      access.createPushAssignmentResult(gen);
    TypeDecl type = access.type().binaryNumericPromotion(typeInt());
    type.emitPushConstant(gen, constant);
    type.add(gen);
    type.emitCastTo(gen, access.type());
    access.emitStore(gen);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:862
   */
  public void emitPrefix(CodeGeneration gen, int constant) {
    Expr operand = getOperand();
    while(operand instanceof ParExpr)
      operand = ((ParExpr)operand).getExpr();
    Access access = ((Access)operand).lastAccess();
    access.createAssignLoadDest(gen);
    TypeDecl type = access.type().binaryNumericPromotion(typeInt());
    type.emitPushConstant(gen, constant);
    type.add(gen);
    type.emitCastTo(gen, access.type());
    if(needsPush())
      access.createPushAssignmentResult(gen);
    access.emitStore(gen);
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public Unary() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public Unary(Expr p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:13
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * Setter for Operand
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Getter for Operand
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Expr getOperand() {
    return (Expr)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Expr getOperandNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:398
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
  private boolean isDAafter_compute(Variable v) {  return getOperand().isDAafter(v);  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:841
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
  private boolean isDUafter_compute(Variable v) {  return getOperand().isDUafter(v);  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:372
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String printPostOp() {
      ASTNode$State state = state();
    String printPostOp_value = printPostOp_compute();
    return printPostOp_value;
  }
  /**
   * @apilevel internal
   */
  private String printPostOp_compute() {  return "";  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:376
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String printPreOp() {
      ASTNode$State state = state();
    String printPreOp_value = printPreOp_compute();
    return printPreOp_value;
  }
  /**
   * @apilevel internal
   */
  private String printPreOp_compute() {  return "";  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:314
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
  private TypeDecl type_compute() {  return getOperand().type();  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
    if(caller == getOperandNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_isSource(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
