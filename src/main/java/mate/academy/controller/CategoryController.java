package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.dto.category.CategoryDto;
import mate.academy.dto.category.CreateCategoryRequestDto;
import mate.academy.service.BookService;
import mate.academy.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Category Management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create new category")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto createCategory(
            @RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Page<CategoryDto> getAll(
            Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public CategoryDto getCategoryById(
            @PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by ID")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category by ID")
    public void delete(
            @PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get books by category ID")
    public Page<BookDtoWithoutCategoryIds> getBooksByCategory(
            @PathVariable Long id,
            Pageable pageable) {
        return bookService.getBooksByCategoryId(id, pageable);
    }
}
