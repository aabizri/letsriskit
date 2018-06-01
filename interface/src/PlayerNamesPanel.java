import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerNamesPanel extends Panel {
    private RiskFrame parentFrame;

    private JButton back = new JButton("<");
    private JButton next = new JButton(">");
    private Font font = new Font("Times Roman", Font.BOLD, 30);
    private String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private ArrayList<Player> allPlayers = new ArrayList<>();

    //Tous les JTextArea
    private JTextField name1 = new JTextField();
    private JTextField name2 = new JTextField();
    private JTextField name3 = new JTextField();
    private JTextField name4 = new JTextField();
    private JTextField name5 = new JTextField();
    private JTextField name6 = new JTextField();
    private JTextField[] names = {name1, name2, name3, name4, name5, name6};

    public PlayerNamesPanel(RiskFrame parentFrame, int numberOfPlayers) {


        for (int i = 0; i < numberOfPlayers; i++) {

            JLabel playerNameTitle = new JLabel("<html>" + tabulation + "Nom du joueur " + (i + 1) + "</html>");

            playerNameTitle.setVisible(true);
            playerNameTitle.setBounds(20, -10, 400, 100);
            playerNameTitle.setFont(font);
            playerNameTitle.setForeground(Color.BLACK);

            this.add(playerNameTitle);

            names[i].setFont(font);
            this.add(names[i]);

            this.setBackground(Color.WHITE);
        }

        back.setVisible(true);
        back.setFont(font);
        this.add(back);

        next.setVisible(true);
        next.setFont(font);
        this.add(next);

        back.addActionListener(e -> {
            parentFrame.setCurrentPanel(new PlayerQuantityChoicePanel(parentFrame));
        });

        next.addActionListener(e -> {
            for (int i = 0; i < numberOfPlayers; i++) {
                allPlayers.add(new Player(names[i].getText(), false));
            }
            parentFrame.setCurrentPanel(new MapPanel(parentFrame,allPlayers));
        });

        this.setLayout(new GridLayout(0, 2));
    }


    public ArrayList getAllPlayers(){
        return allPlayers;
    }
}
