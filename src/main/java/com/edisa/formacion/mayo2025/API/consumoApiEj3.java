package com.edisa.formacion.mayo2025.API;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class consumoApiEj3 {


            public static void main(String[] args) {

                final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
                if (args.length < 2 || args.length > 3) {
                    System.out.println("Uso: java ConversorDivisas <divisa_origen> <divisa_destino> [cantidad]");
                    System.out.println("Ejemplo: java ConversorDivisas USD JPY 100");
                    return;
                }

                String divisaOrigen = args[0].toUpperCase();
                String divisaDestino = args[1].toUpperCase();
                double cantidad = 1.0;

                if (args.length == 3) {
                    try {
                        cantidad = Double.parseDouble(args[2]);
                        if (cantidad <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("La cantidad debe ser un número mayor a 0.");
                        return;
                    }
                }

                try {
                    Map<String, Double> tasas = obtenerTasas();

                    double tasaOrigen = divisaOrigen.equals("EUR") ? 1.0 : tasas.getOrDefault(divisaOrigen, -1.0);
                    double tasaDestino = divisaDestino.equals("EUR") ? 1.0 : tasas.getOrDefault(divisaDestino, -1.0);

                    if (tasaOrigen == -1.0) {
                        System.err.println("Divisa origen no encontrada: " + divisaOrigen);
                        return;
                    }

                    if (tasaDestino == -1.0) {
                        System.err.println("Divisa destino no encontrada: " + divisaDestino);
                        return;
                    }

                    double euros = cantidad / tasaOrigen;
                    double resultado = euros * tasaDestino;

                    System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidad, divisaOrigen, resultado, divisaDestino);

                } catch (Exception e) {
                    System.err.println("Error al obtener los datos del BCE: " + e.getMessage());
                }
            }

            private static Map<String, Double> obtenerTasas() throws Exception {
                Map<String, Double> tasas = new HashMap<>();

                URL url = new URL(ECB_URL);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");

                InputStream flujo = conexion.getInputStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(flujo);
                doc.getDocumentElement().normalize();

                NodeList lista = doc.getElementsByTagName("Cube");

                for (int i = 0; i < lista.getLength(); i++) {
                    Node nodo = lista.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;
                        if (elemento.hasAttribute("currency") && elemento.hasAttribute("rate")) {
                            String moneda = elemento.getAttribute("currency");
                            double tasa = Double.parseDouble(elemento.getAttribute("rate"));
                            tasas.put(moneda.toUpperCase(), tasa);
                        }
                    }
                }

                tasas.put("EUR", 1.0); // El euro no aparece en el XML, lo añadimos manualmente

                return tasas;
            }
        }




