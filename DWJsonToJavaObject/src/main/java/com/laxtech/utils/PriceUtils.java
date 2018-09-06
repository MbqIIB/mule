package com.laxtech.utils;

import java.util.Map;

import com.laxtech.entity.Car;

public class PriceUtils {
	
	public static Car increasePrice(Car car, int incrementPercent) {
		try {
			double increment = (double)incrementPercent/ 100;
			car.setPrice((int)((1 + increment) * car.getPrice()));
			car.getGps().setPrice((int)((1.0 + increment) * car.getGps().getPrice()));
			car.getCamera().setPrice((int)((1.0 + increment) * car.getCamera().getPrice()));
			for (Map<String, Integer> m: car.getInteriorAccessory()) {
				System.out.println("List loop: " + m);
				for(Map.Entry<String, Integer> e: m.entrySet()) {
					if (e.getKey().equalsIgnoreCase("Price")) {			
						e.setValue((int)(e.getValue() * (1.0 + increment)));
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return car;
	}

}
