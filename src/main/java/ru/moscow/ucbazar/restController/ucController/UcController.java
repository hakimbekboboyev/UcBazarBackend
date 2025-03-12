package ru.moscow.ucbazar.restController.ucController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moscow.ucbazar.dto.ItemsDto;
import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.ResponseItems;
import ru.moscow.ucbazar.model.UcModel;
import ru.moscow.ucbazar.responses.objectResponse.ResponseResult;
import ru.moscow.ucbazar.service.UcService;

@RestController
@RequestMapping("/api/v1")
public class UcController {

    @Autowired
    UcService ucService;

    @Operation(summary = "addUc", description = "add Uc")
    @PostMapping(value = "/addUc/{id}")
    public ResponseEntity<ResponseAll<ResponseResult<String>>> addUc(@RequestBody UcModel ucModel,
                                        @PathVariable int id) {
        ResponseAll<ResponseResult<String>> responseAll = ucService.addUc(ucModel, id);
        return ResponseEntity.status(responseAll.getStatus()).body(responseAll);
    }

    @Operation(summary = "get Item data", description = "Item")
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseAll<ResponseResult<ResponseItems>>> getUcList(HttpServletRequest request,
                                                                                @PathVariable int id){

        ResponseAll<ResponseResult<ResponseItems>> result = ucService.getAll(request.getHeader("Accept-Language"), id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @Operation(summary = "post Item", description = "Items field")
    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody ItemsDto itemsDto,
                                          HttpServletRequest request){
        ucService.addAppBanner(itemsDto, request);
        return ResponseEntity.ok("Successfully added item");
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id){
        String response = ucService.deleteItems(id);
        if (response.equals("Item deleted"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

}
