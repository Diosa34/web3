package com.github.diosa.web3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static java.lang.Math.pow;

// поле name = X задает имя таблицы как Х, а не аналогичное названию класса
@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double x;
    private double hiddenX;
    private double y;
    private double hiddenY;
    private boolean r1;
    private boolean r2;
    private boolean r3;
    private boolean r4;
    private boolean r5;
    private String res;
    private double exec = 0;
    private LocalDateTime current = LocalDateTime.now();

    public Point() {
    }

    public Point(double x, double y, boolean r1, boolean r2, boolean r3, boolean r4, boolean r5) {
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.r5 = r5;
        this.res = "Didn't check";
    }

    public double getExec() {
        return exec;
    }

    public void setExec(double exec) {
        this.exec = exec;
    }

    public String getCurrent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return current.format(formatter);
    }

    public void setCurrent(LocalDateTime current) {
        this.current = current;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public double getHiddenX() {
        return hiddenX;
    }

    public double getHiddenY() {
        return hiddenY;
    }

    public void setHiddenX(double hiddenX) {
        this.hiddenX = hiddenX;
    }

    public void setHiddenY(double hiddenY) {
        this.hiddenY = hiddenY;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        String strY = String.valueOf(this.y);
        int minus = strY.charAt(0) == '-' ? 1 : 0;
        return Double.parseDouble(strY.replace(",", "."));
    }

    public void setY(double y) {

        this.y = y;
    }

    public boolean getR1() {
        return this.r1;
    }

    public void setR1(boolean r1) {
        this.r1 = r1;
    }

    public boolean getR2() {
        return this.r2;
    }

    public void setR2(boolean r2) {
        this.r2 = r2;
    }


    public boolean getR3() {
        return this.r3;
    }

    public void setR3(boolean r3) {
        this.r3 = r3;
    }

    public boolean getR4() {
        return this.r4;
    }

    public void setR4(boolean r4) {
        this.r4 = r4;
    }

    public boolean getR5() {
        return this.r5;
    }

    public void setR5(boolean r5) {
        this.r5 = r5;
    }

    private boolean rectangle(double x, double y, double r) {
        return (x > 0) && (y < 0) && (x <= r / 2) && (y >= r*(-1));
    }

    private boolean circle(double x, double y, double r) {
        return (x >= 0) && (y >= 0) && (pow(x, 2) + pow(y, 2) <= (pow(r, 2)));
    }

    private boolean triangle(double x, double y, double r) {
        return (x < 0) && (y > 0) && (y <= (r * x));
    }

    public void checkHit() {
        System.out.println(this.r1 + " " +  this.r2 + " " + this.r3 + " " + this.r4 + " " + this.r5);
        Map<Integer, Boolean> hittingMap = new HashMap<>();
        List<Boolean> rList = Arrays.asList(this.r1, this.r2, this.r3, this.r4, this.r5);
        for (int i = 1; i < 6; i++) {
            hittingMap.put(i, rList.get(i-1) && rectangle(this.x, this.y, i));
        }

        if (hittingMap.get(1) || hittingMap.get(2) || hittingMap.get(3) || hittingMap.get(4) || hittingMap.get(5)) {
            setRes("In rectangles with r:");
            addHittingToResult(hittingMap);
            return;
        } else {
            for (int i = 1; i < 6; i++) {
                hittingMap.put(i, rList.get(i-1) && circle(x, y, i));
            }
        }

        if (hittingMap.get(1) || hittingMap.get(2) || hittingMap.get(3) || hittingMap.get(4) || hittingMap.get(5)) {
            setRes("In quarter circle with r:");
            addHittingToResult(hittingMap);
            return;
        } else {
            for (int i = 1; i < 6; i++) {
                hittingMap.put(i, rList.get(i-1) && triangle(x, y, i));
            }
        }

        if (hittingMap.get(1) || hittingMap.get(2) || hittingMap.get(3) || hittingMap.get(4) || hittingMap.get(5)) {
            setRes("In triangles with r:");
            addHittingToResult(hittingMap);
            return;
        } else {
            setRes("Outside the area");
        }
    }

    private void addHittingToResult(Map<Integer, Boolean> hittingMap){
        for (Integer hitting: hittingMap.keySet()) {
            if (hittingMap.get(hitting)) {
                setRes(getRes() + " " + hitting.toString());
            }
        }
    }

    public void checkHidden() {
        if (this.x == 0d && this.hiddenX != 0d) {
            this.x = this.hiddenX;
        }
        if (this.y == 0d && this.hiddenY != 0d) {
            this.y = this.hiddenY;
        }
    }
}