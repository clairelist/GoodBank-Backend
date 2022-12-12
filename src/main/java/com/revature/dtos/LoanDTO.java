package com.revature.dtos;

import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private User userId;
    private String reason;
    private int initialAmount;
    private String password;
}
