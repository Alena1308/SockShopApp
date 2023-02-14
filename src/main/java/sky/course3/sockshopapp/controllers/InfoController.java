package sky.course3.sockshopapp.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "ИНФО", description = "Приветствие и информация о приложении")
public class InfoController {

    @GetMapping
    @Operation(summary = "Приветствие")
    String greeting() {
        return "Application is running";
    }

    @GetMapping("/info")
    @Operation(summary = "Информаия о приложении")
    String showInfo() {
        return "Author: Alyona <br> " +
                "Project name: SockShop <br> " +
                "Was created at 08.02.2023 <br>" +
                "This project is dedicated to the accounting of socks";
    }

}
