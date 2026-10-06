package com.tatf.core.wait;

import com.tatf.core.driver.instance.DriverManagerSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {
    private DriverManagerSingleton instance;
    private final int waitSeconds;
    private String locator;
    private WebDriverWait webDriverWait;

    /**
     * Espera sobre un localizador puntual.
     *
     * @param instance    Instancia del Singleton que provee el driver.
     * @param waitSeconds Tiempo máximo de espera, en segundos.
     * @param locator     Localizador sobre el que se va a esperar.
     */
    public Wait(DriverManagerSingleton instance, int waitSeconds, String locator) {
        this.instance = instance;
        this.waitSeconds = waitSeconds;
        this.locator = locator;
        this.webDriverWait = new WebDriverWait(this.instance.getDriver(), Duration.ofSeconds(waitSeconds));
    }

    /**
     * Espera solo por tiempo fijo (para sleep).
     *
     * @param waitSeconds Cantidad de segundos a esperar.
     */
    public Wait(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    /**
     * Espera a que el elemento por id esté visible.
     */
    public void id() {
        wait(By.id(locator));
    }

    /**
     * Espera a que el elemento por name esté visible.
     */
    public void name() {
        wait(By.name(locator));
    }

    /**
     * Espera a que el elemento por css esté visible.
     */
    public void css() {
        wait(By.cssSelector(locator));
    }

    /**
     * Espera a que el elemento por xpath esté visible.
     */
    public void xpath() {
        wait(By.xpath(locator));
    }

    /**
     * Espera a que el elemento por texto de link esté visible.
     */
    public void link() {
        wait(By.linkText(locator));
    }

    /**
     * Espera a que el elemento por texto parcial de link esté visible.
     */
    public void partiaLink() {
        wait(By.partialLinkText(locator));
    }

    /**
     * Espera a que el elemento por class name esté visible.
     */
    public void className() {
        wait(By.className(locator));
    }

    /**
     * Espera a que el elemento por tag esté visible.
     */
    public void tag() {
        wait(By.id(locator));
    }

    /**
     * Espera a que haya una alerta presente.
     */
    public void alertPresent() {
        webDriverWait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Espera el tiempo configurado.
     */
    public void sleep() {
        this.sleep(waitSeconds);
    }

    /**
     * Duerme el hilo la cantidad de segundos indicada.
     *
     * @param seconds Cantidad de segundos a dormir.
     */
    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Espera a que el elemento del localizador esté visible.
     *
     * @param locator Localizador del elemento.
     */
    private void wait(By locator) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
