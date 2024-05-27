import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

public class Main {
    static JFrame frame = new JFrame(
            "Jacksons 3D Engine");

    public static void main(String[] args) {
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(new MeshLoadingPanel());
        frame.add(new MeshLoadingPanel());
        frame.pack();
        frame.setVisible(true);

        // Vec3D planePosition = new Vec3D(0, 0, 1);
        // Vec3D planeNormal = new Vec3D(0, 0, 1);
        // Vec3D a = new Vec3D(-1, 0, -1);
        // Vec3D b = new Vec3D(2, 0, 2);
        // System.out.println(Utilities.getIntersection(a, b, planePosition,
        // planeNormal).toString());
    }
}