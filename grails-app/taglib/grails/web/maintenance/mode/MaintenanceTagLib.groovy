package grails.web.maintenance.mode

import org.springframework.beans.factory.annotation.Autowired

class MaintenanceTagLib {
    static defaultEncodeAs = [taglib:'html']
    static namespace = "maintenance"

    MaintenancePropertiesHolder propertiesHolder

    /**
     * Outputs content only if system is in maintenance mode
     */
    def ifEnabled = { attrs, body ->
        if (propertiesHolder.properties.enabled) {
            out << body()
        }
    }

    /**
     * Outputs content only if system is not in maintenance mode
     */
    def ifDisabled = { attrs, body ->
        if (!propertiesHolder.properties.enabled) {
            out << body()
        }
    }

    /**
     * Outputs the current maintenance status as a string
     */
    def status = { attrs ->
        out << (propertiesHolder.properties.enabled ? 'maintenance' : 'operational')
    }

    /**
     * Outputs the current maintenance message
     */
    def message = { attrs ->
        if (propertiesHolder.properties.enabled) {
            out << propertiesHolder.properties.message
        }
    }
}
