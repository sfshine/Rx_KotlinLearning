package mt

import rx.Observable

class LiftAllTransformer : Observable.Transformer<Int, String> {
    override fun call(t: Observable<Int>): Observable<String> {
        return t.map {
            it * 10
        }.map {
            "two map end: $it"
        }
    }
}

/**
 * 批量lift
 */
fun main(args: Array<String>) {
    Observable.create<Int> {
        it.onNext(1)
        it.onCompleted()
    }.compose(LiftAllTransformer())
        .subscribe {
            println(it)
        }
}