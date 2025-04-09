package grails.web.maintenance.mode

import groovy.util.logging.Slf4j

import java.time.LocalDateTime

@Slf4j
class MaintenanceService {

    MaintenancePropertiesHolder maintenancePropertiesHolder
    List<MaintenanceListener> listeners

    void start(String message = null, LocalDateTime estimatedEndTime = null) {
        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        if (properties.enabled) {
            log.debug("Maintenance is already enabled.")
            return
        }
        maintenancePropertiesHolder.setEnabled(true)
        if (message) {
            maintenancePropertiesHolder.setMessage(message)
        }
        maintenancePropertiesHolder.setEstimatedEndTime(estimatedEndTime)
        listeners.each {
            it.onMaintenanceStart()
        }
    }

    void stop() {
        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        if (!properties.enabled) {
            log.debug("Maintenance is already disabled.")
            return
        }
        maintenancePropertiesHolder.setEnabled(false)
        listeners.each {
            it.onMaintenanceEnd()
        }
    }
}
