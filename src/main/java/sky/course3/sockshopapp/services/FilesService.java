package sky.course3.sockshopapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    Path createTempFile(String suffix);

    boolean cleanDataFile();
}
