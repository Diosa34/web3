//package com.github.diosa.web3;
//
//
//import javax.annotation.PostConstruct;
//
//import javax.annotation.Resource;
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.*;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//@ManagedBean
//@ApplicationScoped
//public class ResultsBean implements Serializable {
//    private List<Point> points;
//    private Point newPoint = new Point();
//    private Point lastPoint;
//
//    private final String PERSISTENCE_UNIT_NAME = "orbis";
//    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
//    private EntityManager entityManager;
//
//    public ResultsBean() {
//        this.points = new ArrayList<>();
//    }
//
//    public ResultsBean(ArrayList<Point> points) {
//        this.points = points;
//    }
//
//    @Resource
//    private UserTransaction userTransaction;
//
//    public List<Point> loadPointsFromDB() {
//        return entityManager.createQuery("SELECT e FROM Point e").getResultList();
//    }
//
//    public synchronized void addPointsToDB(Point point) throws Exception {
//        userTransaction.begin();
//        entityManager.persist(point);
//        System.out.println(point.toJSON());
//        userTransaction.commit();
//    }
//
//    public synchronized void clearDB(Point point) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
//        userTransaction.begin();
//        entityManager.remove(entityManager.merge(point));
//        userTransaction.commit();
//    }
//
//    @PostConstruct
//    private void loadPoints() {
//        points = loadPointsFromDB();
//    }
//
//    public synchronized void addPoint() {
//        newPoint.setCurrentTime(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(Calendar.getInstance().getTime()));
//        if (Validator.isValidDate(newPoint)) {
//            try {
//                addPointsToDB(newPoint);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            points.add(0, newPoint);
//            lastPoint = newPoint;
//            newPoint = new Point();
//        } else {
//            newPoint.setHit("Некорректно введены данные!");
//        }
//    }
//
//    public synchronized void clear() {
//        try {
//            for (Point p: points) {
//                clearDB(p);
//            }
//            points.clear();
//            newPoint.setHit("Таблица успешно очищена!");
//        } catch (SystemException | NotSupportedException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
//            e.printStackTrace();
//        }
//        points.clear();
//    }
//
//}