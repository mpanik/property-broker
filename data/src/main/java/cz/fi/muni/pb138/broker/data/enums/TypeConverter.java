package cz.fi.muni.pb138.broker.data.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Milan
 */
@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        return attribute.getText();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        return Type.fromString(dbData);
    }
}
