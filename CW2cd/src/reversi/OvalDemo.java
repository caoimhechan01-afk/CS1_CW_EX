package reversi;

import javax.swing.*;
import java.awt.*;

public class OvalDemo extends JButton {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GREEN);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.WHITE);
        g2.fillOval(2, 2, 46, 46);

        g2.setColor(Color.BLACK);
        g2.drawOval(2, 2, 46, 46);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Oval Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new OvalDemo());
        frame.setSize(100, 100);
        frame.setVisible(true);
    }
    
    
}
