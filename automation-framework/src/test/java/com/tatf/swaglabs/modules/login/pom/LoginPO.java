package com.tatf.swaglabs.modules.login.pom;

import com.tatf.core.browser.IBrowser;

public class LoginPO {
    private final IBrowser browser;

    private final String title = "login_logo";
    private final String usernameInput = "user-name";
    private final String passwordInput = "password";
    private final String loginButton = "login-button";
    private final String errorMessage = "[data-test='error']";

    public LoginPO(IBrowser browser) {
        this.browser = browser;
    }

    public String getTitle() {
        return this.browser.find().className(title).getText();
    }

    public void enterUsername(String username) {
        this.browser.find().id(usernameInput).write(username);
    }

    public void enterPassword(String password) {
        this.browser.find().id(passwordInput).write(password);
    }

    public String getErrorMessage() {
        return this.browser.find().css(errorMessage).getText();
    }

    public void clickLogin() {
        this.browser.find().id(loginButton).click();
    }
}
