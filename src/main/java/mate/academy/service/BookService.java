package mate.academy.service;

import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    BookDto getById(Long id);

    Page<BookDto> findAll(Pageable pageable);

    BookDto updateById(Long id, CreateBookRequestDto updateRequestDto);

    void deleteById(Long id);

    Page<BookDto> search(BookSearchParametersDto bookParams, Pageable pageable);
}
