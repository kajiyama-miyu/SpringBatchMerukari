package com.example;

import org.springframework.batch.item.ItemProcessor;

import com.example.Domain.Original;

public class MerukariItemProcessor implements ItemProcessor<Original, Original>{

	@Override
	public Original process(final Original original) throws Exception {
		final int id = original.getId();
		final String name = original.getName();
		final int condition = original.getConditionId();
		final String categoryName = original.getCategoryName();
		 String brand = null;
		if(original.getBrand() == "") {
			 brand = null;
		} else {
			 brand = original.getBrand();
		}
		final Double price = original.getPrice();
		final int shipping = original.getShipping();
		final String description = original.getDescription();
		
		final Original addOriginalItem = new Original(id, name, condition, categoryName, brand, price, shipping, description);
		
		return addOriginalItem;
	}
}
