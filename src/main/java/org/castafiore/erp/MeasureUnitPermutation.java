package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"convert", "ratio"})
@Search(defaultProperties={})
@DropDown(columns={"convert", "ratio"},defaultSearch={})
public class MeasureUnitPermutation extends BaseErpModel{
	
	@Column(caption="Covert to")
	@Field(caption="Convert to",type=FieldType.lookup,lookupModel=MeasureUnit.class)
	private MeasureUnit convert;
	
	@Column(caption="Fraction")
	@Field(caption="Fraction",type=FieldType.Float)
	private BigDecimal ratio;

	public MeasureUnit getConvert() {
		return convert;
	}

	public void setConvert(MeasureUnit convert) {
		this.convert = convert;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	
	
	
	

}
