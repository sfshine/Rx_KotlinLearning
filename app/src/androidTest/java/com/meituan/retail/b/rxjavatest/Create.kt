package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    println("start test: [Current Thread] ${Thread.currentThread()}")
    var countDownLatch = CountDownLatch(1)
    var observable = Observable
        .create<Int> {
            for (i in 0..5) {
                Thread.sleep(1000)
                it.onNext(i)
            }
            println("subscribeOn Thread ${Thread.currentThread()}")
            it.onCompleted()
        }
        .doOnUnsubscribe {
            println("doOnUnsubscribe")
        }
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe(object : Subscriber<Int>() {
            override fun onNext(t: Int?) {
                println("next value $t")
            }

            override fun onCompleted() {
                println("onCompleted")
            }

            override fun onError(e: Throwable?) {
                println("onError $e")
            }

        })
    countDownLatch.await(2000, TimeUnit.MILLISECONDS)
    observable.unsubscribe()
}
