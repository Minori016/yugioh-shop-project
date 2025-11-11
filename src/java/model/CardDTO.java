/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author xuhoa
 */
public class CardDTO {
    private String cardID;
    private String cardName;
    private String rarity;
    private double price;
    private String image;
    private int setID; // <-- THÊM DÒNG NÀY

    public CardDTO() {
    }

    public CardDTO(String cardID, String cardName, String rarity, double price, String image, int setID) {
        this.cardID = cardID;
        this.cardName = cardName;
        this.rarity = rarity;
        this.price = price;
        this.image = image;
        this.setID = setID;
    }

   

    // ========= THÊM GETTER/SETTER CHO SETID =========
    public int getSetID() {
        return setID;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }
    // ===============================================

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        // Bạn có thể thêm setID vào đây nếu muốn
        return "CardDTO{" + "cardID=" + cardID + ", cardName=" + cardName + ", rarity=" + rarity + ", price=" + price + ", image=" + image + '}';
    }
    
}
