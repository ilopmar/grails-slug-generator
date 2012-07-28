Grails-slug-generator
=====================

This plugin generate unique slugs for String properties. Its main use case is to generate unique an "nice" urls for access to domain objects.

For example, instead of access to a user profile by id http://www.domain.com/user/25 you can generate an unique url based in the user name: http://www.domain.com/user/ivan-lopez (in case the user name is Iván López).

Usage
-----

The plugin provides a Grails Service, `slugGeneratorService`, that can be injected in any artefact of your Grails application. The service only includes a method, `generateSlug(Class theClazz, String property, String value)`.
The plugin checks the `property` in the domain class, `theClazz` and generates a slug from the `value`. If the value already exists it will add a counter and try again after a unique value is generated.  

The tipical use case is to inject into a Domain class and use it automatically when inserting or updating a new domain object. Example:

``` groovy
class Dummy {
    
    def slugGeneratorService
    
    String name
    String slug = ""
    
    def beforeInsert() {
        this.slug = slugGeneratorService.generateSlug(this.class, "slug", name)
    }

    def beforeUpdate() {
        if (isDirty('name')) {
            this.slug = slugGeneratorService.generateSlug(this.class, "slug", name)
        }
    }
}
```

After this configuration you can use it:
``` groovy
def dummy = new Dummy(name:'Iván López').save()
assert dummy.slug == 'ivan-lopez'

dummy.name = 'Another name!!'
dummy.save(flush:true)
def dummyUpdated = Dummy.get(1)
assert dummyUpdated.slug == 'another-name'
```

The plugin supports full UTF-8, so you can use, for instance, ciryllic chars or right-to-left writing. Check out the tests.
