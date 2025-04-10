package grails.web.maintenance.mode

import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

/**
 * Bean that holds the maintenance properties across the application.
 * <p>
 * This class serves as a central repository for maintenance mode state,
 * providing access to current properties and methods to update them.
 * It's initialized during application startup and maintains state in-memory.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
class MaintenancePropertiesHolder {
    @Autowired
    MaintenanceConfigProvider configProvider

    private MaintenanceProperties properties

    MaintenanceProperties getProperties() {
        if (properties) {
            return properties
        }
        properties = configProvider.loadInitialProperties()
        return properties
    }

    void setEnabled(Boolean enabled) {
        properties.enabled = enabled
    }

    void setMessage(String message) {
        properties.message = message
    }

    void setEstimatedEndTime(LocalDateTime estimatedEndTime) {
        properties.estimatedEndTime = estimatedEndTime
    }

}
