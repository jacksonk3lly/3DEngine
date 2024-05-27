import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
//import mouse adapter
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

public class MeshVeiwerPanel extends JPanel {
    Vec3D camera = new Vec3D(0, 0, 0);
    float yaw = 0;
    float pitch = 0;
    JPanel panel = this;
    ArrayList<Mesh> meshes = new ArrayList<Mesh>();
    public float fov = (float) Math.PI * .8f;

    public MeshVeiwerPanel() {
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.black); // Set the background color to black

        try {
            Mesh duck = new Mesh("duck.obj", new Vec3D(0, -0.5f, 6f));
            Mesh mountains = new Mesh("mountains.obj", new Vec3D(0, -10, +15));
            mountains.rotateY(Math.PI + Math.PI / 3);
            duck.rotateY(Math.PI / 2);
            Mesh mainMesh = duck;

            meshes.add(mainMesh);
            meshes.add(mountains);
            meshes.add(new Mesh("teapot.obj", new Vec3D(+6, 1, 5f)));
            Mesh man = new Mesh("man.obj", new Vec3D(0, -1, 15));
            man.rotateY(Math.PI);
            meshes.add(man);
            camera = new Vec3D(0, 3, 0);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        setUpKeyBindings();
        requestFocusInWindow();
    }

    public void setUpKeyBindings() {
        setFocusable(true); // Ensure that the DemoPanel has keyboard focus
        // addMouseListener(new MouseLookController(this));
        // addMouseMotionListener(new MouseLookController());
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
            if (keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
                camera = new Vec3D(camera.x, camera.y - 0.1f, camera.z);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
                camera = new Vec3D(camera.x, camera.y + 0.1f, camera.z);
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                yaw -= Math.PI / 50;
                Vec3D forward = new Vec3D((float) Math.sin(yaw), 0, (float) Math.cos(yaw));
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                yaw += Math.PI / 50;
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                if (pitch < Math.PI / 2) {
                    pitch += Math.PI / 50;
                }
                repaint();
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                if (pitch > -Math.PI / 2) {
                    pitch -= Math.PI / 50;
                }
                repaint();
            }

            // Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            // int mouseX = (int) mouseLocation.getX();
            // int mouseY = (int) mouseLocation.getY();
            // int centerX = getWidth() / 2;
            // int centerY = getHeight() / 2;
            // float sensitivity = 0.00005f;

            // yaw += -(mouseX - centerX) * sensitivity;
            // pitch += -(mouseY - centerY) * sensitivity;

            // repaint();
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

    public class MouseLookController extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clicked");
            panel.requestFocus();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            float sensitivity = 0.0001f;

            yaw += -(mouseX - centerX) * sensitivity;
            pitch += -(mouseY - centerY) * sensitivity;

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        Utilities.drawMeshes(meshes, 100, getWidth() / 2, getWidth() / 2, yaw, pitch, camera, fov, g);
    }
}
