package sample;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * Created by s-hanamura on 2016/11/08.
 */
public class ReadCSVTest extends TestCase {

    @Test
    public void testRead() {
        String csvFilePath = "data/map.csv";
        List<List<String>> read = ReadCSV.read(csvFilePath);
    }
}
