package com.rzlearn.gateway.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rzlearn.base.support.MessageCode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * <p>ClassName:CustomOauthExceptionSerializer</p>
 * <p>Description:</p>
 *
 * @author Zhangwb
 * @date 2018-12-03 11:29
 **/
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public static final String BAD_CREDENTIALS = "Bad credentials";

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("resultCode", "500");
        if (StringUtils.equals(e.getMessage(),BAD_CREDENTIALS)) {
            jsonGenerator.writeStringField("resultMessage", MessageCode.USER_IS_NULL.msg());
        } else {
            jsonGenerator.writeStringField("resultMessage", e.getMessage());
        }
        jsonGenerator.writeEndObject();
    }
}
