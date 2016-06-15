package org.castafiore.erp.utils;

import java.math.BigInteger;
import java.util.List;

import org.castafiore.erp.Sequencer;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class SequenceUtil {
	
	
	
	
	public static BigInteger lockNextSequence(String format){
		Session session = SpringUtil.getBeanOfType(Dao.class).getSession();
		List l = session.createCriteria(Sequencer.class).add(Restrictions.eq("entity", format)).list();
		if(l.size() > 0){
			Sequencer s = ((Sequencer)l.get(0));
			BigInteger current = s.getCurrent();
			if(current == null){
				current = BigInteger.ZERO;
			}
			BigInteger step = s.getStep();
			if(step == null){
				step=BigInteger.ONE;
			}
			current = current.add(step);
			s.setCurrent(current);
			session.update(s);
			return current;
		}else{
			Sequencer s = new Sequencer();
			s.setCurrent(BigInteger.ONE);
			s.setEntity(format);
			s.setStep(BigInteger.ONE);
			session.save(s);
			return BigInteger.ONE;
		}
	}

}
