package model;

public class AddressDTO {

    private int addressID;          // Tương ứng: address_id (int)
    private String userID;          // Tương ứng: user_id (varchar(50))
    private String receiverName;    // TƯƠNG ỨNG: receiver_name (nvarchar(100))
    private String phoneNumber;     // Tương ứng: phone_number (varchar(20))
    private String detailedAddress; // Tương ứng: detailed_address (nvarchar(255))
  

    public AddressDTO() {
    }

    // Constructor (đã cập nhật)
    public AddressDTO(int addressID, String userID, String receiverName, String phoneNumber, String detailedAddress) {
        this.addressID = addressID;
        this.userID = userID;
        this.receiverName = receiverName; // Sửa ở đây
        this.phoneNumber = phoneNumber;
        this.detailedAddress = detailedAddress;

    }

    // --- Getters and Setters (đã cập nhật) ---
    
    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // SỬA GETTER/SETTER NÀY
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    // HẾT SỬA

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }


}