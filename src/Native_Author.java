/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Native_Author extends Author {
  String City;

  public Native_Author(String name_surname, String age, String height, String city) {
    super(name_surname,age,height);
    City = new String(city);

    double Object_Class_Membership_Degree = 1.0;
    for (int i=0;i<18;i++)
      MembershipToClasses[i] = Database.Minimum(Object_Class_Membership_Degree, Database.Similarity_Matrix_for_Classes[16][i]);
  }

  public void Print_Author() {
    super.Print();
    System.out.println(City);
  }

  public static void main(String[] args) {
    Native_Author NA = new Native_Author("Kim Karusmaki","Young","Tall,Short","Ankara");
    NA.Print_Author();
  }
}
