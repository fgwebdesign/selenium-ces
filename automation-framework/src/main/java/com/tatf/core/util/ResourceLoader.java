package com.tatf.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {
    /**
     * Clase de solo métodos estáticos, no se instancia.
     */
    private ResourceLoader() {
    }

    /**
     * Lee un recurso del classpath y lo devuelve como texto.
     *
     * @param resourcePath Ruta del recurso dentro del classpath.
     */
    public static String loadAsString(String resourcePath) {
        try (InputStream input = ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("No se encontró el recurso: " + resourcePath);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Error leyendo el recurso: " + resourcePath, e);
        }
    }
}
