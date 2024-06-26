public class Vec3D {

    float x;
    float y;
    float z;

    public Vec3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalize() {
        float length = getLength();
        if (length != 0) {
            x /= length;
            y /= length;
            z /= length;
        }
    }

    /**
     * multiplies the vector by a scalar
     * 
     * @param scalar
     */
    public void multiply(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    public Vec3D(float[] vec) {
        this.x = vec[0];
        this.y = vec[1];
        this.z = vec[2];
    }

    public float get(int i) {
        if (i == 0)
            return x;
        if (i == 1)
            return y;
        if (i == 2)
            return z;
        return 0;
    }

    public float[] toArray() {
        return new float[] { x, y, z };
    }

    public float getLength() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * toString method returns x, y, z string
     * 
     * @return x, y, z string
     */
    public String toString() {
        return "x:" + x + ", y:" + y + ", z:" + z;
    }
}