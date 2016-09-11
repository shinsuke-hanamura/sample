package jp.azumis.robot;

import java.util.Map;
import java.util.TreeMap;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalMouseListener implements NativeMouseInputListener {

    private static Logger logger = LoggerFactory.getLogger(GlobalMouseListener.class);

    private static int[] PRESERVED_MOUSE_BUTTON = {
        NativeMouseEvent.BUTTON1,
        NativeMouseEvent.BUTTON2,
    };
    
    /**
     * 取得間隔(ミリ秒)
     */
    private static final long LISTEN_INTERVAL = 100L;
    
    private long lastListenTime = 0;

    private Map<Integer, Boolean> mouseButtonMap;

    public GlobalMouseListener() {
        this.mouseButtonMap = new TreeMap();
        for (int k : PRESERVED_MOUSE_BUTTON) {
            this.mouseButtonMap.put(k, false);
        }
        this.lastListenTime = System.currentTimeMillis();
    }


    public void nativeMouseClicked(NativeMouseEvent e) {
        logger.debug("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        int buttonCode = this.getPreservedMouseButtonCode(e);
        if (buttonCode > 0) {
            if (!this.mouseButtonMap.get(buttonCode)) logger.debug("Mouse Pressed: " + e.getButton());
            this.mouseButtonMap.put(buttonCode, true);
        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        int buttonCode = this.getPreservedMouseButtonCode(e);
        if (buttonCode > 0) {
            if (!this.mouseButtonMap.get(buttonCode)) logger.debug("Mouse Released: " + e.getButton());
            this.mouseButtonMap.put(buttonCode, true);
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        long thisTime = System.currentTimeMillis();
        if (thisTime - this.lastListenTime >= LISTEN_INTERVAL) {
            logger.debug("Mouse Moved: " + e.getX() + ", " + e.getY());
            this.lastListenTime = thisTime;
        }
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        logger.debug("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
    
    /**
     * 予約済みのマウスボタンコードのみ取得し、その他は0を返す
     * @return
     */
    private int getPreservedMouseButtonCode(NativeMouseEvent e) {
        int buttonCode = e.getButton();
        
        for (int k : PRESERVED_MOUSE_BUTTON) {
            if (k == buttonCode) return k;
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            logger.error("There was a problem registering the native hook.");
            logger.error(ex.getMessage());

            System.exit(1);
        }

        // Construct the example object.
        GlobalMouseListener example = new GlobalMouseListener();

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
    }
}