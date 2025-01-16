package com.pepcap.adminpanel.application.core.categories;

import org.mapstruct.Mapper;
import com.pepcap.adminpanel.application.core.categories.dto.*;
import com.pepcap.adminpanel.domain.core.categories.Categories;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ICategoriesMapper {
   Categories createCategoriesInputToCategories(CreateCategoriesInput categoriesDto);
   CreateCategoriesOutput categoriesToCreateCategoriesOutput(Categories entity);
   
    Categories updateCategoriesInputToCategories(UpdateCategoriesInput categoriesDto);
    
   	UpdateCategoriesOutput categoriesToUpdateCategoriesOutput(Categories entity);
   	FindCategoriesByIdOutput categoriesToFindCategoriesByIdOutput(Categories entity);


}

