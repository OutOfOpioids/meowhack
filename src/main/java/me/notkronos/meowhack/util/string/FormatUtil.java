package me.notkronos.meowhack.util.string;

public class FormatUtil {

    public String capitalise(String string) {
        if(string.length() != 0) {
            return Character.toTitleCase(string.charAt(0)) + string.substring(1);
        }
        return "";
    }

    public static String formatEnum(Enum<?> in) {
        String enumName = in.name();

        if (!enumName.contains("_")) {
            char firstChar = enumName.charAt(0);
            String suffixChars = enumName.split(String.valueOf(firstChar), 2)[1];
            return String.valueOf(firstChar).toUpperCase() + suffixChars.toLowerCase();
        }

        String[] names = enumName.split("_");
        StringBuilder nameToReturn = new StringBuilder();

        for (String name : names) {
            char firstChar = name.charAt(0);
            String suffixChars = name.split(String.valueOf(firstChar), 2)[1];
            nameToReturn.append(String.valueOf(firstChar).toUpperCase()).append(suffixChars.toLowerCase());
        }

        return nameToReturn.toString();
    }
}
