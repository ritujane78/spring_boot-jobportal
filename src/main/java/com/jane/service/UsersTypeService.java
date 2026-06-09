package com.jane.service;

import com.jane.entity.UsersType;
import com.jane.repository.UsersTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;

    public List<UsersType> findAll() {
        return usersTypeRepository.findAll();
    }
}
