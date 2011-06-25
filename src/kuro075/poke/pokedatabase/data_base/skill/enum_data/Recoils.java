package kuro075.poke.pokedatabase.data_base.skill.enum_data;

/**
 * 反動
 * @author sanogenma
 *
 */
public enum Recoils {
	_0("無し"),_2("1/2"),_3("1/3"),_4("1/4"),ALL("ひんし");
	private final String name;
	Recoils(String name){this.name=name;}
	@Override
	public String toString(){
		return name;
	}
	
	
	/**
	 * nameからRecoilsを取得
	 * @param name
	 * @return
	 */
	public static Recoils fromString(String name){
		for(Recoils r:values()){
			if(r.toString().equals(name)){
				return r;
			}
		}
		return null;
	}
}