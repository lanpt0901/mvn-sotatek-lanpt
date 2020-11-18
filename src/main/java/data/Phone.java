package data;

public class Phone {
	private String website;
	private String name;
	private Integer wholePrice;
	private Integer fractionPrice;
	private String priceUnit;
	private String link;

	public Phone() {

	}

	public Phone(String website, String name, Integer wholePrice, Integer fractionPrice, String link) {
		this.website = website;
		this.name = name;
		this.wholePrice = wholePrice;
		this.fractionPrice = fractionPrice;
		this.link = link;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWholePrice() {
		return wholePrice;
	}

	public void setWholePrice(Integer wholePrice) {
		this.wholePrice = wholePrice;
	}

	public Integer getFractionPrice() {
		return fractionPrice;
	}

	public void setFractionPrice(Integer fractionPrice) {
		this.fractionPrice = fractionPrice;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
