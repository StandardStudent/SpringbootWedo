package com.weido.work.service;

import com.weido.work.pojo.City;
import com.weido.work.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;
    public City findAllByCityName(String cityName){
        City city = cityRepository.findAllByCity_name(cityName);
        return city;
    }
}
