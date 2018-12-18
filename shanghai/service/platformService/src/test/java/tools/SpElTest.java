package tools;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import junit.framework.Assert;


public class SpElTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() 
	{

		Map<String, Integer> map = new HashMap<String, Integer>(); 
		map.put("a",1);

		ExpressionParser parser = new SpelExpressionParser(); 
		
		EvaluationContext  context3 = new StandardEvaluationContext(); 

		context3.setVariable("map", map); 

		String expression = "#map['a']";
		
		int result = parser.parseExpression("#map['a']").getValue(context3,int.class);

		System.out.println(expression + ":" + result);
		
		expression = "{{1+2,2+4},{3,4+4}}";

		List<List<Integer>> resultlist = parser.parseExpression(expression).getValue(List.class);
		
		System.out.println(expression + ":" + resultlist);

		Collection<Integer> collection = new HashSet<Integer>(); 
		
		collection.add(1);
		collection.add(2);
		
		EvaluationContext context2 = new StandardEvaluationContext(); 
		
		context2.setVariable("collection", collection); 
		
		expression = "#collection[1]";
		
		int resultcitem = parser.parseExpression(expression).getValue(context2,int.class);
		
		System.out.println(expression + ":" + resultcitem);

		
	}

}
