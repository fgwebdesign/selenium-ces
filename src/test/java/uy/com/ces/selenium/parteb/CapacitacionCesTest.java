package uy.com.ces.selenium.parteb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import uy.com.ces.selenium.support.BaseTest;
import uy.com.ces.selenium.support.Config;

/**
 * Parte B - Guion completo sobre el sitio de capacitacion del CES (Moodle).
 *
 *   1. Acceder a capacitacion.ces.com.uy
 *   2. Iniciar sesion con un usuario habilitado
 *   3. Entrar al curso "Taller de Automatizacion del Testing Funcional" desde "Mis Cursos"
 *   4. En el bloque "Actividades", ir a "Foros"
 *   5. Buscar "Bienvenida" en "Buscar en los foros" y validar que aparece el foro de bienvenida
 *
 * El usuario y la contrasena se pasan por variable (ces.username / ces.password); si no
 * estan definidos la prueba se omite.
 */
@DisplayName("Parte B - Guion completo capacitacion CES")
class CapacitacionCesTest extends BaseTest {

    private final String baseUrl = Config.get("ces.base.url", "https://capacitacion.ces.com.uy");
    private final String usuario = Config.get("ces.username", "");
    private final String clave = Config.get("ces.password", "");
    private final String nombreCurso =
            Config.get("ces.curso", "Taller de Automatización del Testing Funcional");
    private final String textoBusqueda = Config.get("ces.foro.busqueda", "Bienvenida");

    @Test
    @DisplayName("Login, curso, Foros y busqueda de 'Bienvenida'")
    void guionCompleto() {
        Assumptions.assumeFalse(usuario.isEmpty() || clave.isEmpty(),
                "Parte B omitida: definir ces.username y ces.password (por -D, variable de entorno "
                + "CES_USERNAME/CES_PASSWORD o config.local.properties).");

        accederAlSitio();
        iniciarSesion();
        abrirCurso();
        abrirForos();
        buscarForoDeBienvenida();
    }

    private void accederAlSitio() {
        driver.get(baseUrl);
        assertTrue(driver.getCurrentUrl().contains("capacitacion.ces.com.uy"),
                "No se pudo acceder al sitio. URL: " + driver.getCurrentUrl());
    }

    private void iniciarSesion() {
        driver.get(baseUrl + "/login/index.php");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(usuario);
        driver.findElement(By.id("password")).sendKeys(clave);
        driver.findElement(By.id("loginbtn")).click();

        assertTrue(driver.findElements(By.cssSelector(".loginerrors, #loginerrormessage")).isEmpty(),
                "El login fallo: revisar usuario y contrasena.");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login/index.php")));
    }

    private void abrirCurso() {
        // Enlace al curso en "Mis Cursos", sin importar mayusculas/minusculas ni el sufijo
        // de edicion ("... 202608A") que agrega Moodle al nombre.
        By enlaceCurso = By.xpath("//a[contains(@href,'/course/view.php')]"
                + "[contains(translate(normalize-space(.),"
                + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚÑ', 'abcdefghijklmnopqrstuvwxyzáéíóúñ'),"
                + " '" + nombreCurso.toLowerCase() + "')]");

        driver.get(baseUrl + "/my/courses.php");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(enlaceCurso)).click();
        } catch (TimeoutException sinCurso) {
            driver.get(baseUrl + "/my/");
            wait.until(ExpectedConditions.elementToBeClickable(enlaceCurso)).click();
        }

        WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#page-header h1, .page-header-headings h1, h1")));
        assertTrue(titulo.getText().toLowerCase().contains("taller de automatiz"),
                "No se abrio el curso esperado. Titulo: " + titulo.getText());
        assertTrue(driver.getCurrentUrl().contains("/course/view.php"),
                "La URL no es la de un curso. URL: " + driver.getCurrentUrl());
    }

    private void abrirForos() {
        By enBloqueActividades = By.xpath("//*[contains(@class,'block')]"
                + "[.//*[contains(normalize-space(.),'Actividades')]]"
                + "//a[contains(normalize-space(.),'Foros')]");
        By cualquierIndiceDeForos = By.cssSelector("a[href*='/mod/forum/index.php']");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(enBloqueActividades),
                ExpectedConditions.presenceOfElementLocated(cualquierIndiceDeForos)));

        List<WebElement> enlaces = driver.findElements(enBloqueActividades);
        if (enlaces.isEmpty()) {
            enlaces = driver.findElements(cualquierIndiceDeForos);
        }
        wait.until(ExpectedConditions.elementToBeClickable(enlaces.get(0))).click();

        wait.until(ExpectedConditions.urlContains("/mod/forum/index.php"));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[name='search'], input[placeholder*='foros']")));
    }

    private void buscarForoDeBienvenida() {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='search'], input[placeholder*='foros']")));
        campo.sendKeys(textoBusqueda);
        campo.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/mod/forum/search.php"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#region-main")));

        List<WebElement> resultados = driver.findElements(
                By.cssSelector("#region-main a[href*='/mod/forum/']"));
        assertFalse(resultados.isEmpty(),
                "La busqueda de '" + textoBusqueda + "' no devolvio resultados en los foros.");

        // Los debates de bienvenida del curso se titulan "¡Bienvenid@s al curso!" /
        // "Bienvenidos/as al curso!", por eso se compara contra la raiz "bienvenid".
        boolean hayBienvenida = resultados.stream()
                .anyMatch(a -> a.getText().toLowerCase().contains("bienvenid"));
        assertTrue(hayBienvenida,
                "No aparece ningun foro/debate de bienvenida en los resultados.");
    }
}
