import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends JFrame {//123
    private Image hoop, basketballIcon;
    private GamePanel gamePanel;

    public Game(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setTitle("Basketball Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        hoop = new ImageIcon("basket.gif").getImage();
        basketballIcon = new ImageIcon("basketballIcon.png").getImage();

        gamePanel.setImages(basketballIcon, hoop);  // Обновление изображений в GamePanel
        add(gamePanel);
        gamePanel.requestFocusInWindow();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gamePanel.run();
            }
        });
    }
}

