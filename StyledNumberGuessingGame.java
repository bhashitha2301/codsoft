
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StyledNumberGuessingGame extends JFrame implements ActionListener {
    private final int MAX_ATTEMPTS = 10;
    private int numberToGuess;
    private int attemptsLeft;
    private int score;
    private JTextField guessField;
    private JLabel resultLabel, attemptsLabel, scoreLabel, titleLabel;
    private JButton submitButton;

    public StyledNumberGuessingGame() {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Number Guessing Game");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 245)); // light gray
        setLayout(new BorderLayout(10, 10));

        // Fonts
        Font titleFont = new Font("Arial", Font.BOLD, 22);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Title
        titleLabel = new JLabel("Guess a number between 1 and 100");
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Input field
        guessField = new JTextField(10);
        guessField.setFont(textFont);

        // Button
        submitButton = new JButton("Submit Guess");
        submitButton.setFont(textFont);
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.addActionListener(this);

        // Labels
        resultLabel = new JLabel("You have 10 attempts.");
        resultLabel.setFont(textFont);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        attemptsLabel = new JLabel();
        attemptsLabel.setFont(textFont);
        attemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(textFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel for components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 1, 10, 5));
        centerPanel.setBackground(new Color(245, 245, 245));

        centerPanel.add(titleLabel);
        centerPanel.add(guessField);
        centerPanel.add(submitButton);
        centerPanel.add(resultLabel);
        centerPanel.add(scoreLabel);

        add(centerPanel, BorderLayout.CENTER);
        resetGame();
        setVisible(true);
    }

    private void resetGame() {
        Random rand = new Random();
        numberToGuess = rand.nextInt(100) + 1;
        attemptsLeft = MAX_ATTEMPTS;
        resultLabel.setText("You have " + MAX_ATTEMPTS + " attempts.");
        guessField.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        String userInput = guessField.getText().trim();
        if (userInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
            return;
        }

        try {
            int guess = Integer.parseInt(userInput);
            attemptsLeft--;

            if (guess == numberToGuess) {
                score++;
                JOptionPane.showMessageDialog(this, "🎉 Correct! You guessed the number.");
                scoreLabel.setText("Score: " + score);
                int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play again?");
                if (playAgain == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(this, "❌ Out of attempts! The number was: " + numberToGuess);
                int playAgain = JOptionPane.showConfirmDialog(this, "Play again?");
                if (playAgain == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else {
                String hint = (guess < numberToGuess) ? "Too low!" : "Too high!";
                resultLabel.setText(hint + " Attempts left: " + attemptsLeft);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        new StyledNumberGuessingGame();
    }
}
