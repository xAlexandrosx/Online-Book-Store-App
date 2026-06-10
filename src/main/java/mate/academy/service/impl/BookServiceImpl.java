package mate.academy.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParams;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.BookMapper;
import mate.academy.model.Book;
import mate.academy.repository.BookRepository;
import mate.academy.repository.SpecificationBuilder;
import mate.academy.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder<Book> specBuilder;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {

        Book saved = bookRepository.save(bookMapper.toEntity(createBookRequestDto));

        return bookMapper.toDto(saved);
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + id + " not found")
        );

        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository
                .findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto updateRequestDto) {
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't update book. Book by id: " + id + " not found.")
        );

        bookMapper.updateBookFromDto(updateRequestDto, existingBook);

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParams bookParams) {
        Specification<Book> bookSpecification = specBuilder.build(bookParams);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
