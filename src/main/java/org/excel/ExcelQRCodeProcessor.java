package org.excel;

import com.google.zxing.WriterException;
import org.qrcode.QRCodeGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ExcelQRCodeProcessor {

    public static void processExcelFile(String inputFilePath , int columnIndex ) {
        try ( FileInputStream fileInputStream = new FileInputStream( inputFilePath );
             Workbook workbook = new XSSFWorkbook( fileInputStream ) ) {

            Sheet sheet = workbook.getSheetAt( 0 ); // Assuming first sheet

            for ( Row row : sheet ) {
                Cell cell = row.getCell( columnIndex );
                if ( cell != null && cell.getCellType() == CellType.STRING ) {
                    String cellText = cell.getStringCellValue();


                    try {
                        BufferedImage qrImage = QRCodeGenerator.generateQRCode( cellText , 150 , 150 );
                        File qrFile = new File( "temp_qr.png" );
                        ImageIO.write( qrImage, "PNG" , qrFile );
                        insertImageIntoCell( workbook , sheet , qrFile , row.getRowNum() , columnIndex );
                    } catch ( WriterException exception ) {
                        System.err.println( exception.getMessage() );
                    }
                }
            }

            // Save the modified Excel file
            try ( FileOutputStream fileOutputStream = new FileOutputStream( inputFilePath ) ) {
                workbook.write( fileOutputStream );
            }


            System.out.println( "QR Codes added successfully to the Excel file." );

        } catch ( IOException exception ) {
            System.err.println( exception.getMessage() );
        }
    }

    private static void insertImageIntoCell( Workbook workbook , Sheet sheet , File imageFile , int rowNum , int colNum ) throws IOException {
        InputStream inputStream = new FileInputStream( imageFile );
        byte[] bytes = inputStream.readAllBytes();
        int pictureIdx = workbook.addPicture( bytes , Workbook.PICTURE_TYPE_PNG );
        inputStream.close();

        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1( colNum );
        anchor.setRow1( rowNum );
        anchor.setCol2( colNum + 1 );
        anchor.setRow2( rowNum + 1 );

        ExcelCellResizer.resizeCell( sheet , rowNum , colNum , 150 , 150 );

        Picture pict = drawing.createPicture( anchor , pictureIdx );
        pict.resize();

    }

}