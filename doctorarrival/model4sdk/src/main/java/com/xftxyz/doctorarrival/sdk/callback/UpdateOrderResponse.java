package com.xftxyz.doctorarrival.sdk.callback;

import lombok.Data;

@Data
public class UpdateOrderResponse {

    private Boolean success;

    private Integer availableNumber;

    private String message;
}
