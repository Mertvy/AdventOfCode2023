package Util;

import java.util.ArrayList;

public class Tools {
    public static void padInput(ArrayList<String> input, char padChar) {
        int height = input.size();
        int width = input.get(0).length();

        String paddingRow = Character.toString(padChar).repeat(width + 2);
        input.add(0, paddingRow);
        input.add(paddingRow);
        for (int i = 1; i < input.size() - 1; i++) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(padChar);
            lineBuilder.append(input.get(i));
            lineBuilder.append(padChar);
            input.set(i, lineBuilder.toString());
        }
    }
}
