package org.castafiore.erp.ui.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.erp.annotations.Workflow;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.SimpleSelectControl;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

public class EntityFieldDecorator implements FieldDecorator{

	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		Map<String, ClassMetadata> metadata = SpringUtil.getBeanOfType(SessionFactory.class).getAllClassMetadata();
		
		List<KeyValuePair> kvs = new ArrayList<KeyValuePair>(metadata.size());
		
		for(String key : metadata.keySet()){
			ClassMetadata meta = metadata.get(key);
			if(ReflectionUtils.getAnnotation(meta.getMappedClass(),Workflow.class) !=null){
				String value = meta.getMappedClass().getSimpleName();
				String ent = meta.getEntityName();
				kvs.add(new SimpleKeyValuePair(ent,value));
			}
			
		}
		
		DefaultDataModel<KeyValuePair> model = new DefaultDataModel<KeyValuePair>(kvs);
		
		
		((SimpleSelectControl<KeyValuePair>)field).setModel(model);
		
		
	}

}
