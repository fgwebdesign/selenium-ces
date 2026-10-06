package com.tatf.swaglabs.modules.login.test;

import com.tatf.swaglabs.modules.base.BaseTest;
import com.tatf.swaglabs.modules.login.data.LoginData;
import com.tatf.swaglabs.modules.login.task.LoginTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LoginTest extends BaseTest {

    private LoginTask iniciarSesion;

    @BeforeEach
    public void configurar() {
        this.iniciarSesion = new LoginTask(browser);
    }

    @DisplayName("Inicia sesión con usuario y contraseña correctos")
    @ParameterizedTest(name = "Usuario válido: {0}")
    @CsvFileSource(resources = "/datos/login_validos.csv", numLinesToSkip = 1)
    public void iniciarSesionCorrectoTest(String usuario, String clave) {
        this.iniciarSesion.enterToSystem(url);
        this.iniciarSesion.verifyTitle(LoginData.TITLE);
        this.iniciarSesion.logInToTheSystemAndVerify(usuario, clave);
    }

    @DisplayName("No inicia sesión con credenciales inválidas y muestra el error")
    @ParameterizedTest(name = "Usuario: {0} / Clave: {1}")
    @CsvFileSource(resources = "/datos/login_invalidos.csv", numLinesToSkip = 1)
    public void iniciarSesionIncorrectoTest(String usuario, String clave, String mensajeError) {
        this.iniciarSesion.enterToSystem(url);
        this.iniciarSesion.verifyTitle(LoginData.TITLE);
        this.iniciarSesion.logInWithInvalidCredentialsAndVerify(usuario, clave, mensajeError);
    }
}
