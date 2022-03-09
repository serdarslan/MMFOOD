import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Engineering_Book extends Book {
  String Subject;
  static String[] Fuzzy_Terms_Subject_Range = {"algorithms","data structures","database"};

  public Engineering_Book(String code,String publisher,String title, Vector authors,String subject) {
    super(code,publisher,title,authors);
    SetSubject(subject);

    double Object_Class_Membership_Degree = Database.Membership_Degree(subject,Database.Fuzzy_Terms_Subject_Domain,this.Fuzzy_Terms_Subject_Range,Database.Similarity_Matrix_Subject_Domain);
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree, Database.Similarity_Matrix_for_Classes[9][i]);
  }

  public void SetSubject(String subject) {
    Subject = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Subject_Domain,subject);
  }

  public String GetSubject() {
    return(Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,Subject));
  }

  public void Print() {
    super.Print();
    System.out.println("Subject : "+ GetSubject());
  }
}