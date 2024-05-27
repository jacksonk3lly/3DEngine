import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

public class Utilities {

    /**
     * Sets up the projection matrix for a given z value and distance.
     *
     * @param z        the z value
     * @param distance the distance
     * @return the projection matrix
     */
    public static float[][] setUpProjectionMatrix(float z, float fov) {
        float f = 1 / z * (float) Math.tan((double) fov / 2);
        return new float[][] {
                { f, 0f, 0f },
                { 0f, f, 0f },
                { 0f, 0f, 1f }
        };
    }

    /**
     * draws Meshes on the Screen
     *
     * @param scale   the scale factor for the mesh
     * @param xoffset the x-axis offset for the mesh
     * @param yoffset the y-axis offset for the mesh
     * @param g       the Graphics object used for drawing
     */
    public static void drawMeshes(ArrayList<Mesh> meshses, int scale, float xoffset, float yoffset, float yaw,
            float pitch,
            Vec3D camera, float fov, Graphics g) {
        ArrayList<Triangle> trianglesToDraw = new ArrayList<Triangle>();

        for (Mesh mesh : meshses) {
            ArrayList<Triangle> triangles = mesh.getTriangles();
            for (Triangle t : triangles) {
                // translate position before drawing
                Vec3D translatedLocation = Utilities.vecSub(t.getLocation(), camera);
                Triangle translatedTriangle = Utilities.getTranslatedTriangle(t, translatedLocation);
                if (yaw != 0) {
                    translatedTriangle.rotateY(yaw);
                }
                if (pitch != 0) {
                    translatedTriangle.rotateX(pitch);
                }
                // origin because everything moves around the camera while the camera actually
                // stays in place
                Vec3D cameraRay = Utilities.vecSub(translatedTriangle.vertices[0], new Vec3D(0, 0, 0));
                // dont draw the triangle if it is facing away from the camera
                if (Utilities.dotProduct(translatedTriangle.getNormal(), cameraRay) < 0f) {
                    trianglesToDraw.add(translatedTriangle);
                }
            }
        }
        // sort so that we draw the triangles that are closer to the camera last
        trianglesToDraw.sort(new Comparator<Triangle>() {
            @Override
            public int compare(Triangle t1, Triangle t2) {
                // Get the z position of a random vertex from each triangle
                float z1 = t1.getVertices()[0].z;
                float z2 = t2.getVertices()[0].z;
                // Compare the z positions
                if (z1 > z2) {
                    return -1;
                } else if (z1 < z2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        // Draw the triangles
        for (Triangle t : trianglesToDraw) {
            Triangle[] clippedTriangles = Utilities.clipTriangeletoPlane(new Vec3D(0, 0, .01f), new Vec3D(0, 0, 1), t);
            if (clippedTriangles == null) {
                continue;
            }
            for (Triangle clippedTriangle : clippedTriangles) {
                clippedTriangle.draw(scale, xoffset, yoffset, Color.pink, fov, g);
            }
        }
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

    public static Triangle triangleSubVector(Triangle t, Vec3D translation) {
        return new Triangle(
                vecSub(t.vertices[0], translation),
                vecSub(t.vertices[1], translation),
                vecSub(t.vertices[2], translation));
    }

    public static Vec3D normaliseVector(Vec3D vector) {
        float lengthOfVector = vector.getLength();
        return new Vec3D(vector.x / lengthOfVector, vector.y / lengthOfVector, vector.z / lengthOfVector);
    }

    /**
     * gets the shortest distance from a point to a plane
     * 
     * @param point
     * @param planePosition
     * @param planeNormal
     * @return
     */
    public static float shortDistanceVecToPlane(Vec3D point, Vec3D planePosition, Vec3D planeNormal) {
        float distance = dotProduct(vecSub(point, planePosition), planeNormal);
        return distance;
    }

    /**
     * clips a triangle to a plane
     * 
     * @param planePosition
     * @param planeNormal
     * @param inputTriangle
     * @return
     */
    public static Triangle[] clipTriangeletoPlane(Vec3D planePosition, Vec3D planeNormal, Triangle inputTriangle) {
        // normalise plane normal
        planeNormal = Utilities.normaliseVector(planeNormal);

        Vec3D[] insidePoints = new Vec3D[3];
        Vec3D[] outsidePoints = new Vec3D[3];
        int insidePointCount = 0;
        int outsidePointCount = 0;

        float vert0Distance = Utilities.shortDistanceVecToPlane(inputTriangle.vertices[0], planePosition, planeNormal);
        float vert1Distance = Utilities.shortDistanceVecToPlane(inputTriangle.vertices[1], planePosition, planeNormal);
        float vert2Distance = Utilities.shortDistanceVecToPlane(inputTriangle.vertices[2], planePosition, planeNormal);

        if (vert0Distance >= 0) {
            insidePoints[insidePointCount] = inputTriangle.vertices[0];
            insidePointCount++;
        } else {
            outsidePoints[outsidePointCount] = inputTriangle.vertices[0];
            outsidePointCount++;
        }
        if (vert1Distance >= 0) {
            insidePoints[insidePointCount] = inputTriangle.vertices[1];
            insidePointCount++;
        } else {
            outsidePoints[outsidePointCount] = inputTriangle.vertices[1];
            outsidePointCount++;
        }
        if (vert2Distance >= 0) {
            insidePoints[insidePointCount] = inputTriangle.vertices[2];
            insidePointCount++;
        } else {
            outsidePoints[outsidePointCount] = inputTriangle.vertices[2];
            outsidePointCount++;
        }

        if (insidePointCount == 0) {
            return null;
        }
        if (insidePointCount == 3) {
            return new Triangle[] { inputTriangle };
        }
        if (insidePointCount == 1 && outsidePointCount == 2) {
            Vec3D intersection1 = Utilities.getIntersection(insidePoints[0], outsidePoints[0], planePosition,
                    planeNormal);
            Vec3D intersection2 = Utilities.getIntersection(insidePoints[0], outsidePoints[1], planePosition,
                    planeNormal);
            return new Triangle[] {
                    new Triangle(insidePoints[0], intersection1, intersection2)
            };
        }
        if (insidePointCount == 2 && outsidePointCount == 1) {
            // this case the section of the triangle left inside is now has four vertices
            // so we turn it into two new triangles

            Vec3D intersection1 = Utilities.getIntersection(insidePoints[0], outsidePoints[0], planePosition,
                    planeNormal);
            Vec3D intersection2 = Utilities.getIntersection(insidePoints[1], outsidePoints[0], planePosition,
                    planeNormal);
            return new Triangle[] {
                    // this one gets the both inside points and intersection1
                    new Triangle(insidePoints[0], insidePoints[1], intersection1),
                    // woried about the order of this triangle but it gets both intersections and
                    // the second inside point
                    new Triangle(insidePoints[1], intersection2, intersection1)
            };
        }
        return null;
    }

    /**
     * gets the intersection of a line and a plane
     * 
     * @param a line start position
     * @param b line end position
     * @return
     */
    public static Vec3D getIntersection(Vec3D a, Vec3D b, Vec3D planePosition, Vec3D planeNormal) {
        planeNormal = normaliseVector(planeNormal);
        Vec3D line = vecSub(b, a);
        float d = dotProduct(vecSub(planePosition, a), planeNormal) / dotProduct(line, planeNormal);
        return vecAdd(a, new Vec3D(line.x * d, line.y * d, line.z * d));
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
    public static Vec3D multiplyMatrix(Vec3D vec, float[][] matrix) {
        float[] result = new float[matrix.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += vec.get(j) * matrix[i][j];
            }
        }
        return new Vec3D(result);
    }
}