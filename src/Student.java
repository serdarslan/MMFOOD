import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Student extends Member {
  String Education;
  static String[] Fuzzy_Terms_Education_Range = {"phd","ms","under graduate"};

  public Student(int member_id,String name_surname,int max_borrowing, Vector books,String education) {
    super(member_id,name_surname,max_borrowing,books);
    SetEducation(education);

    double Object_Class_Membership_Degree = Database.Membership_Degree(education,Database.Fuzzy_Terms_Education_Domain,this.Fuzzy_Terms_Education_Range,Database.Similarity_Matrix_Education_Domain);
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree , Database.Similarity_Matrix_for_Classes[6][i]);
  }

  public void SetEducation(String education) {
    Education = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Education_Domain,education);
  }

  public String GetEducation() {
    return(Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,Education));
  }

  public void Print() {
    super.Print();
    System.out.println("Education : "+ GetEducation());
  }
}