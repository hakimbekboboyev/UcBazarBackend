package ru.moscow.ucbazar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moscow.ucbazar.dto.ItemsDto;
import ru.moscow.ucbazar.responses.ResponseItems;
import ru.moscow.ucbazar.entity.Items;
import ru.moscow.ucbazar.entity.UcEntity;
import ru.moscow.ucbazar.model.UcModel;
import ru.moscow.ucbazar.repository.ItemsRepository;
import ru.moscow.ucbazar.repository.UcRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UcService {

    @Autowired
    UcRepository ucRepository;

    @Autowired
    ItemsRepository itemsRepository;

    List<UcEntity> ucEntities = new ArrayList<>();


    public void addUc(UcModel ucModel) {
        UcEntity ucEntity = UcEntity.builder()
                .uc_name(ucModel.getUc_name())
                .uc_image(ucModel.getUc_image())
                .uc_amount(ucModel.getUc_amount())
                .uc_value(ucModel.getUc_value())
                .bonus(ucModel.getBonus())
                .build();

        Items items = itemsRepository.findById(1).get();
        ucEntities.add(ucEntity);

        items.setUc(ucEntities);

        ucRepository.save(ucEntity);
        itemsRepository.save(items);


    }

    public void addAppBanner(ItemsDto itemsDto, HttpServletRequest request) {
        Items items = Items.builder()
                .bannerImage(itemsDto.getBanner_image())
                .isActive(true)
                .build();
        items.setTitle(itemsDto.getTitle());
        itemsRepository.save(items);
    }

    public ResponseItems getAll(String lang) {

        List<UcEntity> allUc = ucRepository.findAll();


        Items items = itemsRepository.findById(1).get();
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



        return responseItems;

    }




}
