package com.example.hector.EventMan;

/**
 * Created by hector on 12/01/15.
 */
public class Events {

    private int _id;
    private String _eventname;
    private int _direction;
    private int _evtime;
    private int _evusetime;

    // empty constructor
    public Events() {
    }
    // constructor
    public Events(int id, String eventname, int direction, int evtime, int evusetime) {
        this._id = id;
        this._eventname = eventname;
        this._direction = direction;
        this._evtime = evtime;
        this._evusetime = evusetime;
    }
    // constructor
    public Events(String eventname, int direction, int evtime, int evusetime) {
        this._eventname = eventname;
        this._direction = direction;
        this._evtime = evtime;
        this._evusetime = evusetime;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_eventname(String _eventname) {
        this._eventname = _eventname;
    }

    public int get_id() {
        return _id;
    }

    public String get_eventname() {
        return _eventname;
    }
    public int get_direction() {
        return _direction;
    }
    public int get_evtime() {
        return _evtime;
    }
    public int get_evusetime() {
        return _evusetime;
    }
}