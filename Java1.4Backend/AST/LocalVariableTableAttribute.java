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
public class LocalVariableTableAttribute extends Attribute {

    public LocalVariableTableAttribute(CodeGeneration gen) {
      super(gen.constantPool(), "LocalVariableTable");
      u2(gen.localVariableTable.size());
      for(Iterator iter = gen.localVariableTable.iterator(); iter.hasNext(); ) {
        CodeGeneration.LocalVariableEntry e = (CodeGeneration.LocalVariableEntry)iter.next();
        u2(e.start_pc);
        u2(e.length);
        u2(e.name_index);
        u2(e.descriptor_index);
        u2(e.index);
      }
    }


}
