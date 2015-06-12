/*
 * HuffmanTree.java
 *
 * Created on May 21, 2007, 2:16 PM
 */

package compression;
import java.util.*;
/**
 * binary tree for Huffman coding
 * @author Moe, Yazel Arce, Carmelita DeLeon
 */
public class HuffmanTree<T extends Comparable<? super T>>
        extends BinaryTree<HuffmanData<T>>
{
    private final T MARKER = null;
    private final T tempMark = null;
    SortedMap<T, String> codeMap;
    SortedMap<String, T> keyMap;
    BinaryNode node = new BinaryNode();
    BinaryNode node2 = new BinaryNode();
    BinaryTree biTree;
    int valueTemp = 0;
    int value = 0;
    private int leafCount = 0;
    
    /**
     * Creates a new instance of HuffmanTree
     */
    public HuffmanTree() 
    {
        super();
    }
   
    /**
     * Creates a new instance of HuffmanTree
    * from an array of Huffman Data
     * @author Moe
     * @param dataArray n array of Huffman Data
     */
    public HuffmanTree(HuffmanData<T>[] dataArray) 
   {
        // your code here
       
         keyMap = new TreeMap<String, T>();
         codeMap = new TreeMap<T, String>();
         setMaps(getRootNode(), "");
    }
    
    /** 
     * creates two new HuffmanTrees and adds them to the root of this tree
     * 
     * @param left 
     * @param rightt
     */
    private void add(BinaryNode<HuffmanData<T>> left,
            BinaryNode<HuffmanData<T>> right)
    {
         HuffmanTree<T> leftTree = new HuffmanTree<T>();
         leftTree.setRootNode(left); 
         HuffmanTree<T> rightTree = new HuffmanTree<T>();
         rightTree.setRootNode(right);
         setTree(new HuffmanData<T>
                 (MARKER, left.getData().getOccurances()
                 + right.getData().getOccurances()), leftTree, rightTree);
    }
    
     /** 
      * set up the 2 maps
      * @param node
      * @param codeString
     */
     private void setMaps(BinaryNodeInterface<HuffmanData<T>> node,
             String codeString)
     { 
       
              
     }
  
      /*
     * accessor for codeMap
     * @ return codeMap
     */
    public SortedMap<T, String> getCodeMap()
    {
        return codeMap;
    }
    
    /*
     * accessor for keyMap
     * @ return keyMap
     */
    public SortedMap<String, T> getKeyMap()
    {
        return keyMap;
    }

}
