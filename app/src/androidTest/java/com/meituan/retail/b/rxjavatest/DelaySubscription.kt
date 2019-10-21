package com.meituan.retail.b.rxjavatest

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * 延迟subscribe, 延迟执行
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    Observable.just(0, 1, 2, 3)
        .delaySubscription(1000, TimeUnit.MILLISECONDS)
        .subscribe {
            println(it)
        }
    countDownLatch.await()
}