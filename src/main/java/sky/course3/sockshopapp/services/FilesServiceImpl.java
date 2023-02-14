package sky.course3.sockshopapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sky.course3.sockshopapp.exceptions.FileProcessingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.socks.data.file}")
    private String dataFileName;
    @Value("socksIn.json")
    private String dataFileIn;
    @Value("socksOut.json")
    private String dataFileOut;

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException(e);
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveToFileIn(String json) {
        try {
            cleanDataFileIn();
            Files.writeString(Path.of(dataFilePath, dataFileIn), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFileIn() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileIn));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException(e);
        }
    }

    @Override
    public File getDataFileIn() {
        return new File(dataFilePath + "/" + dataFileIn);
    }

    @Override
    public boolean cleanDataFileIn() {
        try {
            Path path = Path.of(dataFilePath, dataFileIn);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveToFileOut(String json) {
        try {
            cleanDataFileOut();
            Files.writeString(Path.of(dataFilePath, dataFileOut), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFileOut() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileOut));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException(e);
        }
    }

    @Override
    public File getDataFileOut() {
        return new File(dataFilePath + "/" + dataFileOut);
    }

    @Override
    public boolean cleanDataFileOut() {
        try {
            Path path = Path.of(dataFilePath, dataFileOut);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new FileProcessingException(e);
        }
    }
}
