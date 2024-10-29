package aim;

public class CharacterUtil {
    public static String getJapaneseCharacter(Integer number) {
        if (number == null) return "";
        switch (number) {
            case 1: return "ア";
            case 2: return "イ";
            case 3: return "ウ";
            case 4: return "エ";
            default: return number.toString();
        }
    }
}
