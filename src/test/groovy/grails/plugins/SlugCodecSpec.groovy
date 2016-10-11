package grails.plugins

import grails.plugin.spock.*
import spock.lang.Issue
import spock.lang.Specification

class SlugCodecSpec extends Specification {

    void "slugify an empty string"() {
        expect:
            SlugCodec.encode("") == ""
    }

    void "slugify an null string"() {
        expect:
            SlugCodec.encode(null) == ""
    }

    void "slugify string with spaces"() {
        expect:
            SlugCodec.encode("One Value") == "one-value"
            SlugCodec.encode("-") == ""
    }

    void "slugify string with dots"() {
        expect:
            SlugCodec.encode("aNoTher.vaLue") == "another-value"
    }

    void "slugify string with diacritic marks"() {
        expect:
            SlugCodec.encode("áéíóú.äëïöü!ñÑ%çÇ") == "aeiou-aeiou-nn-cc"
    }

    void "slugify string with non ascii chars"() {
        expect:
            SlugCodec.encode("a\$%/&(b)?*!c") == "a-b-c"
            SlugCodec.encode("æÆ") == "aeae"
            SlugCodec.encode("øØ") == "oo"
            SlugCodec.encode("e²") == "e"
            SlugCodec.encode("~") == ""
    }

    void "slugify cyrillic string"() {
        expect:
            SlugCodec.encode("иван") == "иван"
    }

    void "slugify right-to-left string"() {
        expect:
            SlugCodec.encode("ﻡﺮﺤﺑﺍ ﻕﺬﻴﻓﺓ") == "ﻡﺮﺤﺑﺍ-ﻕﺬﻴﻓﺓ"
            SlugCodec.encode("例 え") == "例-え"
    }

    void "slugify in asciiOnly mode"() {
        expect:
            SlugCodec.encode("ﻡﺮﺤﺑﺍ ﻕﺬﻴﻓﺓ", true) == ""
            SlugCodec.encode("<>", true) == ""
            SlugCodec.encode("иван", true) == ""
            SlugCodec.encode("«»", true) == ""
    }

    @Issue("https://github.com/lmivan/grails-slug-generator/issues/2")
    void "remove punctiation marks, exclamation marks and question marks"() {
        expect:
            SlugCodec.encode("1\\!|\"@·#()='?¡¿") == "1"
    }

    @Issue("https://github.com/lmivan/grails-slug-generator/issues/4")
    void "different results using ascii mode and unicode mode"() {
        expect:
            SlugCodec.encode("æ³o & h³æ") == "ae-o-h-ae"
            SlugCodec.encode("æ³o & h³æ", true) == "aeo-hae"
    }

    @Issue("https://github.com/lmivan/grails-slug-generator/issues/10")
    void "slugify some polish characters"() {
        expect:
            SlugCodec.encode("Pchnąć w tę łódź jeża lub ośm skrzyń fig") == "pchnac-w-te-lodz-jeza-lub-osm-skrzyn-fig"
    }
}