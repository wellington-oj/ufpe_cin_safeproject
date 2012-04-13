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
public class CONSTANT_Long_Info extends CONSTANT_Info {

    public long value;



    public CONSTANT_Long_Info(BytecodeParser parser) {
      super(parser);
      value = p.readLong();
    }



    public String toString() {
      return "LongInfo: " + Long.toString(value);
    }



    public Expr expr() {
      return Literal.buildLongLiteral(value);
    }


}
