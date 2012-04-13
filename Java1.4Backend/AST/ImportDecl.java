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

import util.Recursos;


/**
 * @ast node
 * @declaredat java.ast:7
 */
public abstract class ImportDecl extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    importedTypes_String_values = null;
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
  public ImportDecl clone() throws CloneNotSupportedException {
    ImportDecl node = (ImportDecl)super.clone();
    node.importedTypes_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public ImportDecl() {
    super();
  }
  /**
   * @ast method 
   * @declaredat java.ast:7
   */
  public ImportDecl(Access p0) {
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
   * Setter for Access
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Getter for Access
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public Access getAccess() {
    return (Access)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:18
   */
  public Access getAccessNoTransform() {
    return (Access)getChildNoTransform(0);
  }
  protected java.util.Map importedTypes_String_values;
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:234
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet importedTypes(String name) {
    Object _parameters = name;
    if(importedTypes_String_values == null) importedTypes_String_values = new java.util.HashMap(4);
    if(importedTypes_String_values.containsKey(_parameters)) {
      return (SimpleSet)importedTypes_String_values.get(_parameters);
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    SimpleSet importedTypes_String_value = importedTypes_compute(name);
if(isFinal && num == state().boundariesCrossed) importedTypes_String_values.put(_parameters, importedTypes_String_value);
    return importedTypes_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet importedTypes_compute(String name) {  return SimpleSet.emptySet;  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:263
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isOnDemand() {
      ASTNode$State state = state();
    boolean isOnDemand_value = isOnDemand_compute();
    return isOnDemand_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isOnDemand_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:51
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String typeName() {
      ASTNode$State state = state();
    String typeName_value = typeName_compute();
    return typeName_value;
  }
  /**
   * @apilevel internal
   */
  private String typeName_compute() {
    Access a = getAccess().lastAccess();
    String name = a.isTypeAccess() ? ((TypeAccess)a).nameWithPackage() : "";
    while(a.hasPrevExpr() && a.prevExpr() instanceof Access) {
      Access pred = (Access)a.prevExpr();
      if(pred.isTypeAccess())
        name = ((TypeAccess)pred).nameWithPackage() + "." + name;
      a = pred;
    }
    return name;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:261
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String packageName() {
      ASTNode$State state = state();
    String packageName_value = getParent().Define_String_packageName(this, null);
    return packageName_value;
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:23
   * @apilevel internal
   */
  public boolean Define_boolean_isDest(ASTNode caller, ASTNode child) {
    if(caller == getAccessNoTransform()) {
      return false;
    }
    return getParent().Define_boolean_isDest(this, caller);
  }
  /**
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:32
   * @apilevel internal
   */
  public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
    if(caller == getAccessNoTransform()) {
      return true;
    }
    return getParent().Define_boolean_isSource(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
