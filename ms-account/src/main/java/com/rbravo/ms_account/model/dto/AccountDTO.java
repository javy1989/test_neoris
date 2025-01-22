package com.rbravo.ms_account.model.dto;

import com.rbravo.ms_account.model.enums.AccountTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Account.
 *
 * @author rbravo
 */
public class AccountDTO {

    private String accountNumber;

    @NotBlank(message = "Account type is mandatory")
    @Pattern(regexp = "^(SAVINGS|CURRENT)$", message = "Gender must be SAVINGS, CURRENT")
    private AccountTypeEnum accountType;

    @NotNull(message = "Initial balance is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Initial balance must be non-negative")
    private BigDecimal balance;

    @NotNull(message = "Status is mandatory")
    private Boolean status;

    @NotNull(message = "Client ID is mandatory")
    private Long clientId;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
