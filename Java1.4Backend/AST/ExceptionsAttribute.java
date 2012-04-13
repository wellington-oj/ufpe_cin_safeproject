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
public class ExceptionsAttribute extends Attribute {

    public ExceptionsAttribute(CodeGeneration gen, ExceptionHolder m) {
      super(gen.constantPool(), "Exceptions");
      u2(m.getNumException());
      for(int i = 0; i < m.getNumException(); i++)
        u2(gen.constantPool().addClass(m.getException(i).type().constantPoolName()));
    }


}
