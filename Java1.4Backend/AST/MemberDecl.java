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
 * @declaredat java.ast:74
 */
public abstract class MemberDecl extends BodyDecl implements Cloneable {
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
  public MemberDecl clone() throws CloneNotSupportedException {
    MemberDecl node = (MemberDecl)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:190
   */
  public void checkModifiers() {
    if(!isSynthetic()) {
      super.checkModifiers();
      if(isStatic() && hostType().isInnerClass() && !isConstant())
        error("*** Inner classes may not declare static members, unless they are compile-time constant fields");
    }
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public MemberDecl() {
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
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:220
   */
  @SuppressWarnings({"unchecked", "cast"})
  public abstract boolean isStatic();
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:478
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isConstant() {
      ASTNode$State state = state();
    boolean isConstant_value = isConstant_compute();
    return isConstant_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isConstant_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:212
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
  private boolean isSynthetic_compute() {  return false;  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
