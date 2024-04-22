package a11916925;

import java.util.*;

public class Player implements Comparable<Player> {
private String name;
private Queue<VehicleCard> deck = new ArrayDeque<>();

public Player(final String name) {
	if(name == null || name.isEmpty()) throw new IllegalArgumentException ();
	
	this.name = name;
}

public String getName() {
	return name;
}


public int getScore () {/* return sum of totalBonus of deck ’s cards ( maybe
negative )*/
	
	int output = 0;
	
	for(VehicleCard vc: deck) {
		output+= vc.totalBonus();
	}
	
	return output;
}


public void addCards (final Collection <VehicleCard> cards) {
	deck.addAll(cards);
}


public void addCard ( final VehicleCard card ) {
	deck.add(card);	
}


public void clearDeck () {
	deck.clear();
}


public List<VehicleCard> getDeck() {
List<VehicleCard> nl = new ArrayList<>(deck);
return nl;
}


protected VehicleCard peekNextCard () { 
	return deck.peek();
}


public VehicleCard playNextCard () {
	return deck.poll();
}

@Override
public int compareTo ( final Player other ) {
// compare by name [ case insensitive ]
	return getName().compareToIgnoreCase(other.getName());
}


@Override
public int hashCode() {
	/* hash ( name [ case insensitive ]) */
return getName().toLowerCase().hashCode(); 
}


@Override
public boolean equals (Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Player other = (Player) obj;
	return getName().equalsIgnoreCase(other.getName());	
}


@Override
public String toString () {/* format : name ( score ), one card per line , e.g
.:
Maria (73214) :
- Porsche 911(73054) -> { Beschleunigung = <val > , Zylinder = <val > , ...}
- Renault Clio (160) -> {...}
*/
String output = "";
	
	for(VehicleCard vc: deck) {
		output += vc.toString();
		if(vc != getDeck().get(deck.size()-1)) 
			output += "\n";
		}
	if(deck.isEmpty())
		return getName() + "(" + getScore() + "):";
	
	return getName() + "(" + getScore() + "):\n" + output;
	
} 


public boolean challengePlayer(Player p) {
// throws IllegalArgumentException if p is null or p is this .
	
// playNextCard from this and p.
	
	
// Player who has higher scoring card , adds both of them to the end of his deck . Order is not important. 
// Player who has lower scoring card , loses card . 
// If draw , repeat until winner is found . 
// Winner gets all cards played . Order is not important .
	 
// Loser loses all cards played .
	
// If one of the decks is empty before winner is found , cards are returned to the original decks and the method returns false . YES
	
// Returns true if this wins , false otherwise .
if(p == null || p.equals(this)) throw new IllegalArgumentException();
	
	Collection<VehicleCard> cardsplayed = new ArrayList<VehicleCard>();
	Collection<VehicleCard> thisnd = new ArrayList<VehicleCard>();
	Collection<VehicleCard> pnd = new ArrayList<VehicleCard>();
	
	
	
	while(true) {
	if(this.getDeck().isEmpty() || p.getDeck().isEmpty()) {
		this.addCards(thisnd);
		p.addCards(pnd);
		return false;
	}
	
	int dn = this.peekNextCard().compareTo(p.peekNextCard());
	
	thisnd.add(getDeck().get(0));
	playNextCard();
	pnd.add(p.getDeck().get(0));
	p.playNextCard();
	
	if(dn > 0) {
		cardsplayed.addAll(thisnd);
		cardsplayed.addAll(pnd);
		this.addCards(cardsplayed);
		break;
	} else if (dn < 0) {
		
		cardsplayed.addAll(thisnd);
		cardsplayed.addAll(pnd);
		p.addCards(cardsplayed);
		return false;
	} else if (dn == 0) {
		continue;
	}
	
	}
	return true;
	
}


public static Comparator < Player > compareByScore () {
	return (p1,p2)-> Integer.compare(p1.getScore(),p2.getScore());
}

public static Comparator < Player > compareByDeckSize() {
	return (p1,p2) -> Integer.compare(p1.getDeck().size(), p2.getDeck().size());
}

}



