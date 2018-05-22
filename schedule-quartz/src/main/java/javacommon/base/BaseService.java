package javacommon.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import zhongqiu.javautils.UtilTools;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
@Transactional
public abstract class BaseService<E, PK extends Serializable> {

    protected Log log = LogFactory.getLog(getClass());

    @SuppressWarnings("rawtypes")
    protected abstract EntityDao getEntityDao();

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public E getById(PK id) throws DataAccessException {
        return (E) getEntityDao().getById(id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<E> findAll() throws DataAccessException {
        return getEntityDao().findAll();
    }

    /** 根据id检查是否插入或是更新数据 */
    @SuppressWarnings("unchecked")
    public void saveOrUpdate(E entity) throws DataAccessException {
        getEntityDao().saveOrUpdate(entity);
    }

    /** 插入数据 */
    @SuppressWarnings("unchecked")
    public void save(E entity) throws DataAccessException {
        getEntityDao().save(entity);
    }

    @SuppressWarnings("unchecked")
    public void removeById(PK id) throws DataAccessException {
        getEntityDao().deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public void update(E entity) throws DataAccessException {
        getEntityDao().update(entity);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
        return getEntityDao().isUnique(entity, uniquePropertyNames);
    }

    /*@Transactional(readOnly=true)
    public List<E> findByProperty(String propertyName,Object value){
        return getEntityDao().findByPropertyList(propertyName,value);
    }*/
    @Transactional(readOnly = true)
    public void batDelete(PK[] ids) {
        for (PK id : ids) {
            if (!UtilTools.isNullOrEmpty(id)) {
                this.removeById(id);
            }
        }
    }
}
