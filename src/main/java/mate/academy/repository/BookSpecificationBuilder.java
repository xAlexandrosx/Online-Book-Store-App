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

        if (bookParams.authors() != null && bookParams.authors().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("authors")
                    .getSpecification(bookParams.authors()));
        }

        if (bookParams.titles() != null && bookParams.titles().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("titles")
                    .getSpecification(bookParams.titles()));
        }

        if (bookParams.isbns() != null && bookParams.isbns().length > 0) {
            spec = spec.and(manager.getSpecificationProvider("titles")
                    .getSpecification(bookParams.isbns()));
        }

        return spec;
    }
}
