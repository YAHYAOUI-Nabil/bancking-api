package com.javatuto.bankingapp.dto;

import lombok.Builder;


@Builder
public record AccountDto(Long id, String accountHolderName, double balance) {}
