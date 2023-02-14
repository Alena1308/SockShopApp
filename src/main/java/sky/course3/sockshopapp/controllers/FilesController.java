package sky.course3.sockshopapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.course3.sockshopapp.model.Socks;
import sky.course3.sockshopapp.services.FilesService;
import sky.course3.sockshopapp.services.SocksService;
import sky.course3.sockshopapp.services.SocksServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@RestController
@RequestMapping("/files")
@Tag(name = "ФАЙЛЫ", description = "CRUD-опереации и другие эндпоинты для работы с файлами")
public class FilesController {
    private final FilesService filesService;
    private final SocksService socksService;

    public FilesController(FilesService filesService, SocksService socksService){
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
    public ResponseEntity<Object> downloadDataFile() {
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
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление файла с носками")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл с носками добавлен", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file){
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();
        try(FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity. ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(value = "/import/in", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление файла с поступившими носками")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл с носками добавлен", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Void> uploadDataFileIn(@RequestParam MultipartFile file){
        filesService.cleanDataFileIn();
        File dataFile = filesService.getDataFileIn();
        try(FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity. ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(value = "/import/out", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление файла с выданными носками")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл с носками добавлен", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Void> uploadDataFileOut(@RequestParam MultipartFile file){
        filesService.cleanDataFileOut();
        File dataFile = filesService.getDataFileOut();
        try(FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity. ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/exportSocks/In")
    @Operation(summary = "Получение файла всех поступивших носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл доступен", content = {
                    @Content(mediaType = "application/json")
            }
            )})
    public ResponseEntity<Object> downloadDataFileIn() {
        try {
            Path path = socksService.getAllFileIn();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks File IN.json\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
    @GetMapping("/exportSocks/Out")
    @Operation(summary = "Получение файла всех выданных носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Файл доступен", content = {
                    @Content(mediaType = "application/json")
            }
            )})
    public ResponseEntity<Object> downloadDataFileOut() {
        try {
            Path path = socksService.getAllFileOut();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Socks File Out.json\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
