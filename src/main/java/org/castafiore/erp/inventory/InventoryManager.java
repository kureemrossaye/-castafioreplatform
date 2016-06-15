package org.castafiore.erp.inventory;

import java.math.BigDecimal;

import org.castafiore.erp.Item;
import org.castafiore.erp.PriceList;
import org.castafiore.persistence.Dao;

public class InventoryManager {

	private Dao dao;

	public BigDecimal getPrice(Item item, PriceList pl) {
		return getPrice(item.getId(), pl.getId());
	}

	public BigDecimal getPrice(Integer itemId, Integer priceListId) {
		String sql = "select Price from PriceItem where Item_Id = :itemId and id in (select Items_Id from PriceList_PriceItem where PriceList_Id = :priceListId)";

		try {
			return (BigDecimal) dao.getReadOnlySession().createSQLQuery(sql).setParameter("itemId", itemId).setParameter("priceListId", priceListId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

}
