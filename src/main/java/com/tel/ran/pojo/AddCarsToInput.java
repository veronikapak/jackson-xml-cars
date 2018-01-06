package com.tel.ran.pojo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tel.ran.annotations.InjectionService;
import com.tel.ran.annotations.InjectionServiceType;
import com.tel.ran.core.Operations;
import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class AddCarsToInput {

    private static final int N_CARS = 5;
    private static final String FILE_INPUT = "C:\\java\\json-xml-parser\\input\\file";
    private static String[] brands = {"Toyota", "Honda", "Mazda", "BMW", "Jeep", "Infiniti"};
    private static String[] models = {"Corolla", "Camry", "Civic", "Accord", "MX-5", "3",
                                    "M2", "M3", "Cherokee", "Compass", "Q30", "Q50"};
    static Random rnd = new Random();
    private static final InjectionServiceType[] types = {InjectionServiceType.JSON, InjectionServiceType.XML};
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static XmlMapper xmlMapper = new XmlMapper();

    /*public static void main( String[] args ) throws Exception {
        defineFormatToInput();
    }*/

    private static Car genetateCar(){
        String brand;
        String model;
        int year;
        int km;

            brand =rndBrand();
            model = rndModel();
            year = 1975 + rnd.nextInt(43);
            km = (1+rnd.nextInt(5))*10000;

            Car car = new Car(brand, model, year,km);

        return car;

    }

    private static String rndModel() {
        String model = "";
        for (int i = 0; i < models.length; i++) {
            int index = rnd.nextInt(models.length);
            model = models[index];
        }
        return model;
    }

    private static String rndBrand(){
        String brand = "";
        for (int i = 0; i < brands.length; i++) {
            int index = rnd.nextInt(brands.length);
            brand = brands[index];
        }
        return brand;
    }

    public static void defineFormatToInput() throws Exception {
        Reflections reflections = new Reflections("com.tel.ran.core");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(InjectionService.class);
        for (int i = 0; i<N_CARS; i++) {
            int index = (rnd.nextDouble()>=0.5?1:0);
            Class<?> clazz = (Class<?>) typesAnnotatedWith.toArray()[index];
            Operations operations = (Operations) clazz.newInstance();
            Car car = genetateCar();
            if(index==0) {
                FileUtils.writeStringToFile(new File(FILE_INPUT+i+".txt"),objectMapper.writeValueAsString(car));
            }else{
                FileUtils.writeStringToFile(new File(FILE_INPUT+i+".xml"), xmlMapper.writeValueAsString(car));
            }
        }
    }

}
