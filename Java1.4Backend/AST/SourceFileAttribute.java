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
public class SourceFileAttribute extends Attribute {

    public SourceFileAttribute(ConstantPool p, String sourcefile) {
      super(p, "SourceFile");
      u2(p.addUtf8(sourcefile));
    }


}
