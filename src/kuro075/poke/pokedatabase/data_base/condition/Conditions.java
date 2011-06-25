package kuro075.poke.pokedatabase.data_base.condition;

/**
 * 状態異常
 * @author sanogenma
 *
 */
public enum Conditions {
	POISON("どく"),DEADLY_POISON("もうどく"),PARALYSIS("まひ"),BURN("やけど"),SLEEP("ねむり"),
	ICE("こおり"),CHAOS("こんらん"),NONE("無し");
	private final String name;
	Conditions(String name){
		this.name=name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * nameからConditionsを取得
	 * @param name
	 * @return
	 */
	public static Conditions fromString(String name){
		for(Conditions et:values()){
			if(et.toString().equals(name)){
				return et;
			}
		}
		return null;
	}
}
