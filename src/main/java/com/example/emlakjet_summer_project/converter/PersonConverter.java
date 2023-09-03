package com.example.emlakjet_summer_project.converter;


import com.example.emlakjet_summer_project.entitiy.PersonEntity;
import com.example.emlakjet_summer_project.response.CreatePersonResponse;
import com.example.emlakjet_summer_project.response.GetPersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonConverter {


    public CreatePersonResponse createPersonConverter(PersonEntity person) {
        CreatePersonResponse createPersonResponse = new CreatePersonResponse(
                person.getId(),
                person.getName(),
                person.getSurName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getPassword(),
                person.getStatus());
        return createPersonResponse;
    }
    public GetPersonResponse getPersonConverter(PersonEntity person){
        GetPersonResponse getPersonResponse = new GetPersonResponse(
                person.getId(),
                person.getName(),
                person.getSurName(),
                person.getEmail(),
                person.getPhoneNumber(),
                person.getPassword(),
                person.getStatus());
        return getPersonResponse;
    }
}

