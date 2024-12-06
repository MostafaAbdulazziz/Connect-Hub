package com.socialnetwork.connecthub.frontend.swing.components;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;

public class JButton extends JLabel {

    private Shape shape;
    private int radius;
    private ActionListener actionListener;

    public JButton(String text, int radius, int textSize) {
        super(text);
        this.radius = radius;
        setFont(new Font("Segoe UI", Font.BOLD, textSize));
        setOpaque(false);
        setForeground(GUIConstants.white);
        setHorizontalAlignment(CENTER);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener to handle click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(null); // Call action listener when clicked
                }
            }
        });
    }

    // Set the action listener
    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    // For rounded corners
    protected void paintComponent(Graphics g) {
        g.setColor(GUIConstants.blue);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(g);
    }

    // For rounded border
    protected void paintBorder(Graphics g) {
        g.setColor(GUIConstants.blue);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 45, 45);
        }
        return shape.contains(x, y);
    }

}
