package mate.academy;

import java.math.BigDecimal;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Wiedźmin");
            book.setIsbn("12345");
            book.setDescription("Amazing book.");
            book.setCoverImage("coverImage");
            book.setPrice(BigDecimal.valueOf(30));
            book.setAuthor("Andrzej Sapkowski");

            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
