package com.github.diosa.web3;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.transaction.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ResultsBean implements Serializable {

    private final String PERSISTENCE_UNIT_NAME = "persistenceUnitName";

    private EntityTransaction transaction;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ResultsBean() {
        connection();
        this.resultPointsList = new ArrayList<>();
    }

    public ResultsBean(ArrayList<Point> points) {
        this.resultPointsList = points;
    }

    private void connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnitName");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

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

    public List<Point> loadPointsFromDB() {
        List<Point> points;
        try {
            transaction.begin();
            Query query = entityManager.createQuery("SELECT e FROM Point e");
            points = query.getResultList();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
        return points;
    }

    public void addPointsToDB(Point point){
        try {
            transaction.begin();
            entityManager.persist(point);
            transaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @PostConstruct
    private void loadPoints() {
        this.resultPointsList = loadPointsFromDB();
    }

    public void addPoint() {
        long startTime = System.nanoTime();
        this.newPoint.checkHidden();
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