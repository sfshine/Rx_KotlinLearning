package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.Subscriber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * 在timeEnd事件到来之前, 执行interval事件
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    var timeEnd = Observable.timer(5000, TimeUnit.MILLISECONDS)
    Observable.interval(1000, TimeUnit.MILLISECONDS).takeUntil(timeEnd).subscribe(object : Subscriber<Long>() {
        override fun onNext(it: Long?) {
            println(it)
        }

        override fun onCompleted() {
            println("onCompleted")
        }

        override fun onError(e: Throwable?) {
            println("onError")
        }
    })
    countDownLatch.await()
}