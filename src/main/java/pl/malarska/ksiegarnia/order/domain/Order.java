package pl.malarska.ksiegarnia.order.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(value=EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name ="order_id")
    private List<OrderItem> items;
    @ManyToOne(cascade = CascadeType.ALL)
    private Recipient recipient;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    public BigDecimal totalPrice() {
//        return items.stream()
//                .map(item -> item.getBookId().getPrice()
//                        .multiply(new BigDecimal(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
}
