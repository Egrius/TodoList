package by.egrius.service;

import by.egrius.database.repository.UserRepository;
import by.egrius.dto.UserCreateEditDto;
import by.egrius.dto.UserReadDto;
import by.egrius.mapper.UserCreateEditMapper;
import by.egrius.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    // private final PasswordEncoder passwordEncoder;

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::map);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto object) {
        return Optional.of(object)
                .map(userCreateEditMapper::map)     // Сначала перевели в ентитю
                .map(userRepository::save)      // Затем сохранили ЕНТИТЮ в БД
                .map(userReadMapper::map)       // После чего приводим в readDto
                .orElseThrow();
    }

    @Transactional
    public UserReadDto update(Long id, UserCreateEditDto object) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(object))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow();

    }

    // public boolean delete(Long userId, String passwordHash) { }

    // public List<UserReadDto> findAll(UserFilter) { }
}
