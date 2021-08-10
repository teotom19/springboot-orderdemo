package eu.acme.demo.domain;

import eu.acme.demo.domain.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order extends AuditableEntity {

    public Order(){}

    public Order(String clientReferenceCode, String description, BigDecimal itemTotalAmount, int itemCount, OrderStatus status) {
        this.clientReferenceCode = clientReferenceCode;
        this.description = description;
        this.itemTotalAmount = itemTotalAmount;
        this.itemCount = itemCount;
        this.status = status;
    }

    @Column(name = "ref_code", length = 30, nullable = false)
    private String clientReferenceCode;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal itemTotalAmount;

    @Column(name = "item_count", nullable = false)
    private int itemCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    public String getClientReferenceCode() {
        return clientReferenceCode;
    }

    public void setClientReferenceCode(String orderCode) {
        this.clientReferenceCode = orderCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getItemTotalAmount() {
        return itemTotalAmount;
    }

    public void setItemTotalAmount(BigDecimal itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
