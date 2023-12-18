package com.ashutosh.controller;

import com.ashutosh.data.InMemoryStore;
import com.ashutosh.model.Symbol;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class SymbolsControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(SymbolsControllerTest.class);

    @Inject
    @Client("/symbols")
    private HttpClient client;

    @Inject
    InMemoryStore inMemoryStore;

    @BeforeEach
    void initialize(){
        inMemoryStore.initialize();
    }

    @Test
    void symbolsEndpointReturnsListOfSymbols(){
        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(10, response.getBody().get().size());
    }

    @Test
    void symbolsEndpointReturnsSingleSymbolByPathVariable(){
        var testSymbol = new Symbol("2");
        inMemoryStore.getSymbols().put(testSymbol.value(), testSymbol);

        var response = client.toBlocking().exchange("/" + testSymbol.value(), Symbol.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(testSymbol, response.getBody().get());
    }

    @Test
    void symbolsEndpointReturnsSingleSymbolByQueryParameters(){
        var response = client.toBlocking().exchange("/filter", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());

        // if no max is defined, api is returning 5 records
        assertEquals(5, response.getBody().get().size());
    }

    @Test
    void symbolsEndpointReturnsSymbolsByQueryParametersLimit(){
        var response = client.toBlocking().exchange("/filter?max=2", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(2, response.getBody().get().size());
    }

    @Test
    void symbolsEndpointReturnsSymbolsByQueryParametersLimitOffset(){
        var response = client.toBlocking().exchange("/filter?max=2&offset=1", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        LOG.debug("MAX 2, Offset 1, {}", response.getBody().get().toString());
        assertEquals(2, response.getBody().get().size());
    }
}
