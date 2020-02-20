package com.online.store.waa3l.service;

import com.online.store.waa3l.domain.Advertisement;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> findAllAds();

    void save(Advertisement ads);

    List<Advertisement> saveAllAds(List<Advertisement> advertisementList);

    Advertisement getActiveAdvertisement();



}

