package demo.quote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate()
                .setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void fetchTheQuote() {
        final ResponseEntity<Quote> response = restTemplate.getForEntity("/quote", Quote.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new Quote("Albert Einstein", "Learn from yesterday, live for today, hope for tomorrow. The important thing is not to stop questioning."));
    }
}
