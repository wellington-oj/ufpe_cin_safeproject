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
 * @declaredat java.ast:108
 */
public class AssignMulExpr extends AssignMultiplicativeExpr implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
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
  public AssignMulExpr clone() throws CloneNotSupportedException {
    AssignMulExpr node = (AssignMulExpr)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public AssignMulExpr copy() {
      try {
        AssignMulExpr node = (AssignMulExpr)clone();
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
  public AssignMulExpr fullCopy() {
    AssignMulExpr res = (AssignMulExpr)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:420
   */
  public void createAssignOp(CodeGeneration gen, TypeDecl type)     { type.mul(gen); }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public AssignMulExpr() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public AssignMulExpr(Expr p0, Expr p1) {
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
   * Setter for Dest
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setDest(Expr node) {
    setChild(node, 0);
  }
  /**
   * Getter for Dest
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Expr getDest() {
    return (Expr)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Expr getDestNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
  /**
   * Setter for Source
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setSource(Expr node) {
    setChild(node, 1);
  }
  /**
   * Getter for Source
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Expr getSource() {
    return (Expr)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Expr getSourceNoTransform() {
    return (Expr)getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:249
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String printOp() {
      ASTNode$State state = state();
    String printOp_value = printOp_compute();
    return printOp_value;
  }
  /**
   * @apilevel internal
   */
  private String printOp_compute() {  return " *= ";  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
