package org.qrcode;

import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

    /**
     * Generates a QR code as a BufferedImage.
     *
     * @param text The text to encode into the QR code.
     * @param width The width of the QR code.
     * @param height The height of the QR code.
     * @return A BufferedImage containing the generated QR code.
     * @throws WriterException If an error occurs during encoding.
     */
    public static BufferedImage generateQRCode( String text , int width , int height ) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode( text , BarcodeFormat.QR_CODE , width , height );
        return MatrixToImageWriter.toBufferedImage( bitMatrix );
    }
}
