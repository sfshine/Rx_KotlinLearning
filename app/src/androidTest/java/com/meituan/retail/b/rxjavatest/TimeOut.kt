package com.meituan.retail.b.rxjavatest

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    Observable
        .timer(5000, TimeUnit.MILLISECONDS)
        .timeout(1000, TimeUnit.MILLISECONDS)
        .subscribe(
            { println(it) },
            {
                println("onError")
                it.printStackTrace()
            }
        )
    countDownLatch.await()
}