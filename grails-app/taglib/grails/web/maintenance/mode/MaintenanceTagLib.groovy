package grails.web.maintenance.mode

/**
 * Tag library providing GSP tags for working with maintenance mode.
 * <p>
 * This library provides utility tags for checking maintenance mode status.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
class MaintenanceTagLib {
    static defaultEncodeAs = [taglib:'html']
    static namespace = "maintenance"

    MaintenancePropertiesHolder maintenancePropertiesHolder

    /**
     * Renders content only if maintenance mode is enabled.
     * <p>
     * Usage: <maintenance:ifEnabled>Content to show during maintenance</maintenance:ifEnabled>
     * </p>
     */
    def ifEnabled = { attrs, body ->
        if (maintenancePropertiesHolder.properties.enabled) {
            out << body()
        }
    }

    /**
     * Renders content only if maintenance mode is disabled.
     * <p>
     * Usage: <maintenance:ifDisabled>Content to show when not in maintenance</maintenance:ifDisabled>
     * </p>
     */
    def ifDisabled = { attrs, body ->
        if (!maintenancePropertiesHolder.properties.enabled) {
            out << body()
        }
    }

    /**
     * Outputs the current maintenance message.
     * <p>
     * Usage: <maintenance:message/>
     * <p>
     * Returns: The current maintenance message
     */
    def message = { attrs ->
        out << maintenancePropertiesHolder.properties.message
    }

    /**
     * Outputs the estimated maintenance completion time.
     * <p>
     * Usage: <maintenance:estimatedEndTime/>
     * <p>
     * Returns: estimated maintenance completion time
     */
    def estimatedEndTime = { attrs ->
        out << maintenancePropertiesHolder.properties.estimatedEndTime
    }
}
