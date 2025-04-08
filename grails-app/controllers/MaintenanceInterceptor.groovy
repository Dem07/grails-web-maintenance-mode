

import grails.artefact.Interceptor
import grails.web.maintenance.mode.MaintenanceAccessHandler
import grails.web.maintenance.mode.MaintenanceProperties
import grails.web.maintenance.mode.MaintenancePropertiesHolder
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus

@CompileStatic
class MaintenanceInterceptor implements Interceptor {
    int order = HIGHEST_PRECEDENCE

    MaintenanceAccessHandler maintenanceAccessHandler
    MaintenancePropertiesHolder propertiesHolder

    MaintenanceInterceptor() {
        matchAll()
    }

    boolean before() {

        if (maintenanceAccessHandler.canAccess(webRequest)) {
            return true
        }
        MaintenanceProperties properties = propertiesHolder.getProperties()

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
