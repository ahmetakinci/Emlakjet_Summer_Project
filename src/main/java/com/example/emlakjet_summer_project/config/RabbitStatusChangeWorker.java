package com.example.emlakjet_summer_project.config;

import com.example.emlakjet_summer_project.entitiy.Advert;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.exception.AdvertNotFound;
import com.example.emlakjet_summer_project.exception.Constant;
import com.example.emlakjet_summer_project.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitStatusChangeWorker {
    private final AdvertRepository advertRepository;

    @RabbitListener(queues = "advert_status_changes")
    public void processAdStatusChangeMessage(String message) {
        String[] parts = message.split("_");
        String adId = parts[1];
        Status newStatus = Status.valueOf(parts[3]);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Advert advert = findById(adId);
        advert.setStatus(newStatus);
        advertRepository.save(advert);
    }
    private Advert findById(String id){
        return advertRepository.findById(id).orElseThrow(
                () -> new AdvertNotFound(Constant.ADVERT_NOT_FOUND));

    }
}
