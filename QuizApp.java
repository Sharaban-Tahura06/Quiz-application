import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApp extends JFrame implements ActionListener {

    Question[] questions = {
        new Question("What is 2 + 2?",
            new String[]{"3", "4", "5", "6"}, 1),

        new Question("Which planet is closest to the Sun?",
            new String[]{"Earth", "Mars", "Mercury", "Venus"}, 2),

        new Question("What is the color of the sky?",
            new String[]{"Green", "Red", "Blue", "Yellow"}, 2),

        new Question("How many days are in a week?",
            new String[]{"5", "6", "8", "7"}, 3),

        new Question("What is the capital of France?",
            new String[]{"London", "Paris", "Rome", "Berlin"}, 1),

        new Question("What is 10 x 5?",
            new String[]{"40", "50", "60", "70"}, 1),

        new Question("How many months are in a year?",
            new String[]{"10", "11", "12", "13"}, 2),

        new Question("Which animal is known as King of the Jungle?",
            new String[]{"Tiger", "Elephant", "Lion", "Cheetah"}, 2),

        new Question("What is the boiling point of water?",
            new String[]{"90C", "95C", "100C", "110C"}, 2),

        new Question("How many sides does a triangle have?",
            new String[]{"2", "3", "4", "5"}, 1)
    };

    int currentQuestion = 0;
    int score = 0;

    JLabel questionLabel;
    JLabel questionNumber;
    JButton[] optionButtons = new JButton[4];
    JLabel scoreLabel;

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); // open in center of screen

        // --- TOP PANEL ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));

        questionNumber = new JLabel("Question 1 of 10", SwingConstants.CENTER);
        questionNumber.setFont(new Font("Arial", Font.BOLD, 13));
        questionNumber.setForeground(Color.WHITE);
        questionNumber.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(questionNumber, BorderLayout.NORTH);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // --- MIDDLE PANEL (Buttons) ---
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        buttonPanel.setBackground(Color.WHITE);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionButtons[i].setBackground(new Color(240, 240, 240));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            optionButtons[i].addActionListener(this);
            buttonPanel.add(optionButtons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);

        // --- BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 240));

        scoreLabel = new JLabel("Score: 0 / 10", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(scoreLabel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load first question
        loadQuestion();

        setVisible(true);
    }

    void loadQuestion() {
        Question q = questions[currentQuestion];

        questionNumber.setText("Question " + (currentQuestion + 1) + " of " + questions.length);
        questionLabel.setText("<html><div style='text-align:center'>" + q.questionText + "</div></html>");

        String[] labels = {"A.  ", "B.  ", "C.  ", "D.  "};
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(labels[i] + q.options[i]);
            optionButtons[i].setEnabled(true);
            optionButtons[i].setBackground(new Color(240, 240, 240));
            optionButtons[i].setForeground(Color.BLACK);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            if (e.getSource() == optionButtons[i]) {

                int correct = questions[currentQuestion].correctIndex;

                if (i == correct) {
                    // Correct answer - green
                    optionButtons[i].setBackground(Color.GREEN);
                    score++;
                } else {
                    // Wrong answer - red
                    optionButtons[i].setBackground(Color.RED);
                    optionButtons[i].setForeground(Color.WHITE);
                    // Show correct answer in green
                    optionButtons[correct].setBackground(Color.GREEN);
                }

                // Disable all buttons
                for (JButton btn : optionButtons) {
                    btn.setEnabled(false);
                }

                scoreLabel.setText("Score: " + score + " / " + questions.length);

                // Wait 1 second then go next
                Timer timer = new Timer(1000, event -> nextQuestion());
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    void nextQuestion() {
        currentQuestion++;
        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {
        String grade;
        String message;

        if (score == questions.length) {
            grade = "PERFECT!";
            message = "Outstanding! You got everything right!";
        } else if (score >= 7) {
            grade = "EXCELLENT!";
            message = "Great job! You did very well!";
        } else if (score >= 5) {
            grade = "GOOD!";
            message = "Good effort! Keep practicing!";
        } else {
            grade = "TRY AGAIN!";
            message = "Don't give up! Try once more!";
        }

        int choice = JOptionPane.showConfirmDialog(
            this,
            grade + "\n\n" + message +
            "\n\nYour Score: " + score + " out of " + questions.length +
            "\n\nDo you want to Play Again?",
            "Quiz Finished!",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            currentQuestion = 0;
            score = 0;
            scoreLabel.setText("Score: 0 / " + questions.length);
            loadQuestion();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}
