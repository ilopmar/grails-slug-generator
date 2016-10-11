package grails.plugins

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class SlugGeneratorServiceIntegrationSpec extends IntegrationSpec {

    def slugGeneratorService
    def grailsApplication

    def setup() {
        new DummyDomainClass(name:"thevalue").save()
        new DummyDomainClass(name:"another-value").save()
        new DummyDomainClass(name:"another-value-1").save()
    }

    void 'not a domain class'() {
        expect:
        slugGeneratorService.generateSlug(this.class, "", "") == null
    }

    void 'not a property in domain class'() {
        expect:
        slugGeneratorService.generateSlug(DummyDomainClass.class, "notAProperty", "dummyValue") == null
    }

    @Unroll
    void 'generate slug for value: #value'() {
        expect:
        slugGeneratorService.generateSlug(DummyDomainClass.class, "name", value, strict) == slug

        where:
        value            | strict | slug
        "One Value"      |  false | "one-value"
        "theValue"       |  false | "thevalue-1"
        "aNoTher.vaLue"  |  false | "another-value-2"
        "foo “bar“"      |  true  | "foo-bar"
    }
}
