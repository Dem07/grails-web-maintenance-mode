package grails.web.maintenance.mode

import org.grails.web.servlet.mvc.GrailsWebRequest

/**
 * Interface for determining if a request should be allowed during maintenance mode
 */
interface MaintenanceAccessHandler {
    /**
     * Determines if a request should be allowed during maintenance mode
     * @param webRequest The current Grails web request
     * @return true if the request should be allowed, false if it should be blocked
     */
    boolean canAccess(GrailsWebRequest webRequest)
}