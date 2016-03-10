package io.sytac.resumator.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.sytac.resumator.utils.DateUtils;

import java.io.IOException;
import java.util.Date;

/**
 * DeSerializes {@link String} to Date with template from {@link DateUtils}
 *
 * @author Selman Tayyar
 * @since 0.1
 */
public class StringToDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonparser,
            
	DeserializationContext deserializationcontext) throws IOException {
        String date = jsonparser.getText();       
            
        try {
		return DateUtils.convert(date);
		
	    } catch (Exception e) {
		
		return DateUtils.convertToWrongFormat("11112015");//to help us with further format validation
	    }
      
    }
}
