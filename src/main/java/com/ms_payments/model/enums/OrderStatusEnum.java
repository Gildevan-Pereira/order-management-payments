package com.ms_payments.model.enums;

public enum OrderStatusEnum {

    CREATED,
    PROCESSED,
    REJECTED;

    public static OrderStatusEnum fromName(String name) {
        for (OrderStatusEnum statusEnum : OrderStatusEnum.values()){
            if (statusEnum.name().equals(name)) {
                return statusEnum;
            }
        }
//        throw new BusinessException(MessageEnum.INVALID_STATUS, List.of(OrderStatusEnum.values()).toString(), HttpStatus.BAD_REQUEST);
    return null;
    }

}