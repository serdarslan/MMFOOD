import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

class PathInst {

  public static void CopyPathInst(Vector PI1,Vector PI2) { // it copies the PI in PI2 into PI1
    PI1.removeAllElements();
    for (int i=0;i<PI2.size();i++) {
      PI1.addElement(PI2.elementAt(i));
    }
  }

  public static int Compare(Vector PI1,Vector PI2) { // according to <path relation, return the previously coming PI
    int FO1=FirstObject(PI1);
    int FO2=FirstObject(PI2);
    if (FO1>FO2)
      return(2);
    else if (FO1<FO2)
      return(1);

    // if FO1==FO2
    for (int i=FO1; i>=0 ; i--) {
      if (i==0) {
        if (((Author)PI1.elementAt(i)).OID<((Author)PI2.elementAt(i)).OID)
          return(2);
        else if (((Author)PI1.elementAt(i)).OID>((Author)PI2.elementAt(i)).OID)
          return(1);
      }
      else
      if (i==1) {
        if (((Book)PI1.elementAt(i)).OID<((Book)PI2.elementAt(i)).OID)
          return(2);
        else if (((Book)PI1.elementAt(i)).OID>((Book)PI2.elementAt(i)).OID)
          return(1);
      }
      else
      if (i==2) {
        if (((Member)PI1.elementAt(i)).OID<((Member)PI2.elementAt(i)).OID)
          return(2);
        else if (((Member)PI1.elementAt(i)).OID>((Member)PI2.elementAt(i)).OID)
          return(1);
      }
      else
      if (i==3) {
        if (((Library)PI1.elementAt(i)).OID<((Library)PI2.elementAt(i)).OID)
          return(2);
        else if (((Library)PI1.elementAt(i)).OID>((Library)PI2.elementAt(i)).OID)
          return(1);
      }
    }
    return(0);
  }

  public static boolean Include(Vector PI1,Vector PI2) { // if PI1 includes PI2
    int i;
    int F1=FirstObject(PI1);
    int F2=FirstObject(PI2);

    if ((F1==-1) && (F2==-1))
      return(true);
    if (F1<F2)
      return(false);

    for (i=F2;i>=0;i--) {
      if (PI2.elementAt(i)!=null)
        if (PI1.elementAt(i)!=null)
          if (!PI2.elementAt(i).equals(PI1.elementAt(i)))
            break;
          ;
    }
    if (i<0)
      return(true);
    else
      return(false);
  }


  public static void Eliminate_Duplicates(Vector PIs1,Vector PIs2) { // eliminate duplicates in PIs1 and PIs2
    Vector PI1,PI2;
    int i,j,k;

    if ((PIs1!=null) && (PIs2!=null)) {
      for (i=PIs1.size()-1; i>=0; i--) {
        PI1 = (Vector)PIs1.elementAt(i);
        for (j=PIs2.size()-1; j>=0; j--) {
          PI2 = (Vector)PIs2.elementAt(j);
          if (PathInst.Include(PI2,PI1)) {
            PIs1.removeElementAt(i);
            break;
          }
          else if (PathInst.Include(PI1,PI2)) {
            PIs2.removeElementAt(j);
            break;
          }
        }
      }
    }
  }

  public static void DuplicatePIs(Vector PIs1,Vector PIs2) { // duplicate PIs2 into PIs1
    PIs1.removeAllElements();
    for (int i=0;i<PIs2.size();i++) {
      Vector PI = new Vector(0,1);
      PathInst.CopyPathInst(PI,(Vector)PIs2.elementAt(i));
      PIs1.addElement(PI);
    }
  }

  public static void InsertPIs(Vector PIs1,Vector PIs2) { // eliminate duplicates and insert PIs1 into PIs2
    Vector PI1,PI2;
    int i,j;
    int R;

    PathInst.Eliminate_Duplicates(PIs1,PIs2);

    for (i=0; i<PIs1.size(); i++) {
      PI1 = (Vector)PIs1.elementAt(i);
      if (PIs2.size()==0)
        PIs2.addElement(PI1);
      else {
        for (j=0; j<PIs2.size(); j++) {
          PI2 = (Vector)PIs2.elementAt(j);
          R=Compare(PI1,PI2);
          if ((R==0) || (R==1)) {
            PIs2.insertElementAt(PI1,j);
            break;
          }
        }
        if (j==PIs2.size()) {
          PIs2.addElement(PI1);
        }
      }
    }
  }

  public static int FirstObject(Vector PI) {
    if (PI.size()==0)
      return(-1);
    else
      return(PI.size()-1);
  }

  static public void Combine_Path_Instantiations(Vector PIs) { // combine path instantiations in PIs according to the key values
    Collecting_Path_Instantiations PI1,PI2;
    int i,j,k;

    for (i=PIs.size()-1;i>=0;i--) {
      for (j=0;j<i;j++) {
        PI1 = (Collecting_Path_Instantiations) PIs.elementAt(i);
        PI2 = (Collecting_Path_Instantiations) PIs.elementAt(j);
        if ((PI1.Value1.compareTo(PI2.Value1)==0) && (PI1.Value2.compareTo(PI2.Value2)==0)) {
          for (k=0;k<PI1.PIs.size();k++)
            PI2.PIs.addElement(PI1.PIs.elementAt(k));
          //PI1.finalize();
          PIs.removeElementAt(i);
          break;
        }
      }
    }
  }

  static public void Move_Path_Instantiations_with_Key_Values(Vector PIs1,Vector PIs2) { // move path instantiations and key valuesin PIs1 into PIs2
    Collecting_Path_Instantiations PI;
    int i;

    for (i=PIs1.size()-1;i>=0;i--) {
        PI = (Collecting_Path_Instantiations) PIs1.elementAt(i);
        PIs2.addElement(PI);
        PIs1.removeElementAt(i);
    }
  }

  public static void PrintPathInst(Vector PI) {
    for (int i=0;i<PI.size();i++) {
      System.out.print(PI.elementAt(i).getClass().getName()+"/");
      if (i==0) System.out.print(((Author)PI.elementAt(i)).OID);
      else if (i==1) System.out.print(((Book)PI.elementAt(i)).OID);
      else if (i==2) System.out.print(((Member)PI.elementAt(i)).OID);
      else if (i==3) System.out.print(((Library)PI.elementAt(i)).OID);
    }
  }

  public static void PrintAllPI(Vector PI,int Level) {
    int i;
    for (i=0;i<PI.size();i++) {
      if (i==0) {
        Author A = (Author)PI.elementAt(i);

        System.out.print(Database.GetSpaces(Level));
        System.out.print("PI: Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,A.Age));
        System.out.print(" & Height="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Age,A.Height));
      }
      System.out.print(" : "+PI.elementAt(i).toString()+"/");
      if (i==0) System.out.print(((Author)PI.elementAt(i)).OID);
      else if (i==1) System.out.print(((Book)PI.elementAt(i)).OID);
      else if (i==2) System.out.print(((Member)PI.elementAt(i)).OID);
      else if (i==3) System.out.print(((Library)PI.elementAt(i)).OID);
    }
    System.out.println();
  }

  public static void PrintAllPI(Vector PI,int Level, Vector Tree) {
    int i;
    String TempStr = new String("");

    for (i=0;i<PI.size();i++) {
      if (i==0) {
        Author A = (Author)PI.elementAt(i);

        TempStr=Database.GetSpaces(Level);
        TempStr=TempStr+"PI: Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,A.Age);
        TempStr=TempStr+" & Height="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Age,A.Height);
      }
      TempStr=TempStr+" : "+PI.elementAt(i).toString()+"/";
      if (i==0) TempStr=TempStr+((Author)PI.elementAt(i)).OID;
      else if (i==1) TempStr=TempStr+((Book)PI.elementAt(i)).OID;
      else if (i==2) TempStr=TempStr+((Member)PI.elementAt(i)).OID;
      else if (i==3) TempStr=TempStr+((Library)PI.elementAt(i)).OID;
    }
    Tree.addElement(TempStr);
  }

/*
  public static void main(String args[]) {
  }
*/
}