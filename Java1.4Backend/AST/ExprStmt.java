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
 * @declaredat java.ast:197
 */
public class ExprStmt extends Stmt implements Cloneable {
	/**
	 * @apilevel low-level
	 */
	public void flushCache() {
		super.flushCache();
		isDAafter_Variable_values = null;
		isDUafter_Variable_values = null;
		canCompleteNormally_computed = false;
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
	public ExprStmt clone() throws CloneNotSupportedException {
		ExprStmt node = (ExprStmt)super.clone();
		node.isDAafter_Variable_values = null;
		node.isDUafter_Variable_values = null;
		node.canCompleteNormally_computed = false;
		node.in$Circle(false);
		node.is$Final(false);
		return node;
	}
	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public ExprStmt copy() {
		try {
			ExprStmt node = (ExprStmt)clone();
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
	public ExprStmt fullCopy() {
		ExprStmt res = (ExprStmt)copy();
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
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:548
	 */
	public void toString(StringBuffer s) {
		s.append(indent());
		getExpr().toString(s);
		s.append(";");
	}
	/**
	 * @ast method 
	 * @aspect CreateBCode
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1202
	 */
	public void createBCode(CodeGeneration gen) {

		super.createBCode(gen);
		getExpr().createBCode(gen);
		if(needsPop())
			getExpr().type().emitPop(gen);
	}
	/**
	 * @ast method 
	 * @declaredat java.ast:1
	 */
	public ExprStmt() {
		super();


	}
	/**
	 * @ast method 
	 * @declaredat java.ast:7
	 */
	public ExprStmt(Expr p0) {
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
	protected java.util.Map isDAafter_Variable_values;
	/**
	 * @attribute syn
	 * @aspect DA
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:521
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
	private boolean isDAafter_compute(Variable v) {  return getExpr().isDAafter(v);  }
	protected java.util.Map isDUafter_Variable_values;
	/**
	 * @attribute syn
	 * @aspect DU
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:990
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
	private boolean isDUafter_compute(Variable v) {  return getExpr().isDUafter(v);  }
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
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:58
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
	private boolean canCompleteNormally_compute() {  return reachable();  }
	/**
	 * @attribute syn
	 * @aspect CodeGeneration
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:27
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
	private int sourceLineNumber_compute() {  return getExpr().findFirstSourceLineNumber();  }
	/**
	 * @attribute syn
	 * @aspect CreateBCode
	 * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:218
	 */
	@SuppressWarnings({"unchecked", "cast"})
	public boolean needsPop() {
		ASTNode$State state = state();
		boolean needsPop_value = needsPop_compute();
		return needsPop_value;
	}
	/**
	 * @apilevel internal
	 */
	private boolean needsPop_compute() {  return getExpr().needsPop();  }
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:522
	 * @apilevel internal
	 */
	public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
		if(caller == getExprNoTransform()) {
			return isDAbefore(v);
		}
		return getParent().Define_boolean_isDAbefore(this, caller, v);
	}
	/**
	 * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:991
	 * @apilevel internal
	 */
	public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
		if(caller == getExprNoTransform()) {
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
