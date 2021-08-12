package eu.acme.demo.web.dto;

import eu.acme.demo.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.*;

public class OrderDto extends OrderLiteDto {

    private List<OrderItemDto> orderItems;

    public OrderDto() {
    }

    public OrderDto(UUID id, OrderStatus status, String description, String clientReferenceCode, BigDecimal totalAmount, int itemCount, List<OrderItemDto> orderItems) {
        super(id, status, description, clientReferenceCode, totalAmount, itemCount);
        this.orderItems = orderItems;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null )
            return false;
        if (this == obj)
            return true;
        if (obj.getClass() != this.getClass())
            return false;
        if (!(this.getClientReferenceCode().equals(((OrderDto) obj).getClientReferenceCode())))
            return false;
        if ((((OrderDto) obj).getDescription() == null) || this.getDescription() == null)
            return false;
        if(!(this.getDescription().equals(((OrderDto) obj).getDescription())))
            return false;
        if ((this.getTotalAmount().compareTo(((OrderDto) obj).getTotalAmount())) != 0)
            return false;
        if (!(this.getId().equals(((OrderDto) obj).getId())))
            return false;
        if (!(this.getItemCount() == ((OrderDto) obj).getItemCount()))
            return false;
        if (this.getStatus() == ((OrderDto) obj).getStatus())
            return false;
        if ((this.getOrderItems() == null) && (((OrderDto) obj).getOrderItems() == null))
            return false;
        if((this.getOrderItems() == null && ((OrderDto) obj).getOrderItems() != null)
                || this.getOrderItems() != null && ((OrderDto) obj).getOrderItems() == null
                || this.getOrderItems().size() != ((OrderDto) obj).getOrderItems().size()) {

            return false;
        }
        List<OrderItemDto> tempList1 = new ArrayList<>(this.getOrderItems());
        ArrayList<OrderItemDto> tempList2 = new ArrayList<>(((OrderDto) obj).getOrderItems());

        Comparator<OrderItemDto> orderItemDtoComparator = new Comparator<OrderItemDto>() {

            @Override
            public int compare(OrderItemDto item1, OrderItemDto item2) {
                return item1.getItemId().compareTo(item2.getItemId());
            }
        };

        Collections.sort(tempList1, orderItemDtoComparator);
        Collections.sort(tempList2, orderItemDtoComparator);

        if(!(tempList1.equals(tempList2)))
            return false;

        return true;
    }
}
