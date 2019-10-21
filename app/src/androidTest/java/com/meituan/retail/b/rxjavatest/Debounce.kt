package com.meituan.retail.b.rxjavatest

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * https://segmentfault.com/a/1190000015232015
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    Observable.zip(
        Observable.from(arrayOf("alex", "wendy", "potter")),
        Observable.interval(160, TimeUnit.MILLISECONDS)
    ) { it, _ -> it }
        .debounce(150, TimeUnit.MILLISECONDS)//只使用最新的事件
        .subscribe {
            println("debounce: just pick the events beyond 150ms $it")
        }
    countDownLatch.await()
}