/**
 * 
 */
package com.laxtech.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author sanjeev
 *
 */
public class AccumulatorUtil {

	/**
	 * 
	 * 
	 */
	private String whereCondition;
	
	public AccumulatorUtil() {		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	public static List accumulate(ArrayList<String> indexList, HashMap<String, String> dataSource) {
		System.out.println("index: " + indexList);
		System.out.println("dataSource:" + dataSource);
		List<String> data = new ArrayList<String>();
		for (String i: indexList) {
			data.add(dataSource.get(i));
		}
		return data;

	}

}
