import java.awt.*;
import javax.swing.JFrame;

import javax.swing.*;

public class RiskFrame extends JFrame {
    private JPanel currentPanel;

    public RiskFrame(){
        initUI();
        this.setCurrentPanel(new ConfigPanel(this));
    }

    private void initUI() {
        pack();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        this.setTitle("RISK");
        this.setSize(1280, 750);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(((screen.width -  this.getSize().width)/2), (screen.height - this.getSize().height)/2);
    }

    public synchronized void setCurrentPanel(JPanel newPanel) {
        if (this.currentPanel != null) this.remove(currentPanel);
        repaint();
        this.add(newPanel);

        this.currentPanel = newPanel;
        this.setVisible(true);
    }

    public synchronized JPanel getCurrentPanel() {
        return this.currentPanel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            RiskFrame game = new RiskFrame();
            game.setVisible(true);
        });
    }
}
