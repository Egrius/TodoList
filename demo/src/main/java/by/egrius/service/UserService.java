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

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::map);
    }

    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userReadMapper::map);
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
                .map(entity -> {
                    entity.setUsername(object.getUsername());
                    entity.setEmail(object.getEmail()); // даже если email не меняется — всё нормально
                    entity.setPassword(object.getPassword());
                    return userRepository.saveAndFlush(entity);
        })
                .map(userReadMapper::map)
                .orElseThrow();

    }

    @Transactional
    public boolean delete(Long userId) {
        return userRepository.findById(userId).map(
                user  -> {
                    userRepository.delete(user);
                    return true;
                }
        ).orElse(false);
    }
}
