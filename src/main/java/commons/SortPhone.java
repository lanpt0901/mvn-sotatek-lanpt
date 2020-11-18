package commons;

import java.util.Comparator;

import data.Phone;

public class SortPhone implements Comparator<Phone> {

	@Override
	public int compare(Phone o1, Phone o2) {
		int price1 = o1.getWholePrice();
		int price2 = o2.getWholePrice();
		if (price1 > price2) {
			return 1;
		} else if (price1 == price2) {
			int fPrice1 = o1.getWholePrice();
			int fPrice2 = o2.getWholePrice();
			if (fPrice1 > fPrice2) {
				return 1;
			} else if(fPrice1 == fPrice2) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
