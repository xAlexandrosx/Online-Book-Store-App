package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.book.BookDto;
import mate.academy.dto.book.BookSearchParametersDto;
import mate.academy.dto.book.CreateBookRequestDto;
import mate.academy.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Store Management", description = "Endpoints for managing books")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get paginated books",
            description = "Retrieves a paginated list of books. "
                    + "Supports page, size, and sort parameters.")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID",
            description = "Retrieves specific book details using its unique database ID.")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book",
            description = "Validates and saves a new book into the inventory database.")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book",
            description = "Updates full details of an existing book matched by its unique ID.")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(
            @PathVariable Long id,
            @RequestBody @Valid CreateBookRequestDto updateRequestDto) {
        return bookService.updateById(id, updateRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book",
            description = "Permanently removes a book from the inventory database by its ID.")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books dynamically",
            description = "Dynamically searches and filters books using optional "
                    + "specifications like title, author, or price range. Supports pagination.")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookDto> search(BookSearchParametersDto params, Pageable pageable) {
        return bookService.search(params, pageable);
    }
}
