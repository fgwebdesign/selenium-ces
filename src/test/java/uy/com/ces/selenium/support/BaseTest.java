package uy.com.ces.selenium.support;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base comun de las pruebas: abre un Chrome nuevo antes de cada test y lo cierra al terminar,
 * y deja una captura de pantalla en target/screenshots como evidencia de la ejecucion.
 *
 * El ChromeDriver lo resuelve Selenium Manager (Selenium 4.6+), no hay que instalarlo.
 * Con -Dwebdriver.headless=true la prueba corre sin ventana.
 */
public abstract class BaseTest {

    protected static final Duration ESPERA = Duration.ofSeconds(15);

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void abrirNavegador() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--start-maximized");
        opciones.addArguments("--lang=es-ES");
        opciones.addArguments("--disable-blink-features=AutomationControlled");
        opciones.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        if (Config.getBoolean("webdriver.headless", false)) {
            opciones.addArguments("--headless=new");
            opciones.addArguments("--window-size=1920,1080");
            // Chrome headless envia un user-agent con "HeadlessChrome" que Google responde
            // con una pagina sin JavaScript; se fuerza el user-agent de un Chrome normal.
            opciones.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        }

        driver = new ChromeDriver(opciones);
        wait = new WebDriverWait(driver, ESPERA);
    }

    @AfterEach
    void cerrarNavegador(TestInfo info) {
        try {
            capturarPantalla(info.getDisplayName());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void capturarPantalla(String nombre) {
        if (driver == null) {
            return;
        }
        try {
            Path carpeta = Paths.get("target", "screenshots");
            Files.createDirectories(carpeta);
            String marca = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String archivo = marca + "_" + nombre.replaceAll("[^a-zA-Z0-9-_]+", "_") + ".png";
            Files.write(carpeta.resolve(archivo),
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (Exception e) {
            System.err.println("No se pudo guardar la captura: " + e.getMessage());
        }
    }
}
