package Part2;
import Part1.Horse;
import Part1.Race;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;

class MainGUI {
    private static Race race; // Declare race as a static field

    public static void main(String[] args) {
        JFrame frame = new JFrame("Horse Racing Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2)); // Adjusted grid layout for more inputs
        frame.add(panel, BorderLayout.NORTH);

        // Race Length Input
        JLabel raceLengthLabel = new JLabel("Race Length:");
        JTextField raceLengthField = new JTextField(10);
        panel.add(raceLengthLabel);
        panel.add(raceLengthField);

        // Number of Lanes Input
        JLabel lanesLabel = new JLabel("Number of Lanes:");
        JTextField lanesField = new JTextField(10);
        panel.add(lanesLabel);
        panel.add(lanesField);

        // Horse Name Input
        JLabel horseNameLabel = new JLabel("Horse Name:");
        JTextField horseNameField = new JTextField(10);
        panel.add(horseNameLabel);
        panel.add(horseNameField);

        // Horse Symbol Input
        JLabel horseSymbolLabel = new JLabel("Horse Symbol:");
        JTextField horseSymbolField = new JTextField(1); // Text field for custom letter
        panel.add(horseSymbolLabel);
        panel.add(horseSymbolField);

        // Horse Accessory Input (moved above confidence)
        JLabel horseAccessoryLabel = new JLabel("Horse Accessory:");
        JComboBox<String> horseAccessoryComboBox = new JComboBox<>(new String[] {"None", "Rocket Horseshoes", "Lifeline Lasso", "Hoofshield"});
        panel.add(horseAccessoryLabel);
        panel.add(horseAccessoryComboBox);

        // Horse Confidence Input
        JLabel horseConfidenceLabel = new JLabel("Horse Confidence (0-1):");
        JTextField horseConfidenceField = new JTextField(10);
        panel.add(horseConfidenceLabel);
        panel.add(horseConfidenceField);

        // Lane Selection
        JLabel laneLabel = new JLabel("Select Lane:");
        JComboBox<Integer> laneComboBox = new JComboBox<>();
        panel.add(laneLabel);
        panel.add(laneComboBox);

        // Initialize Race Button
        JButton initializeRaceButton = new JButton("Initialize Race");
        panel.add(initializeRaceButton);

        // Add Horse Button
        JButton addHorseButton = new JButton("Add Horse");
        panel.add(addHorseButton);

        // Start Race Button
        JButton startRaceButton = new JButton("Start Race");
        panel.add(startRaceButton);

        // Add the "View Stats" button
        JButton viewStatsButton = new JButton("View Stats");
        panel.add(viewStatsButton);

        // Action for "View Stats" button
        viewStatsButton.addActionListener(e -> {
            if (race != null) {
                race.displayStats(); // Call the displayStats method to print stats to the console
            } else {
                JOptionPane.showMessageDialog(frame, "No race has been run yet.");
            }
        });

        // Race Output Area
        JTextArea raceOutputArea = new JTextArea();
        raceOutputArea.setEditable(false);
        raceOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(raceOutputArea);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Redirect System.out to JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(raceOutputArea));
        System.setOut(printStream);

        // Initialize Race Button Action
        initializeRaceButton.addActionListener(e -> {
            String raceLength = raceLengthField.getText();
            String lanes = lanesField.getText();

            if (raceLength.isEmpty() || lanes.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter valid inputs for race length and lanes.");
                return;
            }

            try {
                int raceLengthValue = Integer.parseInt(raceLength);
                int lanesValue = Integer.parseInt(lanes);

                if (raceLengthValue <= 0 || lanesValue <= 0) {
                    JOptionPane.showMessageDialog(frame, "Race length and lanes must be positive numbers.");
                } else if (lanesValue > 5) {
                    JOptionPane.showMessageDialog(frame, "The number of lanes cannot exceed 5.");
                } else {
                    race = new Race(raceLengthValue, lanesValue); // Initialize the race object

                    // Update the lane selection dropdown without clearing horse-related fields
                    laneComboBox.removeAllItems();
                    for (int i = 1; i <= lanesValue; i++) {
                        laneComboBox.addItem(i);
                    }

                    JOptionPane.showMessageDialog(frame, "Race initialized with race length: " + raceLengthValue + " and " + lanesValue + " lanes.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for race length and lanes.");
            }
        });

        // Add Horse Button Action
        addHorseButton.addActionListener(e -> {
            if (race == null) {
                JOptionPane.showMessageDialog(frame, "Please initialize the race first.");
                return;
            }

            Integer selectedLane = (Integer) laneComboBox.getSelectedItem();
            if (selectedLane == null) {
                JOptionPane.showMessageDialog(frame, "Please select a lane.");
                return;
            }

            String horseName = horseNameField.getText();
            String horseSymbol = horseSymbolField.getText();
            String horseAccessory = (String) horseAccessoryComboBox.getSelectedItem(); // Get the selected accessory
            String horseConfidence = horseConfidenceField.getText();

            if (horseName.isEmpty() || horseSymbol.isEmpty() || horseConfidence.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                return;
            }

            if (horseSymbol.length() != 1) {
                JOptionPane.showMessageDialog(frame, "Horse symbol must be a single character.");
                return;
            }

            try {
                double confidenceValue = Double.parseDouble(horseConfidence);

                if (confidenceValue < 0 || confidenceValue > 1) {
                    JOptionPane.showMessageDialog(frame, "Confidence must be between 0 and 1.");
                    return;
                }

                Horse horse = race.getHorse(selectedLane);
                if (horse == null) {
                    // Add a new horse
                    horse = new Horse(horseSymbol.charAt(0), horseName, confidenceValue, horseAccessory);
                } else {
                    // Update the existing horse
                    horse.setSymbol(horseSymbol.charAt(0));
                    horse.setConfidence(confidenceValue);
                    horse.setAccessory(horseAccessory); // Save the selected accessory
                }

                race.addHorse(horse, selectedLane); // Add or update the horse in the selected lane

                JOptionPane.showMessageDialog(frame, "Horse added/updated in lane " + selectedLane + ": " + horse.getName());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for confidence.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        // Update Horse Details Dynamically When Lane is Selected
        laneComboBox.addActionListener(e -> {
            if (race == null) {
                JOptionPane.showMessageDialog(frame, "Please initialize the race first.");
                return;
            }

            Integer selectedLane = (Integer) laneComboBox.getSelectedItem();
            if (selectedLane == null) {
                return; // No lane selected
            }

            Horse horse = race.getHorse(selectedLane); // Get the horse in the selected lane
            if (horse == null) {
                // Clear the input fields if no horse is in the selected lane
                horseNameField.setText("");
                horseSymbolField.setText("");
                horseConfidenceField.setText("");
                horseAccessoryComboBox.setSelectedIndex(0); // Default to "None"
            } else {
                // Populate the input fields with the horse's details
                horseNameField.setText(horse.getName());
                horseSymbolField.setText(String.valueOf(horse.getSymbol()));
                horseConfidenceField.setText(String.valueOf(horse.getConfidence()));
                horseAccessoryComboBox.setSelectedItem(horse.getAccessory()); // Update the accessory dropdown
            }
        });

        // Start Race Button Action
        startRaceButton.addActionListener(e -> {
            if (race == null) {
                JOptionPane.showMessageDialog(frame, "Please initialize the race first.");
                return;
            }

            new Thread(() -> {
                race.startRace(); // Let the Race class handle the race logic and output
            }).start();
        });

        frame.setVisible(true);
    }
}


// Custom OutputStream to redirect System.out to JTextArea
class TextAreaOutputStream extends OutputStream {
    private JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        // Append only printable characters
        if (b != '\r') { // Ignore carriage return
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll to the bottom
        }
    }
}

