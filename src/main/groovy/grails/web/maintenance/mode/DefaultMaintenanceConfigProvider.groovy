package grails.web.maintenance.mode

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.util.logging.Slf4j

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Slf4j
class DefaultMaintenanceConfigProvider implements MaintenanceConfigProvider, GrailsConfigurationAware {
    Config config

    @Override
    void setConfiguration(Config config) {
        this.config = config
    }

    @Override
    MaintenanceProperties loadInitialProperties() {
        MaintenanceProperties properties = new MaintenanceProperties()
        // Load from application.yml
        properties.enabled = config.getProperty('maintenanceMode.enabled', Boolean, false)
        properties.message = config.getProperty('maintenanceMode.message', String, properties.message)
        properties.excludedUris = config.getProperty('maintenanceMode.excludedUris', List, []) as List<String>
        properties.excludedControllers = config.getProperty('maintenanceMode.excludedControllers', List, []) as List<String>

        // Handle completion time if specified
        String completionTimeStr = config.getProperty('maintenanceMode.completionTime', String, null)
        if (completionTimeStr) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                properties.estimatedEndTime = LocalDateTime.parse(completionTimeStr, formatter)
            } catch (Exception e) {
                log.error("Could not parse maintenance completion time: $completionTimeStr", e)
            }
        }

        return properties
    }
}
