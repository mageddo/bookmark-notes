package com.mageddo.bookmarks.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mageddo.common.time.DateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public interface TruncatedLocalDateTimeConverter {

	DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
		.parseCaseInsensitive()
		.appendPattern("yyyy-MM-dd")
		.toFormatter();

	class Deserializer extends JsonDeserializer<LocalDateTime> {

		@Override
		public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			// 2018-01-18T17:43:34.101
			if(p.getCurrentToken() == JsonToken.VALUE_NULL){
				return null;
			}
			return LocalDateTime.parse(p.getValueAsString(), FORMATTER);
		}
	}

	class Serializer extends JsonSerializer<LocalDateTime> {
		@Override
		public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			if(value == null){
				gen.writeNull();
				return;
			}
			gen.writeString(DateUtils.format(value, FORMATTER));
		}
	}
}
