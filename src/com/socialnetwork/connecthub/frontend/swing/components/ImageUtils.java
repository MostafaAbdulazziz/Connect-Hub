package com.socialnetwork.connecthub.frontend.swing.components;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static ImageIcon loadImage(String imagePath, String defaultImagePath) {
        BufferedImage image = null;
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                image = ImageIO.read(imageFile);
            } else {
                System.out.println("Image not found at path: " + imagePath);
                image = ImageIO.read(new File(defaultImagePath));  // Load default image if not found
            }
        } catch (IOException e) {
            System.out.println("Error loading image at path: " + imagePath + " - " + e.getMessage());
            try {
                image = ImageIO.read(new File(defaultImagePath));  // Fallback to default image
            } catch (IOException fallbackException) {
                System.out.println("Error loading fallback image: " + fallbackException.getMessage());
                image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);  // Empty image as a last resort
            }
        }
        return new ImageIcon(image);
    }
}

