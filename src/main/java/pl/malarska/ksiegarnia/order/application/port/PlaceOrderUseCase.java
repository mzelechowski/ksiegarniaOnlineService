package pl.malarska.ksiegarnia.order.application.port;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.malarska.ksiegarnia.order.domain.OrderItem;
import pl.malarska.ksiegarnia.order.domain.OrderStatus;
import pl.malarska.ksiegarnia.order.domain.Recipient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public interface PlaceOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    PlaceOrderResponse updateStatusOrder(UpdateStatusOrderCommand command);

    void deleteOrderById(Long id);


    @Value
    class UpdateStatusOrderCommand {
        private Long id;
        private OrderStatus status;
        private List<OrderItem> items;
        private Recipient recipient;
        private LocalDateTime createdAt;
    }


    @Builder
    @Value
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    class PlaceOrderResponse {
        boolean success;
        Long orderId;
        List<String> errors;

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, orderId, emptyList());
        }

        public static PlaceOrderResponse failure(String... errors) {
            return new PlaceOrderResponse(false, null, Arrays.asList(errors));
        }
    }
}
