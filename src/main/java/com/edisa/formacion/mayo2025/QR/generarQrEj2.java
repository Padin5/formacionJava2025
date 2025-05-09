package com.edisa.formacion.mayo2025.QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class generarQrEj2 {
    public static void main(String[] args) {

        try {

            if (args.length != 3) {
                throw new IllegalArgumentException("La aplicación debe recibir tres parámetros");
            }


            String texto = args[0];
            String rutaSalida = args[1];
            String formatoSalida = args[2].toUpperCase();


            if (!formatoSalida.equals("QR_CODE") && !formatoSalida.equals("EAN_13") && !formatoSalida.equals("CODABAR")
                    && !formatoSalida.equals("EAN_8")) {
                System.err.println("Formato desconocido. Debe introducir alguno de los siguientes: 'QR_CODE' , 'EAN_13 , '" +
                        "'CODABAR' , 'EAN_8'");
            }

            BarcodeFormat formato = BarcodeFormat.valueOf(formatoSalida);

            if (formato == BarcodeFormat.EAN_8 && !texto.matches("\\d{7}")) {
                System.err.println("EAN_8 requiere un texto de 8 dígitos numéricos.");
                return;
            }

            if (formato == BarcodeFormat.EAN_13 && !texto.matches("\\d{12}")) {
                System.err.println("EAN_13 requiere un texto de 12 dígitos numéricos.");
                return;
            }

            BitMatrix bitMatrix = new MultiFormatWriter().encode(texto, formato, 500, 500);
            FileOutputStream outputStream = new FileOutputStream(new File(rutaSalida));
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", outputStream);
            System.out.println("Código de barras generado con éxito en: " + rutaSalida);

        } catch (WriterException | IOException e) {
            System.err.println("Error al generar el código: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}




