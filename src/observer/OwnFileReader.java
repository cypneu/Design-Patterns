package observer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;


public class OwnFileReader {
    protected final FileWriterManager fileWriterManager = new FileWriterManager();

    public void readFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                fileWriterManager.notify(line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
