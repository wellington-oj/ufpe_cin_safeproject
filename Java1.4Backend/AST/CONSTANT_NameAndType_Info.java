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
public class CONSTANT_NameAndType_Info extends CONSTANT_Info {

    public int name_index;


    public int descriptor_index;



    public CONSTANT_NameAndType_Info(BytecodeParser parser) {
      super(parser);
      name_index = p.u2();
      descriptor_index = p.u2();
    }



    public String toString() {
      return "NameAndTypeInfo: " + name_index + " " + descriptor_index;
    }


}
