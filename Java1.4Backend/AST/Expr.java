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
 * @declaredat java.ast:101
 */
public abstract class Expr extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
    false_label_computed = false;
    true_label_computed = false;
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
  public Expr clone() throws CloneNotSupportedException {
    Expr node = (Expr)super.clone();
    node.false_label_computed = false;
    node.true_label_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
  /**
   * @ast method 
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:373
   */
  public SimpleSet keepAccessibleTypes(SimpleSet oldSet) {
    SimpleSet newSet = SimpleSet.emptySet;
    TypeDecl hostType = hostType();
    for(Iterator iter = oldSet.iterator(); iter.hasNext(); ) {
      TypeDecl t = (TypeDecl)iter.next();
      if((hostType != null && t.accessibleFrom(hostType)) || (hostType == null && t.accessibleFromPackage(hostPackage())))
        newSet = newSet.add(t);
    }
    return newSet;
  }
  /**
   * @ast method 
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:166
   */
  public SimpleSet keepAccessibleFields(SimpleSet oldSet) {
    SimpleSet newSet = SimpleSet.emptySet;
    for(Iterator iter = oldSet.iterator(); iter.hasNext(); ) {
      Variable v = (Variable)iter.next();
      if(v instanceof FieldDeclaration) {
        FieldDeclaration f = (FieldDeclaration)v;
        if(mayAccess(f))
          newSet = newSet.add(f);
      }
    }
    return newSet;
  }
  /**
   * @ast method 
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:189
   */
  public boolean mayAccess(FieldDeclaration f) {
    if(f.isPublic()) 
      return true;
    else if(f.isProtected()) {
      if(f.hostPackage().equals(hostPackage()))
        return true;
      TypeDecl C = f.hostType();
      TypeDecl S = hostType().subclassWithinBody(C);
      TypeDecl Q = type();
      if(S == null)
        return false;
      if(f.isInstanceVariable() && !isSuperAccess())
        return Q.instanceOf(S);
      return true;
    }
    else if(f.isPrivate())
      return f.hostType().topLevelType() == hostType().topLevelType();
    else
      return f.hostPackage().equals(hostType().hostPackage());
  }
  /**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:106
   */
  public Dot qualifiesAccess(Access access) {
    Dot dot = new Dot(this, access);
    dot.setStart(this.getStart());
    dot.setEnd(access.getEnd());
    return dot;
  }
  /**
   * @ast method 
   * @aspect CodeGeneration
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:696
   */
  public void emitStore(CodeGeneration gen) { error("emitStore called with " + getClass().getName()); }
  /**
   * @ast method 
   * @aspect CodeGenerationBinaryOperations
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CodeGeneration.jrag:1016
   */
  void emitOperation(CodeGeneration gen) {error();}
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:211
   */
  protected boolean needsPush() {
    ASTNode n = getParent();
    while(n instanceof ParExpr)
      n = n.getParent();
    return !(n instanceof ExprStmt);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:350
   */
  public void createAssignSimpleLoadDest(CodeGeneration gen) {
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:364
   */
  public void createPushAssignmentResult(CodeGeneration gen) {
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:382
   */
  public void createAssignLoadDest(CodeGeneration gen) {
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1028
   */
  protected void emitBooleanCondition(CodeGeneration gen) {
    emitEvalBranch(gen);
    int end_label = hostType().constantPool().newLabel();
    gen.addLabel(false_label());
    BooleanLiteral.push(gen, false);
    gen.emitGoto(end_label);
    gen.changeStackDepth(-1); // discard false from stack depth computation
    gen.addLabel(true_label());
    BooleanLiteral.push(gen, true);
    gen.addLabel(end_label);
  }
  /**
   * @ast method 
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1066
   */
  public void emitEvalBranch(CodeGeneration gen) {
    if(isTrue())
      gen.emitGoto(true_label());
    else if(isFalse())
      gen.emitGoto(false_label());
    else {
      createBCode(gen);
      gen.emitCompare(Bytecode.IFNE, true_label());
      gen.emitGoto(false_label());
      //gen.emitCompare(Bytecode.IFEQ, false_label());
      //gen.emitGoto(true_label());
    }
  }
  /**
   * @ast method 
   * @declaredat java.ast:1
   */
  public Expr() {
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
   * @aspect TypeAnalysis
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:276
   */
  @SuppressWarnings({"unchecked", "cast"})
  public abstract TypeDecl type();
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:98
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
  private Constant constant_compute() {
    throw new UnsupportedOperationException("ConstantExpression operation constant" +
      " not supported for type " + getClass().getName()); 
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:241
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPositive() {
      ASTNode$State state = state();
    boolean isPositive_value = isPositive_compute();
    return isPositive_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPositive_compute() {  return false;  }
  /* 
   * representableIn(T) is true if and only if the the expression is a 
   * compile-time constant of type byte, char, short or int, and the value  
   * of the expression can be represented (by an expression) in the type T
   * where T must be byte, char or short.
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:454
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean representableIn(TypeDecl t) {
      ASTNode$State state = state();
    boolean representableIn_TypeDecl_value = representableIn_compute(t);
    return representableIn_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean representableIn_compute(TypeDecl t) {	
  	if (!type().isByte() && !type().isChar() && !type().isShort() && !type().isInt()) {
  		return false;
  	}
  	if (t.isByte())
  		return constant().intValue() >= Byte.MIN_VALUE && constant().intValue() <= Byte.MAX_VALUE;
  	if (t.isChar())
  		return constant().intValue() >= Character.MIN_VALUE && constant().intValue() <= Character.MAX_VALUE;
  	if (t.isShort())
  		return constant().intValue() >= Short.MIN_VALUE && constant().intValue() <= Short.MAX_VALUE;
    if(t.isInt()) 
      return constant().intValue() >= Integer.MIN_VALUE && constant().intValue() <= Integer.MAX_VALUE;
	  return false;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:482
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
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:511
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isTrue() {
      ASTNode$State state = state();
    boolean isTrue_value = isTrue_compute();
    return isTrue_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isTrue_compute() {  return isConstant() && type() instanceof BooleanType && constant().booleanValue();  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ConstantExpression.jrag:512
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFalse() {
      ASTNode$State state = state();
    boolean isFalse_value = isFalse_compute();
    return isFalse_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFalse_compute() {  return isConstant() && type() instanceof BooleanType && !constant().booleanValue();  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:58
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Variable varDecl() {
      ASTNode$State state = state();
    Variable varDecl_value = varDecl_compute();
    return varDecl_value;
  }
  /**
   * @apilevel internal
   */
  private Variable varDecl_compute() {  return null;  }
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:338
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafterFalse(Variable v) {
      ASTNode$State state = state();
    boolean isDAafterFalse_Variable_value = isDAafterFalse_compute(v);
    return isDAafterFalse_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafterFalse_compute(Variable v) {  return isTrue() || isDAbefore(v);  }
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:340
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafterTrue(Variable v) {
      ASTNode$State state = state();
    boolean isDAafterTrue_Variable_value = isDAafterTrue_compute(v);
    return isDAafterTrue_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafterTrue_compute(Variable v) {  return isFalse() || isDAbefore(v);  }
  /**
   * @attribute syn
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:343
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafter(Variable v) {
      ASTNode$State state = state();
    boolean isDAafter_Variable_value = isDAafter_compute(v);
    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {  return (isDAafterFalse(v) && isDAafterTrue(v)) || isDAbefore(v);  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:780
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafterFalse(Variable v) {
      ASTNode$State state = state();
    boolean isDUafterFalse_Variable_value = isDUafterFalse_compute(v);
    return isDUafterFalse_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafterFalse_compute(Variable v) {
    if(isTrue())
      return true;
    return isDUbefore(v);
  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:786
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafterTrue(Variable v) {
      ASTNode$State state = state();
    boolean isDUafterTrue_Variable_value = isDUafterTrue_compute(v);
    return isDUafterTrue_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafterTrue_compute(Variable v) {
    if(isFalse())
      return true;
    return isDUbefore(v);
  }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:796
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafter(Variable v) {
      ASTNode$State state = state();
    boolean isDUafter_Variable_value = isDUafter_compute(v);
    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {  return (isDUafterFalse(v) && isDUafterTrue(v)) || isDUbefore(v);  }
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:32
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet mostSpecificConstructor(Collection constructors) {
      ASTNode$State state = state();
    SimpleSet mostSpecificConstructor_Collection_value = mostSpecificConstructor_compute(constructors);
    return mostSpecificConstructor_Collection_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet mostSpecificConstructor_compute(Collection constructors) {
    SimpleSet maxSpecific = SimpleSet.emptySet;
    for(Iterator iter = constructors.iterator(); iter.hasNext(); ) {
      ConstructorDecl decl = (ConstructorDecl)iter.next();
      if(applicableAndAccessible(decl)) {
        if(maxSpecific.isEmpty())
          maxSpecific = maxSpecific.add(decl);
        else {
          if(decl.moreSpecificThan((ConstructorDecl)maxSpecific.iterator().next()))
            maxSpecific = SimpleSet.emptySet.add(decl);
          else if(!((ConstructorDecl)maxSpecific.iterator().next()).moreSpecificThan(decl))
            maxSpecific = maxSpecific.add(decl);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupConstructor.jrag:50
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean applicableAndAccessible(ConstructorDecl decl) {
      ASTNode$State state = state();
    boolean applicableAndAccessible_ConstructorDecl_value = applicableAndAccessible_compute(decl);
    return applicableAndAccessible_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean applicableAndAccessible_compute(ConstructorDecl decl) {  return false;  }
  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:83
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasQualifiedPackage(String packageName) {
      ASTNode$State state = state();
    boolean hasQualifiedPackage_String_value = hasQualifiedPackage_compute(packageName);
    return hasQualifiedPackage_String_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasQualifiedPackage_compute(String packageName) {  return false;  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:342
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet qualifiedLookupType(String name) {
      ASTNode$State state = state();
    SimpleSet qualifiedLookupType_String_value = qualifiedLookupType_compute(name);
    return qualifiedLookupType_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet qualifiedLookupType_compute(String name) {  return keepAccessibleTypes(type().memberTypes(name));  }
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:148
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet qualifiedLookupVariable(String name) {
      ASTNode$State state = state();
    SimpleSet qualifiedLookupVariable_String_value = qualifiedLookupVariable_compute(name);
    return qualifiedLookupVariable_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet qualifiedLookupVariable_compute(String name) {
    if(type().accessibleFrom(hostType()))
      return keepAccessibleFields(type().memberFields(name));
    return SimpleSet.emptySet;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:25
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String packageName() {
      ASTNode$State state = state();
    String packageName_value = packageName_compute();
    return packageName_value;
  }
  /**
   * @apilevel internal
   */
  private String packageName_compute() {  return "";  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:62
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
  private String typeName_compute() {  return "";  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:13
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isTypeAccess() {
      ASTNode$State state = state();
    boolean isTypeAccess_value = isTypeAccess_compute();
    return isTypeAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isTypeAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:17
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isMethodAccess() {
      ASTNode$State state = state();
    boolean isMethodAccess_value = isMethodAccess_compute();
    return isMethodAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isMethodAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:21
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isFieldAccess() {
      ASTNode$State state = state();
    boolean isFieldAccess_value = isFieldAccess_compute();
    return isFieldAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFieldAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:25
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSuperAccess() {
      ASTNode$State state = state();
    boolean isSuperAccess_value = isSuperAccess_compute();
    return isSuperAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSuperAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:31
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isThisAccess() {
      ASTNode$State state = state();
    boolean isThisAccess_value = isThisAccess_compute();
    return isThisAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isThisAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isPackageAccess() {
      ASTNode$State state = state();
    boolean isPackageAccess_value = isPackageAccess_compute();
    return isPackageAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isPackageAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:41
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isArrayAccess() {
      ASTNode$State state = state();
    boolean isArrayAccess_value = isArrayAccess_compute();
    return isArrayAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isArrayAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:45
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isClassAccess() {
      ASTNode$State state = state();
    boolean isClassAccess_value = isClassAccess_compute();
    return isClassAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isClassAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:49
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSuperConstructorAccess() {
      ASTNode$State state = state();
    boolean isSuperConstructorAccess_value = isSuperConstructorAccess_compute();
    return isSuperConstructorAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isSuperConstructorAccess_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:55
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isLeftChildOfDot() {
      ASTNode$State state = state();
    boolean isLeftChildOfDot_value = isLeftChildOfDot_compute();
    return isLeftChildOfDot_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isLeftChildOfDot_compute() {  return hasParentDot() && parentDot().getLeft() == this;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:56
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isRightChildOfDot() {
      ASTNode$State state = state();
    boolean isRightChildOfDot_value = isRightChildOfDot_compute();
    return isRightChildOfDot_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isRightChildOfDot_compute() {  return hasParentDot() && parentDot().getRight() == this;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:69
   */
  @SuppressWarnings({"unchecked", "cast"})
  public AbstractDot parentDot() {
      ASTNode$State state = state();
    AbstractDot parentDot_value = parentDot_compute();
    return parentDot_value;
  }
  /**
   * @apilevel internal
   */
  private AbstractDot parentDot_compute() {  return getParent() instanceof AbstractDot ? (AbstractDot)getParent() : null;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:70
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasParentDot() {
      ASTNode$State state = state();
    boolean hasParentDot_value = hasParentDot_compute();
    return hasParentDot_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasParentDot_compute() {  return parentDot() != null;  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Access nextAccess() {
      ASTNode$State state = state();
    Access nextAccess_value = nextAccess_compute();
    return nextAccess_value;
  }
  /**
   * @apilevel internal
   */
  private Access nextAccess_compute() {  return parentDot().nextAccess();  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:73
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasNextAccess() {
      ASTNode$State state = state();
    boolean hasNextAccess_value = hasNextAccess_compute();
    return hasNextAccess_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasNextAccess_compute() {  return isLeftChildOfDot();  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:504
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Stmt enclosingStmt() {
      ASTNode$State state = state();
    Stmt enclosingStmt_value = enclosingStmt_compute();
    return enclosingStmt_value;
  }
  /**
   * @apilevel internal
   */
  private Stmt enclosingStmt_compute() {
    ASTNode node = this;
    while(node != null && !(node instanceof Stmt))
      node = node.getParent();
    return (Stmt)node;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeCheck.jrag:15
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVariable() {
      ASTNode$State state = state();
    boolean isVariable_value = isVariable_compute();
    return isVariable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVariable_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:20
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isUnknown() {
      ASTNode$State state = state();
    boolean isUnknown_value = isUnknown_compute();
    return isUnknown_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isUnknown_compute() {  return type().isUnknown();  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:150
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean staticContextQualifier() {
      ASTNode$State state = state();
    boolean staticContextQualifier_value = staticContextQualifier_compute();
    return staticContextQualifier_value;
  }
  /**
   * @apilevel internal
   */
  private boolean staticContextQualifier_compute() {  return false;  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:219
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean needsPop() {
      ASTNode$State state = state();
    boolean needsPop_value = needsPop_compute();
    return needsPop_value;
  }
  /**
   * @apilevel internal
   */
  private boolean needsPop_compute() {  return true;  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:231
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isVarAccessWithAccessor() {
      ASTNode$State state = state();
    boolean isVarAccessWithAccessor_value = isVarAccessWithAccessor_compute();
    return isVarAccessWithAccessor_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isVarAccessWithAccessor_compute() {  return false;  }
  /**
   * @apilevel internal
   */
  protected boolean false_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int false_label_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:939
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int false_label() {
    if(false_label_computed) {
      return false_label_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    false_label_value = false_label_compute();
if(isFinal && num == state().boundariesCrossed) false_label_computed = true;
    return false_label_value;
  }
  /**
   * @apilevel internal
   */
  private int false_label_compute() {  return getParent().definesLabel() ? condition_false_label() : hostType().constantPool().newLabel();  }
  /**
   * @apilevel internal
   */
  protected boolean true_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int true_label_value;
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:941
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int true_label() {
    if(true_label_computed) {
      return true_label_value;
    }
      ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    true_label_value = true_label_compute();
if(isFinal && num == state().boundariesCrossed) true_label_computed = true;
    return true_label_value;
  }
  /**
   * @apilevel internal
   */
  private int true_label_compute() {  return getParent().definesLabel() ? condition_true_label() : hostType().constantPool().newLabel();  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1005
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean canBeTrue() {
      ASTNode$State state = state();
    boolean canBeTrue_value = canBeTrue_compute();
    return canBeTrue_value;
  }
  /**
   * @apilevel internal
   */
  private boolean canBeTrue_compute() {  return !isFalse();  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:1015
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean canBeFalse() {
      ASTNode$State state = state();
    boolean canBeFalse_value = canBeFalse_compute();
    return canBeFalse_value;
  }
  /**
   * @apilevel internal
   */
  private boolean canBeFalse_compute() {  return !isTrue();  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:15
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDest() {
      ASTNode$State state = state();
    boolean isDest_value = getParent().Define_boolean_isDest(this, null);
    return isDest_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:25
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isSource() {
      ASTNode$State state = state();
    boolean isSource_value = getParent().Define_boolean_isSource(this, null);
    return isSource_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:49
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isIncOrDec() {
      ASTNode$State state = state();
    boolean isIncOrDec_value = getParent().Define_boolean_isIncOrDec(this, null);
    return isIncOrDec_value;
  }
  /**
   * @attribute inh
   * @aspect DA
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:234
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDAbefore(Variable v) {
      ASTNode$State state = state();
    boolean isDAbefore_Variable_value = getParent().Define_boolean_isDAbefore(this, null, v);
    return isDAbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:692
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean isDUbefore(Variable v) {
      ASTNode$State state = state();
    boolean isDUbefore_Variable_value = getParent().Define_boolean_isDUbefore(this, null, v);
    return isDUbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupMethod.jrag:23
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Collection lookupMethod(String name) {
      ASTNode$State state = state();
    Collection lookupMethod_String_value = getParent().Define_Collection_lookupMethod(this, null, name);
    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:49
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeBoolean() {
      ASTNode$State state = state();
    TypeDecl typeBoolean_value = getParent().Define_TypeDecl_typeBoolean(this, null);
    return typeBoolean_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:50
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeByte() {
      ASTNode$State state = state();
    TypeDecl typeByte_value = getParent().Define_TypeDecl_typeByte(this, null);
    return typeByte_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:51
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeShort() {
      ASTNode$State state = state();
    TypeDecl typeShort_value = getParent().Define_TypeDecl_typeShort(this, null);
    return typeShort_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:52
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeChar() {
      ASTNode$State state = state();
    TypeDecl typeChar_value = getParent().Define_TypeDecl_typeChar(this, null);
    return typeChar_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:53
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeInt() {
      ASTNode$State state = state();
    TypeDecl typeInt_value = getParent().Define_TypeDecl_typeInt(this, null);
    return typeInt_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:54
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeLong() {
      ASTNode$State state = state();
    TypeDecl typeLong_value = getParent().Define_TypeDecl_typeLong(this, null);
    return typeLong_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:55
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeFloat() {
      ASTNode$State state = state();
    TypeDecl typeFloat_value = getParent().Define_TypeDecl_typeFloat(this, null);
    return typeFloat_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:56
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeDouble() {
      ASTNode$State state = state();
    TypeDecl typeDouble_value = getParent().Define_TypeDecl_typeDouble(this, null);
    return typeDouble_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:57
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeString() {
      ASTNode$State state = state();
    TypeDecl typeString_value = getParent().Define_TypeDecl_typeString(this, null);
    return typeString_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:58
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeVoid() {
      ASTNode$State state = state();
    TypeDecl typeVoid_value = getParent().Define_TypeDecl_typeVoid(this, null);
    return typeVoid_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:59
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeNull() {
      ASTNode$State state = state();
    TypeDecl typeNull_value = getParent().Define_TypeDecl_typeNull(this, null);
    return typeNull_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl unknownType() {
      ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:86
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean hasPackage(String packageName) {
      ASTNode$State state = state();
    boolean hasPackage_String_value = getParent().Define_boolean_hasPackage(this, null, packageName);
    return hasPackage_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:95
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupType.jrag:176
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupType(String name) {
      ASTNode$State state = state();
    SimpleSet lookupType_String_value = getParent().Define_SimpleSet_lookupType(this, null, name);
    return lookupType_String_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/LookupVariable.jrag:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupVariable(String name) {
      ASTNode$State state = state();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);
    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect SyntacticClassification
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:20
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:511
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:568
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
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:583
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl hostType() {
      ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:11
   */
  @SuppressWarnings({"unchecked", "cast"})
  public String methodHost() {
      ASTNode$State state = state();
    String methodHost_value = getParent().Define_String_methodHost(this, null);
    return methodHost_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/uoji/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:134
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean inStaticContext() {
      ASTNode$State state = state();
    boolean inStaticContext_value = getParent().Define_boolean_inStaticContext(this, null);
    return inStaticContext_value;
  }
  /**
   * @attribute inh
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:971
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int condition_false_label() {
      ASTNode$State state = state();
    int condition_false_label_value = getParent().Define_int_condition_false_label(this, null);
    return condition_false_label_value;
  }
  /**
   * @attribute inh
   * @aspect CreateBCode
   * @declaredat /home/uoji/JastAddJ/Java1.4Backend/CreateBCode.jrag:975
   */
  @SuppressWarnings({"unchecked", "cast"})
  public int condition_true_label() {
      ASTNode$State state = state();
    int condition_true_label_value = getParent().Define_int_condition_true_label(this, null);
    return condition_true_label_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
