package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.listener.RabbitStatusChangeWorker;

import com.example.emlakjet_summer_project.entitiy.AdvertEntity;
import com.example.emlakjet_summer_project.entitiy.enums.AdvertType;
import com.example.emlakjet_summer_project.entitiy.enums.RoomNumber;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.core.exception.AdvertNotFound;
import com.example.emlakjet_summer_project.core.exception.Constant;
import com.example.emlakjet_summer_project.core.exception.PersonNotActiveException;
import com.example.emlakjet_summer_project.repository.AdvertRepository;
import com.example.emlakjet_summer_project.request.CreateAdvertRequest;
import com.example.emlakjet_summer_project.request.UpdateAdvertRequest;
import com.example.emlakjet_summer_project.response.CreateAdvertResponse;
import com.example.emlakjet_summer_project.response.GetAdvertResponse;
import com.example.emlakjet_summer_project.response.UpdateAdvertResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final CachePersonService cachePersonService;
    private final PersonService personService;
    private final AdvertRepository advertRepository;

    //  private final AdvertConverter advertConverter;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitStatusChangeWorker rabbitStatusChangeWorker;

    private final ModelMapper modelMapper;

    public CreateAdvertResponse createAdvert(CreateAdvertRequest request) {
        AdvertEntity advert = null;
        if (cachePersonService.getCachePersonStatus(request.getPersonId())) {
            if (request.getType().toString().equals(AdvertType.HOUSE.toString())) {
                advert = new AdvertEntity(
                        request.getType(),
                        request.getTitle(),
                        request.getDescription(),
                        request.getPrice(),
                        request.getRoomNumber(),
                        request.getGrossM(),
                        request.getNetM(),
                        Status.ACTIVE,
                        personService.findById(request.getPersonId()));
            } else {
                advert = new AdvertEntity(
                        request.getType(),
                        request.getTitle(),
                        request.getDescription(),
                        request.getPrice(),
                        RoomNumber.UNKNOWN,
                        request.getGrossM(),
                        request.getNetM(),
                        Status.ACTIVE, personService.findById(request.getPersonId()));
            }
            return modelMapper.map(advertRepository.save(advert), CreateAdvertResponse.class);
        } else {
            throw new PersonNotActiveException(Constant.PERSON_NOT_ACTIVE);
        }

    }

    public UpdateAdvertResponse updateAdvert(UpdateAdvertRequest request) {
        AdvertEntity advert = findById(request.getId());
        if (cachePersonService.getCachePersonStatus(advert.getAdvertiser().getId())) {
            advert.setType(request.getType());
            advert.setTitle(request.getTitle());
            advert.setDescription(request.getDescription());
            advert.setPrice(request.getPrice());
            advert.setGrossM(request.getGrossM());
            advert.setNetM(request.getNetM());
            advert.setRoomNumber(request.getRoomNumber());
            return modelMapper.map(advertRepository.save(advert), UpdateAdvertResponse.class);

        } else {
            throw new PersonNotActiveException(Constant.PERSON_NOT_ACTIVE);
        }
    }

    public UpdateAdvertResponse updateStatusAdvert(String id, Status status) {
        AdvertEntity advert = findById(id);
        if (cachePersonService.getCachePersonStatus(advert.getAdvertiser().getId())) {
            advert.setStatus(status);
            advertRepository.save(advert);

            rabbitTemplate.convertAndSend("advert_status_changes", "advert_" + id + "_changed_" + status);
            rabbitStatusChangeWorker.AdvertStatusChangeMessage("advert_" + id + "_changed_" + status);

            return modelMapper.map(advert, UpdateAdvertResponse.class);
        } else {
            throw new PersonNotActiveException(Constant.PERSON_NOT_ACTIVE);
        }
    }

    public void deleteAdvert(String id) {
        AdvertEntity advert = findById(id);
        if (cachePersonService.getCachePersonStatus(advert.getAdvertiser().getId())) {
            advertRepository.delete(advert);
        } else {
            throw new PersonNotActiveException(Constant.PERSON_NOT_ACTIVE);
        }
    }

    public List<GetAdvertResponse> getAllAdvert() {
        List<AdvertEntity> adverts = advertRepository.findAll().stream().filter(
                advert -> advert.getStatus().toString().equals(Status.ACTIVE.toString()) &&
                        cachePersonService.getCachePersonStatus(advert.getAdvertiser().getId())).toList();
        return adverts
                .stream()
                .map(advert -> modelMapper.map(advert, GetAdvertResponse.class)).toList();
    }

    public List<GetAdvertResponse> search(String query) {
        return advertRepository.findByDescriptionLikeOrTitleLike(
                        "%" + query + "%", "%" + query + "%")
                .stream()
                .map(advert -> modelMapper.map(advert, GetAdvertResponse.class)).toList();
    }

    public List<GetAdvertResponse> filterAdvertType(AdvertType type) {
        return advertRepository.findByType(type)
                .stream()
                .map(advert -> modelMapper.map(advert, GetAdvertResponse.class)).toList();
    }

    public List<GetAdvertResponse> filterBetweenPrice(String minPrice, String maxPrice) {
        return advertRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(advert -> modelMapper.map(advert, GetAdvertResponse.class)).toList();
    }

    private AdvertEntity findById(String id) {
        return advertRepository.findById(id).orElseThrow(
                () -> new AdvertNotFound(Constant.ADVERT_NOT_FOUND));

    }
}
