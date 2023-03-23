package com.relaxiTaxi.token.common;

import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

@Component
public class Memcache {

    public static void main(String[] args) throws IOException {
        /*MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        System.out.println("Connection to server sucessfully");


        mcc.set("1234", 800, "234");
        mcc.set("tutorialspoint", 9, "memcached2");

        System.out.println("Get from Cache:" + mcc.get("tutorialspoint"));
        Memcache memcache = new Memcache();
        System.out.println(Memcache.validate("1234", "234"));
        mcc.shutdown();*/
    }


    public void add(String key, String value) throws IOException {
        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        mcc.set(key, 20000, value);
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
