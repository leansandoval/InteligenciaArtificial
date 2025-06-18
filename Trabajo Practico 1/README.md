# Trabajo Práctico - Ingeniería de Conocimiento, Metodología IDEAL y Sistemas Expertos
## Enunciado
Un equipo de ingenieros desea diseñar una base de conocimiento para el dominio de gestión de personal médico para que un sistema experto asista en el seguimiento del bienestar del personal médico.
Para ello, se realizó una entrevista con expertos y se dispone de un proceso para lograr un sistema integral dividido en fases estratégicas con diferentes criterios a nivel personal y organizacional:

* Fase 1: Reclutamiento y Perfiles Técnicos 
* Fase 2: Capacitación Continua 
* Fase 3: Gestión del Desempeño 
* Fase 4: Bienestar y Desarrollo 
* Fase 5: Gobierno Corporativo

En una primera versión se desea enfocarse en el bienestar y desarrollo del individuo  en alguna de las siguientes áreas:

### Detección y Prevención Temprana de factores de riesgo 
**Objetivo**: Identificar y mitigar factores de riesgo para el bienestar del personal médico.

1. **Regla de detección temprana de estrés**
 * `SI (empleado con más de 3 turnos nocturnos consecutivos + indicadores de fatiga > 70 %)`
 * `ENTONCES recomendar pausa activa de 30 minutos`
* **Acción**: Notificar al supervisor para ajustar horarios.

2. **Regla de apoyo psicológico**
  * `SI (empleado con historial de salud mental + reciente pérdida personal)`
  * `ENTONCES ofrecer sesiones de terapia confidencial`
  * **Acción**: Asignar psicólogo especializado.

3. **Regla de bienestar físico**
  * `SI (empleado con IMC >30 + sedentarismo > 4 h / día)`
  * `ENTONCES recomendar programa de actividad física moderada`
  * Acción: Incluir en programa de fitness hospitalario.

### Intervención y Soporte Activo 
**Objetivo**: Proporcionar intervenciones directas y apoyo continuo para mejorar el bienestar.

1. **Regla de reconocimiento automático**
  * `SI (empleado con logros destacados + evaluación positiva)`
  * `ENTONCES otorgar incentivo económico o reconocimiento público`
  * **Acción**: Notificar a Recursos Humanos para premiar.
  
2. **Regla de gestión de turnos**
  * `SI (empleado con más de 40 horas semanales + ausencia reciente por enfermedad)`
  * `ENTONCES reevaluar carga laboral`
  * **Acción**: Revisar y ajustar horarios según necesidad.
  
3. **Regla de capacitación continua**
  * `SI (empleado con certificación vencida + área crítica)`
  * `ENTONCES priorizar capacitación actualizada`
  * **Acción**: Inscribir en cursos relevantes.

### Consolidación y Mejora Continua 
**Objetivo**: Consolidar los avances y asegurar la mejora continua del bienestar.

1. **Regla de soporte emocional**
  * `SI (empleado con quejas recurrentes sobre ambiente laboral)`
  * `ENTONCES activar mediación con supervisor`
  * **Acción**: Facilitar diálogo constructivo. 

2. **Regla de equilibrio vida-trabajo**
  * `SI (empleado con más de 2 años sin vacaciones + carga laboral alta)`
  * `ENTONCES recomendar plan de vacaciones`
  * **Acción**: Asignar asesor para planificación.

3. **Regla de apoyo tecnológico**
  * `SI (empleado con dificultades en herramientas digitales + bajo rendimiento)`
  * `ENTONCES ofrecer tutoría personalizada`
  * **Acción**: Asignar mentor tecnológico.

4. **Regla de feedback continuo**
  * `SI (empleado con evaluaciones negativas recurrentes)`
  * `ENTONCES activar plan de mejora con retroalimentación regular.`
  * **Acción**: Establecer reuniones periódicas con el supervisor.

## Consigna
A partir del enunciado proporcionado elegir un área y profundizar en el tema, realizar la conceptualización que se realice un razonamiento entre 2-3 fases, formalización y desarrollo completo del caso (Opcional). Se deberá entregar un zip con el documento PDF, presentación y el proyecto Drools(Opcional) con el siguiente formato:
Documentación:
* Nombre del archivo de la documentación: [Numero de materia]_IA_C1_equipo_x_trabajo_practico_INCO.pdf
* Tamaño: A4.
* Encabezado: Extremo izquierdo: Inteligencia artificial 2025 C1 y Extremo derecho: Equipo X.
* Pie de página: debe tener el número de página en el extremo derecho de la hoja.

Archivo comprimido zip:
Nombre zip: Numero_materia_IA_C1_trabajo_practico_INCO.zip
* Estructura de directorios:
  * Documentación.
  * Código Fuente.
  * Presentación.

El **documento** debe ser entregado de manera **grupal** según el cronograma del curso con **la siguiente estructura**:

* Carátula 
* Índice 
* Problema por resolver 
  * Enunciado 
  * Atributo Meta definido. 
* Conceptualización 
  * Etapa de Análisis 
    * Conocimiento Fáctico. 
      * Diccionario de conceptos. 
      * Tabla CAV. 
      * Relaciones entre conceptos. 
    * Conocimiento Táctico. 
      * Tabla de decisión O Seudoreglas (Formato tabular sintaxis Objeto.atributo = valor). 
      * Fórmulas (Si se utilizaron). 
    * Conocimiento Estratégico. 
      * Árbol de descomposición funcional. 
  * Etapa de Síntesis 
    * Modelo estático. 
      * Descripción detallada de atributos. 
    * Modelo dinámico. 
      * Pasos procedimentales. 
    * Mapa de conocimiento. 
* Formalización 
  * Marcos Clase. 
  * Reglas de producción (Incorporar reglas de Drools realizadas en su proyecto). 
* Implementación 
  * **Presentación (Incorporar Link de video y Capturas de PowerPoint de presentación)**. 
  * Capturas de Pantallas (En caso de ser implementadas). 
  * Escenarios / Casos de prueba. 

**Implementación (Opcional)**
* **Realizar la implementación de un sistema experto** desarrollado con la herramienta **Drools**.  
* El prototipo deberá estar acompañado de **pruebas unitarias** realizadas con **Junit** como el ejemplo de clase.   
* El desarrollo debe ser agregado dentro de la carpeta “Código fuente” del zip.  

**Presentación** 
Para finalizar este trabajo práctico deberá realizarse una presentación grabada en vídeo subido privado en YouTube de 16 min como máximo que detalle:

1. Breve descripción del problema a resolver. 
2. Explicar la meta que debe cumplir el sistema 
3. Explicar Conocimiento fáctico (Solamente Diagrama de conceptos y tabla CAV).  
4. Explicar Conocimiento táctico (Reglas más importantes). 
5. Explicar conocimiento estratégico (Árbol de descomposición funcional). 
6. Pasos Procedimentales. 
7. Mapa de conocimiento obtenido en la conceptualización. 
8. Demostrar el funcionamiento del sistema experto desarrollado en Drools explicando y corriendo los casos de prueba **JUnit**. (**Opcional**).

**NOTA**: El **link de acceso al video** debe ser indicado **en la sección Implementación del documento a entregar** y la **presentación PowerPoint** utilizada deben guardarla en **la carpeta presentación**.
