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
public class CONSTANT_Utf8_Info extends CONSTANT_Info {

    public String string;



    public CONSTANT_Utf8_Info(BytecodeParser parser) {
      super(parser);
      string = p.readUTF();
    }



    public String toString() {
      return "Utf8Info: " + string;
    }



    public Expr expr() {
      return Literal.buildStringLiteral(string);
    }



    public String string() {
      return string;
    }


}
