package ru.moscow.ucbazar.restController.ucController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moscow.ucbazar.dto.ItemsDto;
import ru.moscow.ucbazar.responses.ResponseItems;
import ru.moscow.ucbazar.model.UcModel;
import ru.moscow.ucbazar.service.UcService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UcController {

    @Autowired
    UcService ucService;

    @Operation(summary = "addUc", description = "add Uc")
    @PostMapping(value = "/addUc")
    public ResponseEntity<String> addUc(@RequestBody UcModel ucModel){
        ucService.addUc(ucModel);
        return ResponseEntity.ok("Successfully added uc");
    }

    @Operation(summary = "get Item data", description = "Item")
    @GetMapping("/getAll")
    public ResponseEntity<ResponseItems> getUcList(HttpServletRequest request){

        return ResponseEntity.ok(ucService.getAll(request.getHeader("Accept-Language")));
    }

    @Operation(summary = "post Item", description = "Items field")
    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody ItemsDto itemsDto,
                                          HttpServletRequest request){
        ucService.addAppBanner(itemsDto, request);
        return ResponseEntity.ok("Successfully added item");
    }

}
