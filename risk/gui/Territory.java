package gui;

import javax.swing.*;
import java.util.Collection;

public class Territory {
    private static final int BUTTON_WIDTH = 15;

    String name;
    JButton button;

    Territory(final String name, final int x, final int y){
        this.button = newButton(x,y);
        this.name = name;
    }

    public void addToPanel(JPanel panel) {
        panel.add(this.button);
    }

    private static JButton newButton(final int x, final int y) {
        return Territory.newButton(x,y,BUTTON_WIDTH);
    }

    private static JButton newButton(final int x, final int y, final int width){
        final JButton territoryButton = new JButton();
        territoryButton.setBounds(x,y,width,width);
        return territoryButton;
    }


}
