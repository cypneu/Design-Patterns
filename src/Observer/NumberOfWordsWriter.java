package Observer;

import java.io.FileWriter;
import java.io.IOException;

public class NumberOfWordsWriter implements IFileWriter {
    @Override
    public void update(String lineContent) {
        int wordsInLineCounter;
        if (lineContent == null || lineContent.isEmpty())
            wordsInLineCounter = 0;
        else
            wordsInLineCounter = lineContent.split("\\s+").length;

        try {
            FileWriter output = new FileWriter("NumberOfWords.txt", true);
            output.write(wordsInLineCounter + "\n");
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
