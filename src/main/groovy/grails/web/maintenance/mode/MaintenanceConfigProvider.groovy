package grails.web.maintenance.mode

/**
 * Interface for loading initial maintenance configuration.
 * It can be used for overriding default {@link MaintenanceProperties} initialization.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
interface MaintenanceConfigProvider {
    /**
     * Initialize maintenance properties on startup
     * @return Initial maintenance properties
     */
    MaintenanceProperties loadInitialProperties()
}