package com.orderservice.service;

import org.springframework.stereotype.Service;

import com.orderservice.client.ProductClient;
import com.orderservice.client.UserClient;
import com.orderservice.dto.RequestContext;
import com.orderservice.dto.request.OrderRequestDto;
import com.orderservice.dto.response.OrderResponseDto;
import com.orderservice.repository.OrderRepository;
import com.orderservice.util.EntityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final UserClient userClient;

	public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> mono) {
		return mono
				.map(RequestContext::new)
				.flatMap(this::getProductRequestResponse)
				.doOnNext(EntityMapper::setTransactionRequestDto)
				.flatMap(this::getUserRequestResponse)
				.doOnNext(EntityMapper::getOrderEntity)
				.map(EntityMapper::getOrderEntity)
				.map(orderRepository::save)
				.map(EntityMapper::getOrderResponseDto)
				.subscribeOn(Schedulers.boundedElastic())
				.doOnNext(orderResponseDto -> log.info("Order processed successfully"))
				.doOnError(e -> log.error("Error processing order", e));
	}

	private Mono<RequestContext> getProductRequestResponse(RequestContext requestContext) {
		return productClient
				.getProductById(requestContext.getOrderRequestDto().getProductId())
				.doOnNext(requestContext::setProductDto)
				.thenReturn(requestContext);
	}

	private Mono<RequestContext> getUserRequestResponse(RequestContext requestContext) {
		return userClient
				.authorizeTransaction(requestContext.getTransactionRequestDto())
				.doOnNext(requestContext::setTransactionResponseDto)
				.thenReturn(requestContext);
	}

}
