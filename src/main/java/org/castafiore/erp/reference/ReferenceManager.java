package org.castafiore.erp.reference;

import java.math.BigInteger;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.utils.ParameterUtil;
import org.castafiore.erp.utils.SequenceUtil;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;

public class ReferenceManager {

	private static String[] KEYS = new String[] { "dd", "MM", "yy", "hh" };


	public static String getFormat(Class<? extends BaseErpModel> clazz,	IGroup group, BigInteger status) {
		

		if(status != null){
			Workflow workflow = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(clazz);
			State state = null;
			if (workflow != null) {
				for (State s : workflow.getStates()) {
					if (s.getValue().equals(status)) {
						state = s;
						break;
					}
				}
			}
			String referenceConfig = null;
			if (state != null) {
				referenceConfig = state.getReferenceConfig();
			}
	
			if (referenceConfig != null && referenceConfig.length() > 0) {
				return referenceConfig;
			}
		}

		StringBuilder b = new StringBuilder();
		for (char c : clazz.getSimpleName().toCharArray()) {
			String sc = c + "";
			if (sc.toUpperCase().equals(sc)) {
				b.append(sc);
			}
		}
		
		String key = clazz.getSimpleName().toLowerCase() + ".reference.format";

		String format = ParameterUtil.getParam(key, b.toString() + "-#");
		return format;
	}

	public static String lockNextCode(Class<? extends BaseErpModel> clazz,IGroup group, BigInteger status) {
		String format = getFormat(clazz, group, status);

		return nextSequence(format);
	}
	
	public static String nextSequence(String format){
		BigInteger next = SequenceUtil.lockNextSequence(format);
		return format.replace("#", next.intValue() + "");
	}

}
