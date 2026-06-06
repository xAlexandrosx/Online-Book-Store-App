package mate.academy;

import java.math.BigDecimal;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreAppApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                Book wiedzmin = new Book();
                wiedzmin.setTitle("Wiedźmin");
                wiedzmin.setIsbn("12345");
                wiedzmin.setDescription("Amazing book.");
                wiedzmin.setCoverImage("coverImage");
                wiedzmin.setPrice(BigDecimal.valueOf(30));
                wiedzmin.setAuthor("Andrzej Sapkowski");

                bookService.save(wiedzmin);

                System.out.println(bookService.findAll());
            }
        };
    }
}
