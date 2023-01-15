package entity;

import java.util.HashMap;

public class Coin implements Comparable<Coin>
{
	private int value;
	private String name;
	
	// define a private field for the dependency - help
	public Coin(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Coin [value=" + value + ", name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coin other = (Coin) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	public int compareTo(Coin o) {
		return this.value - o.getValue();
	}
	
	
	
		

}
