package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * 能力ランクの段階
 * @author sanogenma
 *
 */
public enum StatusRankDegrees {
	m6("6段階下がる",-6),m5("5段階下がる",-5),m4("4段階下がる",-4),m3("3段階下がる",-3),m2("2段階下がる",-2),m1("1段階下がる",-1),_0("変化無し",0),
	_1("1段階上がる",1),_2("2段階上がる",2),_3("3段階上がる",3),_4("4段階上がる",4),_5("5段階上がる",5),_6("6段階上がる",6);
	private final String name;
	private final int degree;
	private final String string_degree;
	StatusRankDegrees(String name,int degree){
		this.name=name;
		this.degree=degree;
		this.string_degree=String.valueOf(degree);
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public int getDegree(){
		return degree;
	}
	public String getStringDegree(){
		return string_degree;
	}
	
	/**
	 * nameからStatusRankDegreesを取得
	 * @param name
	 * @return
	 */
	public static StatusRankDegrees fromString(String name){
		for(StatusRankDegrees et:values()){
			if(et.toString().equals(name)){
				return et;
			}
			if(et.getStringDegree().equals(name)){
				return et;
			}
		}
		return null;
	}
}
