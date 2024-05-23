import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DemoPanel extends JPanel {

    private float[][] matProj;
    private float[][] matRotZ;
    private float[][] matRotX;
    private float[][] matRotY;
    private int scale = 300;
    private int middleX;
    private int middleY;
    private Vec3D[] cube;
    float hue = 0;
    double keyboardSpeed = Math.PI / 25;
    double autoSpeed = Math.PI / 300;
    private float distance = 1.5f;

    public DemoPanel() {
        // setUpProjectionMatrix();
        setBackground(Color.black);
        makeCube();
        repaint();
        setPreferredSize(new Dimension(800, 800));
        setUpRotations(autoSpeed);
        Timer timer = new Timer(1000 / 60, e -> {
            step();
            repaint();
        });
        timer.start();
        setFocusable(true); // Ensure that the DemoPanel has keyboard focus
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    stepX();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    stepY();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    stepZ();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    distance -= 0.03f;
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    distance += 0.03f;
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!timer.isRunning()) {
                        setUpRotations(autoSpeed);
                        timer.start();
                    } else {
                        setUpRotations(keyboardSpeed);
                        timer.stop();
                    }
                }
            }
        });
    }

    public void setUpRotations(double theta) {
        matRotZ = new float[][] {
                { (float) Math.cos(theta), (float) -Math.sin(theta), 0 },
                { (float) Math.sin(theta), (float) Math.cos(theta), 0 },
                { 0, 0, 1 }
        };

        matRotX = new float[][] {
                { 1, 0, 0 },
                { 0, (float) Math.cos(theta), (float) -Math.sin(theta) },
                { 0, (float) Math.sin(theta), (float) Math.cos(theta) }
        };

        matRotY = new float[][] {
                { (float) Math.cos(theta), 0, (float) Math.sin(theta) },
                { 0, 1, 0 },
                { (float) -Math.sin(theta), 0, (float) Math.cos(theta) }
        };
    }

    public void step() {
        hue += 0.001f;
        for (int i = 0; i < 8; i++) {
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotX));
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotY));
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotZ));
        }
    }

    public void stepX() {
        hue += 0.001f;
        for (int i = 0; i < 8; i++) {
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotX));
        }
    }

    public void stepY() {
        for (int i = 0; i < 8; i++) {
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotY));
        }
    }

    public void stepZ() {
        for (int i = 0; i < 8; i++) {
            cube[i] = new Vec3D(multiplyMatrix(cube[i], matRotZ));
        }

    }

    public void makeCube() {
        cube = new Vec3D[] {
                new Vec3D(-0.5f, -0.5f, -0.5f),
                new Vec3D(0.5f, -0.5f, -0.5f),
                new Vec3D(0.5f, 0.5f, -0.5f),
                new Vec3D(-0.5f, 0.5f, -0.5f),
                new Vec3D(-0.5f, -0.5f, 0.5f),
                new Vec3D(0.5f, -0.5f, 0.5f),
                new Vec3D(0.5f, 0.5f, 0.5f),
                new Vec3D(-0.5f, 0.5f, 0.5f)
        };
    }

    public void setUpProjectionMatrix(float z) {
        z = 1 / (distance - z);
        matProj = new float[][] {
                { z, 0f, 0f },
                { 0f, z, 0f },
                { 0f, 0f, 0f }
        };
    }

    float[] multiplyMatrix(Vec3D vec, float[][] matrix) {
        float[] result = new float[matrix.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += vec.get(j) * matrix[i][j];
            }
        }
        return result;
    }

    // paint component
    @Override
    protected void paintComponent(Graphics paint) {
        Graphics2D g = (Graphics2D) paint;
        g.setStroke(new BasicStroke(15));
        float[][] projectedCube = new float[8][2];
        for (int i = 0; i < 8; i++) {
            setUpProjectionMatrix(cube[i].z);
            float[] vec = multiplyMatrix(cube[i], matProj);
            vec[0] = vec[0] * scale + middleX;
            vec[1] = vec[1] * scale + middleY;
            projectedCube[i] = vec;
        }
        middleX = getWidth() / 2;
        middleY = getHeight() / 2;
        for (float[] vec : projectedCube) {
            g.setColor(Color.getHSBColor(hue, 1, 1));
            int circleradius = 15;
            int circleDiameter = 2 * circleradius;
            g.fillOval((int) vec[0] - circleradius, (int) vec[1] - circleradius,
                    circleDiameter, circleDiameter);
        }
        for (int i = 0; i < 4; i++) {
            connect(projectedCube[i], projectedCube[(i + 1) % 4], g);
            connect(projectedCube[i + 4], projectedCube[(i + 1) % 4 + 4], g);
            connect(projectedCube[i], projectedCube[i + 4], g);

        }
    }

    public void connect(float[] p1, float[] p2, Graphics g) {
        g.drawLine((int) p1[0], (int) p1[1], (int) p2[0], (int) p2[1]);
    }
}
