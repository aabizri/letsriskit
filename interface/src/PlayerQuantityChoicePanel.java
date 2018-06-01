import javax.swing.*;
import java.awt.*;

public class PlayerQuantityChoicePanel extends Panel{

    private final JButton solo = new JButton("1 joueur (contre l'IA)");
    private final JButton duo = new JButton("2 joueurs");
    private final JButton trio = new JButton("3 joueurs");
    private final JButton quator = new JButton("4 joueurs");
    private final JButton quintet = new JButton("5 joueurs");
    private final JButton sextuor = new JButton("6 joueurs");
    private final JButton[] buttonsNumberPlayers = {solo, duo, trio, quator, quintet, sextuor};

    private final RiskFrame parentFrame;

    public PlayerQuantityChoicePanel(RiskFrame parentFrame) {
        this.parentFrame = parentFrame;

        this.initBoardOOF();

        Font font = new Font("Times Roman", Font.BOLD | Font.ITALIC, 35);
        JPanel selectNbPlayers = new JPanel();

        for(int i = 0 ; i < 6 ; i++){
            add(selectNbPlayers.add(buttonsNumberPlayers[i]));
            buttonsNumberPlayers[i].setBackground(new Color(0,0,0,0));
            buttonsNumberPlayers[i].setContentAreaFilled(false);
            buttonsNumberPlayers[i].setBorderPainted(false);
            buttonsNumberPlayers[i].setForeground(Color.WHITE);
            buttonsNumberPlayers[i].setFont(font);
        }
        this.setVisible(true);

        for(int i = 0 ; i < 6 ; i++){
            final int playerQuantity = i+1;
            buttonsNumberPlayers[i].addActionListener(e -> parentFrame.setCurrentPanel(new PlayerNamesPanel(parentFrame,playerQuantity)));
        }
    }

    public void initBoardOOF(){
        this.setBackgroundFile("interface/src/ressources/risk_flou.jpeg");
    }
}
