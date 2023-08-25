package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.entitiy.Person;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.exception.Constant;
import com.example.emlakjet_summer_project.exception.EmailAlreadyException;
import com.example.emlakjet_summer_project.exception.PersonNotFoundException;
import com.example.emlakjet_summer_project.exception.PhoneNumberAlreadyException;
import com.example.emlakjet_summer_project.repository.PersonRepository;
import com.example.emlakjet_summer_project.request.CreatePersonRequest;
import com.example.emlakjet_summer_project.response.CreatePersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.emlakjet_summer_project.converter.PersonConverter;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    private final PersonConverter personConverter;

    //Database tablolarını dışarı açmak güvenlik sorunu oluşturur
    //Oluşacak döngü sorunlarını engellemek için
    //Örnek olarak, ilişkilerden dolayı oluşabilecek döngüleri engellememizi sağlar
    //Bu yüzden direkt olarak person sınıfını kullanmak yerine request ve response sınıflarını oluşturup bunları birbirine çevirebiliyoruz.

    public CreatePersonResponse createPerson(CreatePersonRequest request){
        createPersonErrorCatcher(request);
        Person person = new Person(
                        request.getName(),
                        request.getSurName(),
                        request.getEmail(),
                        request.getPhoneNumber(),
                        request.getPassword(),
                        Status.ACTIVE);
        Person save = personRepository.save(person);
        return personConverter.createPersonConverter(save);

    }
    public void deletePerson(String id){
        personRepository.delete(findById(id));
    }
    protected Person findById(String id){
        return personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(Constant.PERSON_NOT_FOUND));
    }

    private void createPersonErrorCatcher(CreatePersonRequest request) {
        if (personRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyException(Constant.EMAIL_ALREADY_EXIST);
        }
        if (personRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new PhoneNumberAlreadyException(Constant.PHONE_NUMBER_ALREADY_EXIST);
        }
    }

}
