package grails.web.maintenance.mode

import grails.plugins.*

class GrailsWebMaintenanceModeGrailsPlugin extends Plugin {
    def grailsVersion = "3.0.0 > *"
    def pluginExcludes = [
    ]
    def title = "Grails Web Maintenance Mode"
    def author = "Dariusz Bąkowski"
    def license = "APACHE"
    def authorEmail = ""
    def description = "A simple, flexible plugin for implementing maintenance mode in Grails applications"
    def profiles = ['web']
    def documentation = "https://github.com/Dem07/grails-web-maintenance-mode"
    def issueManagement = [system: "GitHub", url: "http://github.com/Dem07/grails-maintenance-mode/issues"]
    Closure doWithSpring() { {->
        // Register the config provider
        maintenanceConfigProvider(DefaultMaintenanceConfigProvider)

        // Register the properties holder
        maintenancePropertiesHolder(MaintenancePropertiesHolder)

        // Register the default access handler
        maintenanceAccessHandler(DefaultMaintenanceAccessHandler)

        // Register the main service
        maintenanceService(MaintenanceService)
        }
    }
}
