import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Mesh {
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Creates a mesh from a list of triangles.
     * 
     * @param triangles
     */
    public Mesh(ArrayList<Triangle> triangles) {
        this.triangles = triangles;
    }

    /**
     * Creates a mesh from a 3D array of vertices.
     * 
     * @param vertices corners of the triangles
     */
    public Mesh(float[][][] vertices) {
        for (int i = 0; i < vertices.length; i++) {
            triangles.add(new Triangle(
                    new Vec3D(vertices[i][0][0], vertices[i][0][1], vertices[i][0][2]),
                    new Vec3D(vertices[i][1][0], vertices[i][1][1], vertices[i][1][2]),
                    new Vec3D(vertices[i][2][0], vertices[i][2][1], vertices[i][2][2])));
        }
    }

    /**
     * Draws the mesh on the screen.
     *
     * @param scale   the scale factor for the mesh
     * @param xoffset the x-axis offset for the mesh
     * @param yoffset the y-axis offset for the mesh
     * @param g       the Graphics object used for drawing
     */
    void draw(int scale, float xoffset, float yoffset, float distance, Graphics g) {
        for (Triangle t : triangles) {
            if (t.getNormal().z < 0) {
                t.draw(scale, xoffset, yoffset, Color.white, distance, g);
            }
        }
    }

    /**
     * Rotates the mesh around the Z-axis.
     * 
     * @param theta
     */
    public void rotateZ(double theta) {
        for (Triangle t : triangles) {
            t.rotateZ(theta);
        }
    }

    /**
     * Rotates the mesh around the Y-axis.
     * 
     * @param theta
     */
    public void rotateY(double theta) {
        for (Triangle t : triangles) {
            t.rotateY(theta);
        }
    }

    /**
     * Rotates the mesh around the X-axis.
     * 
     * @param theta
     */
    public void rotateX(double theta) {
        for (Triangle t : triangles) {
            t.rotateX(theta);
        }
    }

}