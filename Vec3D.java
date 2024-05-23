public class Vec3D {

    float x;
    float y;
    float z;

    public Vec3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
}