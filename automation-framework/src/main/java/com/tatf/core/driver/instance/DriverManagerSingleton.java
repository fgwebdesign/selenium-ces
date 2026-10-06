package com.tatf.core.driver.instance;

import com.tatf.core.driver.factory.DriverManagerFactory;
import com.tatf.core.driver.factory.DriverType;
import org.openqa.selenium.WebDriver;

public class DriverManagerSingleton {
    private static volatile DriverManagerSingleton instance;
    private final WebDriver driver;

    /**
     * Crea el driver a través de la Factory, según el tipo.
     *
     * @param type Tipo de browser a crear.
     */
    private DriverManagerSingleton(DriverType type) {
        driver = DriverManagerFactory.getDriver(type);
    }

    /**
     * Devuelve la única instancia, creándola si todavía no existe.
     *
     * @param type Tipo de browser a crear, si la instancia todavía no existe.
     */
    public static DriverManagerSingleton getInstance(DriverType type) {
        if (instance == null) {
            synchronized (DriverManagerSingleton.class) {
                if (instance == null) {
                    instance = new DriverManagerSingleton(type);
                }
            }
        }
        return instance;
    }

    /**
     * Devuelve el WebDriver actual.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Cierra el driver y libera la instancia.
     */
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
        instance = null;
    }
}
