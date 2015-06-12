/*
 * Huffman.java
 *
 * Created on May 21, 2007, 1:01 PM
 */

package compression;


import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 *
 * @author Jin Chang & pbladek
 */
public class Huffman
{  
    public static final int CHARMAX = 128;
    public static final byte CHARBITS = 7;
    public static final short CHARBITMAX = 128;
  //  private HuffmanTree<Character> theTree;
    private HuffmanChar charCount;
    private HuffmanData data;
    private byte[] byteArray;
    private String hufFile;
    private String codFile;
    private int compression;
    private File File;
    private Map mapCharCount;
    private Map sortedMap;
    protected ArrayList<HuffmanChar> charCountArray;
    char[] readChar; 
    byte[] saveDataArray;
    private File file;
    
    
    /**
     * Creates a new instance of Main
     */
    public Huffman() 
    {
        byteArray = new byte[CHARBITMAX];
        mapCharCount = new HashMap<>();
        sortedMap = new LinkedHashMap<>();
        charCountArray = new ArrayList<>();
    }
    
    /**
     * main
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
//----------------------------------------------------
// used for debugging encoding
//----------------------------------------------------
//        args = new String[1];
//        args[0] = "alice.txt";
//----------------------------------------------------
// used for debugging encoding
//----------------------------------------------------
//        args = new String[2];
//        args[0] = "-d";
//        args[1] = "alice.txt";  
//----------------------------------------------------        
        boolean decode = false;
        boolean fileFound = false;
        String textFileName = "";
        Huffman coder = new Huffman();
        if(args.length > 0)
        {
            if(args[0].substring(0,2).toLowerCase().equals("-d"))
            {
                decode = true;
                if(args.length > 1)
                {   
                    textFileName = args[1];
                }
                else
                {
                    textFileName = JOptionPane.showInputDialog("Please enter "
                            + "the file you wish to compress: ");
                    coder.isFileFound(textFileName);
                    if(fileFound)
                    {
                       textFileName = args[1]; 
                    }
                    else
                    {
                        textFileName = JOptionPane.showInputDialog("Please "
                                + "enter the file you wish to compress: ");
                    }
                }
            }
            else
                textFileName = args[0];
        }
        if(decode)
            coder.decode(textFileName);
        else
            coder.encode(textFileName);
            JOptionPane.showMessageDialog(null,
                     coder.hufFile + ":" + coder.compression + "% compression", 
                     "File Information", INFORMATION_MESSAGE, null);
    } 

    /*
     * encode
     * @param fileName the file to encode
     */
    public void encode(String fileName)
    {
        String text = "";
        try
        {
            BufferedReader readMe = new BufferedReader
                    (new FileReader(fileName));
            while((text = readMe.readLine()) != null)
            {
                String sentence = text + "\n";
                readChar = sentence.toCharArray();
                for(int i = 0; i < readChar.length; i++)
                {
                    char c = readChar[i];
                    countChars((byte)c);
                }
            }
            readMe.close();
            readChar = null;
        }
        catch(FileNotFoundException e)
        {
            System.exit(0);
        }
        catch(IOException e)
        {   
        }
        addCharAndCount();
        //Iterate through the map and add to array list
        Iterator iterateKey = sortedMap.keySet().iterator();
        Iterator iterateValues = sortedMap.values().iterator();
        while(iterateKey.hasNext() && iterateValues.hasNext())
        {
            char key = (char) iterateKey.next();
            int value = (int) iterateValues.next();
            charCount = new HuffmanChar(key, value);
            charCountArray.add(charCount);
        }
        sortedMap.clear();
//        theTree = new HuffmanTree(charCountArray);
//        writeEncodedFile(byteArray, fileName);
//        writeKeyFile(fileName);
    } 
 
    /*
     * decode
     * @param inFileName the file to decode
     */   
    public void decode(String inFileName)
    { 
        String text = "";
        try
        {
            BufferedReader readMe = new BufferedReader
                    (new FileReader(inFileName));
            while((text = readMe.readLine()) != null)
            {
                saveDataArray = text.getBytes();
                for(int i = 0; i < saveDataArray.length; i++)
                {
                    byte c = saveDataArray[i];
                    countChars((byte)c);
                }
            }   
            readMe.close();
        }
        catch(FileNotFoundException e)
        {
            System.exit(0);
        }
        catch(IOException e)
        {
        }
        addCharAndCount();
        charCount = new HuffmanChar(byteArray);
        charCountArray.add(charCount);
        byteArray = null;
    //    theTree = new HuffmanTree(charCountArray);
        //open .huf file
        try
        {
            writeKeyFile(inFileName);
            BufferedReader readMe = new BufferedReader
                    (new FileReader(hufFile));
            while((text = readMe.readLine()) != null)
            {
                readChar = text.toCharArray();
            }
            readMe.close();
        }
        catch(FileNotFoundException e)
        {
            System.exit(0);
        }
        catch(IOException e)
        {
        }
    }
      
    /**
     * writeEncodedFile
     * @param bytes bytes for file
     * @param fileName file input
     * @throws FileNotFoundException if file cannot be found
     */ 
    public void writeEncodedFile(byte[] bytes, String fileName)
    {
        //write first file(array byte)
        writeKeyFile(fileName);
        try
        {
            PrintWriter write = new PrintWriter(new FileOutputStream(
                        file));  
            for(int i = 0; i < bytes.length; i++)
            {
                write.println(bytes[i]);
            }
            write.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        //write second file
        writeSecondFile(fileName);
    }
   
    /**
     * writeKeyFile
     * @param fileName the name of the file with extension .huf to write to
     */
    public void writeKeyFile(String fileName)
    {
        String separate = File.separator;
        String editFileName;
        // Remove the path upto the filename.
        int lastSeparatorIndex = fileName.lastIndexOf(separate);
        if (lastSeparatorIndex == -1) 
        {
            editFileName = fileName;
        } 
        else 
        {
            editFileName = fileName.substring(lastSeparatorIndex + 1);
        }
        // Remove the extension.
        int extensionIndex = editFileName.lastIndexOf(".");
        hufFile = editFileName.substring(0, extensionIndex);
        hufFile += ".huf";
    }
    /**
     * writeSecondFile
     * @param fileName the name of the file with extension .cod to write to
     */
    public void writeSecondFile(String fileName)
    {
        String separate = File.separator;
        String editFileName;
        // Remove the path upto the filename.
        int lastSeparatorIndex = fileName.lastIndexOf(separate);
        if (lastSeparatorIndex == -1) 
        {
            editFileName = fileName;
        } 
        else 
        {
            editFileName = fileName.substring(lastSeparatorIndex + 1);
        }
        // Remove the extension.
        int extensionIndex = editFileName.lastIndexOf(".");
        codFile = editFileName.substring(0, extensionIndex);
        codFile += ".cod";
    }
    /**
     * count the number characters
     * @param character the character
     */
    private void countChars(byte character)
    {
        if(byteArray.length > 0)
        {
            for(int i = 0; i < byteArray.length; i++)
            {
                if(i == character)
                {
                   byteArray[i] += 1; 
                   break;
                }
            } 
        }
    }
    /**
     * adds the Characters and occurences into a map then sorts that map and
     * recreates a new sorted map.
     */
    private void addCharAndCount()
    {
        for(int i = 0; i < byteArray.length; i++)
        {
            byte element = byteArray[i];
            if(element > 0)
            {
                char key = (char)i;
                int count = element;
                mapCharCount.put(key,count);
            }
        }
        byteArray = null;
        sortedMap.putAll(sortMap(mapCharCount));
        mapCharCount.clear();
    }
    /**
     * Sorts the Map from lowest to highest and returns the sorted map.
     * @param <Character> ASCII characters
     * @param <Integer> character occurences
     * @param unsortedmap the unsorted map
     * @return sortedMap 
     */
    public static <Character, Integer extends Comparable< ? super Integer>>
            Map<Character, Integer>
    sortMap(final Map <Character, Integer> unsortedmap)
    {
        List<Map.Entry<Character, Integer>> sortedList =
            new ArrayList<>(unsortedmap.size());  
        Map<Character, Integer> sortedMap = new LinkedHashMap
                <Character, Integer>();
        sortedList.addAll(unsortedmap.entrySet());

        Collections.sort(sortedList,
                         new Comparator<Map.Entry<Character, Integer>>()
        {
            @Override
            public int compare(
                   final Map.Entry<Character, Integer> element1,
                   final Map.Entry<Character, Integer> element2)
            {
                return element1.getValue().compareTo(element2.getValue());
            }
        }); 
        //add sorted list to new map
        for(Map.Entry<Character, Integer> entry : sortedList)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        } 
        return sortedMap;
    }  
    /**
     * finds whether the file exists or can be read
     * @param file the file name
     * @return true if the file is found or false if not found
     */
    private boolean isFileFound(String file)
    {
        boolean found = false;
        File fileIn = new File(file);
        if(fileIn.canRead() || fileIn.exists())
        {
            found = true;
        }
        return found;
    }
}
