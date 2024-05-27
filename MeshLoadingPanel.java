import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MeshLoadingPanel extends JPanel {

    MeshVeiwerPanel meshVeiwerPanel;

    public MeshLoadingPanel() {
        setLayout(new BorderLayout());
        meshVeiwerPanel = new MeshVeiwerPanel();
        setUpControlPanel();
        add(meshVeiwerPanel, BorderLayout.CENTER);
        meshVeiwerPanel.requestFocus();
        requestFocusInWindow();
        Main.frame.getRootPane().requestFocusInWindow(); // Focus on the root pane or another component

    }

    private void setUpControlPanel() {
        JPanel controlPanel = new JPanel();

        // Create text fields for distance, object field of view, etc.
        JTextField locationX = new JTextField("0", 3);
        JTextField locationY = new JTextField("0", 3);
        JTextField locationZ = new JTextField("5", 3);

        JSlider fieldOfViewSlider = new JSlider(30, 100, 80);

        JTextField fileName = new JTextField("teapot.obj", 15);

        // Create labels for the text fields
        JLabel distanceLabel = new JLabel("location (x, y, z):");
        JLabel fieldOfViewLabel = new JLabel("Field of View:");
        JLabel fileNameLabel = new JLabel("File Name:");

        JButton applyButton = new JButton("ReLoad Mesh");
        applyButton.addActionListener(e -> {
            meshVeiwerPanel.reloadMesh(fileName.getText(), Float.parseFloat(locationX.getText()),
                    Float.parseFloat(locationY.getText()),
                    Float.parseFloat(locationZ.getText()),
                    (float) ((fieldOfViewSlider.getValue() / 100.0) * Math.PI));
            meshVeiwerPanel.requestFocus();
        });

        fieldOfViewSlider.addChangeListener(e -> {
            meshVeiwerPanel.fov = (float) ((fieldOfViewSlider.getValue() / 100.0) * Math.PI);
            meshVeiwerPanel.requestFocus();
            meshVeiwerPanel.repaint();
        });
        // Add the labels and text fields to the control panel
        controlPanel.add(fileNameLabel);
        controlPanel.add(fileName);
        controlPanel.add(distanceLabel);
        controlPanel.add(locationX);
        controlPanel.add(locationY);
        controlPanel.add(locationZ);
        controlPanel.add(fieldOfViewLabel);
        controlPanel.add(fieldOfViewSlider);
        controlPanel.add(applyButton);
        add(controlPanel, BorderLayout.NORTH);
        fieldOfViewSlider.requestFocusInWindow();
        applyButton.requestFocusInWindow();
        fileName.setFocusTraversalKeysEnabled(false);

    }

}
