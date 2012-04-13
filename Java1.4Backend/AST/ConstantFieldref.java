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
public class ConstantFieldref extends CPInfo {

    private int classname;


    private int nameandtype;


    public ConstantFieldref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Fieldref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }


    public String toString() {
      return pos + " ConstantFieldref: tag " + ConstantPool.CONSTANT_Fieldref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
