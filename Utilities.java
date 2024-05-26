import java.awt.Color;

public class Utilities {

    /**
     * Sets up the projection matrix for a given z value and distance.
     *
     * @param z        the z value
     * @param distance the distance
     * @return the projection matrix
     */
    public static float[][] setUpProjectionMatrix(float z) {
        return new float[][] {
                { 1 / z, 0f, 0f },
                { 0f, 1 / z, 0f },
                { 0f, 0f, 0f }
        };
    }

    /**
     * method which takes a brightness variable from 0 to 1 returns a new Color
     * 
     * @param color
     * @param brightness
     */
    public static Color adjustColor(Color color, float brightness) {
        int red = (int) (color.getRed() * brightness * brightness);
        int green = (int) (color.getGreen() * brightness * brightness);
        int blue = (int) (color.getBlue() * brightness * brightness);
        return new Color(red, green, blue);
    }

    /**
     * Adds two vectors.
     * 
     * @param a firstVec
     * @param b secondVec
     * @return
     */
    public static Vec3D vecAdd(Vec3D a, Vec3D b) {
        return new Vec3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    /**
     * Translates a triangle by a given vector.
     * 
     * @param t
     * @param translation
     * @return
     */
    public static Triangle getTranslatedTriangle(Triangle t, Vec3D translation) {
        return new Triangle(
                vecAdd(t.vertices[0], translation),
                vecAdd(t.vertices[1], translation),
                vecAdd(t.vertices[2], translation));
    }

    /**
     * dot product
     * 
     * @param vec3d first vector
     * @param b     second vector
     */
    public static float dotProduct(Vec3D vec3d, Vec3D b) {
        return vec3d.x * b.x + vec3d.y * b.y + vec3d.z * b.z;
    }

    public static Vec3D vecSub(Vec3D a, Vec3D b) {
        return new Vec3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    /**
     * Calculates the rotation matrix around the Z-axis.
     *
     * @param theta the rotation angle in radians
     * @return the rotation matrix
     */
    public static float[][] matRotZ(double theta) {
        return new float[][] {
                { (float) Math.cos(theta), (float) -Math.sin(theta), 0 },
                { (float) Math.sin(theta), (float) Math.cos(theta), 0 },
                { 0, 0, 1 }
        };
    }

    public static Vec3D crossProduct(Vec3D a, Vec3D b) {
        return new Vec3D(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);

    }

    /**
     * Calculates the rotation matrix around the X-axis.
     *
     * @param theta the rotation angle in radians
     * @return the rotation matrix
     */
    public static float[][] matRotX(double theta) {
        return new float[][] {
                { 1, 0, 0 },
                { 0, (float) Math.cos(theta), (float) -Math.sin(theta) },
                { 0, (float) Math.sin(theta), (float) Math.cos(theta) }
        };
    }

    /**
     * Calculates the rotation matrix around the Y-axis.
     *
     * @param theta the rotation angle in radians
     * @return the rotation matrix
     */
    public static float[][] matRotY(double theta) {
        return new float[][] {
                { (float) Math.cos(theta), 0, (float) Math.sin(theta) },
                { 0, 1, 0 },
                { (float) -Math.sin(theta), 0, (float) Math.cos(theta) }
        };
    }

    /**
     * Multiplies a vector by a matrix.
     *
     * @param vec    the vector
     * @param matrix the matrix
     * @return the result of the multiplication
     */
    public static float[] multiplyMatrix(Vec3D vec, float[][] matrix) {
        float[] result = new float[matrix.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += vec.get(j) * matrix[i][j];
            }
        }
        return result;
    }
}