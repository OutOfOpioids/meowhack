package me.notkronos.meowhack.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author linustouchtips
 * @since 05/06/2021
 */
public class MathUtil {

    /**
     * Rounds a double to the nearest decimal scale
     * @param number The double to round
     * @param scale The decimal points
     * @return The rounded double
     */
    public static double roundDouble(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);

        // round
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * Rounds a float to the nearest decimal scale
     * @param number The float to round
     * @param scale The decimal points
     * @return The rounded float
     */
    public static float roundFloat(double number, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);

        // round
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.FLOOR);
        return bigDecimal.floatValue();
    }

    /**
     * Takes a number to an exponent (around 6.96x faster than {@link Math} Math.pow())
     * @param num The number to take to an exponent
     * @param exponent The exponent
     * @return The number to an exponent
     */
    public static double toExponent(double num, int exponent) {
        double result = 1;

        // abs, inverse
        if (exponent < 0) {
            int exponentAbs = Math.abs(exponent);

            while (exponentAbs > 0) {
                if ((exponentAbs & 1) != 0) {
                    result *= num;
                }

                exponentAbs >>= 1;
                num *= num;
            }

            return 1 / result;
        }

        else {
            while (exponent > 0) {
                if ((exponent & 1) != 0) { // 1.5% faster
                    result *= num;
                }

                exponent >>= 1; // bitshift fuckery
                num *= num;
            }

            return result;
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean descending) {
        LinkedList<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        if (descending) {
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        } else {
            list.sort(Map.Entry.comparingByValue());
        }
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}