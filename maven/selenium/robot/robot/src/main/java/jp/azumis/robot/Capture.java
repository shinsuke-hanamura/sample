package jp.azumis.robot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;


public class Capture {

    private Robot robot;
    private Dimension screenSize;

    public Capture() {
        try {
            this.robot = new Robot();
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    private String createFilePath(Calendar dateTime) {
        
        String dateTimeString = DateTimeManager.toRFC3339String(dateTime);
        String filePath = "log/screenshot_" + dateTimeString + ".png";
        return filePath;
        
    }
    

    
    public void screenShot() {
        this.screenShot(null);

    }
    public void screenShot(Calendar dateTime) {

        BufferedImage image = this.robot.createScreenCapture(
            new Rectangle(0, 0, this.screenSize.width, this.screenSize.height));
        try {
            
            ImageIO.write(image, "PNG", new File(createFilePath(dateTime)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
