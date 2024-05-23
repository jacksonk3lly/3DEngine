import java.util.ArrayList;

public class Mesh {
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();

    public Mesh(ArrayList<Triangle> triangles) {
        this.triangles = triangles;
    }
}