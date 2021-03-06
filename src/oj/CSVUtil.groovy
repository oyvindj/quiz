package oj

import java.io.IOException
import java.io.Writer
import java.util.List

public class CSVUtils {

    static final char DEFAULT_SEPARATOR = ','

    static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ')
    }

    static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ')
    }

    private static String followCVSformat(String value) {
        def result = value
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"")
        }
        return result
    }

    static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
        def first = true
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR
        }
        def sb = new StringBuilder()
        for (String value : values) {
            if (!first) {
                sb.append(separators)
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value))
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote)
            }
            first = false
        }
        sb.append("\n")
        w.append(sb.toString())
    }
}