package me.achyutdev.reprocessor.service;

public interface ReprocessorService {

    <T, O> T  processEvent(T event, O field);


}
