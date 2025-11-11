/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author xuhoa
 */
public class SetDTO {
    private int setID;
    private String setName;
    private String realeseDate;
    private int totalCard;

    public SetDTO(int setID, String setName, String realeseDate, int totalCard) {
        this.setID = setID;
        this.setName = setName;
        this.realeseDate = realeseDate;
        this.totalCard = totalCard;
    }

    public SetDTO() {
    }

    public int getSetID() {
        return setID;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getRealeseDate() {
        return realeseDate;
    }

    public void setRealeseDate(String realeseDate) {
        this.realeseDate = realeseDate;
    }

    public int getTotalCard() {
        return totalCard;
    }

    public void setTotalCard(int totalCard) {
        this.totalCard = totalCard;
    }

    @Override
    public String toString() {
        return "SetDTO{" + "setID=" + setID + ", setName=" + setName + ", realeseDate=" + realeseDate + ", totalCard=" + totalCard + '}';
    }
    
    
}
