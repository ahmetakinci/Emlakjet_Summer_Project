package com.example.emlakjet_summer_project.service;

import com.example.emlakjet_summer_project.entitiy.PersonEntity;
import com.example.emlakjet_summer_project.entitiy.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CachePersonService {
    private static final String PERSON_CACHE_PREFIX = "person:";

    private final RedisTemplate<String, Status> redisTemplate;

    public void cachePersonStatus(PersonEntity person) {
        redisTemplate.opsForValue().set(PERSON_CACHE_PREFIX + person.getId(), person.getStatus());
    }

    public boolean getCachePersonStatus(String personId) {
        Status status = redisTemplate.opsForValue().get(PERSON_CACHE_PREFIX + personId);
        return status == Status.ACTIVE;

    }

    public void deleteCachePerson(String id) {
        redisTemplate.delete(PERSON_CACHE_PREFIX + id);
    }
}
