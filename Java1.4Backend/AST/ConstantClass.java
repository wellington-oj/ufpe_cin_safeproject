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
public class ConstantClass extends CPInfo {

    private int name;


    public ConstantClass(int name) {
      this.name = name;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Class);
      out.writeChar(name);
    }


    public String toString() {
      return pos + " ConstantClass: tag " + ConstantPool.CONSTANT_Class + ", name_index: " + name;
    }


}
