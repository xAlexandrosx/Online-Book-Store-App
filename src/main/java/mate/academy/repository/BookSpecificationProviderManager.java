package mate.academy.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.model.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {

        return bookSpecificationProviders
                .stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("message"));
    }
}
