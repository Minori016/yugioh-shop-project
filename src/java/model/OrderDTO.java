package model;

import java.time.LocalDateTime;

public class OrderDTO {

    private int orderID;
    private String userID;
    private LocalDateTime orderDate;
    private double totalPrice;
    private String status;
    private String shippingAddress;
    private String shippingPhone;

    // Thuộc tính thêm để join và hiển thị
    private String fullName;

    public OrderDTO() {
    }

    public OrderDTO(int orderID, String userID, LocalDateTime orderDate, double totalPrice, String status, String shippingAddress, String shippingPhone, String fullName) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.shippingPhone = shippingPhone;
        this.fullName = fullName;
    }

    // --- Thêm đầy đủ Getter và Setter cho tất cả các trường ---
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
