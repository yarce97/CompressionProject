
package compression;

/**
 *
 * @author Yazel
 */
abstract class HuffTree implements Comparable
{
    int frequency;
    public int getFrequency()
    {
    return frequency;
    }
    public int compareTo(Object obj)
    {
        HuffTree huffTree = (HuffTree)obj;
        if (frequency == huffTree.frequency)
        {
          return (hashCode() - huffTree.hashCode());
        }
        else
        {
            return frequency - huffTree.frequency;
        }
     }
}
class HuffLeaf extends HuffTree
{
    
    private int value;
    
    public HuffLeaf(int value, int frequency)
    {
      this.value = value;
      //Note that frequency is inherited from HuffTree
      this.frequency = frequency;
    }
    
    public int getValue(){
      return value;
    }//end getValue
  
  }//End HuffLeaf class
  class HuffNode extends HuffTree{
  
    private HuffTree left;
    private HuffTree right;
  
    public HuffNode(
               int frequency,HuffTree left,HuffTree right)
               {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
    }
  
    public HuffTree getLeft()
    {
      return left;
    }
  
    public HuffTree getRight()
    {
      return right;
    }
  
  }
