package grails.plugins

import java.text.Normalizer
import java.util.regex.Pattern

// Generate an slug for the passed string

class SlugCodec {
    static Pattern diactiticPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+", Pattern.UNICODE_CASE)
    static Pattern punctuationPattern1 = Pattern.compile("\\p{Punct}+", Pattern.UNICODE_CASE)
    static Pattern punctuationPattern2 = Pattern.compile("[¡¿·¹²³⁴⁵⁶⁷⁸⁹⁰]+", Pattern.UNICODE_CASE)
    static Pattern whitespacesPattern = Pattern.compile("\\s+", Pattern.UNICODE_CASE)
    static Pattern asciiPattern = Pattern.compile("[^\\p{ASCII}a-z0-9]",  Pattern.UNICODE_CASE)

    static def charMap = ["æ":"ae", "ß":"ss", "ø":"o", "ĸ":"k"];

    static String encode(String str, Boolean asciiOnly = false) {
        if (str.isEmpty()) {
            return "";
        }

        str = translate(str.toLowerCase())
        if (asciiOnly) {
            str = castToAscii(str)
        }

        return removeWhitespaces(normalize(str))
    }

    private static String castToAscii(String str) {
        return str.replaceAll(asciiPattern, "");
    }

    private static String removeWhitespaces(String str) {
        return str.replaceAll(whitespacesPattern, "-")
    }

    private static String normalize(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                         .replaceAll(diactiticPattern, "")
                         .replaceAll(punctuationPattern1, " ")
                         .replaceAll(punctuationPattern2, " ").trim()
    }

    private static String translate(String str) {
        charMap.each { k, v ->
            str = str.replaceAll(k, v);
        }
        return str;
    }
}
