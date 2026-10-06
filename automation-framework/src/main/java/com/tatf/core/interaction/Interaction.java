package com.tatf.core.interaction;

import com.tatf.core.driver.instance.DriverManagerSingleton;
import org.openqa.selenium.*;

public class Interaction {
    private final WebDriver driver;
    private Alert alert;

    /**
     * Obtiene el driver a partir de la instancia del Singleton.
     *
     * @param instance Instancia del Singleton que provee el driver.
     */
    public Interaction(DriverManagerSingleton instance) {
        this.driver = instance.getDriver();
    }

    /**
     * Devuelve el título de la página actual.
     */
    public String title() {
        return this.driver.getTitle();
    }

    /**
     * Devuelve la URL actual.
     */
    public String url() {
        return this.driver.getCurrentUrl();
    }

    /**
     * Navega a la URL indicada.
     *
     * @param url URL a la que navegar.
     */
    public Interaction navigateTo(String url) {
        this.driver.get(url);
        return this;
    }

    /**
     * Vuelve a la página anterior.
     */
    public Interaction back() {
        this.driver.navigate().back();
        return this;
    }

    /**
     * Avanza a la página siguiente.
     */
    public Interaction forward() {
        this.driver.navigate().forward();
        return this;
    }

    /**
     * Recarga la página actual.
     */
    public Interaction refresh() {
        this.driver.navigate().refresh();
        return this;
    }

    /**
     * Cambia el foco a la alerta actual.
     */
    public Interaction switchToAlert() {
        this.alert = this.driver.switchTo().alert();
        return this;
    }

    /**
     * Acepta la alerta actual.
     */
    public Interaction acceptAlert() {
        alert.accept();
        return this;
    }

    /**
     * Devuelve el texto de la alerta actual.
     */
    public String textAlert() {
        return alert.getText();
    }

    /**
     * Escribe texto en la alerta actual.
     *
     * @param text Texto a escribir.
     */
    public Interaction writeAlert(String text) {
        alert.sendKeys(text);
        return this;
    }

    /**
     * Cambia el foco a un frame por índice.
     *
     * @param index Índice del frame.
     */
    public Interaction switchToFrame(int index) {
        this.driver.switchTo().frame(index);
        return this;
    }

    /**
     * Cambia el foco a un frame por name o id.
     *
     * @param nameOrId Name o id del frame.
     */
    public Interaction switchToFrame(String nameOrId) {
        this.driver.switchTo().frame(nameOrId);
        return this;
    }

    /**
     * Vuelve al contenido principal (fuera de cualquier frame).
     */
    public Interaction switchToDefaultContent() {
        this.driver.switchTo().defaultContent();
        return this;
    }

    /**
     * Devuelve el identificador de la ventana actual.
     */
    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }

    /**
     * Devuelve los identificadores de todas las ventanas abiertas.
     */
    public Object[] getWindows() {
        return this.driver.getWindowHandles().toArray();
    }

    /**
     * Cambia el foco a la ventana en la posición indicada.
     *
     * @param indexWindow Posición de la ventana (empieza en 0).
     */
    public Interaction switchToWindow(int indexWindow) {
        String window = getWindows()[indexWindow].toString();
        this.driver.switchTo().window(window);
        return this;
    }

    /**
     * Cierra la ventana actual.
     */
    public Interaction closeWindow() {
        this.driver.close();
        return this;
    }

    /**
     * Devuelve el ancho de la ventana.
     */
    public int width() {
        return driver.manage().window().getSize().getWidth();
    }

    /**
     * Devuelve el alto de la ventana.
     */
    public int height() {
        return this.driver.manage().window().getSize().getHeight();
    }

    /**
     * Devuelve la posición X de la ventana.
     */
    public int x() {
        return this.driver.manage().window().getPosition().getX();
    }

    /**
     * Devuelve la posición Y de la ventana.
     */
    public int y() {
        return this.driver.manage().window().getPosition().getY();
    }

    /**
     * Pone la ventana en pantalla completa.
     */
    public Interaction f11() {
        this.driver.manage().window().fullscreen();
        return this;
    }

    /**
     * Minimiza la ventana.
     */
    public Interaction minimize() {
        this.driver.manage().window().minimize();
        return this;
    }

    /**
     * Devuelve una captura de pantalla en base64.
     */
    public String getScreenshot() {
        TakesScreenshot screenshot = (TakesScreenshot) this.driver;
        return screenshot.getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Cierra el driver.
     */
    public void quit() {
        this.driver.quit();
    }

    /**
     * Ejecuta un script de JavaScript.
     *
     * @param script Script a ejecutar.
     */
    public Interaction javaScriptExecutor(String script) {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript(script);
        return this;
    }

    /**
     * Ejecuta un script de JavaScript sobre un elemento.
     *
     * @param script  Script a ejecutar.
     * @param element Elemento sobre el que se ejecuta el script.
     */
    public void javaScriptExecutor(String script, Object element) {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript(script, element);
    }
}
