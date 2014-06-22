grails.project.work.dir = 'target'

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        // Coveralls plugin
        build 'org.apache.httpcomponents:httpcore:4.3.2'
        build 'org.apache.httpcomponents:httpclient:4.3.2'
        build 'org.apache.httpcomponents:httpmime:4.3.3'
    }

    plugins {
        build ":tomcat:$grailsVersion",
              ":release:2.2.1",
              ":rest-client-builder:1.0.3",
              ":coveralls:0.1.3", {
            export = false
        }

        runtime ":hibernate:$grailsVersion", {
            export = false
        }

        test ":spock:0.7",
             ":code-coverage:1.2.7", {
            export = false
        }
    }
}
