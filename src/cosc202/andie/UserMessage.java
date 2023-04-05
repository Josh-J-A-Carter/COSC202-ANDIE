package cosc202.andie;

import javax.swing.*;

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
public class UserMessage {

    /**
     * The parent frame that the UserMessage instance will appear over.
     * If left blank, the pop-up box will instead appear in the middle of the
     * screen.
     */
    private static JFrame parent;
    /**The result when the user choses the "yes" option for a dialog message. */
    public static final int YES_OPTION = 0;
    /** The result when the user choses the "no" option for a dialog message. */
    public static final int NO_OPTION = 1;
    /** The result when the user choses the "cancel" option for a dialog message. */
    public static final int CANCEL_OPTION = 2;
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


    /**
     * Set the parent component for all UserMessage windows.
     * 
     * @param parent The parent JFrame instance
     */
    public static void setParent(JFrame parent) {
        UserMessage.parent = parent;
    }

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
        return showDialog(dialogOption, UserMessage.parent);
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
        int result = -1;
        String message = Language.getWord("DEFAULT_DIALOG");
        String title = Language.getWord("DIALOG_TITLE");

        //Dialog for when the user tries to overwrite a file.
        if(dialogOption.equalsIgnoreCase(UserMessage.OVERWRITE_EXISTING_FILE_DIALOG)){
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[]{Language.getWord("OVERWRITE_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.parent, message, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,possibleValues,possibleValues[1]);
            if(result == JOptionPane.NO_OPTION) result = JOptionPane.CANCEL_OPTION;
        }//Dialog for when the user tries to exit without saving changes.
        else if(dialogOption.equalsIgnoreCase(UserMessage.SAVE_AND_EXIT_DIALOG)){
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[]{Language.getWord("SAVE_AND_EXIT_OK"), Language.getWord("SAVE_AND_EXIT_NO"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
        }//Dialog for trying to open another file without saving changes.
        else if (dialogOption.equalsIgnoreCase(UserMessage.SAVE_AND_OPEN_DIALOG)) {
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[] {Language.getWord("SAVE_AND_OPEN_OK"), Language.getWord("SAVE_AND_OPEN_NO"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
        }//If the operations file is corrupted, ask if they want to delete it.
        else if (dialogOption.equalsIgnoreCase(UserMessage.DELETE_OPS_DIALOG)) {
            message = Language.getWord(dialogOption);
            Object[] possibleValues = new Object[] {Language.getWord("DELETE_OPS_OK"), Language.getWord("DEFAULT_CANCEL")};
            result = JOptionPane.showOptionDialog(UserMessage.parent, message, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
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

        // Make sure JOptionPane is in the right language
        UIManager.put("OptionPane.cancelButtonText", Language.getWord("OptionPane.cancelButtonText"));
        UIManager.put("OptionPane.okButtonText", Language.getWord("OptionPane.okButtonText"));

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * <p>
     * Show the user a warning window advising them that something has gone wrong, or that an action is disallowed.
     * </p>
     * 
     * @param warning The warning message to be displayed.
     */
    public static void showWarning(String warning){
        showWarning(warning, UserMessage.parent);
    }

}
