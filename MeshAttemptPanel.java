import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import java.awt.event.KeyEvent; // Add the missing import statement

public class MeshAttemptPanel extends JPanel {
    Vec3D camera = new Vec3D(0, 0, 0);
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
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    cube.rotateX(Math.PI / 25);
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    cube.rotateY(Math.PI / 25);
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    cube.rotateZ(Math.PI / 25);
                    repaint();
                }
            }
        });
    }

    void setUpMeshCube() {
        cube = new Mesh(
                new float[][][] {

                        // south
                        { { -1, -1, -1 }, { -1, 1, -1 }, { 1, 1, -1 } },
                        { { -1, -1, -1 }, { 1, 1, -1 }, { 1, -1, -1 } },

                        // west
                        { { 1, -1, -1 }, { 1, 1, -1 }, { 1, 1, 1 } },
                        { { 1, -1, -1 }, { 1, 1, 1 }, { 1, -1, 1 } },

                        // east
                        { { -1, -1, 1 }, { -1, 1, 1 }, { -1, 1, -1 } },
                        { { -1, -1, 1 }, { -1, 1, -1 }, { -1, -1, -1 } },

                        // top
                        { { -1, 1, -1 }, { -1, 1, 1 }, { 1, 1, 1 } },
                        { { -1, 1, -1 }, { 1, 1, 1 }, { 1, 1, -1 } },

                        // bottom
                        { { -1, -1, 1 }, { -1, -1, -1 }, { 1, -1, -1 } },
                        { { -1, -1, 1 }, { 1, -1, -1 }, { 1, -1, 1 } },

                        // north
                        { { 1, -1, 1 }, { 1, 1, 1 }, { -1, 1, 1 } },
                        { { 1, -1, 1 }, { -1, 1, 1 }, { -1, -1, 1 } }
                // distance from camera
                }, new Vec3D(0, 0, 2.5f));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cube.draw(100, getWidth() / 2, getWidth() / 2, camera, g);
    }

}
