package com.insdata.defaultmethod;

import java.util.ArrayList;
import java.util.List;

public class MultipleInheritance {

    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        System.out.println(service.customerData());
        System.out.println(service.data());
    }
}

//kvoli default na interface mozeme v jave robit kvazi multiple inheritance
class ServiceImpl implements CustomerService, Service{

    @Override
    public List<Customer> customerData() {
        ArrayList<Customer> retCustomers = new ArrayList<>(CustomerService.super.customerData());
        retCustomers.add(new Customer("Juraj", "Maly", 32));
        return retCustomers;
    }
}

interface CustomerService {
    /*
    keby bola metoda default customerData nazvana data,
    tak by CustomServiceImpl zahlasil
    clash so Service interface, v ktorom
    uz takato default metoda je implementovana*/
    default List<Customer> customerData(){
        List<Customer> retList = new ArrayList<>();
        retList.add(new Customer("Peter", "Pokorny", 22));
        retList.add(new Customer("Jan", "Zarity", 44));
        retList.add(new Customer("Teodor", "Pribosek", 34));
        return retList;
    }
}

interface Service{
    default List<Object> data(){
        List<Object> retList = new ArrayList<>();
        retList.add("Peter Pokorny 33");
        retList.add("Jan Zarity 44");
        retList.add("Teodor Pribosek 34");
        return retList;
    }
}

class Customer{
    String name;
    String surname;
    Integer age;

    public Customer(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}