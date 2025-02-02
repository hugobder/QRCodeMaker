package org.UI;

import org.excel.ExcelQRCodeProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class View extends JFrame {

    private JTextField filePathField;
    private JTextField columnIndexField;
    private JLabel statusLabel;

    public View() {
        setTitle( "Excel QR Code Generator" );
        setSize( 400, 200 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new GridLayout( 4 , 1 ) );

        // File Path Input
        JPanel filePanel = new JPanel();
        filePathField = new JTextField( 20 );
        JButton browseButton = new JButton( "Browse..." );
        filePanel.add( new JLabel( "Excel File:" ) );
        filePanel.add( filePathField );
        filePanel.add( browseButton );
        add( filePanel );

        // Column Index Input
        JPanel columnPanel = new JPanel();
        columnIndexField = new JTextField( 5 );
        columnPanel.add( new JLabel("Column Index:") );
        columnPanel.add( columnIndexField );
        add( columnPanel );

        // Process Button
        JButton processButton = new JButton( "Generate QR Codes" );
        add(processButton);

        // Status Label
        statusLabel = new JLabel( "Status: Waiting for input..." );
        add(statusLabel);

        // Browse Button Action
        browseButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        } );

        // Process Button Action
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathField.getText();
                String columnIndexText = columnIndexField.getText();

                if ( filePath.isEmpty() || columnIndexText.isEmpty() ) {
                    statusLabel.setText("Error: Please enter all fields.");
                    return;
                }

                try {
                    int columnIndex = Integer.parseInt( columnIndexText );
                    statusLabel.setText("Processing...");
                    ExcelQRCodeProcessor.processExcelFile( filePath , columnIndex );
                } catch ( NumberFormatException ex ) {
                    statusLabel.setText( "Error: Column index must be a number." );
                }
            }
        });
    }
}
