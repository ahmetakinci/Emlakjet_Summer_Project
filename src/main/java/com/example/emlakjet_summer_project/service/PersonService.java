package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.entitiy.PersonEntity;
import com.example.emlakjet_summer_project.core.security.SecurityPerson;
import com.example.emlakjet_summer_project.entitiy.enums.Role;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import com.example.emlakjet_summer_project.core.exception.Constant;
import com.example.emlakjet_summer_project.core.exception.EmailAlreadyException;
import com.example.emlakjet_summer_project.core.exception.PersonNotFoundException;
import com.example.emlakjet_summer_project.core.exception.PhoneNumberAlreadyException;
import com.example.emlakjet_summer_project.repository.PersonRepository;
import com.example.emlakjet_summer_project.request.CreatePersonRequest;
import com.example.emlakjet_summer_project.response.CreatePersonResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final CachePersonService cachePersonService;
    private final PersonRepository personRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    //  private final PersonConverter personConverter;

    private final ModelMapper modelMapper;

    public CreatePersonResponse createPerson(CreatePersonRequest request) {
        createPersonErrorCatcher(request);
        PersonEntity person = new PersonEntity(
                request.getName(),
                request.getSurName(),
                request.getEmail(),
                request.getPhoneNumber(),
                passwordEncoder.encode(request.getPassword()),
                Status.ACTIVE,
                Role.USER);
        PersonEntity save = personRepository.save(person);
        cachePersonService.cachePersonStatus(save);
        return modelMapper.map(save, CreatePersonResponse.class);
    }

    public void deletePerson(String id) {
        cachePersonService.deleteCachePerson(findById(id).getId());
        personRepository.delete(findById(id));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonEntity person = findPersonByEmail(username);
        return new SecurityPerson(person);
    }

    public PersonEntity findPersonByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(
                () -> new PersonNotFoundException(Constant.PERSON_NOT_FOUND));
    }

    protected PersonEntity findById(String id) {
        return personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(Constant.PERSON_NOT_FOUND));
    }

    private void createPersonErrorCatcher(CreatePersonRequest request) {
        if (personRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyException(Constant.EMAIL_ALREADY_EXIST);
        }
        if (personRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyException(Constant.PHONE_NUMBER_ALREADY_EXIST);
        }
    }
}
