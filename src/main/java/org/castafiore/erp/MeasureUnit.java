package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.ArrayList;
import java.util.List;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"measureUnit", "decimalNumber"})
@Forms(name="MeasureUnit",label="MeasureUnit",forms={
		@Form(layout="6:6;12",label="Measure unit",name="Measure unit",groups={
				@Group(layoutData="0:0",label="Main",name="Main",fields={"measureUnit", "decimalNumber"}),
				@Group(layoutData="0:1",label="Permutations",name="Permutations",displayType=DisplayType.TABLE,fields={"permutations"}),
		})
})
@Search(defaultProperties={})
@DropDown(columns={"measureUnit","decimalNumber"},defaultSearch={""})
public class MeasureUnit extends BaseErpModel{
	
	
	@Field(caption="Measure unit")
	@Column(caption="Measure unit")
	private String measureUnit;
	
	@Field(caption="Decimal numbers",type=FieldType.Int)
	@Column(caption="Decimal Nbr.")
	private BigInteger decimalNumber;
	
	@Field(caption="Permitations",type=FieldType.table,lookupModel=MeasureUnitPermutation.class)
	private List<MeasureUnitPermutation> permutations = new ArrayList<MeasureUnitPermutation>();

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	
	
	public BigInteger getDecimalNumber() {
		return decimalNumber;
	}

	public void setDecimalNumber(BigInteger decimalNumber) {
		this.decimalNumber = decimalNumber;
	}

	public String toString(){
		return measureUnit;
	}

	public List<MeasureUnitPermutation> getPermutations() {
		return permutations;
	}

	public void setPermutations(List<MeasureUnitPermutation> permutations) {
		this.permutations = permutations;
	}
	
}
