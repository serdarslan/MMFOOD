import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Member {
  int OID;
  int Member_ID;
  String Name_Surname;
  int Max_Borrowing;
  Vector Books;
  double MembershipToClasses[] = new double[18];

  public Member(int member_id,String name_surname,int max_borrowing, Vector books) {
    Database.OID++;
    OID = Database.OID;
    Member_ID = member_id;
    Name_Surname = new String(name_surname);
    Max_Borrowing = max_borrowing;
    Books = new Vector(0,1);
    Database.CopyVector(Books,books);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree , Database.Similarity_Matrix_for_Classes[1][i]);
  }

  public void Print() {
    System.out.println("Member ID : "+Member_ID);
    System.out.println("Name Surname : "+Name_Surname);
    System.out.println("Max Borrowing : "+Max_Borrowing);
    for (int i=0;i<Books.size();i++)
      ((Book)Books.elementAt(i)).Print();
  }

  public void ForwardTraverse(Vector PIs, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    int i,j;
    Vector Temp_PIs = new Vector(0,1);
    Collecting_Path_Instantiations CPI;
    Book TempBook=null;

    for (i=0;i<this.Books.size();i++) {
      TempBook=(Book)Books.elementAt(i);
      if (TempBook!=null)
        TempBook.ForwardTraverse(Temp_PIs,DiskAccessP,DiskAccessS);
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