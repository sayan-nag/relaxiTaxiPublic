package com.relaxiTaxi.session.common;

import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

@Component
public class Memcache {

    public static void main(String[] args) throws IOException {
        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        System.out.println("Connection to server sucessfully");


        mcc.set("1234", 800, "234");
        mcc.set("tarak.ths@gmail.com", 9, "8793");
        // System.out.println("set status:"+mcc.set("city", 5, "Bangalore"));
        //String actualValue = (String) mcc.get("tutorialspoint");
        //if (actualValue == null || actualValue.isEmpty())
        // System.out.println(actualValue);

        //System.out.println(actualValue);
        //Get value from cache
        //mcc.set("1124", 200, "daddba95-a357-4308-b64c-be66f7f1dc5c");
        System.out.println("Get from Cache:" + mcc.get("tutorialspoint"));
        Memcache memcache = new Memcache();
        System.out.println(Memcache.validate("1234", "234"));
        mcc.shutdown();
        boolean val = Boolean.parseBoolean("true");
        System.out.println("boolean val = "+val);
    }

    public void add(String key, String value) throws IOException {
        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        mcc.set(key, 2000, value);
        System.out.println("Get from Cache:" + mcc.get(key));
        mcc.shutdown();
    }

    public static boolean validate(String key, String value) throws IOException {
        MemcachedClient mcc = null;
        try {
            mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            String actualValue = (String) mcc.get(key);
            System.out.println("session value is" + actualValue);
            if (actualValue == null || actualValue.isEmpty() || !actualValue.equals(value))
                return false;
            else
                return true;
        } catch (IOException exception) {
            throw exception;
        } finally {
            mcc.shutdown();
        }
    }


}
