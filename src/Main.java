import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Create Frame
        JFrame frame = new JFrame(
                "Campus Cafeteria Management System"
        );

        frame.setSize(900, 600);

        frame.setLayout(new BorderLayout());

        // Close Operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =========================
        // Create Tabbed Pane
        // =========================
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Tabs
        tabbedPane.addTab(
                "Food Items",
                new FoodItemPanel()
        );

        tabbedPane.addTab(
                "Orders",
                new OrderPanel()
        );

        // =========================
        // Add Components
        // =========================
        frame.setJMenuBar(
                new AppMenuBar(frame)
        );

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Center Frame
        frame.setLocationRelativeTo(null);

        // Show Frame
        frame.setVisible(true);
    }
}