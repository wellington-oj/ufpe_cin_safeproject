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
 * @declaredat java.ast:187
 */
public class Modifiers extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    isPublic_computed = false;
    isPrivate_computed = false;
    isProtected_computed = false;
    isStatic_computed = false;
    isFinal_computed = false;
    isAbstract_computed = false;
    isVolatile_computed = false;
    isTransient_computed = false;
    isStrictfp_computed = false;
    isSynchronized_computed = false;
    isNative_computed = false;
    isSynthetic_computed = false;
    numModifier_String_values = null;
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
  public Modifiers clone() throws CloneNotSupportedException {
    Modifiers node = (Modifiers)super.clone();
    node.isPublic_computed = false;
    node.isPrivate_computed = false;
    node.isProtected_computed = false;
    node.isStatic_computed = false;
    node.isFinal_computed = false;
    node.isAbstract_computed = false;
    node.isVolatile_computed = false;
    node.isTransient_computed = false;
    node.isStrictfp_computed = false;
    node.isSynchronized_computed = false;
    node.isNative_computed = false;
    node.isSynthetic_computed = false;
    node.numModifier_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Modifiers copy() {
      try {
        Modifiers node = (Modifiers)clone();
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
  public Modifiers fullCopy() {
    Modifiers res = (Modifiers)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:312
   */
  public void checkModifiers() {
    super.checkModifiers();
    if(numProtectionModifiers() > 1)
      error("only one public, protected, private allowed");
    if(numModifier("static") > 1)
      error("only one static allowed");
    // 8.4.3.1
    // 8.4.3.2
    // 8.1.1.2
    if(numCompletenessModifiers() > 1)
      error("only one of final, abstract, volatile allowed");
    if(numModifier("synchronized") > 1)
      error("only one synchronized allowed");
    if(numModifier("transient") > 1)
      error("only one transient allowed");
    if(numModifier("native") > 1)
      error("only one native allowed");
    if(numModifier("strictfp") > 1)
      error("only one strictfp allowed");

    if(isPublic() && !mayBePublic())
      error("modifier public not allowed in this context");
    if(isPrivate() && !mayBePrivate())
      error("modifier private not allowed in this context");
    if(isProtected() && !mayBeProtected())
      error("modifier protected not allowed in this context");
    if(isStatic() && !mayBeStatic())
      error("modifier static not allowed in this context");
    if(isFinal() && !mayBeFinal())
      error("modifier final not allowed in this context");
    if(isAbstract() && !mayBeAbstract())
      error("modifier abstract not allowed in this context");
    if(isVolatile() && !mayBeVolatile())
      error("modifier volatile not allowed in this context");
    if(isTransient() && !mayBeTransient())
      error("modifier transient not allowed in this context");
    if(isStrictfp() && !mayBeStrictfp())
      error("modifier strictfp not allowed in this context");
    if(isSynchronized() && !mayBeSynchronized())
      error("modifier synchronized not allowed in this context");
    if(isNative() && !mayBeNative())
      error("modifier native not allowed in this context");
  }
  /**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:435
   */
  public void toString(StringBuffer s) {
    for(int i = 0; i < getNumModifier(); i++) {
      getModifier(i).toString(s);
      s.append(" ");
    }
  }
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:11
   */
  
  public static final int ACC_PUBLIC       = 0x0001;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:12
   */
   // class field method
  public static final int ACC_PRIVATE      = 0x0002;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:13
   */
   //       field method
  public static final int ACC_PROTECTED    = 0x0004;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:14
   */
   //       field method
  public static final int ACC_STATIC       = 0x0008;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:15
   */
   //       field method
  public static final int ACC_FINAL        = 0x0010;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:16
   */
   // class field method
  public static final int ACC_SYNCHRONIZED = 0x0020;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:17
   */
   //             method
  public static final int ACC_SUPER        = 0x0020;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:18
   */
   // class
  public static final int ACC_VOLATILE     = 0x0040;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:19
   */
   //       field
  public static final int ACC_TRANSIENT    = 0x0080;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:20
   */
   //       field
  public static final int ACC_NATIVE       = 0x0100;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:21
   */
   //             method
  public static final int ACC_INTERFACE    = 0x0200;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:22
   */
   // class
  public static final int ACC_ABSTRACT     = 0x0400;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:23
   */
   // class       method
  public static final int ACC_SYNTHETIC    = 0x1000;
  /**
   * @ast method 
   * @aspect Flags
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/Flags.jrag:24
   */
   //       field method
  public static final int ACC_STRICT       = 0x0800;
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public Modifiers() {
    super();

    setChild(new List(), 0);

  }
  /**
   * @ast method 
   * @declaredat java.ast:8
   */
  public Modifiers(List<Modifier> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:14
   */
  protected int numChildren() {
    return 1;
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
   * Setter for ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:5
   */
  public void setModifierList(List<Modifier> list) {
    setChild(list, 0);
  }
  /**
   * @return number of children in ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:12
   */
  public int getNumModifier() {
    return getModifierList().getNumChild();
  }
  /**
   * Getter for child in list ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Modifier getModifier(int i) {
    return (Modifier)getModifierList().getChild(i);
  }
  /**
   * Add element to list ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:27
   */
  public void addModifier(Modifier node) {
    List<Modifier> list = (parent == null || state == null) ? getModifierListNoTransform() : getModifierList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:34
   */
  public void addModifierNoTransform(Modifier node) {
    List<Modifier> list = getModifierListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:42
   */
  public void setModifier(Modifier node, int i) {
    List<Modifier> list = getModifierList();
    list.setChild(node, i);
  }
  /**
   * Getter for Modifier list.
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:50
   */
  public List<Modifier> getModifiers() {
    return getModifierList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:56
   */
  public List<Modifier> getModifiersNoTransform() {
    return getModifierListNoTransform();
  }
  /**
   * Getter for list ModifierList
   * @apilevel high-level
   * @ast method 
   * @declaredat java.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Modifier> getModifierList() {
    List<Modifier> list = (List<Modifier>)getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat java.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Modifier> getModifierListNoTransform() {
    return (List<Modifier>)getChildNoTransform(0);
  }
  /**
   * @apilevel internal
   */
  protected boolean isPublic_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isPublic_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:370
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPublic() {
    if(isPublic_computed) {
      return isPublic_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isPublic_value = isPublic_compute();
if(isFinal && num == state().boundariesCrossed) isPublic_computed = true;
    return isPublic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPublic_compute() {  return numModifier("public") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isPrivate_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isPrivate_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:371
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPrivate() {
    if(isPrivate_computed) {
      return isPrivate_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isPrivate_value = isPrivate_compute();
if(isFinal && num == state().boundariesCrossed) isPrivate_computed = true;
    return isPrivate_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPrivate_compute() {  return numModifier("private") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isProtected_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isProtected_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:372
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isProtected() {
    if(isProtected_computed) {
      return isProtected_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isProtected_value = isProtected_compute();
if(isFinal && num == state().boundariesCrossed) isProtected_computed = true;
    return isProtected_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isProtected_compute() {  return numModifier("protected") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isStatic_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isStatic_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:373
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isStatic() {
    if(isStatic_computed) {
      return isStatic_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isStatic_value = isStatic_compute();
if(isFinal && num == state().boundariesCrossed) isStatic_computed = true;
    return isStatic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isStatic_compute() {  return numModifier("static") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isFinal_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isFinal_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:374
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFinal() {
    if(isFinal_computed) {
      return isFinal_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isFinal_value = isFinal_compute();
if(isFinal && num == state().boundariesCrossed) isFinal_computed = true;
    return isFinal_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFinal_compute() {  return numModifier("final") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isAbstract_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isAbstract_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:375
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isAbstract() {
    if(isAbstract_computed) {
      return isAbstract_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isAbstract_value = isAbstract_compute();
if(isFinal && num == state().boundariesCrossed) isAbstract_computed = true;
    return isAbstract_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isAbstract_compute() {  return numModifier("abstract") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isVolatile_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isVolatile_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:376
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVolatile() {
    if(isVolatile_computed) {
      return isVolatile_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isVolatile_value = isVolatile_compute();
if(isFinal && num == state().boundariesCrossed) isVolatile_computed = true;
    return isVolatile_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVolatile_compute() {  return numModifier("volatile") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isTransient_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isTransient_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:377
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isTransient() {
    if(isTransient_computed) {
      return isTransient_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isTransient_value = isTransient_compute();
if(isFinal && num == state().boundariesCrossed) isTransient_computed = true;
    return isTransient_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isTransient_compute() {  return numModifier("transient") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isStrictfp_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isStrictfp_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:378
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isStrictfp() {
    if(isStrictfp_computed) {
      return isStrictfp_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isStrictfp_value = isStrictfp_compute();
if(isFinal && num == state().boundariesCrossed) isStrictfp_computed = true;
    return isStrictfp_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isStrictfp_compute() {  return numModifier("strictfp") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isSynchronized_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isSynchronized_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:379
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSynchronized() {
    if(isSynchronized_computed) {
      return isSynchronized_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isSynchronized_value = isSynchronized_compute();
if(isFinal && num == state().boundariesCrossed) isSynchronized_computed = true;
    return isSynchronized_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSynchronized_compute() {  return numModifier("synchronized") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isNative_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isNative_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:380
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isNative() {
    if(isNative_computed) {
      return isNative_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isNative_value = isNative_compute();
if(isFinal && num == state().boundariesCrossed) isNative_computed = true;
    return isNative_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isNative_compute() {  return numModifier("native") != 0;  }
  /**
   * @apilevel internal
   */
  protected boolean isSynthetic_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isSynthetic_value;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:382
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSynthetic() {
    if(isSynthetic_computed) {
      return isSynthetic_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    isSynthetic_value = isSynthetic_compute();
if(isFinal && num == state().boundariesCrossed) isSynthetic_computed = true;
    return isSynthetic_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSynthetic_compute() {  return numModifier("synthetic") != 0;  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:384
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int numProtectionModifiers() {
      ASTNode$State state = state();
    int numProtectionModifiers_value = numProtectionModifiers_compute();
    return numProtectionModifiers_value;
  }
  /**
   * @apilevel internal
   */
  private int numProtectionModifiers_compute() {  return numModifier("public") + numModifier("protected") + numModifier("private");  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:387
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int numCompletenessModifiers() {
      ASTNode$State state = state();
    int numCompletenessModifiers_value = numCompletenessModifiers_compute();
    return numCompletenessModifiers_value;
  }
  /**
   * @apilevel internal
   */
  private int numCompletenessModifiers_compute() {  return numModifier("abstract") + numModifier("final") + numModifier("volatile");  }
  protected java.util.Map numModifier_String_values;
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:390
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int numModifier(String name) {
    Object _parameters = name;
    if(numModifier_String_values == null) numModifier_String_values = new java.util.HashMap(4);
    if(numModifier_String_values.containsKey(_parameters)) {
      return ((Integer)numModifier_String_values.get(_parameters)).intValue();
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    int numModifier_String_value = numModifier_compute(name);
if(isFinal && num == state().boundariesCrossed) numModifier_String_values.put(_parameters, Integer.valueOf(numModifier_String_value));
    return numModifier_String_value;
  }
  /**
   * @apilevel internal
   */
  private int numModifier_compute(String name) {
    int n = 0;
    for(int i = 0; i < getNumModifier(); i++) {
      String s = getModifier(i).getID();
      if(s.equals(name))
        n++;
    }
    return n;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:356
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl hostType() {
      ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:358
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBePublic() {
      ASTNode$State state = state();
    boolean mayBePublic_value = getParent().Define_boolean_mayBePublic(this, null);
    return mayBePublic_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:359
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBePrivate() {
      ASTNode$State state = state();
    boolean mayBePrivate_value = getParent().Define_boolean_mayBePrivate(this, null);
    return mayBePrivate_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:360
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeProtected() {
      ASTNode$State state = state();
    boolean mayBeProtected_value = getParent().Define_boolean_mayBeProtected(this, null);
    return mayBeProtected_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:361
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeStatic() {
      ASTNode$State state = state();
    boolean mayBeStatic_value = getParent().Define_boolean_mayBeStatic(this, null);
    return mayBeStatic_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:362
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeFinal() {
      ASTNode$State state = state();
    boolean mayBeFinal_value = getParent().Define_boolean_mayBeFinal(this, null);
    return mayBeFinal_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:363
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeAbstract() {
      ASTNode$State state = state();
    boolean mayBeAbstract_value = getParent().Define_boolean_mayBeAbstract(this, null);
    return mayBeAbstract_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:364
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeVolatile() {
      ASTNode$State state = state();
    boolean mayBeVolatile_value = getParent().Define_boolean_mayBeVolatile(this, null);
    return mayBeVolatile_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:365
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeTransient() {
      ASTNode$State state = state();
    boolean mayBeTransient_value = getParent().Define_boolean_mayBeTransient(this, null);
    return mayBeTransient_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:366
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeStrictfp() {
      ASTNode$State state = state();
    boolean mayBeStrictfp_value = getParent().Define_boolean_mayBeStrictfp(this, null);
    return mayBeStrictfp_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:367
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeSynchronized() {
      ASTNode$State state = state();
    boolean mayBeSynchronized_value = getParent().Define_boolean_mayBeSynchronized(this, null);
    return mayBeSynchronized_value;
  }
  /**
   * @attribute inh
   * @aspect Modifiers
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/Modifiers.jrag:368
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean mayBeNative() {
      ASTNode$State state = state();
    boolean mayBeNative_value = getParent().Define_boolean_mayBeNative(this, null);
    return mayBeNative_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
