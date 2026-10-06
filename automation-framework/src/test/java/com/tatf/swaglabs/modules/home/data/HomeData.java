package com.tatf.swaglabs.modules.home.data;

import com.tatf.core.util.ConfigReader;

public class HomeData {
    public static final String TITLE = new ConfigReader("config.properties").asString("swaglabs.home.title");
}
