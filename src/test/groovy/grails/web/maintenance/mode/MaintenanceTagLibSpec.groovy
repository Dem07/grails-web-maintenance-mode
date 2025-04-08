package grails.web.maintenance.mode

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class MaintenanceTagLibSpec extends Specification implements TagLibUnitTest<MaintenanceTagLib> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
