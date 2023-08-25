package com.example.emlakjet_summer_project.converter;

import com.example.emlakjet_summer_project.entitiy.Advert;
import com.example.emlakjet_summer_project.response.CreateAdvertResponse;
import com.example.emlakjet_summer_project.response.GetAdvertResponse;
import com.example.emlakjet_summer_project.response.UpdateAdvertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertConverter {
    public final PersonConverter personConverter;

    public List<GetAdvertResponse> getAdvertConverter(List<Advert> adverts){
        List<GetAdvertResponse> getAdvertResponses = adverts.
                stream().
                map(advert -> new GetAdvertResponse(
                        advert.getId(),
                        advert.getType(),
                        advert.getTitle(),
                        advert.getDescription(),
                        advert.getPrice(),
                        advert.getRoomNumber(),
                        advert.getGrossM(),
                        advert.getNetM(),
                        advert.getStatus(),
                        personConverter.getPersonConverter(advert.getAdvertiser()))).toList();
        return getAdvertResponses;
    }
    public CreateAdvertResponse createAdvertConverter(Advert advert){
        CreateAdvertResponse createAdvertResponse = new CreateAdvertResponse(
                advert.getId(),
                advert.getType(),
                advert.getTitle(),
                advert.getDescription(),
                advert.getPrice(),
                advert.getRoomNumber(),
                advert.getGrossM(),
                advert.getNetM());
        return createAdvertResponse;
    }
    public UpdateAdvertResponse updateAdvertConverter(Advert advert){
        UpdateAdvertResponse updateAdvertResponse = new UpdateAdvertResponse(
                advert.getId(),
                advert.getType(),
                advert.getTitle(),
                advert.getDescription(),
                advert.getPrice(),
                advert.getRoomNumber(),
                advert.getGrossM(),
                advert.getNetM(),
                advert.getStatus());
        return updateAdvertResponse;
    }
}
