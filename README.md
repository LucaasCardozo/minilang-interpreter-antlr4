# MiniLang Interpreter

## Integrantes

* Lucas Iván Cardozo
* Leandro Nahuel Blandi

## Materia

Conceptos y Paradigmas de Lenguajes de Programación

## Trabajo Práctico Cuatrimestral

Intérprete de un lenguaje imperativo simple utilizando ANTLR4.

## Link al video de la defensa

https://youtu.be/_JdpSZG7KBo

## Variante asignada

Variante 2: For

## Descripción del lenguaje

MiniLang es un lenguaje imperativo simple desarrollado en Java utilizando ANTLR4. El lenguaje permite declarar variables, realizar operaciones aritméticas, relacionales y lógicas, ejecutar estructuras condicionales e iterativas, y mostrar resultados por consola mediante una instrucción de impresión.

El proyecto incluye análisis léxico, sintáctico y semántico, además de la ejecución de programas válidos mediante el patrón Visitor provisto por ANTLR4.

## Tipos de datos soportados

* int
* float
* string
* bool

## Características implementadas

* Declaración de variables
* Asignación de valores
* Expresiones aritméticas
* Expresiones relacionales
* Expresiones lógicas
* Comentarios de línea
* Instrucción print
* Estructura condicional if/else
* Estructura iterativa for
* Análisis semántico
* Ejecución mediante Visitor

## Validaciones semánticas

El intérprete detecta:

* Uso de variables no declaradas
* Redeclaración de variables
* Incompatibilidad de tipos
* Operaciones inválidas
* División por cero

## Ejemplo 1 - If / Else simple

```text
int nota = 8;

if (nota >= 6) {
    print("Aprobado");
} else {
    print("Desaprobado");
}
```

Salida:

```text
Aprobado
```

## Ejemplo 2 - Varios if independientes

```text
int edad = 25;

if (edad >= 18) {
    print("Es mayor de edad");
}

if (edad >= 21) {
    print("Puede ingresar");
}

if (edad < 65) {
    print("No es jubilado");
}
```

Salida:

```text
Es mayor de edad
Puede ingresar
No es jubilado
```

## Ejemplo 3 - Contador con for

```text
for (int i = 1; i <= 10; i = i + 1) {
    print(i);
}
```

Salida:

```text
1
2
3
4
5
6
7
8
9
10
```
## Ejemplo 4 - Acumulador

```text
int suma = 0;

for (int i = 1; i <= 5; i = i + 1) {
    suma = suma + i;
}

print(suma);
```

Salida:

```text
15
```

## Ejemplo 5 - Operadores lógicos

```text
bool aprobado = true;
bool regular = true;

if (aprobado && regular) {
    print("Puede cursar");
} else {
    print("No puede cursar");
}
```

Salida:

```text
Puede cursar
```

## Ejemplo 6 - Uso de negación

```text
bool activo = false;

if (!activo) {
    print("Usuario inactivo");
} else {
    print("Usuario activo");
}
```

Salida:

```text
Usuario inactivo
```


## Compilación

```bash
mvn clean compile
```

## Generación de código ANTLR

```bash
mvn generate-sources
```

## Ejecución

```bash
mvn exec:java
```

## Estructura del proyecto

```text
src/
└── main/
    ├── antlr4/
    │   └── MiniLang.g4
    └── java/
        ├── Main.java
        ├── SymbolTable.java
        ├── VariableInfo.java
        ├── SemanticAnalyzer.java
        └── Interpreter.java
```
