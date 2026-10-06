package com.tatf.swaglabs.modules.home.task;

import com.tatf.core.browser.IBrowser;
import com.tatf.core.verification.IVerify;
import com.tatf.swaglabs.modules.home.pom.HomePO;

public class HomeTask {
    private final IBrowser browser;
    private final HomePO home;

    public HomeTask(IBrowser browser) {
        this.browser = browser;
        this.home = new HomePO(browser);
    }

    public void verifyTitle(String title) {
        IVerify.create().verify(home.getTitle(), title, "El título no es el esperado.");
    }
}
