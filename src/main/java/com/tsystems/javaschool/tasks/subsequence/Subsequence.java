package com.tsystems.javaschool.tasks.subsequence;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Subsequence {
	@SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
		//Проверка, не являются ли списки null
		if(x==null || y==null) {
			throw  new IllegalArgumentException();
		}
		//Проверка, не пустые ли списки
		if(x.size()==0 && y.size()==0 || x.size()==0) {
			return true;			
		}		
		if (y.size()==0) {			
			return false;
		}		
		//Индекс проверяемого элемента для листа х
		int j=0;		
		for (int i=0; i<y.size();i++) {			
			//Если элемент из списка х найден в списке у
			if( y.get(i).equals(x.get(j))) {				
				//Перейти к следующему элементу в списке у
				j++;
			}			
			//Если были найдены все элементы
			if(j==x.size()) {			
			return true;
			}		
		}
	
		//Если не все элементы списка х были надйены в списке у	
		return false;
		
    
    }
	
	
}
