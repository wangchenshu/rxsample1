import rx.Observable
import rx.Subscriber
import java.util.*

/**
 * Created by chenshuwang on 2016/7/11.
 */
fun main(args: Array<String>) {
    /*
      Observable.from()
      Creates an observable from an Iterable, a Future, or an Array.
     */
    Observable.from(listOf(1, 2, 3, 4, 5).map { it -> it+2 })
        .subscribe { it -> println("(from) Next: " + it) }

    /*
      Observable.just()
      Creates observable from an object or several objects
     */
    Observable.just("hello, world")
    //this will emit "Hello World!" to all its subscribers

    /*
      Observable.create()
      Creates an Observable from scratch by means of a function.
      We just implement the OnSubscribe interface and tell the
      observable what it should send to its subscriber(s).
     */
    Observable.create(object : Observable.OnSubscribe<Int> {
        override fun call(subscriber: Subscriber<in Int>) {
            (1..5).map { it-> it*2 }.forEach { i -> subscriber.onNext(i) }
            subscriber.onCompleted()
        }
    }).subscribe { it -> println("(create) Next: " + it) }

    Observable.just("Hello World Kotlin1").subscribe { it -> println(it) }
    Observable.just(1, 2, 3, 4, 5).map { it -> it+1 }
        .subscribe(object: Subscriber<Int>() {
        override fun onCompleted() {
            println("Sequence complete.")
        }
        override fun onError(e: Throwable?) {
            println("Error encountered: " + e)
        }
        override fun onNext(it: Int?) {
            println("(just) Next: " + it)
        }
    })
}