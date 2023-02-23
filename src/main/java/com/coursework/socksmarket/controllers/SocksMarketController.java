package com.coursework.socksmarket.controllers;

import com.coursework.socksmarket.model.Color;
import com.coursework.socksmarket.model.Size;
import com.coursework.socksmarket.model.Socks;
import com.coursework.socksmarket.services.impl.SocksStockServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/socks")
@Tag(name = "Socks market", description = "CRUD операции для работы со складом носков.")
public class SocksMarketController {

    private final SocksStockServiceImpl socksStockService;

    public SocksMarketController(SocksStockServiceImpl socksStockService) {
        this.socksStockService = socksStockService;
    }

    @GetMapping("/get/{color}&{size}&{cottonMin}&{cottonMax}")
    @Operation(summary = "Вывод количества товара.", description = "Вывод количества носков по заданным параметрам.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Выведено количество товара по запросу."),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Object> getSocks(@PathVariable  Color color,
                                           @PathVariable  Size size,
                                           @PathVariable  int cottonMin,
                                           @PathVariable  int cottonMax){
        int socksCount = socksStockService.getSocks(color, size, cottonMin, cottonMax);
        if(socksCount == 0){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socksCount);
    }


    @PostMapping("/add")
    @Operation(summary = "Приход товара на склад.", description = "Добавление носков на склад по их параметрам.")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200",
            description = "Товар добавлен на склад.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
            }
            ),
            @ApiResponse(responseCode = "400",
            description = "Параметры запроса отсутствуют или имеют некорректный формат."),
            @ApiResponse(responseCode = "500",
            description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Socks> addSocks (@RequestBody Socks socks) {
        socksStockService.addSocks(socks);
        return ResponseEntity.ok().body(socks);
    }

    @PutMapping("/edit")
    @Operation(summary = "Отпуск товара со склада.",
            description = "Получение носков со склада по параметрам, на складе количество уменьшается.")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200",
                    description = "Товар получен со склада.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Socks> editSocks(@RequestBody Socks socks){
        Socks socks1 = socksStockService.editSocksFromStock(socks);
        if(socks1 == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socks1);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Списание товара со склада.",
            description = "Получение носков со склада по параметрам, на складе количество уменьшается.")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200",
                    description = "Товар списан со склада.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат."),
            @ApiResponse(responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны.")
    })
    public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks){
        if(socksStockService.removeSocks(socks)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
