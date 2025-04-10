package grails.web.maintenance.mode

import grails.gorm.services.Service
import groovy.util.logging.Slf4j

import java.time.LocalDateTime

/**
 * Service that manages the maintenance mode state.
 * <p>
 * This service provides methods to start and stop maintenance mode
 * and handles any registered listeners when the maintenance state changes.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
@Slf4j
@Service
class MaintenanceService {

    MaintenancePropertiesHolder maintenancePropertiesHolder
    List<MaintenanceListener> maintenanceListeners

    /**
     * Starts maintenance mode.
     * <p>
     * This method enables maintenance mode, optionally sets a custom message
     * and estimated completion time, and publishes a maintenance started event
     * if maintenance was not already active.
     *
     * @param message The maintenance message to display, or null to use the default
     * @param estimatedEndTime The estimated time when maintenance will complete, or null for indefinite
     */
    void start(String message = null, LocalDateTime estimatedEndTime = null) {
        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        if (properties.enabled) {
            log.debug("Maintenance is already started.")
            return
        }
        maintenancePropertiesHolder.setEnabled(true)
        if (message) {
            maintenancePropertiesHolder.setMessage(message)
        }
        maintenancePropertiesHolder.setEstimatedEndTime(estimatedEndTime)
        maintenanceListeners.each {
            it.onMaintenanceStart()
        }
    }

    /**
     * Stops maintenance mode.
     * <p>
     * This method disables maintenance mode and runs any listeners implementing {@link MaintenanceListener}
     * if maintenance was previously active.
     */
    void stop() {
        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        if (!properties.enabled) {
            log.debug("Maintenance is already stopped.")
            return
        }
        maintenancePropertiesHolder.setEnabled(false)
        maintenanceListeners.each {
            it.onMaintenanceEnd()
        }
    }
}
