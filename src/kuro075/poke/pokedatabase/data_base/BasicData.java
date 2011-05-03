package kuro075.poke.pokedatabase.data_base;

import java.io.Serializable;

/**
 * すべてのデータクラスのスーパークラス
 * 
 * @author sanogenma
 *
 */
public class BasicData implements Comparable<BasicData>,Serializable{

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 1L;
	
	

	//名前
	final private String Name;
	
	protected BasicData(String name){
		Name=name;
	}
	
	public String getName(){
		return Name;
	}
	
	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return Name.compareTo(another.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicData other = (BasicData) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		return result;
	}

	@Override
	public String toString(){
		return Name;
	}
	
	
}
