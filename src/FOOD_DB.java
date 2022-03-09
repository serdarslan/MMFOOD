import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class FOOD_DB {
  Vector Libraries;
  Vector Members;
  Vector Books;
  Vector Authors;
  FOOD_Index FI;
  Where_Conditions WCs;
  long InsertPageAccess;
  long DeletePageAccess;
  long RetrievePageAccess;

  FOOD_DB() {
    Libraries = new Vector(0,1);
    Members = new Vector(0,1);
    Books = new Vector(0,1);
    Authors = new Vector(0,1);
    FI = new FOOD_Index();
    WCs = new Where_Conditions();
    InsertPageAccess=0;
    DeletePageAccess=0;
    RetrievePageAccess=0;
  }

  public void Add_Author(Author A,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Vector PIs = new Vector(0,1);
    this.Authors.addElement(A);
    A.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
    this.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  public void Delete_Author(Author A,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    if (A!=null) {
      Vector PIs = new Vector(0,1);
      int pos = this.Authors.lastIndexOf(A);

      if (pos>=0) {
        A.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
        if (PIs.size()>0) {
          Collecting_Path_Instantiations Temp_CPI=(Collecting_Path_Instantiations)PIs.elementAt(0);
          int Pos = ((Vector)Temp_CPI.PIs.elementAt(0)).size()-1;

          this.Delete_PIs(PIs,A,Pos,DiskAccessP,DiskAccessS);
          for (int i=0;i<Books.size();i++) {
            Book B = (Book)Books.elementAt(i);
            if (B!=null) {
              for (int j=0;j<B.Authors.size();j++) {
                if (B.Authors.elementAt(j).equals(A)) {
                  B.Authors.removeElementAt(j);
                  break;
                }
              }
            }
          }
        }
        this.Authors.removeElementAt(pos);
      }
      else {
        System.out.println("Such an Object is not found in the database !");
        System.exit(1);
      }
    }
  }

  public void Add_Book(Book B,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Vector PIs = new Vector(0,1);
    this.Books.addElement(B);
    B.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
    this.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  public void Delete_Book(Book B,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    if (B!=null) {
      Vector PIs = new Vector(0,1);
      int pos = this.Books.lastIndexOf(B);

      if (pos>=0) {
        B.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
        if (PIs.size()>0) {
          Collecting_Path_Instantiations Temp_CPI=(Collecting_Path_Instantiations)PIs.elementAt(0);
          int Pos = ((Vector)Temp_CPI.PIs.elementAt(0)).size()-1;

          this.Delete_PIs(PIs,B,Pos,DiskAccessP,DiskAccessS);
          for (int i=0;i<Members.size();i++) {
            Member M = (Member)Members.elementAt(i);
            if (M!=null) {
              for (int j=0;j<M.Books.size();j++) {
                if (M.Books.elementAt(j).equals(B)) {
                  M.Books.removeElementAt(j);
                  break;
                }
              }
            }
          }
        }
        this.Books.removeElementAt(pos);
      }
      else {
        System.out.println("Such an Object is not found in the database !");
        System.exit(1);
      }
    }
  }
  public void Add_Member(Member M,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Vector PIs = new Vector(0,1);
    this.Members.addElement(M);
    M.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
    this.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  public void Delete_Member(Member M,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    if (M!=null) {
      Vector PIs = new Vector(0,1);
      int pos = this.Members.lastIndexOf(M);

      if (pos>=0) {
        M.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
        if (PIs.size()>0) {
          Collecting_Path_Instantiations Temp_CPI=(Collecting_Path_Instantiations)PIs.elementAt(0);
          int Pos = ((Vector)Temp_CPI.PIs.elementAt(0)).size()-1;

          this.Delete_PIs(PIs,M,Pos,DiskAccessP,DiskAccessS);
          for (int i=0;i<Libraries.size();i++) {
            Library LB = (Library)Libraries.elementAt(i);
            if (LB!=null) {
              for (int j=0;j<LB.Members.size();j++) {
                if (LB.Members.elementAt(j).equals(M)) {
                  LB.Members.removeElementAt(j);
                  break;
                }
              }
            }
          }
        }
        this.Members.removeElementAt(pos);
      }
      else {
        System.out.println("Such an Object is not found in the database !");
        System.exit(1);
      }
    }
  }

  public void Add_Library(Library L,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Vector PIs = new Vector(0,1);
    this.Libraries.addElement(L);
    L.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
    this.Insert_PIs(PIs,DiskAccessP,DiskAccessS);
  }

  public void Delete_Library(Library L,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    if (L!=null) {
      Vector PIs = new Vector(0,1);
      int pos = this.Libraries.lastIndexOf(L);

      if (pos>=0) {
        L.ForwardTraverse(PIs,DiskAccessP,DiskAccessS);
        if (PIs.size()>0) {
          Collecting_Path_Instantiations Temp_CPI=(Collecting_Path_Instantiations)PIs.elementAt(0);
          int Pos = ((Vector)Temp_CPI.PIs.elementAt(0)).size()-1;
          this.Delete_PIs(PIs,L,Pos,DiskAccessP,DiskAccessS);
        }
        this.Libraries.removeElementAt(pos);
      }
      else {
        System.out.println("Such an Object is not found in the database !");
        System.exit(1);
      }
    }
  }

  public void Insert_PIs(Vector PIs, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    String Temp_BS = new String("");
    String Temp_BS1 = new String("");
    String Temp_BS2 = new String("");
    int i;

    for (i=0;i<PIs.size();i++) {
      Temp_BS1 = Database.Construct_Bit_String("age",((Collecting_Path_Instantiations)PIs.elementAt(i)).Value1);
      if (Database.INDEX_DIMENSION > 1) {
        Temp_BS2 = Database.Construct_Bit_String("height",((Collecting_Path_Instantiations)PIs.elementAt(i)).Value2);
        Temp_BS = Database.Combine_Bit_Strings(Temp_BS1,Temp_BS2);
        this.FI.Insert(Temp_BS,((Collecting_Path_Instantiations)PIs.elementAt(i)).PIs,DiskAccessP,DiskAccessS);
      }
      else {
        this.FI.Insert(Temp_BS1,((Collecting_Path_Instantiations)PIs.elementAt(i)).PIs,DiskAccessP,DiskAccessS);
      }
    }
  }

  public void Delete_PIs(Vector PIs,Object O,int Pos,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    String Temp_BS = new String("");
    String Temp_BS1 = new String("");
    String Temp_BS2 = new String("");
    int i;

    for (i=0;i<PIs.size();i++) {
      Temp_BS1 = Database.Construct_Bit_String("age",((Collecting_Path_Instantiations)PIs.elementAt(i)).Value1);
      if (Database.INDEX_DIMENSION > 1) {
        Temp_BS2 = Database.Construct_Bit_String("height",((Collecting_Path_Instantiations)PIs.elementAt(i)).Value2);
        Temp_BS = Database.Combine_Bit_Strings(Temp_BS1,Temp_BS2);
        this.FI.Delete(Temp_BS,O,Pos,DiskAccessP,DiskAccessS);
      }
      else {
        this.FI.Delete(Temp_BS1,O,Pos,DiskAccessP,DiskAccessS);
      }
    }
  }

  public void Insert(int InsertionCount,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // InsertionCount : Number of Insertion
    int i;

    Vector PIs = new Vector(0,1);
    Vector Temp_PIs = new Vector(0,1);
    String Temp_BS = new String("");
    String Temp_BS1 = new String("");
    String Temp_BS2 = new String("");

    Vector Temp_Authors = new Vector(0,1);
    Vector Temp_Books = new Vector(0,1);
    Vector Temp_Members = new Vector(0,1);
    Vector Temp_Libraries = new Vector(0,1);
    Random R = new Random(1000);
    int Option;

    for (i=0;i<InsertionCount;i++) {
      Vector V = new Vector(0,1);
      String AgeStr;
      String HeightStr;

      if (Math.abs(R.nextInt()%2)>0) { // crisp
        AgeStr=Integer.toString(Math.abs(R.nextInt()%100));
      }
      else {
        AgeStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
        AgeStr="F"+Database.GetChars(3-AgeStr.length(),"0")+AgeStr;
      }

      if (Math.abs(R.nextInt()%2)==0) { // crisp
        HeightStr=Integer.toString(Math.abs(R.nextInt()%80)+120);
      }
      else {
        HeightStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
        HeightStr="F"+Database.GetChars(3-HeightStr.length(),"0")+HeightStr;
      }

      if (Math.abs(R.nextInt()%2)==0) { // native author
        Native_Author NA = new Native_Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr),"City"+Integer.toString(Math.abs(R.nextInt()%10000)));
        Temp_Authors.addElement(NA);
      }
      else {
        Foreign_Author FA = new Foreign_Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr),"Nationality"+Integer.toString(Math.abs(R.nextInt()%10000)));
        Temp_Authors.addElement(FA);
      }

      Option = Math.abs(R.nextInt()%4);
      String SubjectStr;

      SubjectStr=Integer.toBinaryString(Math.abs(R.nextInt()%15)+1);
      SubjectStr="F"+Database.GetChars(4-SubjectStr.length(),"0")+SubjectStr;
      V.removeAllElements();V.addElement((Author)Temp_Authors.elementAt(Math.abs(R.nextInt()%Temp_Authors.size())));
      if (Option==0) {
        Algorithms_Book AB = new Algorithms_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
        Temp_Books.addElement(AB);
      }
      else
      if (Option==1) {
        Robotics_Book RB = new Robotics_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
        Temp_Books.addElement(RB);
      }
      else {
        Database_Book DB = new Database_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
        Temp_Books.addElement(DB);
      }

      Option = Math.abs(R.nextInt()%4);
      String EducationStr;

      EducationStr=Integer.toBinaryString(Math.abs(R.nextInt()%15+1));
      EducationStr="F"+Database.GetChars(4-EducationStr.length(),"0")+EducationStr;
      V.removeAllElements();V.addElement((Book)Temp_Books.elementAt(Math.abs(R.nextInt()%Temp_Books.size())));
      if (Option==0) {
        Lecturer LC = new Lecturer (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
        Temp_Members.addElement(LC);
      }
      else
      if (Option==1) {
        Assistant AS = new Assistant (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
        Temp_Members.addElement(AS);
      }
      else {
        Student ST = new Student (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
        Temp_Members.addElement(ST);
      }

      V.removeAllElements();V.addElement((Member)Temp_Members.elementAt(Math.abs(R.nextInt()%Temp_Members.size())));
      Library LB = new Library("Library"+Integer.toString(Math.abs(R.nextInt()%10000)),"City"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%50000),V);
      Temp_Libraries.addElement(LB);
    }

    for (i=0;i<Temp_Authors.size();i++) {
      this.Add_Author((Author)Temp_Authors.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Books.size();i++) {
      this.Add_Book((Book)Temp_Books.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Members.size();i++) {
      this.Add_Member((Member)Temp_Members.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Libraries.size();i++) {
      this.Add_Library((Library)Temp_Libraries.elementAt(i),DiskAccessP,DiskAccessS);
    }
  }

  public void Delete(ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    int i;

    for (i=this.Libraries.size()-1;i>=0;i--) {
      System.out.println("Delete : L "+i+"  OID="+((Library)this.Libraries.elementAt(i)).OID);
//if (i==52)
// i=i;
      this.Delete_Library((Library)this.Libraries.elementAt(i),DiskAccessP,DiskAccessS);
      this.FI.Root.Test_PIN_Neighbours(false);
      this.FI.Root.Test_PIs(false);
//      this.FI.PrintFOODIndex();
    }

    for (i=this.Members.size()-1;i>=0;i--) {
      System.out.println("Delete : M "+i+"  OID="+((Member)this.Members.elementAt(i)).OID);
      this.Delete_Member((Member)this.Members.elementAt(i),DiskAccessP,DiskAccessS);
      this.FI.Root.Test_PIN_Neighbours(false);
      this.FI.Root.Test_PIs(false);
//      this.FI.PrintFOODIndex();
    }

    for (i=this.Books.size()-1;i>=0;i--) {
      System.out.println("Delete B : "+i+"  OID="+((Book)this.Books.elementAt(i)).OID);
      this.Delete_Book((Book)this.Books.elementAt(i),DiskAccessP,DiskAccessS);
      this.FI.Root.Test_PIN_Neighbours(false);
      this.FI.Root.Test_PIs(false);
//      this.FI.PrintFOODIndex();
    }


    for (i=this.Authors.size()-1;i>=0;i--) {
      System.out.println("Delete A : "+i+"  OID="+((Author)this.Authors.elementAt(i)).OID);
//if (i==13)
//i=i;
      this.Delete_Author((Author)this.Authors.elementAt(i),DiskAccessP,DiskAccessS);
      this.FI.Root.Test_PIN_Neighbours(false);
      this.FI.Root.Test_PIs(false);
//if (i<=480)
//      this.FI.PrintFOODIndex();
    }
  }

  public void Example(ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    Native_Author NA1,NA2,NA3,NA4;
    Foreign_Author FA1,FA2,FA3,FA4,FA5;
    Algorithms_Book AB1,AB2,AB3;
    Robotics_Book RB1,RB2,RB3;
    Database_Book DB1,DB2,DB3;
    Lecturer LC1,LC2,LC3;
    Assistant AS1,AS2,AS3,AS4;
    Student ST1,ST2,ST3,ST4;
    Library LB1,LB2,LB3;

    NA1 = new Native_Author("Recep Tugrul","old","[short]","Adana");
    this.Add_Author(NA1,DiskAccessP,DiskAccessS);

    NA2 = new Native_Author("Murat Ayanoglu","middle-aged","[medium,tall]","Ankara");
    this.Add_Author(NA2,DiskAccessP,DiskAccessS);

    NA3 = new Native_Author("Ayla Gunal","","","Istanbul");
    this.Add_Author(NA3,DiskAccessP,DiskAccessS);

    NA4 = new Native_Author("Eda Akkurt","25","{short,medium}","Ankara");
    this.Add_Author(NA4,DiskAccessP,DiskAccessS);

    FA1 = new Foreign_Author("George Steve","55","","English");
    this.Add_Author(FA1,DiskAccessP,DiskAccessS);

    FA2 = new Foreign_Author("Mary Gate","young","tall","American");
    this.Add_Author(FA2,DiskAccessP,DiskAccessS);

    FA3 = new Foreign_Author("Stefano Martin","36","165","Italian");
    this.Add_Author(FA3,DiskAccessP,DiskAccessS);

    FA4 = new Foreign_Author("James Widow","45","178","");
    this.Add_Author(FA4,DiskAccessP,DiskAccessS);

    FA5 = new Foreign_Author("Tom Grape","old","medium","American");
    this.Add_Author(FA5,DiskAccessP,DiskAccessS);

    Vector AA1 = new Vector(0,1);
    AA1.addElement(NA4);
    AA1.addElement(FA4);
    DB1 = new Database_Book("DB5698","Future Press","Database Management System",AA1,"{database,data structures}");
    this.Add_Book(DB1,DiskAccessP,DiskAccessS);

    Vector AA2 = new Vector(0,1);
    AA2.addElement(FA1);
    AA2.addElement(FA4);
    DB2 = new Database_Book("DB7432","Golden Press","Introduction to Database Applications",AA2,"{database,data Structures,algorithms}");
    this.Add_Book(DB2,DiskAccessP,DiskAccessS);

    Vector AA3 = new Vector(0,1);
    AA3.addElement(FA5);
    DB3 = new Database_Book("DB4596","ABC Press","Fuzzy Database Modelling",AA3,"{database,data structures}");
    this.Add_Book(DB3,DiskAccessP,DiskAccessS);

    Vector AA4 = new Vector(0,1);
    AA4.addElement(NA3);
    AA4.addElement(FA1);
    RB1 = new Robotics_Book("RO1269","Future Press","Robot Behaviours",AA4,"{algorithms,data structures}");
    this.Add_Book(RB1,DiskAccessP,DiskAccessS);

    Vector AA5 = new Vector(0,1);
    AA5.addElement(FA2);
    RB2 = new Robotics_Book("RO7863","Golden Press","Algorithms for Robotics",AA5,"{algorithms,theory}");
    this.Add_Book(RB2,DiskAccessP,DiskAccessS);

    Vector AA6 = new Vector(0,1);
    AA6.addElement(NA2);
    AA6.addElement(FA3);
    RB3 = new Robotics_Book("RO4592","Golden Press","Next Generation Robot Technology",AA6,"{algorithms,theory}");
    this.Add_Book(RB3,DiskAccessP,DiskAccessS);

    Vector AA7 = new Vector(0,1);
    AA7.addElement(NA1);
    AB1 = new Algorithms_Book("AL1575","ABC Press","Algorithms",AA7,"{algorithms}");
    this.Add_Book(AB1,DiskAccessP,DiskAccessS);

    Vector AA8 = new Vector(0,1);
    AA8.addElement(NA3);
    AA8.addElement(FA3);
    AB2 = new Algorithms_Book("AL0052","Golden Press","Analysis of Algorithms",AA8,"{algorithms}");
    this.Add_Book(AB2,DiskAccessP,DiskAccessS);

    Vector AA9 = new Vector(0,1);
    AA9.addElement(NA1);
    AB3 = new Algorithms_Book("CG4523","ABC Press","Algorithms for Computer Graphics",AA9,"{algorithms,data structures}");
    this.Add_Book(AB3,DiskAccessP,DiskAccessS);

    Vector BA1 = new Vector(0,1);
    BA1.addElement(DB2);
    ST1 = new Student(2563,"Wonda Granville",4,BA1,"{under graduate}");
    this.Add_Member(ST1,DiskAccessP,DiskAccessS);

    Vector BA2 = new Vector(0,1);
    BA2.addElement(DB1);
    ST2 = new Student(5236,"Casim Aras",4,BA2,"{ms}");
    this.Add_Member(ST2,DiskAccessP,DiskAccessS);

    Vector BA3 = new Vector(0,1);
    BA3.addElement(DB3);
    BA3.addElement(RB3);
    ST3 = new Student(2587,"Kazim Gulsoy",5,BA3,"{under graduate}");
    this.Add_Member(ST3,DiskAccessP,DiskAccessS);

    Vector BA4 = new Vector(0,1);
    ST4 = new Student(4523,"Tashia Smith",3,BA4,"{ms}");
    this.Add_Member(ST4,DiskAccessP,DiskAccessS);

    Vector BA5 = new Vector(0,1);
    BA5.addElement(AB3);
    AS1 = new Assistant(4589,"Juan Grenz",4,BA5,"{phd}");
    this.Add_Member(AS1,DiskAccessP,DiskAccessS);

    Vector BA6 = new Vector(0,1);
    BA6.addElement(RB3);
    AS2 = new Assistant(1524,"Bahattin Guralp",5,BA6,"{ms}");
    this.Add_Member(AS2,DiskAccessP,DiskAccessS);

    Vector BA7 = new Vector(0,1);
    BA7.addElement(RB1);
    AS3 = new Assistant(7532,"Atila Ascioglu",4,BA7,"{ms}");
    this.Add_Member(AS3,DiskAccessP,DiskAccessS);

    Vector BA8 = new Vector(0,1);
    BA8.addElement(RB2);
    BA8.addElement(DB3);
    AS4 = new Assistant(3547,"Haseeb Naz",5,BA8,"{under graduate}");
    this.Add_Member(AS4,DiskAccessP,DiskAccessS);

    Vector BA9 = new Vector(0,1);
    BA9.addElement(AB2);
    BA9.addElement(RB2);
    LC1 = new Lecturer(1526,"Hilal Ozer",5,BA9,"{phd}");
    this.Add_Member(LC1,DiskAccessP,DiskAccessS);

    Vector BA10 = new Vector(0,1);
    BA10.addElement(DB1);
    LC2 = new Lecturer(5268,"Kursat Ince",3,BA10,"{phd}");
    this.Add_Member(LC2,DiskAccessP,DiskAccessS);

    Vector BA11 = new Vector(0,1);
    LC3 = new Lecturer(4523,"Selcuk Omer",6,BA11,"{phd}");
    this.Add_Member(LC3,DiskAccessP,DiskAccessS);

    Vector MA1 = new Vector(0,1);
    MA1.addElement(LC1);
    MA1.addElement(AS1);
    MA1.addElement(AS4);
    MA1.addElement(ST1);
    MA1.addElement(ST2);
    LB1 = new Library("METU Library","Ankara",50000,MA1);
    this.Add_Library(LB1,DiskAccessP,DiskAccessS);

    Vector MA2 = new Vector(0,1);
    MA2.addElement(LC2);
    MA2.addElement(AS2);
    MA2.addElement(ST3);
    LB2 = new Library("Bogazici Library","Istanbul",45000,MA2);
    this.Add_Library(LB2,DiskAccessP,DiskAccessS);

    Vector MA3 = new Vector(0,1);
    MA3.addElement(LC3);
    MA3.addElement(AS3);
    MA3.addElement(ST4);
    LB3 = new Library("EMU Library","Gazimagosa",40000,MA3);
    this.Add_Library(LB3,DiskAccessP,DiskAccessS);
  }

  public void PrintDBObjects(TextArea textArea_DBObjects) {
    int i,j;
    ObjectDouble MD1 = new ObjectDouble(),MD2 = new ObjectDouble(),MD3 = new ObjectDouble();
    ObjectInt PN1 = new ObjectInt(),PN2 = new ObjectInt(),PN3 = new ObjectInt();
    String TempBits = new String("");
    String TempStr = new String("");

    textArea_DBObjects.setText("");

    TempStr=TempStr+"LIBRARIES\n";
    TempStr=TempStr+"---------------\n";
    for (i=0;i<this.Libraries.size();i++) {
      TempStr=TempStr+"Library "+Integer.toString(i)+" OID="+((Library)this.Libraries.elementAt(i)).OID+" : ";
      TempStr=TempStr+this.Libraries.elementAt(i).toString()+"\n";
    }
    if (this.Libraries.size()>0) TempStr=TempStr+"\n";

    TempStr=TempStr+"MEMBERS\n";
    TempStr=TempStr+"---------------\n";
    for (i=0;i<this.Members.size();i++) {
      TempStr=TempStr+"Member "+Integer.toString(i)+" OID="+((Member)this.Members.elementAt(i)).OID+" : ";
      TempStr=TempStr+this.Members.elementAt(i).toString()+"\n";
    }
    if (this.Members.size()>0) TempStr=TempStr+"\n";

    TempStr=TempStr+"BOOKS\n";
    TempStr=TempStr+"---------------\n";
    for (i=0;i<this.Books.size();i++) {
      TempStr=TempStr+"Book "+Integer.toString(i)+" OID="+((Book)this.Books.elementAt(i)).OID+" : ";
      TempStr=TempStr+this.Books.elementAt(i).toString()+"\n";
    }
    if (this.Books.size()>0) TempStr=TempStr+"\n";

    TempStr=TempStr+"AUTHORS\n";
    TempStr=TempStr+"---------------\n";
    for (i=0;i<this.Authors.size();i++) {
      Author TempA = (Author) this.Authors.elementAt(i);
      TempStr=TempStr+"Author "+Integer.toString(i)+" OID="+((Author)this.Authors.elementAt(i)).OID+" : ";
      TempStr=TempStr+(Object)TempA.toString()+"\n";
      TempStr=TempStr+"                ";
      if ((TempA.Age.length()>0) && (TempA.Age.charAt(0)=='F')) { // fuzzy
        TempStr=TempStr+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,TempA.Age)+" > ";
        if (Database.ClassBitsToIndex(TempA.Age.substring(1))>=0)
          for (j=0;j<8;j++) {
            TempStr=TempStr+Database.Similarity_Matrix_Age[Database.ClassBitsToIndex(TempA.Age.substring(1))][j]+" : ";
          }
        TempStr=TempStr+"\n";
      }
      else { // crisp
        TempStr=TempStr+TempA.Age+" > ";
        Database.Membership_Function_Young(Integer.parseInt(TempA.Age),MD1,PN1);
        Database.Membership_Function_MiddleAged(Integer.parseInt(TempA.Age),MD2,PN2);
        Database.Membership_Function_Old(Integer.parseInt(TempA.Age),MD3,PN3);

        TempStr=TempStr+MD1.Get()+" : ";
        TempStr=TempStr+MD2.Get()+" : ";
        TempStr=TempStr+MD3.Get()+" : ";
        TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Age[0][3],Database.Similarity_Matrix_Age[1][3])+" : ";
        TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Age[0][4],Database.Similarity_Matrix_Age[2][4])+" : ";
        TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Age[1][5],Database.Similarity_Matrix_Age[2][5])+" : ";
        TempStr=TempStr+Database.Minimum(Database.Minimum(Database.Similarity_Matrix_Age[1][6],Database.Similarity_Matrix_Age[2][6]),Database.Similarity_Matrix_Age[3][6])+" : ";
        TempStr=TempStr+"\n";
      }
      if (Database.INDEX_DIMENSION>1)
       {
        TempStr=TempStr+"                ";
        if ((TempA.Height.length()>0) && (TempA.Height.charAt(0)=='F')) { // fuzzy
          TempStr=TempStr+Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,TempA.Height)+" > ";
          if (Database.ClassBitsToIndex(TempA.Height.substring(1))>=0)
            for (j=0;j<8;j++) {
              TempStr=TempStr+Database.Similarity_Matrix_Height[Database.ClassBitsToIndex(TempA.Height.substring(1))][j]+" : ";
            }
          TempStr=TempStr+"\n";
        }
        else { // crisp
          TempStr=TempStr+TempA.Height+" > ";
          Database.Membership_Function_Short(Integer.parseInt(TempA.Height),MD1,PN1);
          Database.Membership_Function_Medium(Integer.parseInt(TempA.Height),MD2,PN2);
          Database.Membership_Function_Tall(Integer.parseInt(TempA.Height),MD3,PN3);

          TempStr=TempStr+MD1.Get()+" : ";
          TempStr=TempStr+MD2.Get()+" : ";
          TempStr=TempStr+MD3.Get()+" : ";
          TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Height[0][3],Database.Similarity_Matrix_Height[1][3])+" : ";
          TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Height[0][4],Database.Similarity_Matrix_Height[2][4])+" : ";
          TempStr=TempStr+Database.Minimum(Database.Similarity_Matrix_Height[1][5],Database.Similarity_Matrix_Height[2][5])+" : ";
          TempStr=TempStr+Database.Minimum(Database.Minimum(Database.Similarity_Matrix_Height[1][6],Database.Similarity_Matrix_Height[2][6]),Database.Similarity_Matrix_Height[3][6])+" : ";
          TempStr=TempStr+"\n";
        }
      }
    }
    if (this.Authors.size()>0) TempStr=TempStr+"\n";
    TempStr=TempStr+"TOTAL Objects : "+Integer.toString(this.Libraries.size()+this.Members.size()+this.Books.size()+this.Authors.size());
    textArea_DBObjects.append(TempStr);
  }

  public void Retrieve(int FromClass, double FromThreholdValue, int TargetClass, double TargetThreholdValue, String Target_Attribute, Vector ResultObjectsStr, ObjectInt DiskAccessP, ObjectInt DiskAccessS) {
    this.FI.Retrieve(FromClass,FromThreholdValue,TargetClass,TargetThreholdValue,Target_Attribute,this.WCs,ResultObjectsStr,DiskAccessP,DiskAccessS);
    if (foodindex.checkbox_printindex.getState()) {
      ObjectStr TempRBS=new ObjectStr();
      WCs.GetResultBitStrings(TempRBS);
      if (TempRBS.Get().compareTo("")!=0)
        foodindex.textArea_ResultBitStrings.setText(TempRBS.Get());
    }
    WCs.ANDConditions.removeAllElements();
    WCs.ResultObjects.removeAllElements();
    WCs.ResultBitStrings.removeAllElements();
  }

  public void Insert_UniformAndRandomInsertion(int Quantity,int Type,int Type2,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // Type = 0 Random, Type = 1 Uniform, Type2 = 0 Random Data, Type2 = 1 Uniform Data, Type2 = 2 Same Data
    int i;
    int ClassIndex;
    int AuthorCount=0;
    Vector V = new Vector(0,1);
    Random R = new Random(1000000); // get a random generator

    Vector Temp_Authors = new Vector(0,1);
    Vector Temp_Books = new Vector(0,1);
    Vector Temp_Members = new Vector(0,1);
    Vector Temp_Libraries = new Vector(0,1);
    String TempAgeStrType2 = new String();
    String TempHeightStrType2 = new String();
    if (Type2==2) {
      TempAgeStrType2=Integer.toBinaryString(Math.abs(R.nextInt()%8));
      TempAgeStrType2="F"+Database.GetChars(3-TempAgeStrType2.length(),"0")+TempAgeStrType2;
      TempHeightStrType2=Integer.toBinaryString(Math.abs(R.nextInt()%8));
      TempHeightStrType2="F"+Database.GetChars(3-TempHeightStrType2.length(),"0")+TempHeightStrType2;
    }

    for (i=0;i<Quantity;i++) {
      if (Type==0) {
        int RandomNumber = Math.abs(R.nextInt()%1000000);
        ClassIndex =  4 - Math.abs(RandomNumber % 4) - 1;
      }
      else
        ClassIndex =  4 - Math.abs(i % 4) -1;

      if (ClassIndex==0) { // Library
        V.removeAllElements();
        if (Temp_Members.size()>0)
          V.addElement((Member)Temp_Members.elementAt(Math.abs(R.nextInt()%Temp_Members.size())));

        Library LB = new Library("Library"+Integer.toString(Math.abs(R.nextInt()%10000)),"City"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%50000),V);
        Temp_Libraries.addElement(LB);
      }
      else
      if (ClassIndex==1) { // Member
        String EducationStr = new String();

        ClassIndex=Math.abs(R.nextInt()%6);
        EducationStr=Integer.toBinaryString(Math.abs(R.nextInt()%15)+1);
        EducationStr="F"+Database.GetChars(4-EducationStr.length(),"0")+EducationStr;
        V.removeAllElements();
        if (Temp_Books.size()>0)
          V.addElement((Book)Temp_Books.elementAt(Math.abs(R.nextInt()%Temp_Books.size())));

        if (ClassIndex==0) {
          Member M = new Member (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V);
          Temp_Members.addElement(M);
        }
        else
        if (ClassIndex==1) {
          Highly_Educated HE = new Highly_Educated (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
          Temp_Members.addElement(HE);
        }
        else
        if (ClassIndex==2) {
          Educated E = new Educated (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
          Temp_Members.addElement(E);
        }
        else
        if (ClassIndex==3) {
          Lecturer LC = new Lecturer (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
          Temp_Members.addElement(LC);
        }
        else
        if (ClassIndex==4) {
          Assistant AS = new Assistant (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
          Temp_Members.addElement(AS);
        }
        else
        if (ClassIndex==5) {
          Student ST = new Student (Math.abs(R.nextInt()%10000),"Name Surname"+Integer.toString(Math.abs(R.nextInt()%10000)),Math.abs(R.nextInt()%11),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Education_Domain, Database.Operator_Education_Domain,EducationStr));
          Temp_Members.addElement(ST);
        }
      }
      else
      if (ClassIndex==2) { // Book
        String SubjectStr = new String();

        ClassIndex=Math.abs(R.nextInt()%8);
        SubjectStr=Integer.toBinaryString(Math.abs(R.nextInt()%15+1));
        SubjectStr="F"+Database.GetChars(4-SubjectStr.length(),"0")+SubjectStr;
        V.removeAllElements();
        if (Temp_Authors.size()>0)
          V.addElement((Author)Temp_Authors.elementAt(Math.abs(R.nextInt()%Temp_Authors.size())));

        if (ClassIndex==0) {
          Book B = new Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V);
          Temp_Books.addElement(B);
        }
        else
        if (ClassIndex==1) {
          Science_Book SB = new Science_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(SB);
        }
        else
        if (ClassIndex==2) {
          Engineering_Book EB = new Engineering_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(EB);
        }
        else
        if (ClassIndex==3) {
          Computer_Science_Book CSB = new Computer_Science_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(CSB);
        }
        else
        if (ClassIndex==4) {
          Computer_Engineering_Book CEB = new Computer_Engineering_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(CEB);
        }
        else
        if (ClassIndex==5) {
          Algorithms_Book AB = new Algorithms_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(AB);
        }
        else
        if (ClassIndex==6) {
          Robotics_Book RB = new Robotics_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(RB);
        }
        else
        if (ClassIndex==7) {
          Database_Book DB = new Database_Book ("Code"+Integer.toString(Math.abs(R.nextInt()%10000)),"Publisher"+Integer.toString(Math.abs(R.nextInt()%10000)),"Title"+Integer.toString(Math.abs(R.nextInt()%10000)),V,Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Subject_Domain,Database.Operator_Subject_Domain,SubjectStr));
          Temp_Books.addElement(DB);
        }
      }
      else
      if (ClassIndex==3) { // Author
        String AgeStr = new String();
        String HeightStr = new String();

        ClassIndex=Math.abs(R.nextInt()%3);

        if (Type2==0) {
          if (Math.abs(R.nextInt()%2) == 0) { // Crisp Data
            AgeStr=Integer.toString(Math.abs(R.nextInt()%100));
          }
          else { // Fuzzy Data
            AgeStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
            AgeStr="F"+Database.GetChars(3-AgeStr.length(),"0")+AgeStr;
          }

          if (Math.abs(R.nextInt()%2) == 0) { // Crisp Data
            HeightStr=Integer.toString(Math.abs(R.nextInt()%80)+120);
          }
          else { // Fuzzy Data
            HeightStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
            HeightStr="F"+Database.GetChars(3-HeightStr.length(),"0")+HeightStr;
          }
        }
        else
        if (Type2==1) { // Uniform Data
          AuthorCount++;
          int RN = Math.abs(AuthorCount % 2);

          if (RN==0) { // Crisp Data
            AgeStr=Integer.toString(Math.abs(R.nextInt()%100));
            HeightStr=Integer.toString(Math.abs(R.nextInt()%80)+120);
          }
          else { // Fuzzy Data
            AgeStr=Integer.toBinaryString(Math.abs((AuthorCount/2)%8));
            AgeStr="F"+Database.GetChars(3-AgeStr.length(),"0")+AgeStr;
            HeightStr=Integer.toBinaryString(Math.abs((AuthorCount/2)%8));
            HeightStr="F"+Database.GetChars(3-HeightStr.length(),"0")+HeightStr;
          }
        }
        else
        if (Type2==2) { // Same Data
          AgeStr=TempAgeStrType2;
          HeightStr=TempHeightStrType2;
        }

        if (ClassIndex==0) { // Author
          Author A = new Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr));
          Temp_Authors.addElement(A);
        }
        else
        if (ClassIndex==1) { // Native Author
          Native_Author NA = new Native_Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr),"City"+Integer.toString(Math.abs(R.nextInt()%10000)));
          Temp_Authors.addElement(NA);
        }
        else
        if (ClassIndex==2) { // Foreign Author
          Foreign_Author FA = new Foreign_Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr),"Nationality"+Integer.toString(Math.abs(R.nextInt()%10000)));
          Temp_Authors.addElement(FA);
        }
      }
    }

    for (i=0;i<Temp_Authors.size();i++) {
      this.Add_Author((Author)Temp_Authors.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Books.size();i++) {
      this.Add_Book((Book)Temp_Books.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Members.size();i++) {
      this.Add_Member((Member)Temp_Members.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=0;i<Temp_Libraries.size();i++) {
      this.Add_Library((Library)Temp_Libraries.elementAt(i),DiskAccessP,DiskAccessS);
    }
  }

  public void Insert_IndexClassInsertion(int Quantity, int Type,ObjectInt DiskAccessP,ObjectInt DiskAccessS) { // Type = 0 Random Data, Type = 1 Uniform Data, Type = 2 Same Data
    int i;
    String TempAgeStrType2 = new String();
    String TempHeightStrType2 = new String();

    Random R = new Random(1000000); // get a random generator

    if (Type==2) {
      TempAgeStrType2=Integer.toBinaryString(Math.abs(R.nextInt()%8));
      TempAgeStrType2="F"+Database.GetChars(3-TempAgeStrType2.length(),"0")+TempAgeStrType2;
      TempHeightStrType2=Integer.toBinaryString(Math.abs(R.nextInt()%8));
      TempHeightStrType2="F"+Database.GetChars(3-TempHeightStrType2.length(),"0")+TempHeightStrType2;
    }


    for (i=0;i<Quantity;i++) {
      String AgeStr = new String();
      String HeightStr = new String();

      if (Type==0) {
        if (Math.abs(R.nextInt()%2) == 0) { // Crisp Data
          AgeStr=Integer.toString(Math.abs(R.nextInt()%100));
        }
        else { // Fuzzy Data
          AgeStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
          AgeStr="F"+Database.GetChars(3-AgeStr.length(),"0")+AgeStr;
        }

        if (Math.abs(R.nextInt()%2) == 0) { // Crisp Data
          HeightStr=Integer.toString(Math.abs(R.nextInt()%80)+120);
        }
        else { // Fuzzy Data
          HeightStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
          HeightStr="F"+Database.GetChars(3-HeightStr.length(),"0")+HeightStr;
        }
      }
      else
      if (Type==1) {
        int RN = Math.abs(i % 9);

        if (RN==8) { // Crisp Data
          AgeStr=Integer.toString(Math.abs(R.nextInt()%100));
          HeightStr=Integer.toString(Math.abs(R.nextInt()%80)+120);
        }
        else { // Fuzzy Data
          AgeStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
          AgeStr="F"+Database.GetChars(3-AgeStr.length(),"0")+AgeStr;
          HeightStr=Integer.toBinaryString(Math.abs(R.nextInt()%8));
          HeightStr="F"+Database.GetChars(3-HeightStr.length(),"0")+HeightStr;
        }
      }
      else
      if (Type==2) { // Same Data
        AgeStr=TempAgeStrType2;
        HeightStr=TempHeightStrType2;
      }

      Author A = new Author("Name"+Integer.toString(Math.abs(R.nextInt()%10000)),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Age,Database.Operator_Age,AgeStr),Database.GetFuzzyTermStr(Database.Fuzzy_Terms_Height,Database.Operator_Height,HeightStr));
      this.Add_Author(A,DiskAccessP,DiskAccessS);
    }
  }

  public void PrintFOODIndexTree() {
    Vector Tree = new Vector(0,1);
    this.FI.PrintFOODIndex();
  }

  public void PrintFOODIndexTree(TextArea textArea_IndexTree) {
    Vector Tree = new Vector(0,1);
    textArea_IndexTree.setText("");
    this.FI.PrintFOODIndex(Tree);
    String TempStr=new String();
    for (int i=0;i<Tree.size();i++) {
      TempStr = (String) Tree.elementAt(i);
      textArea_IndexTree.append(TempStr+"\n");
    }
  }

  public void Delete_All(ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    int i;

    for (i=Libraries.size()-1;i>=0;i--) {
      this.Delete_Library((Library)Libraries.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=Members.size()-1;i>=0;i--) {
      this.Delete_Member((Member)Members.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=Books.size()-1;i>=0;i--) {
      this.Delete_Book((Book)Books.elementAt(i),DiskAccessP,DiskAccessS);
    }

    for (i=Authors.size()-1;i>=0;i--) {
      this.Delete_Author((Author)Authors.elementAt(i),DiskAccessP,DiskAccessS);
    }
  }

  public void Delete_RandomDeletion(int Quantity,ObjectInt DiskAccessP,ObjectInt DiskAccessS) {
    int ClassIndex;
    int ObjectPos;
    int Count;
    boolean Increased=false;
    Random R = new Random(1000000); // get a random generator

    int TotalNumberofObjects = this.Libraries.size()+this.Members.size()+this.Books.size()+this.Authors.size();
    if (Quantity>TotalNumberofObjects)
      Quantity=TotalNumberofObjects;

    Count=0;
    ClassIndex=0;
    while (Count<Quantity) {
      Increased=false;

      if (ClassIndex==0) {
        if (this.Libraries.size()>0) {
          ObjectPos = Math.abs(R.nextInt()%this.Libraries.size());
          this.Delete_Library((Library)this.Libraries.elementAt(ObjectPos),DiskAccessP,DiskAccessS);
          Count++;
          if (Count==Quantity)
            return;
          ClassIndex=Math.abs((ClassIndex+1) % 4);
          Increased=true;
        }
      }

      if ((Count==48) && (ClassIndex==1))
        Count=Count;
      if (ClassIndex==1) {
        if (this.Members.size()>0) {
          ObjectPos = Math.abs(R.nextInt()%this.Members.size());
          this.Delete_Member((Member)this.Members.elementAt(ObjectPos),DiskAccessP,DiskAccessS);
          Count++;
          if (Count==Quantity)
            return;
          ClassIndex=Math.abs((ClassIndex+1) % 4);
          Increased=true;
        }
      }

      if (ClassIndex==2) {
        if (this.Books.size()>0) {
          ObjectPos = Math.abs(R.nextInt()%this.Books.size());
          this.Delete_Book((Book)this.Books.elementAt(ObjectPos),DiskAccessP,DiskAccessS);
          Count++;
          if (Count==Quantity)
            return;
          ClassIndex=Math.abs((ClassIndex+1) % 4);
          Increased=true;
        }
      }

      if (ClassIndex==3) {
        if (this.Authors.size()>0) {
          ObjectPos = Math.abs(R.nextInt()%this.Authors.size());
          this.Delete_Author((Author)this.Authors.elementAt(ObjectPos),DiskAccessP,DiskAccessS);
          Count++;
          if (Count==Quantity)
            return;
          ClassIndex=Math.abs((ClassIndex+1) % 4);
          Increased=true;
        }
      }
      if (!Increased)
        ClassIndex=Math.abs((ClassIndex+1) % 4);
    }
  }
}