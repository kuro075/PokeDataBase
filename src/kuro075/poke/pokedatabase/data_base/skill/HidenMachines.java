package kuro075.poke.pokedatabase.data_base.skill;

import java.util.HashMap;
import java.util.Map;

/**
 * 秘伝マシン
 * @author sanogenma
 *
 */
public enum HidenMachines {
	_1("いあいぎり",1),
	_2("そらをとぶ",2),
	_3("なみのり",3),
	_4("かいりき",4),
	_5("たきのぼり",5),
	_6("ダイビング",6);
	
	private final String name;
	private final int no;
	HidenMachines(String name,int no){this.name=name;this.no=no;}
	
	@Override
	public String toString(){return name;}
	public int getNo(){return no;}
	
	public SkillData getSkillData(){
		return SkillDataManager.INSTANCE.getSkillData(name);
	}
	
	
	
	private static final Map<Integer,HidenMachines> 
		integerToEnum = new HashMap<Integer, HidenMachines>();//数値からenumへ
	private static final Map<String,HidenMachines>
		stringToEnum = new HashMap<String,HidenMachines>();//文字列からenumへ
	static { //定数名からenum定数へのマップを初期化
		for(HidenMachines sm : values()){
			integerToEnum.put(sm.no, sm);
			stringToEnum.put(sm.toString(), sm);
		}
	}
	/**
	 * 文字列からSkillMachinesを取得
	 * @param step
	 * @return
	 */
	public static HidenMachines fromString(String step){
		return stringToEnum.get(step);
	}
	/**
	 * 数値（インデックス）からSkillMachinesを取得
	 * @param integer
	 * @return
	 */
	public static HidenMachines fromNo(int no){
		return integerToEnum.get(no);
	}
}
