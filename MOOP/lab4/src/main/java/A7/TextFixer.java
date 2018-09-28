package A7;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFixer {

    public static String fix(String text) {
        Pattern pattern = Pattern.compile("[Pp][Aa]");
        Matcher matcher = pattern.matcher(text);
        ArrayList<Integer> positions = new ArrayList<>();
        while(matcher.find()) {
            positions.add(matcher.start());
        }
        StringBuilder fixedText = new StringBuilder();
        for (int i = 0; i < positions.size(); i++) {
            fixedText.append(text, i==0?0:positions.get(i-1)+2, positions.get(i));
            String lex = text.substring(positions.get(i), positions.get(i)+2);
            switch(lex) {
                case "PA":
                    fixedText.append("PO");
                    break;
                case "Pa":
                    fixedText.append("Po");
                    break;
                case "pA":
                    fixedText.append("pO");
                    break;
                case "pa":
                    fixedText.append("po");
                    break;
                default:
                    throw new RuntimeException("Unknown value was matched");
            }
        }
        if (!positions.isEmpty()) {
            fixedText.append(text, positions.get(positions.size() - 1)+2, text.length());
        }
        return fixedText.toString();

    }
}
