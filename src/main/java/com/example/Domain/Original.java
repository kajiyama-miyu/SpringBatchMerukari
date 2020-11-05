package com.example.Domain;

public class Original {

	private Integer id;
	private String name;
	private Integer conditionId;
	private String categoryName;
	private String brand;
	private Double price;
	private Integer shipping;
	private String description;

	public Original() {

	}

	public Original(Integer id, String name, Integer conditionId, String categoryName, String brand, Double price,
			Integer shipping, String description) {
		super();
		this.id = id;
		this.name = name;
		this.conditionId = conditionId;
		this.categoryName = categoryName;
		this.brand = brand;
		this.price = price;
		this.shipping = shipping;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
