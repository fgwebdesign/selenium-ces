package com.tatf.core.driver.manager;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class ChromeDriver extends DriverManager {
    /**
     * Crea el driver de Chrome con las opciones por defecto.
     */
    public ChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");
        // Evita los avisos del gestor de contraseñas de Chrome, que interfieren con los clicks.
        chromeOptions.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
        chromeOptions.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));

        this.driver = new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
        setDefaultConfig();
    }
}
