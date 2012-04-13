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
public class ConstantString extends CPInfo {

    private int name;


    public ConstantString(int name) {
      this.name = name;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_String);
      out.writeChar(name);
    }


    public String toString() {
      return pos + " ConstantString: tag " + ConstantPool.CONSTANT_String + ", string_index: " + name;
    }


}
