package builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        Path filePath = Path.of("src/builder/testObject.json");
        String json = Files.readString(filePath);

        JSONParser parser = new JSONParser();
        JSONBuilder jsonBuilder = new JSONBuilder();
        parser.parseJSON(jsonBuilder, json);
        Object parsedJson = jsonBuilder.getResult();
        System.out.println(parsedJson);
    }
}
