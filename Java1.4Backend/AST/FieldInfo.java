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
public class FieldInfo extends java.lang.Object {

    private BytecodeParser p;


    String name;


    int flags;


    private FieldDescriptor fieldDescriptor;


    private Attributes attributes;



    public FieldInfo(BytecodeParser parser) {
      p = parser;
      flags = p.u2();
      if(BytecodeParser.VERBOSE)
        p.print("Flags: " + flags);
      int name_index = p.u2();
      name = ((CONSTANT_Utf8_Info) p.constantPool[name_index]).string();

      fieldDescriptor = new FieldDescriptor(p, name);
      attributes = new Attributes(p);
    }




    public BodyDecl bodyDecl() {
      FieldDeclaration f = new FieldDeclaration(
          BytecodeParser.modifiers(flags),
          fieldDescriptor.type(),
          name,
          new Opt()
          );
      if(attributes.constantValue() != null)
        if(fieldDescriptor.isBoolean()) {
          f.setInit(attributes.constantValue().exprAsBoolean());
        }
        else {
          f.setInit(attributes.constantValue().expr());
        }
      return f;
    }



    public boolean isSynthetic() {
      return attributes.isSynthetic();
    }


}
