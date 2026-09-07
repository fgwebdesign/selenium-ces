# selenium-ces

Pruebas básicas con **Selenium WebDriver 4** y **JUnit 5**, integradas con **Maven**.
Tarea de familiarización con Selenium WebDriver (CES).

Los datos de las pruebas (usuario, contraseña, URLs, términos de búsqueda, modo headless)
se toman de variables; no están fijos en el código.

## Pruebas

| Parte | Clase | Descripción |
|-------|-------|-------------|
| A | `partea.GoogleSearchTest` | Búsqueda en Google y validación de la página de resultados. |
| A | `partea.WikipediaSearchTest` | Búsqueda del artículo **"Hola mundo"** en Wikipedia. |
| B | `parteb.CapacitacionCesTest` | Guion completo en `capacitacion.ces.com.uy`: login → curso *"Taller de Automatización del Testing Funcional"* (Mis Cursos) → bloque **Actividades → Foros** → campo *"Buscar en los foros"* con **"Bienvenida"**. |

`support.BaseTest` abre y cierra un Chrome por prueba y guarda una captura en
`target/screenshots/`. `support.Config` resuelve cada valor en este orden:
`-Dclave=valor` → variable de entorno (`CLAVE_EN_MAYUSCULAS_CON_GUION_BAJO`) →
`src/test/resources/config.properties` → valor por defecto.

## Requisitos

- JDK 11 o superior
- Maven 3.8 o superior
- Google Chrome instalado (el ChromeDriver lo descarga Selenium Manager)

## Ejecución

```bash
# Parte A (no necesita credenciales)
mvn test -Dtest=GoogleSearchTest
mvn test -Dtest=WikipediaSearchTest

# Parte B: usuario y contraseña por variable
mvn test -Dtest=CapacitacionCesTest -Dces.username=USUARIO -Dces.password=CLAVE

# Toda la suite
mvn test
mvn test -Dwebdriver.headless=true
```

Para la Parte B las credenciales también pueden ir en variables de entorno
(`CES_USERNAME`, `CES_PASSWORD`) o en `src/test/resources/config.local.properties`
(ver `config.local.properties.example`; ese archivo está en `.gitignore`).
Si no hay credenciales, `CapacitacionCesTest` se omite.

## Variables

| Clave | Por defecto |
|-------|-------------|
| `google.url` / `google.query` | `https://www.google.com/ncr` / `Selenium WebDriver` |
| `wikipedia.url` / `wikipedia.articulo` | `https://es.wikipedia.org/` / `Hola mundo` |
| `ces.base.url` | `https://capacitacion.ces.com.uy` |
| `ces.username` / `ces.password` | — (obligatorias para la Parte B) |
| `ces.curso` | `Taller de Automatización del Testing Funcional` |
| `ces.foro.busqueda` | `Bienvenida` |
| `webdriver.headless` | `false` |

## Verificaciones

- **Google:** se abre Google, el término queda escrito en la caja, el título y la URL de
  resultados corresponden a la búsqueda y hay resultados que mencionan el término.
- **Wikipedia:** se abre `es.wikipedia.org`, se llega al artículo, `#firstHeading` es
  *"Hola mundo"*, la URL contiene `Hola_mundo` y el contenido menciona *"hola mundo"*.
- **Capacitación CES:** se accede al sitio; el login no da error y sale de `/login`; el curso
  aparece en *Mis Cursos* y se abre (`/course/view.php`); se abre el índice de **Foros**
  (`/mod/forum/index.php`) con el campo *"Buscar en los foros"*; la búsqueda de *"Bienvenida"*
  devuelve resultados y aparece el foro/debate de bienvenida.

> En el curso `202608A` el buscador de foros de Moodle busca dentro del contenido de los
> mensajes, y el foro de bienvenida está publicado como debates *"¡Bienvenid@s al curso!"* /
> *"Bienvenidos/as al curso!"*. Por eso la validación comprueba que aparezca un resultado de
> bienvenida (raíz *"bienvenid"*), no un título exactamente igual a *"Bienvenida"*.

## Estructura

```
selenium-ces/
├── pom.xml
└── src/test/
    ├── java/uy/com/ces/selenium/
    │   ├── support/   BaseTest, Config
    │   ├── partea/    GoogleSearchTest, WikipediaSearchTest
    │   └── parteb/    CapacitacionCesTest
    └── resources/
        ├── config.properties
        └── config.local.properties.example
```
