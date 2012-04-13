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
 * @declaredat java.ast:210
 */
public class ContinueStmt extends Stmt implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    targetStmt_computed = false;
    targetStmt_value = null;
    finallyList_computed = false;
    finallyList_value = null;
    isDAafter_Variable_values = null;
    isDUafterReachedFinallyBlocks_Variable_values = null;
    isDAafterReachedFinallyBlocks_Variable_values = null;
    isDUafter_Variable_values = null;
    canCompleteNormally_computed = false;
    lookupLabel_String_values = null;
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
  public ContinueStmt clone() throws CloneNotSupportedException {
    ContinueStmt node = (ContinueStmt)super.clone();
    node.targetStmt_computed = false;
    node.targetStmt_value = null;
    node.finallyList_computed = false;
    node.finallyList_value = null;
    node.isDAafter_Variable_values = null;
    node.isDUafterReachedFinallyBlocks_Variable_values = null;
    node.isDAafterReachedFinallyBlocks_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.canCompleteNormally_computed = false;
    node.lookupLabel_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ContinueStmt copy() {
      try {
        ContinueStmt node = (ContinueStmt)clone();
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
  public ContinueStmt fullCopy() {
    ContinueStmt res = (ContinueStmt)copy();
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:49
   */
  public void collectBranches(Collection c) {
    c.add(this);
  }
  /**
   * @ast method 
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:384
   */
  public void nameCheck() {
    if(!insideLoop())
      error("continue outside loop");
    else if(hasLabel()) {
      LabeledStmt label = lookupLabel(getLabel());
      if(label == null)
        error("labeled continue must have visible matching label");
      else if(!label.getStmt().continueLabel())
        error(getLabel() + " is not a loop label");
    }
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:675
   */
  public void toString(StringBuffer s) {
    s.append(indent());
    s.append("continue ");
    if(hasLabel())
      s.append(getLabel());
    s.append(";");
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1424
   */
  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    for(Iterator iter = finallyList().iterator(); iter.hasNext(); ) {
      FinallyHost stmt = (FinallyHost)iter.next();
      gen.emitJsr(stmt.label_finally_block());
    }
    gen.emitGoto(targetStmt().continue_label());
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ContinueStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public ContinueStmt(String p0) {
    setLabel(p0);
  }
  /**
   * @ast method 
   * @declaredat java.ast:10
   */
  public ContinueStmt(beaver.Symbol p0) {
    setLabel(p0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:16
   */
  protected int numChildren() {
    return 0;
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
   * Setter for lexeme Label
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setLabel(String value) {
    tokenString_Label = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat java.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_Label;
  /**
   * @ast method 
   * @declaredat java.ast:9
   */
  
  public int Labelstart;
  /**
   * @ast method 
   * @declaredat java.ast:10
   */
  
  public int Labelend;
  /**
   * @ast method 
   * @declaredat java.ast:11
   */
  public void setLabel(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String))
      throw new UnsupportedOperationException("setLabel is only valid for String lexemes");
    tokenString_Label = (String)symbol.value;
    Labelstart = symbol.getStart();
    Labelend = symbol.getEnd();
  }
  /**
   * Getter for lexeme Label
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:22
   */
  public String getLabel() {
    return tokenString_Label != null ? tokenString_Label : "";
  }
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:65
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasLabel() {
      ASTNode$State state = state();
    boolean hasLabel_value = hasLabel_compute();
    return hasLabel_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasLabel_compute() {  return !getLabel().equals("");  }
  /**
   * @apilevel internal
   */
  protected boolean targetStmt_computed = false;
  /**
   * @apilevel internal
   */
  protected Stmt targetStmt_value;
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:150
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Stmt targetStmt() {
    if(targetStmt_computed) {
      return targetStmt_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    targetStmt_value = targetStmt_compute();
if(isFinal && num == state().boundariesCrossed) targetStmt_computed = true;
    return targetStmt_value;
  }
  /**
   * @apilevel internal
   */
  private Stmt targetStmt_compute() {  return branchTarget(this);  }
  /**
   * @apilevel internal
   */
  protected boolean finallyList_computed = false;
  /**
   * @apilevel internal
   */
  protected ArrayList finallyList_value;
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:181
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ArrayList finallyList() {
    if(finallyList_computed) {
      return finallyList_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    finallyList_value = finallyList_compute();
if(isFinal && num == state().boundariesCrossed) finallyList_computed = true;
    return finallyList_value;
  }
  /**
   * @apilevel internal
   */
  private ArrayList finallyList_compute() {
    ArrayList list = new ArrayList();
    collectFinally(this, list);
    return list;
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:647
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
  private boolean isDAafter_compute(Variable v) {  return true;  }
  protected java.util.Map isDUafterReachedFinallyBlocks_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:931
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if(isDUafterReachedFinallyBlocks_Variable_values == null) isDUafterReachedFinallyBlocks_Variable_values = new java.util.HashMap(4);
    if(isDUafterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUafterReachedFinallyBlocks_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDUafterReachedFinallyBlocks_Variable_value = isDUafterReachedFinallyBlocks_compute(v);
if(isFinal && num == state().boundariesCrossed) isDUafterReachedFinallyBlocks_Variable_values.put(_parameters, Boolean.valueOf(isDUafterReachedFinallyBlocks_Variable_value));
    return isDUafterReachedFinallyBlocks_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafterReachedFinallyBlocks_compute(Variable v) {
    if(!isDUbefore(v) && finallyList().isEmpty())
      return false;
    for(Iterator iter = finallyList().iterator(); iter.hasNext(); ) {
      FinallyHost f = (FinallyHost)iter.next();
      if(!f.isDUafterFinally(v))
        return false;
    }
    return true;
  }
  protected java.util.Map isDAafterReachedFinallyBlocks_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:965
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if(isDAafterReachedFinallyBlocks_Variable_values == null) isDAafterReachedFinallyBlocks_Variable_values = new java.util.HashMap(4);
    if(isDAafterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAafterReachedFinallyBlocks_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDAafterReachedFinallyBlocks_Variable_value = isDAafterReachedFinallyBlocks_compute(v);
if(isFinal && num == state().boundariesCrossed) isDAafterReachedFinallyBlocks_Variable_values.put(_parameters, Boolean.valueOf(isDAafterReachedFinallyBlocks_Variable_value));
    return isDAafterReachedFinallyBlocks_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafterReachedFinallyBlocks_compute(Variable v) {
    if(isDAbefore(v))
      return true;
    if(finallyList().isEmpty())
      return false;
    for(Iterator iter = finallyList().iterator(); iter.hasNext(); ) {
      FinallyHost f = (FinallyHost)iter.next();
      if(!f.isDAafterFinally(v))
        return false;
    }
    return true;
  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1170
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
  private boolean isDUafter_compute(Variable v) {  return true;  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:106
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
  private boolean canCompleteNormally_compute() {  return false;  }
  protected java.util.Map lookupLabel_String_values;
  /**
   * @attribute inh
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:170
   */
  @SuppressWarnings({"unchecked", "cast"})
  public LabeledStmt lookupLabel(String name) {
    Object _parameters = name;
    if(lookupLabel_String_values == null) lookupLabel_String_values = new java.util.HashMap(4);
    if(lookupLabel_String_values.containsKey(_parameters)) {
      return (LabeledStmt)lookupLabel_String_values.get(_parameters);
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    LabeledStmt lookupLabel_String_value = getParent().Define_LabeledStmt_lookupLabel(this, null, name);
if(isFinal && num == state().boundariesCrossed) lookupLabel_String_values.put(_parameters, lookupLabel_String_value);
    return lookupLabel_String_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:361
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean insideLoop() {
      ASTNode$State state = state();
    boolean insideLoop_value = getParent().Define_boolean_insideLoop(this, null);
    return insideLoop_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
