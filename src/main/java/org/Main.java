package org;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Main {
    public static void main( String[] args ) {
        String text = "Hello, QR Code!"; // The text to encode
        String filePath = "qrcode.png";  // Output file

        int width = 300;  // QR code width
        int height = 300; // QR code height

        try {
            // Create QR Code Writer
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            // Convert to BufferedImage
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Save QR Code as an image file
            ImageIO.write(qrImage, "PNG", new File(filePath));

            System.out.println("QR Code saved as " + filePath);
        } catch (WriterException | java.io.IOException e) {
            System.out.println( e.getMessage() );
        }
    }
}
