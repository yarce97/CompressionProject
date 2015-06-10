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
     * adds 2 new elements to this tree<br>
     *  smaller on the left
     * @author Carmelita DeLeon
     * @param element1
     * @param element2
     */
    private void firstAdd(HuffmanData<T> element1, HuffmanData<T> element2)
    {
        

    }
    
   /** 
     * add a single element to the tree
     *  smaller on the left
     * @author Yazel Arce
     * @param element1
     */
     private void add(HuffmanData<T> element1)
     {
         Queue<HuffmanData<T>> element = new LinkedList<>();
         BinaryNode tempBi = new BinaryNode();
         element.add(element1);
         T mark = (T) element.peek();
         valueTemp += element.element().getOccurances();         
         if (node.getData() == null)
         {
             tempMark = (T) element.peek();  
             HuffmanData<T> temp = new HuffmanData<T>(MARKER, 
                     element1.getOccurances());
             node.setData(temp);
             tempBi.setData(tempMark);
             node.setLeftChild(tempBi);
         }
         else if (node.getData() != null)
         {
             if (node.hasLeftChild() && !node.hasRightChild())
             {
                 if (tempMark.compareTo(mark) >= 0)
                 {
                     tempBi.setData(mark);
                     HuffmanData temp2 = new HuffmanData(MARKER, valueTemp);
                     node.setData(temp2);
                     node.setRightChild(node.getLeftChild());
                     node.setLeftChild(tempBi);
                 }
                 else if (tempMark.compareTo(mark) < 0)
                 {
                     tempBi.setData(mark);
                     HuffmanData temp2 = new HuffmanData(MARKER, valueTemp);
                     node.setData(temp2);
                     node.setRightChild(tempBi);   
                 }
             }
             else if (node.hasLeftChild() && node.hasRightChild())
             {
                 value += element1.getOccurances();
                 if (node2.getData() == null)
                 {
                     tempMark = (T) element.peek();
                     HuffmanData<T> temp = new HuffmanData<T>(MARKER, 
                             value);
                     node2.setData(temp);
                     tempBi.setData(mark);
                     node2.setLeftChild(tempBi);
                 }
                 else if (node2.getData() != null)
                 {
                     if (node2.hasLeftChild() && !node2.hasRightChild())
                     {
                         if (tempMark.compareTo(mark) >= 0)
                         {
                             tempBi.setData(mark);
                             HuffmanData temp2 = new HuffmanData(MARKER, value);
                             node2.setData(temp2);
                             node2.setRightChild(node2.getLeftChild());
                             node2.setLeftChild(tempBi);
                         }
                         else if (tempMark.compareTo(mark) < 0)
                         {
                             tempBi.setData(mark);
                             HuffmanData temp2 = new HuffmanData(MARKER, value);
                             node2.setData(temp2);
                             node2.setRightChild(tempBi);   
                         }
                     }    
                     else
                     {
                         HuffmanTree huff = new HuffmanTree();
                         huff.add(node, node2); 
                     }
                 }
             }
         }              
         element.remove();
         leafCount++;
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
