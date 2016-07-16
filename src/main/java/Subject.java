import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by chenshuwang on 2016/7/16.
 */
public class Subject {
    public static void println(String... args) {
        for (String i : args) System.out.print(i);
        System.out.println("");
    }

    public static void println() {
        System.out.println("");
    }

    public static void main(String[] args) {
        // Cold observable example
        PublishSubject<String> stringPublishSubject = PublishSubject.create();
        Subscription subscriptionPrint = stringPublishSubject.subscribe(
            (message) -> println(message),
            error -> println("Oh,no!Something wrong happened!"),
            () -> println("Observable completed")
        );
        stringPublishSubject.onNext("Hello World");
        println();

        // Async Subject
        final AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext(0);
        asyncSubject.onNext(1);
        asyncSubject.subscribe(it -> println("(AsyncSubject) it: " + it));

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
        .doOnCompleted(() -> asyncSubject.onNext(2)).subscribe();
        asyncSubject.onNext(3);
        asyncSubject.onNext(4);
        asyncSubject.onCompleted();
        asyncSubject.onNext(5);
        println();

        // Publish Subject
        final PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.onNext(-1);
        publishSubject.onNext(-2);
        publishSubject.subscribe(it -> println("(PublishSubject) it: " + it));

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
        .doOnCompleted(() -> publishSubject.onNext(0)).subscribe();
        publishSubject.onNext(1);
        println();

        // Behavior Subject
        final BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(0);
        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        behaviorSubject.onNext(3);
        behaviorSubject.subscribe((it) -> println("(BehaviorSubject) it: " + it));

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
        .doOnCompleted(() -> behaviorSubject.onNext(1)).subscribe();
        behaviorSubject.onNext(2);
        println();

        // ReplaySubject Subject
        final ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);
        replaySubject.subscribe((it) -> println("(ReplaySubject) it: " + it));

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
        .doOnCompleted(() -> replaySubject.onNext(1)).subscribe();
        replaySubject.onNext(2);
        replaySubject.onNext(4);
        println();
    }
}
