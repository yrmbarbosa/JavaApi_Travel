package com.yrmb.apitravel.factory;

import com.yrmb.apitravel.enumeration.TravelTypeEnum;
import com.yrmb.apitravel.model.Travel;

public class TravelFactory {

    public Travel createTravel (String type){

        if (type.equals(TravelTypeEnum.ONE_WAY.getValue())) {

            return new Travel(TravelTypeEnum.ONE_WAY);

        } else if (type.equals(TravelTypeEnum.MULTI_CITY.getValue())) {

            return new Travel(TravelTypeEnum.MULTI_CITY);

        } else {

            return new Travel(TravelTypeEnum.RETURN);
        }
    }

}
