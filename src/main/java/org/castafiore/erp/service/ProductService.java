package org.castafiore.erp.service;

import java.util.List;

import org.castafiore.erp.Item;

public interface ProductService {
	
	public List<Item> getProducts(int page, int pageSize);
	
	public List<Item> getProducts(String term, int page, int pageSize);
	
	public Item getProduct(Integer id);
	

}
