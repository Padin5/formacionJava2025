package com.edisa.formacion.mayo2025.areas;

public class calculoAreasEj2 {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Debe especificar una figura. Si el primer argumento es circulo," +
                        "introduzca su radio. Si es rectángulo, introduzca ancho y alto");
            }

            String figura = args[0].toLowerCase();

            switch (figura) {
                case "rectángulo":
                    if (args.length < 3) {
                        throw new IllegalArgumentException("Faltan argumentos para 'rectángulo'. Ejemplo: rectángulo 5.0 4.0");
                    }

                    String anchoString = args[1];
                    String altoString = args[2];

                    if (anchoString.isEmpty()) {
                        throw new IllegalArgumentException("El argumento 'ancho' está vacío.");
                    }

                    if (altoString.isEmpty()) {
                        throw new IllegalArgumentException("El argumento 'alto' está vacío.");
                    }

                    double ancho = Double.parseDouble(anchoString);
                    double alto = Double.parseDouble(altoString);

                    if (ancho < 0) {
                        throw new IllegalArgumentException("Valor no válido. Debe ser mayor a 0");
                    }

                    if (alto < 0) {
                        throw new IllegalArgumentException("Valor no válido. Debe ser mayor a 0");
                    }

                    double areaRectangulo = ancho * alto;
                    System.out.println("El área del rectángulo es: " + areaRectangulo);
                    break;

                case "circulo":
                    if (args.length < 2) {
                        throw new IllegalArgumentException("Falta el argumento del radio. Ejemplo: circulo 3.0");
                    }

                    String radioString = args[1];

                    if (radioString.isEmpty()) {
                        throw new IllegalArgumentException("El argumento 'radio' está vacío.");
                    }

                    double radio = Double.parseDouble(radioString);

                    if (radio < 0) {
                        throw new IllegalArgumentException("Valor no válido. Debe ser mayor a 0");
                    }

                    double areaCirculo = Math.PI * radio * radio;
                    System.out.println("El área del círculo es: " + areaCirculo);
                    break;

                default:
                    throw new IllegalArgumentException("Figura no reconocida: '" + figura + "'. Debe ser 'rectángulo' o 'circulo'.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

