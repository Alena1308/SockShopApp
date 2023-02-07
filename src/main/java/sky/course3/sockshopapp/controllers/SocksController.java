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
        long id = socksService.postNewSocks(socks);
        return ResponseEntity.ok().body(id);
        // Написать исключение если данные введены не веррно
    }

    @PutMapping("/sale")
    public ResponseEntity<Boolean> removeSocks(@Valid @RequestBody Socks socks) {
        if (socksService.removeSocks(socks)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{color}&{size}&{cottonMin}&{cottonMax}")
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
    public ResponseEntity<Boolean> deleteDefectiveSocks(@Valid @RequestBody Socks socks) {
        if (socksService.deleteDefectiveSocks(socks)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.notFound().build();
    }
}
