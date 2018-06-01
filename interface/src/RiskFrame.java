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

        this.setTitle("RISK");
        this.setLocationRelativeTo(null);
        this.setSize(1280, 750);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
