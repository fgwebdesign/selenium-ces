package com.tatf.parameterization.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ExampleTest {

    @ParameterizedTest
    @ValueSource(strings = {"Leonardo", "Laura", "Nahuel"})
    void test1(String nombre) {
        System.out.printf("Nombre: %s%n", nombre);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10})
    void ejemploCantidad(int cantidad) {
        System.out.printf("Cantidad: %d%n", cantidad);
    }

    @ParameterizedTest
    @CsvSource({
            "Leonardo, Perez",
            "Laura, Magallanes",
            "Nahuel, Torena"
    })
    void test2(String nombre, String apellido) {
        System.out.printf("Nombre: %s, Apellido: %s%n", nombre, apellido);
    }

    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(
            resources = "/datos_pruebas.csv",
            useHeadersInDisplayName = true
    )
    void test3(String nombre, String apellido) {
        System.out.printf("Nombre: %s, Apellido: %s%n", nombre, apellido);
    }
}
