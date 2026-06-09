package mate.academy.service;

import java.util.List;
import mate.academy.dto.BookDto;
import mate.academy.dto.CreateBookRequestDto;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    BookDto getById(Long id);

    List<BookDto> findAll();

    BookDto updateById(Long id, CreateBookRequestDto updateRequestDto);

    void deleteById(Long id);
}
