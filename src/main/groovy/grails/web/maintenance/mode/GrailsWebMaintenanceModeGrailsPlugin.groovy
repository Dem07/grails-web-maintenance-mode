package grails.web.maintenance.mode

import grails.plugins.*

class GrailsWebMaintenanceModeGrailsPlugin extends Plugin {
    def grailsVersion = "5.2.4 > *"
    def pluginExcludes = [
    ]
    def title = "Grails Web Maintenance Mode"
    def author = "Dariusz Bąkowski"
    def authorEmail = ""
    def description = ""
    def profiles = ['web']
    def documentation = ""

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
