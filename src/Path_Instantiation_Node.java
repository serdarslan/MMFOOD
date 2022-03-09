import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Path_Instantiation_Node {
  int Type;
  Vector Directory;
  Vector PIs;
  Path_Instantiation_Node PrevNode;
  Path_Instantiation_Node NextNode;

  public Path_Instantiation_Node(int Temp_Type) {
    Type = Temp_Type;
    Directory = new Vector(0,1);
    PIs = new Vector(0,1);
    PrevNode = null;
    NextNode = null;
  }

  public void Insert_PI_into_PIs(Vector PI) {
    for (int pos=0;pos<PIs.size();pos++) { // test all the PIs
      if (PathInst.Compare((Vector)PIs.elementAt(pos),PI)==1) { // find the correct place to insert
        PIs.insertElementAt(PI,pos);
        return;
      }
    }
    this.PIs.addElement(PI);
  }

  public void Insert_DR_into_Directory(DirectoryRecord DR) {
    for (int pos=0;pos<Directory.size();pos++) { // test all the PIs of Directory Records in the Directory
      if (PathInst.Compare(((DirectoryRecord)Directory.elementAt(pos)).PI,DR.PI)==1) { // find the correct place to insert
        Directory.insertElementAt(DR,pos);
        return;
      }
    }
    Directory.addElement(DR);
  }

  public boolean Insert_PIs(Vector PIs,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // return true if PIN is empty after insertion
    int i,pos;
    DirectoryRecord DR = new DirectoryRecord(),DR1=new DirectoryRecord(), DR2=new DirectoryRecord();
    Path_Instantiation_Node Temp_PIN=null;
    ObjectInt Overflow = new ObjectInt();
    ObjectInt Underflow = new ObjectInt();

    if (Type==1) {
      this.Eliminate_Duplicates(PIs,Underflow,DR1,DiskAccessP,DiskAccessS);
      for (i=0;i<PIs.size();i++) {
        this.Insert_PI((Vector)PIs.elementAt(i),Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
      }
      if ((Type==1) && (this.PIs.size()==0))
        return(true);
      else
        return(false);
    }
    else if (Type==2) {
      boolean PosChanged=false;
      Temp_PIN = this;
      while (Temp_PIN!=null) {
        pos=0;
        while(pos<Temp_PIN.Directory.size()) {
          PosChanged=false;
          DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos);
          DR.PIN.Eliminate_Duplicates(PIs,Underflow,DR1,DiskAccessP,DiskAccessS);
          if (Underflow.Get()==1) {
            if (pos==0) { // if it is the first directory record
              if (Temp_PIN.PrevNode!=null) {
                DiskAccessP.Set(DiskAccessP.Get()+1);
                DiskAccessS.Set(DiskAccessS.Get()+1);
                DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.PrevNode.Directory.elementAt(Temp_PIN.PrevNode.Directory.size()-1);
                Temp_DR.Copy(DR1);
              }
              else {
                System.out.println("25- Hata It is impossible to be so !");
                System.exit(1);
              }
            }
            else {
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos-1);
              Temp_DR.Copy(DR1);
            }
            Temp_PIN.Directory.removeElementAt(pos);
          }
          else if (Underflow.Get()==2) {
            if (pos==Temp_PIN.Directory.size()-1) { // if it is the last directory record
              if (Temp_PIN.NextNode!=null) {
                DiskAccessP.Set(DiskAccessP.Get()+1);
                DiskAccessS.Set(DiskAccessS.Get()+1);
                DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.NextNode.Directory.elementAt(0);
                Temp_DR.Copy(DR1);
              }
             else {
                System.out.println("26- Hata It is impossible to be so !");
                System.exit(1);
              }
            }
            else {
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos+1);
              Temp_DR.Copy(DR1);
            }

            Temp_PIN.Directory.removeElementAt(pos);
          }

          if (Temp_PIN.PrevNode!=null) {
            if ((Temp_PIN.Directory.size()+Temp_PIN.PrevNode.Directory.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
              pos=Temp_PIN.PrevNode.Directory.size()+pos;
              for (i=Temp_PIN.Directory.size()-1;i>=0;i--) { // move the records
                Temp_PIN.PrevNode.Insert_DR_into_Directory((DirectoryRecord)Temp_PIN.Directory.elementAt(i));
              }
              Temp_PIN.Directory.removeAllElements();

              Temp_PIN.PrevNode.NextNode=Temp_PIN.NextNode;
              if (Temp_PIN.NextNode!=null)
                Temp_PIN.NextNode.PrevNode=Temp_PIN.PrevNode;
              Temp_PIN=Temp_PIN.PrevNode;
              PosChanged=true;
            }
          }

          if (Temp_PIN.NextNode!=null) {
            if ((Temp_PIN.Directory.size()+Temp_PIN.NextNode.Directory.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
              pos=pos;
              for (i=Temp_PIN.NextNode.Directory.size()-1;i>=0;i--) { // move the records into the current record
                Temp_PIN.Insert_DR_into_Directory((DirectoryRecord)Temp_PIN.NextNode.Directory.elementAt(i));
              }
              Temp_PIN.NextNode.Directory.removeAllElements();

              if (Temp_PIN.NextNode.NextNode!=null)
                Temp_PIN.NextNode.NextNode.PrevNode=Temp_PIN;
              Temp_PIN.NextNode=Temp_PIN.NextNode.NextNode;
              Temp_PIN=Temp_PIN;
              PosChanged=true;
            }
          }

          if ((Temp_PIN.PrevNode==null)&&(Temp_PIN.NextNode==null)) { // if there is only one directory record
            if (Temp_PIN.Directory.size()==1) { // if in the directory is only one record
              PathInst.DuplicatePIs(Temp_PIN.PIs,((DirectoryRecord)Temp_PIN.Directory.elementAt(0)).PIN.PIs); // copy the PIs into the high level node, and turn the node to type 1
              ((DirectoryRecord)Temp_PIN.Directory.elementAt(0)).PIN.PIs.removeAllElements();
              Temp_PIN.Directory.removeAllElements();
              Temp_PIN.Type=1;
              Temp_PIN.Eliminate_Duplicates(PIs,Underflow,DR1,DiskAccessP,DiskAccessS); // elimiate the duplicates in the node
              for (i=0;i<PIs.size();i++) {
                this.Insert_PI((Vector)PIs.elementAt(i),Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
              }
              if ((Temp_PIN.PIs==null)&&(Temp_PIN.Directory.size()==0))
                return(true);
              else
                return(false);
            }
          }
          if (!PosChanged)
            pos++;
        }
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        Temp_PIN=Temp_PIN.NextNode;
      }

      for (i=0;i<PIs.size();i++) {
        this.Insert_PI((Vector)PIs.elementAt(i),Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
      }
      if ((this.PIs==null)&&(this.Directory.size()==0))
        return(true);
      else
        return(false);
    }
    return(false);
  }

  public void Insert_PI(Vector PI,ObjectInt Overflow,DirectoryRecord DR1,DirectoryRecord DR2,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    int i,pos;

    if (Type==1) {
      if (PIs.size()<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) {
        for (i=0;i<this.PIs.size();i++) { // insert the PI if find the correct place
          if (PathInst.Compare(PI,(Vector)this.PIs.elementAt(i))==2) {
            this.PIs.insertElementAt(PI,i);
            return;
          }
          else if (PathInst.Compare(PI,(Vector)this.PIs.elementAt(i))==0) {
            return;
          }
        }
        this.PIs.addElement(PI);
        Overflow.Set(0);
      }
      else {
        Type=2;
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        Path_Instantiation_Node New_PIN = new Path_Instantiation_Node(3);
        PathInst.DuplicatePIs(New_PIN.PIs,this.PIs);
        this.PIs.removeAllElements();
        Directory.removeAllElements();
        Directory.addElement(new DirectoryRecord((Vector)New_PIN.PIs.elementAt(New_PIN.PIs.size()-1),New_PIN));
        this.Insert_PI(PI,Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
      }
    }
    else if (Type==2) {
      for (pos=0;pos<this.Directory.size();pos++) {
        DirectoryRecord Temp_DirNode = (DirectoryRecord)this.Directory.elementAt(pos);

        if (PathInst.Compare(Temp_DirNode.PI,PI)<=1) { // if the PI <= PIs.elementAt(i)
          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          Temp_DirNode.PIN.Insert_PI(PI,Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
          if (Overflow.Get()==1) { // leaf path instantiation node is overflowed
            Temp_DirNode.Copy(DR1);
            if (this.Directory.size()<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // if there is an empty place for overflow Directory Record
              DirectoryRecord New_DR = new DirectoryRecord(DR2);
              this.Directory.insertElementAt(New_DR,pos+1);
            }
            else { // if there is no empty space for new DirectoryRecord
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              Path_Instantiation_Node New_PIN = new Path_Instantiation_Node(2);
              New_PIN.NextNode=this.NextNode;
              this.NextNode=New_PIN;
              New_PIN.PrevNode=this;
              if (New_PIN.NextNode!=null)
                New_PIN.NextNode.PrevNode=New_PIN;

              DirectoryRecord New_DR = new DirectoryRecord(DR2);
              int Half = Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE / 2;

              if (pos<Half) { // move the mid record and second half into new node
                for (i=this.Directory.size()-1;i>=Half;i--) {
                  New_PIN.Directory.insertElementAt(this.Directory.elementAt(i),0);
                  this.Directory.removeElementAt(i);
                }

                Temp_DirNode.Copy(DR1);
                this.Directory.insertElementAt(New_DR,pos+1); // ?? i
              }
              else {
                DirectoryRecord Temp_DR;
                for (i=this.Directory.size()-1;i>Half;i--) {
                  New_PIN.Directory.insertElementAt(this.Directory.elementAt(i),0);
                  this.Directory.removeElementAt(i);
                }

                if (pos==Half)
                  Temp_DR = (DirectoryRecord)this.Directory.elementAt(pos);
                else
                  Temp_DR = (DirectoryRecord)New_PIN.Directory.elementAt(pos-Half-1);

                Temp_DirNode.Copy(DR1);
                New_PIN.Directory.insertElementAt(New_DR,pos-Half); // ??
              }
            }
          }
          else {
            Temp_DirNode.Copy(DR1);
            Overflow.Set(0);
          }
          return;
        }
      }
      if (this.NextNode!=null) {
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        this.NextNode.Insert_PI(PI,Overflow,DR1,DR2,DiskAccessP,DiskAccessS); // try to insert into next directory node
      }
      else {
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        pos=this.Directory.size()-1;
        DirectoryRecord Temp_DirNode = (DirectoryRecord)this.Directory.elementAt(pos);
        Temp_DirNode.PIN.Insert_PI(PI,Overflow,DR1,DR2,DiskAccessP,DiskAccessS);
        if (Overflow.Get()==1) { // leaf path instantiation node is overflowed
          Temp_DirNode.Copy(DR1);
          if (this.Directory.size()<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // if there is an empty place for overflow Directory Record
            DirectoryRecord New_DR = new DirectoryRecord(DR2);
            this.Directory.insertElementAt(New_DR,pos+1);
          }
          else { // if there is no empty space for new DirectoryRecord
            DiskAccessP.Set(DiskAccessP.Get()+1);
            DiskAccessS.Set(DiskAccessS.Get()+1);
            Path_Instantiation_Node New_PIN = new Path_Instantiation_Node(2);
            New_PIN.NextNode=this.NextNode;
            this.NextNode=New_PIN;
            New_PIN.PrevNode=this;
            if (New_PIN.NextNode!=null)
              New_PIN.NextNode.PrevNode=New_PIN;

            DirectoryRecord New_DR = new DirectoryRecord(DR2);
            int Half = Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE / 2;

            if (pos<Half) { // move the mid record and second half into new node
              for (i=this.Directory.size()-1;i>=Half;i--) {
                New_PIN.Directory.insertElementAt(this.Directory.elementAt(i),0);
                this.Directory.removeElementAt(i);
              }

              Temp_DirNode.Copy(DR1);
              this.Directory.insertElementAt(New_DR,pos+1);
            }
            else {
              DirectoryRecord Temp_DR;
              for (i=this.Directory.size()-1;i>Half;i--) {
                New_PIN.Directory.insertElementAt(this.Directory.elementAt(i),0);
                this.Directory.removeElementAt(i);
              }

              if (pos==Half)
                Temp_DR = (DirectoryRecord)this.Directory.elementAt(pos);
              else
                Temp_DR = (DirectoryRecord)New_PIN.Directory.elementAt(pos-Half-1);

              Temp_DR.Copy(DR1);
              New_PIN.Directory.insertElementAt(New_DR,pos-Half);
            }
          }
        }
        else {
          Temp_DirNode.Copy(DR1);
          Overflow.Set(0);
        }
        return;
      }
    }
    else if (this.Type==3){
      boolean Inserted=false;
      if (this.PIs.size()<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // if there is an empty place for PI
        this.Insert_PI_into_PIs(PI);
        PathInst.CopyPathInst(DR1.PI,(Vector)PIs.elementAt(PIs.size()-1));
        DR1.PIN=this;
        Overflow.Set(0);
      }
      else { // if there is not an empty place, cretae a new PIN and move the half of the PIs into it
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        Path_Instantiation_Node New_PIN = new Path_Instantiation_Node(3);
        New_PIN.NextNode=this.NextNode;
        this.NextNode=New_PIN;
        New_PIN.PrevNode=this;
        if (New_PIN.NextNode!=null)
          New_PIN.NextNode.PrevNode=New_PIN;

        int Half = Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE / 2;

        for (i=this.PIs.size()-1;i>Half;i--) { // move half of the PIs
          New_PIN.PIs.insertElementAt(this.PIs.elementAt(i),0);
          this.PIs.removeElementAt(i);
        }

        for (i=0;i<this.PIs.size();i++) { // insert the PI if find the correct place
          if (PathInst.Compare(PI,(Vector)this.PIs.elementAt(i))==2) {
            this.PIs.insertElementAt(PI,i);
            New_PIN.PIs.insertElementAt(this.PIs.elementAt(this.PIs.size()-1),0);
            this.PIs.removeElementAt(this.PIs.size()-1);
            Inserted=true;
            break;
          }
        }

        if (!Inserted) {
          for (i=0;i<New_PIN.PIs.size();i++) { // insert the PI if find the correct place
            if (PathInst.Compare(PI,(Vector)New_PIN.PIs.elementAt(i))==2) {
              New_PIN.PIs.insertElementAt(PI,i);
              Inserted=true;
              break;
            }
          }
        }

        if (!Inserted) {
          New_PIN.PIs.addElement(PI);
          Inserted=true;
        }

        Overflow.Set(1);
        PathInst.CopyPathInst(DR1.PI,(Vector)this.PIs.elementAt(this.PIs.size()-1));
        DR1.PIN=this;
        PathInst.CopyPathInst(DR2.PI,(Vector)New_PIN.PIs.elementAt(New_PIN.PIs.size()-1));
        DR2.PIN=New_PIN;
      }
    }
  }

  public boolean Delete_PIs(Object O,int Pos,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // return true if all the PIs are deleted
    Vector Temp_PIs = new Vector(0,1);
    Vector PI;
    ObjectInt Underflow = new ObjectInt();
    ObjectInt Overflow = new ObjectInt();
    Vector Temp_PIs_List = new Vector(0,1);
    DirectoryRecord DR = new DirectoryRecord();
    DirectoryRecord DR1 = new DirectoryRecord();
    DirectoryRecord DR2 = new DirectoryRecord();
    Vector Temp_PI;
    int i,j,pos;

    if (Type==1) {
      pos=0;
      while (pos<PIs.size()) {
        Temp_PI=(Vector)PIs.elementAt(pos);
        if (Temp_PI.size()>Pos) {
          if (((Object)Temp_PI.elementAt(Pos)).equals(O)) { // if the given object is in the PI at Pos
            Vector TempV = new Vector(0,1);
            for (int c=0;c<Temp_PI.size();c++) { TempV.addElement(Temp_PI.elementAt(c)); }
            Temp_PIs_List.addElement(TempV);
            PIs.removeElementAt(pos);
          }
          else {
            pos++;
          }
        }
        else {
          pos++;
        }
      }
      Path_Instantiation_Node.Remove_Deleted_Object_in_PIs(Temp_PIs_List,Pos); // delete the object from the path instantiations

      if (Temp_PIs_List.size()>0) {
        if (this.Insert_PIs(Temp_PIs_List,DiskAccessP,DiskAccessS))
          return(true);
      }
      if (this.PIs.size()==0)
        return(true);
      else
        return(false);
    }
    else if (Type==2) { //---------------------------
      boolean PosChanged=false;
      Path_Instantiation_Node Temp_PIN;

      Temp_PIN = this;
      while (Temp_PIN!=null) {
        pos=0;
        while(pos<Temp_PIN.Directory.size()) {
          PosChanged=false;
          DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos);
          DR.PIN.Delete_PI(O,Pos,Underflow,DR1,Temp_PIs_List,DiskAccessP,DiskAccessS);
          if (Underflow.Get()==1) {
            if (pos==0) { // if it is the first directory record
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              if (Temp_PIN.PrevNode!=null) {
                DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.PrevNode.Directory.elementAt(Temp_PIN.PrevNode.Directory.size()-1);
                Temp_DR.Copy(DR1);
              }
              else {
                System.out.println("27- Hata It is impossible to be so !");
                System.exit(1);
              }
            }
            else {
              DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos-1);
              Temp_DR.Copy(DR1);
            }
            Temp_PIN.Directory.removeElementAt(pos);
            PosChanged=true;
          }
          else if (Underflow.Get()==2) {
            if (pos==Temp_PIN.Directory.size()-1) { // if it is the last directory record
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              if (Temp_PIN.NextNode!=null) {
                DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.NextNode.Directory.elementAt(0);
                Temp_DR.Copy(DR1);
              }
             else {
                System.out.println("26- Hata It is impossible to be so !");
                System.exit(1);
              }
            }
            else {
              DirectoryRecord Temp_DR = (DirectoryRecord)Temp_PIN.Directory.elementAt(pos+1);
              Temp_DR.Copy(DR1);
            }
            Temp_PIN.Directory.removeElementAt(pos);
            PosChanged=true;
          }
          else { // Overflow == 0
              DR.Copy(DR1);
          }
          if (Temp_PIN.PrevNode!=null) {
            if ((Temp_PIN.Directory.size()+Temp_PIN.PrevNode.Directory.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
              pos=Temp_PIN.PrevNode.Directory.size()+pos;
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              for (i=Temp_PIN.Directory.size()-1;i>=0;i--) { // move the records
                 Temp_PIN.PrevNode.Insert_DR_into_Directory((DirectoryRecord)Temp_PIN.Directory.elementAt(i));
              }
              Temp_PIN.Directory.removeAllElements();

              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              Temp_PIN.PrevNode.NextNode=Temp_PIN.NextNode;
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              if (Temp_PIN.NextNode!=null)
                Temp_PIN.NextNode.PrevNode=Temp_PIN.PrevNode;
              Temp_PIN=Temp_PIN.PrevNode;
              PosChanged=true;
            }
          }

          if (Temp_PIN.NextNode!=null) {
            if ((Temp_PIN.Directory.size()+Temp_PIN.NextNode.Directory.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
              pos=pos;
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              for (i=Temp_PIN.NextNode.Directory.size()-1;i>=0;i--) { // move the records into the current record
                Temp_PIN.Insert_DR_into_Directory((DirectoryRecord)Temp_PIN.NextNode.Directory.elementAt(i));
              }
              Temp_PIN.NextNode.Directory.removeAllElements();

              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              if (Temp_PIN.NextNode.NextNode!=null)
                Temp_PIN.NextNode.NextNode.PrevNode=Temp_PIN;
              Temp_PIN.NextNode=Temp_PIN.NextNode.NextNode;
              Temp_PIN=Temp_PIN;
              PosChanged=true;
            }
          }

          if ((Temp_PIN.PrevNode==null)&&(Temp_PIN.NextNode==null)) { // if there is only one directory record
            if (Temp_PIN.Directory.size()==1) { // if in the directory is only one record
              DiskAccessP.Set(DiskAccessP.Get()+1);
              DiskAccessS.Set(DiskAccessS.Get()+1);
              PathInst.DuplicatePIs(Temp_PIN.PIs,((DirectoryRecord)Temp_PIN.Directory.elementAt(0)).PIN.PIs); // copy the PIs into the high level node, and turn the node to type 1
              Temp_PIN.Directory.removeAllElements();
              Temp_PIN.Type=1;

              pos=0;
              while (pos<PIs.size()) {
                Temp_PI=(Vector)PIs.elementAt(pos);
                if (Temp_PI.size()>Pos) {
                  if (((Object)Temp_PI.elementAt(Pos)).equals(O)) { // if the given object is in the PI at Pos
                    Vector TempV = new Vector(0,1);
                    for (int c=0;c<Temp_PI.size();c++) { TempV.addElement(Temp_PI.elementAt(c)); }
                    Temp_PIs_List.addElement(TempV);
                    PIs.removeElementAt(pos);
                  }
                  else {
                    pos++;
                  }
                }
                else {
                  pos++;
                }
              }
              Path_Instantiation_Node.Remove_Deleted_Object_in_PIs(Temp_PIs_List,Pos); // delete the object from the path instantiations
              if (Temp_PIs_List.size()>0) {
                if (this.Insert_PIs(Temp_PIs_List,DiskAccessP,DiskAccessS))
                  return(true);
                else
                  return(false);
              }
              if ((this.Type==1) && (this.PIs.size()==0))
                return(true);
              else
                return(false);
            }
          }
          if (!PosChanged)
            pos++;
        }

        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        Temp_PIN=Temp_PIN.NextNode;
      }

      Path_Instantiation_Node.Remove_Deleted_Object_in_PIs(Temp_PIs_List,Pos); // delete the object from the path instantiations
      if (Temp_PIs_List.size()>0) {
        if (this.Insert_PIs(Temp_PIs_List,DiskAccessP,DiskAccessS))
          return(true);
      }
      if ((this.Type==1) && (this.PIs.size()==0))
        return(true);
      else
        return(false);
    }

    return(false);
  }

  public void Delete_PI(Object O,int Pos,ObjectInt Underflow,DirectoryRecord DR,Vector Temp_PIs_List,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // delete the objects
    Vector Temp_PI;
    int i,j,pos;

    if (Type==3) {
      pos=0;
      while (pos<PIs.size()) {
        Temp_PI=(Vector)PIs.elementAt(pos);
        if (Temp_PI.size()>Pos) {
          if (((Object)Temp_PI.elementAt(Pos)).equals(O)) { // if the given object is in the PI at Pos
            Vector TempV = new Vector(0,1);
            for (int c=0;c<Temp_PI.size();c++) { TempV.addElement(Temp_PI.elementAt(c)); }
            Temp_PIs_List.addElement(TempV);
            PIs.removeElementAt(pos);
          }
          else {
            pos++;
          }
        }
        else {
          pos++;
        }
      }

      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      if (this.PrevNode!=null) {
        if ((this.PIs.size()+this.PrevNode.PIs.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          for (pos=this.PIs.size()-1;pos>=0;pos--) { // move the records
            this.PrevNode.Insert_PI_into_PIs((Vector)this.PIs.elementAt(pos));
          }
          this.PIs.removeAllElements();

          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          this.PrevNode.NextNode=this.NextNode;
          if (this.NextNode!=null)
            this.NextNode.PrevNode=this.PrevNode;
          Underflow.Set(1);
          PathInst.CopyPathInst(DR.PI,(Vector)this.PrevNode.PIs.elementAt(this.PrevNode.PIs.size()-1));
          DR.PIN=this.PrevNode;
          return;
        }
      }

      if (this.NextNode!=null) {
        DiskAccessP.Set(DiskAccessP.Get()+1);
        DiskAccessS.Set(DiskAccessS.Get()+1);
        if ((this.PIs.size()+this.NextNode.PIs.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
          for (pos=this.PIs.size()-1;pos>=0;pos--) { // move the records
            this.NextNode.Insert_PI_into_PIs((Vector)this.PIs.elementAt(pos));
          }
          this.PIs.removeAllElements();

          DiskAccessP.Set(DiskAccessP.Get()+1);
          DiskAccessS.Set(DiskAccessS.Get()+1);
          this.NextNode.PrevNode=this.PrevNode;
          if (this.PrevNode!=null)
            this.PrevNode.NextNode=this.NextNode;
          Underflow.Set(2);
          PathInst.CopyPathInst(DR.PI,(Vector)this.NextNode.PIs.elementAt(this.NextNode.PIs.size()-1));
          DR.PIN=this.NextNode;
          return;
        }
      }
      Underflow.Set(0);
      PathInst.CopyPathInst(DR.PI,(Vector)this.PIs.elementAt(this.PIs.size()-1));
      DR.PIN=this;
    }
  }

  public void PrintPIN() {
    Vector PI;
    int i,j;

    if ((Type==1) || (Type==3)) {
      for (i=0;i<PIs.size();i++) {
        PI = (Vector)PIs.elementAt(i);
        for (j=0;j<PI.size();j++) {
          if (j==0) {
            Author A = (Author)PI.elementAt(j);
            System.out.print(" | Age="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,A.Age));
            System.out.print(" / Height="+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Age,A.Height));
          }
          System.out.print(" : "+PI.elementAt(j).toString());
        }
        System.out.print(" ? ");
      }
    }
    else if (Type==2) {
      for (i=0;i<Directory.size();i++) {
        DirectoryRecord DR = (DirectoryRecord)Directory.elementAt(i);
        DR.PIN.PrintPIN();
      }
      if (this.NextNode!=null)
        this.NextNode.PrintPIN();
    }
  }

  public void PrintAllPIN(int Level) {
    Vector PI;
    int i,j;

    if ((Type==1) || (Type==3)) {
      for (i=0;i<PIs.size();i++) {
        PI = (Vector)PIs.elementAt(i);
        if (Type==1)
          PathInst.PrintAllPI(PI,Level);
        else
          PathInst.PrintAllPI(PI,Level+1);
      }
    }
    else if (Type==2) {
      System.out.println(Database.GetSpaces(Level)+"Directory");
      for (i=0;i<Directory.size();i++) {
        DirectoryRecord DR = (DirectoryRecord)Directory.elementAt(i);
        PathInst.PrintAllPI(DR.PI,Level+1);
        DR.PIN.PrintAllPIN(Level+1);
      }
      if (this.NextNode!=null)
        this.NextNode.PrintAllPIN(Level);
    }
  }

  public void PrintAllPIN(int Level,Vector Tree) {
    Vector PI;
    int i,j;

    if ((Type==1) || (Type==3)) {
      for (i=0;i<PIs.size();i++) {
        PI = (Vector)PIs.elementAt(i);
        if (Type==1)
          PathInst.PrintAllPI(PI,Level,Tree);
        else
          PathInst.PrintAllPI(PI,Level+1,Tree);
      }
    }
    else if (Type==2) {
      Tree.addElement(Database.GetSpaces(Level)+"Directory");
      for (i=0;i<Directory.size();i++) {
        DirectoryRecord DR = (DirectoryRecord)Directory.elementAt(i);
        PathInst.PrintAllPI(DR.PI,Level+1,Tree);
        DR.PIN.PrintAllPIN(Level+1,Tree);
      }
      if (this.NextNode!=null)
        this.NextNode.PrintAllPIN(Level,Tree);
    }
  }

  public void Get_All_PIs(Vector PIs) {
    int i,pos;

    if (Type==1) {
      for (i=0;i<this.PIs.size();i++) {
        PIs.addElement((Vector)this.PIs.elementAt(i));
      }
    }
    else if (Type==2) {
      Path_Instantiation_Node LN = ((DirectoryRecord)this.Directory.elementAt(0)).PIN;
      LN.Get_All_PIs(PIs);
    }
    else if (Type==3){
      if (this.NextNode!=null)
        this.NextNode.Get_All_PIs(PIs);
      for (i=0;i<this.PIs.size();i++) {
        PIs.insertElementAt((Vector)this.PIs.elementAt(i),0);
      }
    }
  }

  public void Eliminate_Duplicates(Vector PIs,ObjectInt Underflow,DirectoryRecord DR,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    int pos;
    PathInst.Eliminate_Duplicates(PIs,this.PIs);

    if (this.PrevNode!=null) {
      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      if ((this.PIs.size()+this.PrevNode.PIs.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
        for (pos=this.PIs.size()-1;pos>=0;pos--) { // move the records
          this.PrevNode.Insert_PI_into_PIs((Vector)this.PIs.elementAt(pos));
        }
        this.PIs.removeAllElements();

        this.PrevNode.NextNode=this.NextNode;
        if (this.NextNode!=null)
          this.NextNode.PrevNode=this.PrevNode;
        Underflow.Set(1);
        PathInst.CopyPathInst(DR.PI,(Vector)this.PrevNode.PIs.elementAt(this.PrevNode.PIs.size()-1));
        DR.PIN=this.PrevNode;
        return;
      }
    }

    if (this.NextNode!=null) {
      DiskAccessP.Set(DiskAccessP.Get()+1);
      DiskAccessS.Set(DiskAccessS.Get()+1);
      if ((this.PIs.size()+this.NextNode.PIs.size())<=Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) { // we can combine the previous PIN with the current one
        for (pos=this.PIs.size()-1;pos>=0;pos--) { // move the records
          this.NextNode.Insert_PI_into_PIs((Vector)this.PIs.elementAt(pos));
        }
        this.PIs.removeAllElements();

        this.NextNode.PrevNode=this.PrevNode;
        if (this.PrevNode!=null)
          this.PrevNode.NextNode=this.NextNode;
        Underflow.Set(2);
        PathInst.CopyPathInst(DR.PI,(Vector)this.NextNode.PIs.elementAt(this.NextNode.PIs.size()-1));
        DR.PIN=this.NextNode;
        return;
      }
    }
    Underflow.Set(0);
  }

  public static void Remove_Deleted_Object_in_PIs(Vector PIs,int Pos) {
    Vector Temp_PI;
    int i,j;

    for (i=PIs.size()-1;i>=0;i--) {
      Temp_PI=(Vector)PIs.elementAt(i);
      for (j=Temp_PI.size()-1;j>=Pos;j--)
        Temp_PI.removeElementAt(j);
      if (Temp_PI.size()==0)
        PIs.removeElementAt(i);
    }
  }

  public static void main(String[] args) {
    Path_Instantiation_Node PIN = new Path_Instantiation_Node(1);
  }
}
