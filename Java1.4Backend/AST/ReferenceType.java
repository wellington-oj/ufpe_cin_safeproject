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
 * @declaredat java.ast:41
 */
public abstract class ReferenceType extends TypeDecl implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    narrowingConversionTo_TypeDecl_values = null;
    bcFields_computed = false;
    bcFields_value = null;
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
  public ReferenceType clone() throws CloneNotSupportedException {
    ReferenceType node = (ReferenceType)super.clone();
    node.narrowingConversionTo_TypeDecl_values = null;
    node.bcFields_computed = false;
    node.bcFields_value = null;
    node.jvmName_computed = false;
    node.jvmName_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:618
   */
  public void emitReturn(CodeGeneration gen) { gen.emit(Bytecode.ARETURN);}
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:671
   */
  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.emitLoadReference(pos);
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:784
   */
  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.emitStoreReference(pos);
  }
  /**
   * @ast method 
   * @aspect CodeGenerationConversions
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:942
   */
  void emitCastTo(CodeGeneration gen, TypeDecl type) { if(!instanceOf(type) && !type.isNull()) gen.emitCheckCast(type); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1139
   */
  public void branchEQ(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ACMPEQ, label); }
  /**
   * @ast method 
   * @aspect CodeGenerationBranch
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1148
   */
  public void branchNE(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ACMPNE, label); }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ReferenceType() {
    super();

    setChild(new List(), 1);

  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public ReferenceType(Modifiers p0, String p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @ast method 
   * @declaredat java.ast:13
   */
  public ReferenceType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:21
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:27
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
   * Setter for BodyDeclList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 1);
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
    List<BodyDecl> list = (List<BodyDecl>)getChild(1);
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
    return (List<BodyDecl>)getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:33
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean wideningConversionTo(TypeDecl type) {
      ASTNode$State state = state();
    boolean wideningConversionTo_TypeDecl_value = wideningConversionTo_compute(type);
    return wideningConversionTo_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean wideningConversionTo_compute(TypeDecl type) {  return instanceOf(type);  }
  protected java.util.Map narrowingConversionTo_TypeDecl_values;
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:36
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean narrowingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if(narrowingConversionTo_TypeDecl_values == null) narrowingConversionTo_TypeDecl_values = new java.util.HashMap(4);
    if(narrowingConversionTo_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)narrowingConversionTo_TypeDecl_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean narrowingConversionTo_TypeDecl_value = narrowingConversionTo_compute(type);
if(isFinal && num == state().boundariesCrossed) narrowingConversionTo_TypeDecl_values.put(_parameters, Boolean.valueOf(narrowingConversionTo_TypeDecl_value));
    return narrowingConversionTo_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean narrowingConversionTo_compute(TypeDecl type) {
    if(type.instanceOf(this))
      return true;
    if(isClassDecl() && !getModifiers().isFinal() && type.isInterfaceDecl())
      return true;
    if(isInterfaceDecl() && type.isClassDecl() && !type.getModifiers().isFinal())
      return true;
    if(isInterfaceDecl() && type.instanceOf(this))
      return true;
    if(fullName().equals("java.lang.Object") && type.isInterfaceDecl())
      return true;
    // Dragons
    // TODO: Check if both are interfaces with compatible methods
    if(isArrayDecl() && type.isArrayDecl() && elementType().instanceOf(type.elementType()))
      return true;
    return false;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:166
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isReferenceType() {
      ASTNode$State state = state();
    boolean isReferenceType_value = isReferenceType_compute();
    return isReferenceType_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isReferenceType_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:483
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSupertypeOfNullType(NullType type) {
      ASTNode$State state = state();
    boolean isSupertypeOfNullType_NullType_value = isSupertypeOfNullType_compute(type);
    return isSupertypeOfNullType_NullType_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSupertypeOfNullType_compute(NullType type) {  return true;  }
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:624
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
  private byte arrayLoad_compute() {  return Bytecode.AALOAD;  }
  /**
   * @attribute syn
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:726
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
  private byte arrayStore_compute() {  return Bytecode.AASTORE;  }
  /**
   * @apilevel internal
   */
  protected boolean bcFields_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection bcFields_value;
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:276
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection bcFields() {
    if(bcFields_computed) {
      return bcFields_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    bcFields_value = bcFields_compute();
if(isFinal && num == state().boundariesCrossed) bcFields_computed = true;
    return bcFields_value;
  }
  /**
   * @apilevel internal
   */
  private Collection bcFields_compute() {
    ArrayList l = new ArrayList();
    for(int i = 0; i < getNumBodyDecl(); i++) 
      if(getBodyDecl(i).isBytecodeField() && getBodyDecl(i).generate())
        l.add(getBodyDecl(i));
    return l;
  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:284
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection bcMethods() {
      ASTNode$State state = state();
    Collection bcMethods_value = bcMethods_compute();
    return bcMethods_value;
  }
  /**
   * @apilevel internal
   */
  private Collection bcMethods_compute() {
    ArrayList l = new ArrayList();
    constructors();
    for(int i = 0; i < getNumBodyDecl(); i++)
      if(getBodyDecl(i).isBytecodeMethod() && getBodyDecl(i).generate())
        l.add(getBodyDecl(i));
    return l;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/InnerClasses.jrag:80
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl stringPromotion() {
      ASTNode$State state = state();
    TypeDecl stringPromotion_value = stringPromotion_compute();
    return stringPromotion_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl stringPromotion_compute() {  return typeObject();  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Java2Rewrites.jrag:18
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
  private String jvmName_compute() {
    if(!isNestedType())
      return fullName();
    else if(isAnonymous() || isLocalClass())
      return enclosingType().jvmName() + "$" + uniqueIndex() + name();
    else
      return enclosingType().jvmName() + "$" + name();
  }
  /**
   * @attribute syn
   * @aspect Java2Rewrites
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Java2Rewrites.jrag:60
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String referenceClassFieldName() {
      ASTNode$State state = state();
    String referenceClassFieldName_value = referenceClassFieldName_compute();
    return referenceClassFieldName_value;
  }
  /**
   * @apilevel internal
   */
  private String referenceClassFieldName_compute() {  return "class$" + jvmName().replace('[', '$').replace('.', '$').replace(';', ' ').trim();  }
  /**
   * @attribute syn
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:125
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int variableSize() {
      ASTNode$State state = state();
    int variableSize_value = variableSize_compute();
    return variableSize_value;
  }
  /**
   * @apilevel internal
   */
  private int variableSize_compute() {  return 1;  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
