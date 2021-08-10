package eu.acme.demo.web.dto;

public class OrderRequest {

    String clientReferenceCode;
    OrderDto order;

    public OrderRequest(String clientReferenceCode, OrderDto order) {
        this.clientReferenceCode = clientReferenceCode;
        this.order = order;
    }

    public String getClientReferenceCode() {
        return clientReferenceCode;
    }

    public void setClientReferenceCode(String clientReferenceCode) {
        this.clientReferenceCode = clientReferenceCode;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }
}
