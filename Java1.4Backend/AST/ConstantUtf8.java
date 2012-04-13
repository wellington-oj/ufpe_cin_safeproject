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
public class ConstantUtf8 extends CPInfo {

    private String name;


    public ConstantUtf8(String name) {
      this.name = name;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Utf8);
      out.writeUTF(name);
    }


    public String toString() {
      return pos + " ConstantUtf8: tag " + ConstantPool.CONSTANT_Utf8 + ", length: " + name.length() + ", bytes: " + name;
    }


}
