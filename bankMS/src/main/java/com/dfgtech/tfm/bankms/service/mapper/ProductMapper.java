package com.dfgtech.tfm.bankms.service.mapper;

import com.dfgtech.tfm.bankms.domain.*;
import com.dfgtech.tfm.bankms.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {BankingEntityMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "bankingEntity.id", target = "bankingEntityId")
    ProductDTO toDto(Product product);

    @Mapping(source = "bankingEntityId", target = "bankingEntity")
    @Mapping(target = "fees", ignore = true)
    @Mapping(target = "removeFees", ignore = true)
    @Mapping(target = "currencies", ignore = true)
    @Mapping(target = "removeCurrencies", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
