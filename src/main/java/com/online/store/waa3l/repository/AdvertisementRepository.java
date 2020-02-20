package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Advertisement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

    @Query("select a from Advertisement a where a.show = true")
    Advertisement getActiveAds();

}
