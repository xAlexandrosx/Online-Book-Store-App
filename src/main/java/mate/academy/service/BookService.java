package mate.academy.service;

import mate.academy.dto.book.BookDto;
import mate.academy.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.dto.book.BookSearchParametersDto;
import mate.academy.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    BookDto getById(Long id);

    Page<BookDto> findAll(Pageable pageable);

    BookDto updateById(Long id, CreateBookRequestDto updateRequestDto);

    void deleteById(Long id);

    Page<BookDto> search(BookSearchParametersDto bookParams, Pageable pageable);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}
