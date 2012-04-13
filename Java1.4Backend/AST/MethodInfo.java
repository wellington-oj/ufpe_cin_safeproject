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
public class MethodInfo extends java.lang.Object {

    private BytecodeParser p;


    String name;


    int flags;


    private MethodDescriptor methodDescriptor;


    private Attributes attributes;



    public MethodInfo(BytecodeParser parser) {
      p = parser;
      flags = p.u2();
      if(BytecodeParser.VERBOSE)
        p.print("  Flags: " + Integer.toBinaryString(flags));
      int name_index = p.u2();
      CONSTANT_Info info = p.constantPool[name_index];
      if(info == null || !(info instanceof CONSTANT_Utf8_Info)) {
        System.err.println("Expected CONSTANT_Utf8_Info but found: " + info.getClass().getName());
        //if(info instanceof CONSTANT_Class_Info) {
        //  System.err.println(" found CONSTANT_Class_Info: " + ((CONSTANT_Class_Info)info).name());
        //  name = ((CONSTANT_Class_Info)info).name();
        //}
      } 
      name = ((CONSTANT_Utf8_Info)info).string();
      methodDescriptor = new MethodDescriptor(p, name);
      attributes = new Attributes(p);
    }



    public BodyDecl bodyDecl() {
      if(isConstructor()) {
        return new ConstructorDecl(
            BytecodeParser.modifiers(flags),
            name,
            methodDescriptor.parameterList(),
            attributes.exceptionList(),
            new Opt(),
            new Block()
            );
      }
      else {
        return new MethodDecl(
            BytecodeParser.modifiers(flags),
            methodDescriptor.type(),
            name,
            methodDescriptor.parameterList(),
            attributes.exceptionList(),
            new Opt(new Block())
            );
      }

    }



    private boolean isConstructor() {
      return name.equals("<init>");
    }



    public boolean isSynthetic() {
      return attributes.isSynthetic() || (flags & 0x1000) != 0;
    }


}
