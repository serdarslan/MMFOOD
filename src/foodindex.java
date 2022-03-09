import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class foodindex extends Applet {
  FOOD_DB FOODDB;
  boolean isStandalone = false;
  private TextArea textArea_IndexTree = new TextArea();
  private Label label_quantity = new Label();
  private Choice choice_TargetClass = new Choice();
  private Checkbox checkbox_randominsertion = new Checkbox();
  private Label label_NRDB = new Label();
  private Label label_Target_Attribute = new Label();
  private Button button_deletecondition = new Button();
  private Label label_From_Class = new Label();
  private Button button_StartRetrieval = new Button();
  private Label label_quantity_deletion = new Label();
  static public TextArea textArea_Status = new TextArea();
  private Button button_LoadExample = new Button();
  private TextField textField_NRN = new TextField();
  private TextField textField_quantity_insertion = new TextField();
  private Button button_DeleteAll = new Button();
  private Choice choice_operator = new Choice();
  private Choice choice_CrispValueOperator = new Choice();
  private TextArea textArea_DBObjects = new TextArea();
  private TextField textField_FromClassThreshold = new TextField();
  private TextField textField_FuzzyValueThreshold = new TextField();
  private Checkbox checkbox_twodimension = new Checkbox();
  private Choice choice_Attribute = new Choice();
  private Checkbox checkbox_uniforminsertion = new Checkbox();
  private TextArea textArea_FI = new TextArea();
  private TextField textField_NRDB = new TextField();
  private Checkbox checkbox_samedata = new Checkbox();
  private Panel tab_database = new Panel();
  private java.awt.List list_SQL = new java.awt.List();
  private Checkbox checkbox_uniformdatadistribution = new Checkbox();
  private TextField textField_TargetClassThreshold = new TextField();
  private Checkbox checkbox_randomdeletion = new Checkbox();
  private Label label_Target_Class = new Label();
  private Checkbox checkbox_FuzzyValue = new Checkbox();
  private Button button_Add = new Button();
  private TextField textField_quantity_deletion = new TextField();
  private Checkbox checkbox_indexclassinsertion = new Checkbox();
  private Checkbox checkbox_CrispValue = new Checkbox();
  private Checkbox checkbox_randomdatadistribution = new Checkbox();
  private Checkbox checkbox_onedimension = new Checkbox();
  private Label label_NRPIN = new Label();
  private Panel tab_insertion = new Panel();
  private TextField textField_NRPIN = new TextField();
  private Choice choice_FromClass = new Choice();
  private Label label_NRN = new Label();
  private Choice choice_TargetAttribute = new Choice();
  static TextArea textArea_ResultBitStrings = new TextArea();
  private Label label_index = new Label();
  private TextField textField_CrispValue = new TextField();
  private Button button_startdeletion = new Button();
  private Panel tab_deletion = new Panel();
  private Button button_startinsertion = new Button();
  private Panel tab_retrieval = new Panel();
  private Choice choice_FuzzyValue = new Choice();
  private CheckboxGroup checkboxGroup_IndexClassInsertion = new CheckboxGroup();
  private CheckboxGroup checkboxGroup_Insertion = new CheckboxGroup();
  private CheckboxGroup checkboxGroup_ValueType = new CheckboxGroup();
  private CheckboxGroup checkboxGroup_Dimension = new CheckboxGroup();
  private Button button_deletion = new Button();
  private Button button_database = new Button();
  private Button button_insertion = new Button();
  private Button button_retrieval = new Button();
  private Label label_FOODIndexDemo = new Label();
  public static Checkbox checkbox_printindex = new Checkbox();
  private Button button_startcontinousinsertion = new Button();
  private TextField textField_stepnumber_insertion = new TextField();
  private Label label_quantity_steps_insertion = new Label();
  public static Checkbox checkbox_FOOD_Index = new Checkbox();
  public static Checkbox checkbox_Path_Index = new Checkbox();
  private CheckboxGroup checkboxGroup_IndexName = new CheckboxGroup();
  private Label label1 = new Label();
  private Button button_startcontinousdeletion = new Button();
  private Label label_quantity_steps_deletion = new Label();
  private TextField textField_stepnumber_deletion = new TextField();
  private Label label_FOODIndexDemo1 = new Label();
  private Button button_GetStatistics = new Button();
  private Button button_getdbinfo = new Button();
  private Button button_StartTest = new Button();
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public foodindex() {
  }
  //Initialize the applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception {
    choice_TargetClass.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        choice_TargetClass_itemStateChanged(e);
      }
    });
    FOODDB = new FOOD_DB();
    choice_TargetClass.setBounds(new Rectangle(97, 31, 180, 23));
    label_quantity.setBounds(new Rectangle(8, 188, 56, 17));
    label_quantity.setFont(new Font("Dialog", 1, 12));
    label_quantity.setText("Quantity : ");
    textArea_IndexTree.setBounds(new Rectangle(8, 395, 413, 189));
    this.setLayout(null);
    choice_TargetClass.addItem("Library");
    choice_TargetClass.addItem("Member");
    choice_TargetClass.addItem("Highly-Educated Member");
    choice_TargetClass.addItem("Educated Member");
    choice_TargetClass.addItem("Lecturer");
    choice_TargetClass.addItem("Assistant");
    choice_TargetClass.addItem("Student");
    choice_TargetClass.addItem("Book");
    choice_TargetClass.addItem("Science Book");
    choice_TargetClass.addItem("Engineering Book");
    choice_TargetClass.addItem("Computer Science Book");
    choice_TargetClass.addItem("Computer Engineering Book");
    choice_TargetClass.addItem("Algorithms Book");
    choice_TargetClass.addItem("Robotics Book");
    choice_TargetClass.addItem("Database Book");
    choice_TargetClass.addItem("Author");
    choice_TargetClass.addItem("Native Author");
    choice_TargetClass.addItem("Foreign Author");
    choice_TargetClass.select(15);
    checkbox_randominsertion.setCheckboxGroup(checkboxGroup_Insertion);
    checkbox_randominsertion.setFont(new Font("Dialog", 1, 12));
    checkbox_randominsertion.setLabel("Insertion into Random Classes");
    checkbox_randominsertion.setBounds(new Rectangle(17, 17, 310, 25));
    label_NRDB.setFont(new Font("Dialog", 1, 12));
    label_NRDB.setText("Max Number of Records in a Data Bucket");
    label_NRDB.setBounds(new Rectangle(7, 92, 235, 22));
    label_Target_Attribute.setFont(new Font("Dialog", 1, 12));
    label_Target_Attribute.setText("Target Attribute");
    label_Target_Attribute.setBounds(new Rectangle(2, 52, 93, 22));
    button_deletecondition.setFont(new Font("Dialog", 1, 12));
    button_deletecondition.setLabel("DELETE CONDITION");
    button_deletecondition.setBounds(new Rectangle(553, 126, 157, 28));
    button_deletecondition.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_deletecondition_actionPerformed(e);
      }
    });
    label_From_Class.setFont(new Font("Dialog", 1, 12));
    label_From_Class.setText("From Class");
    label_From_Class.setBounds(new Rectangle(3, 7, 73, 21));
    button_StartRetrieval.setActionCommand("Start Retieval !");
    button_StartRetrieval.setBackground(new Color(92, 203, 223));
    button_StartRetrieval.setFont(new Font("Dialog", 1, 12));
    button_StartRetrieval.setLabel("START RETRIEVAL !");
    button_StartRetrieval.setBounds(new Rectangle(553, 156, 157, 25));
    button_StartRetrieval.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_StartRetrieval_actionPerformed(e);
     }
    });
    label_quantity_deletion.setBounds(new Rectangle(17, 57, 57, 17));
    label_quantity_deletion.setFont(new Font("Dialog", 1, 12));
    label_quantity_deletion.setText("Quantity : ");
    textArea_Status.setBounds(new Rectangle(426, 478, 289, 106));
    button_LoadExample.setBackground(new Color(92, 203, 217));
    button_LoadExample.setFont(new Font("Dialog", 1, 12));
    button_LoadExample.setLabel("LOAD EXAMPLE");
    button_LoadExample.setBounds(new Rectangle(296, 183, 136, 29));
    button_LoadExample.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_LoadExample_actionPerformed(e);
      }
    });
    textField_NRN.setText("128");
    textField_NRN.setBounds(new Rectangle(305, 67, 225, 21));
    textField_NRN.addTextListener(new TextListener() {
      public void textValueChanged(TextEvent e) {
        textField_NRN_textValueChanged(e);
      }
    });
    textField_quantity_insertion.setSelectionStart(2);
    textField_quantity_insertion.setText("0");
    textField_quantity_insertion.setBounds(new Rectangle(66, 187, 65, 21));
    button_DeleteAll.setBackground(new Color(92, 203, 223));
    button_DeleteAll.setFont(new Font("Dialog", 1, 12));
    button_DeleteAll.setLabel("DELETE ALL !");
    button_DeleteAll.setBounds(new Rectangle(302, 52, 143, 29));
    button_DeleteAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_DeleteAll_actionPerformed(e);
      }
    });
    choice_operator.setBounds(new Rectangle(316, 130, 54, 22));
    choice_CrispValueOperator.setBounds(new Rectangle(207, 82, 60, 20));
    textArea_DBObjects.setBounds(new Rectangle(426, 379, 290, 95));
    textField_FromClassThreshold.setText("0.9");
    textField_FromClassThreshold.setBounds(new Rectangle(284, 6, 54, 23));
    textField_FromClassThreshold.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        textField_FromClassThreshold_keyReleased(e);
      }
    });
    textField_FuzzyValueThreshold.setText("0.8");
    textField_FuzzyValueThreshold.setBounds(new Rectangle(322, 105, 50, 20));
    checkbox_twodimension.setCheckboxGroup(checkboxGroup_Dimension);
    checkbox_twodimension.setFont(new Font("Dialog", 1, 12));
    checkbox_twodimension.setLabel("Two Dimension");
    checkbox_twodimension.setBounds(new Rectangle(423, 41, 121, 21));
    checkbox_twodimension.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_twodimension_itemStateChanged(e);
      }
    });
    choice_Attribute.setBounds(new Rectangle(2, 83, 77, 22));
    choice_Attribute.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        choice_Attribute_itemStateChanged(e);
      }
    });
    checkbox_uniforminsertion.setCheckboxGroup(checkboxGroup_Insertion);
    checkbox_uniforminsertion.setFont(new Font("Dialog", 1, 12));
    checkbox_uniforminsertion.setLabel("Insertion same number of objects into Each Class");
    checkbox_uniforminsertion.setState(true);
    checkbox_uniforminsertion.setBounds(new Rectangle(17, 44, 311, 25));
    textArea_FI.setBounds(new Rectangle(3, 183, 411, 109));
    textField_NRDB.setBounds(new Rectangle(305, 92, 225, 21));
    textField_NRDB.addTextListener(new TextListener() {
      public void textValueChanged(TextEvent e) {
        textField_NRDB_textValueChanged(e);
      }
    });
    textField_NRDB.setText("128");
    checkbox_samedata.setCheckboxGroup(checkboxGroup_IndexClassInsertion);
    checkbox_samedata.setFont(new Font("Dialog", 1, 12));
    checkbox_samedata.setLabel("Insertion Same Data");
    checkbox_samedata.setBounds(new Rectangle(108, 141, 215, 25));
    tab_database.setLayout(null);
    list_SQL.setBounds(new Rectangle(380, 4, 331, 121));
    checkbox_uniformdatadistribution.setCheckboxGroup(checkboxGroup_IndexClassInsertion);
    checkbox_uniformdatadistribution.setFont(new Font("Dialog", 1, 12));
    checkbox_uniformdatadistribution.setLabel("Uniform Data Distribution");
    checkbox_uniformdatadistribution.setState(true);
    checkbox_uniformdatadistribution.setBounds(new Rectangle(108, 121, 214, 25));
    textField_TargetClassThreshold.setText("0.9");
    textField_TargetClassThreshold.setBounds(new Rectangle(284, 31, 55, 20));
    textField_TargetClassThreshold.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        textField_TargetClassThreshold_keyReleased(e);
      }
    });
    checkbox_randomdeletion.setBounds(new Rectangle(17, 17, 310, 25));
    checkbox_randomdeletion.setLabel("Deletion from Random Classes");
    checkbox_randomdeletion.setState(true);
    checkbox_randomdeletion.setCheckboxGroup(checkboxGroup_Insertion);
    checkbox_randomdeletion.setFont(new Font("Dialog", 1, 12));
    label_Target_Class.setFont(new Font("Dialog", 1, 12));
    label_Target_Class.setText("Target Class");
    label_Target_Class.setBounds(new Rectangle(2, 30, 80, 22));
    checkbox_FuzzyValue.setCheckboxGroup(checkboxGroup_ValueType);
    checkbox_FuzzyValue.setFont(new Font("Dialog", 1, 12));
    checkbox_FuzzyValue.setLabel("Fuzzy Value");
    checkbox_FuzzyValue.setBounds(new Rectangle(96, 102, 85, 20));
    checkbox_FuzzyValue.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_FuzzyValue_itemStateChanged(e);
      }
    });
    button_Add.setFont(new Font("Dialog", 1, 12));
    button_Add.setLabel("ADD CONDITION");
    button_Add.setBounds(new Rectangle(380, 126, 164, 28));
    button_Add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_Add_actionPerformed(e);
      }
    });
    textField_quantity_deletion.setBounds(new Rectangle(76, 56, 56, 21));
    textField_quantity_deletion.setText("0");
    textField_quantity_deletion.setSelectionStart(2);
    checkbox_indexclassinsertion.setCheckboxGroup(checkboxGroup_Insertion);
    checkbox_indexclassinsertion.setFont(new Font("Dialog", 1, 12));
    checkbox_indexclassinsertion.setLabel("Insertion into Index Class");
    checkbox_indexclassinsertion.setBounds(new Rectangle(17, 71, 312, 25));
    checkbox_indexclassinsertion.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_indexclassinsertion_itemStateChanged(e);
      }
    });
    checkbox_CrispValue.setCheckboxGroup(checkboxGroup_ValueType);
    checkbox_CrispValue.setFont(new Font("Dialog", 1, 12));
    checkbox_CrispValue.setLabel("Crisp Value");
    checkbox_CrispValue.setState(true);
    checkbox_CrispValue.setBounds(new Rectangle(96, 79, 83, 25));
    checkbox_CrispValue.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_CrispValue_itemStateChanged(e);
      }
    });
    checkbox_randomdatadistribution.setCheckboxGroup(checkboxGroup_IndexClassInsertion);
    checkbox_randomdatadistribution.setFont(new Font("Dialog", 1, 12));
    checkbox_randomdatadistribution.setLabel("Random Data Distribution");
    checkbox_randomdatadistribution.setBounds(new Rectangle(108, 102, 215, 25));
    checkbox_onedimension.setCheckboxGroup(checkboxGroup_Dimension);
    checkbox_onedimension.setFont(new Font("Dialog", 1, 12));
    checkbox_onedimension.setLabel("One Dimension");
    checkbox_onedimension.setState(true);
    checkbox_onedimension.setBounds(new Rectangle(305, 40, 116, 25));
    checkbox_onedimension.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_onedimension_itemStateChanged(e);
      }
    });
    label_NRPIN.setFont(new Font("Dialog", 1, 12));
    label_NRPIN.setText("Max Number of Records in Path Instantiation Node");
    label_NRPIN.setBounds(new Rectangle(7, 116, 282, 24));
    tab_insertion.setLayout(null);
    textField_NRPIN.setText("128");
    textField_NRPIN.setBounds(new Rectangle(305, 116, 225, 21));
    textField_NRPIN.addTextListener(new TextListener() {
      public void textValueChanged(TextEvent e) {
        textField_NRPIN_textValueChanged(e);
      }
    });
    choice_FromClass.setBounds(new Rectangle(97, 7, 181, 23));
    choice_FromClass.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        choice_FromClass_itemStateChanged(e);
      }
    });
    label_NRN.setFont(new Font("Dialog", 1, 12));
    label_NRN.setText("Max Number of Records in a Node");
    label_NRN.setBounds(new Rectangle(8, 68, 198, 23));
    choice_TargetAttribute.setBounds(new Rectangle(97, 55, 181, 20));
    choice_TargetAttribute.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        choice_TargetAttribute_itemStateChanged(e);
      }
    });
    textArea_ResultBitStrings.setBounds(new Rectangle(420, 184, 290, 109));
    label_index.setFont(new Font("Dialog", 1, 12));
    label_index.setText("Index Dimension");
    label_index.setBounds(new Rectangle(8, 43, 114, 25));
    textField_CrispValue.setBounds(new Rectangle(268, 81, 105, 21));
    button_startdeletion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_startdeletion_actionPerformed(e);
      }
    });
    button_startdeletion.setBounds(new Rectangle(152, 52, 144, 29));
    button_startdeletion.setActionCommand("Start Deletion !");
    button_startdeletion.setBackground(new Color(92, 203, 223));
    button_startdeletion.setFont(new Font("Dialog", 1, 12));
    button_startdeletion.setLabel("START DELETION !");
    tab_deletion.setLayout(null);
    button_startinsertion.setBackground(new Color(98, 203, 223));
    button_startinsertion.setFont(new Font("Dialog", 1, 12));
    button_startinsertion.setLabel("START INSERTION !");
    button_startinsertion.setBounds(new Rectangle(142, 183, 144, 29));
    button_startinsertion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_startinsertion_actionPerformed(e);
      }
    });
    tab_retrieval.setLayout(null);
    choice_FuzzyValue.setEnabled(false);
    choice_FuzzyValue.setBounds(new Rectangle(207, 105, 113, 21));
    choice_operator.addItem("AND");
    choice_operator.addItem("OR");
    choice_CrispValueOperator.addItem("=");
    choice_CrispValueOperator.addItem("<");
    choice_CrispValueOperator.addItem("<=");
    choice_CrispValueOperator.addItem(">");
    choice_CrispValueOperator.addItem(">=");
    choice_Attribute.addItem("age");
    choice_Attribute.addItem("height");
    checkboxGroup_Insertion.setSelectedCheckbox(checkbox_uniforminsertion);
    tab_deletion.setVisible(false);
    tab_deletion.setBounds(new Rectangle(6, 73, 709, 299));
    tab_database.setBounds(new Rectangle(7, 76, 709, 295));
    tab_database.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        tab_database_keyPressed(e);
      }
    });
    tab_retrieval.setVisible(false);
    tab_retrieval.setBounds(new Rectangle(6, 77, 715, 298));
    tab_insertion.setVisible(false);
    tab_insertion.setBounds(new Rectangle(8, 74, 709, 298));
    button_deletion.setBackground(Color.pink);
    button_deletion.setFont(new Font("Dialog", 1, 12));
    button_deletion.setLabel("Deletion");
    button_deletion.setBounds(new Rectangle(198, 41, 89, 25));
    button_deletion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_deletion_actionPerformed(e);
      }
    });
    button_database.setBounds(new Rectangle(10, 41, 88, 25));
    button_database.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_database_actionPerformed(e);
      }
    });
    button_database.setBackground(Color.orange);
    button_database.setFont(new Font("Dialog", 1, 12));
    button_database.setLabel("Database");
    button_insertion.setBackground(Color.pink);
    button_insertion.setFont(new Font("Dialog", 1, 12));
    button_insertion.setLabel("Insertion");
    button_insertion.setBounds(new Rectangle(104, 41, 88, 25));
    button_insertion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_insertion_actionPerformed(e);
      }
    });
    button_retrieval.setBackground(Color.pink);
    button_retrieval.setFont(new Font("Dialog", 1, 12));
    button_retrieval.setLabel("Retrieval");
    button_retrieval.setBounds(new Rectangle(295, 41, 88, 25));
    button_retrieval.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_retrieval_actionPerformed(e);
      }
    });
    label_FOODIndexDemo.setAlignment(1);
    label_FOODIndexDemo.setBackground(new Color(243, 255, 223));
    label_FOODIndexDemo.setFont(new Font("Dialog", 1, 16));
    label_FOODIndexDemo.setForeground(new Color(50, 150, 50));
    label_FOODIndexDemo.setText("by serdarslan, December 2020");
    label_FOODIndexDemo.setBounds(new Rectangle(472, 5, 256, 29));
    this.setBackground(new Color(210, 197, 223));
    checkbox_printindex.setLabel("Print FOOD Index");
    checkbox_printindex.setBounds(new Rectangle(9, 376, 138, 15));
    checkbox_printindex.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_printindex_itemStateChanged(e);
      }
    });
    button_startcontinousinsertion.setBounds(new Rectangle(142, 216, 215, 29));
    button_startcontinousinsertion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_startcontinousinsertion_actionPerformed(e);
      }
    });
    button_startcontinousinsertion.setLabel("START CONTINOUS INSERTION !");
    button_startcontinousinsertion.setFont(new Font("Dialog", 1, 12));
    button_startcontinousinsertion.setBackground(new Color(98, 203, 223));
    textField_stepnumber_insertion.setText("10");
    textField_stepnumber_insertion.setBounds(new Rectangle(66, 219, 65, 23));
    label_quantity_steps_insertion.setText("Steps     : ");
    label_quantity_steps_insertion.setFont(new Font("Dialog", 1, 12));
    label_quantity_steps_insertion.setBounds(new Rectangle(9, 222, 56, 17));
    checkbox_FOOD_Index.setCheckboxGroup(checkboxGroup_IndexName);
    checkbox_FOOD_Index.setFont(new Font("Dialog", 1, 12));
    checkbox_FOOD_Index.setLabel("FOOD Index");
    checkbox_FOOD_Index.setBounds(new Rectangle(305, 17, 92, 25));
    checkbox_FOOD_Index.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_FOOD_Index_itemStateChanged(e);
      }
    });
    checkbox_Path_Index.setCheckboxGroup(checkboxGroup_IndexName);
    checkbox_Path_Index.setFont(new Font("Dialog", 1, 12));
    checkbox_Path_Index.setLabel("Path Index");
    checkbox_Path_Index.setBounds(new Rectangle(423, 17, 83, 25));
    checkbox_Path_Index.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        checkbox_Path_Index_itemStateChanged(e);
      }
    });
    checkboxGroup_IndexName.setSelectedCheckbox(checkbox_FOOD_Index);
    label1.setFont(new Font("Dialog", 1, 12));
    label1.setText("INDEX TYPE");
    label1.setBounds(new Rectangle(8, 21, 127, 17));
    button_startcontinousdeletion.setBackground(new Color(98, 203, 223));
    button_startcontinousdeletion.setFont(new Font("Dialog", 1, 12));
    button_startcontinousdeletion.setLabel("START CONTINOUS DELETION !");
    button_startcontinousdeletion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_startcontinousdeletion_actionPerformed(e);
      }
    });
    button_startcontinousdeletion.setBounds(new Rectangle(152, 87, 215, 29));
    label_quantity_steps_deletion.setBounds(new Rectangle(18, 90, 56, 17));
    label_quantity_steps_deletion.setFont(new Font("Dialog", 1, 12));
    label_quantity_steps_deletion.setText("Steps     : ");
    textField_stepnumber_deletion.setBounds(new Rectangle(75, 89, 57, 23));
    textField_stepnumber_deletion.setText("10");
    label_FOODIndexDemo1.setBounds(new Rectangle(3, 3, 725, 33));
    label_FOODIndexDemo1.setText("FOOD INDEX DEMO");
    label_FOODIndexDemo1.setForeground(Color.blue);
    label_FOODIndexDemo1.setFont(new Font("Dialog", 1, 20));
    label_FOODIndexDemo1.setBackground(new Color(243, 255, 223));
    label_FOODIndexDemo1.setAlignment(1);
    button_GetStatistics.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_GetStatistics_actionPerformed(e);
      }
    });
    button_GetStatistics.setBounds(new Rectangle(4, 155, 157, 25));
    button_GetStatistics.setLabel("Get Statistics");
    button_GetStatistics.setFont(new Font("Dialog", 1, 12));
    button_GetStatistics.setBackground(new Color(92, 203, 223));
    button_GetStatistics.setActionCommand("Get Statistics");
    button_getdbinfo.setLabel("Get DB Info");
    button_getdbinfo.setBounds(new Rectangle(167, 155, 112, 24));
    button_getdbinfo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_getdbinfo_actionPerformed(e);
      }
    });
    button_StartTest.setLabel("Start Test");
    button_StartTest.setVisible(false);
    button_StartTest.setBounds(new Rectangle(260, 187, 198, 48));
    button_StartTest.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_StartTest_actionPerformed(e);
      }
    });
    list_SQL.add("SELECT TargetClass.*");
    list_SQL.add("FROM FromClass 0.0, TargetClass 0.0");
    list_SQL.add("WHERE");
    choice_FromClass.addItem("Author");
    choice_FromClass.addItem("Native Author");
    choice_FromClass.addItem("Foreign Author");
    choice_FromClass.select(0);
    choice_TargetAttribute.removeAll();
    choice_TargetAttribute.addItem("*");
    choice_TargetAttribute.addItem("oid");
    choice_TargetAttribute.addItem("name_surname");
    choice_TargetAttribute.addItem("age");
    choice_TargetAttribute.addItem("height");
    tab_deletion.add(checkbox_randomdeletion, null);
    tab_deletion.add(label_quantity_deletion, null);
    tab_deletion.add(textField_quantity_deletion, null);
    tab_deletion.add(button_startdeletion, null);
    tab_deletion.add(button_startcontinousdeletion, null);
    tab_deletion.add(button_DeleteAll, null);
    tab_deletion.add(textField_stepnumber_deletion, null);
    tab_deletion.add(label_quantity_steps_deletion, null);
    this.add(label_FOODIndexDemo, null);
    this.add(label_FOODIndexDemo1, null);
    this.add(textArea_Status, null);
    this.add(checkbox_printindex, null);
    this.add(tab_retrieval, null);
    this.add(tab_insertion, null);
    tab_retrieval.add(choice_FromClass, null);
    tab_retrieval.add(choice_TargetClass, null);
    tab_retrieval.add(choice_TargetAttribute, null);
    tab_retrieval.add(textField_FromClassThreshold, null);
    tab_retrieval.add(textField_TargetClassThreshold, null);
    tab_retrieval.add(choice_Attribute, null);
    tab_retrieval.add(checkbox_CrispValue, null);
    tab_retrieval.add(checkbox_FuzzyValue, null);
    tab_retrieval.add(choice_CrispValueOperator, null);
    tab_retrieval.add(choice_FuzzyValue, null);
    tab_retrieval.add(textField_CrispValue, null);
    tab_retrieval.add(textField_FuzzyValueThreshold, null);
    tab_retrieval.add(choice_operator, null);
    tab_retrieval.add(list_SQL, null);
    tab_retrieval.add(label_From_Class, null);
    tab_retrieval.add(label_Target_Class, null);
    tab_retrieval.add(label_Target_Attribute, null);
    tab_retrieval.add(button_Add, null);
    tab_retrieval.add(textArea_FI, null);
    tab_retrieval.add(textArea_ResultBitStrings, null);
    tab_retrieval.add(button_StartRetrieval, null);
    tab_retrieval.add(button_deletecondition, null);
    tab_retrieval.add(button_GetStatistics, null);
    tab_retrieval.add(button_getdbinfo, null);
    this.add(tab_database, null);
    tab_insertion.add(checkbox_uniforminsertion, null);
    tab_insertion.add(checkbox_indexclassinsertion, null);
    tab_insertion.add(checkbox_randomdatadistribution, null);
    tab_insertion.add(checkbox_uniformdatadistribution, null);
    tab_insertion.add(checkbox_samedata, null);
    tab_insertion.add(checkbox_randominsertion, null);
    tab_insertion.add(label_quantity, null);
    tab_insertion.add(textField_quantity_insertion, null);
    tab_insertion.add(button_startinsertion, null);
    tab_insertion.add(button_LoadExample, null);
    tab_insertion.add(button_startcontinousinsertion, null);
    tab_insertion.add(textField_stepnumber_insertion, null);
    this.add(button_database, null);
    this.add(button_insertion, null);
    this.add(button_deletion, null);
    this.add(button_retrieval, null);
    this.add(textArea_IndexTree, null);
    this.add(textArea_DBObjects, null);
    this.add(tab_deletion, null);
    tab_insertion.add(label_quantity_steps_insertion, null);
    tab_database.add(label_NRPIN, null);
    tab_database.add(label_index, null);
    tab_database.add(label_NRN, null);
    tab_database.add(label_NRDB, null);
    tab_database.add(textField_NRPIN, null);
    tab_database.add(textField_NRDB, null);
    tab_database.add(textField_NRN, null);
    tab_database.add(checkbox_onedimension, null);
    tab_database.add(checkbox_twodimension, null);
    tab_database.add(label1, null);
    tab_database.add(checkbox_FOOD_Index, null);
    tab_database.add(checkbox_Path_Index, null);
    tab_database.add(button_StartTest, null);
    choice_FuzzyValue.addItem("{young}");
    choice_FuzzyValue.addItem("{middle-aged}");
    choice_FuzzyValue.addItem("{old}");
    choice_FuzzyValue.addItem("{young,middle-aged}");
    choice_FuzzyValue.addItem("{young,old}");
    choice_FuzzyValue.addItem("{middle-aged,old}");
    choice_FuzzyValue.addItem("{young,middle-aged,old}");
    CheckClasses();
  }
  //Start the applet
  public void start() {
  }
  //Stop the applet
  public void stop() {
  }
  //Destroy the applet
  public void destroy() {
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }
  //Main method
  public static void main(String[] args) {
    foodindex applet = new foodindex();
    applet.isStandalone = true;
    Frame frame;
    frame = new Frame() {
      protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
          System.exit(0);
        }
      }
      public synchronized void setTitle(String title) {
        super.setTitle(title);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      }
    };
    frame.setTitle("Applet Frame");
    frame.add(applet, BorderLayout.CENTER);
    applet.init();
    applet.start();
    frame.setSize(600,730);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.setVisible(true);
  }

  void choice_TargetClass_itemStateChanged(ItemEvent e) {
    choice_TargetAttribute.removeAll();
    choice_TargetAttribute.addItem("*");

    if (choice_TargetClass.getSelectedIndex()==0) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("name");
      choice_TargetAttribute.addItem("city");
      choice_TargetAttribute.addItem("book_capacity");
    }
    else
    if (choice_TargetClass.getSelectedIndex()==1) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("member_id");
      choice_TargetAttribute.addItem("name_surname");
      choice_TargetAttribute.addItem("max_borrowing");
    }
    else
    if ((choice_TargetClass.getSelectedIndex()>=2) && (choice_TargetClass.getSelectedIndex()<=6)){
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("member_id");
      choice_TargetAttribute.addItem("name_surname");
      choice_TargetAttribute.addItem("max_borrowing");
      choice_TargetAttribute.addItem("education");
    }
    else
    if (choice_TargetClass.getSelectedIndex()==7) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("code");
      choice_TargetAttribute.addItem("publisher");
      choice_TargetAttribute.addItem("title");
    }
    else
    if ((choice_TargetClass.getSelectedIndex()>=8) && (choice_TargetClass.getSelectedIndex()<=14)){
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("code");
      choice_TargetAttribute.addItem("publisher");
      choice_TargetAttribute.addItem("title");
      choice_TargetAttribute.addItem("subject");
    }
    else
    if (choice_TargetClass.getSelectedIndex()==15) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("name_surname");
      choice_TargetAttribute.addItem("age");
      choice_TargetAttribute.addItem("height");
    }
    else
    if (choice_TargetClass.getSelectedIndex()==16) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("name_surname");
      choice_TargetAttribute.addItem("age");
      choice_TargetAttribute.addItem("height");
      choice_TargetAttribute.addItem("city");
    }
    else
    if (choice_TargetClass.getSelectedIndex()==17) {
      choice_TargetAttribute.addItem("oid");
      choice_TargetAttribute.addItem("name_surname");
      choice_TargetAttribute.addItem("age");
      choice_TargetAttribute.addItem("height");
      choice_TargetAttribute.addItem("nationality");
    }
    CheckClasses();
  }


  void button_deletecondition_actionPerformed(ActionEvent e) {
    int i=list_SQL.getSelectedIndex();
    if (i>2)
      if (i % 2 != 0) {
        list_SQL.remove(i);
        if (list_SQL.getItemCount()-1>=i) {
          if (i>2)
            list_SQL.remove(i);
        }
        else
        if (i>3)
          list_SQL.remove(i-1);
      }
  }

  void button_StartRetrieval_actionPerformed(ActionEvent e) {
    FOODDB.WCs.ANDConditions.removeAllElements();
    textArea_FI.setText("");
    textArea_ResultBitStrings.setText("");

    int FromClass = choice_FromClass.getSelectedIndex()+15;
    double FromThreholdValue;
    try {
      FromThreholdValue = Double.valueOf(textField_FromClassThreshold.getText()).doubleValue();
    }
    catch(Exception e1){
      return;
    }
    int TargetClass = choice_TargetClass.getSelectedIndex();
    double TargetThreholdValue;
    try {
      TargetThreholdValue = Double.valueOf(textField_TargetClassThreshold.getText()).doubleValue();
    }
    catch(Exception e2){
      return;
    }
    String Target_Attribute = new String((String)choice_TargetAttribute.getSelectedItem());
    Vector ResultObjectsStr = new Vector(0,1);
    ResultObjectsStr.removeAllElements();

    AND_Condition ANDCondition = new AND_Condition();
    for (int i=3;i<list_SQL.getItemCount();i++) {
      String TempStr = (String)list_SQL.getItem(i);
      if (TempStr.substring(TempStr.length()-3).compareTo("AND")==0);
      else
      if (TempStr.substring(TempStr.length()-2).compareTo("OR")==0) {
        FOODDB.WCs.ANDConditions.addElement(ANDCondition);
        ANDCondition = new AND_Condition();
      }
      else {
        Condition_Element CE = ParseCondition(TempStr);
        ANDCondition.ConditionElements.addElement(CE);
      }
    }

    if (ANDCondition.ConditionElements.size()>0)
      FOODDB.WCs.ANDConditions.addElement(ANDCondition);
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    Date TS1 = new Date();
    FOODDB.Retrieve(FromClass,FromThreholdValue,TargetClass,TargetThreholdValue,Target_Attribute,ResultObjectsStr,DiskAccessP,DiskAccessS);
    Date TS2 = new Date();
    Date TS= new Date(TS2.getTime()-TS1.getTime());
    textArea_Status.append("\n"+DiskAccessS.Get()+"\n"+DiskAccessP.Get()+"\n"+TS.getTime());
//    textArea_Status.append("Total Number of Disk Access (when in PM) is : "+DiskAccessP.Get()+"\n");
//    textArea_Status.append("Total Number of Disk Access (when in SM) is : "+DiskAccessS.Get()+"\n");
//    textArea_Status.append("Total Time Taken : "+TS.getTime()+" ms\n");

    for (int i=0;i<ResultObjectsStr.size();i++) {
      String TempStr = (String) ResultObjectsStr.elementAt(i);
      textArea_FI.append(TempStr+"\n");
    }
  }

  void button_LoadExample_actionPerformed(ActionEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    FOODDB.Delete_All(DiskAccessP,DiskAccessS);
    DiskAccessP.Set(0);
    DiskAccessS.Set(0);

    Date TS1 = new Date();
    FOODDB.Example(DiskAccessP,DiskAccessS);
    Date TS2 = new Date();
    Date TS= new Date(TS2.getTime()-TS1.getTime());

    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    textArea_Status.append("\n"+DiskAccessS.Get()+"\n"+DiskAccessP.Get()+"\n"+TS.getTime());
//    textArea_Status.append("Total Number of Disk Access (when in PM) is : "+DiskAccessP.Get()+"\n");
//    textArea_Status.append("Total Number of Disk Access (when in SM) is : "+DiskAccessS.Get()+"\n");
//    textArea_Status.append("Total Time Taken : "+TS.getTime()+" ms\n");
  }

  void textField_NRN_keyTyped(KeyEvent e) {

  }
  void textField_NRN_keyPressed(KeyEvent e) {

  }


  void button_DeleteAll_actionPerformed(ActionEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    Date TS1 = new Date();
    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
    Date TS2 = new Date();
    Date TS= new Date(TS2.getTime()-TS1.getTime());

    textArea_Status.append("\n"+DiskAccessS.Get()+"\n"+DiskAccessP.Get()+"\n"+TS.getTime());
//    textArea_Status.append("Total Number of Disk Access (when in PM) is : "+DiskAccessP.Get()+"\n");
//    textArea_Status.append("Total Number of Disk Access (when in SM) is : "+DiskAccessS.Get()+"\n");
//    textArea_Status.append("Total Time Taken : "+TS.getTime()+" ms\n");
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }
  }

  void textField_FromClassThreshold_keyTyped(KeyEvent e) {

  }
  void textField_FromClassThreshold_keyPressed(KeyEvent e) {
    CheckClasses();
  }
  void textField_FromClassThreshold_keyReleased(KeyEvent e) {
    CheckClasses();
  }

  void checkbox_twodimension_itemStateChanged(ItemEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    if (checkbox_twodimension.getState()) {
      this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
      if (checkbox_printindex.getState()) { // if checked
        this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
        this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
      }
      else {
        textArea_DBObjects.setText("");
        textArea_IndexTree.setText("");
      }

      Database.INDEX_DIMENSION=2;
    }
  }

  void choice_Attribute_itemStateChanged(ItemEvent e) {
    choice_FuzzyValue.removeAll();
    if (((String)choice_Attribute.getSelectedItem()).compareTo("age")==0) {
      choice_FuzzyValue.addItem("{young}");
      choice_FuzzyValue.addItem("{middle-aged}");
      choice_FuzzyValue.addItem("{old}");
      choice_FuzzyValue.addItem("{young,middle-aged}");
      choice_FuzzyValue.addItem("{young,old}");
      choice_FuzzyValue.addItem("{middle-aged,old}");
      choice_FuzzyValue.addItem("{young,middle-aged,old}");
    }
    else {
      choice_FuzzyValue.addItem("{short}");
      choice_FuzzyValue.addItem("{medium}");
      choice_FuzzyValue.addItem("{tall}");
      choice_FuzzyValue.addItem("{short,medium}");
      choice_FuzzyValue.addItem("{short,tall}");
      choice_FuzzyValue.addItem("{medium,tall}");
      choice_FuzzyValue.addItem("{short,medium,tall}");    }
  }


  void textField_NRDB_keyTyped(KeyEvent e) {

  }
  void textField_NRDB_keyPressed(KeyEvent e) {

  }

  void textField_NRDB_keyReleased(KeyEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);

    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET = Integer.parseInt(textField_NRN.getText());
  }

  void textField_TargetClassThreshold_keyTyped(KeyEvent e) {

  }
  void textField_TargetClassThreshold_keyPressed(KeyEvent e) {

  }
  void textField_TargetClassThreshold_keyReleased(KeyEvent e) {
    CheckClasses();
  }

  void checkbox_FuzzyValue_itemStateChanged(ItemEvent e) {
    choice_CrispValueOperator.setEnabled(false);
    textField_CrispValue.setEnabled(false);
    choice_FuzzyValue.setEnabled(true);
    textField_FuzzyValueThreshold.setEnabled(true);
    choice_CrispValueOperator.repaint();
    textField_CrispValue.repaint();
    choice_FuzzyValue.repaint();
    textField_FuzzyValueThreshold.repaint();
  }

  void button_Add_actionPerformed(ActionEvent e) {
    String TempStr = new String();

    TempStr=((String)choice_Attribute.getSelectedItem());

    if (checkbox_CrispValue.getState()) {
      TempStr=TempStr+((String)choice_CrispValueOperator.getSelectedItem());
      TempStr=TempStr+textField_CrispValue.getText();
      choice_CrispValueOperator.select(0);
      textField_CrispValue.setText("");
    }
    else {
      TempStr=TempStr+"="+((String)choice_FuzzyValue.getSelectedItem());
      TempStr=TempStr+" "+textField_FuzzyValueThreshold.getText();
      choice_FuzzyValue.select(0);
      textField_FuzzyValueThreshold.setText("0.0");
    }

    if (list_SQL.getItemCount()==3)
      list_SQL.add("              "+TempStr);
    else {
      list_SQL.add("                  "+(String)choice_operator.getSelectedItem());
      list_SQL.add("              "+TempStr);
    }
  }

  void checkbox_indexclassinsertion_itemStateChanged(ItemEvent e) {

  }

  void checkbox_CrispValue_itemStateChanged(ItemEvent e) {
    choice_CrispValueOperator.setEnabled(true);
    textField_CrispValue.setEnabled(true);
    choice_FuzzyValue.setEnabled(false);
    textField_FuzzyValueThreshold.setEnabled(false);
    choice_CrispValueOperator.repaint();
    textField_CrispValue.repaint();
    choice_FuzzyValue.repaint();
    textField_FuzzyValueThreshold.repaint();
  }

  void checkbox_onedimension_itemStateChanged(ItemEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    if (checkbox_onedimension.getState()) {
      this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
      if (checkbox_printindex.getState()) { // if checked
        this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
        this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
      }
      else {
        textArea_DBObjects.setText("");
        textArea_IndexTree.setText("");
      }

      Database.INDEX_DIMENSION=1;
    }
  }

  void textField_NRPIN_keyTyped(KeyEvent e) {

  }
  void textField_NRPIN_keyPressed(KeyEvent e) {

  }


  void choice_FromClass_itemStateChanged(ItemEvent e) {
    CheckClasses();
  }

  void choice_TargetAttribute_itemStateChanged(ItemEvent e) {
    CheckClasses();
  }

  void button_startdeletion_actionPerformed(ActionEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    Date TS=null,TS1=null,TS2=null;
    int Quantity;
    try {
      Quantity=Integer.parseInt(textField_quantity_deletion.getText());
    }
    catch (Exception ex) {
      System.out.println("Not a number !");
      return;
    }

    if (checkbox_randomdeletion.getState()) {
      TS1 = new Date();
      FOODDB.Delete_RandomDeletion(Integer.parseInt(textField_quantity_deletion.getText()),DiskAccessP,DiskAccessS);
      TS2 = new Date();
      TS = new Date(TS2.getTime()-TS1.getTime());
    }
    else
      System.exit(1);

    textArea_Status.append("\n"+DiskAccessS.Get()+"\n"+DiskAccessP.Get()+"\n"+TS.getTime());
//    textArea_Status.append("Total Number of Disk Access (when in PM) is : "+DiskAccessP.Get()+"\n");
//    textArea_Status.append("Total Number of Disk Access (when in SM) is : "+DiskAccessS.Get()+"\n");
//    textArea_Status.append("Total Time Taken : "+TS.getTime()+" ms\n");
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }
  }

  void button_startinsertion_actionPerformed(ActionEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    int Quantity;
    try {
      Quantity=Integer.parseInt(textField_quantity_insertion.getText());
    }
    catch (Exception ex) {
      System.out.println("Not a number !");
      return;
    }

    Date TS1 = new Date();
    if (checkbox_randominsertion.getState()) {
      if (checkbox_randomdatadistribution.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),0,0,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_uniformdatadistribution.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),0,1,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_samedata.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),0,2,DiskAccessP,DiskAccessS);
      }
    }
    else
    if (checkbox_uniforminsertion.getState()) {
      if (checkbox_randomdatadistribution.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),1,0,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_uniformdatadistribution.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),1,1,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_samedata.getState()) {
        FOODDB.Insert_UniformAndRandomInsertion(Integer.parseInt(textField_quantity_insertion.getText()),1,2,DiskAccessP,DiskAccessS);
      }
    }
    else
    if (checkbox_indexclassinsertion.getState()) {
      if (checkbox_randomdatadistribution.getState()) {
        FOODDB.Insert_IndexClassInsertion(Integer.parseInt(textField_quantity_insertion.getText()),0,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_uniformdatadistribution.getState()) {
        FOODDB.Insert_IndexClassInsertion(Integer.parseInt(textField_quantity_insertion.getText()),1,DiskAccessP,DiskAccessS);
      }
      else
      if (checkbox_samedata.getState()) {
        FOODDB.Insert_IndexClassInsertion(Integer.parseInt(textField_quantity_insertion.getText()),2,DiskAccessP,DiskAccessS);
      }
    }
    Date TS2 = new Date();
    Date TS= new Date(TS2.getTime()-TS1.getTime());
    textArea_Status.append("\n"+DiskAccessS.Get()+"\n"+DiskAccessP.Get()+"\n"+TS.getTime());
//    textArea_Status.append("Total Number of Disk Access (when in PM) is : "+DiskAccessP.Get()+"\n");
//    textArea_Status.append("Total Number of Disk Access (when in SM) is : "+DiskAccessS.Get()+"\n");
//    textArea_Status.append("Total Time Taken : "+TS.getTime()+" ms\n");
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }
  }

  void button_database_actionPerformed(ActionEvent e) {
    tab_insertion.setVisible(false);
    tab_deletion.setVisible(false);
    tab_retrieval.setVisible(false);
    tab_database.setVisible(true);
    button_insertion.setBackground(Color.pink);
    button_deletion.setBackground(Color.pink);
    button_retrieval.setBackground(Color.pink);
    button_database.setBackground(Color.orange);
  }

  void button_insertion_actionPerformed(ActionEvent e) {
    tab_database.setVisible(false);
    tab_deletion.setVisible(false);
    tab_retrieval.setVisible(false);
    tab_insertion.setVisible(true);
    button_database.setBackground(Color.pink);
    button_deletion.setBackground(Color.pink);
    button_retrieval.setBackground(Color.pink);
    button_insertion.setBackground(Color.orange);
  }

  void button_deletion_actionPerformed(ActionEvent e) {
    tab_database.setVisible(false);
    tab_insertion.setVisible(false);
    tab_retrieval.setVisible(false);
    tab_deletion.setVisible(true);
    button_insertion.setBackground(Color.pink);
    button_database.setBackground(Color.pink);
    button_retrieval.setBackground(Color.pink);
    button_deletion.setBackground(Color.orange);
  }

  void button_retrieval_actionPerformed(ActionEvent e) {
    tab_database.setVisible(false);
    tab_insertion.setVisible(false);
    tab_deletion.setVisible(false);
    tab_retrieval.setVisible(true);
    button_insertion.setBackground(Color.pink);
    button_database.setBackground(Color.pink);
    button_deletion.setBackground(Color.pink);
    button_retrieval.setBackground(Color.orange);
  }

  public void CheckClasses () {
    String TempStr = new String("FROM ");
    String TempStr2 = new String("SELECT ");

    if (choice_FromClass.getSelectedIndex()>=0)
      TempStr=TempStr+(String)choice_FromClass.getSelectedItem()+" "+textField_FromClassThreshold.getText();
    else
      TempStr=TempStr+"FromClass "+textField_FromClassThreshold.getText();

    if (choice_TargetClass.getSelectedIndex()>=0) {
      TempStr=TempStr+", "+(String)choice_TargetClass.getSelectedItem()+" "+textField_TargetClassThreshold.getText();
      if (choice_TargetAttribute.getSelectedIndex()>=0)
        TempStr2=TempStr2+(String)choice_TargetClass.getSelectedItem()+"."+(String)choice_TargetAttribute.getSelectedItem();
      else
        TempStr2=TempStr2+(String)choice_TargetClass.getSelectedItem()+".Attribute";
      if (list_SQL.getItemCount()>=2)
        list_SQL.replaceItem(TempStr2,0);
    }
    else {
      TempStr=TempStr+", "+"TargetClass "+textField_TargetClassThreshold.getText();
      if (choice_TargetAttribute.getSelectedIndex()>=0)
        TempStr2=TempStr2+"TargetClass."+(String)choice_TargetAttribute.getSelectedItem();
      else
        TempStr2=TempStr2+"TargetClass.Attribute";
      if (list_SQL.getItemCount()>=2)
        list_SQL.replaceItem(TempStr2,0);
    }

    if (list_SQL.getItemCount()>=2)
      list_SQL.replaceItem(TempStr,1);
  }

  public static Condition_Element ParseCondition(String S) {
    S = S.trim();
    String Attribute,Operator,Field1,Field2;

    for (int i=0;i<S.length();i++) {
      if ((S.charAt(i)=='<') || (S.charAt(i)=='>') || (S.charAt(i)=='=')) {
        if (i==S.length()-1) {
          if (S.charAt(i)=='=') {
            Attribute=S.substring(0,i);
            Operator=S.substring(i,i+1);
            Field1="";
            Condition_Element CE = new Condition_Element(Attribute,"","1.0");
//            Condition_Element CE = new Condition_Element(Attribute,Operator,Field1);
            return(CE);
          }
          else {
            System.out.println("220- Incomplete Condition !");
            System.exit(1);
          }
        }
        else
        if (i==0) {
          System.out.println("210- Incomplete Condition !");
          System.exit(1);
        }
        if (S.charAt(i+1)=='=') {
          Attribute=S.substring(0,i);
          Operator=S.substring(i,i+2);
          Field1=S.substring(i+2);
          Condition_Element CE = new Condition_Element(Attribute,Operator,Field1);
          return(CE);
        }
        else {
          Attribute=S.substring(0,i);
          Operator=S.substring(i,i+1);

          if (S.charAt(i)=='=') {
            for (int j=i+1;j<S.length();j++) {
              if (S.charAt(j)==' ') {
                Field1=S.substring(i+1,j);
                Field2=S.substring(j+1);
                Condition_Element CE = new Condition_Element(Attribute,Field1,Field2);
                return(CE);
              }
            }
            Field1=S.substring(i+1);
            Condition_Element CE = new Condition_Element(Attribute,Operator,Field1);
            return(CE);
          }
          else {
            Field1=S.substring(i+1);
            Condition_Element CE = new Condition_Element(Attribute,Operator,Field1);
            return(CE);
          }
        }
      }
    }
    return(null);
  }

  void checkbox_printindex_itemStateChanged(ItemEvent e) {
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree);
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }
  }

  void button_startcontinousinsertion_actionPerformed(ActionEvent e) {
    for (int count=0;count<Integer.parseInt(textField_stepnumber_insertion.getText());count++) {
      textArea_Status.append("\n"+count);
//      textArea_Status.append("Insertion Count: "+count+"\n");
      button_startinsertion_actionPerformed(null);
    }
  }

  void checkbox_FOOD_Index_itemStateChanged(ItemEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    checkbox_twodimension.setEnabled(true);
    checkbox_twodimension.setState(false);

    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    Database.FOOD_INDEX=1;
    Database.INDEX_DIMENSION=1;
  }

  void checkbox_Path_Index_itemStateChanged(ItemEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    checkbox_twodimension.setEnabled(false);
    checkbox_onedimension.setState(true);

    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    Database.FOOD_INDEX=0;
    Database.INDEX_DIMENSION=1;
  }

  void button_startcontinousdeletion_actionPerformed(ActionEvent e) {
    for (int count=0;count<Integer.parseInt(textField_stepnumber_deletion.getText());count++) {
      textArea_Status.append("\n"+count);
//      textArea_Status.append("Deletion Count: "+count+"\n");
      button_startdeletion_actionPerformed(null);
    }
  }

  void button_GetStatistics_actionPerformed(ActionEvent e) {
    ObjectInt NPI=new ObjectInt();ObjectInt NPI0=new ObjectInt();ObjectInt NPI1=new ObjectInt();
    ObjectInt NPI2=new ObjectInt();ObjectInt NPI3=new ObjectInt();
    int NDKV = Calculate_NDKV();
    Calculate_NPI(NPI,NPI0,NPI1,NPI2,NPI3);
    int ANPI = (int)Math.round(Math.ceil(NPI.Get()*1.0/NDKV));
    int NDB = (int)(Math.round(Math.ceil(NDKV*1.0/Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET))+Math.round(Math.ceil(Database.MAX_NUMBER_OF_BITS_IN_ONE_BIT_STRING*Database.INDEX_DIMENSION*1.0/2)));
    int D = (int)Math.round(Math.ceil(Math.log(NDB)/Math.log(Database.MAX_NUMBER_OF_RECORD_IN_A_NODE)));
    textArea_Status.append("NF = "+Integer.toString(Database.MAX_NUMBER_OF_RECORD_IN_A_NODE)+"\n");
    textArea_Status.append("DBF = "+Integer.toString(Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET)+"\n");
    textArea_Status.append("DNS = "+Integer.toString(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE)+"\n");
    textArea_Status.append("PINS = "+Integer.toString(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE)+"\n");
    textArea_Status.append("ID = "+Integer.toString(Database.INDEX_DIMENSION)+"\n");
    textArea_Status.append("NDKV = "+Integer.toString(NDKV)+"\n");
    textArea_Status.append("ANPI = "+Integer.toString(ANPI)+"\n");
    textArea_Status.append("NDB = "+Integer.toString(NDB)+"\n");
    textArea_Status.append("D = "+Integer.toString(D)+"\n");
    textArea_Status.append("NPI = "+Integer.toString(NPI.Get())+"\n");
    textArea_Status.append("NPI 0 = "+Integer.toString((int)Math.round(Math.ceil(NPI0.Get()*1.0/NDKV)))+"\n");
    textArea_Status.append("NPI 1 = "+Integer.toString((int)Math.round(Math.ceil(NPI1.Get()*1.0/NDKV)))+"\n");
    textArea_Status.append("NPI 2 = "+Integer.toString((int)Math.round(Math.ceil(NPI2.Get()*1.0/NDKV)))+"\n");
    textArea_Status.append("NPI 3 = "+Integer.toString((int)Math.round(Math.ceil(NPI3.Get()*1.0/NDKV)))+"\n");

    int Cost;
    if (ANPI<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) {
      Cost=D+1+2*ANPI;
    }
    else {
      int PINall=(int)Math.round(Math.floor(ANPI/Math.round(Math.ceil(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE*1.0/2))));
      Cost=D+PINall+1+2*ANPI;
    }
    textArea_Status.append("\n");
    textArea_Status.append("Worst Case Analysis\n");
    textArea_Status.append("-----------------------------\n");
    textArea_Status.append("Crisp Exact Match Query : "+Cost+"\n");

    if (ANPI<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) {
      Cost=D-1+NDB*(2+2*ANPI);
    }
    else {
      int PINall=(int)Math.round(Math.floor(ANPI/Math.round(Math.ceil(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE*1.0/2))));
      Cost=D-1+NDB*(1+PINall+1+2*ANPI);
    }
    Cost=Cost*(int)Math.pow(2,Database.INDEX_DIMENSION);
    textArea_Status.append("Crisp Range Query : "+Cost+"\n");

    if (ANPI<Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE) {
      Cost=D-1+NDB*(2+2*ANPI);
    }
    else {
      int PINall=(int)Math.round(Math.floor(ANPI/Math.round(Math.ceil(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE*1.0/2))));
      Cost=D-1+NDB*(1+PINall+1+2*ANPI);
    }
    Cost=Cost*(int)Math.pow(((int)Math.pow(2,3)+3-1),Database.INDEX_DIMENSION);
    textArea_Status.append("Fuzzy Query : "+Cost+"\n");
  }

  int Calculate_NDKV() {
    int NDKV = 0 ;
    int i,j;
    Vector DifferentKeyValues = new Vector(0,1);
    String TempS1 = new String();
    String TempS2 = new String();

    for (i=0;i<FOODDB.Authors.size();i++) {
      Author TempA = (Author) FOODDB.Authors.elementAt(i);

      if (Database.INDEX_DIMENSION==1)
        TempS2 = TempA.Age;
      else
        TempS2 = TempA.Age+" "+TempA.Height;

      for (j=0;j<DifferentKeyValues.size();j++) {
        TempS1 = (String) DifferentKeyValues.elementAt(j);

        if (TempS1.compareTo(TempS2)==0)
          break;
      }

      if (j==DifferentKeyValues.size()) {
        DifferentKeyValues.addElement(TempS2);
        NDKV++;
      }
    }

    return(NDKV);
  }

  void Calculate_NPI(ObjectInt NPI, ObjectInt NPI0, ObjectInt NPI1, ObjectInt NPI2, ObjectInt NPI3) {
    Vector PIs = new Vector(0,1);
    int i,j;
    DataBucket TempDB=FOODDB.FI.FirstDB;

    while (TempDB!=null) {
      for (i=0;i<TempDB.DBRs.size();i++) {
        DataBucketRecord TempDBR = (DataBucketRecord) TempDB.DBRs.elementAt(i);
        TempDBR.PIN.Get_All_PIs(PIs);
      }
      TempDB=TempDB.Next;
    }

    NPI0.Set(0);NPI1.Set(0);NPI2.Set(0);NPI3.Set(0);
    for (i=0;i<PIs.size();i++) {
      Vector PI = (Vector) PIs.elementAt(i);
      if (PI.size()>0)
        NPI3.Set(NPI3.Get()+1);
      if (PI.size()>1)
        NPI2.Set(NPI2.Get()+1);
      if (PI.size()>2)
        NPI1.Set(NPI1.Get()+1);
      if (PI.size()>3)
        NPI0.Set(NPI0.Get()+1);
    }

    NPI.Set(PIs.size());
  }

  void textField_NRN_textValueChanged(TextEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);

    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    if (textField_NRN.getText().compareTo("") != 0) {
      Database.MAX_NUMBER_OF_RECORD_IN_A_NODE = Integer.parseInt(textField_NRN.getText());
      Database.MIN_NUMBER_OF_RECORD_IN_A_NODE = Database.MAX_NUMBER_OF_RECORD_IN_A_NODE/2+Database.MAX_NUMBER_OF_RECORD_IN_A_NODE%2;
    }
    else {
      Database.MAX_NUMBER_OF_RECORD_IN_A_NODE = 128;
      Database.MIN_NUMBER_OF_RECORD_IN_A_NODE = Database.MAX_NUMBER_OF_RECORD_IN_A_NODE/2+Database.MAX_NUMBER_OF_RECORD_IN_A_NODE%2;
    }
  }

  void tab_database_keyPressed(KeyEvent e) {

  }

  void textField_NRDB_textValueChanged(TextEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();
    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);

    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    if (textField_NRDB.getText().compareTo("") != 0) {
      Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET = Integer.parseInt(textField_NRDB.getText());
    }
    else {
      Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET = 128;
    }
  }

  void textField_NRPIN_textValueChanged(TextEvent e) {
    ObjectInt DiskAccessP = new ObjectInt();
    ObjectInt DiskAccessS = new ObjectInt();

    this.FOODDB.Delete_All(DiskAccessP,DiskAccessS);
    if (checkbox_printindex.getState()) { // if checked
      this.FOODDB.PrintDBObjects(textArea_DBObjects); // print the DB Objects
      this.FOODDB.PrintFOODIndexTree(textArea_IndexTree); // print the FOOD Index Tree
    }
    else {
      textArea_DBObjects.setText("");
      textArea_IndexTree.setText("");
    }

    if (textField_NRPIN.getText().compareTo("") != 0)
      Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE = Integer.parseInt(textField_NRPIN.getText());
    else
      Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE = 128;
  }

  void button_getdbinfo_actionPerformed(ActionEvent e) {
    textArea_Status.append("\nIndex Size = "+Database.INDEX_DIMENSION);
    textArea_Status.append("\nFOOD Index? = "+Database.FOOD_INDEX);
    textArea_Status.append("\nNF = "+Database.MAX_NUMBER_OF_RECORD_IN_A_NODE);
    textArea_Status.append("\nDBF = "+Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET);
    textArea_Status.append("\nPIF = "+Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE);
  }

  void button_StartTest_actionPerformed(ActionEvent e) {
    Date DT = new Date();
    textArea_Status.append(DT.toString());

    DoTest(0,1,32,32,32,25000);
    DoTest(0,1,64,64,64,25000);
    DoTest(0,1,128,128,128,25000);
    DoTest(0,1,128,128,128,50000);
    DoTest(0,1,128,128,128,75000);
    DoTest(0,1,128,128,128,100000);
    DoTest(1,1,32,32,32,25000);
    DoTest(1,1,64,64,64,25000);
    DoTest(1,1,128,128,128,25000);
    DoTest(1,1,128,128,128,50000);
    DoTest(1,1,128,128,128,75000);
    DoTest(1,1,128,128,128,100000);
    DoTest(1,2,128,128,128,25000);
    DoTest(1,2,128,128,128,50000);
    DoTest(1,2,128,128,128,75000);
    DoTest(1,2,128,128,128,100000);

    DT = new Date();
    textArea_Status.append("\n"+DT.toString());
  }

  void DoTest(int FOOD_INDEX, int Index_Dimension, int NF, int DBF, int PIF, int N) {
    Database.FOOD_INDEX=FOOD_INDEX;
    Database.INDEX_DIMENSION=Index_Dimension;
    Database.MAX_NUMBER_OF_RECORD_IN_A_NODE=NF;
    Database.MIN_NUMBER_OF_RECORD_IN_A_NODE = Database.MAX_NUMBER_OF_RECORD_IN_A_NODE/2+Database.MAX_NUMBER_OF_RECORD_IN_A_NODE%2;
    Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET=DBF;
    Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE=PIF;

    textArea_Status.append("\n"+Integer.toString(Database.FOOD_INDEX)+"\t"+Integer.toString(Database.INDEX_DIMENSION));
    textArea_Status.append("\t"+Integer.toString(Database.MAX_NUMBER_OF_RECORD_IN_A_NODE)+"\t"+Integer.toString(Database.MAX_NUMBER_OF_RECORD_IN_A_DATA_BUCKET));
    textArea_Status.append("\t"+Integer.toString(Database.MAX_NUMBER_OF_PATH_INSTANTIATIONS_IN_PATH_INSTANTIATION_NODE)+"\t"+Integer.toString(N));

    textField_quantity_insertion.setText(Integer.toString(N));
    button_startinsertion_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age=50");
    button_StartRetrieval_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age>50");
    button_StartRetrieval_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age={young} 0.8");
    button_StartRetrieval_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age=50");
    list_SQL.add("                  "+"AND");
    list_SQL.add("              "+"height=150");
    button_StartRetrieval_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age>50");
    list_SQL.add("                  "+"AND");
    list_SQL.add("              "+"height<150");
    button_StartRetrieval_actionPerformed(null);

    list_SQL.removeAll();
    list_SQL.add("SELECT Author.*");
    list_SQL.add("FROM Author 0.9, Author 0.9");
    list_SQL.add("WHERE");
    list_SQL.add("              "+"age={young} 0.8");
    list_SQL.add("                  "+"AND");
    list_SQL.add("              "+"height={short} 0.8");
    button_StartRetrieval_actionPerformed(null);

    button_DeleteAll_actionPerformed(null);
  }
}