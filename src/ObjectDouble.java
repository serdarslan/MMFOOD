/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class ObjectDouble {
  double Number;

  public ObjectDouble() {
    Number=0.0;
  }

  public ObjectDouble(double I) {
    Number=I;
  }

  public double Get() {
    return(Number);
  }

  public void Set(double I) {
    Number=I;
  }

  public void Set(ObjectDouble I) {
    Number=I.Get();
  }

/*
  public static void main(String[] args) {
    ObjectDouble OD = new ObjectDouble();
    System.out.println(OD.Get());
    OD.Set(5);
    System.out.println(OD.Get());
  }
*/
}