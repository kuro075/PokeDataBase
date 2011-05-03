package kuro075.poke.pokedatabase.data_base.skill;

import java.util.HashMap;
import java.util.Map;

/**
 * 旧わざマシン
 * @author sanogenma
 *
 */
public enum OldSkillMachines {
		_1("きあいパンチ",1),
		_3("みずのはどう",3),
		_9("タネマシンガン",9),
		_19("ギガドレイン",19),
		_23("アイアンテール",23),
		_34("でんげきは",34),
		_43("ひみつのちから",43),
		_47("はがねのつばさ",47),
		_48("スキルスワップ",48),
		_49("よこどり",49),
		_51("はねやすめ",51),
		_55("しおみず",55),
		_58("こらえる",58),
		_59("りゅうのはどう",59),
		_60("ドレインパンチ",60),
		_62("ぎんいろのかぜ",62),
		_67("リサイクル",67),
		_72("ゆきなだれ",72),
		_76("ステルスロック",76),
		_78("ゆうわく",78),
		_79("あくのはどう",79),
		_82("ねごと",82),
		_83("しぜんのめぐみ",83);
		
		private final String name;
		private final int no;
		OldSkillMachines(String name,int no){this.name=name;this.no=no;}
		
		@Override
		public String toString(){return name;}
		public int getNo(){return no;}
		
		public SkillData getSkillData(){
			return SkillDataManager.INSTANCE.getSkillData(name);
		}
		
		
		
		private static final Map<Integer,OldSkillMachines> 
			integerToEnum = new HashMap<Integer, OldSkillMachines>();//数値からenumへ
		private static final Map<String,OldSkillMachines>
			stringToEnum = new HashMap<String,OldSkillMachines>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(OldSkillMachines sm : values()){
				integerToEnum.put(sm.no, sm);
				stringToEnum.put(sm.toString(), sm);
			}
		}
		/**
		 * 文字列からSkillMachinesを取得
		 * @param step
		 * @return
		 */
		public static OldSkillMachines fromString(String step){
			return stringToEnum.get(step);
		}
		/**
		 * 数値（インデックス）からSkillMachinesを取得
		 * @param integer
		 * @return
		 */
		public static OldSkillMachines fromNo(int no){
			return integerToEnum.get(no);
		}
}
