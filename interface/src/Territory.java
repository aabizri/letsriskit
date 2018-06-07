
import javax.swing.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;


public class Territory /** extends engine.Territory **/{
    private static final int BUTTON_WIDTH = 20;

    String name;
    JButton button;
    Collection<Player> players;

    Territory(final String name, final int x, final int y){
        this.button = newButton(x,y,20);
        this.name = name;
    }

    public void addToPanel(JPanel panel) { panel.add(this.button); }

   // private static JButton newButton(final int x, final int y) { return Territory.newButton(x,y,BUTTON_WIDTH); }

    public JButton getButton() { return button; }


    public String getName() { return name; }

    public Collection<Player> getPlayers(){
        return players;
    }


     public void colorButtonBlue(JButton territoryButton){
        territoryButton.setBackground(Color.BLUE);
    }

    public void colorButtonGreen(JButton territoryButton){
        territoryButton.setBackground(Color.green);
    }

    public void getTerritoryFrame(JButton b){
        Territory territory = this;
        JFrame territoryInformation = new JFrame();
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // panel.setBackground(new Color(0,0,0,0));
                territoryInformation.setLocationRelativeTo(null);
                territoryInformation.setTitle(territory.getName());
                territoryInformation.setVisible(true);
                territoryInformation.setBackground(Color.white);
                territoryInformation.setBounds(1003, 210,300,250);
                JButton exit = new JButton();
                JLabel territoryName = new JLabel(territory.getName());
                JLabel soldier = new JLabel("Soldat : 2");
                JLabel cavalier = new JLabel("Cavalier : 0");
                JLabel canon = new JLabel("Canon :1" );
                JLabel occupant = new JLabel("L'occupant est player1");
                territoryInformation.add(territoryName);
                territoryInformation.add(occupant);
                territoryInformation.add(soldier);
                territoryInformation.add(cavalier);
                territoryInformation.add(canon);



                territoryInformation.setLayout(new GridLayout(6,1));

                exit.setText("<HTML><BODY> CLICK TO CONTINUE</BODY></HTML>");
                   exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        territoryInformation.dispose();
                    }
                });

            }
        });

    }


    private JButton newButton(final int x, final int y, final int width){
        final JButton territoryButton = new JButton();
        territoryButton.setBounds(x,y,width,width );
        this.getTerritoryFrame(territoryButton);
        return territoryButton;
    }


}
