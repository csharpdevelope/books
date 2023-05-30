package com.library.book.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book.model.dto.google.GoogleResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
//        httpService();
    }

    private void httpService() {
        logger.info("Http Service running");
        try {
            HttpEntity<?> http = new HttpEntity<>(objectMapper.createObjectNode());
            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                    "https://www.googleapis.com/books/v1/volumes?q=harry%20Potter",
                    HttpMethod.GET, http, JsonNode.class);
            JsonNode json = responseEntity.getBody();
            GoogleResponse response = objectMapper.readValue(json.toString(), GoogleResponse.class);
            logger.info(response.toString());
            return;
        } catch (HttpStatusCodeException e) {
            logger.error("Error Code : {}", e.getStatusCode());
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("Http service end");
    }
}
