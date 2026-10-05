# selenium-ces

Pruebas básicas con **Selenium WebDriver 4** y **JUnit 5**, integradas con **Maven**.
Tarea de familiarización con Selenium WebDriver (CES).

Las pruebas están **parametrizadas**: los datos de entrada viven en CSV y la configuración
del entorno en `config.properties`; no hay valores fijos en el código (ver *Parametrización*).

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

## Parametrización

Los datos se separaron en dos tipos de archivo:

**1. Datos de prueba → CSV** (`src/test/resources/datos/`, leídos con `@ParameterizedTest` + `@CsvFileSource`).
Cada fila es una ejecución independiente de la misma prueba.

| Archivo | Prueba | Columnas |
|---------|--------|----------|
| `busquedas_google.csv` | `GoogleSearchTest` | `termino`, `palabraEnResultados` |
| `articulos_wikipedia.csv` | `WikipediaSearchTest` | `articulo`, `textoEnContenido` |
| `busquedas_foros_ces.csv` | `CapacitacionCesTest` | `textoBusqueda`, `raizEsperada` |

**2. Configuración y entorno → `config.properties`** (leída con `support.Config`, que además
permite sobreescribir con `-D` o variable de entorno).

| Clave | Por defecto |
|-------|-------------|
| `webdriver.headless` | `false` |
| `webdriver.explicit_wait_seconds` | `15` |
| `webdriver.lang` / `webdriver.window_size` / `webdriver.user_agent` | `es-ES` / `1920,1080` / Chrome 131 |
| `google.url` / `google.results_wait_seconds` | `https://www.google.com/ncr` / `5` |
| `wikipedia.url` / `wikipedia.dominio` | `https://es.wikipedia.org/` / `es.wikipedia.org` |
| `ces.base.url` / `ces.dominio` | `https://capacitacion.ces.com.uy` / `capacitacion.ces.com.uy` |
| `ces.login.path` / `ces.cursos.path` / `ces.inicio.path` | `/login/index.php` / `/my/courses.php` / `/my/` |
| `ces.curso` / `ces.curso.titulo_esperado` | `Taller de Automatización del Testing Funcional` / `taller de automatiz` |
| `ces.username` / `ces.password` | — (solo por `-D`, variable de entorno o `config.local.properties`; nunca en el repo) |

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
        ├── config.local.properties.example
        └── datos/     busquedas_google.csv, articulos_wikipedia.csv, busquedas_foros_ces.csv
```
