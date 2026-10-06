package com.tatf.core.element;

import com.tatf.core.driver.instance.DriverManagerSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class Element {
    private final DriverManagerSingleton instance;
    private final WebElement webElement;

    /**
     * Guarda el driver y el WebElement que representa.
     *
     * @param instance Instancia del Singleton que provee el driver.
     * @param element  WebElement que representa este Element.
     */
    public Element(DriverManagerSingleton instance, WebElement element) {
        this.instance = instance;
        this.webElement = element;
    }

    /**
     * Hace click sobre el elemento.
     */
    public Element click() {
        this.webElement.click();
        return this;
    }

    /**
     * Limpia el contenido del elemento.
     */
    public Element clear() {
        this.webElement.clear();
        return this;
    }

    /**
     * Escribe texto en el elemento.
     *
     * @param text Texto a escribir.
     */
    public Element write(String text) {
        this.webElement.sendKeys(text);
        return this;
    }

    /**
     * Indica si el elemento está visible.
     */
    public boolean isDisplayed() {
        return this.webElement.isDisplayed();
    }

    /**
     * Indica si el elemento está habilitado.
     */
    public boolean isEnabled() {
        return this.webElement.isEnabled();
    }

    /**
     * Indica si el elemento está seleccionado.
     */
    public boolean isSelected() {
        return this.webElement.isSelected();
    }

    /**
     * Devuelve el nombre de la etiqueta HTML del elemento.
     */
    public String getTagName() {
        return this.webElement.getTagName();
    }

    /**
     * Devuelve la posición X del elemento.
     */
    public int x() {
        return this.webElement.getRect().getX();
    }

    /**
     * Devuelve la posición Y del elemento.
     */
    public int y() {
        return this.webElement.getRect().getY();
    }

    /**
     * Devuelve el ancho del elemento.
     */
    public int width() {
        return this.webElement.getRect().getWidth();
    }

    /**
     * Devuelve el alto del elemento.
     */
    public int height() {
        return this.webElement.getRect().getHeight();
    }

    /**
     * Devuelve el valor de una propiedad CSS del elemento.
     *
     * @param propertie Nombre de la propiedad CSS.
     */
    public String cssValue(String propertie) {
        return this.webElement.getCssValue(propertie);
    }

    /**
     * Devuelve el texto visible del elemento.
     */
    public String getText() {
        return this.webElement.getText();
    }

    /**
     * Devuelve el valor de un atributo del elemento.
     *
     * @param attribute Nombre del atributo.
     */
    public String getAttribute(String attribute) {
        return this.webElement.getAttribute(attribute);
    }

    /**
     * Sube un archivo usando este elemento (input de tipo file).
     *
     * @param file Archivo a subir.
     */
    public Element fileUpload(File file) {
        this.webElement.sendKeys(file.getAbsolutePath());
        return this;
    }

    /**
     * Cambia el foco del driver al frame representado por este elemento.
     */
    public Element switchFrameToMe() {
        instance.getDriver().switchTo().frame(this.webElement);
        return this;
    }

    /**
     * Selecciona una opción de un combo por su texto visible.
     *
     * @param text Texto visible de la opción.
     */
    public Element selectText(String text) {
        new Select(this.webElement).selectByVisibleText(text);
        return this;
    }

    /**
     * Selecciona una opción de un combo por su valor.
     *
     * @param value Valor de la opción.
     */
    public Element selectValue(String value) {
        new Select(this.webElement).selectByValue(value);
        return this;
    }

    /**
     * Selecciona una opción de un combo por su posición.
     *
     * @param position Posición de la opción (empieza en 0).
     */
    public Element selectPosition(int position) {
        new Select(this.webElement).selectByIndex(position);
        return this;
    }

    /**
     * Hace click y mantiene presionado el elemento.
     */
    public Element clickHold() {
        new Actions(instance.getDriver()).clickAndHold(this.webElement).perform();
        return this;
    }

    /**
     * Hace doble click sobre el elemento.
     */
    public Element doubleClick() {
        new Actions(instance.getDriver()).doubleClick(this.webElement).perform();
        return this;
    }

    /**
     * Arrastra este elemento y lo suelta sobre otro elemento.
     *
     * @param element Elemento sobre el que se suelta este elemento.
     */
    public Element dragAndDropTo(Element element) {
        new Actions(instance.getDriver()).dragAndDrop(this.webElement, element.webElement).perform();
        return this;
    }

    /**
     * Busca un elemento hijo por id, a partir de este elemento.
     *
     * @param locator Id del elemento hijo.
     */
    public Element id(String locator) {
        return this.find(By.id(locator));
    }

    /**
     * Busca un elemento hijo por name, a partir de este elemento.
     *
     * @param locator Name del elemento hijo.
     */
    public Element name(String locator) {
        return this.find(By.name(locator));
    }

    /**
     * Busca un elemento hijo por selector css, a partir de este elemento.
     *
     * @param locator Selector css del elemento hijo.
     */
    public Element css(String locator) {
        return this.find(By.cssSelector(locator));
    }

    /**
     * Busca un elemento hijo por texto de link, a partir de este elemento.
     *
     * @param locator Texto de link del elemento hijo.
     */
    public Element link(String locator) {
        return this.find(By.linkText(locator));
    }

    /**
     * Busca un elemento hijo por texto parcial de link, a partir de este elemento.
     *
     * @param locator Texto parcial de link del elemento hijo.
     */
    public Element partialLink(String locator) {
        return this.find(By.partialLinkText(locator));
    }

    /**
     * Busca un elemento hijo por xpath, a partir de este elemento.
     *
     * @param locator Xpath del elemento hijo.
     */
    public Element xpath(String locator) {
        return this.find(By.xpath(locator));
    }

    /**
     * Busca un elemento hijo por class name, a partir de este elemento.
     *
     * @param locator Class name del elemento hijo.
     */
    public Element className(String locator) {
        return this.find(By.className(locator));
    }

    /**
     * Busca un elemento hijo por tag, a partir de este elemento.
     *
     * @param locator Tag del elemento hijo.
     */
    public Element tag(String locator) {
        return this.find(By.tagName(locator));
    }

    private Element find(By by) {
        return new Element(this.instance, this.webElement.findElement(by));
    }
}
