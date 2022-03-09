/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Result_BitString {
  BitString StartBS;
  BitString StopBS;

  public Result_BitString(BitString Start_BS, BitString Stop_BS) {
    StartBS = new BitString(Start_BS);
    StopBS = new BitString(Stop_BS);
  }

  public void Print_ResultBitString() {
    System.out.println("Start : "+StartBS.GetBitString()+"   Stop : "+StopBS.GetBitString());
  }

  public void GetResultBitString(ObjectStr TempRBS) {
    TempRBS.Set(TempRBS.Get()+"Start : "+StartBS.GetBitString()+"   Stop : "+StopBS.GetBitString()+"\n");
  }

  public static void main(String[] args) {
  }
}