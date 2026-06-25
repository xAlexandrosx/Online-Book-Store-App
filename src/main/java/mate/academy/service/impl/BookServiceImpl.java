package mate.academy.service.impl;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.book.BookDto;
import mate.academy.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.dto.book.BookSearchParametersDto;
import mate.academy.dto.book.CreateBookRequestDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.BookMapper;
import mate.academy.model.Book;
import mate.academy.model.Category;
import mate.academy.repository.BookRepository;
import mate.academy.repository.CategoryRepository;
import mate.academy.repository.SpecificationBuilder;
import mate.academy.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationBuilder<Book> specBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        Set<Category> categories =
                getValidatedCategories(createBookRequestDto.getCategoryIds());

        Book book = bookMapper.toEntity(createBookRequestDto);
        book.setCategories(categories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id " + id + " not found"));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    @Transactional
    public BookDto updateById(
            Long id, CreateBookRequestDto updateRequestDto) {
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't update book. Book by id: " + id + " not found.")
        );

        Set<Category> categories =
                getValidatedCategories(updateRequestDto.getCategoryIds());

        bookMapper.updateBookFromDto(updateRequestDto, existingBook);
        existingBook.setCategories(categories);

        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> search(
            BookSearchParametersDto bookParams, Pageable pageable) {
        Specification<Book> bookSpecification = specBuilder.build(bookParams);
        return bookRepository.findAll(bookSpecification, pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            Long id, Pageable pageable) {
        return bookRepository
                .findAllByCategoryId(id, pageable)
                .map(bookMapper::toDtoWithoutCategories);
    }

    private Set<Category> getValidatedCategories(Set<Long> requestedIds) {
        if (requestedIds == null || requestedIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Category> categories =
                categoryRepository.findAllByIdIn(requestedIds);
        if (categories.size() != requestedIds.size()) {
            Set<Long> foundIds = categories.stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = requestedIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new EntityNotFoundException(
                    "The following category IDs were not found: " + missingIds);
        }
        return categories;
    }
}
