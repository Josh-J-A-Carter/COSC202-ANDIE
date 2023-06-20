package cosc202.andie;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import cosc202.andie.draw.Selection;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * The current selection.
     */
    private Selection selection;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        selection = new Selection(this);
        scale = 1.0;

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selection.mousePressed(e, scale);
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                selection.mouseDragged(e, scale);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                selection.mouseReleased(e);
            }
        });
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current selection
     * </p>
     * 
     * @return The {@code Selection} instance being used in the {@code ImagePanel}.
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 50) {
            zoomPercent = 50;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Get the location of the top left corner of an image to be centered
     * </p>
     * 
     * <p>
     * Returns the location of where the top left corner of an image should be for it to be centered
     * on the image panel.
     * </p>
     */
    public Dimension getCenteredImageLocation() {
        return new Dimension((int) ((this.getWidth()/(2*scale))-(image.getDimensions().getWidth()/2)),
                            (int) ((this.getHeight()/(2*scale))-(image.getDimensions().getHeight()/2)));
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(0, 0);    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, (int) (this.getCenteredImageLocation().getWidth()),
                                                        (int) (this.getCenteredImageLocation().getHeight()));
            g2.dispose();
        }
        repaint();
    }
}
