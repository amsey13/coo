package main.java.observer;

public interface Subject {
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers(String event);
}
