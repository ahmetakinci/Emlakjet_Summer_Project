package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.converter.AdvertConverter;
import com.example.emlakjet_summer_project.entitiy.Advert;
import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.exception.AdvertNotFound;
import com.example.emlakjet_summer_project.exception.Constant;
import com.example.emlakjet_summer_project.repository.AdvertRepository;
import com.example.emlakjet_summer_project.request.CreateAdvertRequest;
import com.example.emlakjet_summer_project.request.UpdateAdvertRequest;
import com.example.emlakjet_summer_project.response.CreateAdvertResponse;
import com.example.emlakjet_summer_project.response.GetAdvertResponse;
import com.example.emlakjet_summer_project.response.UpdateAdvertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final PersonService personService;
    private final AdvertRepository advertRepository;
    private final AdvertConverter advertConverter;

    public CreateAdvertResponse createAdvert(CreateAdvertRequest request){
        Advert advert = null;
        if (request.getType().toString().equals(AdvertType.HOUSE.toString())){
            advert = new Advert(
                    request.getType(),
                    request.getTitle(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getRoomNumber(),
                    request.getGrossM(),
                    request.getNetM(),
                    Status.ACTIVE,
                    personService.findById(request.getPersonId()));
        }
        else {
            advert = new Advert(
                    request.getType(),
                    request.getTitle(),
                    request.getDescription(),
                    request.getPrice(),
                    RoomNumber.UNKNOWN,
                    request.getGrossM(),
                    request.getNetM(),
                    Status.ACTIVE, personService.findById(request.getPersonId()));
        }
        return advertConverter.createAdvertConverter(advertRepository.save(advert));
    }
    public UpdateAdvertResponse updateAdvert(UpdateAdvertRequest request){
        Advert advert = findById(request.getId());
        advert.setType(request.getType());
        advert.setTitle(request.getTitle());
        advert.setDescription(request.getDescription());
        advert.setPrice(request.getPrice());
        advert.setGrossM(request.getGrossM());
        advert.setNetM(request.getNetM());
        advert.setRoomNumber(request.getRoomNumber());
        return advertConverter.updateAdvertConverter(advertRepository.save(advert));
    }
    public UpdateAdvertResponse updateStatusAdvert(String id,Status status){
        Advert advert = findById(id);
        advert.setStatus(status);
        return advertConverter.updateAdvertConverter(advertRepository.save(advert));
    }
    public void deleteAdvert(String id){
        advertRepository.delete(findById(id));
    }
    public List<GetAdvertResponse> getAllAdvert(){
        List<Advert> adverts = advertRepository.findAll().stream().filter(
                advert -> advert.getStatus().toString().equals(Status.ACTIVE.toString())).toList();
        return advertConverter.getAdvertConverter(adverts);

    }
    private Advert findById(String id){
        return advertRepository.findById(id).orElseThrow(
                () -> new AdvertNotFound(Constant.ADVERT_NOT_FOUND));

    }
}
