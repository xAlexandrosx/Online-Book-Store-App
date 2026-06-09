package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.BookDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    Book toEntity(CreateBookRequestDto createBookRequestDto);

    BookDto toDto(Book book);
}
