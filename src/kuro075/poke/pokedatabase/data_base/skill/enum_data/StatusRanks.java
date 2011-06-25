package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * 能力ランク
 * @author sanogenma
 *
 */
public enum StatusRanks {
	H("HP"),A("攻撃"),B("防御"),C("特攻"),D("特防"),S("素早"),HIT("命中"),AVOID("回避"),CRITICAL("急所"),NONE("-");
	private final String name;
	StatusRanks(String name){
		this.name=name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * nameからStatusRanksを取得
	 * @param name
	 * @return
	 */
	public static StatusRanks fromString(String name){
		for(StatusRanks et:values()){
			if(et.toString().equals(name)){
				return et;
			}
		}
		return null;
	}
}
