package Observer;

public class Main {
    public static void main(String[] args) {
        OwnFileReader ownFileReader = new OwnFileReader();

        NumberOfWordsWriter numberOfWordsWriter = new NumberOfWordsWriter();
        NumberOfVowelsWriter numberOfVowelsWriter = new NumberOfVowelsWriter();
        NumberOfConsonantsWriter numberOfConsonantsWriter = new NumberOfConsonantsWriter();
        OppositeDirectionWriter oppositeDirectionWriter = new OppositeDirectionWriter();

        ownFileReader.fileWriterManager.addObserver(numberOfWordsWriter);
        ownFileReader.fileWriterManager.addObserver(numberOfVowelsWriter);
        ownFileReader.fileWriterManager.addObserver(numberOfConsonantsWriter);
        ownFileReader.fileWriterManager.addObserver(oppositeDirectionWriter);

        ownFileReader.readFile("./src/Observer", "test_file.txt");
    }
}
