package com.pepcap.ecommerce.application.extended.orders;

import org.mapstruct.Mapper;
import com.pepcap.ecommerce.application.core.orders.IOrdersMapper;

@Mapper(componentModel = "spring")
public interface IOrdersMapperExtended extends IOrdersMapper {

}

