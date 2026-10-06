package com.tatf.swaglabs.modules.home.pom;

import com.tatf.core.browser.IBrowser;

public class HomePO {
    private final IBrowser browser;

    private final String title = "app_logo";

    public HomePO(IBrowser browser) {
        this.browser = browser;
    }

    public String getTitle() {
        return this.browser.find().className(title).getText();
    }
}
