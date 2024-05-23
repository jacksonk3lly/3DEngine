import javax.swing.JFrame;

import java.awt.Color;

public class Main {
    static JFrame frame = new JFrame("3D Renderer");

    public static void main(String[] args) {
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DemoPanel());
        frame.pack();
        frame.setVisible(true);

    }
}