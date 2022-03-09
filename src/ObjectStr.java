/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class ObjectStr {
  String Str;

  public ObjectStr() {
    Str="";
  }

  public ObjectStr(String S) {
    Str=S;
  }

  public String Get() {
    return(Str);
  }

  public void Set(String S) {
    Str=S;
  }

  public void Set(ObjectStr I) {
    Str=I.Get();
  }

}