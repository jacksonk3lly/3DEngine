import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class SketchUpPanel extends JPanel {
    MeshVeiwerPanel meshVeiwerPanel = new MeshVeiwerPanel();
    public ArrayList<Triangle> selectedTriangles = new ArrayList<Triangle>();
    public ArrayList<Mesh> userCreation = new ArrayList<Mesh>();
    public int squareSize = 5;
    public Mesh plane;

    public SketchUpPanel(){
        setLayout(new BorderLayout());
        add(meshVeiwerPanel, BorderLayout.CENTER);
        meshVeiwerPanel.importMesh("duck.obj", new Vec3D(0, -0.5f, 6f));
        // meshVeiwerPanel.importMesh("cube.obj", new Vec3D(0, -0.5f, 6f));
        createPlaneOfSquares();
    }

    public void reset(){
        selectedTriangles = new ArrayList<Triangle>();
        // userCreation = new ArrayList<Mesh>();
        meshVeiwerPanel.removeMesh(plane);
        for(Mesh m : userCreation){
            meshVeiwerPanel.removeMesh(m);
        }
        userCreation = new ArrayList<Mesh>();
        createPlaneOfSquares();
    }

    public void undo(){
        if(userCreation.size() == 0){
            System.out.println("Already at oldest state");
            return;
        }
        meshVeiwerPanel.removeMesh(plane);
        meshVeiwerPanel.removeMesh(userCreation.get(userCreation.size()-1));
        userCreation.remove(userCreation.size()-1);
        deselect();
        createPlaneOfSquares();
        // mesh
    }

 
    
    public void extrudeSelectedTriangles() {
        ArrayList<Triangle> newTriangles = new ArrayList<>();
        ArrayList<Triangle> newSelectedTriangles = new ArrayList<>();
        float extrudeHeight = 2f;
        for (Triangle t : selectedTriangles) {
            Vec3D baseNormal = t.getNormal();
            baseNormal.normalize();
            baseNormal.multiply(extrudeHeight);
            Vec3D tLocation = t.getLocation();
            Vec3D v1 = t.getVertices()[0];
            v1 = new Vec3D(v1.x + tLocation.x, v1.y + tLocation.y, v1.z + tLocation.z);
            Vec3D v2 = t.getVertices()[1];
            v2 = new Vec3D(v2.x + tLocation.x, v2.y + tLocation.y, v2.z + tLocation.z);
            Vec3D v3 = t.getVertices()[2];
            v3 = new Vec3D(v3.x + tLocation.x, v3.y + tLocation.y, v3.z + tLocation.z);
            v1.y = v1.y + 0.001f;
            v2.y = v2.y + 0.001f;
            v3.y = v3.y + 0.001f;

            Vec3D v1h = new Vec3D(v1.x+baseNormal.x, v1.y + baseNormal.y, v1.z+ baseNormal.z);
            Vec3D v2h = new Vec3D(v2.x+baseNormal.x, v2.y + baseNormal.y, v2.z+ baseNormal.z);
            Vec3D v3h = new Vec3D(v3.x + baseNormal.x, v3.y + baseNormal.y, v3.z+ baseNormal.z);

            Triangle t1 = new Triangle(v1, v3h, v3, false);
            Triangle t2 = new Triangle(v1, v1h, v3h, false);

            Triangle t3 = new Triangle(v3, v2h, v2, false);
            Triangle t4 = new Triangle(v3, v3h, v2h, false);

            Triangle t5 = new Triangle(v2,v1h,v1,false);
            Triangle t6 = new Triangle(v2,v2h,v1h,false);

            Triangle tt = new Triangle(v1h, v2h, v3h, true);
            // selectedTriangles.add(tt);

            newTriangles.add(t1);
            newTriangles.add(t2);
            newTriangles.add(t3);
            newTriangles.add(t4);
            newTriangles.add(t5);
            newTriangles.add(t6);
            newTriangles.add(tt);
            plane.getTriangles().remove(t);
            newSelectedTriangles.add(tt);
        }

        deselect();
        selectedTriangles = newSelectedTriangles;
        Mesh newMesh = new Mesh(newTriangles);
        userCreation.add(newMesh);
        // plane.addTriangles(newTriangles);
        meshVeiwerPanel.importMesh(newMesh);
    }


    public void deselect(){
        for(Triangle t: selectedTriangles){
            t.setSelected(false);
        }
        selectedTriangles = new ArrayList<Triangle>();
    }


    private void createPlaneOfSquares() {
        ArrayList<Triangle> triangles = new ArrayList<>();

        // Define the size and number of squares
        int numSquares = 10;
        int height = -5;

        int xOffset = -squareSize * numSquares / 2;
        int zOffset = -squareSize * numSquares / 2;

        // Create the triangles for each square
        for (int i = 0; i < numSquares; i++) {
            for (int j = 0; j < numSquares; j++) {
                // Calculate the position of the square
                float x = i * squareSize;
                float z = j * squareSize;

                /* 
                Create the vertices of the square

                v3----v4
                |      |
                |      |
                v1----v2

                */
                Vec3D v1 = new Vec3D(x + xOffset, height, z + zOffset);
                Vec3D v2 = new Vec3D(x+ xOffset + squareSize, height, z+zOffset);
                Vec3D v3 = new Vec3D(x+ xOffset, height, z+squareSize+zOffset);
                Vec3D v4 = new Vec3D(x+squareSize+xOffset, height, z+squareSize+zOffset);

                // Create the triangles for the square
                Triangle t1 = new Triangle(v1, v3, v4, false);
                Triangle t2 = new Triangle(v1, v4, v2, false);

                // Add the triangles to the list
                triangles.add(t1);
                triangles.add(t2);
            }
        }

        // Create the mesh using the triangles
        plane = new Mesh(triangles);

        // Add the mesh to the panel
        meshVeiwerPanel.importMesh(plane);
    }
    
}