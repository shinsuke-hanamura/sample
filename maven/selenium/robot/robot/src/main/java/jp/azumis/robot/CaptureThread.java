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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaptureThread extends Thread{

    private static final long CAPTURE_INTERVAL = 100;

    private static Logger logger = LoggerFactory.getLogger(CaptureThread.class);

    private Robot robot;
    private Dimension screenSize;
    private String name;

    private long lastCaptureTime;

    public CaptureThread(String name) {
        this.name = name;
        try {
            this.robot = new Robot();
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }


    public void run() {
        while (true) {
            long thisTime = System.currentTimeMillis();
            if (thisTime - this.lastCaptureTime >= CAPTURE_INTERVAL) {
                this.lastCaptureTime = thisTime;
                Calendar capTime = Calendar.getInstance();
                logger.debug("screenShot at : " + DateTimeManager.toRFC3339String(capTime));
                screenShot(capTime);
            } else {
                try {
                    sleep(CAPTURE_INTERVAL / 10);
                } catch (InterruptedException e) {}
            }

        }
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
