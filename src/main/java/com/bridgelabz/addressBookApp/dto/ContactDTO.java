package com.bridgelabz.addressBookApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ContactDTO  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotBlank(message = "Name is required")  // Ensures the name is not blank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces") // Ensures the name only contains letters and spaces
    private String name;

    private String email;
    private String phone;
    private String city;


}
