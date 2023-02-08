package sky.course3.sockshopapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import sky.course3.sockshopapp.exceptions.FileProcessingException;
import sky.course3.sockshopapp.exceptions.InvalidValueException;
import sky.course3.sockshopapp.exceptions.NotEnoughSocksException;
import sky.course3.sockshopapp.model.Socks;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {
    private static long id = 1;
    private static long idOfDefectiveSocks = 1;
    private static Map<Long, Socks> socksMap = new HashMap<>();
    private static Map<Long, Socks> defectiveMap = new HashMap<>();
    final private FilesService filesService;

    public SocksServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public long postNewSocks(Socks socks) {
        if(socksMap.isEmpty() || !socksMap.containsValue(socks)) {
            socksMap.put(id, socks);
            saveToFile();
            return id++;
        } else {
            for (Socks oldSocks : socksMap.values()) {
                if (oldSocks.equals(socks)) {
                    oldSocks.setQuantity(oldSocks.getQuantity() + socks.getQuantity());
                    return findKeyFromMap(socks);
                }
            }
        }
        throw new InvalidValueException("Параметры введены некорректно");
    }

    @Override
    public boolean removeSocks(Socks neededSocks) {
        for (Socks socks : socksMap.values()) {
            if (socks.equals(neededSocks) && socks.getQuantity() >= neededSocks.getQuantity()) {
                socks.setQuantity(socks.getQuantity() - neededSocks.getQuantity());
                saveToFile();
                return true;
            } else if (socks.equals(neededSocks) && socks.getQuantity() < neededSocks.getQuantity()) {
                throw new NotEnoughSocksException("Имеется недостаточное количество носков");
            } else if (!socks.equals(neededSocks)){
                throw new NotFoundException("Носки с заданными параметрами н найдены");
            }
        }
        return false;
    }

    @Override
    public long getAllContainsSocksMinMax(String color, Double size, Integer cottonMin, Integer cottonMax) {
        long allContainsSocks = 0;
        for (Socks socks : socksMap.values()) {
            if (socks.getColor().getNamesColor().equals(color)
                    && socks.getSize().getRussianSize()[0] <= size
                    && socks.getSize().getRussianSize()[socks.getSize().getRussianSize().length - 1] >= size
                    && cottonMin <= socks.getCottonPart()
                    && socks.getCottonPart() <= cottonMax) {
                allContainsSocks++;
            }
        }
        return allContainsSocks;
    }

    @Override
    public long getAllContainsSocksMin(String color, Double size, Integer cottonMin) {
        long allContainsSocks = 0;
        for (Socks socks : socksMap.values()) {
            if (socks.getColor().getNamesColor().equals(color)
                    && socks.getSize().getRussianSize()[0] <= size
                    && socks.getSize().getRussianSize()[socks.getSize().getRussianSize().length - 1] >= size
                    && cottonMin <= socks.getCottonPart()) {
                allContainsSocks++;
            }
        }
        return allContainsSocks;
    }

    @Override
    public long getAllContainsSocksMax(String color, Double size, Integer cottonMax) {
        long allContainsSocks = 0;
        for (Socks socks : socksMap.values()) {
            if (socks.getColor().getNamesColor().equals(color)
                    && socks.getSize().getRussianSize()[0] <= size
                    && socks.getSize().getRussianSize()[socks.getSize().getRussianSize().length - 1] >= size
                    && socks.getCottonPart() <= cottonMax) {
                allContainsSocks++;
            }
        }
        return allContainsSocks;
    }

    @Override
    public boolean deleteDefectiveSocks(Socks defectiveSocks) {
        for (Socks socks : socksMap.values()) {
            if (socks.equals(defectiveSocks) && socks.getQuantity() >= defectiveSocks.getQuantity()) {
                socks.setQuantity(socks.getQuantity() - defectiveSocks.getQuantity());
                defectiveMap.put(idOfDefectiveSocks, defectiveSocks);
                idOfDefectiveSocks++;
                saveToFile();
                return true;
            }
        }
        return false;
    }
    @Override
    public Path getAllFile() throws IOException {
        Path path = filesService.createTempFile("allSocks");
        try(Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            for (Socks socks : socksMap.values()) {
                writer.append("Цвет носков: " + socks.getColor().getNamesColor()+"\n"
                        +"Размер: " + Arrays.toString(socks.getSize().getRussianSize()) +"\n"
                        +"Содержание хлопка: "+ socks.getCottonPart() + " %" + "\n"
                        +"Количество на складе: " + socks.getQuantity() + " пар");
                writer.append("\n\n");
            }
        }
        return path;
    }

    private long findKeyFromMap(Socks socks) {
        for (Map.Entry<Long, Socks> pair : socksMap.entrySet()) {
            if (socks.equals(pair.getValue())) {
                return pair.getKey();
            }
        }
        throw new RuntimeException("Нет ключа по данному значению");
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            socksMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Socks>>() {
            });
        } catch (IOException e) {
            throw new FileProcessingException("не удалось прочитать файл");
        }
    }
}
