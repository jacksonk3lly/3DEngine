import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Triangle {

    Vec3D[] vertices;
    private Vec3D location;

    /**
     * Returns an array of vertices that make up the triangle.
     *
     * @return an array of Vec3D objects representing the vertices of the triangle
     */
    public Vec3D[] getVertices() {
        return vertices;
    }

    /**
     * accesser method for the location of the triangle
     * 
     * @return the location of the triangle
     */
    public Vec3D getLocation() {
        return location;
    }

    /**
     * mutator method for the location of the triangle
     * 
     * @param location
     */
    public void setLocation(Vec3D location) {
        this.location = location;
    }

    public Triangle(Vec3D v1, Vec3D v2, Vec3D v3, Vec3D location) {
        vertices = new Vec3D[] { v1, v2, v3 };
        this.location = location;
    }

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

        Vec3D vec1 = new Vec3D(vertices[0].x - vertices[1].x, vertices[0].y - vertices[1].y,
                vertices[0].z - vertices[1].z);
        Vec3D vec2 = new Vec3D(vertices[0].x - vertices[2].x, vertices[0].y - vertices[2].y,
                vertices[0].z - vertices[2].z);
        Vec3D normal = Utilities.crossProduct(vec1, vec2);

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
    public void draw(int scale, float xoffset, float yoffset, Color color, float fov, Graphics g) {
        Vec3D[] projectedVerticies = new Vec3D[3];
        Vec3D light = new Vec3D(0, 0, -1);
        light.normalize();
        for (int i = 0; i < 3; i++) {
            Vec3D vec = Utilities.multiplyMatrix(vertices[i],
                    Utilities.setUpProjectionMatrix(vertices[i].z, fov));
            vec.x = vec.x * scale + xoffset;
            vec.y = -vec.y * scale + yoffset;
            projectedVerticies[i] = vec;
        }
        for (Vec3D vec : projectedVerticies) {
            g.setColor(color);
            int circleradius = 5;
            int circleDiameter = 2 * circleradius;
            // g.fillOval((int) vec.x - circleradius, (int) vec.y - circleradius,
            // g.drawString(Arrays.toString(getNormal().toArray()), (int) vec.x, (int)
            // vec.y);
            // circleDiameter, circleDiameter);
        }
        for (int i = 0; i < 3; i++) {
            connect(projectedVerticies[i], projectedVerticies[(i + 1) % 3], g);
        }

        Graphics2D g2d = (Graphics2D) g;
        float brightness = Utilities.dotProduct(getNormal(), light);
        color = Utilities.adjustColor(color, brightness);

        // Clip the projected vertices to ensure they are within the screen bounds
        for (int i = 0; i < 3; i++) {
            if (projectedVerticies[i].x < 0) {
                projectedVerticies[i].x = 0;
            } else if (projectedVerticies[i].x > g2d.getClipBounds().getWidth()) {
                projectedVerticies[i].x = (float) g2d.getClipBounds().getWidth();
            }

            if (projectedVerticies[i].y < 0) {
                projectedVerticies[i].y = 0;
            } else if (projectedVerticies[i].y > g2d.getClipBounds().getHeight()) {
                projectedVerticies[i].y = (float) g2d.getClipBounds().getHeight();
            }
        }

        g2d.setColor(color);
        // Fill the triangle with the specified color
        g2d.fillPolygon(
                new int[] { (int) projectedVerticies[0].x, (int) projectedVerticies[1].x,
                        (int) projectedVerticies[2].x },
                new int[] { (int) projectedVerticies[0].y, (int) projectedVerticies[1].y,
                        (int) projectedVerticies[2].y },
                3);
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
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.black);
        g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }

    /**
     * Rotates the triangle around the X-axis.
     * 
     * @param theta
     */
    public void rotateX(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = Utilities.multiplyMatrix(vertices[i], Utilities.matRotX(theta));
        }
    }

    /**
     * Rotates the triangle around the Y-axis.
     * 
     * @param theta
     */
    public void rotateY(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = Utilities.multiplyMatrix(vertices[i], Utilities.matRotY(theta));
        }
    }

    /**
     * Rotates the triangle around the Z-axis.
     * 
     * @param theta
     */
    public void rotateZ(double theta) {
        for (int i = 0; i < 3; i++) {
            vertices[i] = Utilities.multiplyMatrix(vertices[i], Utilities.matRotZ(theta));
        }
    }

}