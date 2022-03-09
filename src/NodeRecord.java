/**
 * <p>Title: MMFOOD v.2.1</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: </p>
 * @author serdarslan
 * @version 1.0
 */

public class NodeRecord {
  BitString Key;	// Key Bit String
  Node ChildNode;	// Child Node
  DataBucket ChildDB;	// Childe DataBucket

  NodeRecord() {	// KeyBS: Key BitString to store in the Node Record
    Key = new BitString("");
    ChildNode = null;
    ChildDB=null;
  }

  NodeRecord(BitString KeyBS) {	// KeyBS: Key BitString to store in the Node Record
    Key = new BitString(KeyBS);
    ChildNode = null;
    ChildDB=null;
  }

  NodeRecord(String KeyS) { 	// KeyS: Key Bit String to store in the Node Record
    Key = new BitString(KeyS);
    ChildNode = null;
    ChildDB=null;
  }

  NodeRecord(NodeRecord NR) { 	// KeyS: Key Bit String to store in the Node Record
    Key = new BitString(NR.Key);
    ChildNode = NR.ChildNode;
    ChildDB = NR.ChildDB;
  }

  public void PrintNodeRecord() {
    System.out.println(Key.GetBitString());
  }

  protected void finalize() throws Throwable {
  }

  public static void main(String[] args) {
    NodeRecord NodeRecord1 = new NodeRecord("0110010");
    NodeRecord1.PrintNodeRecord();
  }
}