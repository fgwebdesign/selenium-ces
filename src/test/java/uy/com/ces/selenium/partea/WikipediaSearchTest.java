package uy.com.ces.selenium.partea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import uy.com.ces.selenium.support.BaseTest;
import uy.com.ces.selenium.support.Config;

/**
 * Parte A - Busqueda del articulo "Hola mundo" en Wikipedia.
 *
 * Escribe el termino en el buscador de Wikipedia y verifica que se abre el articulo
 * correcto. El termino se toma de la variable "wikipedia.articulo".
 */
@DisplayName("Parte A - Busqueda de 'Hola mundo' en Wikipedia")
class WikipediaSearchTest extends BaseTest {

    private final String url = Config.get("wikipedia.url", "https://es.wikipedia.org/");
    private final String articulo = Config.get("wikipedia.articulo", "Hola mundo");

    @Test
    @DisplayName("Buscar 'Hola mundo' y validar que se abre el articulo")
    void busquedaEnWikipedia() {
        driver.get(url);
        assertTrue(driver.getCurrentUrl().contains("es.wikipedia.org"),
                "No se abrio Wikipedia en espanol. URL: " + driver.getCurrentUrl());

        // El buscador de Wikipedia se rehidrata al cargar (el input se reemplaza y por un
        // instante no es interactuable). Se escribe sobre la primera caja visible reintentando
        // hasta confirmar que el texto quedo cargado, y luego se envia el formulario.
        By buscador = By.cssSelector("input[name='search']");
        wait.ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .until(d -> {
                    WebElement caja = cajaVisible(d.findElements(buscador));
                    if (caja == null) {
                        return false;
                    }
                    caja.clear();
                    caja.sendKeys(articulo);
                    String valor = caja.getDomProperty("value");
                    if (valor != null && valor.toLowerCase().contains("hola")) {
                        caja.submit();
                        return true;
                    }
                    return false;
                });

        // Segun coincida o no de forma exacta, Wikipedia abre el articulo o una lista de
        // resultados; en ese caso se entra al articulo desde el enlace.
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.id("firstHeading")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mw-search-results"))));
        if (!driver.findElements(By.cssSelector(".mw-search-results")).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".mw-search-results a[title='" + articulo + "']"))).click();
        }

        WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));
        assertEquals(articulo, titulo.getText().trim(),
                "El titulo del articulo no es el esperado.");
        assertTrue(driver.getCurrentUrl().contains(articulo.replace(' ', '_')),
                "La URL no corresponde al articulo. URL: " + driver.getCurrentUrl());

        String contenido = driver.findElement(By.id("mw-content-text")).getText().toLowerCase();
        assertTrue(contenido.contains("hola mundo"),
                "El contenido del articulo no menciona 'Hola mundo'.");
    }

    private static WebElement cajaVisible(List<WebElement> cajas) {
        return cajas.stream()
                .filter(c -> c.isDisplayed() && c.isEnabled())
                .findFirst()
                .orElse(null);
    }
}
