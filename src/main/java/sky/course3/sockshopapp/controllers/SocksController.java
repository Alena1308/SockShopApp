package sky.course3.sockshopapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.course3.sockshopapp.model.Socks;
import sky.course3.sockshopapp.services.SocksService;

import javax.validation.Valid;

@RestController
@RequestMapping("/socks")
@Tag(name = "НОСКИ", description = "CRUD-опереации и другие эндпоинты для работы с носками")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(summary = "Добавление новых носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки добавлены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Long> postNewSocks(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.postNewSocks(socks));
    }

    @PutMapping("/sale")
    @Operation(summary = "Отправка носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки отправлены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Boolean> removeSocks(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.removeSocks(socks));
    }

    @GetMapping("/{color}&{size}&{cottonMin}&{cottonMax}")
    @Operation(summary = "Запрос наличия носков по параметрам")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки найдены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Long> getAllSocksFromMinToMaxCotton(@PathVariable @RequestParam(name = "Белый")
                                                              @Parameter(description = "Name of color in Russian") String color,
                                                              @PathVariable @RequestParam(name = "35")
                                                              @Parameter(description = "Number of size") Double size,
                                                              @PathVariable @Valid @RequestParam(name = "0")
                                                              @Parameter(description = "Number of minimum cotton content") Integer cottonMin,
                                                              @PathVariable @Valid @RequestParam(name = "100")
                                                              @Parameter(description = "Number of maximum cotton content") Integer cottonMax) {
        return ResponseEntity.ok(socksService.getAllContainsSocksMinMax(color, size, cottonMin, cottonMax));
    }

    @DeleteMapping("/defective")
    @Operation(summary = "Списывание бракованных носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки списаны", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Boolean> deleteDefectiveSocks(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.deleteDefectiveSocks(socks));
    }
}
