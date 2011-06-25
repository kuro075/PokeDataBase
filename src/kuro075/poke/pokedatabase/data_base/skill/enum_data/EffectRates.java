package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * 追加効果の発生確率
 * @author sanogenma
 *
 */
public enum EffectRates {
	_0("-"),_10("10%"),_20("20%"),_30("30%"),_40("40%"),_50("50%"),_60("60%"),_100("100%"),SURE("必");
	private final String name;
	EffectRates(String name){
		this.name=name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * nameからEffectRatesを取得
	 * @param name
	 * @return
	 */
	public static EffectRates fromString(String name){
		for(EffectRates et:values()){
			if(et.toString().equals(name)){
				return et;
			}
		}
		return null;
	}
}
