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
 * A catch clause which can catch a single exception type.
 * @ast node
 * @declaredat CatchClause.ast:9
 */
public class BasicCatch extends CatchClause implements Cloneable {
	 /**
	   * @apilevel low-level
	   */
  public void flushCache() {
    super.flushCache();
    parameterDeclaration_String_values = null;
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
  public BasicCatch clone() throws CloneNotSupportedException {
    BasicCatch node = (BasicCatch)super.clone();
    node.parameterDeclaration_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BasicCatch copy() {
      try {
        BasicCatch node = (BasicCatch)clone();
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
  public BasicCatch fullCopy() {
    BasicCatch res = (BasicCatch)copy();
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:722
   */
  public void toString(StringBuffer s) {
    s.append("catch (");
    getParameter().toString(s);
    s.append(") ");
    getBlock().toString(s);
  }
  /**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:368
   */
  public void typeCheck() {
    if(!getParameter().type().instanceOf(typeThrowable()))
      error("*** The catch variable must extend Throwable");
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:17
   */
  public void exceptionTableEntries(CodeGeneration gen, TryStmt tryStmt) {
	    gen.addException(
	      gen.addressOf(tryStmt.label_begin()),
	      gen.addressOf(tryStmt.label_block_end()),
	      gen.addressOf(label()),
	      gen.constantPool().addClass(getParameter().type().constantPoolName())
	      );
	  }
  public void exceptionTableEntries(CodeGeneration gen, SafeStmt safeStmt) {
	    gen.addException(
	      gen.addressOf(safeStmt.label_begin()),
	      gen.addressOf(safeStmt.label_block_end()),
	      gen.addressOf(label()),
	      gen.constantPool().addClass(getParameter().type().constantPoolName())
	      );
	  }
  
  
  
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1523
   */
  public void createBCode(CodeGeneration gen) {
    gen.addLabel(label());
    // add 1 to stack depth
    gen.changeStackDepth(1);
    getParameter().type().emitStoreLocal(gen, getParameter().localNum());
    getBlock().createBCode(gen);
  }
  /**
   * @ast method 
   * @declaredat CatchClause.ast:1
   */
  public BasicCatch() {
    super();


  }
  /**
   * @ast method 
   * @declaredat CatchClause.ast:7
   */
  public BasicCatch(ParameterDeclaration p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat CatchClause.ast:14
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat CatchClause.ast:20
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * Setter for Parameter
   * @apilevel high-level
   * @ast method 
   * @declaredat CatchClause.ast:5
   */
  public void setParameter(ParameterDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Getter for Parameter
   * @apilevel high-level
   * @ast method 
   * @declaredat CatchClause.ast:12
   */
  public ParameterDeclaration getParameter() {
    return (ParameterDeclaration)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat CatchClause.ast:18
   */
  public ParameterDeclaration getParameterNoTransform() {
    return (ParameterDeclaration)getChildNoTransform(0);
  }
  /**
   * Setter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat CatchClause.ast:5
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Getter for Block
   * @apilevel high-level
   * @ast method 
   * @declaredat CatchClause.ast:12
   */
  public Block getBlock() {
    return (Block)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat CatchClause.ast:18
   */
  public Block getBlockNoTransform() {
    return (Block)getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:199
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean handles(TypeDecl exceptionType) {
      ASTNode$State state = state();
    boolean handles_TypeDecl_value = handles_compute(exceptionType);
    return handles_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean handles_compute(TypeDecl exceptionType) {  return !getParameter().type().isUnknown()
    && exceptionType.instanceOf(getParameter().type());  }
  protected java.util.Map parameterDeclaration_String_values;
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:113
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
  private SimpleSet parameterDeclaration_compute(String name) {  return getParameter().name().equals(name) ? getParameter() : SimpleSet.emptySet;  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:83
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if(caller == getParameterNoTransform()) {
      return parameterDeclaration(name);
    }
    return super.Define_SimpleSet_lookupVariable(caller, child, name);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:290
   * @apilevel internal
   */
  public VariableScope Define_VariableScope_outerScope(ASTNode caller, ASTNode child) {
    if(caller == getParameterNoTransform()) {
      return this;
    }
    return getParent().Define_VariableScope_outerScope(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:86
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getParameterNoTransform()) {
      return NameType.TYPE_NAME;
    }
    return getParent().Define_NameType_nameType(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:122
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return reachableCatchClause(getParameter().type());
    }
    return getParent().Define_boolean_reachable(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:64
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterNoTransform()) {
      return false;
    }
    return getParent().Define_boolean_isMethodParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:65
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterNoTransform()) {
      return false;
    }
    return getParent().Define_boolean_isConstructorParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:66
   * @apilevel internal
   */
  public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if(caller == getParameterNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:120
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if(caller == getBlockNoTransform()) {
      return getParameter().localNum() + getParameter().type().variableSize();
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
