package Bubu.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaching on 2016/5/17.
 */
public class FileUtility {
    public static String[] getLines(String fileName[]) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        for(int i=0;i<fileName.length;i++) {
            FileReader fr = new FileReader(fileName[i]);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                lines.add(br.readLine());
            }
            fr.close();
            br.close();
        }

        return lines.toArray(new String[lines.size()]);
    }

    public static String[] getFileName(String folderName) {

        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> lines = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                lines.add(file.getAbsolutePath());
            }
        }
        return lines.toArray(new String[lines.size()]);

    }
}
