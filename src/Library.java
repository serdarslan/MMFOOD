import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Library {
  int OID;
  String Name;
  String City;
  int Book_Capacity;
  Vector Members;
  double MembershipToClasses[] = new double[18];

  public Library(String name,String city,int book_capacity, Vector members) {
    Database.OID++;
    OID = Database.OID;
    Name = new String(name);
    City = new String(city);
    Book_Capacity = book_capacity;
    Members = new Vector(0,1);
    Database.CopyVector(Members,members);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree , Database.Similarity_Matrix_for_Classes[0][i]);
  }

  public void Print() {
    System.out.println("Name : "+Name);
    System.out.println("City : "+City);
    System.out.println("Book Capacity : "+Book_Capacity);
    for (int i=0;i<Members.size();i++)
      ((Member)Members.elementAt(i)).Print();
  }

  public void ForwardTraverse(Vector PIs, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    int i,j;
    Vector Temp_PIs = new Vector(0,1);
    Collecting_Path_Instantiations CPI;
    Member TempMember=null;

    for (i=0;i<this.Members.size();i++) {
      TempMember=(Member)Members.elementAt(i);
      if (TempMember!=null)
        TempMember.ForwardTraverse(Temp_PIs,DiskAccessP,DiskAccessS);
      else
        continue;

      PathInst.Move_Path_Instantiations_with_Key_Values(Temp_PIs,PIs);
      Temp_PIs.removeAllElements();
    }

    for (i=0;i<PIs.size();i++) {
      Vector New_PI;
      CPI = (Collecting_Path_Instantiations)PIs.elementAt(i);
      for (j=0;j<CPI.PIs.size();j++) {
        New_PI=(Vector)CPI.PIs.elementAt(j);
        New_PI.addElement(this);
      }
    }
    PathInst.Combine_Path_Instantiations(PIs);
    DiskAccessP.Set(DiskAccessP.Get()+1);
    DiskAccessS.Set(DiskAccessS.Get()+1);
  }
}