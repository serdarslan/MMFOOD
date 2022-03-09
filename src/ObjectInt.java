/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class ObjectInt {
  int Number;

  public ObjectInt() {
    Number=0;
  }

  public ObjectInt(int I) {
    Number=I;
  }

  public int Get() {
    return(Number);
  }

  public void Set(int I) {
    Number=I;
  }

  public void Set(ObjectInt I) {
    Number=I.Get();
  }

/*
  public static void main(String[] args) {
    ObjectInt OI = new ObjectInt();
    System.out.println(OI.Get());
    OI.Set(5);
    System.out.println(OI.Get());
  }
*/
}