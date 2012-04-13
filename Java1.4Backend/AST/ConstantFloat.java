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
public class ConstantFloat extends CPInfo {

    private float val;


    public ConstantFloat(float val) {
      this.val = val;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Float);
      out.writeFloat(val);
    }


    public String toString() {
      return pos + " ConstantFloat: tag " + ConstantPool.CONSTANT_Float + ", bytes: " + val;
    }


}
