package sky.course3.sockshopapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.course3.sockshopapp.services.FilesService;
import sky.course3.sockshopapp.services.SocksService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@RestController
@RequestMapping("/files")
@Tag(name = "ФАЙЛЫ", description = "CRUD-опереации и другие эндпоинты для работы с файлами")
public class FilesController {
    private final FilesService filesService;
    private final SocksService socksService;

    public FilesController(FilesService filesService, SocksService socksService) {
        this.filesService = filesService;
        this.socksService = socksService;
    }
    @GetMapping("/exportSocks")
    @Operation(summary = "Получение файла наличия носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл доступен", content = {
                    @Content(mediaType = "application/json")
            }
            )})
    public ResponseEntity<Object> getAllFile() {
        try {
            Path path = socksService.getAllFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks File.json\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
