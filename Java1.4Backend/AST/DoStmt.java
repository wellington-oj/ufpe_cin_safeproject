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
 * @declaredat java.ast:206
 */
public class DoStmt extends BranchTargetStmt implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    targetOf_ContinueStmt_values = null;
    targetOf_BreakStmt_values = null;
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    isDUbeforeCondition_Variable_values = null;
    canCompleteNormally_computed = false;
    begin_label_computed = false;
    cond_label_computed = false;
    end_label_computed = false;
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
  public DoStmt clone() throws CloneNotSupportedException {
    DoStmt node = (DoStmt)super.clone();
    node.targetOf_ContinueStmt_values = null;
    node.targetOf_BreakStmt_values = null;
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.isDUbeforeCondition_Variable_values = null;
    node.canCompleteNormally_computed = false;
    node.begin_label_computed = false;
    node.cond_label_computed = false;
    node.end_label_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public DoStmt copy() {
      try {
        DoStmt node = (DoStmt)clone();
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
  public DoStmt fullCopy() {
    DoStmt res = (DoStmt)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:595
   */
  public void toString(StringBuffer s) {
    s.append(indent());
    s.append("do ");
    getStmt().toString(s);
    s.append("while(");
    getCondition().toString(s);
    s.append(");");
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:328
   */
  public void typeCheck() {
    TypeDecl cond = getCondition().type();
    if(!cond.isBoolean()) {
      error("the type of \"" + getCondition() + "\" is " + cond.name() + " which is not boolean");
    }
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1361
   */
  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    gen.addLabel(begin_label());
    getStmt().createBCode(gen);
    gen.addLabel(cond_label());
    getCondition().emitEvalBranch(gen);
    gen.addLabel(end_label());
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public DoStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public DoStmt(Stmt p0, Expr p1) {
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
   * Setter for Stmt
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setStmt(Stmt node) {
    setChild(node, 0);
  }
  /**
   * Getter for Stmt
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Stmt getStmt() {
    return (Stmt)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Stmt getStmtNoTransform() {
    return (Stmt)getChildNoTransform(0);
  }
  /**
   * Setter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setCondition(Expr node) {
    setChild(node, 1);
  }
  /**
   * Getter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Expr getCondition() {
    return (Expr)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Expr getConditionNoTransform() {
    return (Expr)getChildNoTransform(1);
  }
  protected java.util.Map targetOf_ContinueStmt_values;
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:71
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean targetOf(ContinueStmt stmt) {
    Object _parameters = stmt;
    if(targetOf_ContinueStmt_values == null) targetOf_ContinueStmt_values = new java.util.HashMap(4);
    if(targetOf_ContinueStmt_values.containsKey(_parameters)) {
      return ((Boolean)targetOf_ContinueStmt_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean targetOf_ContinueStmt_value = targetOf_compute(stmt);
if(isFinal && num == state().boundariesCrossed) targetOf_ContinueStmt_values.put(_parameters, Boolean.valueOf(targetOf_ContinueStmt_value));
    return targetOf_ContinueStmt_value;
  }
  /**
   * @apilevel internal
   */
  private boolean targetOf_compute(ContinueStmt stmt) {  return !stmt.hasLabel();  }
  protected java.util.Map targetOf_BreakStmt_values;
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:79
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean targetOf(BreakStmt stmt) {
    Object _parameters = stmt;
    if(targetOf_BreakStmt_values == null) targetOf_BreakStmt_values = new java.util.HashMap(4);
    if(targetOf_BreakStmt_values.containsKey(_parameters)) {
      return ((Boolean)targetOf_BreakStmt_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean targetOf_BreakStmt_value = targetOf_compute(stmt);
if(isFinal && num == state().boundariesCrossed) targetOf_BreakStmt_values.put(_parameters, Boolean.valueOf(targetOf_BreakStmt_value));
    return targetOf_BreakStmt_value;
  }
  /**
   * @apilevel internal
   */
  private boolean targetOf_compute(BreakStmt stmt) {  return !stmt.hasLabel();  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:588
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
    if(!getCondition().isDAafterFalse(v))
      return false;
    for(Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
      BreakStmt stmt = (BreakStmt)iter.next();
      if(!stmt.isDAafterReachedFinallyBlocks(v))
        return false;
    }
    return true;
  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1067
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
    if(!isDUbeforeCondition(v)) // start a circular evaluation here
      return false;
    if(!getCondition().isDUafterFalse(v))
      return false;
    for(Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
      BreakStmt stmt = (BreakStmt)iter.next();
      if(!stmt.isDUafterReachedFinallyBlocks(v))
        return false;
    }
    return true;
  }
  protected java.util.Map isDUbeforeCondition_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1081
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUbeforeCondition(Variable v) {
    Object _parameters = v;
    if(isDUbeforeCondition_Variable_values == null) isDUbeforeCondition_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if(isDUbeforeCondition_Variable_values.containsKey(_parameters)) {
      Object _o = isDUbeforeCondition_Variable_values.get(_parameters);
      if(!(_o instanceof ASTNode$State.CircularValue)) {
        return ((Boolean)_o).booleanValue();
      }
      else
        _value = (ASTNode$State.CircularValue)_o;
    }
    else {
      _value = new ASTNode$State.CircularValue();
      isDUbeforeCondition_Variable_values.put(_parameters, _value);
      _value.value = Boolean.valueOf(true);
    }
    ASTNode$State state = state();
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      boolean new_isDUbeforeCondition_Variable_value;
      do {
        _value.visited = new Integer(state.CIRCLE_INDEX);
        state.CHANGE = false;
        new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
        if (new_isDUbeforeCondition_Variable_value!=((Boolean)_value.value).booleanValue()) {
          state.CHANGE = true;
          _value.value = Boolean.valueOf(new_isDUbeforeCondition_Variable_value);
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if(isFinal && num == state().boundariesCrossed) {
        isDUbeforeCondition_Variable_values.put(_parameters, new_isDUbeforeCondition_Variable_value);
      }
      else {
        isDUbeforeCondition_Variable_values.remove(_parameters);
      state.RESET_CYCLE = true;
      isDUbeforeCondition_compute(v);
      state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false; 
      return new_isDUbeforeCondition_Variable_value;
    }
    if(!new Integer(state.CIRCLE_INDEX).equals(_value.visited)) {
      _value.visited = new Integer(state.CIRCLE_INDEX);
      boolean new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
      if (state.RESET_CYCLE) {
        isDUbeforeCondition_Variable_values.remove(_parameters);
      }
      else if (new_isDUbeforeCondition_Variable_value!=((Boolean)_value.value).booleanValue()) {
        state.CHANGE = true;
        _value.value = new_isDUbeforeCondition_Variable_value;
      }
      return new_isDUbeforeCondition_Variable_value;
    }
    return ((Boolean)_value.value).booleanValue();
  }
  /**
   * @apilevel internal
   */
  private boolean isDUbeforeCondition_compute(Variable v) {
    if(!getStmt().isDUafter(v))
      return false;
    else {
      for(Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
        ContinueStmt stmt = (ContinueStmt)iter.next();
        if(!stmt.isDUafterReachedFinallyBlocks(v))
          return false;
      }
    }
    return true;
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:399
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean continueLabel() {
      ASTNode$State state = state();
    boolean continueLabel_value = continueLabel_compute();
    return continueLabel_value;
  }
  /**
   * @apilevel internal
   */
  private boolean continueLabel_compute() {  return true;  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:88
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
  private boolean canCompleteNormally_compute() {  return getStmt().canCompleteNormally() && (!getCondition().isConstant() || !getCondition().isTrue())
    || reachableContinue() && (!getCondition().isConstant() || !getCondition().isTrue()) || reachableBreak();  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:957
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean definesLabel() {
      ASTNode$State state = state();
    boolean definesLabel_value = definesLabel_compute();
    return definesLabel_value;
  }
  /**
   * @apilevel internal
   */
  private boolean definesLabel_compute() {  return true;  }
  /**
   * @apilevel internal
   */
  protected boolean begin_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int begin_label_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1357
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int begin_label() {
    if(begin_label_computed) {
      return begin_label_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    begin_label_value = begin_label_compute();
if(isFinal && num == state().boundariesCrossed) begin_label_computed = true;
    return begin_label_value;
  }
  /**
   * @apilevel internal
   */
  private int begin_label_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean cond_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int cond_label_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1358
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int cond_label() {
    if(cond_label_computed) {
      return cond_label_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    cond_label_value = cond_label_compute();
if(isFinal && num == state().boundariesCrossed) cond_label_computed = true;
    return cond_label_value;
  }
  /**
   * @apilevel internal
   */
  private int cond_label_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean end_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int end_label_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1359
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int end_label() {
    if(end_label_computed) {
      return end_label_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    end_label_value = end_label_compute();
if(isFinal && num == state().boundariesCrossed) end_label_computed = true;
    return end_label_value;
  }
  /**
   * @apilevel internal
   */
  private int end_label_compute() {  return hostType().constantPool().newLabel();  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1401
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int break_label() {
      ASTNode$State state = state();
    int break_label_value = break_label_compute();
    return break_label_value;
  }
  /**
   * @apilevel internal
   */
  private int break_label_compute() {  return end_label();  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1420
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int continue_label() {
      ASTNode$State state = state();
    int continue_label_value = continue_label_compute();
    return continue_label_value;
  }
  /**
   * @apilevel internal
   */
  private int continue_label_compute() {  return cond_label();  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:599
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getConditionNoTransform()){
    if(!getStmt().isDAafter(v))
      return false;
    for(Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
      ContinueStmt stmt = (ContinueStmt)iter.next();
      if(!stmt.isDAafterReachedFinallyBlocks(v))
        return false;
    }
    return true;
  }
    if(caller == getStmtNoTransform()) {
      return isDAbefore(v);
    }
    return getParent().Define_boolean_isDAbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1080
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getConditionNoTransform()) {
      return isDUbeforeCondition(v);
    }
    if(caller == getStmtNoTransform()) {
      return isDUbefore(v) && getCondition().isDUafterTrue(v);
    }
    return getParent().Define_boolean_isDUbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:367
   * @apilevel internal
   */
  public boolean Define_boolean_insideLoop(ASTNode caller, ASTNode child) {
    if(caller == getStmtNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_insideLoop(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:100
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if(caller == getStmtNoTransform()) {
      return reachable();
    }
    return getParent().Define_boolean_reachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:152
   * @apilevel internal
   */
  public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
    if(caller == getStmtNoTransform()) {
      return reachable();
    }
    return getParent().Define_boolean_reportUnreachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:965
   * @apilevel internal
   */
  public int Define_int_condition_false_label(ASTNode caller, ASTNode child) {
    if(caller == getConditionNoTransform()) {
      return end_label();
    }
    return getParent().Define_int_condition_false_label(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:966
   * @apilevel internal
   */
  public int Define_int_condition_true_label(ASTNode caller, ASTNode child) {
    if(caller == getConditionNoTransform()) {
      return begin_label();
    }
    return getParent().Define_int_condition_true_label(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
