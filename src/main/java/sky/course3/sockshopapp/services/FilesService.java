package sky.course3.sockshopapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json);

    boolean saveToFileIn(String json);

    boolean saveToFileOut(String json);

    String readFromFile();

    String readFromFileIn();

    String readFromFileOut();

    File getDataFile();

    File getDataFileIn();

    Path createTempFile(String suffix);

    boolean cleanDataFile();

    boolean cleanDataFileIn();

    File getDataFileOut();

    boolean cleanDataFileOut();
}
