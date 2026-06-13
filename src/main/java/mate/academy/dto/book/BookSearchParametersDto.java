package mate.academy.dto.book;

public record BookSearchParametersDto(String[] author, String[] title, String[] isbn) {
}
