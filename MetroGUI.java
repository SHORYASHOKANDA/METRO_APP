import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MetroGUI extends JFrame {

    private Graph_M metroMap;
    private JTextArea outputArea;
    private JPanel mainPanel;
    private ImageIcon imageIcon;

    public MetroGUI() {
        // Set up the frame
        setTitle("Delhi Metro Map");
        setSize(400, 700); // Simulating a mobile screen size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the graph
        metroMap = new Graph_M();
        Graph_M.Create_Metro_Map(metroMap);

        // Load the image
        try {
            imageIcon = new ImageIcon("C:/Users/ASUS/Downloads/image.png"); // Make sure to replace this with the correct path
            JLabel label = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(400, 100, Image.SCALE_SMOOTH)));
            add(label, BorderLayout.NORTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Main panel with a background image or color
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255, 120)); // Semi-transparent white background
        add(mainPanel, BorderLayout.CENTER);

        // Create the output area with custom font
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Sans Serif", Font.BOLD, 16));
        outputArea.setForeground(new Color(50, 50, 50));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons with a translucent background
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10)); // with gaps
        buttonPanel.setBackground(new Color(0, 0, 0, 65)); // Semi-transparent black background

        // Create buttons with custom fonts and colors
        JButton[] buttons = {
            new JButton("List Stations"),
            new JButton("Display Metro Map"),
            new JButton("Shortest Distance"),
            new JButton("Shortest Time"),
            new JButton("Exit")
        };

        for (JButton button : buttons) {
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(30, 30, 30));
            button.setFont(new Font("Arial", Font.BOLD, 12));
            buttonPanel.add(button);
            button.addActionListener(new ButtonListener(button.getText()));
        }

        // Add button panel to the frame
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private class ButtonListener implements ActionListener {
        private String command;

        public ButtonListener(String command) {
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (command) {
                case "List Stations":
                    listStations();
                    break;
                case "Display Metro Map":
                    displayMap();
                    break;
                case "Shortest Distance":
                    calculateShortestDistance();
                    break;
                case "Shortest Time":
                    calculateShortestTime();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        }
    }

    private void listStations() {
        outputArea.setText("");
        metroMap.display_Stations();
    }

    private void displayMap() {
        outputArea.setText("");
        metroMap.display_Map();
    }

    private void calculateShortestDistance() {
        String source = JOptionPane.showInputDialog("Enter Source Station:");
        String destination = JOptionPane.showInputDialog("Enter Destination Station:");
        if (source != null && destination != null) {
            HashMap<String, Boolean> processed = new HashMap<>();
            if (!metroMap.containsVertex(source) || !metroMap.containsVertex(destination) || !metroMap.hasPath(source, destination, processed)) {
                outputArea.setText("Invalid stations. Please try again.");
            } else {
                int distance = metroMap.dijkstra(source, destination, false);
                outputArea.setText("Shortest Distance from " + source + " to " + destination + " is: " + distance + " KM");
            }
        }
    }

    private void calculateShortestTime() {
        String source = JOptionPane.showInputDialog("Enter Source Station:");
        String destination = JOptionPane.showInputDialog("Enter Destination Station:");
        if (source != null && destination != null) {
            HashMap<String, Boolean> processed = new HashMap<>();
            if (!metroMap.containsVertex(source) || !metroMap.containsVertex(destination) || !metroMap.hasPath(source, destination, processed)) {
                outputArea.setText("Invalid stations. Please try again.");
            } else {
                int time = metroMap.dijkstra(source, destination, true);
                outputArea.setText("Shortest Time from " + source + " to " + destination + " is: " + time / 60 + " minutes");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MetroGUI().setVisible(true);
            }
        });
    }
}
