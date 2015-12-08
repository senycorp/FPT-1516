package fpt.com.component.view;

import fpt.com.controller.ControllerShop;
import fpt.com.core.ControllerManager;

/**
 * Tooltip
 */
public class Tooltip {

    /**
     * Activate/Deactivate tooltips
     */
    private static boolean active = false;

    /**
     * Disappearing Tooltip-Factory
     *
     * @param text
     */
    public static void disappearingTooltip(String text) {
        if (isActive()) {
            try {
                javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(text);
                tooltip.setAutoHide(true);
                tooltip.show(((ControllerShop) ControllerManager.getInstance().get("shop")).getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check status
     *
     * @return
     */
    public static boolean isActive() {
        return active;
    }

    /**
     * Set status
     *
     * @param active
     */
    public static void setActive(boolean active) {
        Tooltip.active = active;
    }
}
