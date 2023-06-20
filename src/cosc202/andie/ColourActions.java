package cosc202.andie;

import java.util.*;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /** The program's main icon, which will be displayed inside JOptionPane dialog messages. */
    private static Icon icon = Andie.getIcon();

    /** The JFrame to create pop up windows inside of. */
    private static JFrame parent = Andie.getFrame();

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(Language.getWord("Greyscale"), null, Language.getWord("Greyscale_desc"), Integer.valueOf((KeyEvent.VK_G))));
        actions.add(new BrightnessContrastAction(Language.getWord("BrightnessContrast"), null, Language.getWord("BrightnessContrast_desc"), Integer.valueOf(KeyEvent.VK_B)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Language.getWord("Colour"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action)).setAccelerator(KeyStroke.getKeyStroke((Integer) action.getValue("MnemonicKey"), InputEvent.CTRL_DOWN_MASK+InputEvent.ALT_DOWN_MASK));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(target.getSelection().isEmpty()) target.getImage().apply(new ConvertToGrey());
            else {
                Point[] corners = target.getSelection().getCorners();
                target.getImage().apply(new ConvertToGrey(corners[0], corners[1]));
            }
            target.repaint();
            target.getParent().revalidate();
        }

    }

        /**
     * <p>
     * Action to adjust the brightness/contrast of image.
     * </p>
     * 
     * @see BrightnessContrastAdjustment
     */
    public class BrightnessContrastAction extends ImageAction {

        /**
         * Create a new brightness-contrast action
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        BrightnessContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

         /**
         * <p>
         * Callback for when the brightness-contrast action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the BrightnessContrastAction is triggered.
         * It prompts the user for a brightness and a contrast percentage change, then applies the appropriate {@link BrightnessContrastAdjustment}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (target.getImage().hasImage() == false) {
                UserMessage.showWarning(UserMessage.NULL_FILE_WARN);
                return;
            }
            
            // Determine brightness/contrast - ask the user
            int brightness = 0;
            int contrast = 0;

            //Pop-up dialog box to ask for the brightness/contrast values
            SpinnerNumberModel brightnessModel = new SpinnerNumberModel(0, -100, 100, 1);
            SpinnerNumberModel contrastModel = new SpinnerNumberModel(0, -100, 100, 1);
            JSpinner brightnessSpinner = new JSpinner(brightnessModel);
            JSpinner contrastSpinner = new JSpinner(contrastModel);

            JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
            JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);

            class JSpinnerListener implements ChangeListener{
                public void stateChanged(ChangeEvent e){
                    try {
                        // set brightness and contrast slider to spinner values
                        brightnessSlider.setValue((int)brightnessSpinner.getValue());
                        contrastSlider.setValue((int)contrastSpinner.getValue());
                        target.getImage().previewApply(new BrightnessContrastAdjustment((int)brightnessSpinner.getValue(), (int)contrastSpinner.getValue()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    target.repaint();
                    target.getParent().revalidate();
                }
            }

            class JSliderListener implements ChangeListener{
                public void stateChanged(ChangeEvent e){
                    try {
                        // set brightness and contrast spinner to slider values
                        brightnessSpinner.setValue((int)brightnessSlider.getValue());
                        contrastSpinner.setValue((int)contrastSlider.getValue());
                        if(target.getSelection().isEmpty()) target.getImage().previewApply(new BrightnessContrastAdjustment((int)brightnessSlider.getValue(), (int)contrastSlider.getValue()));
                        else {
                            Point[] corners = target.getSelection().getCorners();
                            target.getImage().previewApply(new BrightnessContrastAdjustment((int)brightnessSlider.getValue(), (int)contrastSlider.getValue(), corners[0], corners[1]));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    target.repaint();
                    target.getParent().revalidate();
                }
            }

            // add listener to brightness and contrast spinners
            brightnessSpinner.addChangeListener(new JSpinnerListener());
            contrastSpinner.addChangeListener(new JSpinnerListener());
            
            // add listener to brightness and contrast sliders
            brightnessSlider.addChangeListener(new JSliderListener());
            contrastSlider.addChangeListener(new JSliderListener());
            
            JPanel spinnerPanel = new JPanel(new GridLayout(0, 1));
            spinnerPanel.add(new JLabel(Language.getWord("Brightness")));
            spinnerPanel.add(brightnessSlider);
            spinnerPanel.add(brightnessSpinner);
            spinnerPanel.add(new JLabel(Language.getWord("Contrast")));
            spinnerPanel.add(contrastSlider);
            spinnerPanel.add(contrastSpinner);

            int option = JOptionPane.showOptionDialog(ColourActions.parent, spinnerPanel, Language.getWord("EnterValue"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, ColourActions.icon, null, null);

            // Check return value from the dialog box
            if(option != JOptionPane.OK_OPTION) {
                target.getImage().previewApply(new BrightnessContrastAdjustment(0, 0));
                Andie.getImagePanel().getSelection().reset();

            }else {
                brightness = brightnessModel.getNumber().intValue();
                contrast = contrastModel.getNumber().intValue();

                // Create and apply filter
                if(target.getSelection().isEmpty()) target.getImage().apply(new BrightnessContrastAdjustment(brightness, contrast));
                else {
                    Point[] corners = target.getSelection().getCorners();
                    target.getImage().apply(new BrightnessContrastAdjustment(brightness, contrast, corners[0], corners[1]));
                }
            }


            target.repaint();
            target.getParent().revalidate();

        }
    }

}
