import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class DirectoryRecord {
  Vector PI;
  Path_Instantiation_Node PIN;

  public DirectoryRecord() {
    this.PI = new Vector(0,1);
    this.PIN = null;
  }

  public DirectoryRecord(Vector PI, Path_Instantiation_Node PIN) {
    this.PI = new Vector(0,1);
    for (int i=0;i<PI.size();i++) {
      this.PI.addElement(PI.elementAt(i));
    }
    this.PIN = PIN;
  }

  public DirectoryRecord(DirectoryRecord DR) {
    this.PI = new Vector(0,1);
    for (int i=0;i<DR.PI.size();i++) {
      this.PI.addElement(DR.PI.elementAt(i));
    }
    this.PIN = DR.PIN;
  }

  public void Copy(DirectoryRecord DR) {
    PI.removeAllElements();
    for (int i=0;i<DR.PI.size();i++) {
      this.PI.addElement(DR.PI.elementAt(i));
    }
    this.PIN = DR.PIN;
  }

  public static void main(String[] args) {
//    DirectoryRecord directoryRecord1 = new DirectoryRecord();
  }
}