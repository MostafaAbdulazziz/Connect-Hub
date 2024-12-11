package com.socialnetwork.connecthub.frontend.swing.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class RoundedImageLabel extends JLabel {

    private int width;
    private int height;

    // Constructor to create a rounded image label with specified size
    public RoundedImageLabel(String imagePath, int width, int height) {

        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setImage(imagePath);
    }

    // Method to set the image
    public void setImage(String imagePath) {
        try {
            // Load the image
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Resize the image
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Create a circular clipped image
            BufferedImage roundedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = roundedImage.createGraphics();

            // Apply antialiasing for smoother edges
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create a circular clip
            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
            g2d.setClip(circle);

            // Draw the resized image into the circular clip
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            // Set the circular image as the icon of the label
            setIcon(new ImageIcon(roundedImage));
        } catch (Exception e) {
            e.printStackTrace();
            setText("Image not found");
        }
    }

    public void addActionListener(ActionListener listener) {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }
}

