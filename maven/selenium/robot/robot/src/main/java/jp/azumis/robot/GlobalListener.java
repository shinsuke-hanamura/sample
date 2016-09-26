package jp.azumis.robot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalListener {
    
    private static Logger logger = LoggerFactory.getLogger(GlobalListener.class);
    
    public static void main(String[] args) {
        
        // 画面キャプチャー
        Capture cap = new Capture();
        
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            logger.error("There was a problem registering the native hook.");
            logger.error(ex.getMessage());

            System.exit(1);
        }

        // Construct and Add the GlobalMouseListener object.
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());        
        
        // Construct the GlobalMouseListener object.
        GlobalMouseListener mouseListener = new GlobalMouseListener();
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);


    }
}