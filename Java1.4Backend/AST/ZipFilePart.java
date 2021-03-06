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
public class ZipFilePart extends PathPart {

    private HashSet set = new HashSet();


    private ZipFile file;



    public boolean hasPackage(String name) {
      return set.contains(name);
    }



    public ZipFilePart(ZipFile file) {
      this.file = file;
      // process all entries in the zip file
      for (Enumeration e = file.entries() ; e.hasMoreElements() ;) {
        ZipEntry entry = (ZipEntry)e.nextElement();
        String pathName = new File(entry.getName()).getParent();
        if(pathName != null)
          pathName = pathName.replace(File.separatorChar, '.');
        if(!set.contains(pathName)) {
          int pos = 0;
          while(pathName != null && -1 != (pos = pathName.indexOf('.', pos + 1))) {
            String n = pathName.substring(0, pos);
            if(!set.contains(n)) {
              set.add(n);
            }
          }
          set.add(pathName);
        }
        set.add(entry.getName());
      }
    }



    public boolean selectCompilationUnit(String canonicalName) throws IOException {
      String name = canonicalName.replace('.', '/'); // ZipFiles do always use '/' as separator
      name = name + fileSuffix();
      if(set.contains(name)) {
        ZipEntry zipEntry = file.getEntry(name);
        if(zipEntry != null && !zipEntry.isDirectory()) {
          is = file.getInputStream(zipEntry);
          age = zipEntry.getTime();
          pathName = file.getName();
          relativeName = name + fileSuffix();
          fullName = canonicalName;
          return true;
        }
      }
      return false;
    }


}
