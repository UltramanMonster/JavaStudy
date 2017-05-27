package com.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1.
 */
public class Demo {

    private Jedis jedis;

    @Before
    public void setup(){
        jedis = new Jedis("127.0.0.1",6379);
        //jedis.auth("admin");
    }

    @Test
    public void testString(){
        jedis.set("name","zenglin");
        System.out.println(jedis.get("name"));

        jedis.append("name"," is zenglin");
        System.out.println(jedis.get("name"));

        jedis.del("name");
        System.out.println(jedis.get("name"));

        jedis.mset("name","zhihu","age","44","qq","1111111");
        jedis.incr("age");//进行加一操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

    }

    @Test
    public void testMap(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","yingliuzhizhu");
        map.put("age","22");
        map.put("qq","1101478862");
        jedis.hmset("user",map);

        List<String> list = jedis.hmget("user","name","age","qq");
        System.out.println(list);

        //删除map中的某一个键值
        jedis.hdel("user","age");
        System.out.println(jedis.hget("user","age"));
        System.out.println(jedis.hlen("user"));
        System.out.println(jedis.exists("age"));
        System.out.println(jedis.hkeys("user"));
        System.out.println(jedis.hvals("user"));

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()){
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user",key));
        }

    }

    @Test
    public void testList(){

        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework",0,-1));

        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","struts");
        jedis.lpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework",0,-1));

        jedis.del("java framework");
        jedis.rpush("java framework","spring1");
        jedis.rpush("java framework","struts1");
        jedis.rpush("java framework","hibernate1");
        System.out.println(jedis.lrange("java framework",0,-1));

    }

    @Test
    public void testSet(){

        jedis.del("user");

        jedis.sadd("user","z1");
        jedis.sadd("user","z2");
        jedis.sadd("user","z3");
        jedis.sadd("user","z4");
        jedis.sadd("user","z5");

        //移除
        jedis.srem("user","z5");
        System.out.println(jedis.smembers("user"));
        System.out.println(jedis.sismember("user","z5"));
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));

    }

    @Test
    public void test(){
        System.out.println(jedis.get("user"));
        System.out.println(jedis.smembers("user"));
    }

}
