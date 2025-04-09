package grails.web.maintenance.mode

import groovy.util.logging.Slf4j
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
class DefaultMaintenanceAccessHandler implements MaintenanceAccessHandler {
    @Autowired
    MaintenancePropertiesHolder propertiesHolder

    @Override
    boolean canAccess(GrailsWebRequest webRequest) {
        String controllerName = webRequest.controllerName
        String uri = webRequest.request.requestURI
        MaintenanceProperties properties = propertiesHolder.getProperties()
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
