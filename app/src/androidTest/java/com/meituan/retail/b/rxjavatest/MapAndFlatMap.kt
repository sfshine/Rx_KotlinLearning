package com.meituan.retail.b.rxjavatest

import rx.Observable

fun main(args: Array<String>) {

    Observable.from(arrayOf("alex", "wendy", "potter"))
        .map {
            return@map it.toUpperCase()
        }
        .subscribe {
            println("flatMap: print one letter every time $it")
        }

    Observable.from(arrayOf("alex", "wendy", "potter"))
        .flatMap {
            println("flatMap start")
            return@flatMap Observable.from(it.toCharArray().asIterable())
        }
        .subscribe {
            println("flatMap: print one letter every time $it")
        }
}