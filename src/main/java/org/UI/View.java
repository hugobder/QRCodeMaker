package org.UI;

import org.excel.ExcelQRCodeProcessor;
import org.excel.ExcelUtility;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.excel.ExcelUtility.convertIndex;

public class View extends JFrame {

    private JTextField filePathField;
    private JTextField columnIndexField;
    private JLabel statusLabel;

    public View() {
        setTitle( "Excel QR Code Generator" );
        setSize( 500 , 300 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new GridLayout( 4 , 1 ) );

        // File Path Input
        JPanel filePanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        filePathField = new JTextField( 20 );
        JButton browseButton = new JButton( "Browse..." );
        filePanel.add( new JLabel( "Excel File:" ) );
        filePanel.add( filePathField );
        filePanel.add( browseButton );
        add( filePanel );

        // Column Index Input
        JPanel columnPanel = new JPanel();
        columnIndexField = new JTextField( 4 );
        columnPanel.add( new JLabel( "Column Index:" ) );
        columnPanel.add( columnIndexField );
        add( columnPanel );

        // Process Button
        JPanel processPanel = new JPanel();
        JButton processButton = new JButton( "Generate QR Codes" );
        processButton.setPreferredSize(new Dimension(200, 50)); // Set button size
        processPanel.setPreferredSize(new Dimension(250, 60)); // Set panel size
        processPanel.add( processButton );
        add( processPanel );

        // Status Label
        statusLabel = new JLabel( "Status: Waiting for input..." );
        add( statusLabel );

        // Browse Button Action
        browseButton.addActionListener( event -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog( null );
            if ( result == JFileChooser.APPROVE_OPTION ) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText( selectedFile.getAbsolutePath() );
            }
        });

        // Process Button Action
        processButton.addActionListener( event -> {
            String filePath = filePathField.getText();
            String columnIndexText = ExcelUtility.convertIndex( columnIndexField.getText() );

            if ( filePath.isEmpty() || columnIndexText.isEmpty() ) {
                statusLabel.setText( "Error: Please enter all fields." );
                return;
            }

            try {
                int columnIndex = Integer.parseInt( columnIndexText );
                statusLabel.setText( "Processing..." );
                ExcelQRCodeProcessor.processExcelFile( filePath , columnIndex );
                statusLabel.setText( "Done !" );
            } catch ( NumberFormatException ex ) {
                statusLabel.setText( "Error: Wrong column index." );
            }
        });
    }
}
