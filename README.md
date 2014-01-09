Grails-slug-generator
=====================

[![Build Status](https://drone.io/github.com/lmivan/grails-slug-generator/status.png)](https://drone.io/github.com/lmivan/grails-slug-generator/latest)
![Still maintained](http://stillmaintained.com/lmivan/grails-slug-generator.png)

This plugin generates unique slugs for String properties. Its main use case is to generate unique and nice names for domain instances that can be used in URLs, such as `/user/show/<slug>`.

For example, instead of having a URL like http://www.domain.com/user/25 with a number identifying the user, you can generate a unique URL based on the user's name: http://www.domain.com/user/ivan-lopez (from name Iván López).

Usage
-----

The plugin provides a Grails Service, `slugGeneratorService`, that can be injected into any artefact of your Grails application. The service has only one method, `generateSlug(Class domainClass, String property, String value)`, which is used to generate the unique slugs.

How does it work? The method first generates a slug from the given value and then checks whether that slug is already used by another domain instance of the given type. If the slug is unique, then the method returns that value. Otherwise, it appends a number and tries again. This is repeated until a unique slug is found and returned.

The typical use case is to automatically set the slug when inserting a new domain object or when updating an existing one whose source property (for example 'name') has changed. Here's a concrete example:

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

With the above code, you get the following behavior when using the `Dummy` domain class:
``` groovy
def dummy = new Dummy(name:"Iván López").save()
assert dummy.slug == "ivan-lopez"

dummy.name = "Another name!!"
dummy.save(flush:true)
def dummyUpdated = Dummy.get(1)
assert dummyUpdated.slug == "another-name"

def dummy2 = new Dummy(name:"Iván López").save()
assert dummy2.slug == "ivan-lopez-1"
```

The plugin supports full UTF-8, so you can use, for instance, ciryllic chars or right-to-left writing. Check out [the tests](https://github.com/lmivan/grails-slug-generator/blob/master/test/integration/grails/plugins/SlugGeneratorTests.groovy) for more examples.

But, if you need only ascii slugs, you can activate a strict mode that filters all non ascii characters.

``` groovy
class Dummy {
    // [...]
    def beforeInsert() {
        this.slug = slugGeneratorService.generateSlug(this.class, "slug", name, true)
    }

    def beforeUpdate() {
        if (isDirty('name')) {
            this.slug = slugGeneratorService.generateSlug(this.class, "slug", name, true)
        }
    }
}
```

Additional codec
----------------

The plugin also includes a SlugCodec that you can call like the other Grails builtin codecs:

``` groovy
assert "ivan-lopez" == "Iván López!!".encodeAsSlug()
```

Version info
------------

* 0.1 - 02/Aug/2012 - Initial version
* 0.2 - 11/Jan/2013 - Fixed issue with some characters like '¿' and '¡'
* 0.3 - 06/Apr/2013 - Fixed issue #4 and added additional param to generate ascii-only slugs. Thanks [@niwibe](https://github.com/niwibe) for the pull-requests
* 0.3.1 - 06/Apr/2013 - Removed unnecessary maven repositories


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/lmivan/grails-slug-generator/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

