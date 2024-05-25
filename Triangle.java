import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Triangle {

    Vec3D[] vertices;

    public Triangle(Vec3D v1, Vec3D v2, Vec3D v3) {
        vertices = new Vec3D[] { v1, v2, v3 };
    }

    public Triangle(int[][] verticies) {
        vertices = new Vec3D[3];
        for (int i = 0; i < 3; i++) {
            this.vertices[i] = new Vec3D(verticies[i][0], verticies[i][1], verticies[i][2]);
        }
    }

    public Vec3D getNormal() {
        Vec3D normal = Utilities.crossProduct(vertices[1], vertices[0]);
        float lengthOfNormal = normal.getLength();
        Vec3D normalisedNormal = new Vec3D(normal.x / lengthOfNormal, normal.y / lengthOfNormal,
                normal.z / lengthOfNormal);
        return normalisedNormal;
    }

    /*
     * Method which draws the triangle on the screen with the given scale, xoffset
     * mostly intended to be used by the mesh class
     * 
     * @param scale the scale of the triangle
     * 
     * @param xoffset the x offset of the triangle
     * 
     * @param yoffset the y offset of the triangle
     * 
     * @param color the color of the triangle
     * 
     * @param g the graphics object to draw the triangle
     */
    public void draw(int scale, float xoffset, float yoffset, Color color, float distance, Graphics g) {
        Vec3D[] projectedVerticies = new Vec3D[3];
        for (int i = 0; i < 3; i++) {
            float[] vec = Utilities.multiplyMatrix(vertices[i],
                    Utilities.setUpProjectionMatrix(vertices[i].z, distance));
            vec[0] = vec[0] * scale + xoffset;
            vec[1] = vec[1] * scale + yoffset;
            projectedVerticies[i] = new Vec3D(vec[0], vec[1], 0);
        }
        for (Vec3D vec : projectedVerticies) {
            g.setColor(color);
            int circleradius = 5;
            int circleDiameter = 2 * circleradius;
            // g.fillOval((int) vec.x - circleradius, (int) vec.y - circleradius,
            g.drawString(Arrays.toString(getNormal().toArray()), (int) vec.x, (int) vec.y);
            // circleDiameter, circleDiameter);
        }
        for (int i = 0; i < 3; i++) {
            connect(projectedVerticies[i], projectedVerticies[(i + 1) % 3], g);
        }
    }

    /**
     * Connects two points with a line on the graphics object.
     *
     * @param p1 the first point to connect
     * @param p2 the second point to connect
     * @param g  the graphics object to draw the line on
     */
    public void connect(Vec3D p1, Vec3D p2, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }

    /**
     * Rotates the triangle around the X-axis.
     * 
     * @param theta
     */
    public void rotateX(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = new Vec3D(Utilities.multiplyMatrix(vertices[i], Utilities.matRotX(theta)));
        }
    }

    /**
     * Rotates the triangle around the Y-axis.
     * 
     * @param theta
     */
    public void rotateY(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = new Vec3D(Utilities.multiplyMatrix(vertices[i], Utilities.matRotY(theta)));
        }
    }

    /**
     * Rotates the triangle around the Z-axis.
     * 
     * @param theta
     */
    public void rotateZ(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = new Vec3D(Utilities.multiplyMatrix(vertices[i], Utilities.matRotZ(theta)));
        }
    }

}