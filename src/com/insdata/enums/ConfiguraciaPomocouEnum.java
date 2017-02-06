package com.insdata.enums;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by key on 16.1.2017.
 */
public enum ConfiguraciaPomocouEnum {
    APPLICATION("ApplicationName"),
    HOST_NAME("HostName"){
        @Override
        public String getValue() {
            return "Moj hostik";
        }
    },
    SERVER_IP("ServerIP");

    String key;
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            //toto sa nemusi robit pri loadovani clasy, teda staticky, ale moze to byt napr. aj pri starte aplikacie
            properties.load(ConfiguraciaPomocouEnum.class.getClassLoader().getResourceAsStream(
                    "com/insdata/enums/config.properties"));
        }
        catch (IOException e) {
            throw new RuntimeException("Error when loading configuration file", e);
        }
    }

    ConfiguraciaPomocouEnum(String key) {
        this.key = key;
    }

    public String getValue(){
        return properties.getProperty(key);
    }
}

class TestEnumKonfiguraciu {
    public static void main(String[] args) {
        System.out.println(ConfiguraciaPomocouEnum.APPLICATION.getValue());
        System.out.println(ConfiguraciaPomocouEnum.HOST_NAME.getValue());
        System.out.println(ConfiguraciaPomocouEnum.SERVER_IP.getValue());
    }
}
