package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.sytac.resumator.utils.DateUtils;

import java.io.IOException;
import java.util.Date;

/**
 * Serializes {@link Date} to String with template from {@link DateUtils}
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public class DateToStringSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateUtils.convert(date));
    }
}
