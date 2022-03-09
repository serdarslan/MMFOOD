import java.util.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

class FOOD_Index {
  Node Root;
  DataBucket FirstDB;	// First DataBucket in Data Bucket level
  Node FirstLeafNode;// First Leaf Node in Leaf Node level

  FOOD_Index() {
    Root = null;
    FirstDB = null;
    FirstLeafNode = null;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void Insert(String BS,Vector PIs,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Insert(new BitString(BS),PIs,DiskAccessP,DiskAccessS);
  }

  public void Insert(BitString BS,Vector PIs,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    ObjectInt Overflow = new ObjectInt(0);
    NodeRecord NR1 = new NodeRecord();
    NodeRecord NR2 = new NodeRecord();

    if (Root==null) {
      Node NewNode = new Node(true);				// create a leaf node
      DataBucket Temp_DB = new DataBucket();			// create one data buckets for 0 and 1
      NodeRecord TempNR = new NodeRecord("");			// create one node record

      FirstDB = Temp_DB;					// set the first DB as FirstDB
      TempNR.ChildDB = Temp_DB;					// link the node records and DBs
      Temp_DB.Parent=NewNode;

      NewNode.InsertNR(TempNR,DiskAccessP,DiskAccessS);                      // insert Node Record into the Node

      Root = NewNode;						// Set the new node as root
      FirstLeafNode = NewNode;
      // DiskAccess is not increased for SM case since it will be increased in next { }'s
    }

    do {
      Root.Insert(BS,PIs,Overflow,NR1,NR2,DiskAccessP,DiskAccessS);			// insert the bit string into FOOD Index

      if (Overflow.Get()==2) {			// if an overflow occured
        Node OverflowNode = new Node(false);		// create a higher level node as overflow node
        NodeRecord NodeRecord1 = new NodeRecord(NR1);   // create new node record for overfow records
        NodeRecord NodeRecord2 = new NodeRecord(NR2);

        OverflowNode.InsertNR(NodeRecord1,DiskAccessP,DiskAccessS);	// Insert overflow node record into overflow node
        OverflowNode.InsertNR(NodeRecord2,DiskAccessP,DiskAccessS);
        Root = OverflowNode;
        Root.MakeParent();
        // set overflow node as root node
      }

    } while (Overflow.Get()>0);
  }

  protected void finalize() throws Throwable {
  }

  public void PrintFOODIndex () {
    System.out.println("-----------------------------");
    System.out.println("FOOD Index");
    if (Root!=null)
      Root.PrintAllNode(0);
  }

  public void PrintFOODIndex (Vector Tree) {
    Tree.addElement("FOOD Index");
    if (Root!=null)
      Root.PrintAllNode(0,Tree);
  }

  public void Test_Parent () {
    System.out.println("-----------------------------");
    System.out.println("Parent Test");
    if (Root!=null)
      Root.Test_Parent(0);
  }

  public void Test_Neighbours () {
    System.out.println("-----------------------------");
    System.out.println("Neighbours Test");
    if (Root!=null)
      Root.Test_Neighbours(0);
  }

  public void Delete(String BS,Object O,int Pos,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Delete(new BitString(BS),O,Pos,DiskAccessP,DiskAccessS);
  }

  public boolean Delete (BitString Key,Object O,int Pos, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    Node TempN = Root;
    NodeRecord TempNR=null;
    DataBucket TempDB=null;
    DataBucketRecord TempDBR=null;
    int pos=0;
    int pos2=0;
    int pos3=0;

    while (!TempN.IsLeafNode) {
//      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      if (TempN.NRs.size()>0) {
        pos=0;
        while (pos<TempN.NRs.size()) {	// while the last node record is not reached
          TempNR = (NodeRecord)TempN.NRs.elementAt(pos);
          if ((TempNR.Key.GreaterThan(Key)) || (TempNR.Key.Include(Key)) || (TempNR.Key.EqualTo(Key))) {
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
          System.out.println("5- Hata"); // FOOD Index is in a inconsistent state
          return(false);
        }
      }
      else {
        System.out.println("6- Hata"); // FOOD Index is in a inconsistent state
        return(false);
      }
    }

    if (TempN.NRs.size()>0) {
      pos=0;
      while (pos<TempN.NRs.size()) {			// while the last node record is not reached
        TempNR = (NodeRecord)TempN.NRs.elementAt(pos);
        if ((TempNR.Key.Include(Key)) || (TempNR.Key.EqualTo(Key))) {
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
        System.out.println("7- Hata"); // FOOD Index is in a inconsistent state
        return(false);
      }
    }
    else {
      System.out.println("8- Hata"); // FOOD Index is in a inconsistent state
      return(false);
    }

    for (int new_pos=0;new_pos<TempDB.DBRs.size();new_pos++) {

      TempDBR = (DataBucketRecord) TempDB.DBRs.elementAt(new_pos);

      if (TempDBR.Key.Bits == Key.Bits) { // Bit String is found
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          if (TempDBR.PIN.Delete_PIs(O,Pos,DiskAccessP,DiskAccessS)) {// delete all the PIs given and if there is no other PIs in the PIN, delete the DBR
            TempDB.DBRs.removeElementAt(new_pos);

//            if (TempDB.DBRs.size()==0)
//              TempN.NRs.removeElementAt(pos);
            Node Temp_Node = TempN.CheckComplements(DiskAccessP,DiskAccessS);
            if (Temp_Node != null) {
              Root = Temp_Node;
              Root.MakeParent();
            }
//---------- burada son kalan bosluk node record silinecek ve root null yapilacak
//            if ((this.Root.IsLeafNode) && (this.Root.NRs.size()==0)) {
//              this.Root=null;
//            }
          }
          return(true);
      }
    }
    System.out.println("Can not be Found and Deleted !");
    System.exit(1);
    return (false);
  }

  public void Retrieve(int FromClass, double FromThreholdValue, int TargetClass, double TargetThreholdValue, String Target_Attribute, Where_Conditions WCs, Vector ResultObjectsStr, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    int AND_Condition_Count;
    int Condition_Count;

    for (AND_Condition_Count=0;AND_Condition_Count<WCs.ANDConditions.size();AND_Condition_Count++) { // for each AND Condition Group
      AND_Condition AndCondition = (AND_Condition) WCs.ANDConditions.elementAt(AND_Condition_Count);
      Result_BitString ResultBitString;
      BitString BS1 = new BitString(),BS2 = new BitString();
      int ExactMatchCount=0;

      for (Condition_Count=0;Condition_Count<AndCondition.ConditionElements.size();Condition_Count++) {
        Condition_Element CE = (Condition_Element) AndCondition.ConditionElements.elementAt(Condition_Count);
        if (CE.Field1.compareTo("=")==0) {
          ExactMatchCount++;
          if (CE.Attribute.compareTo("age")==0) {
            if (BS1.BitCount>0) {
              System.out.println("110- HATA AND Grubunda iki tane AGE esitligi var !");
              System.exit(1);
            }
            else
              BS1.SetBits(Database.Construct_Bit_String("age",CE.Field2));
          }
          else
          if (Database.INDEX_DIMENSION>1) {
            if (CE.Attribute.compareTo("height")==0) {
              if (BS2.BitCount>0) {
                System.out.println("111- HATA AND Grubunda iki tane HEIGHT esitligi var !");
                System.exit(1);
              }
              else
                BS2.SetBits(Database.Construct_Bit_String("height",CE.Field2));
           }
          }
        }
      }

      if (Database.INDEX_DIMENSION==1) {
        if (BS1.BitCount>0) {
          BitString BS_Start=null,BS_Stop=null;
          BS_Start = new BitString(BS1);
          BS_Stop = new BitString(BS_Start.GetBitString());
          ResultBitString = new Result_BitString(BS_Start,BS_Stop);
          AndCondition.ResultBitStrings.addElement(ResultBitString);
          continue;
        }
      }

      if (Database.INDEX_DIMENSION==2) {
        BitString BS_Start=null,BS_Stop=null;
        if ((BS1.BitCount>0) && (BS2.BitCount>0)) {
          BS_Start = new BitString(Database.Combine_Bit_Strings(BS1.GetBitString(),BS2.GetBitString()));
          BS_Stop = new BitString(BS_Start.GetBitString());
          ResultBitString = new Result_BitString(BS_Start,BS_Stop);
          AndCondition.ResultBitStrings.addElement(ResultBitString);
          continue;
        }
        else if ((BS1.BitCount>0) && (ExactMatchCount==1)){
          BS_Start = new BitString(Database.Combine_Bit_Strings(BS1.GetBitString(),Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"0")));
          BS_Stop = new BitString(Database.Combine_Bit_Strings(BS1.GetBitString(),Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"1")));
          ResultBitString = new Result_BitString(BS_Start,BS_Stop);
          AndCondition.ResultBitStrings.addElement(ResultBitString);
          continue;
        }
        else if ((BS2.BitCount>0) && (ExactMatchCount==1)) {
          BS_Start = new BitString(Database.Combine_Bit_Strings(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"0"),BS2.GetBitString()));
          BS_Stop = new BitString(Database.Combine_Bit_Strings(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"1"),BS2.GetBitString()));
          ResultBitString = new Result_BitString(BS_Start,BS_Stop);
          AndCondition.ResultBitStrings.addElement(ResultBitString);
          continue;
        }
      }

      for (Condition_Count=0;Condition_Count<AndCondition.ConditionElements.size();Condition_Count++) {
        BitString BS_Start=null,BS_Stop=null;
        Vector Temp_RBSs = new Vector(0,1);
        Condition_Element CE = (Condition_Element) AndCondition.ConditionElements.elementAt(Condition_Count);
        String Temp_Str = new String("");

        if (CE.Field1.compareTo("=")==0)
          ; // do nothing
        else {                               // crisp or fuzzy conditions without equality
          if ((CE.Attribute.compareTo("age")==0) || ((Database.INDEX_DIMENSION==2) && (CE.Attribute.compareTo("height")==0))) {
            if (CE.Attribute.compareTo("age")==0)
              Temp_Str = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,CE.Field1);
            else
              Temp_Str = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,CE.Field1);

            if ((Temp_Str.length()>0) && (Temp_Str.charAt(0)=='F')) { // fuzzy conditions without equality
              Database.Construct_Bit_String_Fuzzy(CE.Attribute,CE.Field1,CE.Field2,Temp_RBSs);
            }
            else { // crisp conditions without equality
              Database.Construct_Bit_String_Crisp(CE.Attribute,CE.Field1,CE.Field2,Temp_RBSs);
            }

            if (Database.INDEX_DIMENSION==1) {
              for (int Count=0;Count<Temp_RBSs.size();Count++) {
                Result_BitString Temp_RBS = (Result_BitString)Temp_RBSs.elementAt(Count);
                BS_Start = new BitString(Temp_RBS.StartBS.GetBitString());
                BS_Stop = new BitString(Temp_RBS.StopBS.GetBitString());
                ResultBitString = new Result_BitString(BS_Start,BS_Stop);
                CE.ResultBitStrings.addElement(ResultBitString);
              }
            }
            else {
              for (int Count=0;Count<Temp_RBSs.size();Count++) {
                Result_BitString Temp_RBS = (Result_BitString)Temp_RBSs.elementAt(Count);
                if (BS1.BitCount>0) {
                  if (CE.Attribute.compareTo("age")==0) {
                    System.out.println("130- HATA Ayni dimension icin hem Equality hem de < > operatorleri var !");
                    System.exit(1);
                  }
                  BS_Start = new BitString(Database.Combine_Bit_Strings(BS1,Temp_RBS.StartBS));
                  BS_Stop = new BitString(Database.Combine_Bit_Strings(BS1,Temp_RBS.StopBS));
                  ResultBitString = new Result_BitString(BS_Start,BS_Stop);
                  CE.ResultBitStrings.addElement(ResultBitString);
                }
                else
                if (BS2.BitCount>0) {
                  if (CE.Attribute.compareTo("height")==0) {
                    System.out.println("140- HATA Ayni dimension icin hem Equality hem de < > operatorleri var !");
                    System.exit(1);
                  }
                  BS_Start = new BitString(Database.Combine_Bit_Strings(Temp_RBS.StartBS,BS2));
                  BS_Stop = new BitString(Database.Combine_Bit_Strings(Temp_RBS.StopBS,BS2));
                  ResultBitString = new Result_BitString(BS_Start,BS_Stop);
                  CE.ResultBitStrings.addElement(ResultBitString);
                }
                else { // there is no equality at all
                  if (CE.Attribute.compareTo("age")==0) {
                    BS_Start = new BitString(Database.Combine_Bit_Strings(Temp_RBS.StartBS.GetBitString(),Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"0")));
                    BS_Stop = new BitString(Database.Combine_Bit_Strings(Temp_RBS.StopBS.GetBitString(),Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"1")));
                  }
                  else
                  if (CE.Attribute.compareTo("height")==0) {
                    BS_Start = new BitString(Database.Combine_Bit_Strings(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"0"),Temp_RBS.StartBS.GetBitString()));
                    BS_Stop = new BitString(Database.Combine_Bit_Strings(Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING,"1"),Temp_RBS.StopBS.GetBitString()));
                  }
                  else {
                    System.out.println("150- UNKNOWN DIMENSION !");
                    System.exit(1);
                  }
                  ResultBitString = new Result_BitString(BS_Start,BS_Stop);
                  CE.ResultBitStrings.addElement(ResultBitString);
                }
              }
            }
            Temp_RBSs.removeAllElements();
          }
        }
      } // for AND Conditions

//      AndCondition.Print_ResultBitStrings_Raw();
      AndCondition.AND_ResultBitStrings(); // ANDs the result bit strings
    } // for AND Groups

    WCs.OR_ANDResultBits(); // ORing the AND Group Bit Strings
    WCs.GetResults(Root, FromClass, FromThreholdValue, TargetClass, TargetThreholdValue, Target_Attribute, WCs, ResultObjectsStr, DiskAccessP, DiskAccessS);
  }

  public static void main(String[] args) {
  }
  private void jbInit() throws Exception {
  }
}

