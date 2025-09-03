# C+- Compiler

## English

**C+-** is a custom-designed programming language inspired by **C++**, created as a university capstone project.  
Its main peculiarity is that the **reserved keywords are written phonetically in Spanish**, as they sound when read in English.  
For example:  
- `while` → `guail`  
- `switch` → `suich`  
- `if` → `if` (unchanged)  

This repository contains the full **compiler implementation**, including:  
- **Lexical analysis**  
- **Syntax analysis**  
- **Semantic analysis**  
- **Error handling**  
- **Binding & typing**  
- **Code generation**  

📄 The complete **language specification** is available in [`doc/specifications/Info.pdf`](doc/specifications/Info.pdf).

### Features
- Complete compiler from source code to executable.
- Designed and implemented collaboratively as a team project.
- Modular architecture for extensibility and testing.

### Usage
Compile and run the compiler using Java:

```bash
# Basic usage: compile a .cpm file
java Main path/to/file.cpm

# With output options
java Main path/to/file.cpm -w output.wat -d output_directory
```

### Authors
This project was developed at Universidad Complutense de Madrid (2023-2024) by Javier Saras González, Juan Diego Barrado Daganzo and Daniel González Arbelo.

## Español
**C+-** es un lenguaje de programación diseñado como proyecto universitario, inspirado en **C++**.
Su principal característica es que **las palabras reservadas se escriben fonéticamente en español**, tal como suenan en inglés.
Por ejemplo:
- `while` → `guail`  
- `switch` → `suich`  
- `if` → `if` (sin cambios) 

This repository contains the full **compiler implementation**, including:  
- **Lexical analysis**  
- **Syntax analysis**  
- **Semantic analysis**  
- **Error handling**  
- **Binding & typing**  
- **Code generation**

Este repositorio contiene la **implementación completa de su compilador**, que incluye:
- **Análisis léxico**
- **Análisis sintáctico**
- **Análisis semántico**
- **Gestión de errores**
- **Enlazado y tipado**
- **Generación de código**

📄 La especificación completa del lenguaje está en [`doc/specifications/Info.pdf`](doc/specifications/Info.pdf).

### Uso
Compila y ejecuta el compilador usando Java:

```bash
# Uso básico: compilar un archivo .cpm
java Main ruta/al/archivo.cpm

# Con opciones de salida
java Main ruta/al/archivo.cpm -w salida.wat -d directorio_salida
```

### Authors
Este proyecto fue desarrollado en la Universidad Complutense de Madrid (2023-2024) por Javier Saras González, Juan Diego Barrado Daganzo y Daniel González Arbelo.