import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Where_Conditions {
  Vector ANDConditions;
  Vector ResultBitStrings;
  Vector ResultObjects;

  public Where_Conditions() {
    ANDConditions = new Vector(0,1);
    ResultBitStrings = new Vector(0,1);
    ResultObjects = new Vector(0,1);
  }

  public void OR_ANDResultBits() {
    int i,j,k,l;
    AND_Condition TempAND1,TempAND2;
    Result_BitString TempRBS1,TempRBS2;
    Object T;

    for (i=0;i<this.ANDConditions.size();i++) {
      TempAND1 = (AND_Condition) this.ANDConditions.elementAt(i);

      if (TempAND1.ResultBitStrings.size()>0) {
        for (j=0;j<TempAND1.ResultBitStrings.size();j++) {
          this.ResultBitStrings.addElement((Result_BitString)TempAND1.ResultBitStrings.elementAt(j));
        }
      }
    }

    for (j=this.ResultBitStrings.size()-1;j>=0;j--) {
      boolean Delete=false;
      TempRBS2 = (Result_BitString) this.ResultBitStrings.elementAt(j);

      for (k=0;k<j;k++) {
        TempRBS1 = (Result_BitString) this.ResultBitStrings.elementAt(k);

        if (TempRBS1.StartBS.LessThan(TempRBS2.StartBS) || TempRBS1.StartBS.EqualTo(TempRBS2.StartBS)) {
          if (TempRBS1.StopBS.GreaterThan(TempRBS2.StartBS) || TempRBS1.StopBS.EqualTo(TempRBS2.StartBS)) {
            TempRBS1.StopBS.Copy(TempRBS2.StopBS);
            Delete=true;
          }
        }
        else
        if (TempRBS2.StartBS.LessThan(TempRBS1.StartBS) || TempRBS2.StartBS.EqualTo(TempRBS1.StartBS)) {
          if (TempRBS2.StopBS.GreaterThan(TempRBS1.StartBS) || TempRBS2.StopBS.EqualTo(TempRBS1.StartBS)) {
            TempRBS1.StartBS.Copy(TempRBS2.StartBS);
            Delete=true;
          }
        }
      }

      if (Delete)
        this.ResultBitStrings.removeElementAt(j);
    }
  }

  public void GetResultObjects(Node Root, int FromClass, double FromThreholdValue, int TargetClass, double TargetThreholdValue, String Target_Attribute, Where_Conditions WCs, BitString StartBS, BitString StopBS, Vector ResultObjects,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Node TempN = Root;
    NodeRecord TempNR=null;
    DataBucket TempDB=null;
    DataBucketRecord TempDBR=null;
    boolean ReachStopBS=false;
    int pos=0;
    int pos2=0;
    int pos3=0;

//    DiskAccessP.Set(DiskAccessP.Get()+1);
    DiskAccessS.Set(DiskAccessS.Get()+1);
    if (TempN==null)
      return;

    while (!TempN.IsLeafNode) {
      if (TempN.NRs.size()>0) {
        pos=0;
        while (pos<TempN.NRs.size()) {	// while the last node record is not reached
          TempNR = (NodeRecord)TempN.NRs.elementAt(pos);
          if ((TempNR.Key.GreaterThan(StartBS)) || (TempNR.Key.Include(StartBS)) || (TempNR.Key.EqualTo(StartBS))) {
          // if find the correct node record, break
            break;
          }
          pos++;	// try next node record
        }

        if (pos<TempN.NRs.size()) {	// if an appropriate node record is found
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempN = TempNR.ChildNode;
        }
        else {
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          pos=TempN.NRs.size()-1;
          TempN = ((NodeRecord)TempN.NRs.elementAt(pos)).ChildNode;
        }
      }
      else {
        System.out.println("150- Hata"); // FOOD Index is in a inconsistent state
        System.exit(1);
      }
    }

    if (TempN.NRs.size()>0) {
      pos=0;
      while (pos<TempN.NRs.size()) {			// while the last node record is not reached
        TempNR = (NodeRecord)TempN.NRs.elementAt(pos);
        if ((TempNR.Key.Include(StartBS)) || (TempNR.Key.EqualTo(StartBS))) {
        // if find the correct node record, break
          break;
        }
        pos++;	// try next node record
      }

      if (pos<TempN.NRs.size()) {	// if an appropriate node record is found
//        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        TempDB = TempNR.ChildDB;
      }
      else {
//        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        pos=TempN.NRs.size()-1;
        TempDB = ((NodeRecord)TempN.NRs.elementAt(pos)).ChildDB;
      }
    }
    else {
      System.out.println("160- Hata"); // FOOD Index is in a inconsistent state
      System.exit(1);
    }

    ReachStopBS = false;
    while ((TempDB!=null) && (!ReachStopBS)) {
      for (int new_pos=0;new_pos<TempDB.DBRs.size();new_pos++) {
        TempDBR = (DataBucketRecord) TempDB.DBRs.elementAt(new_pos);
        if ((TempDBR.Key.GreaterThan(StartBS) || TempDBR.Key.EqualTo(StartBS)) && (TempDBR.Key.LessThan(StopBS) || TempDBR.Key.EqualTo(StopBS)))
          CheckObjects(FromClass, FromThreholdValue, TargetClass, TargetThreholdValue, Target_Attribute, WCs, TempDBR, ResultObjects, DiskAccessP, DiskAccessS); // tamamla
          // Check DBR and the objects inside if they satisfy the query conditions
        if (TempDBR.Key.GreaterThan(StopBS) || TempDBR.Key.EqualTo(StopBS)) {
          ReachStopBS = true;
          break;
        }
      }
//      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      TempDB = TempDB.Next; // Check in next DB
    }
  }

  public void CheckObjects(int FromClass, double FromThreholdValue, int TargetClass, double TargetThreholdValue, String Target_Attribute, Where_Conditions WCs, DataBucketRecord TempDBR, Vector ResultObjects, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    Path_Instantiation_Node TempPIN = TempDBR.PIN;
    int TargetClassPos = Database.GetClassPositionInPath(TargetClass);
    DirectoryRecord TempDR = null;

    if (TempPIN.Type==2) {
Loop_PIN:
      while (TempPIN != null) {
        for (int i=0;i<TempPIN.Directory.size();i++) {
          TempDR = (DirectoryRecord) TempPIN.Directory.elementAt(i);
          if (PathInst.FirstObject(TempDR.PI)>=TargetClassPos) {
            TempPIN = TempDR.PIN;
            break Loop_PIN;
          }
        }
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        TempPIN = TempPIN.NextNode;
      }
    }
    if (TempPIN != null) {
      while (TempPIN != null) {
        for (int i=0;i<TempPIN.PIs.size();i++) {
          Vector TempPI = (Vector) TempPIN.PIs.elementAt(i);
          if (PathInst.FirstObject(TempPI)>=TargetClassPos) {
            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            Author TempAuthor = (Author)TempPI.elementAt(0);
            if (TempAuthor.MembershipToClasses[FromClass]>=FromThreholdValue) {
              if (CheckConditions(TempAuthor, WCs)) {
                DiskAccessP.Set(DiskAccessP.Get()+1);
                DiskAccessS.Set(DiskAccessS.Get()+1);
                if (TargetClassPos==0) {
                  Author TempA = (Author)TempPI.elementAt(TargetClassPos);
                  if (TempA.MembershipToClasses[TargetClass]>=TargetThreholdValue) {
                    ResultObjects.addElement((Author)TempPI.elementAt(TargetClassPos));
                  }
                }
                else if (TargetClassPos==1) {
                  Book TempB = (Book)TempPI.elementAt(TargetClassPos);
                  if (TempB.MembershipToClasses[TargetClass]>=TargetThreholdValue) {
                    ResultObjects.addElement((Book)TempPI.elementAt(TargetClassPos));
                  }
                }
                else if (TargetClassPos==2) {
                  Member TempM = (Member)TempPI.elementAt(TargetClassPos);
                  if (TempM.MembershipToClasses[TargetClass]>=TargetThreholdValue) {
                    ResultObjects.addElement((Member)TempPI.elementAt(TargetClassPos));
                  }
                }
                else if (TargetClassPos==3) {
                  Library TempL = (Library)TempPI.elementAt(TargetClassPos);
                  if (TempL.MembershipToClasses[TargetClass]>=TargetThreholdValue) {
                    ResultObjects.addElement((Library)TempPI.elementAt(TargetClassPos));
                  }
                }
              }
            }
          }
        }
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        TempPIN = TempPIN.NextNode;
      }
    }
  }

  public static boolean CheckConditions(Author TempAuthor, Where_Conditions WCs) {
    boolean ANDGroupResult=false;

    for (int i=0;i<WCs.ANDConditions.size();i++) {
      AND_Condition TempAC = (AND_Condition) WCs.ANDConditions.elementAt(i);
      String TempBits = new String("");
      ANDGroupResult=false;
      double TempThreshold=0.0;

      for (int j=0;j<TempAC.ConditionElements.size();j++) {
        Condition_Element TempCE = (Condition_Element) TempAC.ConditionElements.elementAt(j);

        if (TempCE.Attribute.compareTo("age")==0) {
          TempBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,TempCE.Field1);
          if ((TempBits.length()>0) && (TempBits.charAt(0)=='F')) { // fuzzy
            if (TempBits.compareTo("F000")==0) {
              TempThreshold=1.0;
            }
            else {
              TempThreshold=0.0;
              try {
                TempThreshold = Double.valueOf(TempCE.Field2).doubleValue();
              }
              catch (Exception E) { // Threshold is not a number
                System.out.println("190- Threshold is not a number !");
                System.exit(1);
              }
            }

            int TempAge;
            try {
              int Count;
              TempAge = Integer.parseInt(TempAuthor.Age); // fuzzy - crisp
              ObjectDouble MD1=new ObjectDouble(),MD2=new ObjectDouble(),MD3=new ObjectDouble(); // Membership Degree
              ObjectInt PN1=new ObjectInt(),PN2=new ObjectInt(),PN3=new ObjectInt(); // Part No
              Database.Membership_Function_Young(TempAge,MD1,PN1);
              Database.Membership_Function_MiddleAged(TempAge,MD2,PN2);
              Database.Membership_Function_Old(TempAge,MD3,PN3);

              for (Count=0;Count<3;Count++) {
                double MD = Database.Similarity_Matrix_Age[Database.ClassBitsToIndex(TempBits.substring(1))][Count];
                if (MD==0)
                  MD=0.000001;
                double TempMD=0.0;

                MD =  TempThreshold / MD;

                if (Count==0) TempMD = MD1.Get();
                else if (Count==1) TempMD = MD2.Get();
                else if (Count==2) TempMD = MD3.Get();

                if (TempMD>=MD) {
                  ANDGroupResult=true;
                  break;
                }
              }
              if (Count>=3) {
                ANDGroupResult=false;
                break;
              }
              else
                continue;
            }
            catch (Exception E) { // fuzzy - fuzzy
              if (TempThreshold<=Database.Similarity_Matrix_Age[Database.ClassBitsToIndex(TempBits.substring(1))][Database.ClassBitsToIndex(TempAuthor.Age.substring(1))]) {
                ANDGroupResult=true;
                continue;
              }
              else {
                ANDGroupResult=false;
                break;
              }
            }
          }
          else { // crisp
            int TempAge1,TempAge2;
            try {
              TempAge1 = Integer.parseInt(TempAuthor.Age);
              TempAge2 = Integer.parseInt(TempCE.Field2);
            }
            catch (Exception E) { // crisp - fuzzy
              ANDGroupResult=false; // if the age attribute is not a crisp one
              break;
            }

            // crisp - crisp
            if ((TempCE.Field1.compareTo("=")==0) && (TempAge1 == TempAge2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo("<")==0) && (TempAge1 < TempAge2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo("<=")==0) && (TempAge1 <= TempAge2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo(">")==0) && (TempAge1 > TempAge2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo(">=")==0) && (TempAge1 >= TempAge2))
              ANDGroupResult=true;
            else {
              ANDGroupResult=false;
              break;
            }
            continue;
          }
        }
        else if (TempCE.Attribute.compareTo("height")==0) {
          TempBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,TempCE.Field1);
          if ((TempBits.length()>0) && (TempBits.charAt(0)=='F')) { // fuzzy
            if (TempBits.compareTo("F000")==0) {
              TempThreshold=1.0;
            }
            else {
              TempThreshold=0.0;
              try {
                TempThreshold = Double.valueOf(TempCE.Field2).doubleValue();
              }
              catch (Exception E) { // Threshold is not a number
                System.out.println("190- Threshold is not a number !");
                System.exit(1);
              }
            }

            int TempHeight;
            try {
              int Count;
              TempHeight = Integer.parseInt(TempAuthor.Height); // fuzzy - crisp
              ObjectDouble MD1=new ObjectDouble(),MD2=new ObjectDouble(),MD3=new ObjectDouble(); // Membership Degree
              ObjectInt PN1=new ObjectInt(),PN2=new ObjectInt(),PN3=new ObjectInt(); // Part No
              Database.Membership_Function_Short(TempHeight,MD1,PN1);
              Database.Membership_Function_Medium(TempHeight,MD2,PN2);
              Database.Membership_Function_Tall(TempHeight,MD3,PN3);

              for (Count=0;Count<3;Count++) {
                double MD = Database.Similarity_Matrix_Height[Database.ClassBitsToIndex(TempBits.substring(1))][Count];
                double TempMD=0.0;

                MD =  TempThreshold / MD;

                if (Count==0) TempMD = MD1.Get();
                else if (Count==1) TempMD = MD2.Get();
                else if (Count==2) TempMD = MD3.Get();

                if (TempMD>=MD) {
                  ANDGroupResult=true;
                  break;
                }
              }
              if (Count>=3) {
                ANDGroupResult=false;
                break;
              }
              else
                continue;
            }
            catch (Exception E) { // fuzzy - fuzzy
              if (TempThreshold<=Database.Similarity_Matrix_Height[Database.ClassBitsToIndex(TempBits.substring(1))][Database.ClassBitsToIndex(TempAuthor.Height.substring(1))]) {
                ANDGroupResult=true;
                continue;
              }
              else {
                ANDGroupResult=false;
                break;
              }
            }
          }
          else { // crisp
            int TempHeight1,TempHeight2;
            try {
              TempHeight1 = Integer.parseInt(TempAuthor.Height);
              TempHeight2 = Integer.parseInt(TempCE.Field2);
            }
            catch (Exception E) { // crisp - fuzzy
              ANDGroupResult=false; // if the age attribute is not a crisp one
              break;
            }

            // crisp - crisp
            if ((TempCE.Field1.compareTo("=")==0) && (TempHeight1 == TempHeight2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo("<")==0) && (TempHeight1 < TempHeight2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo("<=")==0) && (TempHeight1 <= TempHeight2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo(">")==0) && (TempHeight1 > TempHeight2))
              ANDGroupResult=true;
            else
            if ((TempCE.Field1.compareTo(">=")==0) && (TempHeight1 >= TempHeight2))
              ANDGroupResult=true;
            else {
              ANDGroupResult=false;
              break;
            }
            continue;
          }
        }
        else {
          System.out.println("180- There is not such an attribute !");
          System.exit(1);
        }
      }

      if (ANDGroupResult==true)
        return(true);
    }

    if (WCs.ANDConditions.size()>0)
      return(false);
    else
      return(true);
  }

  public void GetResults(Node Root, int FromClass, double FromThreholdValue, int TargetClass, double TargetThreholdValue, String Target_Attribute, Where_Conditions WCs, Vector ResultObjectsStr, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    Result_BitString TempRBS;
    Vector ResultObjects = new Vector(0,1);
    int TempTargetClass=0;
    int i,j;

    if (this.ResultBitStrings.size()==0) {
      ObjectStr TempStr=new ObjectStr();
      TempRBS = new Result_BitString(new BitString(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"0")),new BitString(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"1")));
      TempRBS.GetResultBitString(TempStr);
      foodindex.textArea_ResultBitStrings.setText(TempStr.Get());
      GetResultObjects(Root, FromClass, FromThreholdValue, TargetClass, TargetThreholdValue, Target_Attribute, WCs, TempRBS.StartBS, TempRBS.StopBS, ResultObjects, DiskAccessP, DiskAccessS);
    }
    else {
      for (i=0;i<this.ResultBitStrings.size();i++) {
        ObjectStr TempStr=new ObjectStr();
        TempRBS = (Result_BitString) this.ResultBitStrings.elementAt(i);
        TempRBS.GetResultBitString(TempStr);
        foodindex.textArea_ResultBitStrings.append(TempStr.Get());
        GetResultObjects(Root, FromClass, FromThreholdValue, TargetClass, TargetThreholdValue, Target_Attribute, WCs, TempRBS.StartBS, TempRBS.StopBS, ResultObjects, DiskAccessP, DiskAccessS);
       }
    }

    for (i=ResultObjects.size()-1;i>=0;i--) {
      for(j=0;j<i;j++)
        if (ResultObjects.elementAt(i).equals(ResultObjects.elementAt(j))) {
          ResultObjects.removeElementAt(i);
          break;
        }
    }

    for (i=0;i<ResultObjects.size();i++) {
      TempTargetClass = Database.GetClassIndex(ExtractClassName(ResultObjects.elementAt(i).getClass().getName()));
      if (TempTargetClass==0) {
        Library TempL = (Library) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempL.OID)+" : Name="
          +TempL.Name+" : City="+TempL.City+" : Book Capacity="+Integer.toString(TempL.Book_Capacity)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempL.OID)));
        else if (Target_Attribute.compareTo("name")==0)
          ResultObjectsStr.addElement(new String("Name="+TempL.Name));
        else if (Target_Attribute.compareTo("city")==0)
          ResultObjectsStr.addElement(new String("City="+TempL.City));
        else if (Target_Attribute.compareTo("book_capacity")==0)
          ResultObjectsStr.addElement(new String("Book Capacity="+Integer.toString(TempL.Book_Capacity)));
      }
      else if (TempTargetClass==1) {
        Member TempM = (Member) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempM.OID)+" : Member ID="
          +Integer.toString(TempM.Member_ID)+" : Name&Surname="+TempM.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempM.Max_Borrowing)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempM.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempM.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempM.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempM.Max_Borrowing)));
      }
      else if (TempTargetClass==2) {
        Highly_Educated TempHE = (Highly_Educated) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempHE.OID)+" : Member ID="
          +Integer.toString(TempHE.Member_ID)+" : Name&Surname="+TempHE.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempHE.Max_Borrowing)+
          " : Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempHE.Education)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempHE.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempHE.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempHE.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempHE.Max_Borrowing)));
        else
        if (Target_Attribute.compareTo("education")==0)
          ResultObjectsStr.addElement("Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempHE.Education));
      }
      else if (TempTargetClass==3) {
        Educated TempE = (Educated) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempE.OID)+" : Member ID="
          +Integer.toString(TempE.Member_ID)+" : Name&Surname="+TempE.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempE.Max_Borrowing)+
          " : Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempE.Education)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempE.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempE.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempE.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempE.Max_Borrowing)));
        else
        if (Target_Attribute.compareTo("education")==0)
          ResultObjectsStr.addElement("Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempE.Education));
      }
      else if (TempTargetClass==4) {
        Lecturer TempL = (Lecturer) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempL.OID)+" : Member ID="
          +Integer.toString(TempL.Member_ID)+" : Name&Surname="+TempL.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempL.Max_Borrowing)+
          " : Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempL.Education)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempL.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempL.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempL.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempL.Max_Borrowing)));
        else
        if (Target_Attribute.compareTo("education")==0)
          ResultObjectsStr.addElement("Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempL.Education));
      }
      else if (TempTargetClass==5) {
        Assistant TempA = (Assistant) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempA.OID)+" : Member ID="
          +Integer.toString(TempA.Member_ID)+" : Name&Surname="+TempA.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempA.Max_Borrowing)+
          " : Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempA.Education)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempA.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempA.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempA.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempA.Max_Borrowing)));
        else
        if (Target_Attribute.compareTo("education")==0)
          ResultObjectsStr.addElement("Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempA.Education));
      }
      else if (TempTargetClass==6) {
        Student TempS = (Student) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempS.OID)+" : Member ID="
          +Integer.toString(TempS.Member_ID)+" : Name&Surname="+TempS.Name_Surname+" : Maximum Borrowing="+Integer.toString(TempS.Max_Borrowing)+
          " : Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempS.Education)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempS.OID)));
        else if (Target_Attribute.compareTo("member_id")==0)
          ResultObjectsStr.addElement(new String("Member ID="+Integer.toString(TempS.Member_ID)));
        else
        if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempS.Name_Surname));
        else
        if (Target_Attribute.compareTo("max_borrowing")==0)
          ResultObjectsStr.addElement(new String("Maximum Borrowing="+Integer.toString(TempS.Max_Borrowing)));
        else
        if (Target_Attribute.compareTo("education")==0)
          ResultObjectsStr.addElement("Education="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain,Database.Operator_Education_Domain,TempS.Education));
      }
      else if (TempTargetClass==7) {
        Book TempB = (Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempB.OID)+" : Code="
          +TempB.Code+" : Publisher="+TempB.Publisher+" : Title="+TempB.Title));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempB.Code));
        else
        if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempB.Publisher));
        else
        if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempB.Title));
      }
      else if (TempTargetClass==8) {
        Science_Book TempSB = (Science_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempSB.OID)+" : Code="
          +TempSB.Code+" : Publisher="+TempSB.Publisher+" : Title="+TempSB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempSB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempSB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempSB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempSB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempSB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempSB.Subject));
      }
      else if (TempTargetClass==9) {
        Engineering_Book TempEB = (Engineering_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempEB.OID)+" : Code="
          +TempEB.Code+" : Publisher="+TempEB.Publisher+" : Title="+TempEB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempEB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempEB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempEB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempEB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempEB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempEB.Subject));
      }
      else if (TempTargetClass==10) {
        Computer_Science_Book TempCSB = (Computer_Science_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempCSB.OID)+" : Code="
          +TempCSB.Code+" : Publisher="+TempCSB.Publisher+" : Title="+TempCSB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempCSB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempCSB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempCSB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempCSB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempCSB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempCSB.Subject));
      }
      else if (TempTargetClass==11) {
        Computer_Engineering_Book TempCEB = (Computer_Engineering_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempCEB.OID)+" : Code="
          +TempCEB.Code+" : Publisher="+TempCEB.Publisher+" : Title="+TempCEB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempCEB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempCEB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempCEB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempCEB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempCEB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempCEB.Subject));
      }
      else if (TempTargetClass==12) {
        Algorithms_Book TempAB = (Algorithms_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempAB.OID)+" : Code="
          +TempAB.Code+" : Publisher="+TempAB.Publisher+" : Title="+TempAB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempAB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempAB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempAB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempAB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempAB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempAB.Subject));
      }
      else if (TempTargetClass==13) {
        Robotics_Book TempRB = (Robotics_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempRB.OID)+" : Code="
          +TempRB.Code+" : Publisher="+TempRB.Publisher+" : Title="+TempRB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempRB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempRB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempRB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempRB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempRB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempRB.Subject));
      }
      else if (TempTargetClass==14) {
        Database_Book TempDB = (Database_Book) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempDB.OID)+" : Code="
          +TempDB.Code+" : Publisher="+TempDB.Publisher+" : Title="+TempDB.Title
          +" : Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempDB.Subject)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempDB.OID)));
        else if (Target_Attribute.compareTo("code")==0)
          ResultObjectsStr.addElement(new String("Code="+TempDB.Code));
        else if (Target_Attribute.compareTo("publisher")==0)
          ResultObjectsStr.addElement(new String("Publisher="+TempDB.Publisher));
        else if (Target_Attribute.compareTo("title")==0)
          ResultObjectsStr.addElement(new String("Title="+TempDB.Title));
        else if (Target_Attribute.compareTo("subject")==0)
          ResultObjectsStr.addElement("Subject="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,TempDB.Subject));
      }
      else if (TempTargetClass==15) {
        Author TempA = (Author) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempA.OID)+" : Name&Surname="
          +TempA.Name_Surname+" : Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempA.Age)+" : Height="
          +Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempA.Height)));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempA.OID)));
        else if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempA.Name_Surname));
        else if (Target_Attribute.compareTo("age")==0)
          ResultObjectsStr.addElement("Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempA.Age));
        else if (Target_Attribute.compareTo("height")==0)
          ResultObjectsStr.addElement("Height="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempA.Height));
      }
      else if (TempTargetClass==16) {
        Native_Author TempNA = (Native_Author) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempNA.OID)+" : Name&Surname="
          +TempNA.Name_Surname+" : Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempNA.Age)+" : Height="
          +Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempNA.Height)
          +" : City="+TempNA.City));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempNA.OID)));
        else if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempNA.Name_Surname));
        else if (Target_Attribute.compareTo("age")==0)
          ResultObjectsStr.addElement("Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempNA.Age));
        else if (Target_Attribute.compareTo("height")==0)
          ResultObjectsStr.addElement("Height="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempNA.Height));
        else if (Target_Attribute.compareTo("city")==0)
          ResultObjectsStr.addElement(new String("City="+TempNA.City));
      }
      else if (TempTargetClass==17) {
        Foreign_Author TempFA = (Foreign_Author) ResultObjects.elementAt(i);
        if (Target_Attribute.compareTo("*")==0) {
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempFA.OID)+" : Name&Surname="
          +TempFA.Name_Surname+" : Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempFA.Age)+" : Height="
          +Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempFA.Height)
          +" : Nationality="+TempFA.Nationality));
        }
        else
        if (Target_Attribute.compareTo("oid")==0)
          ResultObjectsStr.addElement(new String("OID="+Integer.toString(TempFA.OID)));
        else if (Target_Attribute.compareTo("name_surname")==0)
          ResultObjectsStr.addElement(new String("Name&Surname="+TempFA.Name_Surname));
        else if (Target_Attribute.compareTo("age")==0)
          ResultObjectsStr.addElement("Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempFA.Age));
        else if (Target_Attribute.compareTo("height")==0)
          ResultObjectsStr.addElement(new String("Height="+TempFA.Height));
        else if (Target_Attribute.compareTo("nationality")==0)
          ResultObjectsStr.addElement(new String("Nationality="+TempFA.Nationality));
      }
      else {
        System.out.println("200- There is not such a class !");
        System.exit(1);
      }
    }
  }

  public void Print_ResultBitStrings() {
    Result_BitString ResultBitString;
    for (int i=0; i<this.ResultBitStrings.size();i++) {
      ResultBitString = (Result_BitString) this.ResultBitStrings.elementAt(i);
      ResultBitString.Print_ResultBitString();
    }
  }

  public void GetResultBitStrings(ObjectStr TempRBS) {
    Result_BitString ResultBitString;
    for (int i=0; i<this.ResultBitStrings.size();i++) {
      ResultBitString = (Result_BitString) this.ResultBitStrings.elementAt(i);
      ResultBitString.GetResultBitString(TempRBS);
    }
  }

  public void Print_ResultBitStrings_AND() {
    AND_Condition ANDCondition;
    for (int i=0; i<ANDConditions.size();i++) {
      ANDCondition = (AND_Condition) ANDConditions.elementAt(i);
      ANDCondition.Print_ResultBitStrings();
      System.out.println("----------------");
    }
  }

  public String ExtractClassName(String CN) { // extract the classname from the string "abc.classname"
    for (int i=0;i<CN.length();i++) {
      if (CN.charAt(i)=='.') {
        return(CN.substring(i+1).toLowerCase());
      }
    }
    return("");
  }
}