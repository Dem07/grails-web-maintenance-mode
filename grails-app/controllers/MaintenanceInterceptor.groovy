

import grails.artefact.Interceptor
import grails.web.maintenance.mode.MaintenanceAccessHandler
import grails.web.maintenance.mode.MaintenanceProperties
import grails.web.maintenance.mode.MaintenancePropertiesHolder
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus

/**
 * Grails interceptor that manages access to the application during maintenance mode.
 * <p>
 * This interceptor examines each incoming request and, if maintenance mode is active,
 * determines whether the request should be allowed to proceed or disallowed.
 *
 * @author Dariusz Bąkowski
 * @since 0.1
 */
@CompileStatic
class MaintenanceInterceptor implements Interceptor {
    int order = HIGHEST_PRECEDENCE

    MaintenanceAccessHandler maintenanceAccessHandler
    MaintenancePropertiesHolder maintenancePropertiesHolder

    MaintenanceInterceptor() {
        matchAll()
    }

    boolean before() {
        if (maintenanceAccessHandler.canAccess(webRequest)) {
            return true
        }

        MaintenanceProperties properties = maintenancePropertiesHolder.getProperties()
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
        response.setContentType('application/json')
        response.writer.write(JsonOutput.toJson([
            status : 'maintenance',
            message : properties.message,
            estimatedEndTime : properties.estimatedEndTime
        ]))
        return false
    }
}
