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
 * A parameter declaration as used in either method parameter lists
 * or as a catch clause parameter.
 * @ast node
 * @declaredat java.ast:88
 */
public class ParameterDeclaration extends ASTNode<ASTNode> implements Cloneable, SimpleSet, Iterator, Variable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    type_computed = false;
    type_value = null;
    localNum_computed = false;
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
  public ParameterDeclaration clone() throws CloneNotSupportedException {
    ParameterDeclaration node = (ParameterDeclaration)super.clone();
    node.type_computed = false;
    node.type_value = null;
    node.localNum_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ParameterDeclaration copy() {
      try {
        ParameterDeclaration node = (ParameterDeclaration)clone();
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
  public ParameterDeclaration fullCopy() {
    ParameterDeclaration res = (ParameterDeclaration)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:117
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:121
   */
  public boolean isSingleton() { return true; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:122
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:125
   */
  
  private ParameterDeclaration iterElem;
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:126
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:127
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:128
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:129
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * @ast method 
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:328
   */
  public void nameCheck() {
    SimpleSet decls = outerScope().lookupVariable(name());
    for(Iterator iter = decls.iterator(); iter.hasNext(); ) {
      Variable var = (Variable)iter.next();
      if(var instanceof VariableDeclaration) {
        VariableDeclaration decl = (VariableDeclaration)var;
	      if(decl.enclosingBodyDecl() == enclosingBodyDecl())
  	      error("duplicate declaration of local variable " + name());
      }
      else if(var instanceof ParameterDeclaration) {
        ParameterDeclaration decl = (ParameterDeclaration)var;
	      if(decl.enclosingBodyDecl() == enclosingBodyDecl())
          error("duplicate declaration of local variable " + name());
      }
    }

    // 8.4.1  
    if(!lookupVariable(name()).contains(this)) {
      error("duplicate declaration of parameter " + name());
    }
  }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:11
   */
  public ParameterDeclaration(Access type, String name) {
    this(new Modifiers(new List()), type, name);
  }
  /**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:14
   */
  public ParameterDeclaration(TypeDecl type, String name) {
    this(new Modifiers(new List()), type.createQualifiedAccess(), name);
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:233
   */
  public void toString(StringBuffer s) {
    getModifiers().toString(s);
    getTypeAccess().toString(s);
    s.append(" " + name());
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ParameterDeclaration() {
    super();


  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public ParameterDeclaration(Modifiers p0, Access p1, String p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @ast method 
   * @declaredat java.ast:12
   */
  public ParameterDeclaration(Modifiers p0, Access p1, beaver.Symbol p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:20
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat java.ast:26
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
   * Setter for TypeAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
  /**
   * Getter for TypeAccess
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Access getTypeAccess() {
    return (Access)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Access getTypeAccessNoTransform() {
    return (Access)getChildNoTransform(1);
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
  /**   * @apilevel internal   * @ast method 
   * @declaredat java.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_ID;
  /**
   * @ast method 
   * @declaredat java.ast:9
   */
  
  public int IDstart;
  /**
   * @ast method 
   * @declaredat java.ast:10
   */
  
  public int IDend;
  /**
   * @ast method 
   * @declaredat java.ast:11
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
   * @declaredat java.ast:22
   */
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:115
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int size() {
      ASTNode$State state = state();
    int size_value = size_compute();
    return size_value;
  }
  /**
   * @apilevel internal
   */
  private int size_compute() {  return 1;  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:116
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isEmpty() {
      ASTNode$State state = state();
    boolean isEmpty_value = isEmpty_compute();
    return isEmpty_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isEmpty_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DataStructures.jrag:120
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean contains(Object o) {
      ASTNode$State state = state();
    boolean contains_Object_value = contains_compute(o);
    return contains_Object_value;
  }
  /**
   * @apilevel internal
   */
  private boolean contains_compute(Object o) {  return this == o;  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:218
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSynthetic() {
      ASTNode$State state = state();
    boolean isSynthetic_value = isSynthetic_compute();
    return isSynthetic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSynthetic_compute() {  return getModifiers().isSynthetic();  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:813
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String dumpString() {
      ASTNode$State state = state();
    String dumpString_value = dumpString_compute();
    return dumpString_value;
  }
  /**
   * @apilevel internal
   */
  private String dumpString_compute() {  return getClass().getName() + " [" + getID() + "]";  }
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:253
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
  private TypeDecl type_compute() {  return getTypeAccess().type();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:47
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isParameter() {
      ASTNode$State state = state();
    boolean isParameter_value = isParameter_compute();
    return isParameter_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isParameter_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:50
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isClassVariable() {
      ASTNode$State state = state();
    boolean isClassVariable_value = isClassVariable_compute();
    return isClassVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isClassVariable_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:51
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isInstanceVariable() {
      ASTNode$State state = state();
    boolean isInstanceVariable_value = isInstanceVariable_compute();
    return isInstanceVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isInstanceVariable_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:55
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isLocalVariable() {
      ASTNode$State state = state();
    boolean isLocalVariable_value = isLocalVariable_compute();
    return isLocalVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isLocalVariable_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:73
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFinal() {
      ASTNode$State state = state();
    boolean isFinal_value = isFinal_compute();
    return isFinal_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFinal_compute() {  return getModifiers().isFinal();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:74
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVolatile() {
      ASTNode$State state = state();
    boolean isVolatile_value = isVolatile_compute();
    return isVolatile_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVolatile_compute() {  return getModifiers().isVolatile();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:75
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isBlank() {
      ASTNode$State state = state();
    boolean isBlank_value = isBlank_compute();
    return isBlank_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBlank_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:76
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isStatic() {
      ASTNode$State state = state();
    boolean isStatic_value = isStatic_compute();
    return isStatic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isStatic_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:78
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String name() {
      ASTNode$State state = state();
    String name_value = name_compute();
    return name_value;
  }
  /**
   * @apilevel internal
   */
  private String name_compute() {  return getID();  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:80
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasInit() {
      ASTNode$State state = state();
    boolean hasInit_value = hasInit_compute();
    return hasInit_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasInit_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:81
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Expr getInit() {
      ASTNode$State state = state();
    Expr getInit_value = getInit_compute();
    return getInit_value;
  }
  /**
   * @apilevel internal
   */
  private Expr getInit_compute() { throw new UnsupportedOperationException(); }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:82
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Constant constant() {
      ASTNode$State state = state();
    Constant constant_value = constant_compute();
    return constant_value;
  }
  /**
   * @apilevel internal
   */
  private Constant constant_compute() { throw new UnsupportedOperationException(); }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:22
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupVariable(String name) {
      ASTNode$State state = state();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);
    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:288
   */
  @SuppressWarnings({"unchecked", "cast"})
  public VariableScope outerScope() {
      ASTNode$State state = state();
    VariableScope outerScope_value = getParent().Define_VariableScope_outerScope(this, null);
    return outerScope_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/NameCheck.jrag:349
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BodyDecl enclosingBodyDecl() {
      ASTNode$State state = state();
    BodyDecl enclosingBodyDecl_value = getParent().Define_BodyDecl_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:586
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl hostType() {
      ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:52
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isMethodParameter() {
      ASTNode$State state = state();
    boolean isMethodParameter_value = getParent().Define_boolean_isMethodParameter(this, null);
    return isMethodParameter_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:53
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isConstructorParameter() {
      ASTNode$State state = state();
    boolean isConstructorParameter_value = getParent().Define_boolean_isConstructorParameter(this, null);
    return isConstructorParameter_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:54
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isExceptionHandlerParameter() {
      ASTNode$State state = state();
    boolean isExceptionHandlerParameter_value = getParent().Define_boolean_isExceptionHandlerParameter(this, null);
    return isExceptionHandlerParameter_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean localNum_computed = false;
  /**
   * @apilevel internal
   */
  protected int localNum_value;
  /**
   * @attribute inh
   * @aspect LocalNum
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/LocalNum.jrag:13
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int localNum() {
    if(localNum_computed) {
      return localNum_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    localNum_value = getParent().Define_int_localNum(this, null);
if(isFinal && num == state().boundariesCrossed) localNum_computed = true;
    return localNum_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:286
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_mayBeFinal(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
