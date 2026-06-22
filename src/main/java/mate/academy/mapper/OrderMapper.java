package mate.academy.mapper;

import mate.academy.config.MapperConfig;
import mate.academy.dto.order.OrderDto;
import mate.academy.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);
}
