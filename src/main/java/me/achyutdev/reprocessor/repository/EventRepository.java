package me.achyutdev.reprocessor.repository;

import me.achyutdev.reprocessor.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findEventsByStatus(String status);
}
