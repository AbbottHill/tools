package com.cd.tools.file.xml;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

/**
 * @author ChuD
 * @date 2019-08-20 15:59
 * Java Architecturefor XML Binding
 */
public class JaxbTest {

    public static void beanToXml() throws JAXBException {
        House sixthAvenue = new House("001", "Sixth Avenue");
        Student xiaoMing = new Student("XiaoMing", sixthAvenue);
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(xiaoMing, System.out);
        System.out.println();
    }


    public static void xmlToBean(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Student student = (Student) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println("student: " + JSON.toJSONString(student));
    }


    @Test
    public void run() throws JAXBException {
        beanToXml();
        xmlToBean("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><house><num>Sixth Avenue</num><street>001</street></house><name>XiaoMing</name></student>");
    }
}

@XmlRootElement
class Student {
    String name;

    House house;

    public Student() {
    }

    public Student(String name, House house) {
        this.name = name;
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}

class House{

    String street;

    String num;

    public House() {
    }

    public House(String street, String num) {
        this.street = street;
        this.num = num;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}


