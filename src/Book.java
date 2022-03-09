import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Book {
  int OID;
  String Code;
  String Publisher;
  String Title;
  Vector Authors;
  double MembershipToClasses[] = new double[18];

  public Book(String code,String publisher,String title, Vector authors) {
    Database.OID++;
    OID = Database.OID;
    Code = new String(code);
    Publisher = new String(publisher);
    Title = new String(title);
    Authors = new Vector(0,1);
    Database.CopyVector(Authors,authors);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree , Database.Similarity_Matrix_for_Classes[7][i]);
  }

  public void Print() {
    System.out.println("Code : "+Code);
    System.out.println("Publisher : "+Publisher);
    System.out.println("Title : "+Title);
    for (int i=0;i<Authors.size();i++)
      ((Author)Authors.elementAt(i)).Print();
  }

  public void ForwardTraverse(Vector PIs, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    int i,j;
    Vector Temp_PIs = new Vector(0,1);
    Collecting_Path_Instantiations CPI;
    Author TempAuthor=null;

    for (i=0;i<this.Authors.size();i++) {
      TempAuthor=(Author)Authors.elementAt(i);
      if (TempAuthor!=null)
        TempAuthor.ForwardTraverse(Temp_PIs,DiskAccessP,DiskAccessS);
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