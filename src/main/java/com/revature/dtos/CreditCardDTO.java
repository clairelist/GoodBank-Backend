package com.revature.dtos;

import com.revature.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO {
    private Integer id;
    private Status status;
}
