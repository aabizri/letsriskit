import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Image background;

    protected void setBackgroundFile(String filename) {
        Image image = (new ImageIcon(filename)).getImage();
        this.setBackgroundImage(image);
    }

    protected void setBackgroundImage(Image image) {
        background = image;
        int w = background.getWidth(this);
        int h = background.getHeight(this);
        this.setPreferredSize(new Dimension(w, h));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }
}
