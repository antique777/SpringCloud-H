package org.javaboy.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

public class Resilience4jTest {

    @Test
    public void test() {
        //获取一个CircuitBreakerRegistry实例，可以调用ofDefault方法获取一个默认实例，也可以自定义属性
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();


        //自定义一个circuitBreakerRegistry实例
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率阈值百分比，，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                // 断路器保持打开的时间，在到达设置的时间之后，断路器会进入到half open（半打开）的状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //环形缓冲区  当断路器处于half open状态时，环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                //处于关闭状态时，环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);

        CircuitBreaker cb1 = circuitBreakerRegistry.circuitBreaker("javaboy");
        CircuitBreaker cb2 = circuitBreakerRegistry.circuitBreaker("javaboy2", config);

        //circuitBreaker装饰一个函数
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier)
                .map(v -> v + "hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

    @Test
    public void test2() {
        //自定义一个circuitBreakerRegistry实例
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率阈值百分比，，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                // 断路器保持打开的时间，在到达设置的时间之后，断路器会进入到half open（半打开）的状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //环形缓冲区  当断路器处于half open状态时，环形缓冲区的大小
                // 设置2表示有两条数据的时候就可以统计故障率了
//                .ringBufferSizeInHalfOpenState(2)
                //处于关闭状态时，环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = circuitBreakerRegistry.circuitBreaker("javaboy");

        //获取断路器的状态
        System.out.println(cb1.getState());
        cb1.onError(0, new RuntimeException());
        cb1.onError(0, new RuntimeException());
        System.out.println(cb1.getState());

        //断路器处于打开状态，后面就没法走了
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier)
                .map(v -> v + "hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());

    }

    @Test
    public void test3() {

        RateLimiterConfig config = RateLimiterConfig.custom()
                //阈值刷新时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                //阈值刷新频次
                .limitForPeriod(4)
                .timeoutDuration(Duration.ofMillis(1000))
                .build();
        RateLimiter rateLimiter = RateLimiter.of("javaboy", config);
        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });

        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t -> System.out.println(t.getMessage()));

    }

    @Test
    public void test4() {

        RetryConfig config = RetryConfig.custom()
                //最大重试次数
                .maxAttempts(5)
                //重试间隔时间
                .waitDuration(Duration.ofMillis(500))
                //重试的异常
                .retryExceptions(RuntimeException.class)
                .build();
        Retry retry = Retry.of("javaboy", config);
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count++ < 3) {
                    System.out.println(count);
                    throw new RuntimeException();
                }
            }
        }).run();

        System.out.println("下面的是超过重试次数，会抛异常");
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count++ < 6) {
                    System.out.println(count);
                    throw new RuntimeException();
                }
            }
        }).run();

    }


}
