package grails.web.maintenance.mode

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

@Slf4j
class MaintenanceService {
    @Autowired
    MaintenancePropertiesHolder propertiesHolder
    @Autowired
    List<MaintenanceListener> listeners

    void start(String message = null, LocalDateTime estimatedEndTime = null) {
        MaintenanceProperties properties = propertiesHolder.getProperties()
        if (properties.enabled) {
            log.debug("Maintenance is already enabled.")
            return
        }
        propertiesHolder.setEnabled(true)
        if (message) {
            propertiesHolder.setMessage(message)
        }
        propertiesHolder.setEstimatedEndTime(estimatedEndTime)
        listeners.each {
            it.onMaintenanceStart()
        }
    }

    void stop() {
        MaintenanceProperties properties = propertiesHolder.getProperties()
        if (!properties.enabled) {
            log.debug("Maintenance is already disabled.")
            return
        }
        propertiesHolder.setEnabled(false)
        listeners.each {
            it.onMaintenanceEnd()
        }
    }
}
