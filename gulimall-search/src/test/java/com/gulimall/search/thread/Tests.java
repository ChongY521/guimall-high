package com.gulimall.search.thread;

import java.util.concurrent.*;
import java.util.regex.Pattern;

/**
 * 异步测试
 *
 * @author aqiang9  2020-08-23
 */
public class Tests {
    public static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println("开始---");
//
//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(6);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("future1 线程名：" + Thread.currentThread().getName());
//            return 1;
//        }, executor);
//
//        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
//
//            System.out.println("future2 线程名：" + Thread.currentThread().getName());
//            return 2;
//        }, executor);
//        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("future3线程名：" + Thread.currentThread().getName());
//
//            return 3;
//        }, executor);
//
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(future1, future2, future3);
//
//        // 阻塞等待
//        voidCompletableFuture.get() ;
//        // 获取值 。。。
//        System.out.println("结束---");
//        executor.shutdown();
//        System.out.println("abc".replace("a", ""));
        Pattern compile = Pattern.compile("&?attrs=4_5G", Pattern.CASE_INSENSITIVE);
        System.out.println(compile.matcher("attrs=45&attrs=4_5G").replaceFirst(""));
        System.out.println(compile.matcher("&attrs=4_5G").replaceFirst(""));

    }

}
