package me.achyutdev.reprocessor.service.impl;

import me.achyutdev.reprocessor.service.ReprocessorService;

import java.util.ArrayList;
import java.util.List;

public class ReprocessorServiceImpl implements ReprocessorService {

    private int currentInstanceIndex;

    private int threadPoolSize;

    private int totalInstances;

    public ReprocessorServiceImpl(){
        this.currentInstanceIndex = 1;
        this.threadPoolSize = 1;
        this.totalInstances =1;
    }

    public ReprocessorServiceImpl(final int currentInstanceIndex,
                                  final int threadPoolSize,
                                  final int totalInstances){
        this.currentInstanceIndex =currentInstanceIndex;
        this.threadPoolSize = threadPoolSize;
        this.totalInstances = totalInstances;
    }

    @Override
    public <T, O> T processEvent(T event, O field) {

        return null;
    }

    @Override
    public <T, O> void processEvents(List<T> events, final O field) {
        List<T> myEventList = getMyEventList(events);
    }


    private <T>  List<T> getMyEventList(List<T> events) {
        List<T> myEvents = new ArrayList<>();
        for(T event : events){
            if(isEventMine(event)){
                myEvents.add(event);
            }
        }
        return myEvents;
    }

    private <T> boolean isEventMine(T event) {
        int myNumber = getMyNumber();


        int totalThreads = threadPoolSize * totalInstances;
        return Math.abs("".hashCode()) % totalThreads == myNumber;
    }

    private int getMyNumber() {
        int threadNumber = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        return currentInstanceIndex*threadPoolSize + threadNumber;
    }

    private int getFieldValue(String field){
        return Math.abs(field.hashCode());
    }
}
