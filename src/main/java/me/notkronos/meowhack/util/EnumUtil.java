package me.notkronos.meowhack.util;

public class EnumUtil {

    public static Enum<?> next(Enum<?> entry) {
        Enum<?>[] array = entry.getDeclaringClass().getEnumConstants();
        return array.length - 1 == entry.ordinal()
                ? array[0]
                : array[entry.ordinal() + 1];
    }

    public static Enum<?> previous(Enum<?> entry) {
        Enum<?>[] array = entry.getDeclaringClass().getEnumConstants();
        return entry.ordinal() - 1 < 0 ? array[array.length - 1] : array[entry.ordinal() - 1];
    }

    public static Enum<?> fromString(Enum<?> initial, String name)
    {
        Enum<?> e = fromString(initial.getDeclaringClass(), name);
        if (e != null)
        {
            return e;
        }

        return initial;
    }

    public static <T extends Enum<?>> T fromString(Class<T> type, String name)
    {
        for (T constant : type.getEnumConstants())
        {
            if (constant.name().equalsIgnoreCase(name))
            {
                return constant;
            }
        }

        return null;
    }

    public static Enum<?> getEnumStartingWith(String prefix,
                                              Class<? extends Enum<?>> type)
    {
        if (prefix == null)
        {
            return null;
        }

        prefix = prefix.toLowerCase();
        Enum<?>[] array = type.getEnumConstants();
        for (Enum<?> entry : array)
        {
            if (entry.name().toLowerCase().startsWith(prefix))
            {
                return entry;
            }
        }

        return null;
    }

}