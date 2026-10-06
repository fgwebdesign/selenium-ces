package com.tatf.core.driver.manager;

import com.tatf.core.util.ConfigReader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class DriverManager {
    protected static final int IMPLICITLY_WAIT_DEFAULT_SECONDS = new ConfigReader("config.properties")
            .asInt("drivermanager.implicitly_wait_default_seconds");

    protected WebDriver driver;

    /**
     * Devuelve el WebDriver actual.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Aplica el timeout implícito por defecto y borra las cookies.
     */
    protected void setDefaultConfig() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_DEFAULT_SECONDS));
        driver.manage().deleteAllCookies();
    }

    /**
     * Cambia el tamaño de la ventana.
     *
     * @param width  Ancho de la ventana.
     * @param height Alto de la ventana.
     */
    protected void setWindowSize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    /**
     * Cambia la posición de la ventana.
     *
     * @param x Posición X de la ventana.
     * @param y Posición Y de la ventana.
     */
    protected void setWindowPosition(int x, int y) {
        driver.manage().window().setPosition(new Point(x, y));
    }

}
