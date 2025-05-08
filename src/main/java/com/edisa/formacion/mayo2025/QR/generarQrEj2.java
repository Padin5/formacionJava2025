package com.edisa.formacion.mayo2025.QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class generarQrEj2 {
    public static void main(String[] args) {

    try {

        if (args.length != 3) {
            throw new IllegalArgumentException("La aplicación debe recibir dos parámetros");
        }


        String texto = args[0];
        String rutaSalida = args[1];
        String formatoSalida = args[2].toUpperCase();
        BarcodeFormat formato = BarcodeFormat.valueOf(formatoSalida);


            if (formato == BarcodeFormat.EAN_13 && !texto.matches("\\d{12}")) {
                System.err.println("EAN_13 requiere un texto de 12 dígitos numéricos.");
                return;
            }


            File archivoOriginal = new File(rutaSalida);
            File carpetaFormato = new File(archivoOriginal.getParent(), formatoSalida);

            File archivoSalida = new File(carpetaFormato, archivoOriginal.getName());
            Path path = archivoSalida.toPath();

            BitMatrix bitMatrix = new MultiFormatWriter().encode(texto, formato, 500, 500);
            MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);

            System.out.println("Código de barras generado en: " + path.toAbsolutePath());

        } catch (WriterException | IOException e) {
            System.err.println("Error al generar el código: " + e.getMessage());
        }
    }
}




