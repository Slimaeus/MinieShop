package com.master.minieshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MomoRequest {
    @JsonProperty("partnerCode")
    private String partnerCode;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("items")
    private List<MomoItem> items;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("orderInfo")
    private String orderInfo;

    @JsonProperty("redirectUrl")
    private String redirectUrl;

    @JsonProperty("ipnUrl")
    private String ipnUrl;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("extraData")
    private String extraData;

    @JsonProperty("requestType")
    private String requestType;

    @JsonProperty("signature")
    private String signature;
}
