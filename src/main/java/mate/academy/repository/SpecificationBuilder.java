package mate.academy.repository;

import mate.academy.dto.BookSearchParams;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build(BookSearchParams params);
}
