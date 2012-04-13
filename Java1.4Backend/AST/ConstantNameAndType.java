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
public class ConstantNameAndType extends CPInfo {

    private int name;


    private int type;


    public ConstantNameAndType(int name, int type) {
      this.name = name;
      this.type = type;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_NameAndType);
      out.writeChar(name);
      out.writeChar(type);
    }


    public String toString() {
      return pos + " NameAndType: tag " + ConstantPool.CONSTANT_NameAndType + ", name_index: " + name + ", descriptor_index: " + type;
    }


}
