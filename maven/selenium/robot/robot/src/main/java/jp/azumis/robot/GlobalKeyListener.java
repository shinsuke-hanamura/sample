package jp.azumis.robot;

import java.util.Map;
import java.util.TreeMap;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalKeyListener implements NativeKeyListener {

    private static Logger logger = LoggerFactory.getLogger(GlobalMouseListener.class);
    
    private static int[] PRESERVED_KEY_CODE = {NativeKeyEvent.VC_ESCAPE, NativeKeyEvent.VC_SPACE,
        NativeKeyEvent.VC_W, NativeKeyEvent.VC_S,
        NativeKeyEvent.VC_X, NativeKeyEvent.VC_Z,
        };
    
    private Map<Integer, Boolean> keyPressedMap;
    
    public GlobalKeyListener() {
        this.keyPressedMap = new TreeMap();
        for (int k : PRESERVED_KEY_CODE) {
            this.keyPressedMap.put(k, false);
        }
    }

    /**
     * 予約済みのキーコードのみ取得し、その他は0を返す
     * @return
     */
    private int getPreservedKeyCode(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();
        
        for (int k : PRESERVED_KEY_CODE) {
            if (k == keyCode) return k;
        }
        return 0;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = this.getPreservedKeyCode(e);
        if (keyCode > 0) {
            if (!keyPressedMap.get(keyCode)) logger.debug("Key Pressed: " + NativeKeyEvent.getKeyText(keyCode));
            keyPressedMap.put(keyCode, true);
        }
        
        //        if (keyCode == NativeKeyEvent.VC_ESCAPE) {
        //            try {
        //                GlobalScreen.unregisterNativeHook();
        //            } catch (NativeHookException e1) {
        //                e1.printStackTrace();
        //            }
        //        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        int keyCode = this.getPreservedKeyCode(e);
        if (keyCode > 0) {
            if (keyPressedMap.get(keyCode)) logger.debug("Key Released: " + NativeKeyEvent.getKeyText(keyCode));
            keyPressedMap.put(keyCode, false);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        int keyCode = this.getPreservedKeyCode(e);
        if (keyCode > 0) {
            logger.debug("Key Typed: " + NativeKeyEvent.getKeyText(keyCode));
        }
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

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}