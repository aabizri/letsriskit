import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.ArrayList;

public class Missions extends JFrame {

    public Missions(){

    }

    public JFrame showMissions(ArrayList allPlayers){

        JFrame missionPlayer = new JFrame();

        for ( int i = 0; i < allPlayers.size(); i++ ){
            Object player = allPlayers.get(i);
            missionPlayer.setTitle("Mission de " + player);
            missionPlayer.setVisible(true);
            missionPlayer.setSize(300, 180);
            missionPlayer.setLocationRelativeTo(null);
            JLabel mission = new JLabel();
            missionPlayer.add(mission);
            return missionPlayer;
        }
        return missionPlayer;
    }
}
