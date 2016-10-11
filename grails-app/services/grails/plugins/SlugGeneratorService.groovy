package grails.plugins

import org.grails.core.DefaultGrailsDomainClass


class SlugGeneratorService {
    static transactional = false

    def grailsApplication

    /**
     * Generate a unique slug for the property in the domain class based on the value. All the diacritical
     * marks are removed from the value before generate the slug and then, if the slug exists in the database
     * a counter is added.
     *
     * Note: this service uses SlugCodec for create a slug for a string and SlugCodec, by
     * default is slighty permisive with unicode characters. If you really need a slug with only
     * ascii characters, put a strict parameter to true.
     *
     * @param theClazz The domain class.
     * @param property The property of the domain class.
     * @param value The value from which generate the slug.
     * @param strict Activates the strict mode (default false).
     * @return The unique slug generated or null if there is an error.
     */
    public String generateSlug(Class theClazz, String property, String value, Boolean strict = false) {
        // Check if the class if a DomainClass
        if (!grailsApplication.isArtefactOfType("Domain", theClazz)) {
            println "not a domain"
            return null
        }

        // Get the GrailsDomainClass related to the class
        def grailsClass = new DefaultGrailsDomainClass(theClazz)

        // Check if the class has the property
        def persistentProperty = grailsClass.getPersistentProperty(property)
        if (!persistentProperty) {
            println "not persistence"
            return null
        }

        // Generate the initial slug and look for it
        def initialSlug = SlugCodec.encode(value, strict)
        def instance = theClazz."findBy${property.capitalize()}"(initialSlug)
        if (!instance) {
            return initialSlug
        }

        int c = 1
        def slug = ""

        while (true) {
            slug = "${initialSlug}-${c}"
            instance = theClazz."findBy${property.capitalize()}"(slug)

            if (!instance) {
                return slug
            } else {
                c++
            }
        }
    }
}
