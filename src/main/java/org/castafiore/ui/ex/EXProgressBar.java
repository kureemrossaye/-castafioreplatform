package org.castafiore.ui.ex;

import org.castafiore.ui.Container;

public class EXProgressBar extends EXContainer{

	
	private Container bar = new EXContainer("bar", "div").addClass("progress-bar").setAttribute("role", "progressbar");
	
	
	private Integer min = 0;
	
	private Integer max = 100;
	
	private Integer value = 40;
	
	public EXProgressBar(String name) {
		super(name, "div");
		addClass("progress");
		addChild(bar);
	}
	
	public EXProgressBar setMin(Integer min){
		bar.setAttribute("aria-valuemin", min.toString());
		this.min = min;
		setMin(min).setMax(max);
		return this;
	}
	
	
	public EXProgressBar setMax(Integer max){
		bar.setAttribute("aria-valuemax", max.toString());
		this.max = max;
		return this;
	}
	
	
	public Container getBar() {
		return bar;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	public EXProgressBar setValue(Integer value){
		double percent =((max - min)/(value - min))*100;
		this.value = value;
		
		bar.setStyle("width", percent + "%");
		return this;
		
	}
	
	public Double getPercent(){
		double percent =(max - min)/(value - min);
		return percent;
	}
	
	
	public EXProgressBar setLabel(String label){
		bar.setText("<span class=\"sr-only\">" + label+"</span>");
		return this;
	}

}
