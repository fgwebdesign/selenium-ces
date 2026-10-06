package com.tatf.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties = new Properties();
    private final String fileName;

    public ConfigReader(String fileName) {
        this.fileName = fileName;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IllegalArgumentException("Archivo no encontrado en el classpath: " + fileName);
            }
            properties.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Error al cargar " + fileName, e);
        }
    }

    public String asString(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta la propiedad '" + key + "' en " + fileName);
        }
        return value.trim();
    }

    public int asInt(String key) {
        String value = asString(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("'" + key + "' no es un entero válido: " + value, e);
        }
    }

    public double asDouble(String key) {
        String value = asString(key).replace(',', '.');
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("'" + key + "' no es un decimal válido: " + value, e);
        }
    }

    public boolean asBoolean(String key){
        String value = asString(key);
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("'" + key + "' no es un booleano válido: " + value, e);
        }
    }
}
