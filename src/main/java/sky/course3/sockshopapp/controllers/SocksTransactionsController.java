package sky.course3.sockshopapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.course3.sockshopapp.model.Socks;
import sky.course3.sockshopapp.services.FilesService;
import sky.course3.sockshopapp.services.SocksTransactionsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/socksTransactions")
@Tag(name = "ТРАНЗАКЦИИ", description = "Работа с файлами транзакций носков")
public class SocksTransactionsController {
    private final FilesService filesService;
    private final SocksTransactionsService socksTransactionsService;

    public SocksTransactionsController(@Qualifier("SocksTransactionsFilesService") FilesService filesService,
                                       SocksTransactionsService socksTransactionsService) {
        this.filesService = filesService;
        this.socksTransactionsService = socksTransactionsService;
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление файла с транзакциями носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл с транзакциями носков добавлен", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/exportSocks")
    @Operation(summary = "Получение файла транзакций носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл доступен", content = {
                    @Content(mediaType = "application/json")
            }
            )})
    public ResponseEntity<Object> downloadDataFile() {
        try {
            Path path = socksTransactionsService.getAllFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks Transactions File.json\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
