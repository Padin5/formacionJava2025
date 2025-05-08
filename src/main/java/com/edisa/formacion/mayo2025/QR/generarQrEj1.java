package com.edisa.formacion.mayo2025.QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.*;
import java.nio.file.Path;


public class generarQrEj1 {
    public static void main(String[] args) {

        try {

            if (args.length != 2) {
                throw new IllegalArgumentException("La aplicación debe recibir dos parámetros");
            }

            String texto = args[0];
            String rutaSalida = args[1];

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 500, 500);

            Path path = new File(rutaSalida).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);

        } catch (WriterException e) {
            System.err.println("Error generando el QR");

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error generando el QR:");
        }
    }
}
