package com.quasar.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.quasar.model.Coordinates;
import com.quasar.model.Equation;

@Component
public class MathOperations {

	public Equation getCircumferenceEquation(Coordinates coordinates, double radius) {
		
		double a = coordinates.getX() * -2;
		double b = coordinates.getY() * -2;
		double c = Math.pow(coordinates.getX(), 2) + Math.pow(coordinates.getY(), 2) - Math.pow(radius, 2);
		
		return new Equation(Util.roundingDecimals(a,2),Util.roundingDecimals(b,2),Util.roundingDecimals(c,2),0);
	}

	public List<Coordinates> getTwoCirclesIntersection(List<Coordinates> coordinatesList, Equation circleEquationOne, Equation circleEquationTwoNormal) {

		double ySquared, y, independentTerm, yTwo, independentTermTwo;
		double discriminating;
		double yFinalOne, yFinalTwo;
		double xFinalOne, xFinalTwo;
		
		//The equations are added
		//The second equation is converted to negative
		Equation circleEquationTwo = new Equation();
		circleEquationTwo.setCoefficientX(circleEquationTwoNormal.getCoefficientX() * -1);
		circleEquationTwo.setCoefficientY(circleEquationTwoNormal.getCoefficientY() * -1);
		circleEquationTwo.setIndependentTermC(circleEquationTwoNormal.getIndependentTermC() * -1);
		
		//TODO validar x y y
		Equation lineEquation = new Equation();
		lineEquation.setCoefficientX(circleEquationOne.getCoefficientX() + circleEquationTwo.getCoefficientX());
		lineEquation.setCoefficientY(circleEquationOne.getCoefficientY() + circleEquationTwo.getCoefficientY());
		lineEquation.setIndependentTermC(circleEquationOne.getIndependentTermC() + circleEquationTwo.getIndependentTermC());

		//Solve for X
		lineEquation.setCoefficientY((lineEquation.getCoefficientY() / lineEquation.getCoefficientX()) * -1);
		lineEquation.setIndependentTermC((lineEquation.getIndependentTermC() / lineEquation.getCoefficientX()) * -1);
		lineEquation.setCoefficientX(lineEquation.getCoefficientX() / lineEquation.getCoefficientX());
		
		//It is substituted into the general equation of the circumference
		//The quadratic term is solved
		ySquared = Math.pow(lineEquation.getCoefficientY(), 2);
		independentTerm = Math.pow(lineEquation.getIndependentTermC(), 2);
		y = 2 * lineEquation.getCoefficientY() * lineEquation.getIndependentTermC();
		
		//The second operation is solved
		yTwo = circleEquationOne.getCoefficientX() * lineEquation.getCoefficientY();
		independentTermTwo = circleEquationOne.getCoefficientX() * lineEquation.getIndependentTermC();
		
		//They add up
		ySquared = ySquared + 1;
		y = y + circleEquationOne.getCoefficientY() + yTwo;
		independentTerm = independentTerm + independentTermTwo + circleEquationOne.getIndependentTermC();
		
		//It simplifies
		y = y / ySquared;
		independentTerm = independentTerm / ySquared;
		ySquared = ySquared / ySquared;
		
		//Solve the quadratic equation
		discriminating = Math.pow(y, 2) - (4 * ySquared * independentTerm);
		
		//if(discriminating > 0) has two real roots
		yFinalOne = ((-1 * y) + Math.sqrt(discriminating)) / (2 * ySquared);
		yFinalTwo = ((-1 * y) - Math.sqrt(discriminating)) / (2 * ySquared);
		
		//The intersection points are constructed
		xFinalOne = (lineEquation.getCoefficientY() * yFinalOne) + lineEquation.getIndependentTermC();
		xFinalTwo = (lineEquation.getCoefficientY() * yFinalTwo) + lineEquation.getIndependentTermC();
		
		coordinatesList.add(new Coordinates(xFinalOne, yFinalOne));
		coordinatesList.add(new Coordinates(xFinalTwo, yFinalTwo));
		return coordinatesList;
	}

	public Coordinates getCoordinates(List<Coordinates> coordinatesList) {

		LinkedList<String> coordinatesListS = new LinkedList<>();
		coordinatesList.stream().forEach(coordinates -> coordinatesListS.add(coordinates.getConcatString()));
		
		List<String> duplicateList = coordinatesListS.stream()
		            .collect(Collectors.groupingBy(s -> s)).entrySet().stream()
		            .filter(e -> e.getValue().size() > 1)
		            .map(e -> e.getKey())
		            .collect(Collectors.toList());
		
		String[] duplicatedArray = duplicateList.get(0).split(",");
		
		double x = Double.parseDouble(duplicatedArray[0]);
		double y = Double.parseDouble(duplicatedArray[1]);
		
		return new Coordinates(x, y);
	}
	
}
