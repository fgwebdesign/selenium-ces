package com.tatf.swaglabs.modules.login.data;

import com.tatf.core.util.ConfigReader;

public class LoginData {
    public static final String TITLE = new ConfigReader("config.properties").asString("swaglabs.login.title");
}
