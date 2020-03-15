import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mylabel extends JLabel {
    mainWindow frame;
    playerWindow playerPanel;
    gameWindow gamePanel;
    String str;

    Mylabel(mainWindow frame, playerWindow panel, gameWindow gamePanel, String str, int x, int y, int width, int hight) {
        super(str, JLabel.CENTER);
        this.str = str;
        this.playerPanel = panel;
        this.gamePanel = gamePanel;
        this.frame = frame;

        setBounds(x, y, width, hight);
        setLayout(null);
        setForeground(Color.green);
        Font font = new Font(null, Font.BOLD, 24);
        setFont(font);

        addMouseListener(new mouse());
        frame.add(this);
    }

    class mouse extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (getText().equalsIgnoreCase("random")) {
                frame.repaint();
                playerPanel.randomInit();

                playerPanel.repaint();
                playerPanel.setVisible(true);
                frame.repaint();
                frame.start = new Mylabel(frame, playerPanel, gamePanel, "Start", 300, 320, 100, 100);
                frame.start.setEnabled(true);
            } else {
                if (getText().equalsIgnoreCase("start")) {
                    frame.random.setVisible(false);
                    frame.start.setVisible(false);

                    frame.remove(frame.random);
                    frame.remove(frame.start);

                    gamePanel = new gameWindow(0, 0, mainWindow.d.width / 2 - 5, mainWindow.d.height, playerPanel);
                    playerPanel.setGameWindow(gamePanel);
                    frame.getContentPane().add(gamePanel);

                    gamePanel.setVisible(true);

                    frame.repaint();
                }
            }
        }
    }
}
