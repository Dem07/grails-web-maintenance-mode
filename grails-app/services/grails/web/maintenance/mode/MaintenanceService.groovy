package grails.web.maintenance.mode

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

@Slf4j
class MaintenanceService {

    @Autowired
    MaintenancePropertiesHolder maintenancePropertiesHolder
    @Autowired
    List<MaintenanceListener> maintenanceListeners

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
        maintenanceListeners.each {
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
        maintenanceListeners.each {
            it.onMaintenanceEnd()
        }
    }
}
