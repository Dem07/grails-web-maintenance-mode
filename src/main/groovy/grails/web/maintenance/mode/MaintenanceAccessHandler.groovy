package grails.web.maintenance.mode

import org.grails.web.servlet.mvc.GrailsWebRequest

/**
 * Interface for determining if a request should be allowed during maintenance mode.
 * <p>
 * Implementations of this interface define access rules that determine
 * which requests should be allowed to proceed during maintenance mode
 * and which should be blocked or redirected.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
interface MaintenanceAccessHandler {
    boolean canAccess(GrailsWebRequest webRequest)
}