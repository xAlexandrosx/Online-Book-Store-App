package mate.academy.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.model.Order;

public record UpdateStatusRequest(@NotNull Order.Status status) {}
