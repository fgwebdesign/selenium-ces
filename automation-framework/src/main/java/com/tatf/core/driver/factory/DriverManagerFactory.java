package com.tatf.core.driver.factory;

import com.tatf.core.driver.manager.ChromeDriver;
import com.tatf.core.driver.manager.DriverManager;
import org.openqa.selenium.WebDriver;

public class DriverManagerFactory {
    private DriverManagerFactory() {
    }

    /**
     * Crea el WebDriver según el tipo indicado.
     *
     * @param type Tipo de browser a crear.
     */
    public static WebDriver getDriver(DriverType type) {
        DriverManager dm;

        switch (type) {
            case CHROME:
                dm = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Tipo de driver no soportado: " + type);
        }

        return dm.getDriver();
    }
}
