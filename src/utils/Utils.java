package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by Thaotonto on 3/29/2017.
 */
public class Utils {

    public static Image loadImageFromRes(String url) {
        try {
            Image image = ImageIO.read(new File("resources/" + url));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector<String> loadFile(String url) {
        try {
            Vector<String> fileContaint = new Vector<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/" + url));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContaint.add(line);
            }
            return fileContaint;
        } catch (Exception e) {
            if (e instanceof IOException || e instanceof FileNotFoundException) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


