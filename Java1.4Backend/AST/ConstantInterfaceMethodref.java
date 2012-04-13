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
public class ConstantInterfaceMethodref extends CPInfo {

    private int classname;


    private int nameandtype;


    public ConstantInterfaceMethodref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_InterfaceMethodref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }


    public String toString() {
      return pos + " ConstantInterfaceMethodref: tag " + ConstantPool.CONSTANT_InterfaceMethodref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
