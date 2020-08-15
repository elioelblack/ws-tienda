package com.tienda.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.omnifaces.util.Messages;

public abstract class Operaciones<T> {

    private static final long serialVersionUID = -169258812805375171L;

    private final Class<T> entityClass;

    public Operaciones(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @param <T>
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T crear(T entity) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            session.save(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e.getMessage());
            System.out.println("e " + e);
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T crearOEditar(T entity) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            session.saveOrUpdate(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    public List<T> crearOEditar(List<T> entities) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            entities.forEach((entity) -> {
                session.saveOrUpdate(entity);
            });
            tx.commit();
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

        } finally {
            HibernateManager.closeSession(session);
        }
        return entities;
    }

    /**
     *
     * @param <T>
     * @param session
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T crear(Session session, T entity) throws Exception {
        try {
            session.save(entity);
            return entity;
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }

            return null;
        }
    }

    /**
     *
     * @param <T>
     * @param session
     * @param entityList
     * @return
     * @throws Exception
     */
    public <T> List<T> crear(Session session, List<T> entityList) throws Exception {
        List<T> list = new ArrayList<>();
        for (T t : entityList) {
            list.add(this.crear(session, t));
        }
        return list;
    }

    /**
     *
     * @param <T>
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T editar(T entity) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            session.update(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    public <T> T eliminar(T entity) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            session.delete(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param session
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T editar(Session session, T entity) throws Exception {
        session.update(entity);
        return entity;
    }

    /**
     *
     * @param <T>
     * @param session
     * @param entityList
     * @return
     * @throws Exception
     */
    public <T> List<T> editar(Session session, List<T> entityList) throws Exception {
        List<T> list = new ArrayList<>();
        for (T t : entityList) {
            list.add(this.editar(session, t));
        }
        return list;
    }

    /**
     *
     * @param <T>
     * @param session
     * @param entity
     * @return
     * @throws Exception
     */
    public <T> T eliminar(Session session, T entity) throws Exception {
        try {
            session.delete(entity);
            return entity;
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }

            return null;
        }
    }

    /**
     * @param <T>
     * @param session
     * @param entityList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> eliminar(Session session, List<T> entityList) throws Exception {
        List<T> list = new ArrayList<>();
        for (T t : entityList) {
            list.add(this.eliminar(session, t));
        }
        return list;
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultarPor(Session session, String hql, String parameterName, int parameterValue) {
        T entity = (T) session.createQuery(hql)
                .setParameter(parameterName, parameterValue)
                .setMaxResults(1)
                .setFetchSize(1)
                .uniqueResult();
        return entity;
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultarPor(Session session, String hql, String parameterName, String parameterValue) {
        T entity = (T) session.createQuery(hql)
                .setParameter(parameterName, parameterValue)
                .setMaxResults(1)
                .setFetchSize(1)
                .uniqueResult();
        return entity;
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultarPor(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            //select d from BanBanco d where  d.banId =:banId 
            entity = (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T consultarPor(String hql, String parameterName, int parameterValue, String parameterName1, int parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            //select d from BanBanco d where  d.banId =:banId 
            entity = (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .setParameter(parameterName1, parameterValue1)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultarPor(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            //SELECT p FROM ParParametro p WHERE p.parNombre = :totalSocio 
            entity = (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            result = session.createQuery(hql, Boolean.class)
                    .setParameter(parameterName, parameterValue)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            result = session.createQuery(hql, Boolean.class)
                    .setParameter(parameterName, parameterValue)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param session
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe(Session session, String hql, String parameterName, int parameterValue) {
        boolean result = session.createQuery(hql, Boolean.class)
                .setParameter(parameterName, parameterValue)
                .setMaxResults(1)
                .setFetchSize(1)
                .uniqueResult();
        return result;
    }

    /**
     *
     * @param session
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe(Session session, String hql, String parameterName, String parameterValue) {
        boolean result = session.createQuery(hql, Boolean.class)
                .setParameter(parameterName, parameterValue)
                .setMaxResults(1)
                .setFetchSize(1)
                .uniqueResult();
        return result;
    }

    /**
     *
     * @param <T>
     * @param hql
     * @return
     */
    
    /*
    select t from BecBecario t "
                + " inner join fetch t.becIdCon convocatoria "
                + " inner join fetch t.becIdCar carrera "
                + " inner join fetch t.becIdAmb ambito "
                + " inner join fetch t.becIdDep depto "
                + " inner join fetch t.becIdMun municipio "
                + " left join fetch t.becIdEsc acd "
                + " left join fetch t.becIdEsa arc "
                + "INNER JOIN FETCH t.becIdBan ban "
                + " inner join fetch t.becIdUni universidad WHERE t.becEstado=1
    */
    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodos(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createQuery(hql).list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {
                Messages.addGlobalError("Error en estructura de consulta", e.getMessage());
            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPorNamedQuery(String namedQuery) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNamedQuery(namedQuery, this.entityClass).list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPorNamedQuery(String namedQuery, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNamedQuery(namedQuery, this.entityClass)
                    .setParameter(parameterName, parameterValue)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    public <T> List<T> consultarTodos(Session session, String hql) {
        @SuppressWarnings("unchecked")
        List<T> entitiesFound = (List<T>) (T) session.createQuery(hql).list();
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPor(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            //SELECT dp FROM DplDetallePlanilla dp INNER JOIN FETCH dp.dplIdBec b INNER JOIN FETCH b.becIdDep dep INNER JOIN FETCH b.becIdMun muni INNER JOIN FETCH dp.dplIdPla pl WHERE pl.plaId=:pla_id ORDER BY dp.dplId DESC
            //SELECT a FROM AneAnexoEntrevista a INNER JOIN FETCH a.aniIdEnt e WHERE e.entId=:id_entrevista'
            //select d from BanBanco d where d.banEstado=:estado
            //SELECT pp FROM PprAprobacionPresupuesto pp INNER JOIN FETCH pp.pprIdPre pre WHERE pre.preId=:id_presu
            //SELECT pr FROM PrrPresupuestoRubro pr INNER JOIN FETCH pr.prrIdRub r INNER JOIN FETCH pr.prrIdPre pre WHERE pre.preId

            //select t from AsmAsignacionMaxima t inner join fetch t.asmIdAmb ambito inner join fetch t.asmIdCon convocatoria inner join fetch t.asmIdNiv nivel where convocatoria.conId=:conId
            //SELECT din FROM DinDistribucionNivel din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon conv WHERE conv.conId =:id_convocatoria
            //"SELECT din FROM DinDistribucionNivel din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon conv WHERE conv.conId =:id_convocatoria"
           //SELECT din FROM DinDistribucionNivel din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon conv WHERE conv.conId =:id_distribucion
            //SELECT dia FROM DiaDistribucionAreaEstudio dia INNER JOIN FETCH dia.diaIdAre are INNER JOIN FETCH dia.diaIdDin din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon c WHERE c.conId=:
            //SELECT m FROM DxeDocumentoxempleado dxe INNER JOIN dxe.dxeIdEmp m INNER JOIN dxe.dxeIdTde tip WHERE tip.tdeId=:idDoc AND m 
            entitiesFound = (List<T>) (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPor(String hql, String parameterName, Date parameterValue, String parameterName2, Date parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            //SELECT dp FROM DplDetallePlanilla dp INNER JOIN FETCH dp.dplIdBec b INNER JOIN FETCH b.becIdDep dep INNER JOIN FETCH b.becIdMun muni INNER JOIN FETCH dp.dplIdPla pl WHERE pl.plaId=:pla_id ORDER BY dp.dplId DESC
            entitiesFound = (List<T>) (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .setParameter(parameterName2, parameterValue2)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPor(String hql, String parameterName, Date parameterValue, String parameterName2, Date parameterValue2, String parameterName3, String parameterValue3) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            //SELECT dp FROM DplDetallePlanilla dp INNER JOIN FETCH dp.dplIdBec b INNER JOIN FETCH b.becIdDep dep INNER JOIN FETCH b.becIdMun muni INNER JOIN FETCH dp.dplIdPla pl WHERE pl.plaId=:pla_id ORDER BY dp.dplId DESC
            entitiesFound = (List<T>) (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .setParameter(parameterName2, parameterValue2)
                    .setParameter(parameterName3, parameterValue3)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPor(String hql, String parameterName, Date parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPor(Session session, String hql, String parameterName, int parameterValue) {
        List<T> entitiesFound = (List<T>) (T) session.createQuery(hql)
                .setParameter(parameterName, parameterValue)
                .list();
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosNativeQuery(String nativeSQL) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNativeQuery(nativeSQL).list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosNativeQuery(Session session, String nativeSQL) {
        return (List<T>) (T) session.createNativeQuery(nativeSQL).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPorNativeQuery(String nativeSQL, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNativeQuery(nativeSQL)
                    .setParameter(parameterName, parameterValue)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    /**
     *
     * @param <T>
     * @param nativeSQL
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @param parameterName2
     * @param parameterValue2
     * @return
     */
    public <T> List<T> consultarTodosPorNativeQuery(String nativeSQL, String parameterName, Date parameterValue, String parameterName1, Date parameterValue1, String parameterName2, String parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNativeQuery(nativeSQL)
                    .setParameter(parameterName, parameterValue)
                    .setParameter(parameterName1, parameterValue1)
                    .setParameter(parameterName2, parameterValue2)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {
            } else {
            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    /**
     *
     * @param <T>
     * @param nativeSQL
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @return
     */
    public <T> List<T> consultarTodosPorNativeQuery(String nativeSQL, String parameterName, Date parameterValue, String parameterName1, Date parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> entitiesFound = new ArrayList<>();
        try {
            entitiesFound = (List<T>) (T) session.createNativeQuery(nativeSQL)
                    .setParameter(parameterName, parameterValue)
                    .setParameter(parameterName1, parameterValue1)
                    .list();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {
            } else {
            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return entitiesFound;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> consultarTodosPorNativeQuery(Session session, String nativeSQL, String parameterName, int parameterValue) {
        return (List<T>) (T) session.createNativeQuery(nativeSQL).setParameter(parameterName, parameterValue).list();
    }

    /**
     *
     * @param hql
     * @return
     */
    public Long contarPor(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        Long qty = 0L;
        try {
            qty = (Long) session.createQuery(hql)
                    .setMaxResults(1)
                    .setFetchSize(1)
                    .uniqueResult();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return qty;
    }

    public Long contarPor(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        Long qty = 0L;
        try {
            qty = (Long) session.createQuery(hql)
                    .setParameter(parameterName, parameterValue)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return qty;
    }

    /**
     *
     * @param session
     * @param hql
     * @return
     */
    public Long contarPor(Session session, String hql) {
        Long qty = (Long) session.createQuery(hql)
                .setMaxResults(1)
                .setFetchSize(1)
                .uniqueResult();
        return qty;
    }

    public Long contarPor(Session session, String hql, String parameterName, int parameterValue) {
        Long qty = (Long) session.createQuery(hql)
                .setParameter(parameterName, parameterValue)
                .getSingleResult();
        return qty;
    }

    /*Funciones a conveniencia por darv*/
    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultar_ultimo_registro_agregado(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            //SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b where b.becId =:becid ORDER BY p.preId DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            entity = (T) query.getSingleResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultar_ultimo_registro_agregado_string(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            //SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b where b.becId =:becid ORDER BY p.preId DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            entity = (T) query.getSingleResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> Metodo_Buscar(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            //query.setMaxResults(1).getResultList();
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> traer_lista_general(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {

            Query query = session.createQuery(hql);
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> traer_lista_con_id(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean eliminar_en_cascada_registro(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            // result = query.getResultList().size() > 0;
            //delete
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.executeUpdate();
            tx.commit();
            result = true;
            return result;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);
            return false;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> traer_lista_segun_campo(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> consultarPorParametro(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    public <T> T eliminar_completo(T entity) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        try {
            session.remove(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param consulta
     * @return
     */
    public List<T> consulta_general(String consulta) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> lista = null;
        try {
            Query query = session.createNativeQuery(consulta);
            lista = query.getResultList();
            tx.commit();
            return lista;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    public T consulta_general_entidad(String consulta) throws Exception {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            Query query = session.createNativeQuery(consulta, entityClass);
            Object q = query.getSingleResult();
            tx.commit();
            return (T) q;
        } catch (Exception e) {
            HibernateManager.transactionRollBack(tx);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public List consultar_native_query(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List lista = null;
        try {
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            lista = query.getResultList();
            tx.commit();
            return lista;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    @SuppressWarnings("unchecked")
    public List consultar_native_query(String hql, String parameterName, Date parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List lista = null;
        try {
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            lista = query.getResultList();
            tx.commit();
            return lista;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultar_native_query_id(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            entity = (T) query.getResultList();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultar_primer_registro(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity = null;
        try {
            Query query = session.createQuery(hql);
            query.setMaxResults(1).getResultList();
            entity = (T) query.getSingleResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T consultar_primer_registro(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        T entity;
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            entity = (T) query.getSingleResult();
            tx.commit();
            return entity;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe_en_consulta(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b WHERE b.becId=:id_bec ORDER BY p.preId DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @return
     */
    public boolean consultar_si_hay_datos_en_la_tabla(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b WHERE b.becId=:id_bec ORDER BY p.preId DESC
            Query query = session.createQuery(hql);
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public String obtener_campo(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        String result = "";
        Object valor_final = "";
        try {
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            valor_final = query.getSingleResult();
            if (valor_final == null) {
                result = "";
            } else {
                result = String.valueOf(valor_final.toString());
            }
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {
            } else {
            }
            HibernateManager.transactionRollBack(tx);
        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public Integer contar_tamano_de_lista(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        Integer result = 0;
        try {
//SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b WHERE b.becId=:id_bec ORDER BY p.preId DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size();
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public Integer contar_valores(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        Integer result = 0;
        Object valor_final = 0;
        try {
//SELECT COUNT(con_codigo) as valor  FROM con_convocatoria inner JOIN sub_subprograma on sub_id = con_id_sub where sub_id =1;
            //no hace la consulta
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            valor_final = query.getSingleResult();
            if (valor_final == null) {
                result = 0;
            } else {
                result = Integer.valueOf(valor_final.toString());
            }
            // result = conteo != null ? conteo.intValue() : null;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;

        /*
           Long qty = (Long) session.createQuery(hql)
                .setParameter(parameterName, parameterValue)
                .getSingleResult();
        return qty;
         */
    }

    /**
     *
     * @param hql
     * @return
     */
    public Integer contar_valores_nativa_sin_parametros(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        Integer result = 0;
        Object valor_final = 0;
        try {
//SELECT COUNT(con_codigo) as valor  FROM con_convocatoria inner JOIN sub_subprograma on sub_id = con_id_sub where sub_id =1;
            //no hace la consulta
            Query query = session.createNativeQuery(hql);
            valor_final = query.getSingleResult();
            if (valor_final == null) {
                result = 0;
            } else {
                result = Integer.valueOf(valor_final.toString());
            }
            // result = conteo != null ? conteo.intValue() : null;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;

    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean existe_en_consulta_string(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT c FROM ConConvocatoria c  WHERE c.conNombre=:nombre_convo ORDER BY c.conCodigo DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {
            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @param parameterName2
     * @param parameterValue2
     * @return
     */
    public boolean verificar_existe_campo_id(String hql, String parameterName, String parameterValue, String parameterName1, int parameterValue1, String parameterName2, int parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setParameter(parameterName2, parameterValue2);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @return
     */
    public boolean verificar_existe_campo_id(String hql, String parameterName, String parameterValue, String parameterName1, int parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=:id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @return
     */
    public boolean verificar_existe_campo_id_entero(String hql, String parameterName, int parameterValue, String parameterName1, int parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=:id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @return
     */
    public boolean verificar_existe_campo_id_Integer(String hql, String parameterName, Integer parameterValue, String parameterName1, int parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    public boolean verificar_existe_campo_id_con_enteros(String hql, String parameterName, Integer parameterValue, String parameterName1, Integer parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @return
     */
    public boolean verificar_existe_campo_id_enteros(String hql, String parameterName, Integer parameterValue, String parameterName1, Integer parameterValue1) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=:id
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName1
     * @param parameterValue1
     * @param parameterName2
     * @param parameterValue2
     * @return
     */
    public boolean verificar_existe_dos_campo_id_enteros(String hql, String parameterName, Integer parameterValue, String parameterName1, Integer parameterValue1, String parameterName2, Integer parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {

            //SELECT n FROM NotNotas n INNER JOIN n.notIdBec b WHERE n.notCiclo=:ciclo and b.becId=:id"
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName1, parameterValue1);
            query.setParameter(parameterName2, parameterValue2);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean verficar_existe_campo(String hql, String parameterName, String parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=0
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=0
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=0
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean verficar_existe_campo_Integer(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=0
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=0
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=0
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     * @return
     */
    public boolean existe_en_consulta_dos_string(String hql, String parameterName, String parameterValue, String parameterName2, Integer parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT c FROM ConConvocatoria c  WHERE c.conNombre=:nombre_convo ORDER BY c.conCodigo DESC
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {
            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     *
     */
    public void eliminar_en_cascada_registro_final(String hql, String parameterName, int parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            // result = query.getResultList().size() > 0;
            //delete
            
            //DELETE FROM DiaDistribucionAreaEstudio dia WHERE dia.diaIdDin IN (SELECT din.dinId FROM DinDistribucionNivel din WHERE din.dinIdDis.disId
           /*
            DELETE FROM DinDistribucionNivel din WHERE din.dinIdDis IN (SELECT dis.disId FROM DisDistribucionConvocatoria dis WHERE dis.disIdCon.conId=:id_conv)
            */
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     *
     */
    public void actualizar_un_campo_con_el_id(String hql, String parameterName, Integer parameterValue, String parameterName2, BigDecimal parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            // result = query.getResultList().size() > 0;
            //delete
            //UPDATE PrrPresupuestoRubro prr SET prr.prrSaldoDisponible=:valor_total WHERE prr.prrId=:prr_id
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     * @param parameterName3
     * @param parameterValue3
     *
     */
    public void acualizar_dos_campos_segun_id(String hql, String parameterName, Integer parameterValue, String parameterName2, BigDecimal parameterValue2, String parameterName3, BigDecimal parameterValue3) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            // result = query.getResultList().size() > 0;
            //delete
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.setParameter(parameterName3, parameterValue3);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     *
     */
    public void actualizar_un_campo_integer_con_el_id(String hql, String parameterName, Integer parameterValue, String parameterName2, Integer parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     * @return
     */
    public boolean existe_en_consulta_dos_parametros(String hql, String parameterName, int parameterValue, String parameterName2, int parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b WHERE b.becId=:id_bec ORDER BY p.preId DESC
//SELECT d FROM DisDistribucionConvocatoria d INNER JOIN FETCH d.disIdAmb amb INNER JOIN FETCH d.disIdCon c WHERE amb.ambId =:id_amb AND c.conId=:id_conv ORDER BY d.disId DESC
            //SELECT din FROM DinDistribucionNivel din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon c  WHERE niv.nivId=:id_nivel AND c.conId=:id_conv ORDER BY din.dinId DESC         
//SELECT d FROM DisDistribucionConvocatoria d INNER JOIN FETCH d.disIdAmb amb INNER JOIN FETCH d.disIdCon c WHERE amb.ambId =:id_amb AND c.conId=:id_distri ORDER BY d.disId DESC
           
//SELECT d FROM DisDistribucionConvocatoria d INNER JOIN FETCH d.disIdAmb amb INNER JOIN FETCH d.disIdCon c WHERE amb.ambId =:id_amb AND d.disId:id_distri ORDER BY amb.ambNombre DESC
//SELECT dia FROM DiaDistribucionAreaEstudio dia INNER JOIN FETCH dia.diaIdAre are INNER JOIN FETCH dia.diaIdDin din INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon con WHERE are.areId=:id_area AND con.conId=:id_conv ORDER BY dia.diaId DESC
Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     *     * @param parameterName3
     * @param parameterValue3
     * @return
     */
    public boolean existe_en_consulta_tres_parametros(String hql, String parameterName, int parameterValue, String parameterName2, int parameterValue2, String parameterName3, int parameterValue3) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
//SELECT p FROM PrePresupuesto p INNER JOIN FETCH p.preIdBec b WHERE b.becId=:id_bec ORDER BY p.preId DESC
//SELECT d FROM DisDistribucionConvocatoria d INNER JOIN FETCH d.disIdAmb amb INNER JOIN FETCH d.disIdCon c WHERE amb.ambId =:id_amb AND c.conId=:id_conv ORDER BY d.disId DESC
            //SELECT din FROM DinDistribucionNivel din INNER JOIN FETCH din.dinIdNiv niv INNER JOIN FETCH din.dinIdDis dis INNER JOIN FETCH dis.disIdCon c  WHERE niv.nivId=:id_nivel AND c.conId=:id_conv ORDER BY din.dinId DESC         
////SELECT d FROM DisDistribucionConvocatoria d INNER JOIN FETCH d.disIdAmb amb INNER JOIN FETCH d.disIdCon c WHERE amb.ambId =:id_amb AND c.conId!=:id_conv ORDER BY d.disId DESC
  
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.setParameter(parameterName3, parameterValue3);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @param parameterName2
     * @param parameterValue2
     * @param parameterName3
     * @param parameterValue3
     * @return
     */
    public boolean existe_en_consulta_dos_parametros(String hql, String parameterName, Date parameterValue, String parameterName2, Date parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    /**
     * @param hql
     * @return
     */
    @SuppressWarnings("unchecked")
    public List consultar_nativa_sin_parametros(String hql) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List lista = null;
        try {
            Query query = session.createNativeQuery(hql);
            lista = query.getResultList();
            tx.commit();
            return lista;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    public boolean verificar_si_existe_en_la_tabla_con_el_id(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        boolean result = false;
        try {
            //select t from IntInstalacionEntrevista t where t.intNombre=:nombre and t.intId!=0
            //SELECT b FROM BanBanco b WHERE b.banNombre=:nombre and b.banId!=0
            Query query = session.createNativeQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setMaxResults(1).getResultList();
            result = query.getResultList().size() > 0;
            tx.commit();
        } catch (Exception e) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            if (e instanceof ConstraintViolationException) {

            } else {

            }
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
        return result;
    }

    public void actualizar_un_campo_con_el_id_Integer(String hql, String parameterName, Integer parameterValue, String parameterName2, Integer parameterValue2) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);

        try {
            // result = query.getResultList().size() > 0;
            //delete
            //actualizar estado
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            query.setParameter(parameterName2, parameterValue2);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

        } finally {
            HibernateManager.closeSession(session);
        }
    }

    /**
     *
     * @param <T>
     * @param hql
     * @param parameterName
     * @param parameterValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> traer_lista_segun_campo_Integer(String hql, String parameterName, Integer parameterValue) {
        Session session = HibernateManager.getSession();
        Transaction tx = session.beginTransaction();
        tx.setTimeout(HibernateManager.TRANSACTION_TIMEOUT);
        List<T> list = new ArrayList<>();
        try {
            Query query = session.createQuery(hql);
            query.setParameter(parameterName, parameterValue);
            list = (List<T>) query.getResultList();
            tx.commit();
            return list;
        } catch (Exception e) {
            System.out.println("e " + e);
            Logger.getLogger(Operaciones.class.getName()).log(Level.WARNING, e.getMessage());
            HibernateManager.transactionRollBack(tx);

            return null;
        } finally {
            HibernateManager.closeSession(session);
        }
    }

}

//capital lo que  sobra de la resta de los pagos 

