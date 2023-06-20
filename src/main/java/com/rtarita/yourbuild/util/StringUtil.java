package com.rtarita.yourbuild.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public final class StringUtil {
    private StringUtil() {
    }

    public static String truncate(String original, int maxlen) {
        return original.substring(0, Math.min(original.length(), maxlen));
    }

    public static String padWith(String original, int count, char padChar) {
        return original + String.join("", Collections.nCopies(count - original.length(), String.valueOf(padChar)));
    }

    public static String getUnique(Map<String, Integer> registry, String candidate) {
        Integer count = registry.get(candidate);
        registry.merge(candidate, 1, (oldVal, newVal) -> oldVal + 1);
        return count == null ? candidate : candidate + " " + count;
    }

    public static String uniqueAndTruncated(Map<String, Integer> registry, String candidate, int maxlen) {
        int internalMaxlen = maxlen - registry.values()
            .stream()
            .max(Comparator.naturalOrder())
            .orElse(-1)
            .toString()
            .length() - 1;
        return getUnique(registry, truncate(candidate, internalMaxlen));
    }
}
