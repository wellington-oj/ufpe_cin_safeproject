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
public class CONSTANT_Methodref_Info extends CONSTANT_Info {

    public int class_index;


    public int name_and_type_index;



    public CONSTANT_Methodref_Info(BytecodeParser parser) {
      super(parser);
      class_index = p.u2();
      name_and_type_index = p.u2();
    }



    public String toString() {
      return "MethodRefInfo: " + class_index + " " + name_and_type_index;
    }


}
