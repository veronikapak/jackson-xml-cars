package com.tel.ran;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.io.Files;
import com.tel.ran.annotations.InjectionService;
import com.tel.ran.annotations.InjectionServiceType;
import com.tel.ran.core.Operations;
import com.tel.ran.pojo.AddCarsToInput;
import com.tel.ran.pojo.Car;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class App
{
    private static final InjectionServiceType type = InjectionServiceType.JSON;
    private static final String PATH = "C:\\java\\json-xml-parser\\input";
    static volatile List<Car> cars = Collections.synchronizedList(new ArrayList<Car>());
    private static final String PATH_STORAGE = "C:\\java\\json-xml-parser\\storageCars.txt";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main( String[] args ) throws Exception {
        final File fileStorage = new File(PATH_STORAGE);
        File directory = new File(PATH);
        while (true) {
           new Thread(){
               public void run(){
                   if (fileStorage.exists()) {
                       try {
                            cars = deserializedFromFile(fileStorage);
                      } catch (IOException e) {  }
                  }
              }
          }.start();

            File[] listFiles = directory.listFiles();

            if (listFiles.length == 0) {
                continue;
            }

            readInputFiles(listFiles);
            serializedToFile(cars);

            System.out.println("----------------");
            findCarsByBrand("Toyota");
            System.out.println("----------------");
            findCarsByModel("Camry");
            System.out.println("----------------");
            findCarsByLessKilometers(200000);
            System.out.println("----------------");
            findCarsByNewerYear(2003);
            System.out.println("----------------");

            AddCarsToInput.defineFormatToInput();
            Thread.sleep(5000);
        }

    }

    private static void readInputFiles(File[] fileList) throws IOException {
        for (File f : fileList) {
            String s = FileUtils.readFileToString(f);
            System.out.println(s);

            XmlMapper xmlMapper = new XmlMapper();
            Car car = null;
            String extention = FilenameUtils.getExtension(f.getName());

            if (extention.equalsIgnoreCase(String.valueOf(InjectionServiceType.XML))) {
                car = xmlMapper.readValue(f, Car.class);
            } else {
                car = objectMapper.readValue(f, Car.class);
            }

            cars.add(car);
            f.delete();
            System.out.println(cars.toString());
        }
    }

    private static void findCarsByNewerYear(int year) throws IOException {
        for (Car car:cars) {
            if(car.getYear()<=year){
                System.out.println(car);
            }
        }
    }

    private static void findCarsByLessKilometers(int km) throws IOException {
        for (Car car:cars) {
            if(car.getKillometerRun()<=km){
                System.out.println(car);
            }
        }
    }

    private static void findCarsByModel(String model) throws IOException {
        for (Car car:cars) {
            if(car.getModel().equalsIgnoreCase(model)){
                System.out.println(car);
            }
        }
    }

    private static void findCarsByBrand(String brand) throws IOException {
        for (Car car:cars) {
            if(car.getBrand().equalsIgnoreCase(brand)){
                System.out.println(car);
            }
        }
    }

    private static void serializedToFile(List<Car> cars) throws IOException {
        String json = objectMapper.writeValueAsString(cars);
        FileUtils.writeStringToFile(new File(PATH_STORAGE), json);
    }

    private static List<Car> deserializedFromFile(File file) throws IOException {
        return objectMapper.readValue(file, new TypeReference<List<Car>>() {});
    }

    private static void defineFormat() throws Exception {
        Reflections reflections = new Reflections("com.tel.ran.core");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(InjectionService.class);
        for (Class<?> clazz:typesAnnotatedWith) {
            if (clazz.getAnnotation(InjectionService.class).type() == type) {
                Operations operations = (Operations) clazz.newInstance();
                Car car = new Car(
                        "Toyota",
                        "Corolla",
                        2006,
                        200000);
                operations.print(car);
            }
        }
    }
}
