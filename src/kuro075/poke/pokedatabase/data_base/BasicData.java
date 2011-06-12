package kuro075.poke.pokedatabase.data_base;

/**
 * すべてのデータクラスのスーパークラス
 * 
 * @author sanogenma
 *
 */
public class BasicData implements Comparable<BasicData>{
	//名前
	final private String Name;
	final private int no;
	private final String string_no;//文字列のNo
	protected BasicData(String name,int no){
		Name=name;
		this.no=no;
		//Noを文字列にして保存
		StringBuilder sb=new StringBuilder();
		if(no<10){
			sb.append("00");
		}else if(no<100){
			sb.append("0");
		}
		sb.append(no);
		string_no=new String(sb);
	}
	
	public String getName(){
		return Name;
	}
	
	public int getNo(){return no;}
	
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
	
	/**
	 * Noを文字列で取得(001,015など)
	 * @return
	 */
	public String getNo2String(){
		return string_no;
	}
}
