class SlugGeneratorGrailsPlugin {
    // the plugin version
    def version = "0.5"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/domain/**",
            "grails-app/controllers/**",
            "grails-app/taglib/**",
            "grails-app/i18n/**",
            "web-app/**"
    ]

    def title = "Slug Generator" // Headline display name of the plugin
    def author = "Iván López"
    def authorEmail = "lopez.ivan@gmail.com"
    def description = '''\
This plugin generates unique slugs for String properties. Its main use case is to generate unique and "nice" URLs for accessing domain objects.

For example, instead of accessing a user profile by its id (http://www.domain.com/user/25) you can generate an unique URL based on the user's name: http://www.domain.com/user/ivan-lopez (in case the user name is Iván López).
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/lmivan/grails-slug-generator/blob/master/README.md"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/lmivan/grails-slug-generator" ]

}
