package ru.moscow.ucbazar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moscow.ucbazar.dto.ItemsDto;
import ru.moscow.ucbazar.responses.objectResponse.Error;
import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.ResponseItems;
import ru.moscow.ucbazar.entity.Items;
import ru.moscow.ucbazar.entity.UcEntity;
import ru.moscow.ucbazar.model.UcModel;
import ru.moscow.ucbazar.repository.ItemsRepository;
import ru.moscow.ucbazar.repository.UcRepository;
import ru.moscow.ucbazar.responses.objectResponse.ResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UcService {

    @Autowired
    UcRepository ucRepository;

    @Autowired
    ItemsRepository itemsRepository;

    List<UcEntity> ucEntities = new ArrayList<>();


    public ResponseAll<ResponseResult<String>> addUc(UcModel ucModel, int id) {
        UcEntity ucEntity = UcEntity.builder()
                .uc_name(ucModel.getUc_name())
                .uc_image(ucModel.getUc_image())
                .uc_amount(ucModel.getUc_amount())
                .uc_value(ucModel.getUc_value())
                .bonus(ucModel.getBonus())
                .build();

        Optional<Items> items_opt = itemsRepository.findById(id);
        if (items_opt.isPresent()) {
            Items items = items_opt.get();
            ucEntities = items.getUc();
            ucEntities.add(ucEntity);

            items.setUc(ucEntities);

            ucRepository.save(ucEntity);
            itemsRepository.save(items);

            ResponseResult<String> responseResult = ResponseResult.<String>builder()
                    .result("Successfully added uc")
                    .error(null)
                    .build();
            return ResponseAll.<ResponseResult<String>>builder()
                    .response(responseResult)
                    .status(200)
                    .build();

        }else {
            ResponseResult<String> responseResult = ResponseResult.<String>builder()
                    .result(null)
                    .error(Error.builder()
                            .errorMessage("Not found item")
                            .errorCode(404)
                            .build())
                    .build();
            return ResponseAll.<ResponseResult<String>>builder()
                    .response(responseResult)
                    .status(400)
                    .build();

        }


    }

    public ResponseAll<ResponseResult<ResponseItems>> getAll(String lang, int id) {

        List<UcEntity> allUc = ucRepository.findAllById(id);


        Optional<Items> items_opt = itemsRepository.findById(id);
        if (items_opt.isPresent()) {
            Items items = items_opt.get();
            ResponseItems responseItems = ResponseItems.builder()
                    .banner_image(items.getBannerImage())
                    .uc(allUc)
                    .isActive(items.getIsActive())
                    .build();
            if (items.getTitle().get(lang) != null) {
                responseItems.setTitle(items.getTitle().get(lang));
            } else {
                responseItems.setTitle(items.getTitle().get("uz"));
            }

            ResponseResult<ResponseItems> responseResult = ResponseResult.<ResponseItems>builder()
                    .result(responseItems)
                    .error(null)
                    .build();

            return ResponseAll.<ResponseResult<ResponseItems>>builder()
                    .response(responseResult)
                    .status(200)
                    .build();
        } else {
            ResponseResult<ResponseItems> responseResult = ResponseResult.<ResponseItems>builder()
                    .result(null)
                    .error(Error.builder()
                            .errorMessage("Not Found")
                            .errorCode(404)
                            .build())
                    .build();

            return ResponseAll.<ResponseResult<ResponseItems>>builder()
                    .response(responseResult)
                    .status(400)
                    .build();
        }
    }

    public void addAppBanner(ItemsDto itemsDto, HttpServletRequest request) {
        Items items = Items.builder()
                .bannerImage(itemsDto.getBanner_image())
                .isActive(true)
                .build();
        items.setTitle(itemsDto.getTitle());
        itemsRepository.save(items);
    }







    public String deleteItems(int id) {
        Optional<Items> byId = itemsRepository.findById(id);
        if (byId.isPresent()) {
            itemsRepository.delete(byId.get());
            return "Item deleted";
        } else
            return "Item not found";
    }
}
