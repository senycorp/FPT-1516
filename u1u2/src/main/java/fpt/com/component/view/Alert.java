package fpt.com.component.view;

/**
 * Alert-Dialogs
 *
 * @author senycorp
 */
public class Alert {

    /**
     * Warning-Dialog
     *
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static javafx.scene.control.Alert warning(String title, String header, String content) {
        return createDialog(javafx.scene.control.Alert.AlertType.WARNING,
                            "Warning: " + title,
                            header,
                            content);
    }

    /**
     * Info-Dialog
     *
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static javafx.scene.control.Alert info(String title, String header, String content) {
        return createDialog(javafx.scene.control.Alert.AlertType.INFORMATION,
                            "Info: " + title,
                            header,
                            content);
    }

    /**
     * Error-Dialog
     *
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static javafx.scene.control.Alert error(String title, String header, String content) {
        return createDialog(javafx.scene.control.Alert.AlertType.ERROR,
                            "Error: " + title,
                            header,
                            content);
    }

    /**
     * Alert-Factory
     *
     * @param type
     * @return
     */
    public static javafx.scene.control.Alert factory(javafx.scene.control.Alert.AlertType type) {
        return new javafx.scene.control.Alert(type);
    }

    /**
     * Creates dialog with all necessary information
     *
     * @param type
     * @param title
     * @param header
     * @param content
     * @return
     */
    private static javafx.scene.control.Alert createDialog(
            javafx.scene.control.Alert.AlertType type, String title, String header, String content) {
        javafx.scene.control.Alert alert = factory(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);

        return alert;
    }
}
