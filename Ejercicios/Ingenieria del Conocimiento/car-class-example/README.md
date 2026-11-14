<div align="center">
  <h1>🚗 Sistema Experto de Diagnóstico de Automóvil</h1>
  <p><em>Módulo 1: Ingeniería del Conocimiento — Sistema Basado en Reglas con Apache Drools 10 y Java 21.</em></p>

  <!-- Badges -->
  <img src="https://img.shields.io/badge/Universidad-UNLaM-1F4F90?style=for-the-badge&logo=google-scholar&logoColor=white" alt="UNLaM" />
  <img src="https://img.shields.io/badge/Materia-Inteligencia%20Artificial-7057D2?style=for-the-badge" alt="IA" />
  <img src="https://img.shields.io/badge/Java-21%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Apache%20Drools-10.1.0-BA281A?style=for-the-badge&logo=apache&logoColor=white" alt="Drools 10" />
  <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/JUnit-5%20(Jupiter)-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5" />
</div>

<br>

## 📑 Índice
- [Descripción del Proyecto](#descripcion-del-proyecto)
- [Dominio del Problema y Reglas](#dominio-del-problema-y-reglas)
  - [Modelo de Clases](#modelo-de-clases)
  - [Matriz de Diagnósticos y Prescripciones](#matriz-de-diagnosticos-y-prescripciones)
- [Estrategias de Razonamiento](#estrategias-de-razonamiento)
  - [Forward Chaining (Encadenamiento hacia Adelante)](#forward-chaining)
  - [Backward Chaining Simulado (Agenda Groups)](#backward-chaining-simulado)
- [Stack Tecnológico y Dependencias](#stack-tecnologico-y-dependencias)
- [Requisitos Previos e Instalación](#requisitos-previos-e-instalacion)
- [Configuración en Visual Studio Code o Cursor](#configuracion-en-visual-studio-code-o-cursor)
- [Cómo Ejecutar el Proyecto](#como-ejecutar-el-proyecto)
- [Casos de Prueba (Test Suites)](#casos-de-prueba-test-suites)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Detalles Técnicos y Buenas Prácticas Drools 10](#detalles-tecnicos-y-buenas-practicas-drools-10)

---

<a id="descripcion-del-proyecto"></a>
## 💡 Descripción del Proyecto
Este proyecto implementa un **Sistema Experto** (Knowledge-Based System) enfocado en el **diagnóstico de fallas en el arranque de un automóvil**. Utiliza un motor de inferencia basado en reglas (**Apache Drools**) para procesar síntomas físicos y mediciones sobre los componentes del vehículo, deduciendo paso a paso las condiciones anómalas hasta emitir una acción o recomendación mecánica correctiva.

El proyecto está diseñado como un conjunto de pruebas automatizadas con **JUnit 5**, donde cada test construye una instancia de `Automovil` con parámetros específicos, inserta los hechos en la memoria de trabajo de la sesión de conocimiento (`KieSession`), ejecuta el motor de reglas y valida que la `AccionRecomendada` obtenida coincida exactamente con la prescripción esperada por el experto humano.

---

<a id="dominio-del-problema-y-reglas"></a>
## 🧩 Dominio del Problema y Reglas

### Modelo de Clases
El modelo de datos vive en el paquete `model`:
* **`Automovil`**: Hecho principal insertado en el motor de reglas. Contiene referencias a `Motor`, `Bateria`, `TanqueCombustible` y su respectiva `Recomendacion`.
* **`Motor`**: Almacena el estado de giro (`Arranque: Gira, NoGira, NoInformado`), la fuerza del arranque (`Fuerza: Debil, Normal, NoInformado`), y banderas de síntomas audibles/mecánicos (`presentaExplosiones: Boolean`, `presentaGolpes: Boolean`).
* **`Bateria`**: Registra la medición de carga porcentual (`cargaPorcentual: Double` de 0.0% a 100.0%).
* **`TanqueCombustible`**: Registra la cantidad de combustible disponible (`cargaLts: Double` de 0.0 a 60.0 lts).
* **`Recomendacion`**: Contenedor del estado de inferencia intermedio y final:
  * `DiagnosticoPreliminar` (`nivelBateria`, `nivelCombustible`, `resultado`).
  * `DiagnosticoMotor` (`resultadoFinal`).
  * `AccionRecomendada` (Prescripción final resuelta).

### Matriz de Diagnósticos y Prescripciones
La base de conocimiento cuenta con **18 reglas** divididas en fases estratégicas:

| Reglas | Fase | Condición Antecedente | Inferencia / Acción Consecuente |
| :---: | :--- | :--- | :--- |
| **R01 – R03** | Diagnóstico Batería | $\le 30\%$ (Bajo) \| $31\% - 70\%$ (Medio) \| $> 70\%$ (Alto) | Establece `nivelBateria` en `DiagnosticoPreliminar` |
| **R04 – R08** | Diagnóstico Tanque | $0$ L (Vacío) \| $1 - 10$ L (Bajo) \| $11 - 40$ L (Medio) \| $41 - 59.9$ L (Alto) \| $60$ L (Completo) | Establece `nivelCombustible` en `DiagnosticoPreliminar` |
| **R09** | Diagnóstico Preliminar | Batería Baja + Motor No Gira | `ResultadoDiagnosticoPreliminar.BateriaBaja` |
| **R10** | Diagnóstico Preliminar | Tanque Vacío | `ResultadoDiagnosticoPreliminar.FaltaCombustible` |
| **R11** | Diagnóstico Motor | Motor Gira + Fuerza Débil | `ResultadoDiagnosticoMotor.TuberiasDeCombustibleSucias` |
| **R12** | Diagnóstico Motor | Presenta Explosiones | `ResultadoDiagnosticoMotor.PuntosDeInyeccionDesajustados` |
| **R13** | Diagnóstico Motor | Presenta Golpes | `ResultadoDiagnosticoMotor.TimingDelMotorDesajustado` |
| **R14** | Prescripción Final | Diagnóstico Preliminar = Batería Baja | `AccionRecomendada.CargarBateria` |
| **R15** | Prescripción Final | Diagnóstico Preliminar = Falta Combustible | `AccionRecomendada.CargarCombustible` |
| **R16** | Prescripción Final | Diagnóstico Motor = Tuberías Sucias | `AccionRecomendada.LimpiarTuberiasCombustible` |
| **R17** | Prescripción Final | Diagnóstico Motor = Puntos Inyección Desajustados | `AccionRecomendada.AjustarPuntosDeInyeccion` |
| **R18** | Prescripción Final | Diagnóstico Motor = Timing Desajustado | `AccionRecomendada.AjustarTimingMotor` |

---

<a id="estrategias-de-razonamiento"></a>
## ⚙️ Estrategias de Razonamiento

El sistema ejemplifica las dos modalidades de razonamiento sobre las mismas 18 reglas:

### Forward Chaining (Encadenamiento hacia Adelante)
* **Archivo de reglas**: `src/main/resources/rules/forward/forward-car-rules.drl`
* **Sesión**: `kforward-chaining-session` (definida en `kmodule.xml`)
* **Test Suite**: `src/test/java/forward/ForwardTestCases.java`
* **Mecanismo**: Razonamiento guiado por datos (*Data-driven*). Se introducen los síntomas observados y el motor propaga automáticamente los hechos modificados a través del ciclo Rete/Phreak (`modify($automovil)`). A medida que se derivan diagnósticos de batería/combustible o motor, se disparan en cascada las reglas de diagnóstico preliminar y luego las de prescripción final sin intervención manual en la agenda.

### Backward Chaining Simulado (Agenda Groups)
* **Archivo de reglas**: `src/main/resources/rules/backward/backward-car-rules.drl`
* **Sesión**: `kbackward-chaining-session` (definida en `kmodule.xml`)
* **Test Suite**: `src/test/java/backward/BackwardTestCases.java`
* **Mecanismo**: Emulación de razonamiento guiado por metas/hipótesis (*Goal-driven*). Las reglas tienen asignados atributos `agenda-group` y `lock-on-active`:
  1. `"Diagnostico Bateria"`
  2. `"Diagnostico Tanque de combustible"`
  3. `"Diagnostico preliminar"`
  4. `"Diagnostico motor"`
  5. `"Prescripcion de falla de arranque de automovil"`
* En el setup del test se apilan los focos en **orden inverso** (`Prescripcion` $\rightarrow$ `Motor` $\rightarrow$ `Preliminar` $\rightarrow$ `Tanque` $\rightarrow$ `Bateria`), garantizando el control de fases para resolver submetas antes de evaluar la prescripción final.

---

<a id="stack-tecnologico-y-dependencias"></a>
## 🛠️ Stack Tecnológico y Dependencias

A diferencia del proyecto original diseñado para Eclipse con Drools 7, este proyecto ha sido **migrado y actualizado integralmente** al ecosistema moderno de Java y Apache KIE:

* **Java**: **JDK 21 LTS** (Eclipse Adoptium Temurin 21.0.13+9).
* **Build System**: **Apache Maven 3.9+** (gestionado vía `pom.xml`).
* **Motor de Reglas**: **Apache Drools 10.1.0** (Apache KIE):
  * `org.drools:drools-engine`: Núcleo del motor de reglas ejecutable.
  * `org.drools:drools-xml-support`: Requerido para procesar `META-INF/kmodule.xml`.
  * `org.drools:drools-mvel`: Compilador y evaluador de expresiones MVEL en las restricciones de los patrones DRL.
* **Testing**: **JUnit 5 (Jupiter 5.12.2)**.
* **Logging**: **Logback Classic 1.5.18** + SLF4J para trazabilidad de eventos.

---

<a id="requisitos-previos-e-instalacion"></a>
## 📋 Requisitos Previos e Instalación

Para ejecutar este proyecto **no necesitas Eclipse ni plugins privativos**. Solo requieres contar con Java y Maven:

### 1. Java 21 LTS
Instalar **OpenJDK 21 (Temurin recomendado)**:
* Descarga desde [Adoptium](https://adoptium.net/temurin/releases/?version=21).
* Verificar instalación en la terminal:
  ```powershell
  java -version
  ```
  *Debe responder `openjdk version "21.x.x"`.*

* Asegúrate de que las variables de entorno `JAVA_HOME` y `PATH` apunten a dicho JDK:
  ```powershell
  $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.13.9-hotspot"
  $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
  ```

### 2. Apache Maven
Instalar **Maven 3.8+ o 3.9+**:
* Descarga desde [Apache Maven Project](https://maven.apache.org/download.cgi).
* Verificar instalación:
  ```powershell
  mvn -version
  ```

---

<a id="configuracion-en-visual-studio-code-o-cursor"></a>
## 💻 Configuración en Visual Studio Code o Cursor

El proyecto está 100% optimizado para desarrollarse y ejecutarse en **Visual Studio Code** o **Cursor**:

1. **Abrir la carpeta del proyecto**:
   * En VS Code / Cursor: `File -> Open Folder...` y seleccionar la carpeta:
     `InteligenciaArtificial/Ejercicios/Ingenieria del Conocimiento/car-class-example`
2. **Extensiones recomendadas**:
   * **Extension Pack for Java** (`vscjava.vscode-java-pack`) de Microsoft: provee compilación en segundo plano, autocompletado y runner de pruebas.
   * *(Opcional)* **Drools** (`kie-group.drools-vscode`): para resaltado de sintaxis en archivos `.drl`.
3. Al abrir la carpeta con el `pom.xml`, el Language Server de Java reconocerá automáticamente el proyecto Maven y todas sus dependencias.

---

<a id="como-ejecutar-el-proyecto"></a>
## 🚀 Cómo Ejecutar el Proyecto

No hay método `main` porque la lógica se evalúa mediante la suite de tests unitarios:

### 1. Ejecutar todos los tests desde Terminal (Recomendado)
Abre una terminal en el directorio `car-class-example`:

```powershell
# Compilar código y reglas
mvn clean compile

# Ejecutar las 10 pruebas unitarias (5 Forward + 5 Backward)
mvn test
```

Salida esperada:
```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running backward.BackwardTestCases
...
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 -- in backward.BackwardTestCases
[INFO] Running forward.ForwardTestCases
...
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 -- in forward.ForwardTestCases
[INFO] 
[INFO] Results:
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 2. Ejecutar suites individuales
```powershell
# Solo Forward Chaining
mvn test -Dtest=ForwardTestCases

# Solo Backward Chaining Simulado
mvn test -Dtest=BackwardTestCases
```

### 3. Ejecutar desde la interfaz gráfica de Cursor / VS Code
* Dirígete a la pestaña de **Testing** (icono de matraz/beaker en la barra lateral izquierda).
* Verás el árbol de pruebas:
  * `car-class-example`
    * `backward.BackwardTestCases`
    * `forward.ForwardTestCases`
* Haz clic en el botón de **Play (▶️)** de cualquiera de las clases o métodos de prueba para ejecutarlos o depurarlos paso a paso.

---

<a id="casos-de-prueba-test-suites"></a>
## 🧪 Casos de Prueba (Test Suites)

Tanto `ForwardTestCases` como `BackwardTestCases` evalúan los siguientes 5 escenarios canónicos:

| Test | Síntomas Ingresados | Reglas Clave Disparadas | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| `cargarBateriaTest` | Motor no gira, Batería al 1%, Tanque 20L | R01, R06, R09, R14 | `AccionRecomendada.CargarBateria` |
| `necesitaCombustibleTest` | Motor gira, Tanque en 0L, Batería al 70% | R02, R04, R10, R15 | `AccionRecomendada.CargarCombustible` |
| `limpiarTuberiasCombustibleTest` | Motor gira con fuerza débil, Batería 80%, Tanque 40L | R03, R06, R11, R16 | `AccionRecomendada.LimpiarTuberiasCombustible` |
| `ajustarPuntosDeInjeccionTest` | Motor gira con explosiones, Batería 20%, Tanque 40L | R01, R06, R12, R17 | `AccionRecomendada.AjustarPuntosDeInyeccion` |
| `ajustarTimingMotorTest` | Motor gira con golpes, Batería 60%, Tanque 60L | R02, R08, R13, R18 | `AccionRecomendada.AjustarTimingMotor` |

---

<a id="estructura-del-proyecto"></a>
## 📁 Estructura del Proyecto

```text
📦 car-class-example
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┗ 📂 model                       # Clases del dominio del automóvil
 ┃ ┃ ┃   ┣ 📜 AccionRecomendada.java
 ┃ ┃ ┃   ┣ 📜 Arranque.java
 ┃ ┃ ┃   ┣ 📜 Automovil.java
 ┃ ┃ ┃   ┣ 📜 Bateria.java
 ┃ ┃ ┃   ┣ 📜 DiagnosticoMotor.java
 ┃ ┃ ┃   ┣ 📜 DiagnosticoPreliminar.java
 ┃ ┃ ┃   ┣ 📜 Fuerza.java
 ┃ ┃ ┃   ┣ 📜 Motor.java
 ┃ ┃ ┃   ┣ 📜 NivelBateria.java
 ┃ ┃ ┃   ┣ 📜 NivelCombustible.java
 ┃ ┃ ┃   ┣ 📜 Recomendacion.java
 ┃ ┃ ┃   ┣ 📜 ResultadoDiagnosticoMotor.java
 ┃ ┃ ┃   ┣ 📜 ResultadoDiagnosticoPreliminar.java
 ┃ ┃ ┃   ┗ 📜 TanqueCombustible.java
 ┃ ┃ ┗ 📂 resources
 ┃ ┃   ┣ 📂 META-INF
 ┃ ┃   ┃ ┗ 📜 kmodule.xml               # Declaración de kbase y ksessions
 ┃ ┃   ┗ 📂 rules
 ┃ ┃     ┣ 📂 backward
 ┃ ┃     ┃ ┗ 📜 backward-car-rules.drl  # 18 reglas con agenda-groups
 ┃ ┃     ┗ 📂 forward
 ┃ ┃       ┗ 📜 forward-car-rules.drl   # 18 reglas en encadenamiento directo
 ┃ ┗ 📂 test
 ┃   ┗ 📂 java
 ┃     ┣ 📂 backward
 ┃     ┃ ┗ 📜 BackwardTestCases.java    # Tests JUnit 5 para backward chaining
 ┃     ┣ 📂 forward
 ┃     ┃ ┗ 📜 ForwardTestCases.java     # Tests JUnit 5 para forward chaining
 ┃     ┗ 📂 utils
 ┃       ┣ 📜 KnowledgeSessionHelper.java # Carga de KieClasspathContainer
 ┃       ┗ 📜 TestCaseUtils.java          # Formateo y aserciones de prueba
 ┣ 📜 pom.xml                           # Configuración Maven, Drools 10 y JUnit 5
 ┗ 📜 README.md                         # Documentación del proyecto
```

---

<a id="detalles-tecnicos-y-buenas-practicas-drools-10"></a>
## 🔍 Detalles Técnicos y Buenas Prácticas Drools 10

1. **Resolución de Paquetes en `kmodule.xml`**:
   En Drools 10, el atributo `packages` de `<kbase>` debe coincidir con el `package` declarado en la cabecera de los archivos `.drl` (`forwardCarRules` y `backwardCarRules`), permitiendo la correcta segregación de sesiones.
2. **Inclusión de `drools-mvel`**:
   Las restricciones con métodos y navegación de propiedades en el LHS (ej. `recomendacion.diagnosticoPreliminar.nivelBateria.isa(...)`) requieren el evaluador de expresiones de MVEL en el classpath para evitar excepciones de `DummyConstraintBuilder`.
3. **Comparación segura en Enums**:
   Los métodos `.isa()` de los enumerados utilizan `this.equals(...)` en reemplazo de operadores de identidad de cadenas (`==`) para garantizar una evaluación semántica correcta y robusta en cualquier entorno de ejecución.
4. **Prevención de Ciclos Infinitos**:
   Las reglas de inferencia condicionan sus antecedentes verificando que el valor previo sea `NoDeterminado` antes de aplicar `modify($automovil)`, evitando bucles infinitos de activación en el grafo de dependencias.

