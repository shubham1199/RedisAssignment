package com.sm;

import com.sm.ActionImpl.Action;
import redis.clients.jedis.Jedis;
import java.util.Scanner;


public class RedisApp {

    private static Integer pid = 0;
    public static void main(final String[] args){
        Jedis jedis = new Jedis("localhost");
        RedisApp.showActionMenu(jedis);
    }

    public static void showActionMenu(Jedis jedis){
        while(true){
            System.out.println("Select one of the following action\n");
            System.out.println("1. Add product (input: name)\n" +
                    "2. Add item to cart(input: product id) \n" +
                    "3. Buy product (input: product id)\n" +
                    "4. View Product (input : pid)\n" +
                    "5. Get Top Selling\n" +
                    "6. Trending Products\n" +
                    "7. Non-Performing Products\n" +
                    "" +
                    "\n\n");

            System.out.println("Enter choice :: ");
            Scanner inputScanner = new Scanner(System.in);
            int choice = Integer.parseInt(inputScanner.nextLine());
            String actionInput = inputScanner.nextLine();
            Action action = new Action();


            switch (choice){
                case 1 : action.addProduct(jedis,actionInput,++pid);action.showElements(jedis,"scoreTotal",0);break;

                case 2 : action.addToCart(jedis,actionInput);action.showElements(jedis,"scoreTotal",0); break;

                case 3 : action.buyProduct(jedis , actionInput);action.showElements(jedis,"scoreTotal",0); break;

                case 4 : action.viewProduct(jedis , actionInput);action.showElements(jedis,"scoreTotal",0); break;

                case 5 : action.getTopSelling(jedis);break;

                case 6 : action.getTrending(jedis);break;

                case 7 : action.getNotPerformingProducts(jedis);break;

                //for debugging purpose
//                case 80 : jedis.flushAll();break;
//
//                case 90 : action.showElements(jedis,"scoreTotal",5);break;
//
//                case 91 : action.showElements(jedis,"sellCount",5);break;


            }
        }
    }
}