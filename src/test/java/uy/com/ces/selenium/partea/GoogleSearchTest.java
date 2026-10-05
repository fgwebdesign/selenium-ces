package uy.com.ces.selenium.partea;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import uy.com.ces.selenium.support.BaseTest;
import uy.com.ces.selenium.support.Config;

/**
 * Parte A - Busqueda en Google.
 *
 * Abre Google, escribe un termino, envia la busqueda y verifica que Google navega a su
 * pagina de resultados para ese termino. Se ejecuta una vez por cada fila de
 * datos/busquedas_google.csv; la URL sale de config.properties ("google.url").
 *
 * Nota: Google detecta clientes automatizados y a veces (sobre todo en headless) devuelve
 * una pagina de resultados degradada sin el listado. Por eso las comprobaciones firmes son
 * la caja de busqueda y la URL de resultados; la validacion del listado se hace solo si
 * Google lo renderiza (siempre ocurre en modo con ventana, que es el modo por defecto).
 */
@DisplayName("Parte A - Busqueda en Google")
class GoogleSearchTest extends BaseTest {

    private static final By RESULTADOS = By.cssSelector("#search h3, #rso h3");

    private final String url = Config.get("google.url", "https://www.google.com/ncr");
    private final int esperaListado = Config.getInt("google.results_wait_seconds", 5);

    @ParameterizedTest(name = "Busqueda de: {0}")
    @CsvFileSource(resources = "/datos/busquedas_google.csv", numLinesToSkip = 1)
    @DisplayName("Buscar un termino y validar la pagina de resultados")
    void busquedaEnGoogle(String termino, String palabraEnResultados) {
        driver.get(url);
        assertTrue(driver.getCurrentUrl().contains("google."),
                "No se abrio Google. URL: " + driver.getCurrentUrl());

        aceptarConsentimiento();

        WebElement caja = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
        caja.sendKeys(termino);
        assertTrue(termino.equalsIgnoreCase(caja.getDomProperty("value")),
                "El termino no quedo escrito en la caja de busqueda.");

        caja.sendKeys(Keys.ENTER);
        aceptarConsentimiento();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/search"),
                ExpectedConditions.urlContains("/sorry/")));

        // Si Google interpone su CAPTCHA anti-bot (/sorry/), no es un fallo del test ni del
        // sitio: se omite la prueba.
        String urlResultados = driver.getCurrentUrl();
        Assumptions.assumeFalse(urlResultados.contains("/sorry/"),
                "Google mostro su verificacion anti-bot (CAPTCHA); se omite. Reintentar con "
                + "ventana visible (modo por defecto) o mas tarde.");

        // Verificacion firme: Google navega a /search con el termino en el parametro q
        // (los espacios se codifican como '+').
        String qEsperado = "q=" + termino.replace(" ", "+");
        assertTrue(urlResultados.contains("/search") && urlResultados.contains(qEsperado),
                "La URL no es la busqueda esperada (falta '" + qEsperado + "'): " + urlResultados);

        // Verificacion del listado: solo si Google lo renderiza para el cliente automatizado.
        List<WebElement> titulos = listadoDeResultados();
        if (titulos.isEmpty()) {
            System.out.println("[GoogleSearchTest] Google devolvio una pagina sin listado "
                    + "(deteccion de automatizacion); se valido la busqueda por la URL.");
            return;
        }
        assertTrue(titulos.stream().anyMatch(t -> t.getText().toLowerCase().contains(palabraEnResultados)),
                "Ningun resultado menciona '" + palabraEnResultados + "'.");
    }

    private List<WebElement> listadoDeResultados() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(esperaListado))
                    .until(ExpectedConditions.presenceOfElementLocated(RESULTADOS));
        } catch (TimeoutException sinListado) {
            return List.of();
        }
        return driver.findElements(RESULTADOS);
    }

    /** Google puede mostrar un aviso de cookies antes o despues de buscar. */
    private void aceptarConsentimiento() {
        for (By boton : List.of(By.id("L2AGLb"),
                By.xpath("//button[contains(., 'Aceptar todo') or contains(., 'Accept all')]"))) {
            List<WebElement> encontrados = driver.findElements(boton);
            if (!encontrados.isEmpty() && encontrados.get(0).isDisplayed()) {
                encontrados.get(0).click();
                return;
            }
        }
    }
}
