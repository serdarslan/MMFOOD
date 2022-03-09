import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Condition_Element {
  String Attribute;
  String Field1;
  String Field2;
  Vector ResultBitStrings;

  public Condition_Element(String Attr, String F1, String F2) {
    Attribute = new String(Attr);
    Field1 = new String(F1);
    Field2 = new String(F2);
    ResultBitStrings = new Vector(0,1);
  }

  public void Print_ResultBitStrings() {
    Result_BitString ResultBitString;
    for (int i=0; i<ResultBitStrings.size();i++) {
      ResultBitString = (Result_BitString) ResultBitStrings.elementAt(i);
      ResultBitString.Print_ResultBitString();
    }
  }
}