import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Author {
  int OID;
  String Name_Surname;
  String Age;
  String Height;
  double MembershipToClasses[] = new double[18];

  public Author(String name_surname, String age, String height) {
    Database.OID++;
    OID = Database.OID;
    Name_Surname = new String();
    Age = new String();
    Height = new String();
    Name_Surname=name_surname;
    SetAge(age);
    SetHeight(height);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree, Database.Similarity_Matrix_for_Classes[15][i]);
  }

  public void SetAge(String age) {
    Age=Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,age);
  }

  public void SetHeight(String height) {
    Height=Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,height);
  }

  public String GetAge() {
    return(Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,Age));
  }

  public String GetHeight() {
    return(Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,Height));
  }

  public void Print() {
    System.out.println("Name Surname : "+ Name_Surname);
    System.out.println("Age : "+ GetAge());
    System.out.println("Height : "+ GetHeight());
  }

  public void ForwardTraverse(Vector PIs,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Vector New_PI = new Vector(Database.NUMBER_OF_AGGREGATION_CLASS);
    Collecting_Path_Instantiations CPI = new Collecting_Path_Instantiations(Age,Height);
    New_PI.addElement(this);
    CPI.PIs.addElement(New_PI);
    PIs.addElement(CPI);
    DiskAccessP.Set(DiskAccessP.Get()+1);
    DiskAccessS.Set(DiskAccessS.Get()+1);
  }

  public static void main(String[] args) {
    Author A = new Author("Serdar Arslan","45","[Tall,Short,Medium]");
    A.Print();
  }
}
