package com.pepcap.inventorymanagement.application.core.suppliers;

import org.mapstruct.Mapper;
import com.pepcap.inventorymanagement.application.core.suppliers.dto.*;
import com.pepcap.inventorymanagement.domain.core.suppliers.Suppliers;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ISuppliersMapper {
   Suppliers createSuppliersInputToSuppliers(CreateSuppliersInput suppliersDto);
   CreateSuppliersOutput suppliersToCreateSuppliersOutput(Suppliers entity);
   
    Suppliers updateSuppliersInputToSuppliers(UpdateSuppliersInput suppliersDto);
    
   	UpdateSuppliersOutput suppliersToUpdateSuppliersOutput(Suppliers entity);
   	FindSuppliersByIdOutput suppliersToFindSuppliersByIdOutput(Suppliers entity);


}

