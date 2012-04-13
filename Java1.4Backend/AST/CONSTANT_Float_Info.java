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
public class CONSTANT_Float_Info extends CONSTANT_Info {

    public float value;



    public CONSTANT_Float_Info(BytecodeParser parser) {
      super(parser);
      value = p.readFloat();
    }



    public String toString() {
      return "FloatInfo: " + Float.toString(value);
    }



    public Expr expr() {
      return Literal.buildFloatLiteral(value);
    }


}
