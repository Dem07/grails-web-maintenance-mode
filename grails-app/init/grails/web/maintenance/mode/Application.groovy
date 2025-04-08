package grails.web.maintenance.mode

import grails.boot.*
import grails.boot.config.GrailsAutoConfiguration
import grails.plugins.metadata.*
import org.springframework.boot.context.properties.EnableConfigurationProperties

@PluginSource
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}