package org.example;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Pretty much a Waypoint that also acts as a button to go to FRC teams, YAY!
 */
public class ButtonWaypoint extends DefaultWaypoint {
    private final JButton button;
    URI uri;
    Desktop desktop = Desktop.getDesktop();

    private int teamNumber;
    private String name;

    /**
     * Constructor for the button
     * @param text The name of the FRC Team
     * @param coord Pretty much the coordinates given in {@link Main} to act as central points
     * @param teamNumber The team number, it's used in the URI (Uniform Resource Identifier) to open up your browser to go to that team.
     * @throws URISyntaxException
     */
    public ButtonWaypoint(String text, GeoPosition coord, int teamNumber) throws URISyntaxException {
        super(coord);
        this.teamNumber = teamNumber;
        this.name = text;
        this.uri = new URI("https://thebluealliance.com/" + teamNumber);

        // 1. Fetch proper background icon based on your existing fallback logic
        String path1 = "src/main/resources/icons/" + teamNumber + ".png";
        String path2 = "src/main/resources/icons/frc" + teamNumber + ".png";
        String defaultPath = "src/main/resources/icons/default.png";

        String finalPath = defaultPath;
        if (new File(path1).exists()) finalPath = path1;
        else if (new File(path2).exists()) finalPath = path2;

        ImageIcon originalIcon = new ImageIcon(finalPath);

        // 2. Clear button text and generate a custom outlined text image
        this.button = new JButton();
        this.button.setIcon(createOutlinedTextIcon(originalIcon, text));

        // 3. Styling the button
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.button.setOpaque(false);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);

        // Remove internal Swing button padding
        this.button.setMargin(new java.awt.Insets(0, 0, 0, 0));

        // 4. Action listener
        this.button.addActionListener(e -> {
            try {
                if (desktop != null) {
                    desktop.browse(uri);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * A thing that is only used in {@link ButtonWaypointRenderer}
     * @return Returns the button inside this class
     */
    public JButton getButton() {
        return button;
    }

    public void setVisible(boolean aFlag){
        this.setVisible(aFlag);
    }

    private ImageIcon createOutlinedTextIcon(ImageIcon baseIcon, String label) {
        int iconWidth = baseIcon.getIconWidth();
        int iconHeight = baseIcon.getIconHeight();

        Font font = new Font("Arial", Font.BOLD, 12);

        // Create a temporary graphic to calculate string width
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG = tempImg.createGraphics();
        tempG.setFont(font);
        FontMetrics fm = tempG.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        int textHeight = fm.getHeight();
        tempG.dispose();

        // Canvas wide and tall enough to prevent text clipping
        int canvasWidth = Math.max(iconWidth, textWidth) + 15;
        int canvasHeight = iconHeight + textHeight + 10;

        BufferedImage combined = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        // Enable high-quality text rendering
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 1. Draw the centered team icon at the top
        int iconX = (canvasWidth - iconWidth) / 2;
        g.drawImage(baseIcon.getImage(), iconX, 0, null);

        // 2. Setup positions for the centered text at the bottom
        int textX = (canvasWidth - textWidth) / 2;
        int textY = iconHeight + fm.getAscent() + 5;

        g.setFont(font);

        // 3. Draw a thick black outline (glow effect)
        g.setColor(Color.BLACK);
        for (int xOffset = -2; xOffset <= 2; xOffset++) {
            for (int yOffset = -2; yOffset <= 2; yOffset++) {
                if (xOffset != 0 || yOffset != 0) {
                    g.drawString(label, textX + xOffset, textY + yOffset);
                }
            }
        }

        // 4. Draw the core white text over the outline
        g.setColor(Color.WHITE);
        g.drawString(label, textX, textY);

        g.dispose();
        return new ImageIcon(combined);
    }
}