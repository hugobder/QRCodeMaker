package org.excel;

import com.google.zxing.WriterException;
import org.qrcode.QRCodeGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ExcelQRCodeProcessor {

    /**
     * Processes an Excel file by generating QR codes for the values in a specific column.
     *
     * @param inputFilePath The path of the Excel file to process.
     * @param columnIndex    The index of the column to generate QR codes for (0-based).
     */
    public static void processExcelFile(String inputFilePath , int columnIndex ) {
        try ( FileInputStream fileInputStream = new FileInputStream( inputFilePath );
             Workbook workbook = new XSSFWorkbook( fileInputStream ) ) {

            Sheet sheet = workbook.getSheetAt( 0 ); // Assuming first sheet

            File qrFile = null;
            for ( Row row : sheet ) {
                Cell cell = row.getCell( columnIndex );
                if ( cell != null && cell.getCellType() == CellType.STRING ) {
                    String cellText = cell.getStringCellValue();


                    try {
                        BufferedImage qrImage = QRCodeGenerator.generateQRCode( cellText, 150, 150 );
                        qrFile = new File("temp_qr.png");
                        ImageIO.write(qrImage, "PNG", qrFile);
                        insertImageIntoCell( workbook, sheet, qrFile, row.getRowNum(), columnIndex );
                    } catch (WriterException exception) {
                        System.err.println(exception.getMessage());
                    }
                }
            }

            if ( qrFile != null ) {
                if ( qrFile.delete() ) {
                    System.err.println("Failed to delete temporary QR code image file.");
                }
            }

            // Save the modified Excel file
            try (FileOutputStream fileOutputStream = new FileOutputStream(inputFilePath)) {
                workbook.write(fileOutputStream);
            }


            System.out.println("QR Codes added successfully to the Excel file.");

        } catch ( IOException exception ) {
            System.err.println( exception.getMessage() );
        }
    }

    /**
     * Inserts an image into a specific cell in an Excel sheet.
     *
     * @param workbook  The workbook containing the sheet.
     * @param sheet     The sheet containing the cell.
     * @param imageFile The image file to insert.
     * @param rowNum    The row index (0-based).
     * @param colNum    The column index (0-based).
     * @throws IOException If an I/O error occurs.
     */
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

        ExcelUtility.resizeCell( sheet , rowNum , colNum , 150 , 150 );

        Picture pict = drawing.createPicture( anchor , pictureIdx );
        pict.resize();

    }

}