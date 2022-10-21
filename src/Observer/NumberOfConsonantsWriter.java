package Observer;

import java.io.FileWriter;
import java.io.IOException;

public class NumberOfConsonantsWriter implements IFileWriter{
    private static final String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";

    @Override
    public void update(String lineContent) {
        int consonantsCounter = 0;
        for (int i = 0; i < lineContent.length(); i++){
            char character = lineContent.charAt(i);
            if (consonants.contains(String.valueOf(character)))
                consonantsCounter++;
        }

        try {
            FileWriter output = new FileWriter("NumberOfConsonants.txt", true);
            output.write(consonantsCounter + "\n");
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
