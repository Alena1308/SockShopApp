package sky.course3.sockshopapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sky.course3.sockshopapp.exceptions.FileProcessingException;
import sky.course3.sockshopapp.model.SocksTransaction;
import sky.course3.sockshopapp.services.FilesService;
import sky.course3.sockshopapp.services.SocksTransactionsService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksTransactionsServiceImpl implements SocksTransactionsService {
    private static long id = 1;
    private static Map<Long, SocksTransaction> infoSocksTransactionsMap = new HashMap<>();
    private final FilesService filesService;

    public SocksTransactionsServiceImpl(@Qualifier("SocksTransactionsFilesService") FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(infoSocksTransactionsMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            infoSocksTransactionsMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, SocksTransaction>>() {
            });
        } catch (IOException e) {
            throw new FileProcessingException("не удалось прочитать файл");
        }
    }

    @Override
    public void addSocksTransactions(SocksTransaction socksTransaction) {
        infoSocksTransactionsMap.put(id++, socksTransaction);
        saveToFile();
    }

    @Override
    public Path getAllFile() throws IOException {
        Path path = filesService.createTempFile("allSocksIn");
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (SocksTransaction socks : infoSocksTransactionsMap.values()) {
                writer.append("Тип операции: " + socks.getOperationType().getOperation() + "\n"
                        + "Дата и время: " + socks.getDate().toString() + "\n"
                        + "Цвет носков: " + socks.getSock().getColor().getNamesColor() + "\n"
                        + "Размер: " + Arrays.toString(socks.getSock().getSize().getRussianSize()) + "\n"
                        + "Содержание хлопка: " + socks.getSock().getCottonPart() + " %" + "\n"
                        + "Количество: " + socks.getSock().getQuantity() + " пар");
                writer.append("\n\n");
            }
        }
        return path;
    }

}
