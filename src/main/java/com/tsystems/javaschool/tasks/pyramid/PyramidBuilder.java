package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {
	
	//Число строк для пирамиды
	static int rows;
	//Число столбцов для пирамиды
	static int columns;
	//Подготовительный массив
	static int[][] prePyramid;
	//Массив пирамида
	static int[][] pyramid;
	//Метод определяет размеры пирамиды
	public static void getSize(List<Integer> input)   {
		//Получаем количество чисел в листе
		rows=input.size();				
		for (int i=1; i<input.size();i++) {
			//Количество чисел в каждой строке пирамиды равно i
			//С каждой строкой количество чисел в ней увеличивается на 1
			//Уменьшаем количество чисел в строке на количество чисел в соответствующей строке	
			rows=rows-i;
			//Если чисел было достаточно
			if(rows==0) {
				//Число уровней пирамиды равно i
				rows=i;
				break;
			}
			//Если чисел не хватает для построения пирамиды - бросить исключение
			if(rows<0) {
				throw new CannotBuildPyramidException();
			}
		}	
		//Так как кроме чисел в каждой  строке пирамиды будут еще нули, то длина строки пирамиды = количество чисел в строке + количество нулей
		//Количество нулей в последней строке будет на 1 меньше, чем чисел
		//Количество чисел в последней строке равно количеству строк
		columns=rows+(rows-1);		
	}
	//Этот метод создает массив, где на каждой строке находится соответствующее количество чисел
	public static void preparePyramid(List<Integer> input){
		//Сортировка исходного листа с числами
		Collections.sort(input);
		//Создать подготовительный массив
		prePyramid=new int[rows][rows];
		//Эта переменная увеличивается в зависимости от того, сколько чисел должно быть помещено в строку
		int count=0;
		//Эта переменная используется, чтобы начинать заполнение с начала строчки массива
		int k=0;
		//Заполнение массива
		for (int i=0;i<rows;i++) {			
			//Из исходного листа с числами значения извлекаются после того значения, которое было извлечено на предыдущем шаге
			//И в том количестве, которое соответствует именно этой строке
			for(int j=count; j<=(count+i);j++) {				
				prePyramid[i][k++]=input.get(j);				
			}
			//Увеличить переменную, чтобы дальнейшее считывание было после последнего считанного элемента
			count=count+i+1;
			k=0;		
		}
	}
	//Метод проверяет, нет ли в входном листе пустых значений
	public static void notNull(List<Integer> input)   {
		for(int i=0;i<input.size();i++) {			
			if(input.get(i)==null) {					
						throw new CannotBuildPyramidException();						
			}		
		}
	}	
	public static int [][] buildPyramid(List<Integer> input) {
		//Проверяем входные данные на правильность		
			notNull(input);
			//Получаем размер пирамиды	
			getSize(input);		
			//Подготавливаем массив
			preparePyramid(input);		
			//Создаем массив с пирамидой
			pyramid=new int[rows][columns];
			//Эта переменная используется, чтобы начинать считывание с начала строчки массива
			int k=0;
			for (int i=0;i<pyramid.length;i++) {
			//Количество нулей, которые нужно отступить от начала строки в массиве, равно разности количества строк и номера текущей строки
			for(int j=(rows-i-1);j<columns;j+=2) {				
				pyramid[i][j]=prePyramid[i][k++];				
			}
			k=0;
		}			
			return pyramid;		
	}	
}
