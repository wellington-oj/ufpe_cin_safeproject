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
public class LineNumberTableAttribute extends Attribute {

    public LineNumberTableAttribute(CodeGeneration gen) {
      super(gen.constantPool(), "LineNumberTable");
      u2(gen.lineNumberTable.size());
      for(Iterator iter = gen.lineNumberTable.iterator(); iter.hasNext(); ) {
        CodeGeneration.LineNumberEntry e = (CodeGeneration.LineNumberEntry)iter.next();
        u2(e.start_pc);
        u2(e.line_number);
      }
    }


}
