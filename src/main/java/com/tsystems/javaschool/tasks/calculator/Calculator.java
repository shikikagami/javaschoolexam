package com.tsystems.javaschool.tasks.calculator;

import java.util.Stack;

public class Calculator {
	
	public static String evaluate(String expression) {
		//Проверка, если входная строка null или пустая
		if (expression==null || expression.isEmpty()) {
			return null;
		}
		//Строка, в которую будет записываться выражение в обратной Польской нотации
		String RPNString="";
		//Стэк, куда будут помещаться арифметические знаки
		Stack<Character> stack=new Stack<Character>();
		//Проверка, правильно ли введено выражение
		if(!isCorrect(expression)) {
			return null;
		}
		//Посимвольное считывание строки с исходным выражением
		for (int i=0;i<expression.length();i++) {		
			//Проверка, является ли считанный символ числом или точкой (в случае десятичного числа)
			if(priority(expression.charAt(i))==0) {
				//Поместить его в строку для обратной Польской нотации
				RPNString+=expression.charAt(i);				
			}
			//Проверка, является ли считанный символ открывающей скобкой
			else if(priority(expression.charAt(i))==2) {
				//Поместить скобку в стэк
				stack.push(expression.charAt(i));
				
			}
			//Проверка, является считанный символ арифметической операцией
			else if(priority(expression.charAt(i))>=3) {
				//Поместить пробел в строку, т.к. после операции идет уже следующее число
				RPNString+=" ";	
				//Пока стэк не пустой
				while(!stack.empty()) {
					//Проверка, больше/равен приоритет последнего элемента в стэке приоритета последнего считанного элемента 
					if(priority(stack.peek())>=priority(expression.charAt(i))) {
						//Переместить его в строку и удалить из стэка
						RPNString+=stack.pop();						
					}
					else {
						//Если приоритет последнего элемента в стэке меньше приоритета последнего считанного элемента - прервать цикл
						break;
					}					
				}
				//Затем поместить символ в стэк
				stack.push(expression.charAt(i));
			}
			//Проверка, является ли считанный символ закрывающей скобкой
			else if(priority(expression.charAt(i))==1) {
				
				//Поместить пробел в строку
				RPNString+=" ";	
				//Переместить все элементы из стэка в строку, пока очередь не дойдет до открывающей скобки
				while(priority(stack.peek())!=2) {
					RPNString+=stack.pop();
				}
				//Затем удалить из стэка открывающую скобку
				stack.pop();
			}
			//Проверка, если выражение содержит недопустимый символ
			else {
				return null;
			}
		}
		//Проверка, если вся строка считана, но стэк не пустой
		while(!stack.empty()) {
			//Переместить все элементы из стэка в строку
			RPNString+=stack.pop();
			
		}
		
		
		//Строка, куда будут помещаться числа
		String currentNum="";
		//Стек, куда будут помещаться числа и результат
		Stack<Double> stackNum=new Stack<Double>();
		//Считывание полученной строки 
		for(int i=0;i<RPNString.length();i++) {			
			//Если считанный символ - число
			 if(priority(RPNString.charAt(i))==0) {
				//Считываем дальше до тех пор, пока считанный символ - это число
				while(RPNString.charAt(i)!=' ' && priority(RPNString.charAt(i))==0){
					//Добавить считанный символ к числу в строке
					currentNum+=RPNString.charAt(i++);				
				}
				//Если достигнут конец строки
				if(i==RPNString.length()) {
					//Прервать цикл
					break;
				}				
				//Затем поместить считанное число в стэк
				stackNum.push(Double.valueOf(currentNum));
				//Обнулить строку со считанным числом
				currentNum="";
			}
			//Если считанный символ - арифметическая операция
			if(priority(RPNString.charAt(i))>=3) {
				//Получаем последние два числа из стэка				
				double b=stackNum.pop();
				double a=stackNum.pop();				
				//Определяем, какой именно операции соответствует считанный символ, и выполняем операцию
				switch(RPNString.charAt(i)) {
				case '+':stackNum.push(a+b);break;
				case '-': stackNum.push(a-b);break;
				case '*': stackNum.push(a*b);break;
				case '/': {
					//Проверка, если происходит деление на ноль
					if (b==0) {
						//Вернуть null
						return null;
					}
					stackNum.push(a/b);break;
				}
				}			
			}		
		}
		//В итоге в стэке останется только одно число, которое и будет ответом. Извлекаем его
		double answer=stackNum.pop();
		//Проверка, является ли ответ целым числом
		if(isInteger(answer)) {
			//Вернуть результат в виде целого числа
			return Integer.toString((int) answer);			
		}
		return Double.toString(answer);
	}
	
	//Метод возвращает значение приоритета для операции
	public static int priority(char symbol) {
		//Если символ - это умножение или деление
		if (symbol=='*'||symbol=='/') {
			//Приоритет 4
			return 4;
		}
		//Если символ - сложение или вычитание
		else if (symbol=='+'||symbol=='-') {
			//Приоритет 3
			return 3;
		}
		//Если символ - открывающая скобка
		else if(symbol=='(') {
			//Приоритет 2
			return 2;
		}
		//Если символ - закрывающая скобка
		else if(symbol==')') {
			//Приоритет 1
			return 1;
		}
		//Если символ - число или точка (в случае дробного числа)
		else if(Character.isDigit(symbol)||symbol=='.') {
			//Приоритет 0
			return 0;
		}
		return -1;		
	}
	//Метод проверяет, правильно ли введено выражение (четное или нулевое количество скобок, нет ли нескольких точек и знаков подряд)
	public static boolean isCorrect(String expression) {
		//Счетчик количества открывающих и закрывающих скобок
		int count=0;
		//Поиск скобок в выражении
		for (int i=0;i<expression.length();i++) {
			//Если считана точка
			if(expression.charAt(i)=='.') {
				//Проверить, является ли следующий символ тоже точкой
				if(expression.charAt(i+1)=='.') {
					//Если да - вернуть false
					return false;
				}
			}
			//Если считан арифметический знак
			if(priority(expression.charAt(i))>=3) {
				//Проверяем следующий символ
				if(priority(expression.charAt(i+1))>=3) {
					//Если да - вернуть false
					return false;
				}
			}
			if(expression.charAt(i)=='(' || expression.charAt(i)==')'){
				count++;
			}
		}
		//Проверка, четное или равное нулю количетсво скобок в выражении
		if (count%2==0 || count==0) {
			return true;
		}
		return false;
	}
	//Метод проверяет, является ли число целым или дробным
	public static boolean isInteger(double answer) {
		if(answer%1==0) {
			return true;
		}
		return false;
	}
	
}
