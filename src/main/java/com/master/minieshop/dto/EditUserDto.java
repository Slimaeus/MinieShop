package com.master.minieshop.dto;

import com.master.minieshop.enumeration.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EditUserDto {
    private Long id;
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    private String userName;
    @Length(min = 10, max = 10, message = "Phone must be 10 characters")
    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
    private String phoneNumber;
    @NotBlank(message = "Full name is required")
    private String fullName;
    private Gender gender = Gender.Male;
    @Size(max = 50, message = "Email must be less than 50 characters")
    @Email
    private String email;
}
