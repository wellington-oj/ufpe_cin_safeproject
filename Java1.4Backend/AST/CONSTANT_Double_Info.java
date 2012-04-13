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
public class CONSTANT_Double_Info extends CONSTANT_Info {

    public double value;



    public CONSTANT_Double_Info(BytecodeParser parser) {
      super(parser);
      value = this.p.readDouble();
    }



    public String toString() {
      return "DoubleInfo: " + Double.toString(value);
    }



    public Expr expr() {
      return Literal.buildDoubleLiteral(value);
    }


}
