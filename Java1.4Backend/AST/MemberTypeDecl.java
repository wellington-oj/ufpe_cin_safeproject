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
 * @declaredat java.ast:95
 */
public abstract class MemberTypeDecl extends MemberDecl implements Cloneable {
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
  public MemberTypeDecl clone() throws CloneNotSupportedException {
    MemberTypeDecl node = (MemberTypeDecl)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public MemberTypeDecl() {
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
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:396
   */
  @SuppressWarnings({"unchecked", "cast"})
  public abstract TypeDecl typeDecl();
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:392
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
  private boolean declaresType_compute(String name) {  return typeDecl().name().equals(name);  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:394
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
  private TypeDecl type_compute(String name) {  return declaresType(name) ? typeDecl() : null;  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:246
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
  private boolean isStatic_compute() {  return typeDecl().isStatic();  }
  /**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:761
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
  private boolean addsIndentationLevel_compute() {  return false;  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
