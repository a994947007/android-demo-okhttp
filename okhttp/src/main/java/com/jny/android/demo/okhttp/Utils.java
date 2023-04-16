package com.jny.android.demo.okhttp;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okio.BufferedSource;
import okio.ByteString;

public class Utils {
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    private static final ByteString UTF_8_BOM = ByteString.decodeHex("efbbbf");
    private static final ByteString UTF_16_BE_BOM = ByteString.decodeHex("feff");
    private static final ByteString UTF_16_LE_BOM = ByteString.decodeHex("fffe");
    private static final ByteString UTF_32_BE_BOM = ByteString.decodeHex("0000ffff");
    private static final ByteString UTF_32_LE_BOM = ByteString.decodeHex("ffff0000");

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    private static final Charset UTF_16_BE = Charset.forName("UTF-16BE");
    private static final Charset UTF_16_LE = Charset.forName("UTF-16LE");
    private static final Charset UTF_32_BE = Charset.forName("UTF-32BE");
    private static final Charset UTF_32_LE = Charset.forName("UTF-32LE");

    public static final Comparator<String> NATURAL_ORDER = new Comparator<String>() {
        @Override public int compare(String a, String b) {
            return a.compareTo(b);
        }
    };

    public static boolean nonEmptyIntersection(
            Comparator<String> comparator, String[] first, String[] second) {
        if (first == null || second == null || first.length == 0 || second.length == 0) {
            return false;
        }
        for (String a : first) {
            for (String b : second) {
                if (comparator.compare(a, b) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns an array containing only elements found in {@code first} and also in {@code
     * second}. The returned elements are in the same order as in {@code first}.
     */
    @SuppressWarnings("unchecked")
    public static String[] intersect(
            Comparator<? super String> comparator, String[] first, String[] second) {
        List<String> result = new ArrayList<>();
        for (String a : first) {
            for (String b : second) {
                if (comparator.compare(a, b) == 0) {
                    result.add(a);
                    break;
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public static int indexOf(Comparator<String> comparator, String[] array, String value) {
        for (int i = 0, size = array.length; i < size; i++) {
            if (comparator.compare(array[i], value) == 0) return i;
        }
        return -1;
    }

    public static String[] concat(String[] array, String value) {
        String[] result = new String[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[result.length - 1] = value;
        return result;
    }

    /** Returns true if two possibly-null objects are equal. */
    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static void checkOffsetAndCount(long arrayLength, long offset, long count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    public static Charset bomAwareCharset(BufferedSource source, Charset charset) throws IOException {
        if (source.rangeEquals(0, UTF_8_BOM)) {
            source.skip(UTF_8_BOM.size());
            return UTF_8;
        }
        if (source.rangeEquals(0, UTF_16_BE_BOM)) {
            source.skip(UTF_16_BE_BOM.size());
            return UTF_16_BE;
        }
        if (source.rangeEquals(0, UTF_16_LE_BOM)) {
            source.skip(UTF_16_LE_BOM.size());
            return UTF_16_LE;
        }
        if (source.rangeEquals(0, UTF_32_BE_BOM)) {
            source.skip(UTF_32_BE_BOM.size());
            return UTF_32_BE;
        }
        if (source.rangeEquals(0, UTF_32_LE_BOM)) {
            source.skip(UTF_32_LE_BOM.size());
            return UTF_32_LE;
        }
        return charset;
    }
}
