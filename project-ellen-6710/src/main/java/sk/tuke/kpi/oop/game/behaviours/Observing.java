package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;


import java.util.function.Consumer;
import java.util.function.Predicate;

public class Observing<A extends Actor, T> implements Behaviour<A> {
    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;
    public Observing(Topic<T> topic,Predicate<T> predicate,Behaviour<A> delegate){
        this.topic=topic;
        this.predicate=predicate;
        this.delegate=delegate;
    }
    public void setUp(A actor) {
        if(actor == null){
            return;
        }
        Consumer<T> tConsumer = tconsumer ->{
            if(predicate.test(tconsumer)&&actor!=null){
                delegate.setUp(actor);
            }
        };
        actor.getScene().getMessageBus().subscribe(topic,tConsumer);

    }
}
