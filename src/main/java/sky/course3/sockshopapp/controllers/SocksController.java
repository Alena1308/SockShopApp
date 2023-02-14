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
    @Operation(summary = "Запрос наличия носков по параметрам Max Min")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки найдены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Long> getAllSocksFromMinToMaxCotton(@PathVariable @RequestParam(name = "Color")
                                                              @Parameter(name = "Color",
                                                                      description = "Name of color in Russian",
                                                                      example = "Белый") String color,
                                                              @PathVariable @RequestParam(name = "Size")
                                                              @Parameter(name = "Size",
                                                                      description = "Number of size",
                                                                      example = "35.5") Double size,
                                                              @PathVariable @Valid @RequestParam(name = "Min cotton")
                                                              @Parameter(name = "Min cotton",
                                                                      description = "Number of minimum cotton content, %",
                                                                      example = "0") Integer cottonMin,
                                                              @PathVariable @Valid @RequestParam(name = "Max cotton")
                                                              @Parameter(name = "Max cotton",
                                                                      description = "Number of maximum cotton content, %",
                                                                      example = "100") Integer cottonMax) {
        return ResponseEntity.ok(socksService.getAllContainsSocksMinMax(color, size, cottonMin, cottonMax));
    }
    @GetMapping("/{color}&{size}&{cottonMin}")
    @Operation(summary = "Запрос наличия носков по параметрам Min")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки найдены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Long> getAllSocksFromMinCotton(@PathVariable @RequestParam(name = "Color")
                                                              @Parameter(name = "Color",
                                                                      description = "Name of color in Russian",
                                                                      example = "Белый") String color,
                                                              @PathVariable @RequestParam(name = "Size")
                                                              @Parameter(name = "Size",
                                                                      description = "Number of size",
                                                                      example = "35.5") Double size,
                                                              @PathVariable @Valid @RequestParam(name = "Min cotton")
                                                              @Parameter(name = "Min cotton",
                                                                      description = "Number of minimum cotton content, %",
                                                                      example = "0") Integer cottonMin){
        return ResponseEntity.ok(socksService.getAllContainsSocksMin(color, size, cottonMin));
    }
    @GetMapping("/{color}&{size}&{cottonMax}")
    @Operation(summary = "Запрос наличия носков по параметрам Max")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Носки найдены", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Long> getAllSocksMaxCotton(@PathVariable @RequestParam(name = "Color")
                                                              @Parameter(name = "Color",
                                                                      description = "Name of color in Russian",
                                                                      example = "Белый") String color,
                                                              @PathVariable @RequestParam(name = "Size")
                                                              @Parameter(name = "Size",
                                                                      description = "Number of size",
                                                                      example = "35.5") Double size,
                                                              @PathVariable @Valid @RequestParam(name = "Max cotton")
                                                              @Parameter(name = "Max cotton",
                                                                      description = "Number of maximum cotton content, %",
                                                                      example = "100") Integer cottonMax) {
        return ResponseEntity.ok(socksService.getAllContainsSocksMax(color, size, cottonMax));
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
    @PutMapping("/sort")
    @Operation(summary = "Добавление, отправка, списывание носков")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Изменения учтены на складе", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Socks.class)))
            }
            )})
    public ResponseEntity<Object> sortSocksByType(@Valid @RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.sortSocks(socks));
    }
}
