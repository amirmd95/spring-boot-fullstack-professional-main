package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DemoApplicationTests {
Calculator undertest=new Calculator();
	@Test
	void itshouldAddTwoNumber() {
		//given
		int numberOne=20;
		int numberTwo=20;

       // when
		int actual=undertest.add(numberOne,numberTwo);

		//then
		int expected=40;
		assertThat(actual).isEqualTo(expected);
	}
	class Calculator{
		int add(int a,int b){
			return a+b;
		}
	}

}
