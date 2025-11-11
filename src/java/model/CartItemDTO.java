package model;

// (Tên file: CartItemDTO.java)
public class CartItemDTO {
    
    // Từ tblCards
    private String cardCode;
    private String cardName;
    private String imageUrl;
    private double price; // Giá của 1 sản phẩm

    // Từ tblCarts
    private int quantity;
    private double totalPrice; // Tổng tiền (quantity * price)
    
    // (Thêm constructor, getter, setter cho tất cả các trường trên)
    
    public CartItemDTO() {
    }

    public CartItemDTO(String cardCode, String cardName, String imageUrl, double price, int quantity, double totalPrice) {
        this.cardCode = cardCode;
        this.cardName = cardName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // --- Getter và Setter ---
    
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}