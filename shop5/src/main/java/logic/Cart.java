package logic;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<ItemSet> itemSetList = new ArrayList<ItemSet>();

	public List<ItemSet> getItemSetList() {
		return itemSetList;
	}

	public void push(ItemSet itemSet) {
		for (int i = 0; i < itemSetList.size(); i++) {
			if (itemSetList.get(i).getItem().getName().equals(itemSet.getItem().getName())) {
				int total = itemSetList.get(i).getQuantity() + itemSet.getQuantity();
				itemSetList.get(i).setQuantity(total);
				return;
			}
		}
		itemSetList.add(itemSet);
	}
	
	public long getTotal() {
		long sum = 0;
		for(ItemSet is : itemSetList) {
			sum += is.getItem().getPrice() * is.getQuantity();
		}
		return sum;
	}
}
