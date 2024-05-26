import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyEvent; // Add the missing import statement

public class MeshVeiwerPanel extends JPanel {
    Vec3D camera = new Vec3D(0, 0, 0);
    float yaw = 0;
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
        }
        setUpKeyBindings();
    }

    /**
     * Sets up key bindings for the panel using the KeyboardStatus class.
     */
    public void setUpKeyBindings() {
        setFocusable(true); // Ensure that the DemoPanel has keyboard focus
        KeyboardStatus keyboard = new KeyboardStatus();
        addKeyListener(keyboard);
        Timer keyCheckTimer = new Timer(20, e -> {
            if (keyboard.isKeyPressed(KeyEvent.VK_X)) {
                meshes.get(0).rotateX(Math.PI / 50);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_Y)) {
                meshes.get(0).rotateY(Math.PI / 50);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_Z)) {
                meshes.get(0).rotateZ(Math.PI / 50);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_W)) {
                // Vec3D forward = new Vec3D((float) Math.sin(yaw), 0, (float) Math.cos(yaw));

                Vec3D forward = new Vec3D((float) -Math.sin(yaw), 0, (float) Math.cos(yaw));
                forward.multiply(.2f);
                camera = Utilities.vecAdd(camera, forward);
                // camera = Utilities.vecAdd(camera, forward);
                repaint();
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
                Vec3D forward = new Vec3D((float) -Math.sin(yaw), 0, (float) Math.cos(yaw));
                forward.multiply(.2f);
                camera = Utilities.vecSub(camera, forward);
                // camera = new Vec3D(camera.x, camera.y, camera.z - 0.1f);
                // System.out.println(camera.z);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_A)) {
                Vec3D left = new Vec3D((float) -Math.sin(yaw + Math.PI / 2), 0, (float) Math.cos(yaw + Math.PI / 2));
                left.multiply(.2f);
                camera = Utilities.vecAdd(camera, left);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_D)) {
                Vec3D right = new Vec3D((float) -Math.sin(yaw - Math.PI / 2), 0, (float) Math.cos(yaw - Math.PI / 2));
                right.multiply(.2f);
                camera = Utilities.vecAdd(camera, right);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
                camera = new Vec3D(camera.x, camera.y + 0.1f, camera.z);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                yaw -= Math.PI / 90;
                Vec3D forward = new Vec3D((float) Math.sin(yaw), 0, (float) Math.cos(yaw));
                System.out.println(forward.x);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                yaw += Math.PI / 90;
                repaint();
            }
        });
        keyCheckTimer.start();
    }

    /**
     * Reloads the mesh with the specified filename, location, and field of view.
     * used by the controll Panel
     * 
     * @param filename
     * @param x
     * @param y
     * @param z
     * @param fov
     */
    public void reloadMesh(String filename, float x, float y, float z, float fov) {
        try {
            meshes.set(0, new Mesh(filename, new Vec3D(x, y, z)));
            // this.fov = fov;
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
            mesh.draw(100, getWidth() / 2, getWidth() / 2, yaw, camera, fov, g);
        }
    }

}
