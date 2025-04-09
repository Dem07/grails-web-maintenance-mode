package grails.web.maintenance.mode

import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

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
