package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.entitiy.Person;
import com.example.emlakjet_summer_project.entitiy.SecurityPerson;
import com.example.emlakjet_summer_project.entitiy.enums.Role;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.exception.Constant;
import com.example.emlakjet_summer_project.exception.EmailAlreadyException;
import com.example.emlakjet_summer_project.exception.PersonNotFoundException;
import com.example.emlakjet_summer_project.exception.PhoneNumberAlreadyException;
import com.example.emlakjet_summer_project.repository.PersonRepository;
import com.example.emlakjet_summer_project.request.CreatePersonRequest;
import com.example.emlakjet_summer_project.response.CreatePersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.emlakjet_summer_project.converter.PersonConverter;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final CachePersonService cachePersonService;
    private final PersonRepository personRepository;

    private final BCryptPasswordEncoder passwordEncoder;

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
                        passwordEncoder.encode(request.getPassword()),
                        Status.ACTIVE,
                        Role.USER);
        Person save = personRepository.save(person);
        cachePersonService.cachePersonStatus(save);
        return personConverter.createPersonConverter(save);

    }
    public void deletePerson(String id){
        cachePersonService.deleteCachePerson(findById(id).getId());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = findPersonByEmail(username);
        return new SecurityPerson(person);
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(
                () -> new PersonNotFoundException(Constant.PERSON_NOT_FOUND));
    }
}
