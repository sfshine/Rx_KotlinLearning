package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    println("start test: [Current Thread] ${Thread.currentThread()}")
    var countDownLatch = CountDownLatch(1)
    Observable.timer(10000, TimeUnit.MILLISECONDS)
        .subscribe {
            println("timer from [time Thread] ${Thread.currentThread()}, value $it")
            countDownLatch.countDown()
        }

    Observable.interval(1000, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.io())
        .map {
            println("interval map from [io Thread] ${Thread.currentThread()}")
            return@map it * 10
        }
        .observeOn(Schedulers.computation())
        .subscribe {
            println("interval subscribe from [computation Thread] ${Thread.currentThread()}, value is $it")
        }
    countDownLatch.await()
}