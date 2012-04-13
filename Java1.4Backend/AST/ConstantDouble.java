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
public class ConstantDouble extends CPInfo {

    private double val;


    public ConstantDouble(double val) {
      this.val = val;
    }


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Double);
      out.writeDouble(val);
    }


    public int size() {
      return 2;
    }


    public String toString() {
      return pos + " ConstantDouble: tag " + ConstantPool.CONSTANT_Double + ", bytes: " + val;
    }


}
