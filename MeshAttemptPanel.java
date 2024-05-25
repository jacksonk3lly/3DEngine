import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import java.awt.event.KeyEvent; // Add the missing import statement

public class MeshAttemptPanel extends JPanel {

    Mesh cube;

    public MeshAttemptPanel() {
        setBackground(Color.black); // Set the background color to black
        setUpMeshCube();
        setPreferredSize(new Dimension(800, 800));
        repaint();

        setFocusable(true); // Ensure that the DemoPanel has keyboard focus
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cube.rotateY(Math.PI / 25);
                    repaint();
                }
            }
        });
    }

    void setUpMeshCube() {
        cube = new Mesh(
                new float[][][] {
                        { { -1, -1, -1 }, { 1, -1, -1 }, { 1, 1, -1 } },
                        { { 1, 1, -1 }, { -1, 1, -1 }, { -1, -1, -1 } },
                        { { -1, -1, 1 }, { 1, -1, 1 }, { 1, 1, 1 } },
                        { { 1, 1, 1 }, { -1, 1, 1 }, { -1, -1, 1 } },
                        { { -1, -1, -1 }, { -1, 1, -1 }, { -1, 1, 1 } },
                        { { -1, 1, 1 }, { -1, -1, 1 }, { -1, -1, -1 } },
                        { { 1, -1, -1 }, { 1, 1, -1 }, { 1, 1, 1 } },
                        { { 1, 1, 1 }, { 1, -1, 1 }, { 1, -1, -1 } },
                        { { -1, -1, -1 }, { 1, -1, -1 }, { 1, -1, 1 } },
                        { { 1, -1, 1 }, { -1, -1, 1 }, { -1, -1, -1 } },
                        { { -1, 1, -1 }, { 1, 1, -1 }, { 1, 1, 1 } },
                        { { 1, 1, 1 }, { -1, 1, 1 }, { -1, 1, -1 } }
                });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cube.draw(100, getWidth() / 2, getWidth() / 2, 2, g);
    }

}
