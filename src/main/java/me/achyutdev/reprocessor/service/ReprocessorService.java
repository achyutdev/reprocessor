package me.achyutdev.reprocessor.service;

import java.util.List;

public interface ReprocessorService {

    /**
     * This method takes single event and assign to one thread of any instances
     * @param event is going to pick by one event
     * @param field has to be unique key of the event in order to process single time
     * @param <T> event class type
     * @param <O> field event type
     * @return same processed event
     */
    <T, O> T  processEvent(T event, O field);


    /**
     * This method takes List of event and process one by one
     * @param events
     * @param field
     * @param <T>
     * @param <O>
     */
    <T, O> void  processEvents(List<T> events, O field);


}
