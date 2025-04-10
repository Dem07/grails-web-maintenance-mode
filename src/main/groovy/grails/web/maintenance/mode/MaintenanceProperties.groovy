package grails.web.maintenance.mode

import java.time.LocalDateTime

/**
 * Holds the current state of the maintenance mode configuration.
 * <p>
 * This class encapsulates all properties related to maintenance mode including
 * whether it's enabled, the message to display to users, estimated completion time,
 * and any URIs or controllers that should be excluded from maintenance restrictions.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
class MaintenanceProperties {
    Boolean enabled = false
    String message = "System is currently undergoing maintenance."
    LocalDateTime estimatedEndTime
    List<String> excludedUris = []
    List<String> excludedControllers = []
}