/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class BitString {

  public int BitCount;
  public long Bits;

  public BitString () {
    BitCount=0;
    Bits=0;
  }

  public BitString (int BCount) {
    BitCount=BCount;			// number of bits to use is assigned
    Bits=0;
  }

  public BitString (String BS) {
    BitCount=BS.length();		// number of bits to use is assigned
    SetBits(0,BS);
  }

  public BitString (BitString BS) {
    BitCount=BS.BitCount;		// number of bits to use is assigned
    Bits=BS.Bits;
  }

  public void Copy (BitString BS) {
    BitCount=BS.BitCount;		// number of bits to use is assigned
    Bits=BS.Bits;
  }

  public void SetBits(int StartBit,String Value) {
    if ((StartBit>=0) && ((StartBit+Value.length())<=Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING)) {
      for (int i=1;i<=Value.length();i++) {
        if (Value.charAt(i-1)=='0')
          Bits=(Bits & ~((long)0 | ((long)1<<(Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING-StartBit-i))));
        else
          Bits=(Bits | ((long)1<<(Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING-StartBit-i)));
      }

      if (StartBit+Value.length()>BitCount)
        BitCount=StartBit+Value.length();
    }
  }

  public void SetBits(String BS) {
    BitCount=BS.length();
    SetBits(0,BS);
  }

  public String GetBitString(int StartBit, int BitLength) {
    String S="";
    int i;

    if (BitLength>0) {
      if (StartBit+BitLength>BitCount)
        BitLength=BitCount-StartBit;
      for (i=StartBit;i<StartBit+BitLength;i++) {
        if (((Bits<<i)>>>(Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING-1))==1)
          S=S+"1";
        else
          S=S+"0";
      }
    }
    else
      S="";

    return S;
  }

  public String GetBitString() {
    return GetBitString(0,BitCount);
  }

  public boolean GreaterThan(BitString B) {
    int b=Math.min(this.BitCount,B.BitCount);
    String TempStr=this.GetBitString(0,b);
    return (TempStr.compareTo(B.GetBitString(0,b))>0);
  }

  public boolean LessThan(BitString B) {
    int b=Math.min(this.BitCount,B.BitCount);
    String TempStr=this.GetBitString(0,b);
    return (TempStr.compareTo(B.GetBitString(0,b))<0);
  }

  public boolean EqualTo(BitString B) {
    int b=Math.min(this.BitCount,B.BitCount);
    String TempStr=this.GetBitString(0,b);
    return (TempStr.compareTo(B.GetBitString(0,b))==0);
  }

  public boolean Include(BitString B) {
    int b=Math.min(this.BitCount,B.BitCount);
    String TempStr=this.GetBitString(0,b);
    return ((TempStr.compareTo(B.GetBitString(0,b))==0) && (this.BitCount<B.BitCount));
  }

  public BitString Parent() {
    return (new BitString(this.GetBitString(0,this.BitCount-1)));
  }

  public BitString Complement() {
    BitString BS=new BitString(this.GetBitString());

    if (BS.GetBitString(BS.BitCount-1,1).compareTo("0")==0)
      BS.SetBits(BS.BitCount-1,"1");
    else
      BS.SetBits(BS.BitCount-1,"0");

    return (BS);
  }

  public static BitString Get_Biggest_Common_Partition_Number(Node N, int pos) { // it gets the biggest partition number which is common to all the data bucket records
    BitString TempBS1,TempBS2,TempBS3;
    NodeRecord TempNR;

    if (pos>0)
      TempBS1 = ((NodeRecord)N.NRs.elementAt(pos-1)).Key;
    else {
      if (N.PrevNode!=null)
        TempBS1 = ((NodeRecord)N.PrevNode.NRs.elementAt(N.PrevNode.NRs.size()-1)).Key;
      else
        TempBS1=null;
    }

    TempNR=((NodeRecord)N.NRs.elementAt(pos));
    TempBS2=TempNR.Key;

    if (pos<(N.NRs.size()-1))
      TempBS3 = ((NodeRecord)N.NRs.elementAt(pos+1)).Key;
    else {
      if (N.NextNode!=null)
        TempBS3 = ((NodeRecord)N.NextNode.NRs.elementAt(0)).Key;
      else
        TempBS3=null;
    }

    DataBucketRecord Temp_DBR;
    BitString TempBS = new BitString(BitString.GetLargestPartitionNumber(TempBS1,TempBS2,TempBS3));
    int Bit_Count=TempBS.BitCount;
    do {
      TempBS.SetBits(TempBS2.GetBitString(0,Bit_Count));
      int IncludeNumber=0;
      for (int i=0;i<TempNR.ChildDB.DBRs.size();i++) {
        Temp_DBR=(DataBucketRecord)TempNR.ChildDB.DBRs.elementAt(i);
        if (TempBS.Include(Temp_DBR.Key))
          IncludeNumber++;
      }
      if (IncludeNumber==TempNR.ChildDB.DBRs.size()) { // all the data bucket records are included by this key
        return(TempBS);
      }
      Bit_Count++;
    } while(Bit_Count<Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING);

    System.out.println("50 - Impossible!!!");
    System.exit(1);
    return(null);
  }

  public static String GetLargestPartitionNumber(BitString BS1,BitString BS,BitString BS2) {
    int i=0;

    if ((BS1!=null) && (BS2==null)) {
      do {
        BitString TempBS = new BitString(BS.GetBitString(0,i));
        if (TempBS.GreaterThan(BS1))
          break;
        i++;
      } while(i<Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING);
    }
    else if ((BS1==null) && (BS2!=null)) {
      do {
        BitString TempBS = new BitString(BS.GetBitString(0,i));
        if (TempBS.LessThan(BS2))
          break;
        i++;
      } while(i<Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING);
    }
    else  if ((BS1!=null) && (BS2!=null)) {
      do {
        BitString TempBS = new BitString(BS.GetBitString(0,i));
        if (TempBS.GreaterThan(BS1) && (TempBS.LessThan(BS2)))
          break;
        i++;
      } while(i<Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING);
    }
    else {
      return("");
    }

    if (i==Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING) {
      System.out.println("20- HATA");
      System.exit(1);
    }

    return(BS.GetBitString(0,i));
  }


  public static void main(String args[]) {
    BitString BS1=new BitString("0001");
    BitString BS=new BitString("0010000000");
    BitString BS2=new BitString("01");
    System.out.println(GetLargestPartitionNumber(BS1,BS,BS2));
  }
}