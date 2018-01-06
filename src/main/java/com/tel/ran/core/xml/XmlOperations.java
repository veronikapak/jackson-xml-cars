package com.tel.ran.core.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tel.ran.annotations.InjectionService;
import com.tel.ran.annotations.InjectionServiceType;
import com.tel.ran.core.Operations;
import com.tel.ran.pojo.Car;

@InjectionService(type = InjectionServiceType.XML)
public class XmlOperations implements Operations{

    public void print(Car car) throws Exception {
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(car);
        System.out.println(xml);

        /*Car car1 = mapper.readValue(xml, Car.class);
        System.out.println(car1.getBrand());*/
    }
}
