package com.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.orderservice.dto.ProductDto;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class ProductClient {

	private final WebClient webClient;

	public ProductClient(@Value("${product.service.url}") String url) {
		this.webClient = WebClient.builder().baseUrl(url).build();
	}

	public Mono<ProductDto> getProductById(final String productId) {
		Mono<ProductDto> product = webClient
				.get()
				.uri("/{productId}", productId)
				.retrieve()
				.bodyToMono(ProductDto.class)
				.doOnNext(p -> log.info("Product with id {} retrieved", productId))
				.doOnError(e -> log.error("Error retrieving product with id {}", productId, e));

		return product;
	}

}
