package observer;

import java.io.FileWriter;
import java.io.IOException;

public class NumberOfVowelsWriter implements IFileWriter{
    private static final String vowels = "aeiouyAEIOUY";

    @Override
    public void update(String lineContent) {
        int vowelsCounter = 0;
        for (int i = 0; i < lineContent.length(); i++){
            char character = lineContent.charAt(i);
            if (vowels.contains(String.valueOf(character)))
                vowelsCounter++;
        }

        try {
            FileWriter output = new FileWriter("NumberOfVowels.txt", true);
            output.write(vowelsCounter + "\n");
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
