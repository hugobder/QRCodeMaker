package org;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.excel.ExcelQRCodeProcessor;

public class Main {
    public static void main( String[] args ) {
        ExcelQRCodeProcessor.processExcelFile( "CECAZ_Historique_Commandes_Dell.xlsx" , "CECAZ_Historique_Commandes_Dell.xlsx" , 2 );
        // modifier colum index A = 0 , B = 1 , etc
    }
}
