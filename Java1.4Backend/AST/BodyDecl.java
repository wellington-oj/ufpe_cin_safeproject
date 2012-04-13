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
 * @declaredat java.ast:69
 */
public abstract class BodyDecl extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    attributes_computed = false;
    attributes_value = null;
    isDAbefore_Variable_values = null;
    isDUbefore_Variable_values = null;
    typeThrowable_computed = false;
    typeThrowable_value = null;
    lookupVariable_String_values = null;
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
  public BodyDecl clone() throws CloneNotSupportedException {
    BodyDecl node = (BodyDecl)super.clone();
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.attributes_computed = false;
    node.attributes_value = null;
    node.isDAbefore_Variable_values = null;
    node.isDUbefore_Variable_values = null;
    node.typeThrowable_computed = false;
    node.typeThrowable_value = null;
    node.lookupVariable_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect BranchTarget
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/BranchTarget.jrag:211
   */
  public void collectFinally(Stmt branchStmt, ArrayList list) {
    // terminate search if body declaration is reached
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:242
   */
  public void generateMethod(DataOutputStream out, ConstantPool cp) throws IOException {
  }
  /**
   * @ast method 
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:261
   */
  public void touchMethod(ConstantPool cp) {
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public BodyDecl() {
    super();


  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:10
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:16
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:243
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
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:707
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
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:391
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean declaresType(String name) {
      ASTNode$State state = state();
    boolean declaresType_String_value = declaresType_compute(name);
    return declaresType_String_value;
  }
  /**
   * @apilevel internal
   */
  private boolean declaresType_compute(String name) {  return false;  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:393
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl type(String name) {
      ASTNode$State state = state();
    TypeDecl type_String_value = type_compute(name);
    return type_String_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl type_compute(String name) {  return null;  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:760
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean addsIndentationLevel() {
      ASTNode$State state = state();
    boolean addsIndentationLevel_value = addsIndentationLevel_compute();
    return addsIndentationLevel_value;
  }
  /**
   * @apilevel internal
   */
  private boolean addsIndentationLevel_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:271
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVoid() {
      ASTNode$State state = state();
    boolean isVoid_value = isVoid_compute();
    return isVoid_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVoid_compute() {  return false;  }
  /**
   * @apilevel internal
   */
  protected boolean attributes_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection attributes_value;
  /**
   * @attribute syn
   * @aspect Attributes
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Attributes.jrag:182
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection attributes() {
    if(attributes_computed) {
      return attributes_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    attributes_value = attributes_compute();
if(isFinal && num == state().boundariesCrossed) attributes_computed = true;
    return attributes_value;
  }
  /**
   * @apilevel internal
   */
  private Collection attributes_compute() {  return new ArrayList();  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:293
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBytecodeField() {
      ASTNode$State state = state();
    boolean isBytecodeField_value = isBytecodeField_compute();
    return isBytecodeField_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBytecodeField_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:296
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBytecodeMethod() {
      ASTNode$State state = state();
    boolean isBytecodeMethod_value = isBytecodeMethod_compute();
    return isBytecodeMethod_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBytecodeMethod_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/GenerateClassfile.jrag:300
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean generate() {
      ASTNode$State state = state();
    boolean generate_value = generate_compute();
    return generate_value;
  }
  /**
   * @apilevel internal
   */
  private boolean generate_compute() {  return true;  }
  protected java.util.Map isDAbefore_Variable_values;
  /**
   * @attribute inh
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:242
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAbefore(Variable v) {
    Object _parameters = v;
    if(isDAbefore_Variable_values == null) isDAbefore_Variable_values = new java.util.HashMap(4);
    if(isDAbefore_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAbefore_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDAbefore_Variable_value = getParent().Define_boolean_isDAbefore(this, null, v);
if(isFinal && num == state().boundariesCrossed) isDAbefore_Variable_values.put(_parameters, Boolean.valueOf(isDAbefore_Variable_value));
    return isDAbefore_Variable_value;
  }
  protected java.util.Map isDUbefore_Variable_values;
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:706
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUbefore(Variable v) {
    Object _parameters = v;
    if(isDUbefore_Variable_values == null) isDUbefore_Variable_values = new java.util.HashMap(4);
    if(isDUbefore_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUbefore_Variable_values.get(_parameters)).booleanValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDUbefore_Variable_value = getParent().Define_boolean_isDUbefore(this, null, v);
if(isFinal && num == state().boundariesCrossed) isDUbefore_Variable_values.put(_parameters, Boolean.valueOf(isDUbefore_Variable_value));
    return isDUbefore_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeThrowable_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeThrowable_value;
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:22
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeThrowable() {
    if(typeThrowable_computed) {
      return typeThrowable_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    typeThrowable_value = getParent().Define_TypeDecl_typeThrowable(this, null);
if(isFinal && num == state().boundariesCrossed) typeThrowable_computed = true;
    return typeThrowable_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:25
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection lookupMethod(String name) {
      ASTNode$State state = state();
    Collection lookupMethod_String_value = getParent().Define_Collection_lookupMethod(this, null, name);
    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:97
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl lookupType(String packageName, String typeName) {
      ASTNode$State state = state();
    TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:173
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupType(String name) {
      ASTNode$State state = state();
    SimpleSet lookupType_String_value = getParent().Define_SimpleSet_lookupType(this, null, name);
    return lookupType_String_value;
  }
  protected java.util.Map lookupVariable_String_values;
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:15
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupVariable(String name) {
    Object _parameters = name;
    if(lookupVariable_String_values == null) lookupVariable_String_values = new java.util.HashMap(4);
    if(lookupVariable_String_values.containsKey(_parameters)) {
      return (SimpleSet)lookupVariable_String_values.get(_parameters);
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);
if(isFinal && num == state().boundariesCrossed) lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect SyntacticClassification
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:21
   */
  @SuppressWarnings({"unchecked", "cast"})
  public NameType nameType() {
      ASTNode$State state = state();
    NameType nameType_value = getParent().Define_NameType_nameType(this, null);
    return nameType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:567
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String hostPackage() {
      ASTNode$State state = state();
    String hostPackage_value = getParent().Define_String_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:582
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl hostType() {
      ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
    return hostType_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:353
   * @apilevel internal
   */
  public String Define_String_typeDeclIndent(ASTNode caller, ASTNode child) {
    if(true) {
      int childIndex = this.getIndexOfChild(caller);
      return indent();
    }
    return getParent().Define_String_typeDeclIndent(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:515
   * @apilevel internal
   */
  public BodyDecl Define_BodyDecl_enclosingBodyDecl(ASTNode caller, ASTNode child) {
    if(true) {
      int childIndex = this.getIndexOfChild(caller);
      return this;
    }
    return getParent().Define_BodyDecl_enclosingBodyDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
