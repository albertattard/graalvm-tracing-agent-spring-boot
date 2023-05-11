package demo.quote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

    @GetMapping("/quote")
    public ResponseEntity<Quote> quote() {
        final Quote quote = new Quote("Albert Einstein", "Learn from yesterday, live for today, hope for tomorrow. The important thing is not to stop questioning.");
        return ResponseEntity.ok(quote);
    }
}
