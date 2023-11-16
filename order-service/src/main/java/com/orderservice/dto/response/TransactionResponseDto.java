package com.orderservice.dto.response;

import com.orderservice.dto.TransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponseDto {

	private Integer userId;
	private Double amount;
	private TransactionStatus status;

}
