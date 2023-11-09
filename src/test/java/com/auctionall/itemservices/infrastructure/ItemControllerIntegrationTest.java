package com.auctionall.itemservices.infrastructure;

import com.auctionall.itemservices.application.domain.shared.Status;
import com.auctionall.itemservices.infrastructure.adapter.in.rest.resource.ItemRequest;
import com.auctionall.itemservices.infrastructure.adapter.out.persistence.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerIntegrationTest {

    @Value("classpath:h2/init.sql")
    private Resource initSql;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ItemRepository userRepository;

    @Autowired
    private TransactionalOperator transactionalOperator;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @BeforeEach
    public void setUp() throws IOException {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
    }

    @Test
    public void given_NewItem_When_CreateItem_Then_Create_Item_Success() {
        ItemRequest itemRequest = new ItemRequest(null, 1000,"Ferrari 1941", new BigDecimal("11000000.00"), Status.ENABLED);

        webClient.post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(itemRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("name").isEqualTo(itemRequest.name());
    }

}