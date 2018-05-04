package browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils
{
    String fileName;
    String path;

    public static void saveFileContents(File f, ArrayList<String> ar)
    {
        FileWriter writer = null;
        try
        {
            if(f.getName().equals("default.web"))
            {
                writer = new FileWriter(f, false);
            }
            else
            {
                writer = new FileWriter(f, true);
            }
            for (String t : ar)
            {
                writer.write(t + "\n");
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException f1)
            {
                f1.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getFileContentsAsArrayList(File f)
    {
        ArrayList<String> myList=new ArrayList();
        try
        {
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine())
            {
                myList.add(scan.nextLine());
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return myList;
    }

    public static Boolean fileExists(File f)
    {
        if(f.exists()&&f.isFile())
        {
            return true;
        }
        else
            return false;
    }

    public static Boolean fileExists(String s)
    {
        Path path = Paths.get(s);

        if(Files.exists(path)) { return true;}
        else return false;
    }


    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
