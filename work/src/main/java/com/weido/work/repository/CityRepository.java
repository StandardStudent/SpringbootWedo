package com.weido.work.repository;

import com.weido.work.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City,Integer> {
    @Query(value = "select c.* from cities c where city_name=?1",nativeQuery = true)
    City findAllByCity_name(String cityName);
}
