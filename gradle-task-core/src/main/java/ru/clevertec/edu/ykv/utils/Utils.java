package ru.clevertec.edu.ykv.utils;

public class Utils {
    public static boolean isAllPositiveNumbers(String... str) {
        if (str == null) {
            return false;
        }

        boolean result = true;

        for (String number : str) {
            boolean isPositive = StringUtils.isPositiveNumber(number);
            System.out.println(number + " - " + isPositive);
            if (!isPositive) {
                result = false;
            }
        }

        return result;
    }

    public static boolean isAllPositiveNumbers2(String... str) {
        if (str == null) {
            return false;
        }
        boolean result = true;

        for (String number : str) {
            boolean isPositive = StringUtils.isPositiveNumber(number);
            System.out.println(number + " - " + isPositive);
            if (!isPositive) {
                return false;
            }
        }

        return result;
    }
}
