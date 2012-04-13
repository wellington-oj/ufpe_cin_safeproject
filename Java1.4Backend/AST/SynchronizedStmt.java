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
 * @declaredat java.ast:214
 */
public class SynchronizedStmt extends Stmt implements Cloneable, FinallyHost {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    canCompleteNormally_computed = false;
    label_begin_computed = false;
    label_end_computed = false;
    label_finally_computed = false;
    label_finally_block_computed = false;
    label_exception_handler_computed = false;
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
  public SynchronizedStmt clone() throws CloneNotSupportedException {
    SynchronizedStmt node = (SynchronizedStmt)super.clone();
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.canCompleteNormally_computed = false;
    node.label_begin_computed = false;
    node.label_end_computed = false;
    node.label_finally_computed = false;
    node.label_finally_block_computed = false;
    node.label_exception_handler_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SynchronizedStmt copy() {
      try {
        SynchronizedStmt node = (SynchronizedStmt)clone();
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
  public SynchronizedStmt fullCopy() {
    SynchronizedStmt res = (SynchronizedStmt)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:207
   */
  public void collectFinally(Stmt branchStmt, ArrayList list) {
    list.add(this);
    super.collectFinally(branchStmt, list);
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:699
   */
  public void toString(StringBuffer s) {
    s.append(indent());
    s.append("synchronized(");
    getExpr().toString(s);
    s.append(") ");
    getBlock().toString(s);
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:362
   */
  public void typeCheck() {
    TypeDecl type = getExpr().type();
    if(!type.isReferenceType() || type.isNull())
      error("*** The type of the expression must be a reference");
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:817
   */
  public void emitMonitorEnter(CodeGeneration gen) {
    gen.emitDup();
    int num = localNum();
    gen.emitStoreReference(num);
    gen.emit(Bytecode.MONITORENTER);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:823
   */
  public void emitExceptionHandler(CodeGeneration gen) {
    // add 1 to stack depth
    gen.changeStackDepth(1);
    int num = localNum() + 1;
    gen.emitStoreReference(num);
    gen.emitJsr(label_finally_block());
    gen.emitLoadReference(num);
    gen.emit(Bytecode.ATHROW);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:832
   */
  public void emitFinallyBlock(CodeGeneration gen) {
    // add 1 to stack depth
    gen.changeStackDepth(1);
    int num = localNum() + 2;
    gen.emitStoreReference(num);
    gen.emitLoadReference(localNum()); // monitor
    gen.emit(Bytecode.MONITOREXIT);
    gen.emit(Bytecode.RET).add(num);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1537
   */
  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    getExpr().createBCode(gen);
    emitMonitorEnter(gen);
    gen.addLabel(label_begin());
    getBlock().createBCode(gen);
    gen.addLabel(label_finally());
    if(getBlock().canCompleteNormally()) {
      gen.emitJsr(label_finally_block());
      gen.emitGoto(label_end());
    }
    gen.addLabel(label_exception_handler());
    emitExceptionHandler(gen);
    gen.addLabel(label_finally_block());
    emitFinallyBlock(gen);
    gen.addLabel(label_end());
    gen.createExceptionTable(this);
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public SynchronizedStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public SynchronizedStmt(Expr p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:14
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:20
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * Setter for Expr
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Getter for Expr
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Expr getExpr() {
    return (Expr)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Expr getExprNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
  /**
   * Setter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Getter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Block getBlock() {
    return (Block)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Block getBlockNoTransform() {
    return (Block)getChildNoTransform(1);
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:654
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
  private boolean isDAafter_compute(Variable v) {  return getBlock().isDAafter(v);  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:914
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafterFinally(Variable v) {
      ASTNode$State state = state();
    boolean isDUafterFinally_Variable_value = isDUafterFinally_compute(v);
    return isDUafterFinally_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafterFinally_compute(Variable v) {  return true;  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:917
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafterFinally(Variable v) {
      ASTNode$State state = state();
    boolean isDAafterFinally_Variable_value = isDAafterFinally_compute(v);
    return isDAafterFinally_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafterFinally_compute(Variable v) {  return false;  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1177
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
  private boolean isDUafter_compute(Variable v) {  return getBlock().isDUafter(v);  }
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_value;
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:110
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean canCompleteNormally() {
    if(canCompleteNormally_computed) {
      return canCompleteNormally_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    canCompleteNormally_value = canCompleteNormally_compute();
if(isFinal && num == state().boundariesCrossed) canCompleteNormally_computed = true;
    return canCompleteNormally_value;
  }
  /**
   * @apilevel internal
   */
  private boolean canCompleteNormally_compute() {  return getBlock().canCompleteNormally();  }
  /**
   * @apilevel internal
   */
  protected boolean label_begin_computed = false;
  /**
   * @apilevel internal
   */
  protected int label_begin_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1531
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int label_begin() {
    if(label_begin_computed) {
      return label_begin_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    label_begin_value = label_begin_compute();
if(isFinal && num == state().boundariesCrossed) label_begin_computed = true;
    return label_begin_value;
  }
  /**
   * @apilevel internal
   */
  private int label_begin_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean label_end_computed = false;
  /**
   * @apilevel internal
   */
  protected int label_end_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1532
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int label_end() {
    if(label_end_computed) {
      return label_end_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    label_end_value = label_end_compute();
if(isFinal && num == state().boundariesCrossed) label_end_computed = true;
    return label_end_value;
  }
  /**
   * @apilevel internal
   */
  private int label_end_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean label_finally_computed = false;
  /**
   * @apilevel internal
   */
  protected int label_finally_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1533
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int label_finally() {
    if(label_finally_computed) {
      return label_finally_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    label_finally_value = label_finally_compute();
if(isFinal && num == state().boundariesCrossed) label_finally_computed = true;
    return label_finally_value;
  }
  /**
   * @apilevel internal
   */
  private int label_finally_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean label_finally_block_computed = false;
  /**
   * @apilevel internal
   */
  protected int label_finally_block_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1534
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int label_finally_block() {
    if(label_finally_block_computed) {
      return label_finally_block_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    label_finally_block_value = label_finally_block_compute();
if(isFinal && num == state().boundariesCrossed) label_finally_block_computed = true;
    return label_finally_block_value;
  }
  /**
   * @apilevel internal
   */
  private int label_finally_block_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean label_exception_handler_computed = false;
  /**
   * @apilevel internal
   */
  protected int label_exception_handler_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1535
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int label_exception_handler() {
    if(label_exception_handler_computed) {
      return label_exception_handler_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    label_exception_handler_value = label_exception_handler_compute();
if(isFinal && num == state().boundariesCrossed) label_exception_handler_computed = true;
    return label_exception_handler_value;
  }
  /**
   * @apilevel internal
   */
  private int label_exception_handler_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:656
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getBlockNoTransform()) {
      return getExpr().isDAafter(v);
    }
    if(caller == getExprNoTransform()) {
      return isDAbefore(v);
    }
    return getParent().Define_boolean_isDAbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1179
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getBlockNoTransform()) {
      return getExpr().isDUafter(v);
    }
    if(caller == getExprNoTransform()) {
      return isDUbefore(v);
    }
    return getParent().Define_boolean_isDUbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:111
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return reachable();
    }
    return getParent().Define_boolean_reachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:157
   * @apilevel internal
   */
  public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return reachable();
    }
    return getParent().Define_boolean_reportUnreachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:122
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return localNum() + 3;
    }
    return getParent().Define_int_localNum(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
