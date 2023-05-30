package com.library.book.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class JSONUtils {

    public static <T> List<T> convertFromJsonToList(String json, TypeReference<List<T>> var) throws IOException {
        return Jackson.getObjectMapper().readValue(json, var);
    }

    public static <T> T covertFromJsonToObject(String json, Class<T> var) throws IOException {
        return Jackson.getObjectMapper().readValue(json, var);//Convert Json into object of Specific Type
    }

}
