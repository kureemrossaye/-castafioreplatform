package org.castafiore.erp.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.grid.QueryParams;
import org.castafiore.erp.ui.grid.Restriction;
import org.castafiore.persistence.Dao;
import org.castafiore.utils.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.PageSize;

public class SearchEngine {
	
	private Dao dao;
	
	
	public Dao getDao() {
		return dao;
	}


	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	public List<String> suggestField(String term,Class<? extends BaseErpModel> object, String field){
		
		
		String hql= "select "+field+" from "+object.getName()+"  where lower("+field+") like :term" ;
		
		return dao.getReadOnlySession().createQuery(hql).setParameter("term", term.trim().toLowerCase() + "%").list();
		
		
	}
	
	protected Criteria getCriteria (Class<? extends BaseErpModel> object, QueryParams params){
		
		
		Session session = dao.getReadOnlySession();
		Criteria crit = session.createCriteria(object);
		
		if(params.getShowAll()){
			return crit;
		}
		
		for (Restriction r :  params.getRestrictions()){
			String field = r.getField();
			if(r.getParams().size() == 1){
				//like
				if(r.getParams().containsKey("like")){
					crit.add(Restrictions.ilike(field, r.getParams().get("like") + "%"));
				}else{
					List l =(List)r.getParams().get("in");
					if(l.size() > 0)
						crit.add(Restrictions.in(field, l));
				}
			}else{
				crit.add(Restrictions.ge(field, r.getParams().get("from"))).add(Restrictions.le(field, r.getParams().get("to")));
			}
			
			
		}
		
		List<Criterion> predicates = new ArrayList<Criterion>();
		
		if(params.getStatus().size() > 0){
			crit.add(Restrictions.in("status", params.getStatus()));
		}
		
		if(StringUtil.isNotEmpty(params.getFullTextSearchTerm())){
		
	
			for(Field f : object.getDeclaredFields()){
					if(f.getType().equals(String.class)){
						
						predicates.add(Restrictions.ilike(f.getName(), params.getFullTextSearchTerm()+"%"));
						
					}
			}
		}
		
		
		
		if(predicates.size() > 1){
			crit.add(Restrictions.or(predicates.toArray(new Criterion[predicates.size()])));
		}else if(predicates.size() == 1){
			crit.add(predicates.get(0));
		}
		
		return crit;
		
	}


	
	public SearchResponse search(Class<? extends BaseErpModel> object, QueryParams params){
		
		
		
		int count = ((Long)getCriteria(object, params).setProjection(Projections.rowCount()).uniqueResult()).intValue();
		
		List data = getCriteria(object, params).setFirstResult(params.getPage()*params.getPagSize()).setMaxResults(params.getPagSize()).list();
		
		SearchResponse r = new SearchResponse();
		r.setCount(count);
		r.setData(data);
		return r;
	}
	
	


}
