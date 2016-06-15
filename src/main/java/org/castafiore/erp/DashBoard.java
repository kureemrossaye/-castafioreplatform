package org.castafiore.erp;

import java.util.ArrayList;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DashBoard {

	private String label;
	private String name;
	private String img;
	private String module;
	private String help;
	private List<DashBoard> children = new ArrayList<DashBoard>();
	private String actions;
	
	

	public List<DashBoard> getChildren() {
		return children;
	}

	public void setChildren(List<DashBoard> children) {
		this.children = children;
	}

	public int size() {
		return children.size();
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

	public boolean contains(Object o) {
		return children.contains(o);
	}

	public Iterator<DashBoard> iterator() {
		return children.iterator();
	}

	public Object[] toArray() {
		return children.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return children.toArray(a);
	}

	public boolean add(DashBoard e) {
		return children.add(e);
	}

	public boolean remove(Object o) {
		return children.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return children.containsAll(c);
	}

	public boolean addAll(Collection<? extends DashBoard> c) {
		return children.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends DashBoard> c) {
		return children.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return children.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return children.retainAll(c);
	}

	public void clear() {
		children.clear();
	}

	public DashBoard get(int index) {
		return children.get(index);
	}

	public DashBoard set(int index, DashBoard element) {
		return children.set(index, element);
	}

	public void add(int index, DashBoard element) {
		children.add(index, element);
	}

	public int indexOf(Object o) {
		return children.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return children.lastIndexOf(o);
	}

	public ListIterator<DashBoard> listIterator() {
		return children.listIterator();
	}

	public ListIterator<DashBoard> listIterator(int index) {
		return children.listIterator(index);
	}

	public List<DashBoard> subList(int fromIndex, int toIndex) {
		return children.subList(fromIndex, toIndex);
	}

	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	
	
	
	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	
	public String toString(){
		return label;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
