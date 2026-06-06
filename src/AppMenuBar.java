import javax.swing.*;
import java.awt.event.*;

public class AppMenuBar extends JMenuBar {

    // Constructor
    public AppMenuBar(JFrame frame) {

        // =========================
        // File Menu
        // =========================
        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exitItem);

        // =========================
        // Help Menu
        // =========================
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");

        helpMenu.add(aboutItem);

        // Add Menus to MenuBar
        add(fileMenu);
        add(helpMenu);

        // =========================
        // Exit Event
        // =========================
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // =========================
        // About Event
        // =========================
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Campus Cafeteria Management System\n" +
                                "Developed by JVM Junkies",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
}