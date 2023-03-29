package com.himedia.item.entity.enums;

public enum CategoryEnum2 {
	KNIT(CategoryEnum1.TOP),
	LONGSLEEVES(CategoryEnum1.TOP),
	SHORTSLEEVES(CategoryEnum1.TOP),
	SLEEVELESS(CategoryEnum1.TOP),
	PANTS(CategoryEnum1.BOTTOM),
	SHORTS(CategoryEnum1.BOTTOM),
	COATPADDING(CategoryEnum1.OUTER),
	CARDIGAN(CategoryEnum1.OUTER);
	
	private final CategoryEnum1 parentCategory;
	
	CategoryEnum2(CategoryEnum1 parentCategory){
		this.parentCategory = parentCategory;
	}
	public CategoryEnum1 getParentCategory() {
		return parentCategory;
	}

}
