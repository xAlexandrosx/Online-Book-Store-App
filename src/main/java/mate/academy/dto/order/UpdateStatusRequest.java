package mate.academy.dto.order;

import mate.academy.model.Order;

public record UpdateStatusRequest(Order.Status status) {}
