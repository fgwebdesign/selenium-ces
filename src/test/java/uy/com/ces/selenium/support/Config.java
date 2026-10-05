package uy.com.ces.selenium.support;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Resuelve los datos de las pruebas (usuario, contrasena, URLs, terminos de busqueda)
 * sin dejarlos fijos en el codigo.
 *
 * Para cada clave se busca, en este orden:
 *   1. Propiedad de sistema:  -Dces.username=...
 *   2. Variable de entorno:   CES_USERNAME
 *   3. src/test/resources/config.properties  (o config.local.properties)
 *   4. El valor por defecto indicado en la llamada
 */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        cargar("config.local.properties");
        cargar("config.properties");
    }

    private Config() {
    }

    public static String get(String clave, String porDefecto) {
        String valor = System.getProperty(clave);
        if (vacio(valor)) {
            valor = System.getenv(clave.toUpperCase().replace('.', '_'));
        }
        if (vacio(valor)) {
            valor = PROPS.getProperty(clave);
        }
        return vacio(valor) ? porDefecto : valor.trim();
    }

    public static boolean getBoolean(String clave, boolean porDefecto) {
        return Boolean.parseBoolean(get(clave, String.valueOf(porDefecto)));
    }

    public static int getInt(String clave, int porDefecto) {
        String valor = get(clave, String.valueOf(porDefecto));
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("La propiedad " + clave + " debe ser un entero: " + valor, e);
        }
    }

    private static void cargar(String recurso) {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(recurso)) {
            if (in == null) {
                return;
            }
            Properties p = new Properties();
            try (Reader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                p.load(r);
            }
            p.forEach(PROPS::putIfAbsent);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo leer " + recurso, e);
        }
    }

    private static boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
