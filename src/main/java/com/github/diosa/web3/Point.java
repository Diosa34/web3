//package com.github.diosa.web3;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import static java.lang.Math.pow;
//
//// поле name = X задает имя таблицы как Х, а не аналогичное названию класса
//@Entity(name = "Points")
//public class Point {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private double x;
//    private double y;
//    private double r;
//    private String res;
//    private double exec = 0;
//    private LocalDateTime current = LocalDateTime.now();
//
//    public Point() {
//    }
//
//    public Point(double x, double y, double r) {
//        this.x = x;
//        this.y = y;
//        this.r = r;
//        this.res = "no";
//    }
//
//    public double getExec() {
//        return exec;
//    }
//
//    public void setExec(double exec) {
//        this.exec = exec;
//    }
//
//    public String getCurrent() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return current.format(formatter);
//    }
//
//    public void setCurrent(LocalDateTime current) {
//        this.current = current;
//    }
//
//    public String getRes() {
//        return this.res;
//    }
//
//    public void setRes(String res) {
//        this.res = res;
//    }
//
//    public double getX() {
//        return this.x;
//    }
//
//    public void setX(double x) {
//        this.x = BigDecimal.valueOf(x)
//                .setScale(4, RoundingMode.HALF_UP)
//                .doubleValue();
//    }
//
//    public double getY() {
//        String strY = String.valueOf(this.y);
//        int minus = strY.charAt(0) == '-' ? 1 : 0;
//        return Double.parseDouble(strY.replace(",", "."));
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    public double getR() {
//        return this.r;
//    }
//
//    public void setR(double r) {
//        this.r = BigDecimal.valueOf(r)
//                .setScale(4, RoundingMode.HALF_UP)
//                .doubleValue();
//    }
//
//    private boolean rectangle(double x, double y, double r) {
//        return (x > 0) && (y < 0) && (x <= r / 2) && (y >= r*(-1));
//    }
//
//    private boolean circle(double x, double y, double r) {
//        return (x >= 0) && (y >= 0) && (pow(x, 2) + pow(y, 2) <= (pow(r, 2)));
//    }
//
//    private boolean triangle(double x, double y, double r) {
//        return (x < 0) && (y > 0) && (y <= (r * x));
//    }
//
//    public void checkHit() {
//        if (rectangle(x, y, r)) {
//            setRes("Точка попала в прямоугольник");
//        } else if (circle(x, y, r)) {
//            setRes("Точка попала в четверь круга");
//        } else if (triangle(x, y, r)) {
//            setRes("Точка попала в треугольник");
//        } else {
//            setRes("Точка не попала в область");
//        }
//    }
//}