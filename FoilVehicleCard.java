package a11916925;

import java.util.*;

public class FoilVehicleCard extends VehicleCard{

private Set<Category> specials;

public FoilVehicleCard(final String name, final Map<Category, Double> categories, final Set<Category> specials) {
	super(name, categories);
	
	if(specials == null || specials.size() > (int) 3 || specials.isEmpty())
		throw new IllegalArgumentException();
	
	Set<Category> ns = new HashSet<>(specials);
	this.specials = ns;
	
}

public Set<Category> getSpecials() {
	Set<Category> ns = new HashSet<>(specials);
	return ns;
}

@Override
public int totalBonus() {
	
int output = super.totalBonus();
	
	for(Category c: specials) {
		if(getCategories().containsKey(c)) {
			output += Math.abs(getCategories().get(c));
		}
	}
	
	return output;
}

@Override
public String toString () { // add * before and after special categories ,e.g.
/* - Ladla (4) -> {* Miles / Gallon *=1.0 , Hubraum [cc ]=1.0 , Gewicht [lbs ]=1.0 ,
Beschleunigung =1.0 , Zylinder =1.0 , Baujahr [19 xx ]=0.0 , * Leistung [hp
]*=1.0}*/
	
	String op = "";
	for(Category c: Category.values()) {
		if(specials.contains(c)) {
			op += "*" + c.toString() + "*=" + getCategories().get(c);	
		}
		else {
			op += c.toString() + "=" + getCategories().get(c);
		}
		
		if(c != Category.values()[getCategories().size()-1])
			op += ", ";
	}
	return "- " + getName() + "(" + totalBonus() + ") -> {" + op + "}";
}

}

