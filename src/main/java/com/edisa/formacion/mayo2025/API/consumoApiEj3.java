package com.edisa.formacion.mayo2025.API;

// Librerías para crear objetos que procesen documentos XML

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
// Para manejar nodos XML
import org.w3c.dom.*;
// Para leer datos de la API
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// Usamos colecciones
import java.util.HashMap;
import java.util.Map;


public class consumoApiEj3 {

    // URL del banco central Europeo
    private static final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    // Metodo para realizar la petición GET al API del BCE
    private static Map<String, Double> obtenerTasas() throws Exception {
        // Inicializa map para guardar tasas de cambio ("USD" => 1.09)
        Map<String, Double> tasas = new HashMap<>();
        // Creamos objeto URL
        URL url = new URL(ECB_URL);
        // Abrimos conexion
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        // Indicamos metodo GET
        conexion.setRequestMethod("GET");

        // Obtenemos los datos de la respuesta
        InputStream flujo = conexion.getInputStream();
        // Usamos el documentBuilder para procesar el XML, creando una instancia
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Convertimos el XML en un documento DOM
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(flujo);
        doc.getDocumentElement().normalize();

        // Buscamos todos los nodos Cube, que es donde tenemos las divisas
        NodeList lista = doc.getElementsByTagName("Cube");

        // Recorremos los nodos y los almacenamos en un objeto de la clase Node
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            // Nos aseguramos que cada nodo sea de tipo elemento y lo almacenamos en un objeto de la clase Element
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                // Si el nodo tiene los atributos currency y rate, los guardamos en el map
                if (elemento.hasAttribute("currency") && elemento.hasAttribute("rate")) {
                    String moneda = elemento.getAttribute("currency");
                    double tasa = Double.parseDouble(elemento.getAttribute("rate"));
                    tasas.put(moneda.toUpperCase(), tasa);
                }
            }
        }

        // El euro no aparece en el XML del BCE, lo añadimos manualmente al map
        tasas.put("EUR", 1.0);

        // Devolvemos el map con todas las divisas
        return tasas;
    }

    public static void main(String[] args) {

        // Verificamos que se pasen 2 o 3 argumentos (la cantidad puede ir vacía)
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("El programa debe recibir 3 argumentos. Ejemplo: USD JPY 5.25");

        }

        String divisaOrigen = args[0].toUpperCase();
        String divisaDestino = args[1].toUpperCase();
        // Si el tercer argumento (cantidad) existe, lo convertimos a Double. Si no, usamos 1.0 por defecto
        double cantidad = args.length == 3 ? Double.parseDouble(args[2]) : 1.0;

        // Nos aseguramos de que la cantidad pasada por argumento no sea ni 0 ni negativa
        try {
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("La cantidad debe ser un número mayor a 0.");
        }


        try {
            // Llamamos a nuestro metodo para obtener las tasas y guardamos el map
            Map<String, Double> tasas = obtenerTasas();

            // Si alguna de las divisas es el euro, la establecemos a 1.0. Si no, obtenemos su valor
            // Si no la encuentra en el map, la establecemos a -1.0
            double tasaOrigen = divisaOrigen.equals("EUR") ? 1.0 : tasas.getOrDefault(divisaOrigen, -1.0);
            double tasaDestino = divisaDestino.equals("EUR") ? 1.0 : tasas.getOrDefault(divisaDestino, -1.0);

            // Si alguna de las tasas es -1.0, se entiende que la divisa no existe en el XML y lanzamos mensaje de error
            if (tasaOrigen == -1.0) {
                System.err.println("Divisa origen no encontrada: " + divisaOrigen);
                return;
            }

            if (tasaDestino == -1.0) {
                System.err.println("Divisa destino no encontrada: " + divisaDestino);
                return;
            }

            double valorResultado = tasaDestino * (cantidad / tasaOrigen);

            System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidad, divisaOrigen, valorResultado, divisaDestino);

        } catch (Exception e) {
            System.err.println("Error al obtener los datos del BCE: " + e.getMessage());
        }
    }
}




