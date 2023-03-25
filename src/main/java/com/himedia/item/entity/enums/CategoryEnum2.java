package com.himedia.item.entity.enums;

public enum CategoryEnum2 {
	KNIT(CategoryEnum.TOP),
	LONGSLEEVES(CategoryEnum.TOP),
	SHORTSLEEVES(CategoryEnum.TOP),
	SLEEVELESS(CategoryEnum.TOP),
	PANTS(CategoryEnum.BOTTOM),
	SHORTS(CategoryEnum.BOTTOM),
	COATPADDING(CategoryEnum.OUTER),
	CARDIGAN(CategoryEnum.OUTER);
	
	private final CategoryEnum parentCategory;

	CategoryEnum2(CategoryEnum parentCategory){
		this.parentCategory = parentCategory;
	}
	
	 public CategoryEnum getParentCategory() {
	        return parentCategory;
	    }
}