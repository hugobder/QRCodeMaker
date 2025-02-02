package org.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelCellResizer {
    /**
     * Resizes a specific Excel cell to a given width and height in pixels.
     *
     * @param sheet     The sheet containing the cell.
     * @param rowIndex  The row index (0-based).
     * @param colIndex  The column index (0-based).
     * @param widthPx   The desired width in pixels.
     * @param heightPx  The desired height in pixels.
     */
    public static void resizeCell( Sheet sheet , int rowIndex , int colIndex , int widthPx , int heightPx ) {
        // Convert width from pixels to Excel column width units
        int excelColumnWidth = ( int ) ( widthPx / 7.0 * 256 ); // Approximate conversion
        sheet.setColumnWidth( colIndex , excelColumnWidth );

        // Get or create the row
        Row row = sheet.getRow( rowIndex );
        if ( row == null ) {
            row = sheet.createRow( rowIndex );
        }

        // Convert height from pixels to Excel row height units
        short excelRowHeight = ( short ) ( heightPx * 20 ); // Approximate conversion
        row.setHeight( excelRowHeight );
    }
}
