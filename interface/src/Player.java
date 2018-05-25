import javax.swing.*;
import java.awt.*;

public class Player {

    private JPanel container = new JPanel();
    private JTextField name = new JTextField("Nom du joueur");

    public Player(){
    }

    public JPanel addPlayerName(int numberOfPlayers){

        for (int i = 0; i < numberOfPlayers; i++){

            JLabel titleName = new JLabel("Nom du joueur " + i+1);
            container.add(titleName);
            container.add(name);
            container.setVisible(true);
            container.setLayout(new GridLayout(1,2));

        }
        return container;
    }

}
