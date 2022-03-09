import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

class DataBucket {
  Vector DBRs;		// Data Bucket Records
  Node Parent;		// Parent Leaf Node
  DataBucket Prev;	// Previous DB
  DataBucket Next;	// Next DB


  DataBucket() {
    DBRs = new Vector(Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET);
    Parent = null;
    Prev = null;
    Next = null;
  }

  protected void finalize() throws Throwable {

  }

  public void Test_DBs() {
    if (this!=null) {
      if (this.Prev!=null)
        if (this.Prev.Next==this)
          System.out.print("Prev DB OK : ");
        else
          System.out.print("Prev DB HATA : ");
      else if (this.Next!=null)
        if (this.Next.Prev==this)
          System.out.print("Next DB OK : ");
        else
          System.out.print("Next DB HATA : ");
      System.out.println();
    }
  }

  public boolean InsertDBR(DataBucketRecord DBRec, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    boolean equal=false;

    if (DBRs.size()>0) {
      int pos=0;
      DataBucketRecord TempDBR;

      while (pos<DBRs.size()) {	// while the last data bucket record is not reached
        TempDBR = (DataBucketRecord)DBRs.elementAt(pos);
        if (TempDBR.Key.GreaterThan(DBRec.Key)) { // if find a data bucket record which is greater than the one to be inserted
          equal=false;
          break;
        }
        else if (TempDBR.Key.EqualTo(DBRec.Key)) { // if find a data bucket record which is equal to the one to be inserted
          equal=true;
          break;
        }
        pos++;	// try next node record
      }

      if (pos<DBRs.size()) {	// if an appropriate node record is found
        if (equal) { // there is such a data bucket record with the same bit string
          Vector Temp_PIs = new Vector(0,1);
          TempDBR = (DataBucketRecord)this.DBRs.elementAt(pos);
          DBRec.PIN.Get_All_PIs(Temp_PIs);
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempDBR.PIN.Insert_PIs(Temp_PIs,DiskAccessP,DiskAccessS);
        }
        else {
          if (DBRs.size()<Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET)
            DBRs.insertElementAt(DBRec,pos);
          else
            return(false);
        }
      }
      else {
        if (DBRs.size()<Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET)
          DBRs.addElement(DBRec);
        else
          return(false);
      }
    }
    else
      DBRs.addElement(DBRec);
    return(true);
  }

  public void MoveAllDataBucketRecordsTo(DataBucket TempDB, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    for (int i=this.DBRs.size()-1;i>=0;i--) {
      TempDB.InsertDBR((DataBucketRecord)TempDB.DBRs.elementAt(i),DiskAccessP,DiskAccessS);
      this.DBRs.removeElementAt(i);
    }
  }

  public void PrintDBR(int index) {
    ((DataBucketRecord)DBRs.elementAt(index)).PrintDBR();
  }

  public void PrintDBRs() {
    for (int i=0;i<DBRs.size();i++)
      PrintDBR(i);
  }

  public void PrintAllDB(int Level) {
    if (this!=null) {
      DataBucketRecord Temp;
      if (DBRs.size()>0) {
        System.out.println(Database.GetSpaces(Level)+"Data Bucket");
        for (int i=0;i<DBRs.size();i++) {
          Temp = (DataBucketRecord) this.DBRs.elementAt(i);
          Temp.PrintAllDBRs(Level+1);
        }
      }
    }
  }

  public void PrintAllDB(int Level,Vector Tree) {
    if (this!=null) {
      DataBucketRecord Temp;
      if (DBRs.size()>0) {
        Tree.addElement(Database.GetSpaces(Level)+"Data Bucket");
        for (int i=0;i<DBRs.size();i++) {
          Temp = (DataBucketRecord) this.DBRs.elementAt(i);
          Temp.PrintAllDBRs(Level+1,Tree);
        }
      }
    }
  }

/*
  public static void main (String args[]) {
    DataBucket DB = new DataBucket();
    DataBucketRecord DBRec1 = new DataBucketRecord("01101");
    DB.InsertDBR(DBRec1);
    DataBucketRecord DBRec2 = new DataBucketRecord("110");
    DB.InsertDBR(DBRec2);
    DataBucketRecord DBRec3 = new DataBucketRecord("0");
    DB.InsertDBR(DBRec3);
    DataBucketRecord DBRec4 = new DataBucketRecord("0110001");
    DB.InsertDBR(DBRec4);
    DataBucketRecord DBRec5 = new DataBucketRecord("11010110");
    DB.InsertDBR(DBRec5);
    DB.PrintDBRs();
  }
*/
}