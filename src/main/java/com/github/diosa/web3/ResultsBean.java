package com.github.diosa.web3;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class ResultsBean implements Serializable {
    public List<Point> getResultPointsList() {
        return resultPointsList;
    }

    public void setResultPointsList(List<Point> resultPointsList) {
        this.resultPointsList = resultPointsList;
    }

    public void setNewPoint(Point newPoint) {
        this.newPoint = newPoint;
    }

    private List<Point> resultPointsList;

    public Point getNewPoint() {
        return newPoint;
    }

    private Point newPoint = new Point();

    private final String PERSISTENCE_UNIT_NAME = "dot";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public ResultsBean() {
        this.resultPointsList = new ArrayList<>();
    }

    public ResultsBean(ArrayList<Point> points) {
        this.resultPointsList = points;
    }

    @Resource
    private UserTransaction userTransaction;

    public List<Point> loadPointsFromDB() {
        return entityManager.createQuery("SELECT e FROM Points e").getResultList();
    }

    public synchronized void addPointsToDB(Point point) throws Exception {
        userTransaction.begin();
        entityManager.persist(point);
        userTransaction.commit();
    }

    @PostConstruct
    private void loadPoints() {
        this.resultPointsList = loadPointsFromDB();
    }

    public synchronized void addPoint() {
        long startTime = System.nanoTime();
        this.newPoint.checkHit();
        this.newPoint.setCurrent(LocalDateTime.now());
        this.newPoint.setExec((System.nanoTime() - startTime) / 1000000d);
        try {
            addPointsToDB(this.newPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.resultPointsList.add(0, this.newPoint);
        this.newPoint = new Point();
    }
}