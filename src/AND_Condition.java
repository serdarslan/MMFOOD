import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class AND_Condition {
  Vector ConditionElements;
  Vector ResultBitStrings;

  public AND_Condition() {
    ConditionElements = new Vector(0,1);
    ResultBitStrings = new Vector(0,1);
  }

  public void AND_ResultBitStrings() {
    int i,j,k,l;
    Condition_Element TempCE;
    Result_BitString TempRBS1,TempRBS2;
    Vector TempRBS = new Vector(0,1);

    for (i=0;i<this.ConditionElements.size();i++) {
      TempCE = (Condition_Element) this.ConditionElements.elementAt(i);

      if (TempCE.ResultBitStrings.size()>0) {
        for (j=0;j<TempCE.ResultBitStrings.size();j++) {
          this.ResultBitStrings.addElement((Result_BitString)TempCE.ResultBitStrings.elementAt(j));
        }
        break;
      }
    }

    for (j=i+1;j<this.ConditionElements.size();j++) {
      TempCE = (Condition_Element) this.ConditionElements.elementAt(j);
      TempRBS.removeAllElements();

      for (k=TempCE.ResultBitStrings.size()-1;k>=0;k--) {
        TempRBS2 = (Result_BitString) TempCE.ResultBitStrings.elementAt(k);

        for (l=0;l<this.ResultBitStrings.size();l++) {
          TempRBS1 = (Result_BitString) this.ResultBitStrings.elementAt(l);

          if (TempRBS1.StartBS.LessThan(TempRBS2.StartBS) || TempRBS1.StartBS.EqualTo(TempRBS2.StartBS)) {
            if (TempRBS1.StopBS.GreaterThan(TempRBS2.StartBS) || TempRBS1.StopBS.EqualTo(TempRBS2.StartBS)) {
              if (TempRBS2.StopBS.LessThan(TempRBS1.StopBS))
                TempRBS.addElement(new Result_BitString(new BitString(TempRBS2.StartBS),new BitString(TempRBS2.StopBS)));
              else
                TempRBS.addElement(new Result_BitString(new BitString(TempRBS2.StartBS),new BitString(TempRBS1.StopBS)));
            }
          }
          else
          if (TempRBS2.StartBS.LessThan(TempRBS1.StartBS) || TempRBS2.StartBS.EqualTo(TempRBS1.StartBS)) {
            if (TempRBS2.StopBS.GreaterThan(TempRBS1.StartBS) || TempRBS2.StopBS.EqualTo(TempRBS1.StartBS)) {
              if (TempRBS2.StopBS.LessThan(TempRBS1.StopBS))
                TempRBS.addElement(new Result_BitString(new BitString(TempRBS1.StartBS),new BitString(TempRBS2.StopBS)));
              else
                TempRBS.addElement(new Result_BitString(new BitString(TempRBS1.StartBS),new BitString(TempRBS1.StopBS)));
            }
          }
        }
      }
      if (TempCE.ResultBitStrings.size()>0) {
        this.ResultBitStrings.removeAllElements();
        for (k=0;k<TempRBS.size();k++) { // move TempRBS into this.ResulBitStrings
          this.ResultBitStrings.addElement(TempRBS.elementAt(k));
        }
      }
    }
  }

  public void Print_ResultBitStrings() {
    Result_BitString ResultBitString;
    for (int i=0; i<ResultBitStrings.size();i++) {
      ResultBitString = (Result_BitString) ResultBitStrings.elementAt(i);
      ResultBitString.Print_ResultBitString();
    }
  }

  public void Print_ResultBitStrings_Raw() {
    Condition_Element ConditionElement;
    for (int i=0; i<ConditionElements.size();i++) {
      ConditionElement = (Condition_Element) ConditionElements.elementAt(i);
      ConditionElement.Print_ResultBitStrings();
      System.out.println();
    }
  }

  public static void main(String[] args) {
    AND_Condition AND_Condition1 = new AND_Condition();
  }
}