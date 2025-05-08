package com.edisa.formacion.mayo2025.areas;

public class calculoAreasEj1 {
    public static void main(String[] args) {
    
        double ancho;
        double alto;
        double area;

    try {

        if (args.length < 2) {
            throw new IllegalArgumentException("Faltan argumentos");
        }

        String anchoString = args[0];
        String altoString = args[1];

        if (anchoString.isEmpty()) {
            throw new IllegalArgumentException("El argumento ancho está vacio");
        }

        if (altoString.isEmpty()) {
            throw new IllegalArgumentException("El argumento alto está vacio");
        }

        ancho = Double.parseDouble(anchoString);
        alto = Double.parseDouble(altoString);

        if (ancho < 0) {
            throw new IllegalArgumentException("Valor no válido. Debe ser mayor a 0");
        }

        if (alto < 0) {
            throw new IllegalArgumentException("Valor no válido. Debe ser mayor a 0");
        }
        
        area = ancho * alto;

        System.out.println("El área del cuadrado es: " + area);

    } catch (NumberFormatException e) {

        System.err.println("Uno de los parámetros no es un número");

    } catch (IllegalArgumentException e) {

        System.err.println("Error: " + e.getMessage());

    }
    }
}
