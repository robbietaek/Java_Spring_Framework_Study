package logic;

public class ItemSet {
	private Item item;
	private Integer quantity;
	public ItemSet(Item item, Integer quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
