package grails.plugins

import static org.junit.Assert.*
import grails.test.mixin.Mock
import org.junit.*

@Mock(DummyDomainClass)
class SlugGeneratorTests {

    def slugGeneratorService
    def grailsApplication

    @Before
    void setUp() {
        new DummyDomainClass(name:"thevalue").save()
        new DummyDomainClass(name:"another-value").save()
        new DummyDomainClass(name:"another-value-1").save()
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void 'not a domain class'() {
        assert null == slugGeneratorService.generateSlug(this.class, "", "")
    }

    @Test
    void 'not a property in domain class'() {
        assert null == slugGeneratorService.generateSlug(grails.plugins.DummyDomainClass.class, "notAProperty", "dummyValue")
    }

    @Test
    void 'the value is not found'() {
        assert "one-value" == slugGeneratorService.generateSlug(grails.plugins.DummyDomainClass.class, "name", "One Value")
    }

    @Test
    void 'Generate one slug'() {
        assert "thevalue-1" == slugGeneratorService.generateSlug(grails.plugins.DummyDomainClass.class, "name", "theValue")
    }

    @Test
    void 'Generate another slug'() {
        assert "another-value-2" == slugGeneratorService.generateSlug(grails.plugins.DummyDomainClass.class, "name", "aNoTher.vaLue")
    }

    @Test
    void "Generate slug with strict mode"() {
        assert "foo-kk" == slugGeneratorService.generateSlug(grails.plugins.DummyDomainClass.class, "name", "foo “kk“", true)
    }
}
