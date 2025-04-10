package grails.web.maintenance.mode

/**
 * Interface for maintenance mode event listeners
 * Implement this interface to receive notifications when maintenance mode is toggled
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
interface MaintenanceListener {
    /**
     * Called when maintenance mode is enabled
     */
    void onMaintenanceStart()

    /**
     * Called when maintenance mode is disabled
     */
    void onMaintenanceEnd()

}