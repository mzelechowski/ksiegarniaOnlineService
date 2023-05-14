package pl.malarska.ksiegarnia.order.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase;
import pl.malarska.ksiegarnia.order.application.port.QueryOrderUseCase;
import pl.malarska.ksiegarnia.order.domain.Order;
import pl.malarska.ksiegarnia.order.domain.OrderStatus;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase.*;

@RequestMapping("/orders")
@RestController
@AllArgsConstructor
public class OrdersController {

    private QueryOrderUseCase catalog;
    private PlaceOrderUseCase placeOrderUse;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll() {
        return catalog.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (id.equals(42L)) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "I am a teapot. Sorry");
        }
        return catalog
                .findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Void> placeOrder(@RequestBody PlaceOrderCommand command) {
        PlaceOrderResponse response = placeOrderUse.placeOrder(command);
        return ResponseEntity.created(createdOrderUri(response)).build();
    }

    private static URI createdOrderUri(PlaceOrderResponse response) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + response.getOrderId().toString()).build().toUri();
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @PathVariable OrderStatus status)  {
         Order order =catalog.findById(id).get();
        UpdateStatusOrderCommand update = new UpdateStatusOrderCommand(order.getId(), status, order.getItems(), order.getRecipient(), order.getCreatedAt());
        PlaceOrderResponse response = placeOrderUse.updateStatusOrder(update);
        return ResponseEntity.created(createdOrderUri(response)).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        placeOrderUse.deleteOrderById(id);
    }
}
