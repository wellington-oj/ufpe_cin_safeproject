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
public class ConstantInteger extends CPInfo {

    private int val;


    public ConstantInteger(int val) {
      this.val = val;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Integer);
      out.writeInt(val);
    }


    public String toString() {
      return pos + " ConstantInteger: tag " + ConstantPool.CONSTANT_Integer + ", bytes: " + val;
    }


}
