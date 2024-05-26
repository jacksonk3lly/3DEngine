import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.event.KeyEvent; // Add the missing import statement

public class MeshVeiwerPanel extends JPanel {
    Vec3D camera = new Vec3D(0, 0, 0);
    ArrayList<Mesh> meshes = new ArrayList<Mesh>();
    public float fov = (float) Math.PI * .8f;

    public MeshVeiwerPanel() {
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.black); // Set the background color to black

        try {
            Mesh mainMesh = new Mesh("teapot.obj", new Vec3D(0, 0, 5f));
            meshes.add(mainMesh);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } // Load the mesh from the file "cube.obj"
          // meshes.add(setUpMeshCube(-0f, 0, 4f));
          // meshes.get(1).rotateY(Math.PI / 4);
          // meshes.get(1).rotateX(Math.PI / 4);
        setUpKeyBindings();
    }

    public void setUpKeyBindings() {

        setFocusable(true); // Ensure that the DemoPanel has keyboard focus
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    meshes.get(0).rotateX(Math.PI / 25);
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    meshes.get(0).rotateY(Math.PI / 25);
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    meshes.get(0).rotateZ(Math.PI / 25);
                    repaint();
                }
            }
        });
    }

    public void reloadMesh(String filename, float x, float y, float z, float fov) {
        try {
            meshes.set(0, new Mesh(filename, new Vec3D(x, y, z)));
            this.fov = fov;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Invalid input");
            e.printStackTrace();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Mesh mesh : meshes) {
            mesh.draw(100, getWidth() / 2, getWidth() / 2, camera, fov, g);
        }
    }

}
