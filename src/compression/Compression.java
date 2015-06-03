/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compression;


/**
 *
 * @author 955311177
 */
public class Compression 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Huffman huff = new Huffman();
        String fileName = "11.txt";
        if(args[0].equals("-d") && args[1].equals(fileName))
        {
           huff.decode(fileName); 
        }
        else
        {
            huff.encode(fileName);
        }
    }
}
