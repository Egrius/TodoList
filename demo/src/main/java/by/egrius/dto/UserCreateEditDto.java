package by.egrius.dto;


import lombok.Value;

@Value
public class UserCreateEditDto {
     String username;
     String email;
     String password;
}
