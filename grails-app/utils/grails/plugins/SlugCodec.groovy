package grails.plugins

import java.text.Normalizer
import java.util.regex.Pattern

/**
 * Generate an slug for the passed string
 */
class SlugCodec {

    static encode = { str ->
        
        Pattern p = Pattern.compile("\\p{InCombiningDiacriticalMarks}+", Pattern.UNICODE_CASE);
        Pattern p2 = Pattern.compile("\\p{Punct}+", Pattern.UNICODE_CASE);
        Pattern p3 = Pattern.compile("\\s+", Pattern.UNICODE_CASE);

        // Decompose any funny characters.
        def link = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll(p, '') // remove all the diacritic marks
                .replaceAll('¿', '')
                .replaceAll(p2, ' ').trim() // transform the punctuation into spaces first, so that we can trim some ending or beginning punctuation
                .replaceAll(p3, '-') // and replace all the whitespace with a dash.
                .toLowerCase()

        return link
    }
}