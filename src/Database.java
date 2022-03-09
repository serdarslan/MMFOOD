import java.util.*;
/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class Database {
  public static int FOOD_INDEX=1;
  public static int INDEX_DIMENSION=1;
  public static int NUMBER_OF_AGGREGATION_CLASS=5;
  public static int MAX_NUMBER_OF_RECORD_IN_A_NODE=128;
  public static int MIN_NUMBER_OF_RECORD_IN_A_NODE=MAX_NUMBER_OF_RECORD_IN_A_NODE/2+MAX_NUMBER_OF_RECORD_IN_A_NODE%2;
  public static int MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET=128;
  public static int MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING=32;
  public static int MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING=64;
  public static int MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE=128;

  public static int BIT_COUNT_FUZZY_TERMS=3;
  public static int BIT_COUNT_PARTNO=2;
  public static int BIT_COUNT_MEMBERSHIP_DEGREE=18;
  public static int BIT_COUNT_ORIGINAL_DATA=9;

/*  public static int MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING=16;
  public static int MAX_NUMBER_OF_BITS_IN_ANY_BIT_STRING=64;

  public static int BIT_COUNT_FUZZY_TERMS=3;
  public static int BIT_COUNT_PARTNO=2;
  public static int BIT_COUNT_MEMBERSHIP_DEGREE=3;
  public static int BIT_COUNT_ORIGINAL_DATA=8;*/

  public static int OID=0;

  static String[] Fuzzy_Terms_Age = {"old","middle-aged","young"};
  static String[] Fuzzy_Terms_Height = {"tall","medium","short"};
  static String[] Fuzzy_Terms_Subject_Domain = {"algorithms","data structures","database","theory"};
  static String[] Fuzzy_Terms_Education_Domain = {"phd","ms","under graduate","high school"};

//                                             Y    M    O   YM    YO  MO   YMO  NULL
  static double[][] Similarity_Matrix_Age = {{1.0, 0.6, 0.3, 0.6, 0.3, 0.3, 0.3, 0.0}, // Y
                                             {0.6, 1.0, 0.5, 0.6, 0.5, 0.5, 0.5, 0.0}, // M
                                             {0.3, 0.5, 1.0, 0.3, 0.3, 0.5, 0.3, 0.0}, // O
                                             {0.6, 0.6, 0.3, 1.0, 0.3, 0.3, 0.3, 0.0}, // YM
                                             {0.3, 0.5, 0.3, 0.3, 1.0, 0.3, 0.3, 0.0}, // YO
                                             {0.3, 0.5, 0.5, 0.3, 0.3, 1.0, 0.3, 0.0}, // MO
                                             {0.3, 0.5, 0.3, 0.3, 0.3, 0.3, 1.0, 0.0}, // YMO
                                             {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}}; // NULL

//                                                S    M    T   SM   ST   MT   SMT  NULL
  static double[][] Similarity_Matrix_Height = {{1.0, 0.5, 0.3, 0.5, 0.3, 0.3, 0.3, 0.0}, // S
                                                {0.5, 1.0, 0.6, 0.5, 0.6, 0.6, 0.6, 0.0}, // M
                                                {0.3, 0.6, 1.0, 0.3, 0.3, 0.6, 0.3, 0.0}, // T
                                                {0.5, 0.5, 0.3, 1.0, 0.3, 0.3, 0.3, 0.0}, // SM
                                                {0.3, 0.6, 0.3, 0.3, 1.0, 0.3, 0.3, 0.0}, // ST
                                                {0.3, 0.6, 0.6, 0.3, 0.3, 1.0, 0.3, 0.0}, // MT
                                                {0.3, 0.6, 0.3, 0.3, 0.3, 0.3, 1.0, 0.0}, // SMT
                                                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}}; // NULL

  static double[][] Similarity_Matrix_Subject_Domain = {{1.0, 0.8, 0.7, 0.6},
                                                        {0.8, 1.0, 0.6, 0.9},
                                                        {0.7, 0.6, 1.0, 0.5},
                                                        {0.6, 0.9, 0.5, 1.0}};

  static double[][] Similarity_Matrix_Education_Domain = {{1.0 ,0.8, 0.6, 0.4},
                                                          {0.8 ,1.0 ,0.8 ,0.5},
                                                          {0.6, 0.8, 1.0, 0.7},
                                                          {0.4, 0.5, 0.7, 1.0}};
//                                                    L    M    HE   E    L    A    S    B    S    E    CS   CE   A    R    D    A    NA   FA
  static double[][] Similarity_Matrix_for_Classes = {{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // L
                                                     {0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // M
                                                     {0.0, 1.0, 1.0, 0.0, 1.0, 0.9, 0.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // HE
                                                     {0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // E
                                                     {0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // L
                                                     {0.0, 1.0, 0.9, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // A
                                                     {0.0, 0.9, 0.6, 0.9, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // S
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.9, 0.9, 0.0, 0.0, 0.0}, // B
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.7, 1.0, 0.7, 0.7, 0.0, 0.0, 0.0}, // S
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.7, 1.0, 0.9, 0.9, 0.9, 0.0, 0.0, 0.0}, // E
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.7, 1.0, 0.0, 1.0, 0.6, 0.7, 0.0, 0.0, 0.0}, // CS
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.7, 1.0, 0.0, 1.0, 0.9, 0.9, 0.9, 0.0, 0.0, 0.0}, // CE
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.9, 1.0, 0.9, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // A
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9, 0.7, 0.9, 0.6, 0.9, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0}, // R
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9, 0.7, 0.9, 0.7, 0.9, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0}, // D
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0}, // A
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0}, // NA
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0}, // FA
                                                    };
  static double[][] Similarity_Matrix_for_Classes2 = {{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // L
                                                     {0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // M
                                                     {0.0, 0.0, 1.0, 0.0, 0.9, 0.7, 0.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // HE
                                                     {0.0, 0.0, 0.0, 1.0, 0.0, 0.6, 0.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // E
                                                     {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // L
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // A
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // S
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // B
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // S
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.7, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // E
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.8, 0.9, 0.6, 0.0, 0.0, 0.0}, // CS
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.6, 0.7, 0.8, 0.0, 0.0, 0.0}, // CE
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // A
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0}, // R
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0}, // D
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0}, // A
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0}, // NA
                                                     {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}, // FA
                                                    };
  static String[] Classes = {"library","member","highly_educated","educated","lecturer","assistant","student","book","science_book","engineering_book","computer_science_book","computer_engineering_book","algorithms_book","robotics_book","database_book","author","native_author","foreign_author"};
  static String Operator_Age = new String("OR");
  static String Operator_Height = new String("OR");
  static String Operator_Subject_Domain = new String("OR");
  static String Operator_Education_Domain = new String("OR");

  static int Get_Fuzzy_Term_Index(String[] Fuzzy_Terms,String Fuzzy_Term) {
    return(Database.ClassBitsToIndex(Database.GetFuzzyTermBits(Fuzzy_Terms,Fuzzy_Term).substring(1)));

/*    for (int Count=0;Count<Fuzzy_Terms.length;Count++) {
      if (Fuzzy_Terms[Count].compareTo(Fuzzy_Term)==0) {
        return(Fuzzy_Terms.length-Count-1);
      }
    }
    return(-1);*/
  }

  public static int GetClassIndex (String ClassName) {
    int i;
    for (i=0;i<Database.Classes.length;i++) {
      if (Database.Classes[i].compareTo(ClassName)==0) {
        return(i);
      }
    }
    return(-1);
  }

  public static int GetClassPositionInPath (int Index) { // Converts the class index into the class position in the path
    if (Index==0) return (3);
    else if (Index<7) return (2);
    else if (Index<15) return (1);
    else if (Index<18) return (0);
    else {
      System.out.println("170- There is not such an index !");
      System.exit(1);
    }
    return(-1);
  }

  public static void Membership_Function_Young (int age, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (age<20) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else if (age>35) {
      Membership_Degree.Set(0);
      Part_No.Set(2);
    }
    else {
      Membership_Degree.Set((35-age)/15.0);
      Part_No.Set(2);
    }
  }

  public static void Membership_Function_MiddleAged (int age, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (age<20) {
      Membership_Degree.Set(0);
      Part_No.Set(0);
    }
    else if ((age>=20) && (age<35)) {
      Membership_Degree.Set((age-20)/15.0);
      Part_No.Set(0);
    }
    else if ((age>=35) && (age<50)) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else if ((age>=50) && (age<65)) {
      Membership_Degree.Set((65-age)/15.0);
      Part_No.Set(2);
    }
    else {
      Membership_Degree.Set(0);
      Part_No.Set(2);
    }
  }

  public static void Membership_Function_Old (int age, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (age<50) {
      Membership_Degree.Set(0);
      Part_No.Set(0);
    }
    else if (age>65) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else {
      Membership_Degree.Set((age-50)/15.0);
      Part_No.Set(0);
    }
  }

  public static void Membership_Function_Short (int height, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (height<120) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else if (height>140) {
      Membership_Degree.Set(0);
      Part_No.Set(2);
    }
    else {
      Membership_Degree.Set((140-height)/20.0);
      Part_No.Set(2);
    }
  }

  public static void Membership_Function_Medium (int height, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (height<120) {
      Membership_Degree.Set(0);
      Part_No.Set(0);
    }
    else if ((height>=120) && (height<140)) {
      Membership_Degree.Set((height-120)/20.0);
      Part_No.Set(0);
    }
    else if ((height>=140) && (height<160)) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else if ((height>=160) && (height<180)) {
      Membership_Degree.Set((180-height)/20.0);
      Part_No.Set(2);
    }
    else {
      Membership_Degree.Set(0);
      Part_No.Set(2);
    }
  }

  public static void Membership_Function_Tall (int height, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    if (height<160) {
      Membership_Degree.Set(0);
      Part_No.Set(0);
    }
    else if (height>180) {
      Membership_Degree.Set(1);
      Part_No.Set(1);
    }
    else {
      Membership_Degree.Set((height-160)/20.0);
      Part_No.Set(0);
    }
  }

  public static double Membership_Degree (String Fuzzy_Terms,String[] domain,String[] range,double[][] similarity_matrix) {
    // Min of Max
    int i,j;
    double Min=2,Max=-1;
    String FuzzyTerms = new String("");

    for (i=0;i<domain.length;i++)
      if (Database.IncludeString(Fuzzy_Terms,domain[i])>=0)
        FuzzyTerms=FuzzyTerms+"1";
      else
        FuzzyTerms=FuzzyTerms+"0";

    for (i=0;i<FuzzyTerms.length();i++) {
      Max=-1;
      if (FuzzyTerms.charAt(i)=='1') {
        for (j=0;j<range.length;j++) {
          if (similarity_matrix[i][j]>Max)
            Max=similarity_matrix[i][j];
        }
        if (Max!=-1) {
          if (Max<Min)
            Min=Max;
        }
      }
    }
    if (Min!=2)
      return(Min);
    else
      return(0);
  }

  public static int IncludeString (String S1, String S2) {
    for (int i=0;i<=S1.length()-S2.length();i++) {
      if (S1.substring(i,i+S2.length()).equals(S2)) {
        return(i);
      }
    }
    return(-1);
  }

  public static String GetFuzzyTermBits(String[] Fuzzy_Terms,String Value) {
    int Number;
    String Temp_Bits = "";

    if (Value.compareTo("")==0)
      return("F000");

    try {
      Number=Integer.parseInt(Value);
      Temp_Bits=Value;
    }
    catch (Exception E){
      for (int i=0;i<Fuzzy_Terms.length;i++)
        if (Database.IncludeString(Value,Fuzzy_Terms[i])>=0)
          Temp_Bits=Temp_Bits+"1";
        else
          Temp_Bits=Temp_Bits+"0";
      Temp_Bits="F"+Temp_Bits;
    }
    if (Temp_Bits.compareTo("F000")==0)
      return("");
    return(Temp_Bits);
  }

  public static String GetFuzzyTermStr(String[] Fuzzy_Terms,String Fuzzy_Operator,String Value) {
    int Number;
    String TempFuzzyTermStr = new String("");

    try {
      Number=Integer.parseInt(Value);
      return(Value);
    }
    catch (Exception E) {
      for (int i=1;i<Value.length();i++)
        if (Value.substring(i,i+1).equals("1"))
          TempFuzzyTermStr=","+Fuzzy_Terms[i-1]+TempFuzzyTermStr;

      if (TempFuzzyTermStr.equals("")) {
        return("");
      }
      else {
        TempFuzzyTermStr=TempFuzzyTermStr.substring(1);
        if (Fuzzy_Operator.equals("AND"))
          TempFuzzyTermStr="<"+TempFuzzyTermStr+">";
        else if (Fuzzy_Operator.equals("OR"))
          TempFuzzyTermStr="{"+TempFuzzyTermStr+"}";
        else
          TempFuzzyTermStr="["+TempFuzzyTermStr+"]";
        return(TempFuzzyTermStr);
      }
    }
  }

  public static String Construct_Bit_String(String Attribute,String Attribute_Value) {
    String Temp_BS = new String("");
    String FuzzyTermBits = new String("");
    int Number;

    if (Attribute.equals("age")) {
      try {
        if (Attribute_Value.compareTo("")==0)
          FuzzyTermBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,Attribute_Value);
        else {
          Number=Integer.parseInt(Attribute_Value);
          FuzzyTermBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,Attribute_Value);
        }
      }
      catch (Exception E){
        FuzzyTermBits=Attribute_Value;
      }
    }
    else {
      try {
        if (Attribute_Value.compareTo("")==0)
          FuzzyTermBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,Attribute_Value);
        else {
          Number=Integer.parseInt(Attribute_Value);
          FuzzyTermBits = Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,Attribute_Value);
        }
      }
      catch (Exception E){
        FuzzyTermBits=Attribute_Value;
      }
    }

    if ((FuzzyTermBits.length()>0) && (FuzzyTermBits.charAt(0)=='F')) {
      if (Database.FOOD_INDEX == 1) { // if FOOD Index
        Temp_BS=FuzzyTermBits.substring(1);
        Temp_BS=Temp_BS+"11";
        Temp_BS=Temp_BS+Database.Convert_Membership_Degree_into_Binary(1);
        Temp_BS=Temp_BS+GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0");
      }
      else { // if Path Index
        Temp_BS="0"; // fuzzy value
        Temp_BS=Temp_BS+FuzzyTermBits.substring(1); // fuzzy term
        Temp_BS=Temp_BS+"1"; // Membership degree = 1
        Temp_BS=Temp_BS+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-5,"0");
      }
    }
    else {
      if (Database.FOOD_INDEX == 1) { // if FOOD Index
        ObjectInt PartNo1 = new ObjectInt(0), PartNo2 = new ObjectInt(0), PartNo3 = new ObjectInt(0);
        ObjectDouble MD1 = new ObjectDouble(0), MD2 = new ObjectDouble(0), MD3 = new ObjectDouble(0);
        String TempBinary =  new String("");

        if (Attribute.equals("age")) {
          Database.Membership_Function_Young(Integer.parseInt(Attribute_Value),MD1,PartNo1);
          Database.Membership_Function_MiddleAged(Integer.parseInt(Attribute_Value),MD2,PartNo2);
          Database.Membership_Function_Old(Integer.parseInt(Attribute_Value),MD3,PartNo3);
        }
        else {
          Database.Membership_Function_Short(Integer.parseInt(Attribute_Value),MD1,PartNo1);
          Database.Membership_Function_Medium(Integer.parseInt(Attribute_Value),MD2,PartNo2);
          Database.Membership_Function_Tall(Integer.parseInt(Attribute_Value),MD3,PartNo3);
        }

        if ((MD1.Get()>=MD2.Get()) && (MD1.Get()>=MD3.Get())) {
          Temp_BS = "001"; // fuzzy term
          String PartNoS = new String(Integer.toBinaryString(PartNo1.Get())); // Part No
          Temp_BS = Temp_BS + GetChars(Database.BIT_COUNT_PARTNO-PartNoS.length(),"0") + PartNoS;
          Temp_BS = Temp_BS + Convert_Membership_Degree_into_Binary(MD1.Get()); // membership degree
        }
        else if ((MD2.Get()>=MD1.Get()) && (MD2.Get()>=MD3.Get())) {
          Temp_BS = "010"; // fuzzy term
          String PartNoS = new String(Integer.toBinaryString(PartNo2.Get())); // Part No
          Temp_BS = Temp_BS + GetChars(Database.BIT_COUNT_PARTNO-PartNoS.length(),"0") + PartNoS;
          Temp_BS = Temp_BS + Convert_Membership_Degree_into_Binary(MD2.Get()); // membership degree
        }
        else {
          Temp_BS = "100"; // fuzzy term
          String PartNoS = new String(Integer.toBinaryString(PartNo3.Get())); // Part No
          Temp_BS = Temp_BS + GetChars(Database.BIT_COUNT_PARTNO-PartNoS.length(),"0") + PartNoS;
          Temp_BS = Temp_BS + Convert_Membership_Degree_into_Binary(MD3.Get()); // membership degree
        }

        TempBinary = Integer.toBinaryString(Integer.parseInt(Attribute_Value)); // original data in binary form
        Temp_BS = Temp_BS + GetChars(Database.BIT_COUNT_ORIGINAL_DATA-TempBinary.length(),"0") + TempBinary; // original data
      }
      else { // if Path Index
        String TempOriginalData = new String();
        Temp_BS="1"; // crisp value
        TempOriginalData=Integer.toBinaryString(Integer.parseInt(Attribute_Value));
        Temp_BS=Temp_BS+Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA-TempOriginalData.length(),"0")+TempOriginalData; // original data in binary form
        Temp_BS=Temp_BS+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Database.BIT_COUNT_ORIGINAL_DATA-1,"0");
      }
    }
    return(Temp_BS);
  }

  public static void Construct_Bit_String_Fuzzy(String Attribute,String Fuzzy_Term,String Threshold,Vector RBSs) {
    if (Database.FOOD_INDEX == 1) { // if FOOD Index
      double ThresholdDouble = Double.valueOf(Threshold).doubleValue();
      int Fuzzy_Term_Index;
      String Fuzzy_Term_Bits;
      boolean MultiValued_Fuzzy_Term;

      if (Attribute.compareTo("age")==0) {
        Fuzzy_Term_Bits = new String (Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Age,Fuzzy_Term));
        Fuzzy_Term_Index = Database.Get_Fuzzy_Term_Index(Database.Fuzzy_Terms_Age,Fuzzy_Term);
      }
      else { // height
        Fuzzy_Term_Bits = new String (Database.GetFuzzyTermBits(Database.Fuzzy_Terms_Height,Fuzzy_Term));
        Fuzzy_Term_Index = Database.Get_Fuzzy_Term_Index(Database.Fuzzy_Terms_Height,Fuzzy_Term);
      }

      if (ThresholdDouble==0) {
        String TempBS1 = new String(""), TempBS2 = new String("");

        TempBS1 = GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING ,"0");
        TempBS2 = GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING ,"1");
        RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
        return;
      }

      if (Fuzzy_Term_Bits.compareTo("F000")==0) {
        String TempBS1 = new String(""), TempBS2 = new String("");

        TempBS1 = Fuzzy_Term_Bits.substring(1) + GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-3 ,"0");
        TempBS2 = Fuzzy_Term_Bits.substring(1) + GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-3 ,"1");
        RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
        return;
      }

      if (Database.IsMaltivalued(Fuzzy_Term_Bits)) { // if Fuzzy Term is multi-valued
        String TempBS1 = new String(""), TempBS2 = new String("");
        String TempBS = new String("");
        double Membership_Degree;

        for (int Count=0;Count<8;Count++) {
          TempBS=Database.ClassIndexToBits(Count);
          Membership_Degree = ThresholdDouble / Database.Similarity_Matrix_Age[Fuzzy_Term_Index][Count];

          if (Membership_Degree>1) // Membership degree is impossible to be greater than 1.0
            continue;

          TempBS1 = TempBS;
          TempBS1 = TempBS1 + "00"; // Part No = 0
          TempBS1 = TempBS1 + Convert_Membership_Degree_into_Binary(Membership_Degree); // membership degree
          TempBS1 = TempBS1 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0");

          TempBS2 = TempBS;
          TempBS2 = TempBS2 + "11"; // Part No = 3
          TempBS2 = TempBS2 + Database.Convert_Membership_Degree_into_Binary(1); // membership degree
          TempBS2 = TempBS2 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1");

          RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
        }
      }
      else { // if Fuzzy Term is single-valued
        String TempBS1 = new String(""), TempBS2 = new String("");
        String TempBS = new String("");

        for (int Count=0;Count<8;Count++) {
          TempBS=Database.ClassIndexToBits(Count);

          if (Count<3) { // for crisp values
            double Membership_Degree = ThresholdDouble * Database.Similarity_Matrix_Age[Fuzzy_Term_Index][Count];
            String Temp_PartNo = new String("");

            if (Count==Fuzzy_Term_Index) {
              TempBS1 = TempBS;
              TempBS1 = TempBS1 + "00"; // Part No = 0
              TempBS1 = TempBS1 + Database.Convert_Membership_Degree_into_Binary(Membership_Degree); // membership degree
              TempBS1 = TempBS1 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0");

              TempBS2 = TempBS;
              TempBS2 = TempBS2 + "10"; // Part No = 2
              TempBS2 = TempBS2 + Convert_Membership_Degree_into_Binary(1); // membership degree
              TempBS2 = TempBS2 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1");

              RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
//              continue;
            }

            if ((Count==(Fuzzy_Term_Index-1)) || (Count==(Fuzzy_Term_Index+1))) {
              if (Count==(Fuzzy_Term_Index-1))  // if fuzzy term is in the previous place
                Temp_PartNo="10"; // Part No = 2
              else
              if (Count==(Fuzzy_Term_Index+1))  // if fuzzy term is in the next place
                Temp_PartNo="00"; // Part No = 0

              TempBS1 = TempBS;
              TempBS1 = TempBS1 + Temp_PartNo; // Part No
              TempBS1 = TempBS1 + Database.Convert_Membership_Degree_into_Binary(0); // membership degree
              TempBS1 = TempBS1 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0");

              TempBS2 = TempBS;
              TempBS2 = TempBS2 + Temp_PartNo; // Part No
              TempBS2 = TempBS2 + Convert_Membership_Degree_into_Binary(Membership_Degree); // membership degree
              TempBS2 = TempBS2 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1");

              RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
            }
          }

          // for fuzzy values
          double Membership_Degree = ThresholdDouble / Database.Similarity_Matrix_Age[Fuzzy_Term_Index][Count];

          if (Membership_Degree>1) // Membership degree is impossible to be greater than 1.0
            continue;

          TempBS1 = TempBS;
          TempBS1 = TempBS1 + "11"; // Part No = 3
          TempBS1 = TempBS1 + Convert_Membership_Degree_into_Binary(Membership_Degree); // membership degree
          TempBS1 = TempBS1 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0");

          TempBS2 = TempBS;
          TempBS2 = TempBS2 + "11"; // Part No = 3
          TempBS2 = TempBS2 + Database.Convert_Membership_Degree_into_Binary(1); // membership degree
          TempBS2 = TempBS2 + GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1");

          RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
        }
      }
    }
    else { // if Path Index
      String TempBS1 = new String(""), TempBS2 = new String("");

      TempBS1 = "0"; // fuzzy value
      TempBS1 = TempBS1 + GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-1,"0"); // all others are 0

      TempBS2 = "1"; // crisp value
      TempBS2 = TempBS2 + GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-1,"1"); // all others are 0

      RBSs.addElement(new Result_BitString(new BitString(TempBS1),new BitString(TempBS2)));
    }
  }

  public static void Construct_Bit_String_Crisp(String Attribute,String Operator,String Attribute_Value,Vector RBSs) {
    if (Database.FOOD_INDEX == 1) { // if FOOD Index
      ObjectInt PartNo = new ObjectInt(0);
      ObjectDouble MD = new ObjectDouble(0);
      BitString StartBS,StopBS;
      String Temp_BS = new String(""), Temp_BS1 = new String(""), Temp_BS2 = new String("");
      String TempBinary="";

      for (int FuzzyTermCount=0;FuzzyTermCount<3;FuzzyTermCount++) {
        if (FuzzyTermCount==0) {
          Temp_BS = "001";

          if (Attribute.compareTo("age")==0) {
            Database.Membership_Function_Young(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else
          if (Attribute.compareTo("height")==0) {
            Database.Membership_Function_Short(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else {
            System.out.println("160- Hata Unknown Dimension");
            System.exit(1);
          }
        }
        else
        if (FuzzyTermCount==1) {
          Temp_BS = "010";

          if (Attribute.compareTo("age")==0) {
            Database.Membership_Function_MiddleAged(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else
          if (Attribute.compareTo("height")==0) {
            Database.Membership_Function_Medium(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else {
            System.out.println("161- Hata Unknown Dimension");
            System.exit(1);
          }
        }
        else
        if (FuzzyTermCount==2) {
          Temp_BS = "100";

          if (Attribute.compareTo("age")==0) {
            Database.Membership_Function_Old(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else
          if (Attribute.compareTo("height")==0) {
            Database.Membership_Function_Tall(Integer.parseInt(Attribute_Value),MD,PartNo);
          }
          else {
            System.out.println("162- Hata Unknown Dimension");
            System.exit(1);
          }
        }

        // if Memhership Degree is 0, do not care
        if (MD.Get()==0)
          continue;

        if ((PartNo.Get()==0) || (PartNo.Get()==1)) {
          if (PartNo.Get()==0)
            Temp_BS1 = Temp_BS + "00";
          else
            Temp_BS1 = Temp_BS + "01";

          Temp_BS1 = Temp_BS1 + Database.Convert_Membership_Degree_into_Binary(MD.Get()); // membership degree
          TempBinary = Integer.toBinaryString(Integer.parseInt(Attribute_Value)); // original data in binary form
          Temp_BS1 = Temp_BS1 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA-TempBinary.length(),"0") + TempBinary; // original data

          if ((Operator.compareTo("<")==0) || (Operator.compareTo("<=")==0)) {
            Temp_BS2 = Temp_BS + "00";
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_MEMBERSHIP_DEGREE,"0"); // membership degree
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0"); // original data
            RBSs.addElement(new Result_BitString(new BitString(Temp_BS2),new BitString(Temp_BS1)));
          }
          else {
            Temp_BS2 = Temp_BS + "10";
            Temp_BS2 = Temp_BS2 + Database.Convert_Membership_Degree_into_Binary(1); // membership degree
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1"); // original data
            RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));
          }
        }
        else
        if (PartNo.Get()==2) {
          if ((Operator.compareTo("<")==0) || (Operator.compareTo("<=")==0)) {
            Temp_BS1 = Temp_BS + "10";
            Temp_BS1 = Temp_BS1 + Database.Convert_Membership_Degree_into_Binary(MD.Get()); // membership degree
            Temp_BS1 = Temp_BS1 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0"); // original data

            Temp_BS2 = Temp_BS + "10";
            Temp_BS2 = Temp_BS2 + Database.Convert_Membership_Degree_into_Binary(1); // membership degree
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1"); // original data

            RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));

            Temp_BS1 = Temp_BS + "00";
            Temp_BS1 = Temp_BS1 + Database.Convert_Membership_Degree_into_Binary(0); // membership degree
            Temp_BS1 = Temp_BS1 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0"); // original data

            Temp_BS2 = Temp_BS + "01";
            Temp_BS2 = Temp_BS2 + Database.Convert_Membership_Degree_into_Binary(1); // membership degree
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1"); // original data

            RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));
          }
          else {
            Temp_BS1 = Temp_BS + "10";
            Temp_BS1 = Temp_BS1 + Database.Convert_Membership_Degree_into_Binary(0); // membership degree
            Temp_BS1 = Temp_BS1 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"0"); // original data

            Temp_BS2 = Temp_BS + "10";
            Temp_BS2 = Temp_BS2 + Database.Convert_Membership_Degree_into_Binary(MD.Get()); // membership degree
            Temp_BS2 = Temp_BS2 + Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA,"1"); // original data

            RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));
          }
        }
      }
    }
    else { // if Path Index
      String Temp_BS1 = new String(""), Temp_BS2 = new String("");

      if ((Operator.compareTo("<")==0) || (Operator.compareTo("<=")==0)) {
        Temp_BS1="1"; // crisp value
        Temp_BS1=Temp_BS1+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Temp_BS1.length(),"0");

        String TempOriginalData = new String();
        Temp_BS2="1"; // crisp value
        TempOriginalData=Integer.toBinaryString(Integer.parseInt(Attribute_Value));
        Temp_BS2=Temp_BS2+Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA-TempOriginalData.length(),"0")+TempOriginalData; // original data in binary form
        Temp_BS2=Temp_BS2+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Temp_BS2.length(),"1");
        RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));
/*        Temp_BS1=Temp_BS1+Database.Convert_Membership_Degree_into_Binary(0); // original value
        Temp_BS1=Temp_BS1+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Database.BIT_COUNT_ORIGINAL_DATA-1,"0");

        Temp_BS2="1"; // crisp value
        Temp_BS2=Temp_BS2+Database.Convert_Membership_Degree_into_Binary(Integer.parseInt(Attribute_Value)); // original value
        Temp_BS2=Temp_BS2+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Database.BIT_COUNT_ORIGINAL_DATA-1,"1");*/
      }
      else
      if ((Operator.compareTo(">")==0) || (Operator.compareTo(">=")==0)) {
        String TempOriginalData = new String();
        Temp_BS1="1"; // crisp value
        TempOriginalData=Integer.toBinaryString(Integer.parseInt(Attribute_Value));
        Temp_BS1=Temp_BS1+Database.GetChars(Database.BIT_COUNT_ORIGINAL_DATA-TempOriginalData.length(),"0")+TempOriginalData; // original data in binary form
        Temp_BS1=Temp_BS1+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Temp_BS1.length(),"1");

        Temp_BS2="1"; // crisp value
        Temp_BS2=Temp_BS2+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Temp_BS2.length(),"1");
        RBSs.addElement(new Result_BitString(new BitString(Temp_BS1),new BitString(Temp_BS2)));
/*        Temp_BS1="1"; // crisp value
        Temp_BS1=Temp_BS1+Database.Convert_Membership_Degree_into_Binary(Integer.parseInt(Attribute_Value)); // original value
        Temp_BS1=Temp_BS1+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Database.BIT_COUNT_ORIGINAL_DATA-1,"0");

        Temp_BS2="1"; // crisp value
        Temp_BS2=Temp_BS2+Database.Convert_Membership_Degree_into_Binary(1); // original value
        Temp_BS2=Temp_BS2+Database.GetChars(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING-Database.BIT_COUNT_ORIGINAL_DATA-1,"1");*/
      }
    }
  }

  public static String Max_Membership_Function_for_Crisp_Age(int Age, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    ObjectInt PartNo1 = new ObjectInt(0), PartNo2 = new ObjectInt(0), PartNo3 = new ObjectInt(0);
    ObjectDouble MD1 = new ObjectDouble(0), MD2 = new ObjectDouble(0), MD3 = new ObjectDouble(0);

    Database.Membership_Function_Young(Age,MD1,PartNo1);
    Database.Membership_Function_MiddleAged(Age,MD2,PartNo2);
    Database.Membership_Function_Old(Age,MD3,PartNo3);

    if ((MD1.Get()>=MD2.Get()) || (MD1.Get()>=MD3.Get())) {
      Membership_Degree.Set(MD1);
      Part_No.Set(PartNo1);
      return("young");
    }
    else
    if ((MD2.Get()>=MD1.Get()) && (MD2.Get()>=MD3.Get())) {
      Membership_Degree.Set(MD2);
      Part_No.Set(PartNo2);
      return("middle-aged");
    }
    else {
      Membership_Degree.Set(MD3);
      Part_No.Set(PartNo3);
      return("old");
    }
  }

  public static String Max_Membership_Function_for_Crisp_Height(int Height, ObjectDouble Membership_Degree, ObjectInt Part_No) {
    ObjectInt PartNo1 = new ObjectInt(0), PartNo2 = new ObjectInt(0), PartNo3 = new ObjectInt(0);
    ObjectDouble MD1 = new ObjectDouble(0), MD2 = new ObjectDouble(0), MD3 = new ObjectDouble(0);

    Database.Membership_Function_Short(Height,MD1,PartNo1);
    Database.Membership_Function_Medium(Height,MD2,PartNo2);
    Database.Membership_Function_Tall(Height,MD3,PartNo3);

    if ((MD1.Get()>=MD2.Get()) || (MD1.Get()>=MD3.Get())) {
      Membership_Degree.Set(MD1);
      Part_No.Set(PartNo1);
      return("short");
    }
    else
    if ((MD2.Get()>=MD1.Get()) && (MD2.Get()>=MD3.Get())) {
      Membership_Degree.Set(MD2);
      Part_No.Set(PartNo2);
      return("medium");
    }
    else {
      Membership_Degree.Set(MD3);
      Part_No.Set(PartNo3);
      return("tall");
    }
  }

  public static String Convert_Membership_Degree_into_Binary (double M) {
    String Temp_M = new String("");
    double D;
    int i;

    if (M==0)
      Temp_M=GetChars(Database.BIT_COUNT_MEMBERSHIP_DEGREE,"0");
    else if (M==1)
      Temp_M="1"+GetChars(Database.BIT_COUNT_MEMBERSHIP_DEGREE-1,"0");
    else {
      Temp_M="0";
      for (i=1;i<Database.BIT_COUNT_MEMBERSHIP_DEGREE;i++) {
        D = 1/Math.pow(2,i);
        if (M>=D) {
          Temp_M=Temp_M+"1";
          M=M-D;
        }
        else {
          Temp_M=Temp_M+"0";
        }
      }
    }
    return(Temp_M);
  }

  public static double Convert_Membership_Degree_into_Double (String MDS) {
    double Temp_M = 0.0;
    int i;

    if (MDS.compareTo("1"+GetChars(Database.BIT_COUNT_MEMBERSHIP_DEGREE-1,"0"))==0)
      return(1.0);
    else if (MDS.compareTo(GetChars(Database.BIT_COUNT_MEMBERSHIP_DEGREE,"0"))==0)
      return(0.0);
    else {
      for (i=1;i<Database.BIT_COUNT_MEMBERSHIP_DEGREE;i++) {
        if (MDS.charAt(i)=='1')
          Temp_M = Temp_M + 1/Math.pow(2,i);
      }
    }
    return(Temp_M);
  }

  public static String GetChars(int Count,String S) {
    int i;
    String TempS = new String("");

    for (i=0;i<Count;i++) {
      TempS = TempS + S;
    }
    return(TempS);
  }

  public static String Combine_Bit_Strings(String BS1,String BS2) {
    String Temp_BS=new String("");

    for (int i=0; i<Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING;i++) {
      Temp_BS = Temp_BS + BS1.charAt(i) + BS2.charAt(i);
    }
    return(Temp_BS);
  }

  public static String Combine_Bit_Strings(BitString BS1,BitString BS2) {
    return(Database.Combine_Bit_Strings(BS1.GetBitString(),BS2.GetBitString()));
  }

  public static String GetSpaces(int Number) {
    String S="";
    for (int i=0;i<Number;i++)
      S=S+" |   ";

    S=S+" |---";
    return(S);
  }

  public static boolean IsMaltivalued(String Fuzzy_Term) {
    int Count=0;
    for (int i=1;i<Fuzzy_Term.length();i++) {
      if (Fuzzy_Term.charAt(i) == '1')
        Count++;
    }
    if (Count>1)
      return(true);
    else
      return(false);
  }

  public static double Minimum(double N1,double N2) {
    if (N1<=N2)
      return (N1);
    else
      return (N2);
  }

  public static String ClassIndexToBits (int Index) {
    if (Index==0) return("001");
    else if (Index==1) return("010");
    else if (Index==2) return("100");
    else if (Index==3) return("011");
    else if (Index==4) return("101");
    else if (Index==5) return("110");
    else if (Index==6) return("111");
    else
      return("");
  }

  public static int ClassBitsToIndex (String Bits) {
    if (Bits.compareTo("001")==0) return(0);
    else if (Bits.compareTo("010")==0) return(1);
    else if (Bits.compareTo("100")==0) return(2);
    else if (Bits.compareTo("011")==0) return(3);
    else if (Bits.compareTo("101")==0) return(4);
    else if (Bits.compareTo("110")==0) return(5);
    else if (Bits.compareTo("111")==0) return(6);
    else
      return(7);
  }

  public static void CopyVector(Vector V1, Vector V2) {
    for (int i=0;i<V2.size();i++)
      V1.addElement(V2.elementAt(i));
  }

  public Database() {
  }

  public static void main(String[] args) {
    for (int i=0;i<Database.Classes.length;i++)
      System.out.println(Database.Classes[i]+" : "+Database.GetClassIndex(Database.Classes[i]));
  }
}