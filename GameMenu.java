import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameMenu extends JFrame {
    private JButton playButton;
    private JButton continueButton;
    private JButton exitButton;
    private GamePanel gamePanel;

    public GameMenu() {
        setTitle("Basketball Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        playButton = new JButton("Play");
        playButton.setBounds(100, 20, 100, 50);
        add(playButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(100, 80, 100, 50);
        add(continueButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(100, 140, 100, 50);
        add(exitButton);

        gamePanel = new GamePanel(new ImageIcon("ball.png").getImage(), new ImageIcon("hoop.png").getImage());
        gamePanel.setBounds(0, 0, 500, 500);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game(gamePanel);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gamePanel != null) {
                    System.out.println("kITR");
                    gamePanel.loadGameState("NBA_FILE.ser");
                    System.out.println(gamePanel.lives);
                    new Game(gamePanel);
                    System.out.println(gamePanel.makes);

                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Добавляем обработчик события закрытия окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gamePanel != null) {
                     // Сохраняем состояние при закрытии окна
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMenu();
            }
        });
    }
}