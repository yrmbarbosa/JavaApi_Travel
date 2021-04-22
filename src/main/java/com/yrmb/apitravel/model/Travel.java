package com.yrmb.apitravel.model;

import com.yrmb.apitravel.enumeration.TravelTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    private long id;
    private String orderNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal amount;
    private TravelTypeEnum type;

    public Travel(TravelTypeEnum type){
        this.type = type;
    }

}
