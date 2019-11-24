package me.achyutdev.reprocessor.scheduler;

import lombok.extern.slf4j.Slf4j;
import me.achyutdev.reprocessor.model.Event;
import me.achyutdev.reprocessor.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableAsync
public class EventReprocessScheduler {

    private static final org.slf4j.Logger Log = org.slf4j.LoggerFactory.getLogger(EventReprocessScheduler.class);

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private EventRepository repository;

    @Value("${reprocessor.thread.pool.size}")
    private int threadPoolSize;

    @Value("${reprocessor.total.instances}")
    private int totalInstances;

    @Value("${reprocessor.current.instance.index}")
    private int currentInstanceIndex;

    @Async(value = "threadPoolTaskScheduler")
    @Scheduled(fixedDelayString = "${reprocessor.scheduler.fixDelay}")
    public void processEvent(){
        Log.info("BEFORE :: Scheduler is started for thread :::" + Thread.currentThread().getName());
        Log.info("BEFORE :: Total active thread in the threadPool ::: " + taskScheduler.getActiveCount());
        Log.info("BEFORE :: taskScheduler.isRemoveOnCancelPolicy() ::: "+ taskScheduler.isRemoveOnCancelPolicy());
        Log.info("BEFORE :: taskScheduler.getThreadGroup() ::: "+ taskScheduler.getThreadGroup());

        Iterable<Event> failedEvents = repository.findEventsByStatus("FAILED");
        List<Event> myFailedEvent = getMyFailedEvent(failedEvents);

        for(Event evnt : myFailedEvent){
            evnt.setProcessedBy(getThreadDetails());
            evnt.setStatus("SUCCESS");
            evnt.setProcessedOn(LocalDateTime.now());
            repository.save(evnt);
        }
        Log.info("AFTER :: Scheduler is started for thread :::" + Thread.currentThread().getName());
        Log.info("AFTER :: Total active thread in the threadPool ::: " + taskScheduler.getActiveCount());
        Log.info("AFTER :: taskScheduler.isRemoveOnCancelPolicy() ::: "+ taskScheduler.isRemoveOnCancelPolicy());
        Log.info("AFTER :: taskScheduler.getThreadGroup() ::: "+ taskScheduler.getThreadGroup());

    }

    private String getThreadDetails() {
        return "Name::"+ Thread.currentThread().getName()+" Id::" +Thread.currentThread().getId();
    }

    private List<Event> getMyFailedEvent(Iterable<Event> failedEvents) {
        List<Event> myEvents = new ArrayList<>();
        for(Event event : failedEvents){
            if(isThisMyEvent(event)){
                 myEvents.add(event);
            }
        }
        return myEvents;
    }

    private boolean isThisMyEvent(Event event) {
        int myNumber = getMyNumber();
        int totalThreads = threadPoolSize * totalInstances;
        return Math.abs(event.getEventName().hashCode()) % totalThreads == myNumber;
    }

    private int getMyNumber() {
        int threadNumber = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        return currentInstanceIndex*threadPoolSize + threadNumber;
    }
}
