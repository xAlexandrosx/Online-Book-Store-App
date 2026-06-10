package mate.academy.service;

import java.util.List;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParams;
import mate.academy.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    BookDto getById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto updateById(Long id, CreateBookRequestDto updateRequestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParams bookParams);
}
