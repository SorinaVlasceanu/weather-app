package com.fitbit.weatherapp.repository;

import com.fitbit.weatherapp.model.CachedWeatherModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Sorina23 on 7/14/2017.
 */
@Repository
public interface CachedWeatherRepository extends MongoRepository<CachedWeatherModel, String> {

    /**
     * Queries MongoDb repo for the document whose city field matches the given cityName param
     * @param cityName
     * @return CachedWeatherModel object having only _id (default) and lastUpdatedAt fields populated
     */
    @Query(value="{ 'city' : ?0 }", fields="{'lastUpdatedAt' : 1}")
    CachedWeatherModel findLastUpdatedByCity(String cityName);

    /**
     * Queries MongoDb repo for the document whose city field matches the given cityName param
     * @param cityName
     * @return CachedWeatherModel object having only _id (default) and currentWeather fields populated
     */
    @Query(value="{ 'city' : ?0 }", fields="{'currentWeather' : 1}")
    CachedWeatherModel findCurrentWeatherByCity(String cityName);

    /**
     * Queries MongoDb repo for the document whose city field matches the given cityName param
     * @param cityName
     * @return CachedWeatherModel object having only _id (default) and forecast fields populated
     */
    @Query(value="{ 'city' : ?0 }", fields="{'forecast' : 1}")
    CachedWeatherModel findForecastByCity(String cityName);
}
