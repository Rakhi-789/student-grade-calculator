import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GPACalculatorPart3 extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GPACalculatorPart3() {
        setTitle("Grade Calculator");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createSelectionPanel(), "Selection");
        mainPanel.add(createSchoolPanel(), "School");
        mainPanel.add(createUniversityPanel(), "University");

        add(mainPanel);
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.decode("#A0D3E8"));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("Select Your Grade Calculation", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        JButton schoolButton = createStyledButton("Calculate School Grade");
        JButton universityButton = createStyledButton("Calculate University Grade");

        schoolButton.addActionListener(e -> cardLayout.show(mainPanel, "School"));
        universityButton.addActionListener(e -> cardLayout.show(mainPanel, "University"));

        panel.add(schoolButton);
        panel.add(universityButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(64, 158, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JPanel createSchoolPanel() {
        JPanel panel = new JPanel(new GridLayout(15, 1));  // Increased rows to accommodate back button
        panel.setBackground(Color.decode("#B9FBC0"));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField[] marksFields = new JTextField[12];
        for (int i = 0; i < 12; i++) {
            panel.add(new JLabel("Enter Subject " + (i + 1) + " Marks:", JLabel.CENTER));
            marksFields[i] = new JTextField();
            panel.add(marksFields[i]);
        }

        JButton calculateButton = createStyledButton("Calculate Grade");
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        panel.add(calculateButton);
        panel.add(resultLabel);

        calculateButton.addActionListener(e -> {
            int totalMarks = 0;
            for (JTextField marksField : marksFields) {
                try {
                    int marks = Integer.parseInt(marksField.getText());
                    if (marks < 0 || marks > 100) {
                        JOptionPane.showMessageDialog(panel, "Marks should be between 0 and 100.");
                        return;
                    }
                    totalMarks += marks;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid marks.");
                    return;
                }
            }
            int averageMarks = totalMarks / 12;
            resultLabel.setText("Final Grade: " + calculateSchoolGrade(averageMarks));
        });

        panel.add(createBackButtonPanel());  // Add Back Button to School panel

        return panel;
    }

    private String calculateSchoolGrade(int marks) {
        if (marks >= 80) return "A+ (5.00)";
        if (marks >= 70) return "A (4.00)";
        if (marks >= 60) return "A- (3.50)";
        if (marks >= 50) return "B (3.00)";
        if (marks >= 40) return "C (2.00)";
        if (marks >= 33) return "D (1.00)";
        return "F (0.00)";
    }

    private JPanel createUniversityPanel() {
        JPanel panel = new JPanel(new GridLayout(12, 1));  // Increased rows to accommodate back button
        // panel.setBackground(Color.decode("#B9FBC0"));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField[] creditsFields = new JTextField[8];
        JTextField[] marksFields = new JTextField[8];

        for (int i = 0; i < 8; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 2));
            creditsFields[i] = new JTextField();
            marksFields[i] = new JTextField();
            rowPanel.add(new JLabel("Course " + (i + 1) + " Credits:"));
            rowPanel.add(creditsFields[i]);
            rowPanel.add(new JLabel("Marks:"));
            rowPanel.add(marksFields[i]);
            panel.add(rowPanel);
        }

        JButton calculateButton = createStyledButton("Calculate GPA");
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        panel.add(calculateButton);
        panel.add(resultLabel);

        calculateButton.addActionListener(e -> {
            double totalQualityPoints = 0;
            int totalCredits = 0;

            for (int i = 0; i < 8; i++) {
                try {
                    double credits = Double.parseDouble(creditsFields[i].getText());
                    int marks = Integer.parseInt(marksFields[i].getText());
                    if (marks < 0 || marks > 100) {
                        JOptionPane.showMessageDialog(panel, "Marks should be between 0 and 100.");
                        return;
                    }
                    if (credits < 1.5 || credits > 4) {
                        JOptionPane.showMessageDialog(panel, "Credits should be between 1.5 and 4.");
                        return;
                    }
                    double gradePoint = calculateUniversityGrade(marks);
                    totalQualityPoints += gradePoint * credits;
                    totalCredits += credits;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid credits and marks.");
                    return;
                }
            }
            double gpa = totalCredits > 0 ? totalQualityPoints / totalCredits : 0;
            if (gpa > 4.00) gpa = 4.00;
            resultLabel.setText("CGPA: " + String.format("%.2f", gpa));
        });

        panel.add(createBackButtonPanel());  // Add Back Button to University panel

        return panel;
    }

    private double calculateUniversityGrade(int marks) {
        if (marks >= 80) return 4.00;
        if (marks >= 75) return 3.75;
        if (marks >= 70) return 3.50;
        if (marks >= 65) return 3.25;
        if (marks >= 60) return 3.00;
        if (marks >= 55) return 2.75;
        if (marks >= 50) return 2.50;
        if (marks >= 45) return 2.25;
        if (marks >= 40) return 2.00;
        return 0.00;
    }

    private JPanel createBackButtonPanel() {
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusable(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));
        backPanel.add(backButton);
        return backPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GPACalculatorPart3().setVisible(true);
        });
    }
}
