package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;


public class Territory {
    private static final int BUTTON_WIDTH = 20;

    String name;
    JButton button;

    Territory(final String name, final int x, final int y){
        this.button = newButton(x,y,BUTTON_WIDTH);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public JButton getButton() {
        return button;
    }

    public void ColorButton(){
        JButton button = this.getButton();
        button.setBackground(Color.BLUE);
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
                territoryInformation.setBounds(1003, 210,500,350);
                JButton exit = new JButton();
                JButton manageTroups = new JButton();
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

                territoryInformation.add(manageTroups);

                territoryInformation.add(exit);

                territoryInformation.setLayout(new GridLayout(7,1));

                manageTroups.setText("<HTML><BODY> Placer des troupes </BODY></HTML>");
                manageTroups.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        territoryInformation.dispose();
                        JFrame troups = new JFrame();
                        troups.setTitle(territory.getName() + "  Managing");
                        troups.setVisible(true);
                        troups.setBackground(Color.white);
                        troups.setBounds(1003, 210,500,350);
                        JLabel territoryName = new JLabel("The current territory is " + territory.getName());
                        /*String[] soldats = {"Soldats","1","2","3"};
                        String[] canons = {"Canons","1","2","3"};
                        String[] cavaliers = {"Cavaliers","1","2","3"};
                        JComboBox soldatSelect = new JComboBox(soldats);
                        JComboBox canonSelect = new JComboBox(canons);
                        JComboBox cavalierSelect = new JComboBox(cavaliers);*/
                        JFormattedTextField soldatSelect = new JFormattedTextField(NumberFormat.getIntegerInstance());
                        JFormattedTextField canonSelect = new JFormattedTextField(NumberFormat.getIntegerInstance());
                        JFormattedTextField cavalierSelect = new JFormattedTextField(NumberFormat.getIntegerInstance());
                        JButton Validate =new JButton();
                        JLabel soldat = new JLabel();
                        JLabel cavalier = new JLabel();
                        JLabel canon = new JLabel();
                        canon.setText("Canon");
                        cavalier.setText("Cavalier" );
                        soldat.setText("Soldat");
                        Validate.setText("Validate");
                        troups.add(territoryName);
                        troups.add(soldat);
                        troups.add(soldatSelect);
                        troups.add(cavalier);
                        troups.add(cavalierSelect);
                        troups.add(canon);
                        troups.add(canonSelect);
                        troups.add(Validate);
                        troups.setLayout(new GridLayout(8,1));
                        troups.setVisible(true);

                        Validate.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int soldat = Integer.parseInt(soldatSelect.getText());
                                int canon = Integer.parseInt(canonSelect.getText());
                                int cavalier = Integer.parseInt(cavalierSelect.getText());
                                troups.dispose();
                            }
                        });


                    }
                });
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

    public void addToPanel(JPanel panel) {
        panel.add(this.button);
    }

    //private void JButton newButton(final int x, final int y) {return Territory.newButton(x,y,BUTTON_WIDTH);}

    private JButton newButton(final int x, final int y, final int width){
        final JButton territoryButton = new JButton();
        territoryButton.setBounds(x,y,width,width);
        this.getTerritoryFrame(territoryButton);
        return territoryButton;
    }


}
