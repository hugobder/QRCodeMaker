package org;

import javax.swing.*;

import org.UI.View;

public class Main {
    public static void main( String[] args ) {
        SwingUtilities.invokeLater( () -> {
            View view = new View();
            view.setVisible( true );
            view.setLocationRelativeTo( null );
        } );
    }
}
