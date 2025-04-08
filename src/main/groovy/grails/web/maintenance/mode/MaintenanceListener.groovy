package grails.web.maintenance.mode

/**
 * Interface for maintenance mode event listeners
 * Implement this interface to receive notifications when maintenance mode is toggled
 */
interface MaintenanceListener {
    /**
     * Called when maintenance mode is enabled
     * @param details The maintenance mode details
     */
    void onMaintenanceStart()

    /**
     * Called when maintenance mode is disabled
     */
    void onMaintenanceEnd()

}