package com.online.store.waa3l.controller.admin;

import com.online.store.waa3l.domain.Advertisement;
import com.online.store.waa3l.domain.Image;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.service.AdvertisementService;
import com.online.store.waa3l.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    ResourceLoader resourceLoader;


    @GetMapping("/ads/list")
    public String showFormAds(@ModelAttribute("ads") Advertisement ads, Model model) {
        model.addAttribute("adses", advertisementService.findAllAds());

        return "admin/ads";
    }

    @PostMapping("/ads/save")
    public String saveAds(@ModelAttribute("ads") Advertisement ads, @RequestParam(value = "fileImage", required = false) MultipartFile fileImage) {

        Resource resource = resourceLoader.getResource("classpath:static/img/core-img/ads-img/");
        String path = null;
        try {
            path = resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = Util.randomString() + fileImage.getOriginalFilename();
        Image image = new Image(fileName);
        File imageFile = new File(path, fileName);
        try {
            fileImage.transferTo(imageFile);

        } catch (IOException e) {
            e.printStackTrace();

        }

        ads.setImage(image);

        advertisementService.save(ads);
        return "redirect:/admin/ads/list";
    }


    @PostMapping("/ads/approve/{adsId}")
    @ResponseBody
    public Response approveAdsToShow(@PathVariable("adsId") Long adsId){
        List<Advertisement> advertisementList = advertisementService.findAllAds();

        for(Advertisement advertisement: advertisementList){
            if(advertisement.getId() == adsId){
                advertisement.setShow(true);
            }else
                advertisement.setShow(false);
        }
        return new Response(advertisementService.saveAllAds(advertisementList));

    }


}
