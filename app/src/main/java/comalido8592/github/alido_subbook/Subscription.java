/*
 * Copyright 2018 Team Me. CMPUT 301. University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise please contact alido@ualberta.ca.
 */

package comalido8592.github.alido_subbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by eleiee on 2018-02-03.
 */

/**
 * Represents a Subscription object
 * @author alido
 * @version 1.0
 */

public class Subscription implements Serializable{
    private String name;
    private float rate;
    private String comment;
    private Calendar dateStarted;


    /**
     * Constructs an empty Subscription object
     */

    public Subscription(){ //empty constructor
        this.name = "";
        this.rate = 0;
        this.comment = "";
        this.dateStarted = Calendar.getInstance();
    }

    /**
     * Constructs a Subscription object with the provided name and rate
     * @param name String for subscription name
     * @param rate float for the monthly rate
     */

    public Subscription(String name, float rate){
        setName(name);
        setRate(rate);
        this.dateStarted = Calendar.getInstance();
    }

    /**
     * Constructs a Subscription object with the provided name, date, and rate
     * @param name String for subscription name
     * @param date Calendar date for the subscription start date
     * @param rate float for the monthly rate
     */

    public Subscription(String name, Calendar date, float rate){
        setName(name);
        setRate(rate);
        setDate(date);
    }

    /**
     * Constructs a Subscription object with the provided name, date, rate, and comment
     * @param name String for the subscription name
     * @param date Calendar date for the subscription start date
     * @param rate float for the monthly rate
     * @param comment String for comments regarding the subscription
     */

    public Subscription(String name, Calendar date, float rate, String comment){
        setName(name);
        setDate(date);
        setRate(rate);
        setComment(comment);
    }

    /**
     * Constructs a Subscription object with the provided name, rate, and comment
     * @param name String for the subscription name
     * @param rate float for the monthly rate
     * @param comment String for comments regarding the subscription
     */

    public Subscription(String name, float rate, String comment){
        setName(name);
        setRate(rate);
        setComment(comment);
    }

    /**
     * Sets the name for the subscription
     * @param name String for the subscription name
     * @throws IllegalArgumentException Exception thrown for when the length is too long or too short
     */

    public void setName(String name) throws IllegalArgumentException{
        if ((name.length()>20) || (name.length() == 0)) {
            throw new IllegalArgumentException();
        }
        else {
            this.name = name;
        }
    }

    /**
     * Sets the monthly rate for the subscription
     * @param rate float for the rate
     * @throws IllegalArgumentException Exception thrown for when the float is below 0
     */

    public void setRate(float rate) throws IllegalArgumentException{
        if (rate<0) {
            throw new IllegalArgumentException();
        }
        else {
            this.rate = rate;
        }
    }

    /**
     * Sets the comment for the subscription
     * @param comment String for the comment
     * @throws IllegalArgumentException Exception thrown for when the comment is past 30 chars
     */

    public void setComment(String comment) throws IllegalArgumentException{
        if (comment.length()>30) {
            throw new IllegalArgumentException();
        }
        else {
            this.comment = comment;
        }
    }

    /**
     * Sets the date for the subscription
     * @param date Calendar date for the subscription's date
     */

    public void setDate(Calendar date) {
        this.dateStarted = date;

    }

    /**
     * Gets the subscription name
     * @return String of the subscription's name
     */

    public String getName() {
        return this.name;
    }

    /**
     * Gets the rate of the subscription
     * @return float of the subscription rate
     */

    public float getRate() {
        return this.rate;
    }

    /**
     * Gets the date of the subscription
     * @return Calendar date of the subscription
     */

    public Calendar getDate() {
        return this.dateStarted;
    }

    /**
     * Gets the comment of the subscription
     * @return String comment of the subscription
     */

    public String getComment() {
        return this.comment;
    }

    /**
     * Override of the toString() function to allow for displaying all relevant details at once
     * @return String consisting of the name, rate, comment, and date of subscriptions
     */

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String newdate = dateFormat.format(this.dateStarted.getTime());
        return this.name + " || " + this.rate + " || " + this.comment + " || " + newdate;
    }
}
