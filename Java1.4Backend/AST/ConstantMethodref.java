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
public class ConstantMethodref extends CPInfo {

    private int classname;


    private int nameandtype;


    public ConstantMethodref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }



    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Methodref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }


    public String toString() {
      return pos + " ConstantMethodref: tag " + ConstantPool.CONSTANT_Methodref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
