package cosc202.andie;

import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Transform menu.
 * </p>
 *  
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @version 1.0
 */
public class TransformActions {

    /** A list of actions for the Transform menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Transform menu actions.
     * </p>
     */
    public TransformActions() {
        actions = new ArrayList<Action>();
        actions.add(new FlipImageAction(Language.getWord("FlipHorizontal"), null, Language.getWord("FlipHorizontal_desc"),
                Integer.valueOf(KeyEvent.VK_H), "horizontal"));
        actions.add(new FlipImageAction(Language.getWord("FlipVertical"), null, Language.getWord("FlipVertical_desc"),
                Integer.valueOf(KeyEvent.VK_V), "vertical"));
        actions.add(new RotateImageAction(Language.getWord("Rotate180"), null, Language.getWord("Rotate180_desc"),
        null, 180));
        actions.add(new RotateImageAction(Language.getWord("Rotate90Right"), null, Language.getWord("Rotate90Right_desc"),
        null, 90));
        actions.add(new RotateImageAction(Language.getWord("Rotate90Left"), null, Language.getWord("Rotate90Left_desc"),
                null, 270));
        actions.add(new RotateImageActionInput(Language.getWord("CustomRotation"), null, Language.getWord("Rotate_desc"),
                Integer.valueOf(KeyEvent.VK_T), true,-180, 180, 0, 0));
        actions.add(new ResizeImageAction(Language.getWord("Resize"), null, Language.getWord("Resize_desc"),
                Integer.valueOf(KeyEvent.VK_T), true, 50, 200, 100, 100));
        actions.add(new CropImageAction(Language.getWord("Crop"), null, Language.getWord(Language.getWord("Crop_desc")),
                Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu containing the list of Transform actions.
     * </p>
     * 
     * @return The Transform menu UI element.
     */
    public JMenu createMenu() {
        JMenu transformMenu = new JMenu(Language.getWord("Transform"));
        JMenu rotateMenu = new JMenu(Language.getWord("Rotate"));
        

        for (Action action : actions) {
            if(action instanceof FlipImageAction){
                transformMenu.add(new JMenuItem(action)).setAccelerator(KeyStroke.getKeyStroke((Integer) action.getValue("MnemonicKey"), InputEvent.CTRL_DOWN_MASK+InputEvent.ALT_DOWN_MASK));
            } else if (action instanceof RotateImageAction || action instanceof RotateImageActionInput) {
                if(action.getValue("MnemonicKey") == null){
                    rotateMenu.add(new JMenuItem(action));
                }else{
                    rotateMenu.add(new JMenuItem(action)).setAccelerator(KeyStroke.getKeyStroke((Integer) action.getValue("MnemonicKey"), InputEvent.CTRL_DOWN_MASK+InputEvent.ALT_DOWN_MASK));

                }
            } else if(action instanceof CropImageAction) {
                transformMenu.add(new JMenuItem(action)).setAccelerator(KeyStroke.getKeyStroke((Integer) action.getValue("MnemonicKey"), InputEvent.CTRL_DOWN_MASK+InputEvent.ALT_DOWN_MASK));
            }else {
                transformMenu.add(new JMenuItem(action)).setAccelerator(KeyStroke.getKeyStroke((Integer) action.getValue("MnemonicKey"), InputEvent.CTRL_DOWN_MASK));
            }
        }

        transformMenu.add(rotateMenu);

        return transformMenu;
    }

    /**
     * <p>
     * Action to flip an image.
     * </p>
     * 
     * @see FlipImage
     */
    public class FlipImageAction extends ImageAction {

        private String direction;

        /**
         * <p>
         * Create a new flip-image action.
         * </p>
         * 
         * @param name      The name of the action (ignored if null).
         * @param icon      An icon to use to represent the action (ignored if null).
         * @param desc      A brief description of the action (ignored if null).
         * @param mnemonic  A mnemonic key to use as a shortcut (ignored if null).
         * @param direction The line the flip will occur on.
         */
        FlipImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, String direction) {
            super(name, icon, desc, mnemonic);
            this.direction = direction;
        }

        /**
         * <p>
         * Callback for when the flip-image action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipImageAction is triggered.
         * It Flips the image over a line deciphered from the direction data-field.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new FlipImage(this.direction));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RotateImageAction extends ImageAction {

        private int rotation;

        /**
         * <p>
         * Create a new rotate-image action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param rotation The direction and degree of rotation.
         */
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, int rotation) {
            super(name, icon, desc, mnemonic);
            this.rotation = rotation;
        }

        /**
         * <p>
         * Callback for when the rotate-image action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It Rotates the image based on the degree and direction given by the
         * rotation datafield.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new RotateImage(rotation));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RotateImageActionInput extends UserInput {

        /**
         * <p>
         * Create a new rotate-image action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param rotation The direction and degree of rotation.
         */
        RotateImageActionInput(String name, ImageIcon icon, String desc, Integer mnemonic,
                            boolean slider, int min, int max, int val, int zeroVal) {
            super(name, icon, desc, mnemonic, slider, min, max, val, zeroVal);
        }

        @Override
        Object mutateImage(int input) {
            return new RotateImage(input);
        }
    }

    public class ResizeImageAction extends UserInput {

        /**
         * <p>
         * Create a new rotate-image action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param rotation The direction and degree of rotation.
         */
        ResizeImageAction(String name, ImageIcon icon, String desc, Integer mnemonic,
                            boolean slider, int min, int max, int val, int zeroVal) {
            super(name, icon, desc, mnemonic, slider, min, max, val, zeroVal);
        }

        @Override
        Object mutateImage(int input) {
            return new ResizeImage(input);
        }
    }

    public class CropImageAction extends ImageAction {

        int x1, y1, x2, y2;

        public CropImageAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

		@Override
		public void actionPerformed(ActionEvent e) {

            if(target.getImage().hasImage() == false){
                UserMessage.showWarning(UserMessage.NULL_FILE_WARN);
                return;
            }

            if(target.getSelection().isEmpty()){
                UserMessage.showWarning(UserMessage.EMPTY_SELECTION_WARN);
                return;
            }

            Point[] corners = target.getSelection().getCorners();

            target.getImage().apply(new CropImage(corners[0], corners[1]));
            target.getSelection().reset();

            target.repaint();
            target.getParent().revalidate();
		}

    }

}
