package grails.plugins

import static org.junit.Assert.*
import grails.test.mixin.Mock
import org.junit.*

class SlugCodecTests {
    @Test
    void "slugify string with spaces"() {
        assert "one-value" == SlugCodec.encode("One Value")
        assert "" == SlugCodec.encode("-")
    }

    @Test
    void "slugify string with dots"() {
        assert  "another-value" == SlugCodec.encode("aNoTher.vaLue")
    }

    @Test
    void "slugify string with diacritic marks"() {
         assert "aeiou-aeiou-nn-cc" == SlugCodec.encode("áéíóú.äëïöü!ñÑ%çÇ")
    }

    @Test
    void "slugify string with non ascii chars 01"() {
        assert "a-b-c" == SlugCodec.encode("a\$%/&(b)?*!c")
    }

    @Test
    void "slugify string with non ascii chars 02"() {
        assert "aeae" == SlugCodec.encode("æÆ")
    }

    @Test
    void "slugify string with non ascii chars 03"() {
        assert "oo" == SlugCodec.encode("øØ")
    }

    @Test
    void "slugify string with non ascii chars 04"() {
        assert "e" == SlugCodec.encode("e²")
    }

    @Test
    void "slugify string with non ascii chars 05"() {
        assert "" == SlugCodec.encode("~")
    }

    @Test
    void "slugify cyrillic string"() {
        assert "иван" == SlugCodec.encode("иван")
    }

    @Test
    void "slugify right-to-left string 01"() {
        assert "ﻡﺮﺤﺑﺍ-ﻕﺬﻴﻓﺓ" == SlugCodec.encode("ﻡﺮﺤﺑﺍ ﻕﺬﻴﻓﺓ")
    }

    @Test
    void "slugify right-to-left string 02"() {
        assert "例-え"== SlugCodec.encode("例 え")
    }

    @Test
    void "slugify in asciiOnly mode 01"() {
        assert "" == SlugCodec.encode("ﻡﺮﺤﺑﺍ ﻕﺬﻴﻓﺓ", true)
    }

    @Test
    void "slugify in asciiOnly mode 02"() {
        assert "" == SlugCodec.encode("<>", true)
    }

    @Test
    void "slugify in asciiOnly mode 03"() {
        assert "" == SlugCodec.encode("иван", true)
    }

    @Test
    void "slugify in asciiOnly mode 04"() {
        assert "" == SlugCodec.encode("«»", true)
    }

    // https://github.com/lmivan/grails-slug-generator/issues/2
    @Test
    void "regression test #2"() {
        assert "1" == SlugCodec.encode("1\\!|\"@·#()='?¡¿")
    }

    // https://github.com/lmivan/grails-slug-generator/issues/4
    @Test
    void "regressiont test #4 - unicode mode"() {
        assert "ae-o-h-ae" == SlugCodec.encode("æ³o & h³æ")
    }

    // https://github.com/lmivan/grails-slug-generator/issues/4
    @Test
    void "regressiont test #4 - asciiOnly mode"() {
        assert "aeo-hae" == SlugCodec.encode("æ³o & h³æ", true)
    }
}
