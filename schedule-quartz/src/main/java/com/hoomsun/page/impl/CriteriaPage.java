package com.hoomsun.page.impl;

import com.hoomsun.page.Page;
import com.hoomsun.page.PageRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

/**
 * 
 */

@SuppressWarnings("rawtypes")
@Deprecated
public class CriteriaPage extends Page{
    private static final long serialVersionUID = -5110334895915944394L;

    @SuppressWarnings("unchecked")
    public CriteriaPage(Criteria criteria,int pageNumber, int pageSize) {
		super(pageNumber,pageSize,queryTotalCount(criteria));
		result = criteria
			.setFirstResult(getFirstResult())
			.setMaxResults(this.pageSize).list();
	}
	
	public CriteriaPage(Criteria criteria,PageRequest p) {
		this(criteria,p.getPageNumber(),p.getPageSize());
	}

	private static int queryTotalCount(Criteria criteria) {
		return ((Integer)(criteria.setProjection(Projections.rowCount()).uniqueResult())).intValue();
	}

}
