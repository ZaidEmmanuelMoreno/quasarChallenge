package com.quasar.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.quasar.model.Coordinates;
import com.quasar.model.Equation;

/**
 * @author emmanuel
 *
 */
@Component
public class MathOperations {

	/**
	 * From x, y coordinates and the radius, generate the equation of the circumference, and store it in an object.
	 * 
	 * @param coordinates			An object that stores x and y coordinates, what is the center of the circle.
	 * @param radius				Radius of the circle.
	 * @return						Objeto que contiene la ecuacion de la circunferencia,(se asume que x^2 = 1 y y^2 = 1, por lo que no se almacenan)
	 */
	public Equation getCircumferenceEquation(Coordinates coordinates, double radius) {
		
		double a = coordinates.getX() * -2;
		double b = coordinates.getY() * -2;
		double c = Math.pow(coordinates.getX(), 2) + Math.pow(coordinates.getY(), 2) - Math.pow(radius, 2);
		
		return new Equation(Util.roundingDecimals(a,2),Util.roundingDecimals(b,2),Util.roundingDecimals(c,2),0);
	}

	/**
	 * Method that calculates the x and y intersection points between two circles, following the following steps:
	* 1.- A sum of equations is made to eliminate the quadratic terms.
	* 2.- Solve for x and obtain a linear equation.
	* 3.- We substitute x in the first equation of the circumference.
	* 4.- The quadratic term and the multiplication of terms are solved
	* 5.- It is simplified to clear and positive and negative.
	* 6.- The coordinates are constructed and added to the intersection list.
	 * 
	 * @param coordinatesList			List of coordinates, which are the points of intersection.
	 * @param circleEquationOne			Equation of circumference one.
	 * @param circleEquationTwoNormal	Equation of circumference two.
	 * @return							List of coordinates, which are the points of intersection.
	 */
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

	/**
	 * Method that obtains the points (coordinates) that are repeated the most in the list of coordinates, these are the points where the three intersect
	 * circles, which is where the message comes from.
	 * 
	 * @param coordinatesList		List of coordinates, which are the points of intersection.
	 * @return						Empty ResponseEntity with http status.
	 */
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
