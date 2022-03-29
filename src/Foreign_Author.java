/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Foreign_Author extends Author {
  String Nationality;

  public Foreign_Author(String name_surname, String age, String height, String nationality) {
    super(name_surname,age,height);
    Nationality = new String(nationality);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree, Database.Similarity_Matrix_for_Classes[17][i]);
  }

  public void Print() {
    super.Print();
    System.out.println(Nationality);
  }

  public static void main(String[] args) {
    Foreign_Author NA = new Foreign_Author("Ingrad Bergman","Young","Tall,Short","Italian");
    NA.Print();
  }
}
