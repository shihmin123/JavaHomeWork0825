package util;

import javafx.application.Platform;

public final class FxPlatformUtil {
    private FxPlatformUtil() {}
    private static volatile boolean keepAliveSet = false;

    public static synchronized void keepAlive() {
        if (keepAliveSet) return;
        try {
            Platform.setImplicitExit(false); 
        } catch (IllegalStateException ignored) {
    
        }
        keepAliveSet = true;
    }
}
