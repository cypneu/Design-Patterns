package Observer;

import java.util.ArrayList;
import java.util.List;

public class FileWriterManager {
    private final List<IFileWriter> observers = new ArrayList<>();

    public void addObserver(IFileWriter fileWriter){
        this.observers.add(fileWriter);
    }

    public void removeObserver(IFileWriter fileWriter){
       this.observers.remove(fileWriter);
    }

    public void notify(String lineContent){
        for (IFileWriter fileWriter: this.observers)
            fileWriter.update(lineContent);
    }
}
