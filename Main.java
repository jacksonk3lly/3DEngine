import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyListener;

public class Main {
    static KeyListener key;
    static JFrame frame = new JFrame("Jacksons 3D Engine");
    public static SketchUpPanel sketchUpPanel = new SketchUpPanel();

    public static Demo demoPanel = new Demo();

    public static void main(String[] args) {
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(sketchUpPanel, BorderLayout.CENTER);
        // frame.add(demoPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }


    public static int getWindowHeight() {
        return frame.getHeight();
    }

    public static int getWindowWidth() {
        return frame.getWidth();
    }
}
