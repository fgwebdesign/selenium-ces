package com.tatf.core.find;

import com.tatf.core.driver.instance.DriverManagerSingleton;
import com.tatf.core.element.Element;
import com.tatf.core.interaction.Interaction;
import com.tatf.core.util.ConfigReader;
import com.tatf.core.util.ResourceLoader;
import com.tatf.core.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Find {
    private static final int DEFAUTL_DELAY_SECONDS = new ConfigReader("config.properties")
            .asInt("find.defautl_delay_seconds");

    private final DriverManagerSingleton instance;
    private final String highlightScript = ResourceLoader.loadAsString("js/highlight.js");
    private final boolean debugging;
    private final WebDriver driver;

    /**
     * Guarda el driver y si está en modo debug.
     *
     * @param instance  Instancia del Singleton que provee el driver.
     * @param debugging Si es true, resalta los elementos al buscarlos.
     */
    public Find(DriverManagerSingleton instance, boolean debugging) {
        this.instance = instance;
        this.driver = instance.getDriver();
        this.debugging = debugging;
    }

    /**
     * Busca un único elemento y lo envuelve en un Element.
     *
     * @param by Localizador del elemento.
     */
    private Element find(By by) {
        WebElement webElement = this.driver.findElement(by);

        if (this.debugging) {
            new Interaction(this.instance).javaScriptExecutor(highlightScript, webElement);
            new Wait(DEFAUTL_DELAY_SECONDS).sleep();
        }

        return new Element(this.instance, webElement);
    }

    /**
     * Busca varios elementos y los envuelve en una lista de Element.
     *
     * @param by Localizador de los elementos.
     */
    private List<Element> finds(By by) {
        List<WebElement> webElements = this.driver.findElements(by);
        List<Element> elements = new ArrayList<>();

        for (WebElement webElement : webElements) {
            if (this.debugging) {
                new Interaction(this.instance).javaScriptExecutor(highlightScript, webElement);
                new Wait(1).sleep();
            }

            elements.add(new Element(this.instance, webElement));
        }

        return elements;
    }

    /**
     * Busca un elemento por id.
     *
     * @param id Id del elemento.
     */
    public Element id(String id) {
        return find(By.id(id));
    }

    /**
     * Busca un elemento por name.
     *
     * @param name Name del elemento.
     */
    public Element name(String name) {
        return find(By.name(name));
    }

    /**
     * Busca un elemento por class name.
     *
     * @param className Class name del elemento.
     */
    public Element className(String className) {
        return find(By.className(className));
    }

    /**
     * Busca un elemento por selector css.
     *
     * @param cssSelector Selector css del elemento.
     */
    public Element css(String cssSelector) {
        return find(By.cssSelector(cssSelector));
    }

    /**
     * Busca un elemento por texto de link.
     *
     * @param linkText Texto de link del elemento.
     */
    public Element link(String linkText) {
        return find(By.linkText(linkText));
    }

    /**
     * Busca un elemento por texto parcial de link.
     *
     * @param partialLinkText Texto parcial de link del elemento.
     */
    public Element linkPartial(String partialLinkText) {
        return find(By.partialLinkText(partialLinkText));
    }

    /**
     * Busca un elemento por tag.
     *
     * @param tagName Tag del elemento.
     */
    public Element tag(String tagName) {
        return find(By.tagName(tagName));
    }

    /**
     * Busca un elemento por xpath.
     *
     * @param xpath Xpath del elemento.
     */
    public Element xpath(String xpath) {
        return find(By.xpath(xpath));
    }

    /**
     * Busca una lista de elementos por class name.
     *
     * @param className Class name de los elementos.
     */
    public List<Element> classNameList(String className) {
        return finds(By.className(className));
    }

    /**
     * Busca una lista de elementos por selector css.
     *
     * @param cssSelector Selector css de los elementos.
     */
    public List<Element> cssSelectorList(String cssSelector) {
        return finds(By.cssSelector(cssSelector));
    }

    /**
     * Busca una lista de elementos por id.
     *
     * @param id Id de los elementos.
     */
    public List<Element> idList(String id) {
        return finds(By.id(id));
    }

    /**
     * Busca una lista de elementos por name.
     *
     * @param name Name de los elementos.
     */
    public List<Element> nameList(String name) {
        return finds(By.name(name));
    }

    /**
     * Busca una lista de elementos por texto de link.
     *
     * @param linkText Texto de link de los elementos.
     */
    public List<Element> linkList(String linkText) {
        return finds(By.linkText(linkText));
    }

    /**
     * Busca una lista de elementos por texto parcial de link.
     *
     * @param partialLinkText Texto parcial de link de los elementos.
     */
    public List<Element> linkPartialList(String partialLinkText) {
        return finds(By.partialLinkText(partialLinkText));
    }

    /**
     * Busca una lista de elementos por tag.
     *
     * @param tagName Tag de los elementos.
     */
    public List<Element> tagList(String tagName) {
        return finds(By.tagName(tagName));
    }

    /**
     * Busca una lista de elementos por xpath.
     *
     * @param xpath Xpath de los elementos.
     */
    public List<Element> xpathList(String xpath) {
        return finds(By.xpath(xpath));
    }

    /**
     * Busca una lista de elementos por selector css.
     *
     * @param locator Selector css de los elementos.
     */
    public List<Element> cssList(String locator) {
        return finds(By.cssSelector(locator));
    }
}
