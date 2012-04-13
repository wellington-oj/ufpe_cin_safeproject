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
 * @declaredat java.ast:211
 */
public class ReturnStmt extends Stmt implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    finallyList_computed = false;
    finallyList_value = null;
    isDAafter_Variable_values = null;
    isDUafterReachedFinallyBlocks_Variable_values = null;
    isDAafterReachedFinallyBlocks_Variable_values = null;
    isDUafter_Variable_values = null;
    canCompleteNormally_computed = false;
    resultSaveLocalNum_computed = false;
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
  public ReturnStmt clone() throws CloneNotSupportedException {
    ReturnStmt node = (ReturnStmt)super.clone();
    node.finallyList_computed = false;
    node.finallyList_value = null;
    node.isDAafter_Variable_values = null;
    node.isDUafterReachedFinallyBlocks_Variable_values = null;
    node.isDAafterReachedFinallyBlocks_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.canCompleteNormally_computed = false;
    node.resultSaveLocalNum_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ReturnStmt copy() {
      try {
        ReturnStmt node = (ReturnStmt)clone();
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
  public ReturnStmt fullCopy() {
    ReturnStmt res = (ReturnStmt)copy();
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:55
   */
  public void collectBranches(Collection c) {
    c.add(this);
  }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:62
   */
  public ReturnStmt(Expr expr) {
    this(new Opt(expr));
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:683
   */
  public void toString(StringBuffer s) {
    s.append(indent());
    s.append("return ");
    if(hasResult()) {
      getResult().toString(s);
    }
    s.append(";");
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:408
   */
  public void typeCheck() {
    if(hasResult() && !returnType().isVoid()) {
      if(!getResult().type().assignConversionTo(returnType(), getResult()))
        error("return value must be an instance of " + returnType().typeName() + " which " + getResult().type().typeName() + " is not");
    }
    // 8.4.5 8.8.5
    if(returnType().isVoid() && hasResult())
      error("return stmt may not have an expression in void methods");
    // 8.4.5
    if(!returnType().isVoid() && !hasResult())
      error("return stmt must have an expression in non void methods");
    if(enclosingBodyDecl() instanceof InstanceInitializer || enclosingBodyDecl() instanceof StaticInitializer)
      error("Initializers may not return");

  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1433
   */
  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    if(hasResult()) {
      TypeDecl type = null;
      BodyDecl b = enclosingBodyDecl();
      if(b instanceof MethodDecl) {
        type = ((MethodDecl)b).type();
      }
      else {
        throw new Error("Can not create code that returns value within non method");
      }
      getResult().createBCode(gen);
      getResult().type().emitCastTo(gen, type);
      if(!finallyList().isEmpty()) {
        type.emitStoreLocal(gen, resultSaveLocalNum());
      }
      for(Iterator iter = finallyList().iterator(); iter.hasNext(); ) {
        FinallyHost stmt = (FinallyHost)iter.next();
        gen.emitJsr(stmt.label_finally_block());
      }
      if(!finallyList().isEmpty()) {
        type.emitLoadLocal(gen, resultSaveLocalNum());
      }
      type.emitReturn(gen);
    }
    else {
      for(Iterator iter = finallyList().iterator(); iter.hasNext(); ) {
        FinallyHost stmt = (FinallyHost)iter.next();
        gen.emitJsr(stmt.label_finally_block());
      }
      gen.emitReturn();
    }
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ReturnStmt() {
    super();

    setChild(new Opt(), 0);

  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public ReturnStmt(Opt<Expr> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:14
   */
  protected int numChildren() {
    return 1;
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
   * Setter for ResultOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setResultOpt(Opt<Expr> opt) {
    setChild(opt, 0);
  }
  /**
   * Does this node have a Result child?
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public boolean hasResult() {
    return getResultOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child Result
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr getResult() {
    return (Expr)getResultOpt().getChild(0);
  }
  /**
   * Setter for optional child Result
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void setResult(Expr node) {
    getResultOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getResultOpt() {
    return (Opt<Expr>)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getResultOptNoTransform() {
    return (Opt<Expr>)getChildNoTransform(0);
  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:186
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:648
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:941
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:977
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
    if(hasResult() ? getResult().isDAafter(v) : isDAbefore(v))
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1171
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:107
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
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:16
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
  private int sourceLineNumber_compute() {
    int num = super.sourceLineNumber();
    if(num != -1)
      return num;
    if(hasResult()) {
      num = getResult().findFirstSourceLineNumber();
      if(num != -1)
        return num;
    }
    return getLine(getParent().getParent().getEnd());
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:403
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl returnType() {
      ASTNode$State state = state();
    TypeDecl returnType_value = getParent().Define_TypeDecl_returnType(this, null);
    return returnType_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean resultSaveLocalNum_computed = false;
  /**
   * @apilevel internal
   */
  protected int resultSaveLocalNum_value;
  /**
   * @attribute inh
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int resultSaveLocalNum() {
    if(resultSaveLocalNum_computed) {
      return resultSaveLocalNum_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    resultSaveLocalNum_value = getParent().Define_int_resultSaveLocalNum(this, null);
if(isFinal && num == state().boundariesCrossed) resultSaveLocalNum_computed = true;
    return resultSaveLocalNum_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:651
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getResultOptNoTransform()) {
      return isDAbefore(v);
    }
    return getParent().Define_boolean_isDAbefore(this, caller, v);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1174
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getResultOptNoTransform()) {
      return isDUbefore(v);
    }
    return getParent().Define_boolean_isDUbefore(this, caller, v);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
