public class Utilities {

    /**
     * Sets up the projection matrix for a given z value and distance.
     *
     * @param z        the z value
     * @param distance the distance
     * @return the projection matrix
     */
    public static float[][] setUpProjectionMatrix(float z, float distance) {
        z = 1 / (distance - z);
        return new float[][] {
                { z, 0f, 0f },
                { 0f, z, 0f },
                { 0f, 0f, 0f }
        };
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