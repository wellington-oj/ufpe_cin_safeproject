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
 * @ast interface
 * @declaredat :0
 */
public interface Variable {

     
    public String name();

     
    public TypeDecl type();

     
    public boolean isParameter();

    // 4.5.3
     
    // 4.5.3
    public boolean isClassVariable();

     
    public boolean isInstanceVariable();

     
    public boolean isMethodParameter();

     
    public boolean isConstructorParameter();

     
    public boolean isExceptionHandlerParameter();

     
    public boolean isLocalVariable();

    // 4.5.4
     
    // 4.5.4
    public boolean isFinal();

     
    public boolean isVolatile();


     

    public boolean isBlank();

     
    public boolean isStatic();

     
    public boolean isSynthetic();


     

    public TypeDecl hostType();


     

    public Expr getInit();

     
    public boolean hasInit();


     

    public Constant constant();


     

    public Modifiers getModifiers();
}
