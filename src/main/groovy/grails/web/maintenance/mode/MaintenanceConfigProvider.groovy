package grails.web.maintenance.mode

/**
 * Interface for loading initial maintenance configuration
 */
interface MaintenanceConfigProvider {
    /**
     * Initialize maintenance properties on startup
     * @return Initial maintenance properties
     */
    MaintenanceProperties loadInitialProperties()
}