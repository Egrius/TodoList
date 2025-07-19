package by.egrius.unit.service;

import by.egrius.database.entity.User;
import by.egrius.database.repository.UserRepository;
import by.egrius.dto.UserCreateEditDto;
import by.egrius.dto.UserReadDto;
import by.egrius.mapper.UserCreateEditMapper;
import by.egrius.mapper.UserReadMapper;
import by.egrius.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private UserCreateEditMapper userCreateEditMapper;


    @Mock
    private UserCreateEditDto userCreateEditDto;

    @Test
    void findByUsernameTest() {
        String username = "A";
        User mockUser = new User(6L, username, "A@gmail.com", "12345", Collections.emptyList());
        UserReadDto expectedDto = new UserReadDto(6L, username, "A@gmail.com");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(userReadMapper.map(mockUser)).thenReturn(expectedDto);

        Optional<UserReadDto> actualResult = userService.findByUsername(username);

        assertTrue(actualResult.isPresent());
        assertEquals(expectedDto.id(), actualResult.get().id());
        assertEquals(expectedDto.username(), actualResult.get().username());
        assertEquals(expectedDto.email(), actualResult.get().email());

        verify(userRepository).findByUsername(username);
        verify(userReadMapper).map(mockUser);
    }

    @Test
    void findByIdTest() {
        Long id = 1L;

        User mockUser = new User(id, null, null, null,Collections.emptyList());

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));
        when(userReadMapper.map(mockUser)).thenReturn(new UserReadDto(id, null, null));

        Optional<UserReadDto> actualResult = userService.findById(id);

        assertTrue(actualResult.isPresent());

        UserReadDto expected = new UserReadDto(id, null, null);

        actualResult.ifPresent(actual -> assertEquals(actual, expected));
    }

    @Test
    void findAllTest() {

        List<User> mockUsers = List.of(
                new User(1L, "user1", "user1@mail.com", "pass1", Collections.emptyList()),
                new User(2L, "user2", "user2@mail.com", "pass2", Collections.emptyList())
        );

        List<UserReadDto> expectedDtos = List.of(
                new UserReadDto(1L, "user1", "user1@mail.com"),
                new UserReadDto(2L, "user2", "user2@mail.com")
        );

        Map<User, UserReadDto> mockMap = Map.of(
                mockUsers.get(0), expectedDtos.get(0),
                mockUsers.get(1), expectedDtos.get(1)
        );

        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userReadMapper.map(any(User.class))).thenAnswer(invocation -> mockMap.get(invocation.getArgument(0)));

        List<UserReadDto> actualResult = userService.findAll();

        assertEquals(actualResult, expectedDtos);
    }

    @Test
    void createTest() {

        UserCreateEditDto mockDto = new UserCreateEditDto(null, null, null);
        User mockUser = new User(null, null, null, null, Collections.emptyList());
        UserReadDto expectedDto = new UserReadDto(null, null, null);

        when(userCreateEditMapper.map(mockDto)).thenReturn(mockUser);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userReadMapper.map(mockUser)).thenReturn(expectedDto);

        UserReadDto actualResult = userService.create(mockDto);

        assertNotNull(actualResult);

        assertEquals(expectedDto, actualResult);

        verify(userCreateEditMapper).map(mockDto);
        verify(userRepository).save(mockUser);
        verify(userReadMapper).map(mockUser);

    }

    @Test
    void updateTest() {
        Long id = 2L;
        User mockUserEntity = new User(id, null, null, null, null);
        User mockNewUserEntity = new User(id, "test", "test@gmail.com", "{noop}12345", Collections.emptyList());
        UserReadDto mockNewUserDto = new UserReadDto(id, "test", "test@gmail.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUserEntity));
        when(userCreateEditMapper.map(any(UserCreateEditDto.class))).thenAnswer(invocation -> mockNewUserEntity);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(mockNewUserEntity);
        when(userReadMapper.map(any(User.class))).thenReturn(mockNewUserDto);

        UserReadDto actualResult = userService.update(id, userCreateEditDto);

        assertNotNull(actualResult);

        assertEquals(actualResult, mockNewUserDto);

        InOrder inOrder = inOrder(userRepository, userCreateEditMapper, userReadMapper);

        inOrder.verify(userRepository).findById(id);
        inOrder.verify(userCreateEditMapper).map(userCreateEditDto);
        inOrder.verify(userRepository).saveAndFlush(mockNewUserEntity);
        inOrder.verify(userReadMapper).map(mockNewUserEntity);
    }

    @Test
    void deleteTest() {

    }
 }