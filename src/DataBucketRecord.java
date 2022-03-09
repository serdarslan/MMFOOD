import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class DataBucketRecord {
  BitString Key;	        // Key Bit String
  Path_Instantiation_Node PIN;	// Pointer to the Path Instantiation Node

  DataBucketRecord() {
    Key = new BitString("");
    Vector PIs = new Vector(0,1);
    PIN = new Path_Instantiation_Node(1);
  }

  DataBucketRecord(BitString KeyBS,Vector PIs) { 	// KeyBS is the Key
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    Key = new BitString(KeyBS);
    PIN = new Path_Instantiation_Node(1);
    PIN.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  DataBucketRecord(String KeyBS,Vector PIs) { 	// KeyBS is the Key
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    Key = new BitString(KeyBS);
    PIN = new Path_Instantiation_Node(1);
    PIN.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  protected void finalize() throws Throwable {
  }

  public void PrintDBR() {
    Vector PI;
    System.out.print(this.Key.GetBitString()+" - | PIs:");
    this.PIN.PrintPIN();
    System.out.println();
  }

  public void PrintAllDBRs(int Level) {
    if (this!=null) {
      System.out.println(Database.GetSpaces(Level)+this.Key.GetBitString()+" : Data Bucket Record");
      this.PIN.PrintAllPIN(Level+1);
    }
  }

  public void PrintAllDBRs(int Level,Vector Tree) {
    if (this!=null) {
      Tree.addElement(Database.GetSpaces(Level)+this.Key.GetBitString()+" : Data Bucket Record");
      this.PIN.PrintAllPIN(Level+1,Tree);
    }
  }

/*
  public static void main (String args[]) {
    DataBucketRecord DBR = new DataBucketRecord("0000110110");
    DBR.PrintDataBucketRecord();
  }
*/
}