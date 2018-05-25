import javafx.embed.swing.JFXPanel;

import javax.swing.*;

public class Missions {

    public Missions(){

    }

    private void showMissions(int numberOfPlayers){

        for ( int i = 0; i < numberOfPlayers; i++ ){
            JPanel missionPlayer = new JPanel();
            missionPlayer.setVisible(true);
            missionPlayer.setSize(300, 180);
            JLabel mission = new JLabel();
            missionPlayer.add(mission);
        }
    }
}
