package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * わざの種類
 * @author sanogenma
 *
 */
public enum SkillKinds {
	FIRST("先攻"),LAST("後攻"),STATUS("能力ランク"),KILL("いちげきひっさつ"),REFLECT("反射"),
	CONDITION("状態異常"),RECOIL("反動"),SEQUENCE("連続"),GATHER("ため"),CURE("回復"),DRAIN("吸収"),BIND("束縛"),
	HIT("必中"),POW_CHANGE("威力変動"),DOUBLE_POW("条件下威力二倍"),FIXATION("固定ダメージ"),
	WEATHER("天候"),SET("設置"),RELAY("交代"),CHARACTER("とくせい変化"),
	TYPE("タイプ変化"),VETO("わざ禁止"),SKILL_CHANGE("わざ変化"),
	ITEM("もちもの操作"),OATH("誓い"),DOUBLES_TRIPLES("ダブル・トリプル用"),INCERTITUDE("効果不定"),OTHER("その他");
	
	private final String name;
	SkillKinds(String name){this.name=name;}
	
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * nameからSkillKindsを取得
	 * @param name
	 * @return
	 */
	public static SkillKinds fromString(String name){
		for(SkillKinds sk:values()){
			if(sk.toString().equals(name))
				return sk;
		}
		return null;
	}
}
