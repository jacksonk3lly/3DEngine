public class Triangle {
    Vec3D[] vertices;

    public Triangle(Vec3D v1, Vec3D v2, Vec3D v3) {
        vertices = new Vec3D[] { v1, v2, v3 };
    }

    public Triangle(int[][] verticies) {
        vertices = new Vec3D[3];
        for (int i = 0; i < 3; i++) {
            vertices[i] = new Vec3D(verticies[i][0], verticies[i][1], verticies[i][2]);
        }
    }
}