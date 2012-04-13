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
public class Attribute extends java.lang.Object {

    int attribute_name_index;


    ByteArrayOutputStream buf = new ByteArrayOutputStream();


    DataOutputStream output = new DataOutputStream(buf);



    public Attribute(ConstantPool cp, String name) {
      attribute_name_index = cp.addUtf8(name);
    }



    public void emit(DataOutputStream out) throws IOException {
      out.writeChar(attribute_name_index);
      out.writeInt(buf.size());
      buf.writeTo(out);
      output.close();
      buf.close();
    }


    public int size() { return buf.size(); }


    public void u1(int v) { try { output.writeByte(v); } catch(IOException e) {} }


    public void u2(int v) { try { output.writeChar(v); } catch(IOException e) {} }


    public void u4(int v) { try { output.writeInt(v); } catch(IOException e) {} }


    public void append(byte[] data) { try { output.write(data, 0, data.length); } catch(IOException e) {} }


    public void append(Attribute attribute) { try { attribute.emit(output); } catch(IOException e) {} }


}
