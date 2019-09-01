package SearchAndPrintContentsOfAFile;

import org.openqa.selenium.InvalidArgumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchAndPrintContentsOfAFile {

    // Problem1.Create a method called doesFileExist(String path), which takes the path of the file and tells the user if the file exists at that path or not.
    // Assume all paths are relative to your project structure. If the file does not exist, catch the requisite exception.

    // filePath is relative to project directory structure
    private static String getAbsolutePathForFile(String path){

        if(path==null || path=="")
            throw new InvalidArgumentException("path is either null or empty");

        // Path to current folder will be Path to current folder: /Users/chandramohanboggaramgopinath/IdeaProjects/WeightWatchers
        String pathToCurrentFolder = System.getProperty("user.dir");
        String absoluteFilePath = pathToCurrentFolder + "/" + path;
        System.out.println("Absolute file path: " + absoluteFilePath);
        return absoluteFilePath;
    }

    private static void parseLine(String line){

        if(line == null || line =="")
            throw new InvalidArgumentException("line is either null or empty");

        String[] wordAndMeanings = line.split("-");

        //Printing the word
        if(wordAndMeanings == null || wordAndMeanings.length==0){
            System.out.println("");
            System.out.println("");
        }else if(wordAndMeanings.length == 1){
            System.out.println(wordAndMeanings[0]);
        }else{
            System.out.println(wordAndMeanings[0]);
            String[] meanings = wordAndMeanings[1].split(",");
            for(int i =0; i < meanings.length; i++)
                System.out.println(meanings[i]);
        }
    }

    public static void doesFileExist(String path){

        if(path == null || path =="")
            throw new InvalidArgumentException("path is either null or empty");

        String absoluteFilePath = getAbsolutePathForFile(path);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(absoluteFilePath));

            System.out.println("Following are the contents of the file: ");
            String currLine = bufferedReader.readLine();
            while (currLine != null) {
                parseLine(currLine);
                currLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {

            System.out.println("Following file path does not exist: " + absoluteFilePath );
            e.printStackTrace();

        } catch(Exception e){

            e.printStackTrace();
        }
    }

    // Simple and alternative way to find a file given filepath relative to project directory structure.
    public static boolean doesFilePathExist(String path){

        if(path == null || path =="")
            throw new InvalidArgumentException("path is either null or empty");

        String absoluteFilePath = getAbsolutePathForFile(path);
        File file = new File(absoluteFilePath);
        if(file.exists() && !file.isDirectory()){
            return true;
        }

        return false;
    }

    public static void main(String[] args){

        SearchAndPrintContentsOfAFile.doesFileExist("CodingChallenge/src/SearchAndPrintContentsOfAFile/input.txt");
    }
}
