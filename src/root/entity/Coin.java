package root.entity;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.util.StringUtils;

public class Coin implements Comparable<Coin>, Serializable
{
	private float value;
	private String name;
	
	// define a private field for the dependency - help
	public Coin(String name, float value) {
		this.name = name;
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
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
		return StringUtils.capitalize(name)+ " [Value= " + value +"$]";
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
		
		if (!name.equals(other.name))
			return false;
		
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	public int compareTo(Coin o) {
		return (int)(this.value - o.getValue());
	}
	
	
	
		

}
