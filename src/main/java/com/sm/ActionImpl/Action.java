package com.sm.ActionImpl;

import redis.clients.jedis.Jedis;

public class Action {

    private static double scoreForView = 10;
    private static double scoreForAddToCart = 50;
    private static double scoreForBuy = 100;

    public void addProduct(Jedis jedis,String name,Integer pid){
        jedis.hset(pid.toString(),"name",name);
        jedis.zadd("scoreTotal",0,pid.toString());
        jedis.zadd("sellCount",0,pid.toString());
        jedis.lpush("products",pid.toString());
    }

    public void addToCart(Jedis jedis,String pid){
        jedis.zincrby("scoreTotal",scoreForAddToCart,pid);
    }

    public void viewProduct(Jedis jedis,String pid){
        jedis.hincrBy(pid,"viewCount" , 1L);
        jedis.zincrby("scoreTotal",scoreForView,pid);
    }

    public void buyProduct(Jedis jedis,String pid){
        jedis.zincrby("scoreTotal",scoreForBuy,pid);
        jedis.zincrby("sellCount",1,pid);
    }

    public void getTopSelling(Jedis jedis){
        Long noOfElements = jedis.zcard("sellCount");

        for(int  i = 0 ; i < 10 && noOfElements > i;i++){
            String topProduct = jedis.zrevrange("sellCount",i,i+1).iterator().next();
            String name = jedis.hget(topProduct , "name");
            System.out.println(i + ". " + topProduct + " " + name);

        }
    }

    public void getTrending(Jedis jedis){
        Long noOfElements = jedis.zcard("scoreTotal");

        for(int  i = 0 ; i < 10 && noOfElements > i;i++){
            String topProduct = jedis.zrevrange("scoreTotal",i,i+1).iterator().next();
            String name = jedis.hget(topProduct , "name");
            System.out.println(i + ". " + topProduct + " " + name);

        }
    }

    public void getNotPerformingProducts(Jedis jedis){
        Long noOfElements = jedis.zcard("scoreTotal");

        for(int  i = 0 ; i < 10 && noOfElements > i;i++){
            String topProduct = jedis.zrange("scoreTotal",i,i+1).iterator().next();
            String name = jedis.hget(topProduct , "name");
            System.out.println(i + ". " + topProduct + " " + name);
        }
    }

    public void getNewArrivals(Jedis jedis){
        Long noOfElements = jedis.llen("products");

        for(int  i = 0 ; i < 10 && noOfElements > i;i++){
            String newProduct = jedis.lrange("products",i,i+1).iterator().next();
            String name = jedis.hget(newProduct , "name");
            System.out.println(i + ". " + newProduct + " " + name);
        }
    }

    public void showElements(Jedis jedis,String key,int a){
        int noOfElements = jedis.zcard(key).intValue();
        System.out.println("Pr.Id      Name ");
        for(int  i = 0 ;  i < a && noOfElements > i;i++){
            String topProduct = jedis.zrevrange(key,i,i + 1).iterator().next();
            String name = jedis.hget(topProduct , "name");
            System.out.println(topProduct + "   ====== " + name);

        }
    }
}
