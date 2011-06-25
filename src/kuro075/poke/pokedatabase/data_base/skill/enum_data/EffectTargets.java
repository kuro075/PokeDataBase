package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * 追加効果の対象
 * @author sanogenma
 *
 */
public enum EffectTargets {
	MYSELF("自分"),ENEMY("相手"),NONE("無し");
	
	private final String name;
	EffectTargets(String name){
		this.name=name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * nameからEffectTargetsを取得
	 * @param name
	 * @return
	 */
	public static EffectTargets fromString(String name){
		for(EffectTargets et:values()){
			if(et.toString().equals(name)){
				return et;
			}
		}
		return null;
	}
}
