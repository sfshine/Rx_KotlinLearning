package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.Observer
import rx.Subscriber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * 在timeEnd事件到来之前, 执行interval事件
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    var timeEnd = Observable.timer(5000, TimeUnit.MILLISECONDS)
    Observable
        .create<Int> {
            for (i in 0..10) {
                it.onNext(i)
                println("OnSubscribe $i Thread ${Thread.currentThread()}")
                Thread.sleep(1000)
            }
            it.onCompleted()
        }
        .takeUntil(timeEnd)
        .subscribe(
            object : Subscriber<Int>() {

                override fun onStart() {
                    super.onStart()
                    println("-------------onStart-------------------")
                }

                override fun onNext(p0: Int?) {
                    println("-------------onNext $p0 -------------------")
                }

                override fun onCompleted() {
                    println("-------------onCompleted-------------------")
                }

                override fun onError(p0: Throwable?) {
                    println("-------------onError-------------------")
                }

            }
        )
    countDownLatch.await()
}
