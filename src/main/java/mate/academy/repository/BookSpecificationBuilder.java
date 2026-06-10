package mate.academy.repository;

import lombok.RequiredArgsConstructor;
import mate.academy.dto.BookSearchParams;
import mate.academy.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> manager;

    @Override
    public Specification<Book> build(BookSearchParams bookParams) {
        Specification<Book> spec =
                Specification.where((root, query, criteriaBuilder)
                        -> criteriaBuilder.conjunction());

        if (bookParams.author() != null && bookParams.author().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("author")
                    .getSpecification(bookParams.author()));
        }

        if (bookParams.title() != null && bookParams.title().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("title")
                    .getSpecification(bookParams.title()));
        }

        if (bookParams.isbn() != null && bookParams.isbn().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("isbn")
                    .getSpecification(bookParams.isbn()));
        }

        return spec;
    }
}
