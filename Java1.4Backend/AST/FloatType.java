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
 * @declaredat java.ast:59
 */
public class FloatType extends FloatingPointType implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    typeDescriptor_computed = false;
    typeDescriptor_value = null;
    jvmName_computed = false;
    jvmName_value = null;
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
  public FloatType clone() throws CloneNotSupportedException {
    FloatType node = (FloatType)super.clone();
    node.typeDescriptor_computed = false;
    node.typeDescriptor_value = null;
    node.jvmName_computed = false;
    node.jvmName_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public FloatType copy() {
      try {
        FloatType node = (FloatType)clone();
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
  public FloatType fullCopy() {
    FloatType res = (FloatType)copy();
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:849
   */
  public void toString(StringBuffer s) {
		s.append("float");
	}
  /**
   * @ast method 
   * @aspect Attributes
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Attributes.jrag:65
   */
  public int addConstant(ConstantPool p, Constant c)    { return p.addConstant(c.floatValue()); }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:546
   */
  public void emitPushConstant(CodeGeneration gen, int value) { FloatingPointLiteral.push(gen, value); }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:616
   */
  public void emitReturn(CodeGeneration gen)     { gen.emit(Bytecode.FRETURN);}
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:653
   */
  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+1);
    if(pos == 0) gen.emit(Bytecode.FLOAD_0);
    else if(pos == 1) gen.emit(Bytecode.FLOAD_1);
    else if(pos == 2) gen.emit(Bytecode.FLOAD_2);
    else if(pos == 3) gen.emit(Bytecode.FLOAD_3);
    else if(pos < 256) gen.emit(Bytecode.FLOAD).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.FLOAD).add2(pos);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:766
   */
  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+1);
    if(pos == 0) gen.emit(Bytecode.FSTORE_0);
    else if(pos == 1) gen.emit(Bytecode.FSTORE_1);
    else if(pos == 2) gen.emit(Bytecode.FSTORE_2);
    else if(pos == 3) gen.emit(Bytecode.FSTORE_3);
    else if(pos < 256) gen.emit(Bytecode.FSTORE).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.FSTORE).add2(pos);
  }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:944
   */
  void emitCastTo(CodeGeneration gen, TypeDecl type)    { type.floatToThis(gen); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:956
   */
  void intToThis(CodeGeneration gen)  { gen.emit(Bytecode.I2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:967
   */
  void floatToThis(CodeGeneration gen)  { }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:976
   */
  void doubleToThis(CodeGeneration gen)  { gen.emit(Bytecode.D2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:985
   */
  void longToThis(CodeGeneration gen)  { gen.emit(Bytecode.L2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:992
   */
  void byteToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1000
   */
  void charToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1008
   */
  void shortToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1040
   */
  void neg(CodeGeneration gen)    { gen.emit(Bytecode.FNEG); }
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1052
   */
  void add(CodeGeneration gen) {gen.emit(Bytecode.FADD);}
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1058
   */
  void sub(CodeGeneration gen) {gen.emit(Bytecode.FSUB);}
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1064
   */
  void mul(CodeGeneration gen) {gen.emit(Bytecode.FMUL);}
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1070
   */
  void div(CodeGeneration gen) {gen.emit(Bytecode.FDIV);}
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1076
   */
  void rem(CodeGeneration gen) {gen.emit(Bytecode.FREM);}
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1111
   */
  public void branchLT(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPG).emitCompare(Bytecode.IFLT, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1117
   */
  public void branchLE(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPG).emitCompare(Bytecode.IFLE, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1123
   */
  public void branchGE(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFGE, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1129
   */
  public void branchGT(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFGT, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1135
   */
  public void branchEQ(CodeGeneration gen, int label)     { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFEQ, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1144
   */
  public void branchNE(CodeGeneration gen, int label)     { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFNE, label); }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public FloatType() {
    super();

    setChild(new Opt(), 1);
    setChild(new List(), 2);

  }
  /**
   * @ast method 
   * @declaredat java.ast:9
   */
  public FloatType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @ast method 
   * @declaredat java.ast:15
   */
  public FloatType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:24
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:30
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * Setter for Modifiers
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
  /**
   * Getter for Modifiers
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Modifiers getModifiers() {
    return (Modifiers)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers)getChildNoTransform(0);
  }
  /**
   * Setter for lexeme ID
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public void setID(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String))
      throw new UnsupportedOperationException("setID is only valid for String lexemes");
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
  /**
   * Getter for lexeme ID
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * Setter for SuperClassAccessOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setSuperClassAccessOpt(Opt<Access> opt) {
    setChild(opt, 1);
  }
  /**
   * Does this node have a SuperClassAccess child?
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public boolean hasSuperClassAccess() {
    return getSuperClassAccessOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child SuperClassAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Access getSuperClassAccess() {
    return (Access)getSuperClassAccessOpt().getChild(0);
  }
  /**
   * Setter for optional child SuperClassAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void setSuperClassAccess(Access node) {
    getSuperClassAccessOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Access> getSuperClassAccessOpt() {
    return (Opt<Access>)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Access> getSuperClassAccessOptNoTransform() {
    return (Opt<Access>)getChildNoTransform(1);
  }
  /**
   * Setter for BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 2);
  }
  /**
   * @return number of children in BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public int getNumBodyDecl() {
    return getBodyDeclList().getNumChild();
  }
  /**
   * Getter for child in list BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BodyDecl getBodyDecl(int i) {
    return (BodyDecl)getBodyDeclList().getChild(i);
  }
  /**
   * Add element to list BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void addBodyDecl(BodyDecl node) {
    List<BodyDecl> list = (parent == null || state == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:34
   */
  public void addBodyDeclNoTransform(BodyDecl node) {
    List<BodyDecl> list = getBodyDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:42
   */
  public void setBodyDecl(BodyDecl node, int i) {
    List<BodyDecl> list = getBodyDeclList();
    list.setChild(node, i);
  }
  /**
   * Getter for BodyDecl list.
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:50
   */
  public List<BodyDecl> getBodyDecls() {
    return getBodyDeclList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:56
   */
  public List<BodyDecl> getBodyDeclsNoTransform() {
    return getBodyDeclListNoTransform();
  }
  /**
   * Getter for list BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<BodyDecl> getBodyDeclList() {
    List<BodyDecl> list = (List<BodyDecl>)getChild(2);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>)getChildNoTransform(2);
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:315
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant cast(Constant c) {
      ASTNode$State state = state();
    Constant cast_Constant_value = cast_compute(c);
    return cast_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant cast_compute(Constant c) {  return Constant.create(c.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:326
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant plus(Constant c) {
      ASTNode$State state = state();
    Constant plus_Constant_value = plus_compute(c);
    return plus_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant plus_compute(Constant c) {  return c;  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:335
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant minus(Constant c) {
      ASTNode$State state = state();
    Constant minus_Constant_value = minus_compute(c);
    return minus_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant minus_compute(Constant c) {  return Constant.create(-c.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:351
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant mul(Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant mul_Constant_Constant_value = mul_compute(c1, c2);
    return mul_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant mul_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() * c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:360
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant div(Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant div_Constant_Constant_value = div_compute(c1, c2);
    return div_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant div_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() / c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:369
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant mod(Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant mod_Constant_Constant_value = mod_compute(c1, c2);
    return mod_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant mod_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() % c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:378
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant add(Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant add_Constant_Constant_value = add_compute(c1, c2);
    return add_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant add_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() + c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:388
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant sub(Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant sub_Constant_Constant_value = sub_compute(c1, c2);
    return sub_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant sub_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() - c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:442
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant questionColon(Constant cond, Constant c1, Constant c2) {
      ASTNode$State state = state();
    Constant questionColon_Constant_Constant_Constant_value = questionColon_compute(cond, c1, c2);
    return questionColon_Constant_Constant_Constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant questionColon_compute(Constant cond, Constant c1, Constant c2) {  return Constant.create(cond.booleanValue() ? c1.floatValue() : c2.floatValue());  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:546
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean eqIsTrue(Expr left, Expr right) {
      ASTNode$State state = state();
    boolean eqIsTrue_Expr_Expr_value = eqIsTrue_compute(left, right);
    return eqIsTrue_Expr_Expr_value;
  }
  /**
   * @apilevel internal
   */
  private boolean eqIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() == right.constant().floatValue();  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:554
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean ltIsTrue(Expr left, Expr right) {
      ASTNode$State state = state();
    boolean ltIsTrue_Expr_Expr_value = ltIsTrue_compute(left, right);
    return ltIsTrue_Expr_Expr_value;
  }
  /**
   * @apilevel internal
   */
  private boolean ltIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() < right.constant().floatValue();  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:560
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean leIsTrue(Expr left, Expr right) {
      ASTNode$State state = state();
    boolean leIsTrue_Expr_Expr_value = leIsTrue_compute(left, right);
    return leIsTrue_Expr_Expr_value;
  }
  /**
   * @apilevel internal
   */
  private boolean leIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() <= right.constant().floatValue();  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:196
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFloat() {
      ASTNode$State state = state();
    boolean isFloat_value = isFloat_compute();
    return isFloat_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFloat_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:627
   */
  @SuppressWarnings({"unchecked", "cast"})
  public byte arrayLoad() {
      ASTNode$State state = state();
    byte arrayLoad_value = arrayLoad_compute();
    return arrayLoad_value;
  }
  /**
   * @apilevel internal
   */
  private byte arrayLoad_compute() {  return Bytecode.FALOAD;  }
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:729
   */
  @SuppressWarnings({"unchecked", "cast"})
  public byte arrayStore() {
      ASTNode$State state = state();
    byte arrayStore_value = arrayStore_compute();
    return arrayStore_value;
  }
  /**
   * @apilevel internal
   */
  private byte arrayStore_compute() {  return Bytecode.FASTORE;  }
  /**
   * @apilevel internal
   */
  protected boolean typeDescriptor_computed = false;
  /**
   * @apilevel internal
   */
  protected String typeDescriptor_value;
  /**
   * @attribute syn
   * @aspect ConstantPoolNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/ConstantPoolNames.jrag:23
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String typeDescriptor() {
    if(typeDescriptor_computed) {
      return typeDescriptor_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    typeDescriptor_value = typeDescriptor_compute();
if(isFinal && num == state().boundariesCrossed) typeDescriptor_computed = true;
    return typeDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private String typeDescriptor_compute() {  return "F";  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:826
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int arrayPrimitiveTypeDescriptor() {
      ASTNode$State state = state();
    int arrayPrimitiveTypeDescriptor_value = arrayPrimitiveTypeDescriptor_compute();
    return arrayPrimitiveTypeDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private int arrayPrimitiveTypeDescriptor_compute() {  return 6;  }
  /**
   * @apilevel internal
   */
  protected boolean jvmName_computed = false;
  /**
   * @apilevel internal
   */
  protected String jvmName_value;
  /**
   * @attribute syn
   * @aspect Java2Rewrites
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Java2Rewrites.jrag:40
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String jvmName() {
    if(jvmName_computed) {
      return jvmName_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    jvmName_value = jvmName_compute();
if(isFinal && num == state().boundariesCrossed) jvmName_computed = true;
    return jvmName_value;
  }
  /**
   * @apilevel internal
   */
  private String jvmName_compute() {  return "F";  }
  /**
   * @attribute syn
   * @aspect Java2Rewrites
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Java2Rewrites.jrag:52
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String primitiveClassName() {
      ASTNode$State state = state();
    String primitiveClassName_value = primitiveClassName_compute();
    return primitiveClassName_value;
  }
  /**
   * @apilevel internal
   */
  private String primitiveClassName_compute() {  return "Float";  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
