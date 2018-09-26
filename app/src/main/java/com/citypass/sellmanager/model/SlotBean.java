package com.citypass.sellmanager.model;

/**
 * Created by 赵涛 on 2018/8/23.
 */
public class SlotBean {

    /**
     * SlotId : 10361
     * CardKindId : 1
     * CardImg : ./sale/uploads/1527149433123.jpg
     * SlotBalance : 20
     * isSell : true
     */


    private int SlotId;
    private int LocalId;
    private int CardKindId;
    private String CardImg;
    private int SlotBalance;
    private boolean isSell;

    public int getSlotId() {
        return SlotId;
    }

    public void setSlotId(int SlotId) {
        this.SlotId = SlotId;
    }

    public int getCardKindId() {
        return CardKindId;
    }

    public void setCardKindId(int CardKindId) {
        this.CardKindId = CardKindId;
    }

    public String getCardImg() {
        return CardImg;
    }

    public void setCardImg(String CardImg) {
        this.CardImg = CardImg;
    }

    public int getSlotBalance() {
        return SlotBalance;
    }

    public void setSlotBalance(int SlotBalance) {
        this.SlotBalance = SlotBalance;
    }

    public int getLocalId() {
        if (SlotId > 10000) {
            return (SlotId % 100);
        } else {
            return (SlotId % 10);
        }
    }

    public void setLocalId(int localId) {
        LocalId = localId;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }
}
