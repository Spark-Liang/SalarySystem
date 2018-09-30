package com.lzh.salarysystem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.lzh.salarysystem.entity.WorkRecord;

@Repository
public class WorkRecordRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	
	public WorkRecord findCurrentWorkRecord(Integer empID) {
		Specification<WorkRecord> specification = (root,query,cb) ->{
			Predicate predicate = cb.equal(root.get("info.employee.id"), empID);
			query.where(predicate);
			query.orderBy(new OrderImpl(root.get("info.startTime"), false));
			return query.getRestriction();
		};
		
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<WorkRecord> criteriaQuery = cb.createQuery(WorkRecord.class);
//		specification.toPredicate(criteriaQuery.from(WorkRecord.class), criteriaQuery, cb);
//		List<WorkRecord> results = em.createQuery(criteriaQuery).getResultList();
//		if(CollectionUtils.isEmpty(results)) {
//			return null;
//		}else {
//			return results.get(0);
//		}
		return ((WorkRecordRepository)this).findOne(specification);
	}

}
