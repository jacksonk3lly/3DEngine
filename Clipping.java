import java.util.ArrayList;
import java.util.List;

public class Clipping {

    public static boolean inside(float x, float y, String edge, float boundary) {
        switch (edge) {
            case "LEFT":
                return x >= boundary;
            case "RIGHT":
                return x <= boundary;
            case "BOTTOM":
                return y >= boundary;
            case "TOP":
                return y <= boundary;
            default:
                return false;
        }
    }

    public static float[] computeIntersection(float x1, float y1, float x2, float y2, String edge,
            float boundary) {
        float[] intersection = new float[2];
        if (edge.equals("LEFT") || edge.equals("RIGHT")) {
            intersection[0] = boundary;
            intersection[1] = y1 + (y2 - y1) * (boundary - x1) / (x2 - x1);
        } else {
            intersection[1] = boundary;
            intersection[0] = x1 + (x2 - x1) * (boundary - y1) / (y2 - y1);
        }
        return intersection;
    }

    public static List<float[]> clipPolygon(List<float[]> vertices, String edge, float boundary) {
        List<float[]> clippedVertices = new ArrayList<>();
        float[] prevVertex = vertices.get(vertices.size() - 1);

        for (float[] currVertex : vertices) {
            if (inside(currVertex[0], currVertex[1], edge, boundary)) {
                if (!inside(prevVertex[0], prevVertex[1], edge, boundary)) {
                    clippedVertices.add(computeIntersection(prevVertex[0], prevVertex[1], currVertex[0], currVertex[1],
                            edge, boundary));
                }
                clippedVertices.add(currVertex);
            } else if (inside(prevVertex[0], prevVertex[1], edge, boundary)) {
                clippedVertices.add(computeIntersection(prevVertex[0], prevVertex[1], currVertex[0], currVertex[1],
                        edge, boundary));
            }
            prevVertex = currVertex;
        }

        return clippedVertices;
    }
}
