package com.aslam.co321_project;

public class GraphPoints {
    float Humidity;
    float Temperature;
    String Stability;
    float Pressure;

    static double time =  -.8;
    double thisTime;

    public GraphPoints(float Humidity,  float Pressure, String Stability, float Temperature ) {
        this.Humidity = Humidity;
	this.Pressure = Pressure;        
	this.Stability = Stability;	
	this.Temperature = Temperature;
    }

    public double getTime(){
        time+=.2;
        return time;
    }

    public double getThisTime() {
        return thisTime;
    }

    public float getHumidity() {
        return Humidity;
    }

    public float getTemperature() {
        return Temperature;
    }

    public String getStability() {
        return Stability;
    }

    public float getPressure() {
        return Pressure;
    }
}
