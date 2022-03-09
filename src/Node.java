import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 02</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

class Node {
  boolean IsLeafNode;   	// true if Leaf Node
  Vector NRs;			// Vector which stroes Node Record
  Node Parent;		        // Parent Node
  Node PrevNode;		// Previous Node in the same level
  Node NextNode;		// Next Node in the same level

      static DataBucketRecord Temp_DBR = null; // sil
      static DataBucketRecord Temp_DBR_old = null;  // sil

  Node(boolean IsLeaf) { 	// Is this node a Leaf Node
    IsLeafNode=IsLeaf;
    NRs = new Vector(Database.MAX_NUMBER_OF_RECORD_IN_A_NODE);
  }

  protected void finalize() throws Throwable {
  }

  public void ArrangeNode(int pos,ObjectInt Overflow,NodeRecord NR1,NodeRecord NR2,int N,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // N: Number of node record to dela with
    if (NRs.size()<Database.MAX_NUMBER_OF_RECORD_IN_A_NODE) { // if there is a place for new NodeRecord
      if (N==1) { // there is only one node to insert
        NodeRecord NewNR = new NodeRecord(NR1.Key);
        NewNR.ChildNode=NR1.ChildNode;
        NewNR.ChildDB=NR1.ChildDB;
        this.InsertNR(NewNR,DiskAccessP,DiskAccessS);
        this.MakeParent();
      }
      else { // there is one node to change and one node to insert
        NodeRecord NewNR = new NodeRecord(NR2.Key);
        NodeRecord TempNR = ((NodeRecord)NRs.elementAt(pos));
        TempNR.Key.Copy(NR1.Key);
        TempNR.ChildNode=NR1.ChildNode;
        TempNR.ChildDB=NR1.ChildDB;

        NewNR.ChildNode=NR2.ChildNode;
        NewNR.ChildDB=NR2.ChildDB;
        this.InsertNR(NewNR,DiskAccessP,DiskAccessS);
        this.MakeParent();
      }

      NR1.Key.Copy(((NodeRecord)(NRs.elementAt(NRs.size()-1))).Key);
      NR1.ChildNode=this;
      NR1.ChildDB=null;
      if (Overflow.Get()>0)
        Overflow.Set(1);
      else
        Overflow.Set(0);
    }
    else { // if there is not any place for new NodeRecord
//      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      Node NewNode = new Node(IsLeafNode);
      int Half = Database.MAX_NUMBER_OF_RECORD_IN_A_NODE/2;

      NewNode.NextNode=this.NextNode; // create new node for overflow records
      if (NewNode.NextNode!=null)
        NewNode.NextNode.PrevNode=NewNode;
      NewNode.PrevNode=this;
      this.NextNode=NewNode;

      if (pos<Half) { // move the mid record and second half into new node
        for (int i=NRs.size()-1;i>=Half;i--) {
          NewNode.InsertNR(((NodeRecord)(NRs.elementAt(i))),DiskAccessP,DiskAccessS);
          NRs.removeElementAt(i);
        }

        NodeRecord NewNR;
        NodeRecord TempNR;

        if (N==1) {
          NewNR = new NodeRecord(NR1.Key); // insert new node record
          NewNR.ChildNode=NR1.ChildNode;
          NewNR.ChildDB=NR1.ChildDB;
          InsertNR(NewNR,DiskAccessP,DiskAccessS);
        }
        else {
          TempNR = ((NodeRecord)NRs.elementAt(pos));
          NewNR = new NodeRecord(NR2.Key); // update the record at given position
          TempNR.Key.Copy(NR1.Key);
          TempNR.ChildNode=NR1.ChildNode;
          TempNR.ChildDB=NR1.ChildDB;

          NewNR.ChildNode=NR2.ChildNode;
          NewNR.ChildDB=NR2.ChildDB;
          InsertNR(NewNR,DiskAccessP,DiskAccessS);
        }

        NR1.Key.Copy(((NodeRecord)(NRs.elementAt(NRs.size()-1))).Key); // save the last record in the node
        NR1.ChildNode=this;
        NR1.ChildDB=null;

        NR2.Key.Copy(((NodeRecord)(NewNode.NRs.elementAt(NewNode.NRs.size()-1))).Key); // save the last record in the new node
        NR2.ChildNode=NewNode;
        NR2.ChildDB=null;
      }
      else if (pos>=Half) { // move the second half into new node
        for (int i=NRs.size()-1;i>Half;i--) {
          NewNode.InsertNR(((NodeRecord)NRs.elementAt(i)),DiskAccessP,DiskAccessS);
          NRs.removeElementAt(i);
        }

        if (N==1) {
          NodeRecord NewNR = new NodeRecord(NR1.Key);
          NewNR.ChildNode=NR1.ChildNode;
          NewNR.ChildDB=NR1.ChildDB;
          NewNode.InsertNR(NewNR,DiskAccessP,DiskAccessS);
        }
        else {
          NodeRecord TempNR;
          if (pos==Half) { // update the record at given position
            TempNR = ((NodeRecord)NRs.elementAt(pos));
          }
          else {
            TempNR = ((NodeRecord)NewNode.NRs.elementAt(pos-Half-1));
          }
          TempNR.Key.Copy(NR1.Key);
          TempNR.ChildNode=NR1.ChildNode;
          TempNR.ChildDB=NR1.ChildDB;

          NodeRecord NewNR = new NodeRecord(NR2.Key);
          NewNR.ChildNode=NR2.ChildNode;
          NewNR.ChildDB=NR2.ChildDB;
          NewNode.InsertNR(NewNR,DiskAccessP,DiskAccessS);
        }

        NR1.Key.Copy(((NodeRecord)(NRs.elementAt(NRs.size()-1))).Key); // save the last record in the node
        NR1.ChildNode=this;
        NR1.ChildDB=null;

        NR2.Key.Copy(((NodeRecord)(NewNode.NRs.elementAt(NewNode.NRs.size()-1))).Key); // save the last record in the new node
        NR2.ChildNode=NewNode;
        NR2.ChildDB=null;
      }

      this.MakeParent();
      NewNode.MakeParent();
      Overflow.Set(2);
    }
  }

  public void MakeParent() {
    for (int i=0;i<NRs.size();i++) {
      if (IsLeafNode) {
        DataBucket TempDB = ((NodeRecord) NRs.elementAt(i)).ChildDB;
        TempDB.Parent=this;
      }
      else {
        Node TempNode = ((NodeRecord) NRs.elementAt(i)).ChildNode;
        TempNode.Parent=this;
      }
    }
  }

  public boolean InsertNR(NodeRecord NRec, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
//    DiskAccessP.Set(DiskAccessP.Get()+1);
    DiskAccessS.Set(DiskAccessS.Get()+1);
    if (NRs.size()<Database.MAX_NUMBER_OF_RECORD_IN_A_NODE) {
      if (NRec!=null) {
        NodeRecord TempNR=null;
        int i;

        for (i=0;i<NRs.size();i++) {
          TempNR = (NodeRecord) NRs.elementAt(i);
          if (! (NRec.Key.GreaterThan(TempNR.Key)))
            break;
        };

        if (i<NRs.size()) {
          if (NRec.Key.EqualTo(TempNR.Key))
            return (false);
        }

        NRs.insertElementAt(NRec,i);
        return (true);
      }
    }
    return (false);
  }

  public void Insert(BitString Key,Vector PIs,ObjectInt Overflow,NodeRecord NR1,NodeRecord NR2,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // insert the Key into the node
//    DiskAccessP.Set(DiskAccessP.Get()+1);
    DiskAccessS.Set(DiskAccessS.Get()+1);
    if (!IsLeafNode) {	// if different than the leaf node
      do {
        if (NRs.size()>0) {
          NodeRecord TempNR=null;
          int pos=0;

          while (pos<NRs.size()) {  // while the last node record is not reached
            TempNR = (NodeRecord)NRs.elementAt(pos);
            if ((TempNR.Key.GreaterThan(Key)) || (TempNR.Key.Include(Key)) || (TempNR.Key.EqualTo(Key))) {
            // if find the correct node record, break
              break;
            }
            pos++;	// try next node record
          }
          if (pos==NRs.size())
            pos=NRs.size()-1;
          // if an appropriate node record is found
          TempNR.ChildNode.Insert(Key,PIs,Overflow,NR1,NR2,DiskAccessP,DiskAccessS);	// insert the key into the child node
          if (Overflow.Get()==2) {			// if overflow occured
            ArrangeNode(pos,Overflow,NR1,NR2,2,DiskAccessP,DiskAccessS);
            if (Overflow.Get()==2)			// if still overflow
              return;
            else if (Overflow.Get()==0) {
              NodeRecord TempNRec1 = (NodeRecord)NRs.elementAt(pos);
              NodeRecord TempNRec2;
              TempNRec1.Key.Copy(NR1.Key);
              TempNRec1.ChildDB = NR1.ChildDB;
              TempNRec1.ChildNode = NR1.ChildNode;

              TempNRec2 = (NodeRecord)NRs.elementAt(NRs.size()-1);
              NR1.Key.Copy(TempNRec2.Key);
              NR1.ChildDB = null;
              NR1.ChildNode=this;
            }
          }
          else if (Overflow.Get()==0) {
            NodeRecord TempNRec1 = (NodeRecord)NRs.elementAt(pos);
            NodeRecord TempNRec2;
            TempNRec1.Key.Copy(NR1.Key);
            TempNRec1.ChildDB = NR1.ChildDB;
            TempNRec1.ChildNode = NR1.ChildNode;

            TempNRec2 = (NodeRecord)NRs.elementAt(NRs.size()-1);
            NR1.Key.Copy(TempNRec2.Key);
            NR1.ChildDB = null;
            NR1.ChildNode=this;
          }
       }
      } while (Overflow.Get()>0);	// do while overflow occurs
    }
    else { // if leaf node
      if (NRs.size()>0) {
        NodeRecord TempNR=null;
        int pos=0;

        while (pos<NRs.size()) {								// while the last node record is not reached
          TempNR = (NodeRecord)NRs.elementAt(pos);
          if ((TempNR.Key.Include(Key)) || (TempNR.Key.EqualTo(Key))) {
          // if find the correct node record, break
            break;
          }
          else if (Key.LessThan(TempNR.Key)) {
            this.AddLostDB(Key,PIs,Overflow,this,pos,NR1,NR2,DiskAccessP,DiskAccessS);
            return;
          }
          pos++;	// try next node record
        }

        if (pos<NRs.size()) {	// if an appropriate node record is found
          DataBucket TempDB = TempNR.ChildDB;
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);

          if (TempDB.DBRs.size()<Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET) {
          // if there is free space for new Data Bucket Record
//            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            DataBucketRecord TempDBR = new DataBucketRecord(Key,PIs);
            TempDB.InsertDBR(TempDBR,DiskAccessP,DiskAccessS);
            Overflow.Set(0);
          }
          else
          { // if there is not any free space for new Data Bucket Record
            DataBucketRecord TempDBR = new DataBucketRecord(Key,PIs);
//            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            if (TempDB.InsertDBR(TempDBR,DiskAccessP,DiskAccessS)) {
              Overflow.Set(0);
              this.MakeParent();
            }
            else {
              DataBucket NewDB = new DataBucket();
              NewDB.Next = TempDB.Next;
              NewDB.Prev = TempDB;
              TempDB.Next = NewDB;
              boolean inserted=false;
              if (NewDB.Next != null) {
                NewDB.Next.Prev = NewDB;
              }

              if (TempNR.Key.BitCount==Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING) {
                System.out.println("4- Maximum Number of Bits is used !");
                System.exit(1);
              }

              NR1.Key.SetBits(TempNR.Key.GetBitString()+"0"); // two new node record is created
              NR1.ChildDB=TempNR.ChildDB;
              NR1.ChildNode=null;

              NR2.Key.SetBits(TempNR.Key.GetBitString()+"1");
              NR2.ChildDB=NewDB;
              NR2.ChildNode=null;

              NodeRecord Temp_NR1=NR1;
              NodeRecord Temp_NR2=NR2;
              while (!inserted) {
                for (int pos2=Temp_NR1.ChildDB.DBRs.size()-1;pos2>=0;pos2--) {
                  if ((Temp_NR2.Key.Include(((DataBucketRecord)(Temp_NR1.ChildDB.DBRs.elementAt(pos2))).Key)) ||
                    (Temp_NR2.Key.EqualTo(((DataBucketRecord)(Temp_NR1.ChildDB.DBRs.elementAt(pos2))).Key))) {
                    Temp_NR2.ChildDB.InsertDBR((DataBucketRecord)(Temp_NR1.ChildDB.DBRs.elementAt(pos2)),DiskAccessP,DiskAccessS);
                    Temp_NR1.ChildDB.DBRs.removeElementAt(pos2);
                  }
                }

                if ((Temp_NR1.Key.Include(Key)) || (Temp_NR1.Key.EqualTo(Key))) {
//                  DiskAccessP.Set(DiskAccessP.Get()+1);
                  DiskAccessS.Set(DiskAccessS.Get()+1);
                  Temp_NR1.ChildDB.InsertDBR(TempDBR,DiskAccessP,DiskAccessS);
                }
                else if ((Temp_NR2.Key.Include(Key)) || (Temp_NR2.Key.EqualTo(Key))) {
//                  DiskAccessP.Set(DiskAccessP.Get()+1);
                  DiskAccessS.Set(DiskAccessS.Get()+1);
                  Temp_NR2.ChildDB.InsertDBR(TempDBR,DiskAccessP,DiskAccessS);
                }

                if ((Temp_NR1.ChildDB.DBRs.size()>0) && (Temp_NR2.ChildDB.DBRs.size()>0))
                  inserted=true;
                else {
                  BitString Temp_Key= new BitString();
                  if (NR1.Key.BitCount==Database.MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING) {
                    System.out.println("30 - HATA");
                    System.exit(1);
                  }

                  if (Temp_NR1.ChildDB.DBRs.size()>0) {
                    Temp_Key.Copy(Temp_NR1.Key);
                    NR1.Key.SetBits(Temp_Key.GetBitString()+"0");
                    NR2.Key.SetBits(Temp_Key.GetBitString()+"1");
                  }
                  else {
                    Temp_Key.Copy(Temp_NR2.Key);
                    NR1.Key.SetBits(Temp_Key.GetBitString()+"0");
                    NR2.Key.SetBits(Temp_Key.GetBitString()+"1");

                    NodeRecord Temp_NR3=Temp_NR2;
                    Temp_NR2=Temp_NR1;
                    Temp_NR1=Temp_NR3;
                  }
                }
              }
              Overflow.Set(0);
              ArrangeNode(pos,Overflow,NR1,NR2,2,DiskAccessP,DiskAccessS);
            }
          }

          if (Overflow.Get()==0) {
            NR1.Key.Copy(((NodeRecord)(NRs.elementAt(NRs.size()-1))).Key);
            NR1.ChildNode=this;
            NR1.ChildDB=null;
          }
        }
        else {
          this.AddLostDB(Key,PIs,Overflow,this,pos,NR1,NR2,DiskAccessP,DiskAccessS); // add the lost data bucket
          return;
        }
      }
    }
  }

  public void PrintNode() {
    NodeRecord Temp=null;
    int i;

    for (i=0;i<NRs.size();i++) {
      Temp = (NodeRecord) NRs.elementAt(i);
      System.out.println(Temp.Key.GetBitString());
    };
  }

  public void AddLostDB(BitString Key, Vector PIs, ObjectInt Overflow, Node TempN, int pos, NodeRecord NR1, NodeRecord NR2, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    DataBucket NewDB = new DataBucket();  // create new data bucket
    NodeRecord TempNR1=null,TempNR2=null;

    if (pos==0) {
//     DiskAccessP.Set(DiskAccessP.Get()+1);
     DiskAccessS.Set(DiskAccessS.Get()+1);
     TempNR2=(NodeRecord)TempN.NRs.elementAt(pos);
      if (TempN.PrevNode!=null)
        TempNR1=(NodeRecord)TempN.PrevNode.NRs.elementAt(TempN.PrevNode.NRs.size()-1);
      else
        TempNR1=null;

      NewDB.Next = TempNR2.ChildDB;
      NewDB.Prev = TempNR2.ChildDB.Prev;
      TempNR2.ChildDB.Prev=NewDB;
      if (NewDB.Prev!=null)
        NewDB.Prev.Next = NewDB;

      pos=pos-1;
    }
    else if (pos==NRs.size()) {
//      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      TempNR1=(NodeRecord)TempN.NRs.elementAt(pos-1);
      if (TempN.NextNode!=null)
        TempNR2=(NodeRecord)TempN.NextNode.NRs.elementAt(0);
      else
        TempNR2=null;

      NewDB.Prev = TempNR1.ChildDB;
      NewDB.Next = TempNR1.ChildDB.Next;
      TempNR1.ChildDB.Next=NewDB;
      if (NewDB.Next!=null)
        NewDB.Next.Prev = NewDB;

      pos=pos;
    }
    else {
      TempNR1=(NodeRecord)TempN.NRs.elementAt(pos-1);
      TempNR2=(NodeRecord)TempN.NRs.elementAt(pos);
      NewDB.Next = TempNR2.ChildDB;
      NewDB.Prev = TempNR2.ChildDB.Prev;
      TempNR2.ChildDB.Prev=NewDB;
      if (NewDB.Prev!=null)
        NewDB.Prev.Next = NewDB;

      pos=pos-1;
    }

    if ((TempNR1==null) && (TempNR2==null))
      NR1.Key.SetBits(BitString.GetLargestPartitionNumber(null,Key,null));
    else
    if (TempNR1==null)
      NR1.Key.SetBits(BitString.GetLargestPartitionNumber(null,Key,TempNR2.Key));
    else
    if (TempNR2==null)
      NR1.Key.SetBits(BitString.GetLargestPartitionNumber(TempNR1.Key,Key,null));
    else
      NR1.Key.SetBits(BitString.GetLargestPartitionNumber(TempNR1.Key,Key,TempNR2.Key));
    DataBucketRecord Temp_DB = new DataBucketRecord(Key,PIs);
    NewDB.InsertDBR(Temp_DB,DiskAccessP,DiskAccessS);

    NR1.ChildDB=NewDB;
    NR1.ChildNode=null;
    Overflow.Set(0);
    this.ArrangeNode(pos,Overflow,NR1,NR2,1,DiskAccessP,DiskAccessS);
  }

  public void PrintAllNode(int Level) {
    if (this!=null) {
      NodeRecord Temp=null;
      int i;

      for (i=0;i<NRs.size();i++) {
        Temp = (NodeRecord) NRs.elementAt(i);
        if (Temp.Key.BitCount==0) {
          if (!IsLeafNode)
            Temp.ChildNode.PrintAllNode(Level);
          else
            Temp.ChildDB.PrintAllDB(Level);
        }
        else {
          if (i==0) {
            if (!IsLeafNode)
              System.out.println(Database.GetSpaces(Level)+"Node");
            else
              System.out.println(Database.GetSpaces(Level)+"Leaf Node");
          }

          if (!IsLeafNode) {
            System.out.println(Database.GetSpaces(Level+1)+Temp.Key.GetBitString()+" : Node Record");
            Temp.ChildNode.PrintAllNode(Level+2);
          }
          else {
            System.out.println(Database.GetSpaces(Level+1)+Temp.Key.GetBitString()+" : Leaf Node Record");
            Temp.ChildDB.PrintAllDB(Level+2);
          }
        }
      }
    }
  }

  public void PrintAllNode(int Level,Vector Tree) {
    if (this!=null) {
      NodeRecord Temp=null;
      int i;

      for (i=0;i<NRs.size();i++) {
        Temp = (NodeRecord) NRs.elementAt(i);
        if (Temp.Key.BitCount==0) {
          if (!IsLeafNode)
            Temp.ChildNode.PrintAllNode(Level,Tree);
          else
            Temp.ChildDB.PrintAllDB(Level,Tree);
        }
        else {
          if (i==0) {
            if (!IsLeafNode)
              Tree.addElement(Database.GetSpaces(Level)+"Node");
            else
              Tree.addElement(Database.GetSpaces(Level)+"Leaf Node");
          }

          if (!IsLeafNode) {
            Tree.addElement(Database.GetSpaces(Level+1)+Temp.Key.GetBitString()+" : Node Record");
            Temp.ChildNode.PrintAllNode(Level+2,Tree);
          }
          else {
            Tree.addElement(Database.GetSpaces(Level+1)+Temp.Key.GetBitString()+" : Leaf Node Record");
            Temp.ChildDB.PrintAllDB(Level+2,Tree);
          }
        }
      }
    }
  }

  public void MoveAllNodeRecordsTo(Node TempN,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    for (int i=this.NRs.size()-1;i>=0;i--) {
      TempN.InsertNR((NodeRecord)this.NRs.elementAt(i),DiskAccessP,DiskAccessS);
      this.NRs.removeElementAt(i);
    }
  }

  public Node CheckNodeRecordNumber(ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
//    if (!this.IsLeafNode)
    {
      int i=0;
      if (this.NRs.size()<Database.MIN_NUMBER_OF_RECORD_IN_A_NODE) { // if there is not enough number of elements in the node
//        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        Node TempN2 = this.PrevNode;
        if ((TempN2!=null) && (TempN2.NRs.size()==0)) {
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          Node P=TempN2.Parent;
          if (P!=null) {
            for (i=0;i<P.NRs.size();i++) {
              if (((NodeRecord)P.NRs.elementAt(i)).ChildNode==TempN2) {
                break;
              }
            }
            if (i<P.NRs.size()) {
              if (TempN2.PrevNode!=null)
                TempN2.PrevNode.NextNode=TempN2.NextNode;
              if (TempN2.NextNode!=null)
                TempN2.NextNode.PrevNode=TempN2.PrevNode;
              P.NRs.removeElementAt(i);
              P.CheckLastRecord();
              TempN2=null;
            }
          }
        }

//        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        TempN2 = this.PrevNode;
        if (TempN2!=null) {
          if ((this.NRs.size()+TempN2.NRs.size())<=Database.MAX_NUMBER_OF_RECORD_IN_A_NODE) {
            this.MoveAllNodeRecordsTo(TempN2,DiskAccessP,DiskAccessS);
//            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            Node P=this.Parent;
            for (i=0;i<P.NRs.size();i++) {
              if (((NodeRecord)P.NRs.elementAt(i)).ChildNode==this) {
                break;
              }
            }
            if (i<P.NRs.size()) {
              P.NRs.removeElementAt(i);
              if (this.PrevNode!=null)
                this.PrevNode.NextNode=this.NextNode;
              if (this.NextNode!=null)
                this.NextNode.PrevNode=this.PrevNode;
              TempN2.MakeParent();
              TempN2.CheckLastRecord();
//            this.finalize();
              return(P.CheckNodeRecordNumber(DiskAccessP,DiskAccessS));
            }
          }
          else {
            NodeRecord TempNR = (NodeRecord)TempN2.NRs.elementAt(TempN2.NRs.size()-1);
            this.InsertNR(TempNR,DiskAccessP,DiskAccessS);
            TempN2.NRs.removeElementAt(TempN2.NRs.size()-1);
            this.MakeParent();
            TempN2.CheckLastRecord();
            this.CheckLastRecord();
          }
          return(null);
        }
        else {
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempN2=this.NextNode;
          if ((TempN2!=null) && (TempN2.NRs.size()==0)) {
//            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            Node P=TempN2.Parent;
            if (P!=null) {
              for (i=0;i<P.NRs.size();i++) {
                if (((NodeRecord)P.NRs.elementAt(i)).ChildNode==TempN2) {
                  break;
                }
              }
              if (i<P.NRs.size()) {
                if (TempN2.PrevNode!=null)
                  TempN2.PrevNode.NextNode=TempN2.NextNode;
                if (TempN2.NextNode!=null)
                  TempN2.NextNode.PrevNode=TempN2.PrevNode;
                P.NRs.removeElementAt(i);
                P.CheckLastRecord();
                TempN2=null;
              }
            }
          }
          TempN2=this.NextNode;
          if (TempN2!=null) {
            if ((this.NRs.size()+TempN2.NRs.size())<=Database.MAX_NUMBER_OF_RECORD_IN_A_NODE) {
              TempN2.MoveAllNodeRecordsTo(this,DiskAccessP,DiskAccessS);
              this.MakeParent();
              this.CheckLastRecord();
//              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              Node P=TempN2.Parent;
              for (i=0;i<P.NRs.size();i++) {
                if (((NodeRecord)P.NRs.elementAt(i)).ChildNode==TempN2) {
                  break;
                }
              }
              if (i<P.NRs.size()) {
                P.NRs.removeElementAt(i);

                if (TempN2.PrevNode!=null)
                  TempN2.PrevNode.NextNode=TempN2.NextNode;
                if (TempN2.NextNode!=null)
                TempN2.NextNode.PrevNode=TempN2.PrevNode;
//            TempN2.finalize();
                return(P.CheckNodeRecordNumber(DiskAccessP,DiskAccessS));
              }
            }
            else { //-------------------------acaba
              NodeRecord TempNR = (NodeRecord)TempN2.NRs.elementAt(0);
              this.InsertNR(TempNR,DiskAccessP,DiskAccessS);
              TempN2.NRs.removeElementAt(0);
              this.MakeParent();
              TempN2.CheckLastRecord();
              this.CheckLastRecord();
            }
            return(null);
          }
          else {
            if (this.Parent != null) { // if this is not the root node
              Node TempNode1=this.Parent;
              Node TempNode2;

              while (TempNode1!=null) {
                TempNode2=TempNode1;
                TempNode1=TempNode1.Parent;
//            TempNode2.finalize();
              }
              return(TempNode1);
            }
            if ((!this.IsLeafNode) &&(this.NRs.size()==1)) {
              Node NewRoot = ((NodeRecord)this.NRs.elementAt(0)).ChildNode;
              NewRoot.Parent=null;
//          this.finalize();
              return(NewRoot);
            }
            return(null);
          }
        }
      }
      else {
        this.CheckLastRecord();
        return(null);
      }
    }
/*    else {
      return(null);
    }*/
  }

  public void CheckLastRecord() {
    Node TempN=this.Parent;
    int i=0;

    if ((TempN!=null) && (this.NRs.size()>0)) {
      NodeRecord TempNR1 = (NodeRecord)this.NRs.elementAt(this.NRs.size()-1); // last record in the node
      NodeRecord TempNR2=null;

      for (i=0;i<TempN.NRs.size();i++) {
        TempNR2=(NodeRecord)TempN.NRs.elementAt(i);
        if (TempNR2.ChildNode==this) {
          break;
       }
      }
      if (i<TempN.NRs.size()) {
        if (TempNR1.Key.GetBitString().compareTo(TempNR2.Key.GetBitString())!=0) {
          TempNR2.Key.Copy(TempNR1.Key);
          TempN.CheckLastRecord();
        }
      }
      else {
        System.out.println("10- HATA");
//       exit(1);
      }
   }
  }

  public void Test_Parent(int Level) {
    if (this!=null) {
      NodeRecord Temp=null;
      int i;

      for (i=0;i<this.NRs.size();i++) {
        Temp = (NodeRecord) this.NRs.elementAt(i);

        if (!this.IsLeafNode) {
          if (Temp.ChildNode.Parent != this)
            System.out.println("Hata:Level:"+Integer.toString(Level)+"   Pos:"+Integer.toString(i));
          else
            System.out.println("OK  :Level:"+Integer.toString(Level)+"   Pos:"+Integer.toString(i));
          Temp.ChildNode.Test_Parent(Level+1);
        }
        else {
          if (Temp.ChildDB.Parent != this)
            System.out.println("Hata:Level:"+Integer.toString(Level)+"   Pos:"+Integer.toString(i)+" : DB");
          else
            System.out.println("OK  :Level:"+Integer.toString(Level)+"   Pos:"+Integer.toString(i));
        }
      }
    }
  }

  public void Test_Neighbours(int Level) {
    if (this!=null) {
      NodeRecord Temp=null;
      int i;

      if (this.PrevNode!=null)
        if (this.PrevNode.NextNode==this)
          System.out.print(Level+" Prev OK : ");
        else
          System.out.print(Level+" Prev HATA : ");
      else if (this.NextNode!=null)
        if (this.NextNode.PrevNode==this)
          System.out.print(Level+" Next OK : ");
        else
          System.out.print(Level+" Next HATA : ");
      System.out.println();

      for (i=0;i<this.NRs.size();i++) {
        Temp = (NodeRecord) this.NRs.elementAt(i);
        if (Temp.ChildNode!=null)
          Temp.ChildNode.Test_Neighbours(Level+1);
        else if (Temp.ChildDB!=null)
          Temp.ChildDB.Test_DBs();
      }
    }
  }

  public Node CheckComplements(ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
      Node TempN=null;
      Node TempN2=null;
      Node Temp_Node1=null;
      Node Temp_Node2=null;
      NodeRecord TempNR=null;
      NodeRecord TempNR2=null;
      DataBucket TempDB=null;
      DataBucket TempDB2=null;
      boolean Before=false, Current=false, After=false;
      int pos;
      int pos1;
      int pos2;

      for (pos=0;pos<=this.NRs.size();pos++) {
        if (pos==0) { // if the node record is the first one in the node
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempNR=(NodeRecord)this.NRs.elementAt(pos); // current node record to check
          TempN = this;
          TempN2 = this.PrevNode;

          if ((TempN2==null) || (TempN2.NRs.size()==0))
            continue;

          TempNR2=(NodeRecord)TempN2.NRs.elementAt(TempN2.NRs.size()-1); // last record in the previous node
          pos1=pos;
          pos2=TempN2.NRs.size()-1;
        }
        else if (pos==this.NRs.size()) {
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempNR2=(NodeRecord)this.NRs.elementAt(pos-1); // current node record to check
          TempN = this.NextNode;
          TempN2 = this;

          if ((TempN==null) || (TempN.NRs.size()==0))
            continue;

          TempNR=(NodeRecord)TempN.NRs.elementAt(0); // first record in the next node
          pos1=0;
          pos2=pos-1;
        }
        else {
          TempNR=(NodeRecord)this.NRs.elementAt(pos); // current node record to check
          TempN = this;
          TempN2 = this;
          TempNR2=(NodeRecord)TempN2.NRs.elementAt(pos-1); // last record in the previous node
          pos1=pos;
          pos2=pos-1;
        }

        String S1=TempNR.Key.Complement().GetBitString(); // check if the two node records are the complements
        String S2=TempNR2.Key.GetBitString();

        if (S1.compareTo(S2)==0) {
//          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          TempDB = TempNR.ChildDB;
          TempDB2 = TempNR2.ChildDB;
          if ((TempDB.DBRs.size()+TempDB2.DBRs.size())<=Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET) {
            // if the # of data bucket record within two data buckets are not the maximum, merge them
            for (int i=TempDB.DBRs.size()-1;i>=0;i--) {
              TempDB2.InsertDBR((DataBucketRecord)TempDB.DBRs.elementAt(i),DiskAccessP,DiskAccessS);
              TempDB.DBRs.removeElementAt(i);
            }
//                  TempN2.MakeParent();


            if (pos==0) {
              Before=true;
            }
            else if (pos==this.NRs.size()) {
              After=true;
            }
            else {
              Current=true;
            }

            TempN.NRs.removeElementAt(pos1);
            TempNR2.Key.Copy(BitString.Get_Biggest_Common_Partition_Number(TempN2,pos2));


//          TempDB.finalize();
            if ((pos==0) || (pos==this.NRs.size())) {
              TempN.CheckLastRecord();
              TempN2.CheckLastRecord();
            }
            else {
              TempN.CheckLastRecord();
            }
            break;
          }
        }
      }
      if (Before==true) {
        Temp_Node1 = this.CheckNodeRecordNumber(DiskAccessP,DiskAccessS);
        Temp_Node2 = this.PrevNode.CheckComplements(DiskAccessP,DiskAccessS);

        if (Temp_Node2!=null)
          return(Temp_Node2);
        else
          return(Temp_Node1);
      }
      else if (Current==true) {
        Temp_Node1 = this.CheckNodeRecordNumber(DiskAccessP,DiskAccessS);
        if (this.NRs.size()>0)
          Temp_Node2 = this.CheckComplements(DiskAccessP,DiskAccessS);
        else if (this.PrevNode!=null)
          Temp_Node2 = this.PrevNode.CheckComplements(DiskAccessP,DiskAccessS);

        if (Temp_Node2!=null)
          return(Temp_Node2);
        else
          return(Temp_Node1);
      }
      else if (After==true) {
        Temp_Node1 = this.NextNode.CheckNodeRecordNumber(DiskAccessP,DiskAccessS);
        Temp_Node2 = this.CheckComplements(DiskAccessP,DiskAccessS);

        if (Temp_Node2!=null)
          return(Temp_Node2);
        else
          return(Temp_Node1);
      }
      else
        return(null);
  }

  public void Test_PIN_Neighbours(boolean Write) {
    if (this!=null) {
      NodeRecord Temp=null;
      int i,j,k;

      for (k=0;k<this.NRs.size();k++) {
        Temp = (NodeRecord) this.NRs.elementAt(k);
        if (Temp.ChildNode!=null)
          Temp.ChildNode.Test_PIN_Neighbours(Write);
        else if (Temp.ChildDB!=null) {
          DataBucket Temp_DB = Temp.ChildDB;
          DataBucketRecord Temp_DBR;

        for (i=0;i<Temp_DB.DBRs.size();i++) {
            Temp_DBR = (DataBucketRecord) Temp_DB.DBRs.elementAt(i);
            if (Write)
              System.out.println(Temp_DBR.Key.GetBitString());
            Path_Instantiation_Node Temp_PIN = Temp_DBR.PIN;
            if (Temp_PIN==null) {
              System.out.println("50- HATA");
              System.exit(1);
            }

            if (Temp_PIN.Type==1) {
              if ((Temp_PIN.PrevNode!=null) || (Temp_PIN.NextNode!=null)) {
                System.out.println("51- Type 1 Neighbours Hata");
                System.exit(1);
              }
              else {
                if (Write) {
                  System.out.println("OK ! Type 1");
                }
                    for (int t=0;t<Temp_PIN.PIs.size()-1;t++) {
                      if (Write) {
                        System.out.println("Size = "+Temp_PIN.PIs.size());
                        PathInst.PrintPathInst((Vector)Temp_PIN.PIs.elementAt(t));
                        System.out.println();
                        PathInst.PrintPathInst((Vector)Temp_PIN.PIs.elementAt(t+1));
                      }
                      if (PathInst.Compare((Vector)Temp_PIN.PIs.elementAt(t),(Vector)Temp_PIN.PIs.elementAt(t+1))==1) {
                        System.out.println("61- Order in PIN is wrong !");
                        System.exit(1);
                      }
                    }
              }
            }
            else if (Temp_PIN.Type==2) {
              Path_Instantiation_Node Temp_PIN2;

              if (((DirectoryRecord)Temp_PIN.Directory.elementAt(0)).PIN.PrevNode!=null) {
                System.out.println("52- Type 2 ilk PIN 3 Prev Node Neighbours Hata");
                System.exit(1);
              }

Loop_Outside:
              while (Temp_PIN!=null) {
                Path_Instantiation_Node Temp_PIN_Below1=null;
                Path_Instantiation_Node Temp_PIN_Below2=null;
                for (j=0;j<Temp_PIN.Directory.size();j++) {
                  if (j==Temp_PIN.Directory.size()-1) {
                    Temp_PIN_Below1 = ((DirectoryRecord)Temp_PIN.Directory.elementAt(j)).PIN;
                    if (Temp_PIN.NextNode!=null)
                      Temp_PIN_Below2 = ((DirectoryRecord)Temp_PIN.NextNode.Directory.elementAt(0)).PIN;
                    else
                      if (Temp_PIN_Below1.NextNode!=null) {
                        System.out.println("53- Type 2 son PIN 3 Next Node Neighbours Hata");
                        System.exit(1);
                      }
                      else {
                        if (Write)
                          System.out.println("OK ! Type 2");
                        break Loop_Outside;
                      }
                  }
                  else {
                    Temp_PIN_Below1 = ((DirectoryRecord)Temp_PIN.Directory.elementAt(j)).PIN;
                    Temp_PIN_Below2 = ((DirectoryRecord)Temp_PIN.Directory.elementAt(j+1)).PIN;
                  }

                  if ((Temp_PIN_Below1.NextNode!=Temp_PIN_Below2) || (Temp_PIN_Below2.PrevNode!=Temp_PIN_Below1)) {
                    System.out.println("54- Type 2 PIN 3 Neighbours Hata");
                    System.exit(1);
                  }
                  else {
                    for (int t=0;t<Temp_PIN_Below1.PIs.size()-1;t++) {
                      if (Write) {
                        System.out.println("Size = "+Temp_PIN_Below1.PIs.size());
                        PathInst.PrintPathInst((Vector)Temp_PIN_Below1.PIs.elementAt(t));
                        System.out.println();
                        PathInst.PrintPathInst((Vector)Temp_PIN_Below1.PIs.elementAt(t+1));
                      }
                      if (PathInst.Compare((Vector)Temp_PIN_Below1.PIs.elementAt(t),(Vector)Temp_PIN_Below1.PIs.elementAt(t+1))==1) {
                        System.out.println("60- Order in PIN is wrong !");
                        System.exit(1);
                      }
                    }
                  }

                }
                Temp_PIN = Temp_PIN.NextNode;
              }
            }
          }

        }
      }
    }
  }

  public void Test_PIs(boolean Write) {
    if (this!=null) {
      NodeRecord Temp=null;
      DataBucket Temp_DB = null;
      int i,j,k;
      Temp_DBR=null;
      Temp_DBR_old=null;

      for (k=0;k<this.NRs.size();k++) {
        Temp = (NodeRecord) this.NRs.elementAt(k);
        if (Temp.ChildNode!=null)
          Temp.ChildNode.Test_PIs(Write);
        else if (Temp.ChildDB!=null) {
          Temp_DB = Temp.ChildDB;

        for (i=0;i<Temp_DB.DBRs.size();i++) {
            Temp_DBR_old = Temp_DBR;
            Temp_DBR = (DataBucketRecord) Temp_DB.DBRs.elementAt(i);

            if (Write)
              System.out.println(Temp_DBR.Key.GetBitString());
            if (Temp_DBR_old!=null) {
              if (Temp_DBR_old.Key.GreaterThan(Temp_DBR.Key)) {
                System.out.println("70- Smaller PI");
                System.out.println();
                System.out.println(Temp_DBR_old.Key.GetBitString());
                System.out.println(Temp_DBR.Key.GetBitString());
                System.exit(1);
              }
            }
          }
        }
      }
    }
  }
/*
  public static void main (String args[]) {
    Node N = new Node(false);
    NodeRecord NR = new NodeRecord("00111");
    N.InsertNR(NR);
    N.InsertNR(new NodeRecord("00110"));
    N.InsertNR(new NodeRecord("00101"));
    N.InsertNR(new NodeRecord("00101"));
    N.PrintNode();
  }
*/
}