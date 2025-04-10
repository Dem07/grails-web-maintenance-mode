package grails.web.maintenance.mode

import groovy.util.logging.Slf4j
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Default implementation of {@link MaintenanceAccessHandler} that checks
 * requests against the configured excluded controllers and URIs.
 * <p>
 * This handler allows requests to proceed if they target a controller or URI
 * that has been explicitly excluded from maintenance mode restrictions.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
@Slf4j
class DefaultMaintenanceAccessHandler implements MaintenanceAccessHandler {
    @Autowired
    MaintenancePropertiesHolder maintenancePropertiesHolder

    @Override
    boolean canAccess(GrailsWebRequest webRequest) {
        String controllerName = webRequest.controllerName
        String uri = webRequest.request.requestURI
        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        if (!properties.enabled) {
            return true
        }

        if (properties.excludedControllers.contains(controllerName)) {
            log.debug("Allowing access to excluded controller: ${controllerName}")
            return true
        }

        if (properties.excludedUris.any { pattern -> uri.matches(pattern)}) {
            log.debug("Allowing access to excluded URI: {}", uri)
            return true
        }

        return false
    }
}
