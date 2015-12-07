package fpt.com.component.view;

/**
 * Tooltip
 */
public class Tooltip {
    public static void disappearingTooltip() {
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip("asdasdasd");

        tooltip.setAutoHide(true);


    }
}
