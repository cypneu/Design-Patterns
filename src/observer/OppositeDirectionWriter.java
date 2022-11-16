package observer;

import java.io.FileWriter;
import java.io.IOException;

public class OppositeDirectionWriter implements IFileWriter {

    @Override
    public void update(String lineContent) {
        String[] words = lineContent.split("\\s+");

        try {
            FileWriter output = new FileWriter("OppositeDirectionWords.txt", true);

            for (String word: words){
                StringBuilder sb = new StringBuilder(word);
                output.write(sb.reverse() + " ");
            }
            output.write("\n");

            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
