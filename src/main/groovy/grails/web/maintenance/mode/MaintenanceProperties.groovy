package grails.web.maintenance.mode

import java.time.LocalDateTime

class MaintenanceProperties {
    Boolean enabled = false
    String message = "System is currently undergoing maintenance."
    LocalDateTime estimatedEndTime
    List<String> excludedUris = []
    List<String> excludedControllers = []
}