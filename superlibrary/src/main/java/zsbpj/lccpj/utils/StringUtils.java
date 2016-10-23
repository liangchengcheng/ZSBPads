package zsbpj.lccpj.utils;

public class StringUtils {

    /**
     * 提取出城市或者县
     */
    public static String extractLocation(final String city, final String district){
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }

    public static String[] splitWithFlag(String data, String ch) {
        String flagString = System.nanoTime() + "";
        data = data.replaceAll(ch, ch + flagString);
        String[] words = data.split(ch);
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll(flagString, ch);
        }
        return words;
    }
}
