import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerNames {

    private JPanel playerTag = new JPanel();
    private JFrame playerTagArea = new JFrame();
    private JButton back = new JButton("<");
    private JButton next = new JButton(">");
    private Font font = new Font("Times Roman", Font.BOLD, 30);
    private String tabulation = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private ArrayList allPlayers = new ArrayList();

    //Tous les JTextArea
    private JTextField name1 = new JTextField();
    private JTextField name2 = new JTextField();
    private JTextField name3 = new JTextField();
    private JTextField name4 = new JTextField();
    private JTextField name5 = new JTextField();
    private JTextField name6 = new JTextField();
    private JTextField[] names = {name1, name2, name3, name4, name5, name6};

    public PlayerNames(int numberOfPlayers) {

        playerTagArea.setTitle("Choix des noms des joueurs");
        playerTagArea.pack();
        playerTagArea.setLocationRelativeTo(null);
        playerTagArea.setVisible(true);
        playerTagArea.setSize(1280, 750);
        playerTagArea.setResizable(false);

        for (int i = 0; i < numberOfPlayers; i++) {

            JLabel playerNameTitle = new JLabel("<html>" + tabulation + "Nom du joueur " + (i + 1) + "</html>");

            playerNameTitle.setVisible(true);
            playerNameTitle.setBounds(20, -10, 400, 100);
            playerNameTitle.setFont(font);
            playerNameTitle.setForeground(Color.BLACK);
            playerTag.add(playerNameTitle);

            names[i].setFont(font);
            playerTag.add(names[i]);

            playerTag.setBackground(Color.WHITE);
        }

        back.setVisible(true);
        back.setFont(font);
        playerTag.add(back);

        next.setVisible(true);
        next.setFont(font);
        playerTag.add(next);

        playerTagArea.setLocationRelativeTo(null);
        playerTagArea.add(playerTag);

        back.addActionListener(e -> {
            playerTagArea.setVisible(false);
        });

        next.addActionListener(e -> {
            playerTagArea.setVisible(false);
            for (int i = 0; i < numberOfPlayers; i++) {
                allPlayers.add(new Player(names[i].getText(), false));
            }
        });

        playerTag.setLayout(new GridLayout(0, 2));
    }

    public JFrame getPlayerTagArea() {
        return playerTagArea;
    }

    public ArrayList getAllPlayers(){
        return allPlayers;
    }
}
