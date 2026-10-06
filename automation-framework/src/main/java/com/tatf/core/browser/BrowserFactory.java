package com.tatf.core.browser;

import com.tatf.core.driver.factory.DriverType;
import com.tatf.core.driver.instance.DriverManagerSingleton;
import com.tatf.core.util.ConfigReader;

public class BrowserFactory {

    private static final ConfigReader CONFIG = new ConfigReader("config.properties");

    private static final int EXPLICIT_WAIT_DEFAULT_SECONDS = CONFIG.asInt("browserfactory.explicit_wait_default_seconds");
    private static final boolean DEBUGGING = CONFIG.asBoolean("browserfactory.debugging");
    private static final String DRIVER_TYPE = CONFIG.asString("browserfactory.driver_type");

    private BrowserFactory() {
    }

    /**
     * Arma un IBrowser listo para usar, con el driver correspondiente.
     *
     */
    public static IBrowser getBrowser() {
        DriverType type = resolveDriverType();
        DriverManagerSingleton instance = DriverManagerSingleton.getInstance(type);
        return new BrowserImpl(instance, EXPLICIT_WAIT_DEFAULT_SECONDS, DEBUGGING);
    }

    /**
     * Lee el browser a usar desde la propiedad del sistema "browser" (CHROME por defecto).
     */
    private static DriverType resolveDriverType() {
        return DriverType.valueOf(DRIVER_TYPE.toUpperCase());
    }

    /**
     * Cierra el browser actual y libera la instancia del Singleton.
     */
    public static void quitBrowser() {
        DriverType type = resolveDriverType();
        DriverManagerSingleton.getInstance(type).quit();
    }
}
