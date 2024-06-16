package com.belhard.bookstore.data.conversion;

import com.belhard.bookstore.data.entity.CoverType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CoverTypeConverter implements AttributeConverter <CoverType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CoverType coverType) {
        return  coverType.ordinal();
    }

    @Override
    public CoverType convertToEntityAttribute(Integer integer) {
        return CoverType.values()[integer];
    }
}
