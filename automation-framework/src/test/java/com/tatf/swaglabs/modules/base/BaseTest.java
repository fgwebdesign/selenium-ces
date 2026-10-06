package com.tatf.swaglabs.modules.base;

import com.tatf.core.browser.BrowserFactory;
import com.tatf.core.browser.IBrowser;
import com.tatf.core.util.ConfigReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static IBrowser browser;
    protected static String url;

    @BeforeAll
    static public void configuration() {
        browser = BrowserFactory.getBrowser();
        url = new ConfigReader("config.properties").asString("swaglabs.url");
    }

    @AfterAll
    static public void close() {
        BrowserFactory.quitBrowser();
    }
}
