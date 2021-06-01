package com.example.leasing.service.mapper;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface MapperService {

    <T> T toObject(String string, Class<T> type);

    String toString(Object object);

}
