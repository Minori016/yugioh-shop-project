package model;

import java.time.LocalDateTime;

public class CartDTO {

    private String userId;
    private String cardId;
    private String cardName;
    private String image;
    private int quantity;
    private double price;
    private LocalDateTime addTime;

    public CartDTO() {
    }

    public CartDTO(String userId, String cardId, String cardName, String image, int quantity, double price, LocalDateTime addTime) {
        this.userId = userId;
        this.cardId = cardId;
        this.cardName = cardName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.addTime = addTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    
}
