import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public static void main(String[] args) {
        GUI g = new GUI();
        g.gui();
    }

    public void gui() {


        JPanel p1 = new JPanel(new GridLayout(2,0));
        JPanel panel = new JPanel();


        panel.setLayout(new GridLayout(2,2));
        JLabel question = new JLabel("FRÅGA HÄR");
        JButton b1 = new JButton("SVAR 1");
        b1.setSize(10,10);
        JButton b2 = new JButton("SVAR 2");
        b2.setSize(50,20);
        JButton b3 = new JButton("SVAR 3");
        b3.setSize(50,20);
        JButton b4 = new JButton("SVAR 4");
        b4.setSize(50,20);
        add(p1);
        p1.add(question,0);
        p1.add(panel,1);
        panel.add(b1,0);
        panel.add(b2,1);
        panel.add(b3,2);
        panel.add(b4,3);
        setSize(555, 555);
        setVisible(true);
    }
}
