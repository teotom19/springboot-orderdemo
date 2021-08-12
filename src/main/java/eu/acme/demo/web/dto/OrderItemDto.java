package eu.acme.demo.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemDto {
    private UUID itemId;
    private int units;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public OrderItemDto(UUID itemId, int units, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.itemId = itemId;
        this.units = units;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj.getClass() != this.getClass())
            return false;
        OrderItemDto tempItem = (OrderItemDto) obj;
        if (this.getTotalPrice().compareTo(tempItem.getTotalPrice()) != 0)
            return false;
        if (this.getUnits() != tempItem.getUnits())
            return false;
        if (this.getUnitPrice().compareTo(tempItem.getUnitPrice()) != 0)
            return false;

        return true;
    }
}
