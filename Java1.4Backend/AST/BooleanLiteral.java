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
 * Represents a Java boolean; either "true" or "false".
 * @ast node
 * @declaredat Literals.ast:9
 */
public class BooleanLiteral extends Literal implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    constant_computed = false;
    constant_value = null;
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
  public BooleanLiteral clone() throws CloneNotSupportedException {
    BooleanLiteral node = (BooleanLiteral)super.clone();
    node.constant_computed = false;
    node.constant_value = null;
    node.type_computed = false;
    node.type_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BooleanLiteral copy() {
      try {
        BooleanLiteral node = (BooleanLiteral)clone();
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
  public BooleanLiteral fullCopy() {
    BooleanLiteral res = (BooleanLiteral)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:52
   */
  public BooleanLiteral(boolean b) {
    this(b ? "true" : "false");
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:538
   */
  public static void push(CodeGeneration gen, boolean value) {
    gen.emit(value ? Bytecode.ICONST_1 : Bytecode.ICONST_0);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:582
   */
  public void emitPushConstant(CodeGeneration gen) {
    BooleanLiteral.push(gen, constant().booleanValue());
  }
  /**
   * @ast method 
   * @declaredat Literals.ast:1
   */
  public BooleanLiteral() {
    super();


  }
  /**
   * @ast method 
   * @declaredat Literals.ast:7
   */
  public BooleanLiteral(String p0) {
    setLITERAL(p0);
  }
  /**
   * @ast method 
   * @declaredat Literals.ast:10
   */
  public BooleanLiteral(beaver.Symbol p0) {
    setLITERAL(p0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat Literals.ast:16
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat Literals.ast:22
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * Setter for lexeme LITERAL
   * @apilevel high-level
   * @ast method 
   * @declaredat Literals.ast:5
   */
  public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
  /**
   * @ast method 
   * @declaredat Literals.ast:8
   */
  public void setLITERAL(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String))
      throw new UnsupportedOperationException("setLITERAL is only valid for String lexemes");
    tokenString_LITERAL = (String)symbol.value;
    LITERALstart = symbol.getStart();
    LITERALend = symbol.getEnd();
  }
  /**
   * Getter for lexeme LITERAL
   * @apilevel high-level
   * @ast method 
   * @declaredat Literals.ast:19
   */
  public String getLITERAL() {
    return tokenString_LITERAL != null ? tokenString_LITERAL : "";
  }
  /**
   * @apilevel internal
   */
  protected boolean constant_computed = false;
  /**
   * @apilevel internal
   */
  protected Constant constant_value;
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:302
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant constant() {
    if(constant_computed) {
      return constant_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    constant_value = constant_compute();
if(isFinal && num == state().boundariesCrossed) constant_computed = true;
    return constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant constant_compute() {  return Constant.create(Boolean.valueOf(getLITERAL()).booleanValue());  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:304
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
  private TypeDecl type_compute() {  return typeBoolean();  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
