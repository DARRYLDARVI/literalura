# 📚 Literalura

Literalura es una aplicación de consola en Java que permite consultar libros desde la API pública de [Gutendex](https://gutendex.com/), almacenarlos localmente en una base de datos, y consultar información sobre autores, idiomas, títulos y fechas. 

---

## 🚀 Tecnologías y herramientas utilizadas

- **Lenguaje:** Java 21
- **Framework:** Spring Boot
- **ORM:** JPA (Java Persistence API) con Hibernate
- **Base de datos:** H2 (en memoria)
- **Cliente HTTP:** `HttpClient` de Java
- **Serialización JSON:** Jackson (`ObjectMapper`)
- **Patrón de diseño:** Orientado a Objetos (POO)
- **Dependencias gestionadas con:** Maven
- **IDE recomendado:** IntelliJ IDEA o Eclipse

---

## 🧠 Conceptos aplicados

### 🔹 Programación Orientada a Objetos (POO)
Todo el proyecto está construido aplicando los principios de POO:
- **Encapsulamiento:** Cada clase tiene sus atributos privados y expone getters/setters.
- **Abstracción:** Se crean entidades como `Libro`, `Autor`, y `DatosLibros` para representar los conceptos del dominio.
- **Modularidad:** Separación clara de responsabilidades entre `model`, `repository`, `service` y `principal`.
- **Reutilización:** Uso de constructores y clases de datos (`record`) para simplificar el mapeo de la API.

---

### 🔹 Spring Data JPA
Se utiliza JPA para la persistencia de datos:
- Entidades anotadas con `@Entity`, `@Id`, `@ManyToOne`, `@OneToMany`.
- Repositorios basados en `JpaRepository` que permiten:
  - Guardar y recuperar libros y autores.
  - Consultar libros por idioma.
  - Buscar autores por nombre o por año.

---

### 🔹 Base de datos H2
- Base de datos relacional en memoria.
- Se configura automáticamente por Spring Boot.
- Se reinicia en cada ejecución (ideal para pruebas).

---

### 🔹 Consumo de APIs
- Se hace una solicitud HTTP a `https://gutendex.com/books/` para buscar libros.
- Se convierte la respuesta JSON en objetos Java usando `ObjectMapper`.

---

## 📋 Funcionalidades principales

1. **Buscar libro por título**  
   - Consulta la API de Gutendex y guarda el libro y autor si no existen en la base de datos.

2. **Listar libros guardados**  
   - Muestra todos los libros que ya han sido buscados y almacenados.

3. **Listar todos los autores registrados**

4. **Buscar autores vivos en un año específico**  
   - Busca autores cuyo año de nacimiento sea menor o igual al año ingresado.

5. **Buscar libros por idioma**  
   - Filtra los libros almacenados según su idioma (`es`, `en`, `fr`, etc.).

---

## 💡 Posibles mejoras
Persistencia con base de datos PostgreSQL o MySQL.

Interfaz gráfica (Swing/JavaFX) o web (Spring MVC).

Validaciones y manejo de errores más robusto.

Soporte para múltiples autores por libro.



