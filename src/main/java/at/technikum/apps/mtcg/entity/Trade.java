package at.technikum.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade {

    @JsonProperty("Id")
    private String trade_id;

    private String dealerUserId;

    private String customerUserId;

    @JsonProperty("CardToTrade")
    private String dealerCardId;

    private String customerCardId;

    private String status;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("MinimumDamage")
    private int minimumDamage;

    public Trade(){

    }

    public Trade(String trade_id, String dealerUserId, String customerUserId, String dealerCardId, String customerCardId, String status, String type, int minimumDamage) {
        this.trade_id = trade_id;
        this.dealerUserId = dealerUserId;
        this.customerUserId = customerUserId;
        this.dealerCardId = dealerCardId;
        this.customerCardId = customerCardId;
        this.status = status;
        this.type = type;
        this.minimumDamage = minimumDamage;
    }

    public int getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(int minimumDamage) {
        this.minimumDamage = minimumDamage;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    public String getDealerCardId() {
        return dealerCardId;
    }

    public void setDealerCardId(String dealerCardId) {
        this.dealerCardId = dealerCardId;
    }

    public String getCustomerCardId() {
        return customerCardId;
    }

    public void setCustomerCardId(String customerCardId) {
        this.customerCardId = customerCardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
