package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s-hanamura on 2016/11/08.
 */
public class ReadCSV {

    public static List<List<String>> read(String csvFilePath) {
        try {
            List<List<String>> retData = new ArrayList<List<String>>();

            File file = new File(csvFilePath);
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input, "UTF-8");
            BufferedReader buffer = new BufferedReader(stream);

            String line;

            while ((line = buffer.readLine()) != null) {
                List<String> eachLine = new ArrayList<String>();

                byte[] b = line.getBytes();
                line = new String(b, "UTF-8");
                String[] columns = line.split(",",-1);

                for (int j = 0; j < columns.length; j++) {
                    eachLine.add(columns[j]);
                    System.out.println(j + " : " + columns[j]);
                }
                retData.add(eachLine);
                System.out.println("");

            }

            input.close();
            stream.close();
            buffer.close();
            return retData;

        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
