package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.functions.FuncN
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 *
 * zip
 * zip工作原理如下，当每个传入zip的流都发射完毕第一次数据时，zip将这些数据合并为数组并发射出去；当这些流都发射完第二次数据时，
 * zip再次将它们合并为数组并发射。以此类推直到其中某个流发出结束信号，整个被合并后的流结束，不再发射数据。
 *
 * combineLatest
 * combineLatest一开始也会等待每个子流都发射完一次数据，但是在合并时，如果子流1在等待其他流发射数据期间又发射了新数据，则使用
 * 子流最新发射的数据进行合并,之后每当有某个流发射新数据，不再等待其他流同步发射数据，而是使用其他流之前的最近一次数据进行合并。
 *
 * 参考
 * https://segmentfault.com/a/1190000012369871
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    var singleIntervalObservables = arrayListOf(
        Observable.just(1),
        Observable.interval(1000, TimeUnit.MILLISECONDS),
        Observable.just(3)
    )
    Observable.combineLatest(singleIntervalObservables, FuncN {
        //do some data process
        return@FuncN it
    }).subscribe {
        println("---combineLatest funcN start---")
        it.forEach { it ->
            println(it)
        }
        println("---combineLatest funcN end---")
    }


    var wholeIntervalObservables = arrayListOf(
        Observable.interval(1000, TimeUnit.MILLISECONDS),
        Observable.interval(1000, TimeUnit.MILLISECONDS),
        Observable.interval(1000, TimeUnit.MILLISECONDS)
    )


    Observable.zip(wholeIntervalObservables) { it -> it }
        .subscribe {
            println("---zip funcN start---")
            it.forEach { it ->
                println(it)
            }
            println("---zip funcN start---")
        }
    countDownLatch.await()
}