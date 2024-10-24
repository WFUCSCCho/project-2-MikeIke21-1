/**
@file: proj2.java
@description: This class takes data from my input file and inserts it into arraylist as the class I created in player.java
 I made two arraytlist with a sorted and shuffled version of my data.
 after that AI make 4 BST trees BST trees for unsorted and sorted arraylist and AVL trees for unsorted and sorted arraylist
 here is where a I can change my numNodes before the inserting functions to change the duration I have the loop run for the insert method
 I call my insert methods for all four trees first to make the trees, I then use the search method later.
 While using all the search methods I calculate the runtimes and write them out to my file with the filewriter method I have at the bottom of the code
@author: Michael Iaccarino
@date: october 23, 2024
 **/


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.random.RandomGenerator;

//PUT OUTPUT INTO FILE
public class Proj2{



    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        ArrayList dataArrList = new ArrayList();
        ArrayList<player> playerArrList = new ArrayList();


        //adding to the Arraylist
        while (inputFileNameScanner.hasNextLine()) {
            dataArrList.add(inputFileNameScanner.nextLine());
        }
        //making arraylist data player data

        String playerStats;
        String[] parsedStats;
        for (int i = 0; i < numLines-1; i++) {
            playerStats = (String) dataArrList.get(i);
            parsedStats = playerStats.split(",");
            if (parsedStats.length != 14){
                //System.out.println(playerStats.length);
            }
            //removes the first line
            else if (parsedStats[0].equals("overall_pick")){

            }
            else {
                playerArrList.add(new player(Integer.parseInt(parsedStats[0]),Integer.parseInt(parsedStats[1]),parsedStats[2],parsedStats[3],parsedStats[4],Integer.parseInt(parsedStats[6]),
                        Integer.parseInt(parsedStats[7]),Double.parseDouble(parsedStats[8]),Double.parseDouble(parsedStats[9]),Double.parseDouble(parsedStats[10]),Double.parseDouble(parsedStats[11]),Double.parseDouble(parsedStats[12]),Double.parseDouble(parsedStats[13])));
            }


        }



        //sorted arraylist
        ArrayList sortedArrList = new ArrayList();
        sortedArrList.addAll(playerArrList);
        Collections.sort(sortedArrList);

        //random arraylist
        ArrayList randomArrList = new ArrayList();
        randomArrList.addAll(playerArrList);
        Collections.sort(randomArrList);


        //sanity check
        //System.out.println(randomArrList);
        //outputs player name | win40 stat| 
        //System.out.println(playerArrList);
        //System.out.println(sortedArrList);


        BST unsortedBst = new BST<>();
        BST sortedBst = new BST<>();
        AvlTree unsortedAVL = new AvlTree();
        AvlTree sortedAVL = new AvlTree();

        //numNodes counter, this is what I changed to get the data of runtimes for different amounts of insertions
        //did not use numLines because when I take in the file I delete some lines for incomplete data
        int numNodes = 512;


        //avl vs BST runtime insertion unsorted data
        //USING full set (may have to test runtimes with smaller node counts for graphing purposes)

        long startInsertBSTUnsort = System.nanoTime();
        for (int i = 0; i < numNodes-1; i++) {
            unsortedBst.insert((Comparable) randomArrList.get(i));
        }
        long endTInsertBSTUnsort = System.nanoTime();

        long startInsertAVLUnsort = System.nanoTime();
        for (int i = 0; i < numNodes-1; i++) {
            unsortedAVL.insert((Comparable) randomArrList.get(i));
        }
        long endTInsertAVLUnsort = System.nanoTime();

        //divide by 10^9 to convert to seconds
        long avlTimeInsertUnsorted = (endTInsertAVLUnsort - startInsertAVLUnsort);
        long bstTimeInsertUnsorted = (endTInsertBSTUnsort - startInsertBSTUnsort);

        writeTofile("_____________RUNTIMES_____________","./result.txt");
        writeTofile("NODES: " + numNodes, "./result.txt");
        writeTofile("BST insert time (unsorted): " + bstTimeInsertUnsorted, "./result.txt");
        writeTofile("AVL insert time (unsorted): " + avlTimeInsertUnsorted, "./result.txt");
        writeTofile("----------------------------------", "./result.txt");


        //avl vs BST runtime sorted data

        long startInsertBSTSort = System.nanoTime();
        for (int i = 0; i < numNodes-1; i++) {
            sortedBst.insert((Comparable) randomArrList.get(i));
        }
        long endTInsertBSTSort = System.nanoTime();

        long startInsertAVLSort = System.nanoTime();
        for (int i = 0; i < numNodes-1; i++) {
            sortedAVL.insert((Comparable) randomArrList.get(i));
        }
        long endTInsertAVLSort = System.nanoTime();

        long avlTimeInsertSorted = endTInsertAVLSort - startInsertAVLSort;
        long bstTimeInsertSorted = endTInsertBSTSort - startInsertBSTSort;

        writeTofile("BST insert time (Sorted): " + bstTimeInsertSorted, "./result.txt");
        writeTofile("AVL insert time (Sorted): " + avlTimeInsertSorted, "./result.txt");
        writeTofile("----------------------------------", "./result.txt");

        //Search times (I search for a random item index each time, but the same index for all 4 operations (unsorted,sorted + avl,bst combinations)
        Random random = new Random();
        //can use either arr list for the size they are both the same
        int searchNum = random.nextInt(numNodes-1);

        //unsorted BST
        long startSearchBSTUnsort = System.nanoTime();
            unsortedBst.find((Comparable) randomArrList.get(searchNum));
        long endSearchBSTUnsort = System.nanoTime();
        //unsorted AVL
        long startSearchAVLUnsort = System.nanoTime();
            unsortedAVL.contains((Comparable) randomArrList.get(searchNum));
        long endSearchAVLUnsort = System.nanoTime();

        long avlSearchTimeUnsorted = endSearchAVLUnsort - startSearchAVLUnsort;
        long bstSearchTimeUnsorted = endSearchBSTUnsort - startSearchBSTUnsort;

        writeTofile("BST search time (unsorted): " + bstSearchTimeUnsorted, "./result.txt");
        writeTofile("AVL search time (unsorted): " + avlSearchTimeUnsorted, "./result.txt");
        writeTofile("----------------------------------", "./result.txt");


        //search SORTED
        long startSearchBSTSort = System.nanoTime();
        sortedBst.find((Comparable) randomArrList.get(searchNum));
        long endSearchBSTSort = System.nanoTime();

        long startSearchAVLSort = System.nanoTime();
        sortedAVL.contains((Comparable) randomArrList.get(searchNum));
        long endSearchAVLSort = System.nanoTime();

        long avlSearchTimeSorted = endSearchAVLSort - startSearchAVLSort;
        long bstSearchTimeSorted = endSearchBSTSort - startSearchBSTSort;

        writeTofile("BST search time (Sorted): " + bstSearchTimeSorted,"./result.txt");
        writeTofile("AVL search time (Sorted): " + avlSearchTimeSorted,"./result.txt");
        writeTofile("----------------------------------", "./result.txt");


        //running rates
        writeTofile("_____________RUNRATES_____________", "./result.txt");
        writeTofile("NUMBER OF NODES: " + numNodes, "./result.txt");

        //insertion operations
        long bstInsertUnsortedPerNode = bstTimeInsertUnsorted/numNodes;
        long avlInsertUnsortedPerNode = avlTimeInsertUnsorted/numNodes;
        long bstInsertSortedPerNode = bstTimeInsertSorted/numNodes;
        long avlInsertSortedPerNode = avlTimeInsertSorted/numNodes;

        long bstSearchUnsortedPerNode = bstSearchTimeUnsorted/numNodes;
        long avlSearchUnsortedPerNode = avlSearchTimeUnsorted/numNodes;
        long bstSearchSortedPerNode = bstSearchTimeSorted/numNodes;
        long avlSearchSortedPerNode = avlSearchTimeSorted/numNodes;

        writeTofile("BST insert runrate (unsorted)" + bstInsertUnsortedPerNode, "./result.txt");
        writeTofile("AVL insert runrate (unsorted)" + avlInsertUnsortedPerNode, "./result.txt");
        writeTofile("BST insert runrate (sorted)" + bstInsertSortedPerNode, "./result.txt");
        writeTofile("AVL insert runrate (sorted)" + avlInsertSortedPerNode, "./result.txt");
        writeTofile("BST search runrate (unsorted)" + bstSearchUnsortedPerNode,  "./result.txt");
        writeTofile("AVL search runrate (unsorted)" + avlSearchUnsortedPerNode,  "./result.txt");
        writeTofile("BST search runrate (sorted)" + bstSearchSortedPerNode,  "./result.txt");
        writeTofile("AVL search runrate (sorted)" + avlSearchSortedPerNode,  "./result.txt");




	// FINISH ME
        //takes in dataset store into a sorted and unsorted version
        // 1) read into arraylist dataset 2) 2 copies sorted and unsorted
        //3) BST insertion 4) bst search 5)time of each one



    }

    //fileWriterMethod
    private static void writeTofile(String content, String filePath) throws IOException {
        FileWriter file  = new FileWriter(filePath, true);
        file.write(content + System.lineSeparator());
        file.close();
    }


}
