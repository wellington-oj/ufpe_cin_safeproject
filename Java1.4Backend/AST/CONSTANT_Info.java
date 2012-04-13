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
 * @ast class
 * @declaredat :0
 */
public class CONSTANT_Info extends java.lang.Object {

    protected BytecodeParser p;


    public CONSTANT_Info(BytecodeParser parser) {
      p = parser;

    }


    public Expr expr() {
      throw new Error("CONSTANT_info.expr() should not be computed for " + getClass().getName());
    }


    public Expr exprAsBoolean() {
      return expr();
    }


}
