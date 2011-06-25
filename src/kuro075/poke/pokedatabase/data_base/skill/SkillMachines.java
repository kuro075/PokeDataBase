package kuro075.poke.pokedatabase.data_base.skill;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import kuro075.poke.pokedatabase.data_base.skill.HidenMachines.DataTypes;

/**
 * 技マシン
 * @author sanogenma
 *
 */
public enum SkillMachines{
	_01("つめとぎ",1),
	_02("ドラゴンクロー",2),
	_03("サイコショック",3),
	_04("めいそう",4),
	_05("ほえる",5),
	_06("どくどく",6),
	_07("あられ",7),
	_08("ビルドアップ",8),
	_09("ベノムショック",9),
	_10("めざめるパワー",10),
	_11("にほんばれ",11),
	_12("ちょうはつ",12),
	_13("れいとうビーム",13),
	_14("ふぶき",14),
	_15("はかいこうせん",15),
	_16("ひかりのかべ",16),
	_17("まもる",17),
	_18("あまごい",18),
	_19("テレキネシス",19),
	_20("しんぴのまもり",20),
	_21("やつあたり",21),
	_22("ソーラービーム",22),
	_23("うちおとす",23),
	_24("10まんボルト",24),
	_25("かみなり",25),
	_26("じしん",26),
	_27("おんがえし",27),
	_28("あなをほる",28),
	_29("サイコキネシス",29),
	_30("シャドーボール",30),
	_31("かわらわり",31),
	_32("かげぶんしん",32),
	_33("リフレクター",33),
	_34("ヘドロウェーブ",34),
	_35("かえんほうしゃ",35),
	_36("ヘドロばくだん",36),
	_37("すなあらし",37),
	_38("だいもんじ",38),
	_39("がんせきふうじ",39),
	_40("つばめがえし",40),
	_41("いちゃもん",41),
	_42("からげんき",42),
	_43("ニトロチャージ",43),
	_44("ねむる",44),
	_45("メロメロ",45),
	_46("どろぼう",46),
	_47("ローキック",47),
	_48("りんしょう",48),
	_49("エコーボイス",49),
	_50("オーバーヒート",50),
	_51("サイドチェンジ",51),
	_52("きあいだま",52),
	_53("エナジーボール",53),
	_54("みねうち",54),
	_55("ねっとう",55),
	_56("なげつける",56),
	_57("チャージビーム",57),
	_58("フリーフォール",58),
	_59("やきつくす",59),
	_60("さきおくり",60),
	_61("おにび",61),
	_62("アクロバット",62),
	_63("さしおさえ",63),
	_64("だいばくはつ",64),
	_65("シャドークロー",65),
	_66("しっぺがえし",66),
	_67("かたきうち",67),
	_68("ギガインパクト",68),
	_69("ロックカット",69),
	_70("フラッシュ",70),
	_71("ストーンエッジ",71),
	_72("ボルトチェンジ",72),
	_73("でんじは",73),
	_74("ジャイロボール",74),
	_75("つるぎのまい",75),
	_76("むしのていこう",76),
	_77("じこあんじ",77),
	_78("じならし",78),
	_79("こおりのいぶき",79),
	_80("いわなだれ",80),
	_81("シザークロス",81),
	_82("ドラゴンテール",82),
	_83("ふるいたてる",83),
	_84("どくづき",84),
	_85("ゆめくい",85),
	_86("くさむすび",86),
	_87("いばる",87),
	_88("ついばむ",88),
	_89("とんぼがえり",89),
	_90("みがわり",90),
	_91("ラスターカノン",91),
	_92("トリックルーム",92),
	_93("ワイルドボルト",93),
	_94("いわくだき",94),
	_95("バークアウト",95);
	
	/**
	 * 登録されているデータ
	 * @author sanogenma
	 *
	 */
	public enum DataTypes{
		No("No",0) {
			@Override
			public Comparator<SkillMachines> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillMachines>(){
					@Override
					public int compare(SkillMachines machine1,
							SkillMachines machine2) {
						// TODO Auto-generated method stub
						return machine1.no-machine2.no;
					}
				};
			}
		},
		Name("技名",1) {
			@Override
			public Comparator<SkillMachines> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<SkillMachines>(){
					@Override
					public int compare(SkillMachines machine1,
							SkillMachines machine2) {
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
		
		abstract public Comparator<SkillMachines> getComparator();
		
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
	SkillMachines(String name,int no){
		this.name=name;
		this.no=no;
		StringBuilder sb=new StringBuilder();
		sb.append("No.");
		if(no<10){
			sb.append(0);
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
	
	
	
	private static final Map<Integer,SkillMachines> 
		integerToEnum = new HashMap<Integer, SkillMachines>();//数値からenumへ
	private static final Map<String,SkillMachines>
		stringToEnum = new HashMap<String,SkillMachines>();//文字列からenumへ
	static { //定数名からenum定数へのマップを初期化
		for(SkillMachines sm : values()){
			integerToEnum.put(sm.no, sm);
			stringToEnum.put(sm.toString(), sm);
		}
	}
	/**
	 * 文字列からSkillMachinesを取得
	 * @param step
	 * @return
	 */
	public static SkillMachines fromString(String name){
		return stringToEnum.get(name);
	}
	/**
	 * 数値（インデックス）からSkillMachinesを取得
	 * @param integer
	 * @return
	 */
	public static SkillMachines fromNo(int no){
		return integerToEnum.get(no);
	}
}
