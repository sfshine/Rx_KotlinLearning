package com.meituan.retail.b.rxjavatest

import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 *  compose() 是针对 Observable 自身进行变换
 */
class SubscribeUnSubscribeTransformer<T> : Observable.Transformer<T, T> {
    override fun call(t: Observable<T>): Observable<T>? {
        return t.doOnSubscribe {
            println("doOnSubscribe")
        }.doOnUnsubscribe {
            println("doOnUnsubscribe")
        }
    }

}

/**
 * 批量lift
 */
fun main() {
    var countDown = CountDownLatch(1)
    var observer = Observable.create<Int> {
        for (i in 0..5) {
            Thread.sleep(1000)
            it.onNext(i)
        }
        it.onCompleted()
    }
        .subscribeOn(Schedulers.computation())
        .compose(SubscribeUnSubscribeTransformer())
        .subscribe {
            println(it)
        }
    countDown.await(3000, TimeUnit.MILLISECONDS)
    observer.unsubscribe()
}