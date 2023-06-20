package cosc202.andie;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import java.awt.Color;
import java.awt.Dimension;

/**
 * <p>
 * Create a pop up box with {@code showWarning()} to notify the user when an error has occurred, instead of
 * crashing with no explanation. Or, to ask the user for input using the {@code showDialog()} method.
 * </p>
 * 
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see javax.swing.JOptionPane
 * @author Joshua Carter
 */
public abstract class UserMessage {

    /** The parent frame that the UserMessage instance will appear over.
     * If left blank, the pop-up box will instead appear in the middle of the screen. */
    private static JFrame PARENT = Andie.getFrame();
    /** Take the main icon being stored in Andie.java and save this as the icon for pop-up boxes. */
    public static final Icon ICON = Andie.getIcon();
    /** The dimensions of the scroll pane used for displaying a scrollable selection of items */
    private static final Dimension SCROLL_DIMENSIONS = new Dimension(400,100);

    /** The option that is returned if an error occurs. */
    public static final int ERROR_OPTION = -10;
    /**The result when the user choses the "yes" option for a dialog message. */
    public static final int YES_OPTION = 0;
    /** The result when the user choses the "no" option for a dialog message. */
    public static final int NO_OPTION = 1;
    /** The result when the user choses the "cancel" option for a dialog message. */
    public static final int CANCEL_OPTION = -1;
    /** The result when the user closes the dialog message windo4w. */
    public static final int CLOSED_OPTION = 3;

    /** The dialog option asking the user whether they would like to overwrite the existing file, or not. */
    public static final String OVERWRITE_EXISTING_FILE_DIALOG = "OVERWRITE_EXISTING_FILE_DIALOG";
    /** The dialog option asking the user whether they would like to save and exit, or exit without saving, or cancel. */
    public static final String SAVE_AND_EXIT_DIALOG = "SAVE_AND_EXIT_DIALOG";
    /** The dialog option asking the user whether they would like to save the current file and option the selection,
     * or open the selection without saving the current file, or cancel. */
    public static final String SAVE_AND_OPEN_DIALOG = "SAVE_AND_OPEN_DIALOG";
    /** The dialog option informing the user that the operations file is corrupted, and asking if they would like to
     * continue opening the image by first deleting the operations file. */
    public static final String DELETE_OPS_DIALOG = "DELETE_OPS_DIALOG";
    /** The scroll pane option for when the user tries to paste multiple image files into ANDIE.
     * The user is asked which file they would like to open from a list of available images.*/
    public static final String PASTE_FILES_SCROLL = "PASTE_FILES_DIALOG";
    /** A dialog option asking the user whether they would like to continue with the selected process
     * (and stop recording) or cancel as there is a recording in process. */
    public static final String RECORDING_END_DIALOG = "RECORDING_END_DIALOG";
    /** A dialog option asking the user whether they would like to continue with the selected process
     * (and continue recording) or cancel as there is a recording in process. */
    public static final String RECORDING_CONTINUE_DIALOG = "RECORDING_CONTINUE_DIALOG";



    /** A generic warning to tell the user that an error has occurred. */
    public static final String GENERIC_WARN = "GENERIC_WARN";
    /** A warning to tell the user that the redo stack is empty. */
    public static final String EMPTY_REDO_STACK_WARN = "EMPTY_REDO_STACK_WARN";
    /** A warning to tell the user that the undo stack is empty. */
    public static final String EMPTY_UNDO_STACK_WARN = "EMPTY_UNDO_STACK_WARN";
    /** A warning to tell the user that the image file they are trying to open is in an unreadable format. */
    public static final String INVALID_IMG_FILE_WARN = "INVALID_IMG_FILE_WARN";
    /** A warning to tell the user that the operations file they are trying to open is unreadable. */
    public static final String INVALID_OPS_FILE_WARN = "INVALID_OPS_FILE_WARN";
    /** A warning to tell the user that no file is currently open. */
    public static final String NULL_FILE_WARN = "NULL_FILE_WARN";
    /** A warning to tell the user that the file the program can only open image files. */
    public static final String NON_IMG_FILE_WARN = "NON_IMG_FILE_WARN";
    /** A warning to tell the user that the file must be an ops file. */
    public static final String NON_OPS_FILE_WARN = "NON_OPS_FILE_WARN";
    /** A warning to tell the user that a fatal error has occurred. */
    public static final String FATAL_ERROR_WARN = "FATAL_ERROR_WARN";
    /** A warning to tell the user that the desired file was not found. */
    public static final String FILE_NOT_FOUND_WARN = "FILE_NOT_FOUND_WARN";
    /** A warning to tell the user that the entered file path is invalid. */
    public static final String INVALID_PATH_WARN = "INVALID_PATH_WARN";
    /** A warning to tell the user that the program does not have permission to open the desired file. */
    public static final String SECURITY_WARN = "SECURITY_WARN";
    /** A warning to tell the user that some values in the desired language are missing. */
    public static final String MISSING_LANG_WARN = "MISSING_LANG_WARN";
    /** A warning to tell the user that a fatal language error has occurred, and the program cannot continue. */
    public static final String FATAL_LANG_WARN = "FATAL_LANG_WARN";
    /** A warning to tell the user that they have not selected an area to operate on */
    public static final String EMPTY_SELECTION_WARN = "EMPTY_SELECTION_WARN";
    /** A warning to tell the user that the selected ops file cannot be read */
    public static final String UNREADABLE_OPS_FILE_WARN = "UNREADABLE_OPS_FILE_WARN";
    /** A warning to tell the user that the selected ops file is outdated and so cannot be applied */
    public static final String OUTDATED_OPS_FILE_WARN = "OUTDATED_OPS_FILE_WARN";


    /**
     * <p>
     * Show the user a dialog window, asking them to choose between different options.
     * </p>
     * 
     * @param dialogOption The type of query in the dialog box - e.g. if they would like to overwrite
     * the existing file, or save before exiting with unsaved changes.
     * @return The user's answer in response to the dialog - e.g. they may choose {@code UserMessage.YES_OPTION}
     * when asked shown the dialog for {@code UserMessage.showDialog(UserMessage.SAVE_AND_EXIT_DIALOG)}
     */
    public static int showDialog(String dialogOption){
        return showDialog(dialogOption, UserMessage.PARENT);
    }

    /**
     * <p>
     * Show the user a dialog window, asking them to choose between different options.
     * </p>
     * 
     * @param dialogOption The type of query in the dialog box - e.g. if they would like to overwrite
     * the existing file, or save before exiting with unsaved changes.
     * @param parent The parent component for the dialog box to appear over.
     * @return The user's answer in response to the dialog - e.g. they may choose {@code UserMessage.YES_OPTION}
     * when asked shown the dialog for {@code UserMessage.showDialog(UserMessage.SAVE_AND_EXIT_DIALOG)}
     */
    public static int showDialog(String dialogOption, JFrame parent){
        int result = UserMessage.CANCEL_OPTION;
        String message = Language.getWord("DEFAULT_DIALOG");
        String title = Language.getWord("DIALOG_TITLE");

        //Dialog for when the user tries to overwrite a file.
        if(dialogOption.equalsIgnoreCase(UserMessage.OVERWRITE_EXISTING_FILE_DIALOG)){
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[]{Language.getWord("OVERWRITE_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, message, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, ICON, possibleValues,possibleValues[1]);
            if(result == JOptionPane.NO_OPTION) result = JOptionPane.CANCEL_OPTION;
        }//Dialog for when the user tries to exit without saving changes.
        else if(dialogOption.equalsIgnoreCase(UserMessage.SAVE_AND_EXIT_DIALOG)){
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[]{Language.getWord("SAVE_AND_EXIT_OK"), Language.getWord("SAVE_AND_EXIT_NO"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, ICON, possibleValues, possibleValues[0]);
        }//Dialog for trying to open another file without saving changes.
        else if (dialogOption.equalsIgnoreCase(UserMessage.SAVE_AND_OPEN_DIALOG)) {
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[] {Language.getWord("SAVE_AND_OPEN_OK"), Language.getWord("SAVE_AND_OPEN_NO"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, ICON, possibleValues, possibleValues[0]);
        }//If the operations file is corrupted, ask if they want to delete it.
        else if (dialogOption.equalsIgnoreCase(UserMessage.DELETE_OPS_DIALOG)) {
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[] {Language.getWord("DELETE_OPS_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, message, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, ICON, possibleValues, possibleValues[0]);
            if(result == JOptionPane.NO_OPTION) result = JOptionPane.CANCEL_OPTION;
        }else if(dialogOption.equalsIgnoreCase(UserMessage.RECORDING_END_DIALOG) || dialogOption.equalsIgnoreCase(UserMessage.RECORDING_CONTINUE_DIALOG)){
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[] {Language.getWord("RECORDING_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, message, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, ICON, possibleValues, possibleValues[0]);
            if(result == JOptionPane.NO_OPTION) result = JOptionPane.CANCEL_OPTION;
        }
        else{ //If it's not one of the expected dialog options, then that is an illegal argument.
            throw new IllegalArgumentException("Invalid dialog option.");
        }

        if(result == JOptionPane.YES_OPTION) return UserMessage.YES_OPTION;
        else if(result == JOptionPane.NO_OPTION) return UserMessage.NO_OPTION;
        else if(result == JOptionPane.CANCEL_OPTION) return UserMessage.CANCEL_OPTION;
        else if(result == JOptionPane.CLOSED_OPTION) return UserMessage.CLOSED_OPTION;
        else return -1; // If something goes wrong
    }

    /**
     * <p>
     * Show the user a JOptionPane with a scroll panel inside,
     * containing all of the options to choose from.
     * There are also the OK, CANCEL, and CLOSE buttons.
     * </p>
     * 
     * <p>
     * If the user hits OK, the currently selected item's
     * index in the array is returned. Otherwise, the result will
     * equal {@code UserMessage.CANCEL_OPTION}
     * </p>
     * 
     * 
     * @param scrollOption The type of scroll dialog to be displayed.
     * @param listItems The array of items to chose from, as strings.
     * @return The index of the selected item from the inputted array.
     * If the user closed or cancelled the dialog, then 
     * {@code UserMessage.CANCEL_OPTION} is returned.
     */
    public static int showScroll(String scrollOption, String[] listItems){
        int result = UserMessage.CANCEL_OPTION; //Default is the 'cancel' option
        JList<String> scrollList = new JList<>(listItems); scrollList.setSelectedIndex(0); // Select the first item by default.
        JScrollPane scrollPane = new JScrollPane(scrollList); scrollPane.setPreferredSize(SCROLL_DIMENSIONS);
        String title = Language.getWord("SCROLL_TITLE");

        if(scrollOption.equalsIgnoreCase(UserMessage.PASTE_FILES_SCROLL)){
            title = Language.getWord("PASTE_FILES_TITLE");
            Object[] possibleValues = new Object[]{Language.getWord("PASTE_FILES_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.PARENT, scrollPane, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, UserMessage.ICON, possibleValues, possibleValues[0]);
            //The user hits cancels
            if(result != JOptionPane.YES_OPTION) return UserMessage.CANCEL_OPTION;
            result = scrollList.getSelectedIndex();
        }
        else{
            throw new IllegalArgumentException("Invalid scrollpane option.");
        }
        return result;
    }

    /**
     * <p>
     * Asks the user to select a colour.
     * </p>
     * 
     * @param original The colour to be displayed when the colour chooser is initially opened.
     * @param hasAlpha Whether or not the user should be able to select transparency.
     * @return The selected colour, or if none was selected, the original.
     */
    public static Color showColourChooser(Color original, boolean hasAlpha){
        String title = Language.getWord("PICK_COLOUR_TITLE");
        JColorChooser chooser = new JColorChooser(original);
        AbstractColorChooserPanel panel = chooser.getChooserPanels()[1];
        panel.setColorTransparencySelectionEnabled(hasAlpha);

        Object[] possibleValues = new Object[]{Language.getWord("PICK_COLOUR_OK"), Language.getWord("DEFAULT_CANCEL")};
        int result = JOptionPane.showOptionDialog(UserMessage.PARENT, panel, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, UserMessage.ICON, possibleValues, possibleValues[0]);
        //The user hits cancels
        if(result != JOptionPane.YES_OPTION) return original;
        //Otherwise, return the current selection
        return chooser.getColor();
    }

    /**
     * <p>
     * Show the user a warning window advising them that something has gone wrong, or that an action is disallowed.
     * </p>
     * 
     * @param warning The warning message to be displayed.
     * @param parent The parent component for the warning window to appear over.
     */
    public static void showWarning(String warning, JFrame parent) {
        String message;
        String title;

        //I have not used a Language.getWord() call, because it may not be retrievable due to issues with the language file.
        if(warning.equalsIgnoreCase(FATAL_LANG_WARN)){
            message = "ANDIE encountered a fatal error accessing the\nlanguage assets. Please make sure the program\nis being opened from the ANDIE directory, and contact the\nadministrators if this problem persists.";
            title = "ANDIE Fatal Error Message";
        }
        else{
            message = Language.getWord(warning);
            title = Language.getWord("ERROR_TITLE"); //Can't have this out in the open, otherwise it creates an infinite loop
        }

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE, ICON);
    }

    /**
     * <p>
     * Show the user a warning window advising them that something has gone wrong, or that an action is disallowed.
     * </p>
     * 
     * @param warning The warning message to be displayed.
     */
    public static void showWarning(String warning){
        showWarning(warning, UserMessage.PARENT);
    }

}
