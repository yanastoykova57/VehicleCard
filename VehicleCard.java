package a11916925;

import java.util.*;

public class VehicleCard implements Comparable <VehicleCard>{
	
	public enum Category {
		ECONOMY_MPG("Miles/Gallon"),
		DISPLACEMENT_CCM("Hubraum[cc]"),
		WEIGHT_LBS ("Gewicht[lbs]") {
			@Override public boolean isInverted() {
				return true;
			}},
		ACCELERATION ("Beschleunigung") {
				@Override public boolean isInverted() {
					return true;
				}},
		CYLINDERS_CNT("Zylinder"),
			YEAR("Baujahr[19xx]"),
		POWER_HP("Leistung[hp]");
		
		
	private final String categoryName;

	private Category(final String categoryName) {
		
		if(categoryName == null || categoryName.isEmpty())
			throw new IllegalArgumentException("err0");
		
		this.categoryName = categoryName;
	}
		

	public boolean isInverted() {
		return false;
	}

	public int bonus(final double value) {  
		
		if(this.isInverted())
			return (int) -value;
		else return (int) value;
		
	}

	@Override
	public String toString() { 
		return categoryName;
	}
}

//BEGINNING VEHICLECARD	
	
private String name;
private Map<Category, Double> categories;

public VehicleCard(final String name, final Map<Category, Double> categories) {
	
	
	if(name == null || name.isEmpty()) throw new IllegalArgumentException();
	
	if(categories == null || categories.containsValue(null)) throw new IllegalArgumentException();
	
	for (Category c : Category.values()) {
		if (!categories.containsKey(c)|| categories.get(c) < 0)
			throw new IllegalArgumentException();
	}
	
	this.name = name;
	Map<Category, Double> sc = new HashMap<>(categories); 
	this.categories = sc;
	
}

public String getName () { 
	return name;
}

public Map < Category , Double > getCategories () {/* returns shallow copy (!)
of this . categories */
	Map<Category, Double> sc = new HashMap<>(categories);	
	return sc;
}

public static Map < Category , Double > newMap ( double economy , double cylinders , double displacement , double power , double weight , double
acceleration , double year ) {/* factory method to create a new vehicle
card ’s categories map */
	
Map<Category, Double> op = new HashMap<Category, Double>();
op.put(Category.ECONOMY_MPG, economy);
op.put(Category.CYLINDERS_CNT, cylinders);
op.put(Category.DISPLACEMENT_CCM, displacement);
op.put(Category.POWER_HP, power);
op.put(Category.WEIGHT_LBS, weight);
op.put(Category.ACCELERATION, acceleration);
op.put(Category.YEAR, year);

return op;
}


@Override
public int compareTo ( final VehicleCard other ) {
// compare by name and totalBonus
	return Integer.compare(totalBonus(), other.totalBonus());
} 

public int totalBonus(){ 
	int ret = 0;
	for(var c: categories.entrySet())
		ret = ret + c.getKey().bonus(c.getValue());
	
	return (int)Math.round(ret); 
}

@Override
public int hashCode() { 
	return Objects.hash(name, totalBonus());
}

@Override
public boolean equals(Object obj) {
	
	if (obj == null)
		return false;
	
	if(obj.getClass().equals(this.getClass())) {
		if(Objects.equals(name, ((VehicleCard) obj).getName())) {
			if(((VehicleCard) obj).totalBonus() == this.totalBonus()) {
				return true;
			}
		}else return false;
	}
	return false;
}

@Override
public String toString () {
/* " - <name >( totalBonus ) -> { < categories >}" e.g.:
- Telsa (2) -> { Miles / Gallon =1.0 , Hubraum [cc ]=1.0 , Gewicht [lbs ]=1.0 ,
Beschleunigung =1.0 , Zylinder =1.0 , Baujahr [19 xx ]=0.0 , Leistung [hp
]=1.0} */

String op = "";
for(Category c: Category.values()) {
	op += c.toString() + "=" + categories.get(c);
	if(c != Category.values()[categories.size()-1])
		op += ", ";
}
return "- " + getName() + "(" + totalBonus() + ") -> {" + op + "}";
}

}
