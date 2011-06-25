package kuro075.poke.pokedatabase.data_base.skill;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import kuro075.poke.pokedatabase.data_base.skill.SkillMachines.DataTypes;

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
		
		/**
		 * 登録されているデータ
		 * @author sanogenma
		 *
		 */
		public enum DataTypes{
			No("No",0) {
				@Override
				public Comparator<OldSkillMachines> getComparator() {
					// TODO Auto-generated method stub
					return new Comparator<OldSkillMachines>(){
						@Override
						public int compare(OldSkillMachines machine1,
								OldSkillMachines machine2) {
							// TODO Auto-generated method stub
							return machine1.no-machine2.no;
						}
					};
				}
			},
			Name("技名",1) {
				@Override
				public Comparator<OldSkillMachines> getComparator() {
					// TODO Auto-generated method stub
					return new Comparator<OldSkillMachines>(){
						@Override
						public int compare(OldSkillMachines machine1,
								OldSkillMachines machine2) {
							// TODO Auto-generated method stub
							return machine1.name.compareTo(machine2.name);
						}
					};
				}
			};
			private final String name;
			private final int index;
			DataTypes(String name,int index){
				this.name=name;
				this.index=index;
			}
			@Override
			public String toString(){ return name; }
			public int getIndex(){ return index; }
			
			abstract public Comparator<OldSkillMachines> getComparator();
			
			private static final Map<Integer,DataTypes> 
				integerToEnum = new HashMap<Integer, DataTypes>();//数値からenumへ
			private static final Map<String,DataTypes>
				stringToEnum = new HashMap<String,DataTypes>();//文字列からenumへ
			static { //定数名からenum定数へのマップを初期化
				for(DataTypes sm : values()){
					integerToEnum.put(sm.index, sm);
					stringToEnum.put(sm.toString(), sm);
				}
			}
			/**
			 * 文字列からHidenMachinesを取得
			 * @param step
			 * @return
			 */
			public static DataTypes fromString(String step){
				return stringToEnum.get(step);
			}
			/**
			 * 数値（インデックス）からHidenMachinesを取得
			 * @param integer
			 * @return
			 */
			public static DataTypes fromIndex(int index){
				return integerToEnum.get(index);
			}
		}
		
		private final String name;
		private final int no;
		private final String text_no;
		OldSkillMachines(String name,int no){
			this.name=name;
			this.no=no;
			StringBuilder sb=new StringBuilder();
			sb.append("No.");
			if(no<10){
				sb.append("0");
			}
			sb.append(no);
			text_no=new String(sb);
		}
		
		@Override
		public String toString(){return name;}
		public int getNo(){return no;}
		public String toNo(){
			return text_no;
		}
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
		public static OldSkillMachines fromString(String name){
			return stringToEnum.get(name);
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
