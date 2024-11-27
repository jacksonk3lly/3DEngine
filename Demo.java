import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class Demo extends JPanel {
    MeshVeiwerPanel meshVeiwerPanel = new MeshVeiwerPanel();

    public Demo(){
        setLayout(new BorderLayout());
        add(meshVeiwerPanel, BorderLayout.CENTER);
        // meshVeiwerPanel.importMesh("duck.obj", new Vec3D(0, -0.5f, 6f));
        // meshVeiwerPanel.importMesh("cube.obj", new Vec3D(0, -0.5f, 6f));
            try {
            Mesh duck = new Mesh("duck.obj", new Vec3D(-4f, -0.5f, 6f));
            Mesh mountains = new Mesh("mountains.obj", new Vec3D(0, -10, +15));
            mountains.rotateY(Math.PI + Math.PI / 3);
            Mesh teaPot = new Mesh("teapot.obj", new Vec3D(+6, 1, 5f));
            duck.rotateY(Math.PI / 2);
            meshVeiwerPanel.importMesh(teaPot);
            meshVeiwerPanel.importMesh(duck);
            meshVeiwerPanel.importMesh(mountains);

            }catch(Exception e){
                System.out.println("File not found");
            }
    }
}
