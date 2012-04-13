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
public class CONSTANT_Integer_Info extends CONSTANT_Info {

    public int value;



    public CONSTANT_Integer_Info(BytecodeParser parser) {
      super(parser);
      value = p.readInt();
    }



    public String toString() {
      return "IntegerInfo: " + Integer.toString(value);
    }



    public Expr expr() {
      return Literal.buildIntegerLiteral(value);
    }


    public Expr exprAsBoolean() {
      return Literal.buildBooleanLiteral(value == 0);
    }


}
