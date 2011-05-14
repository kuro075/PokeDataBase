package kuro075.poke.pokedatabase.data_base.poke;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.skill.HidenMachines;
import kuro075.poke.pokedatabase.data_base.skill.OldSkillMachines;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillMachines;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;

/**
 * 一種類のポケモンを表すデータクラス
 * @author sanogenma
 *
 */
public class PokeData extends BasicData implements Serializable{

	protected static class Builder{
		private String name;//名前
		//==No,タイプ、とくせい、種族値、努力値
		private int no=-1;//図鑑No
		private int[] types=new int[2];//タイプ
		private CharacterData[] characters=new CharacterData[3];//とくせい
		private int[] specs=new int[6],effs=new int[6];//種族値,努力値
		//==タマゴ==
		private int[] egg_groups=new int[2];//タマゴグループ
		private int hatching_step=-1;//孵化歩数
		//==たかさ・おもさ==
		private int height=0;//たかさ
		private int weight=0;//おもさ
		//==性別比率
		private int[] sex_ratios=new int[2];//性別比率
		//==持っている道具==
		private ItemData[] items=new ItemData[2];//持っている道具
		//==その他==
		private int final_ex=0;//最終経験値
		private int ease_get=0;//つかまえやすさ
		private int basic_ex=0;//基礎経験値
		private int initial_natsuki=0;//初期なつき
		//===覚えるわざ==
		private List<SkillData> lv_skills=new ArrayList<SkillData>();//レベルアップで覚えるわざ
		private List<Integer[]> learning_lvs=new ArrayList<Integer[]>();//レベルアップで覚えるわざの覚えるレベル
		private List<SkillMachines> machines=new ArrayList<SkillMachines>();
		private List<HidenMachines> hidens=new ArrayList<HidenMachines>();
		private List<OldSkillMachines> old_machines=new ArrayList<OldSkillMachines>();//技マシン、秘伝マシン、旧技マシン
		private List<SkillData> egg_skills=new ArrayList<SkillData>();//タマゴ技
		private List<SkillData> teach_skills_Pt=new ArrayList<SkillData>(),teach_skills_HS=new ArrayList<SkillData>(),teach_skills_BW=new ArrayList<SkillData>();//教え技Pt,HS,bw
		//==進化系列==
		private List<Integer> evolutions=new ArrayList<Integer>();//進化系列
		private List<String> condition_evolutions=new ArrayList<String>();//進化条件

		protected Builder(){
			Arrays.fill(types, -1);
			Arrays.fill(characters, CharacterDataManager.NullData);
			Arrays.fill(egg_groups, -1);
			Arrays.fill(specs,0);
			Arrays.fill(effs, 0);
			Arrays.fill(sex_ratios, 0);
			Arrays.fill(items, ItemDataManager.NullData);
			
		}
		
		public PokeData build(){
			TypeData[] tmp_types=new TypeData[2];
			for(int i=0;i<2;i++){
				if(types[i]==-1){tmp_types[i]=null;}
				tmp_types[i]=TypeData.fromInteger(types[i]);
			}
			
			//タマゴ技、教えわざをソート
			Collections.sort(egg_skills);
			Collections.sort(teach_skills_Pt);
			Collections.sort(teach_skills_HS);
			Collections.sort(teach_skills_BW);
			
			return new PokeData(name,no,tmp_types,characters,specs,effs,
					   egg_groups,hatching_step,
					   height,weight,
					   sex_ratios,
					   items,
					   final_ex,ease_get,basic_ex,initial_natsuki,
					   lv_skills.toArray(new SkillData[0]),learning_lvs.toArray(new Integer[0][]),
					   machines.toArray(new SkillMachines[0]),hidens.toArray(new HidenMachines[0]),old_machines.toArray(new OldSkillMachines[0]),
					   egg_skills.toArray(new SkillData[0]),
					   teach_skills_Pt.toArray(new SkillData[0]),teach_skills_HS.toArray(new SkillData[0]),teach_skills_BW.toArray(new SkillData[0]),
					   evolutions.toArray(new Integer[0]),condition_evolutions.toArray(new String[0])); 
		}
		
		public void setName(String name){
			this.name=name;
		}
		public void setNo(int no) {
			this.no = no;
		}

		public void setType(int index,int type) {
			this.types[index] = type;
		}

		public void setCharacter(int index,int character) {
			this.characters[index] = CharacterDataManager.INSTANCE.getCharacterData(character);
		}

		public void setSpec(int index,int spec) {
			this.specs[index] = spec;
		}

		public void setEff(int index,int eff) {
			this.effs[index] = eff;
		}

		public void setEggGroup(int index,int egg_group) {
			this.egg_groups[index] = egg_group;
		}

		public void setHatchingStep(int hatching_step) {
			this.hatching_step = hatching_step;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public void setSexRatio(int index,int sex_ratio) {
			this.sex_ratios[index] = sex_ratio;
		}

		public void setItem(int index,String item) {
			this.items[index] = ItemDataManager.INSTANCE.getItemData(item);
		}

		public void setFinalEx(int final_ex) {
			this.final_ex = final_ex;
		}

		public void setEaseGet(int ease_get) {
			this.ease_get = ease_get;
		}

		public void setBasicEx(int basic_ex) {
			this.basic_ex = basic_ex;
		}

		public void setInitialNatsuki(int initial_natsuki) {
			this.initial_natsuki = initial_natsuki;
		}

		public void addLvSkill(int lv_skill) {
			this.lv_skills.add(SkillDataManager.INSTANCE.getSkillData(lv_skill));
		}

		public void addLearningLv(int[] learning_lvs) {
			Integer[] tmp=new Integer[learning_lvs.length];
			for(int i=0,n=learning_lvs.length;i<n;i++){
				tmp[i]=learning_lvs[i];
			}
			this.learning_lvs.add(tmp);
		}
		public void addLearningLv(String learning_lvs){
			String[] string_lvs=learning_lvs.split("/");
			final int length=string_lvs.length;
			int[] integer_lvs=new int[length];
			for(int i=0;i<length;i++){
				if(string_lvs[i].equals("-")){
					integer_lvs[i]=-1;
				}else{
					integer_lvs[i]=Integer.valueOf(string_lvs[i]);
				}
			}
			addLearningLv(integer_lvs);
		}

		public void addMachine(int machine) {
			this.machines.add(SkillMachines.fromNo(machine));
		}

		public void addHiden(int hiden) {
			this.hidens.add(HidenMachines.fromNo(hiden));
		}

		public void addOldMachine(int old_machine) {
			this.old_machines.add(OldSkillMachines.fromNo(old_machine));
		}

		public void addEggSkill(int egg_skill) {
			this.egg_skills.add(SkillDataManager.INSTANCE.getSkillData(egg_skill));
		}

		public void addTeachSkillPt(int teach_skill_Pt) {
			this.teach_skills_Pt.add(SkillDataManager.INSTANCE.getSkillData(teach_skill_Pt));
		}

		public void addTeachSkillHS(int teach_skill_HS) {
			this.teach_skills_HS.add(SkillDataManager.INSTANCE.getSkillData(teach_skill_HS));
		}

		public void addTeachSkillBW(int teach_skill_BW) {
			this.teach_skills_BW.add(SkillDataManager.INSTANCE.getSkillData(teach_skill_BW));
		}

		public void addEvolutions(int evolution) {
			this.evolutions.add(evolution);
		}

		public void addConditionEvolutions(String condition_evolutions) {
			this.condition_evolutions.add(condition_evolutions);
		}
	}
	
	
	/**
	 * シリアルバージョンID
	 * 更新したら変更
	 */
	private static final long serialVersionUID = 1L;
	
	
	//==No,タイプ、とくせい、種族値、努力値
	private final String string_no;//文字列のNo
	private final TypeData[] types;//タイプ
	private final CharacterData[] characters;//とくせい
	private final int[] specs,effs;//種族値,努力値
	//==タマゴ==
	private final EggGroups[] egg_groups;//タマゴグループ
	private final HatchingSteps hatching_step;//孵化歩数
	//==たかさ・おもさ==
	private final int height;//たかさ
	private final int weight;//おもさ
	//==性別比率
	private final int[] sex_ratios;//性別比率
	//==持っている道具==
	private final ItemData[] items;//持っている道具
	//==その他==
	private final FinalExperiences final_ex;//最終経験値
	private final int ease_get;//つかまえやすさ
	private final int basic_ex;//基礎経験値
	private final int initial_natsuki;//初期なつき
	//===覚えるわざ==
	private final SkillData[] lv_skills;//レベルアップで覚えるわざ
	private final Integer[][] learning_lvs;//レベルアップで覚えるわざの覚えるレベル
	private final SkillMachines[] machines;//技マシン
	private final HidenMachines[] hidens;//秘伝マシン
	private final OldSkillMachines[] old_machines;//旧技マシン
	private final SkillData[] egg_skills;//タマゴ技
	private final SkillData[] teach_skills_Pt,teach_skills_HS,teach_skills_BW;//教え技Pt,HS,bw
	//==進化系列==
	private final Integer[] evolutions;//進化系列
	private final String[] condition_evolutions;//進化条件
	

	
	
	private PokeData(String name,int no,TypeData[] types,CharacterData[] characters,int[] specs,int[] effs,
					   int[] egg_groups,int hatching_step,
					   int height,int weight,
					   int[] sex_ratios,
					   ItemData[] items,
					   int final_ex,int ease_get,int basic_ex,int initial_natsuki,
					   SkillData[] lv_skills,Integer[][] learning_lvs,
					   SkillMachines[] machines,HidenMachines[] hidens,OldSkillMachines[] old_machines,
					   SkillData[] egg_skills,
					   SkillData[] teach_skills_Pt,SkillData[] teach_skills_HS,SkillData[] teach_skills_BW,
					   Integer[] evolutions,String[] condition_evolutions) 
	{
		super(name,no);
		// TODO Auto-generated constructor stub
		//Noを文字列にして保存
		StringBuilder sb=new StringBuilder();
		if(no<10){
			sb.append("00");
		}else if(no<100){
			sb.append("0");
		}
		sb.append(no);
		string_no=new String(sb);
		this.types=types;
		this.characters=characters;
		this.specs=specs;
		this.effs=effs;
		
		this.egg_groups=new EggGroups[egg_groups.length];
		for(int i=0,n=egg_groups.length;i<n;i++){
			this.egg_groups[i]=EggGroups.fromInteger(egg_groups[i]);
		}
		this.hatching_step=HatchingSteps.fromInteger(hatching_step);
		
		this.height=height;
		this.weight=weight;
		
		this.sex_ratios=sex_ratios;
		this.items=items;
		
		this.final_ex=FinalExperiences.fromInteger(final_ex);
		this.ease_get=ease_get;
		this.basic_ex=basic_ex;
		this.initial_natsuki=initial_natsuki;
		
		this.lv_skills=lv_skills;
		this.learning_lvs=learning_lvs;
		this.machines=machines;
		this.hidens=hidens;
		this.old_machines=old_machines;
		this.egg_skills=egg_skills;
		this.teach_skills_Pt=teach_skills_Pt;
		this.teach_skills_HS=teach_skills_HS;
		this.teach_skills_BW=teach_skills_BW;
		
		this.evolutions=evolutions;
		this.condition_evolutions=condition_evolutions;
		
	}

	//=============================================================
	//enum
	/**
	 * 持っている道具のレアリティーenum
	 */
	public enum ItemRarities implements Serializable{
		COMMON("通常",0),RARE("レア",1);
		final private String name;
		final private int index;
		ItemRarities(String name,int index){this.name=name;this.index=index;}
		@Override
		public String toString(){return name;}
		public int getIndex(){return index;}
	}

	/**
	 * 特性の種類
	 * @author sanogenma
	 *
	 */
	public enum CharacterTypes implements Serializable{
		FIRST(0),SECOND(1),DREAM(2);
		final private int index;
		CharacterTypes(int index){this.index=index;}
		public int getIndex(){
			return index;
		}
	}
	/**
	 * 教え技のソフトバージョン
	 * @author sanogenma
	 *
	 */
	public enum TeachSkillROMs implements Serializable{
		PT,HS,BW;
	}
	/**
	 * ステータスの種類
	 * @author sanogenma
	 *
	 */
	public enum Statuses implements Serializable{
		H("HP",0),A("攻撃",1),B("防御",2),C("特攻",3),D("特防",4),S("素早",5),TOTAL("合計",6),MIN("最小",7),MAX("最大",8);
		final private String name;
		final private int index;
		Statuses(String name,int index){this.name=name;this.index=index;}
		@Override
		public String toString(){
			return name;
		}
		public int getIndex(){
			return index;
		}
	
		public static Statuses fromString(String name){
			for(Statuses status:values()){
				if(status.toString().equals(name)) return status;
			}
			return null;
		}
	}
	/**
	 * 性別
	 * @author sanogenma
	 */
	public enum Sexes implements Serializable{
		MALE("♂",0),FEMALE("♀",1);
		final private String name;
		final private int index;
		Sexes(String name,int index){this.name=name;this.index=index;}
		@Override
		public String toString(){return name;}
		public int getIndex(){return index;}
	}
	/**
	 * 孵化歩数
	 * @author sanogenma
	 *
	 */
	public enum HatchingSteps implements Serializable{
		_1280(1280,0),_2560(2560,1),_3840(3840,2),_5120(5120,3),_6400(6400,4),
		_7680(7680,5),_8960(8960,6),_10240(10240,7),_20480(20480,8),_30720(30720,9);
		
		private final int index;
		private final int step;
		private final String name;
		HatchingSteps(int step,int index){this.step=step;this.name=step+"歩";this.index=index;}
		@Override public String toString(){return name;}
		public int getStep(){return step;}
		public int getIndex(){return index;}
		
		/**
		 * 文字列配列で取得
		 * @return
		 */
		public static String[] toStringArray(){
			String[] array=new String[values().length];
			for(int i=0,n=array.length;i<n;i++){
				array[i]=values()[i].toString();
			}
			return array;
		}
		
		private static final Map<Integer,HatchingSteps> 
			integerToEnum = new HashMap<Integer, HatchingSteps>();//数値からenumへ
		private static final Map<String,HatchingSteps>
			stringToEnum = new HashMap<String,HatchingSteps>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(HatchingSteps hs : values()){
				integerToEnum.put(hs.index, hs);
				integerToEnum.put(hs.step, hs);
				stringToEnum.put(hs.toString(), hs);
				stringToEnum.put(String.valueOf(hs.step), hs);
			}
		}
		/**
		 * 文字列からHatchingStepsを取得
		 * @param step
		 * @return
		 */
		public static HatchingSteps fromString(String step){
			return stringToEnum.get(step);
		}
		/**
		 * 数値（インデックスか孵化歩数）からHatchingStepsを取得
		 * @param integer
		 * @return
		 */
		public static HatchingSteps fromInteger(int integer){
			return integerToEnum.get(integer);
		}
	}
	/**
	 * タマゴグループ
	 * @author sanogenma
	 *
	 */
	public enum EggGroups implements Serializable{
		陸上("陸上",0),    怪獣("怪獣",1), 水中1("水中1",2),     水中2("水中2",3),      水中3("水中3",4),
		虫("虫",5),       飛行("飛行",6), 妖精("妖精",7),       植物("植物",8),        人型("人型",9),
		不定形("不定形",10),鉱物("鉱物",11),ドラゴン("ドラゴン",12),メタモン("メタモン",13),未発見("未発見",14);
		final private String name;
		final private int index;
		EggGroups(String name,int index){this.name=name;this.index=index;}
		@Override
		public String toString(){return name;}
		public int getIndex(){return index;}
		
		public static final int changeEggGroupToNum(EggGroups group1,EggGroups group2){
			int num=0;
			if(group1!=null) num+=group1.getIndex()*EggGroups.values().length;
			if(group2!=null) num+=group2.getIndex();
			return num;
		}
		
		
		private static final Map<Integer,EggGroups> 
			integerToEnum = new HashMap<Integer, EggGroups>();//数値からenumへ
		private static final Map<String,EggGroups>
			stringToEnum = new HashMap<String,EggGroups>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(EggGroups eg : values()){
				integerToEnum.put(eg.index, eg);
				stringToEnum.put(eg.toString(), eg);
			}
		}
		/**
		 * 文字列からEggGroupsを取得
		 * @param name
		 * @return
		 */
		public static EggGroups fromString(String name){
			return stringToEnum.get(name);
		}
		/**
		 * 数値（インデックス）からEggGroupsを取得
		 * @param integer
		 * @return
		 */
		public static EggGroups fromInteger(int integer){
			return integerToEnum.get(integer);
		}
	}
	/**
	 * 最終経験値
	 * @author sanogenma
	 *
	 */
	public enum FinalExperiences implements Serializable{
		_60(60,0),_80(80,1),_100(100,2),_105(105,3),_125(125,4),_164(164,5);
		final private int num;
		final private int index;
		final private String name;
		FinalExperiences(int num,int index){this.num=num;this.name=num+"万";this.index=index;}
		@Override
		public String toString(){return name;}
		public int getIndex(){return index;}

		/**
		 * 文字列配列で取得
		 * @return
		 */
		public static String[] toStringArray(){
			String[] array=new String[values().length];
			for(int i=0,n=array.length;i<n;i++){
				array[i]=values()[i].toString();
			}
			return array;
		}
		
		private static final Map<String,FinalExperiences> 
			stringToEnum = new HashMap<String, FinalExperiences>();//数値からenumへ
		private static final Map<Integer,FinalExperiences> 
			integerToEnum = new HashMap<Integer, FinalExperiences>();//数値からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(FinalExperiences fe : values()){
				integerToEnum.put(fe.index, fe);
				integerToEnum.put(fe.num, fe);
				stringToEnum.put(fe.toString(), fe);
			}
		}
		/**
		 * 文字列(XX歩)からEggGroupsを取得
		 * @param integer
		 * @return
		 */
		public static FinalExperiences fromString(int string){
			return stringToEnum.get(string);
		}
		
		/**
		 * 数値（index or num）からEggGroupsを取得
		 * @param integer
		 * @return
		 */
		public static FinalExperiences fromInteger(int integer){
			return integerToEnum.get(integer);
		}
	}
	//=============================================================
	/*　ゲッター系
	　・図鑑No
	　・タイプ
	　・とくせい
	　・種族値
	　・努力値
	　・タマゴグループ
	　・孵化歩数
	　・性別比率
	　・最終経験値
	　・つかまえやすさ
	　・基礎経験値
	　・初期なつき
	　・持っている道具
	　・レベルアップで覚える技とそのレベル
	　・技マシン
	　・秘伝マシン
	　・旧技マシン
	　・タマゴ技
	　・教え技(Pt,HS,BW)
	　・進化系列
	
	　・最大物理耐久
	　・最大特殊耐久
	
	　未・全てのわざを取得
	*/
	
	/**
	 * 図鑑Noを文字列で取得(001,015など)
	 * @return
	 */
	public String getNo2String(){
		return string_no;
	}
	
	/**
	 * タイプを取得
	 * @param index:タイプ1or2
	 * @return
	 */
	public TypeData getType(int index) {
		if(index<0 || types.length<=index) return types[0];
		return types[index];
	}
	
	/**
	 * とくせいを取得
	 * @param index:とくせい1or2or夢特性
	 * @return
	 */
	public CharacterData getCharacter(int index) {
		return characters[index];
	}
	/**
	 * とくせいを取得
	 * @param ctype:FIRST,SECOND,DREAM
	 * @return
	 */
	public CharacterData getCharacter(CharacterTypes ctype){
		return characters[ctype.getIndex()];
	}

	/**
	 * 種族値を取得
	 * @param index:H=0,A=1,B=2,C=3,D=4,S=5
	 * @return
	 */
/*	public int getSpec(int index) {
		if(index<0 || Statuses.values().length<=index){
			return getTotalSpec();
		}
		return specs[index];
	}*/
	/**
	 * 種族値を取得
	 * @param status:H,A,B,C,D,S
	 * @return
	 */
	public int getSpec(Statuses status){
		switch(status){
		case H:
		case A:
		case B:
		case C:
		case D:
		case S:
			return specs[status.getIndex()];
		case TOTAL://合計
			return getTotalSpec();
		case MIN://最小
			return getMinSpec();
		case MAX://最大
			return getMaxSpec();
		}
		return 0;
	}
	
	/**
	 * 最小の種族値を返す
	 * @return
	 */
	public int getMinSpec(){
		int min=255;
		for(int spec:specs){
			if(spec<min){
				min=spec;
			}
		}
		return min;
	}
	
	/**
	 * 最大の種族値を返す
	 * @return
	 */
	public int getMaxSpec(){
		int max=0;
		for(int spec:specs){
			if(spec>max){
				max=spec;
			}
		}
		return max;
	}
	/**
	 * 種族値の合計値を取得
	 * @return
	 */
	public int getTotalSpec(){
		int total=0;
		for(int i:specs){
			total+=i;
		}
		return total;
	}

	/**
	 * 努力値を取得
	 * @return:H=0,A=1,B=2,C=3,D=4,S=5
	 */
/*	public int getEff(int index) {
		if(index<0 || Statuses.values().length<=index){
			return getTotalEff();
		}
		return effs[index];
	}*/
	/**
	 * 努力値を取得
	 * @param status:H,A,B,C,D,S
	 * @return
	 */
	public int getEff(Statuses status){
		switch(status){
			case H:
			case A:
			case B:
			case C:
			case D:
			case S:
				return effs[status.index];
			case TOTAL:
				return getTotalEff();
			case MIN://最小
				return getMinEff();
			case MAX://最大
				return getMaxEff();
		}
		return 0;
	}
	
	/**
	 * 努力値の最小値を返す
	 * @return
	 */
	public int getMinEff(){
		int min=3;
		for(int eff:effs){
			if(eff>0 && eff<min){
				min=eff;
			}
		}
		return min;
	}
	
	/**
	 * 努力値の最大値を返す
	 * @return
	 */
	public int getMaxEff(){
		int max=0;
		for(int eff:effs){
			if(eff>max){
				max=eff;
			}
		}
		return max;
	}

	public int getTotalEff(){
		int total=0;
		for(int i:effs){
			total+=i;
		}
		return total;
	}
	/**
	 * タマゴグループを取得
	 * @param index:タマゴグループ1or2
	 * @return
	 */
	public EggGroups getEggGroup(int index) {
		if(index>=egg_groups.length){
			return egg_groups[0];
		}
		return egg_groups[index];
	}

	/**
	 * 孵化歩数を取得
	 * @return
	 */
	public HatchingSteps getHatchingStep() {
		return hatching_step;
	}

	/**
	 * たかさを取得
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * おもさを取得
	 * @return
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * 性別比率
	 * @param index 0:♂ 1:♀
	 * @return
	 */
	public int getSexRatio(int index) {
		return sex_ratios[index];
	}
	/**
	 * 性別比率
	 * @param sex:MALE,FEMALE
	 * @return
	 */
	public int getSexRatio(Sexes sex){
		return sex_ratios[sex.getIndex()];
	}


	/**
	 * 持っている道具を取得
	 * @param index 0:通常　1:レア
	 * @return
	 */
	public ItemData getItem(int index) {
		return items[index];
	}
	/**
	 * 持っている道具を取得
	 * @param ir:COMMON,RARE
	 * @return
	 */
	public ItemData getItem(ItemRarities ir){
		return items[ir.getIndex()];
	}

	/**
	 * 最終経験値を取得
	 * @return
	 */
	public FinalExperiences getFinalEx() {
		return final_ex;
	}

	/**
	 * つかまえやすさ
	 * @return
	 */
	public int getEaseGet() {
		return ease_get;
	}

	/**
	 * 基礎経験値
	 * @return
	 */
	public int getBasicEx() {
		return basic_ex;
	}

	/**
	 * 初期なつき
	 * @return
	 */
	public int getInitialNatsuki() {
		return initial_natsuki;
	}

	/**
	 * レベルアップで覚えるわざを全て取得
	 * @return
	 */
	public SkillData[] getLvSkill() {
		return lv_skills.clone();
	}

	/**
	 * レベルアップで覚える技の覚えるレベルを全て取得
	 * @return
	 */
	public Integer[][] getLearningLv() {
		return learning_lvs.clone();
	}
	/**
	 * skillを覚えるレベルを取得
	 * @param skill
	 * @return
	 */
	public Integer[] getLearningLv(SkillData skill){
		for(int i=0,n=lv_skills.length;i<n;i++){
			if(lv_skills[i].equals(skill)){
				return learning_lvs[i];
			}
		}
		return null;
	}
	/**
	 * 覚える技マシンNoを取得を全て取得
	 * @return
	 */
	public SkillMachines[] getMachine() {
		return machines.clone();
	}
	
	/**
	 * 覚える秘伝マシンを全て取得
	 * @return
	 */
	public HidenMachines[] getHiden() {
		return hidens.clone();
	}

	/**
	 * 覚える旧技マシンを全て取得
	 * @return
	 */
	public OldSkillMachines[] getOldMachine() {
		return old_machines.clone();
	}

	/**
	 * タマゴ技を全て取得
	 * @return
	 */
	public SkillData[] getEggSkill() {
		return egg_skills.clone();
	}

	/**
	 * 教え技(Pt)を全て取得
	 * @return
	 */
	public SkillData[] getTeachSkillPt() {
		return teach_skills_Pt.clone();
	}

	/**
	 * 教え技(HS)を全て取得
	 * @return
	 */
	public SkillData[] getTeachSkillHS() {
		return teach_skills_HS.clone();
	}

	/**
	 * 教え技(BW)を全て取得
	 * @return
	 */
	public SkillData[] getTeachSkillBW() {
		return teach_skills_BW.clone();
	}

	/**
	 * 進化系列を全て取得
	 * @return
	 */
	public Integer[] getEvolutions() {
		return evolutions.clone();
	}

	
	/**
	 * 進化系列を取得
	 * @param index
	 * @return
	 */
	public PokeData getEvolution(int index){
		return PokeDataManager.INSTANCE.getPokeData(evolutions[index]);
	}
	/**
	 * 進化系列で自分が何番目かを返す
	 * @return
	 */
	public int getPositionOfEvolution(){
		int no=getNo();
		for(int i=0,n=evolutions.length;i<n;i++){
			if(evolutions[i]==no){
				return i;
			}
		}
		return -1;
	}
	/**
	 * 進化条件を全て取得
	 * @return
	 */
	public String[] getConditionEvolutions() {
		return condition_evolutions.clone();
	}
	
	/**
	 * 最大物理耐久を取得(（HP種族値＋107）×(int)｛(防御種族値＋52）×1.1｝)
	 * @return
	 */
	public int getMaxPhysicalDefense(){
		return (specs[0]+107)*(int)((specs[2]+52)*1.1);
	}
	/**
	 * 最大特殊耐久を取得(（HP種族値＋107）×(int)｛(特防種族値＋52）×1.1｝)
	 * @return
	 */
	public int getMaxSpecialDefense(){
		return (specs[0]+107)*(int)((specs[4]+52)*1.1);
	}
	/**
	 * すべての覚える技を取得
	 * @return
	 */
	public SkillData[] getAllSkill(){
		Set<SkillData> all_skills=new HashSet<SkillData>();
		//レベルアップで覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.lv_skills));
		//技マシンで覚える技をall_skillsに追加
		  //マシンNoで登録されているのでskills番号に変換してからall_skillsに追加
		for(SkillMachines sm:this.machines){
			all_skills.add(sm.getSkillData());
		}
		//秘伝マシンで覚える技をall_skillsに追加
		for(HidenMachines hm:this.hidens){
			all_skills.add(hm.getSkillData());
		}
		//旧わざマシンで覚える技をall_skillsに追加
		for(OldSkillMachines osm:this.old_machines){
			all_skills.add(osm.getSkillData());
		}
		//タマゴ技で覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.egg_skills));
		//教え技で覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.teach_skills_Pt));
		all_skills.addAll(Arrays.asList(this.teach_skills_HS));
		all_skills.addAll(Arrays.asList(this.teach_skills_BW));
		//all_skillsをint型配列にして返す
		final SkillData[] array_all_skills=all_skills.toArray(new SkillData[0]);
		Arrays.sort(array_all_skills);
		return array_all_skills;
	}

	//=============================================================
	//has系
	/*
	  済・タイプ
	　済・とくせい
	　済・タマゴグループ
	　済・持っている道具
	　済・レベルアップで覚える技
	　済・技マシン、秘伝マシン、旧技マシン
	　済・タマゴ技
	　済・教え技(Pt,HS,BW) 
	 */
	/**
	 * とくせいにcharaが含まれているかどうか
	 * @param chara
	 * @return
	 */
	public boolean hasCharacter(CharacterData chara){
		for(CharacterData c:characters){
			if(c.equals(chara)) return true;
		}
		return false;
	}
	/**
	 * タマゴグループにgroupが含まれているかどうか
	 * @param group
	 * @return
	 */
	public boolean hasEggGroup(EggGroups egg_group){
		for(EggGroups g:egg_groups){
			if(g!=null && g.equals(egg_group)) return true;
		}
		return false;
	}
	
	/**
	 * 持っている道具にitemがブ組まれているかどうか
	 * @param item
	 * @return
	 */
	public boolean hasItem(ItemData item){
		for(ItemData i:items){
			if(i!=null && i.equals(item)) return true;
		}
		return false;
	}
	
	/**
	 * skillを覚えるかどうか
	 * @param skill
	 * @return skillを覚えればtrue
	 */
	public boolean hasSkill(SkillData skill){
		//レベルアップで覚える技
		if(hasSkillByLvSkill(skill)) return true;
		//わざマシンで覚えるかどうか
		if(hasSkillByMachine(skill)) return true;
		//タマゴ技
		if(hasSkillByEggSkill(skill)) return true;
		//教え技(Pt,HS,BW) 
		if(hasSkillByTeachSkill(skill))return true;
		
		int position=getPositionOfEvolution();
		if(position>0){
			return PokeDataManager.INSTANCE.getPokeData(evolutions[position-1]).hasSkill(skill);
		}
		return false;
	}
	
	/**
	 * skillをレベルアップで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByLvSkill(SkillData skill){
		for(SkillData lv_skill:lv_skills){
			if(lv_skill.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * わざマシン、秘伝マシン、旧わざマシンで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByMachine(SkillData skill){
		//技マシン
		if(hasSkillBySkillMachine(SkillMachines.fromString(skill.toString()))) return true;
		//秘伝マシン
		if(hasSkillByHidenMachine(HidenMachines.fromString(skill.toString()))) return true;
		//旧わざマシン
		if(hasSkillByOldSkillMachine(OldSkillMachines.fromString(skill.toString()))) return true;
		return false;
	}
	/**
	 * skillを技マシンで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillBySkillMachine(SkillMachines skill){
		//skillが技マシンかどうかをチェックしマシンNo(machine_no)に変換
		//this.machineの中にmachine_noがあるかをチェックしあればtrue
		for(SkillMachines machine:machines){
			if(machine.equals(skill)){
				return true;
			}
		}
		return false;
	}
	/**
	 * skillを秘伝マシンで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByHidenMachine(HidenMachines skill){
		//skillが秘伝マシンかどうかをチェックし秘伝No(hiden_no)に変換
		//this.hidensの中にhiden_noがあるかをチェックしあればtrue
		for(HidenMachines hiden:hidens){
			if(hiden.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * skillを旧技マシンで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByOldSkillMachine(OldSkillMachines skill){
		//skillが旧わざマシンかどうかをチェックし旧技マシンNo(old_machine_no)に変換
		//this.old_machineにold_machine_noがあるかをチェックしあればtrue
		for(OldSkillMachines old_machine:old_machines){
			if(old_machine.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * skillをタマゴ技で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByEggSkill(SkillData skill){
		//this.egg_skillにskillがあるかをチェックしあればtrue
		for(SkillData egg:egg_skills){
			if(egg.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * skillを教え技で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkill(SkillData skill){
		//Ptで覚えるかどうかをチェックしtrueならreturn
		if(hasSkillByTeachSkillPt(skill)) return true;
		//HSで覚えるかどうかをチェックしtrueならreturn
		if(hasSkillByTeachSkillHS(skill)) return true;
		//BWで覚えるかどうかをチェックしtrueならreturn
		if(hasSkillByTeachSkillBW(skill)) return true;
		//どれでも覚えないならfalseを返す;
		return false;
	}
	/**
	 * skillを教え技(Pt)で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkillPt(SkillData skill){
		//this.teach_skill_Ptにskillがあるかをチェックしあればtrue
		for(SkillData teach:teach_skills_Pt){
			if(teach.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * skillを教え技(HS)で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkillHS(SkillData skill){
		//this.teach_skills_HSにskillがあるかをチェックしあればtrue
		for(SkillData teach:teach_skills_HS){
			if(teach.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * skillを教え技(BW)で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkillBW(SkillData skill){
		//this.teach_skill_BWにskillがあるかをチェックしあればtrue
		for(SkillData teach:teach_skills_BW){
			if(teach.equals(skill)) return true;
		}
		return false;
	}
	/**
	 * タイプにtypeが含まれているかどうか
	 * @param type
	 * @return
	 */
	public boolean hasType(TypeData type){
		for(TypeData t:types){
			if(t!=null && t.equals(type)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasForm(){
		final int no=getNo();
		int position=0;
		for(int i=0,n=evolutions.length;i<n;i++){
			if(evolutions[i]==no){
				position=i;
				break;
			}
		}
		if(!condition_evolutions[position].equals("フォルム")){
			return evolutions.length-1==position+1 && condition_evolutions[position+1].equals("フォルム");
		}
		return false;
	}
	//=============================================================
	//is系
	/**
	 * 1進化後かどうか
	 */
	public boolean isAfterOneEvolution(){
		final int no=getNo();
		
		if(evolutions.length>1){//進化系が存在する
			if(evolutions[1]==no && //1進化後
			   !condition_evolutions[1].equals("フォルム")){//フォルムじゃない
				return true;
			}
		}
		return false;
	}
	/**
	 * 2進化後かどうか
	 * @return
	 */
	public boolean isAfterTwoEvolution(){
		final int no=getNo();
		
		if(evolutions.length>2){//2進化する
			if(evolutions[2]==no &&//2進化後
			   !condition_evolutions[2].equals("フォルム")){//フォルムじゃない
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 最終進化系かどうかを返す
	 * @return
	 */
	public boolean isFinalEvolution(){
		final int no=getNo();
		//Evolutions配列の最後の場合
		if(evolutions[evolutions.length-1]==no){
			return true;
		}
		//Evolutions配列のnoの次の進化条件が『フォルム』のとき
		for(int i=0,n=evolutions.length-1;i<n;i++){
			if(evolutions[i]==no){
				return this.condition_evolutions[i+1].equals("フォルム");
			}
		}
		return false;
	}
	/**
	 * 進化可能かどうかを返す
	 * @param no
	 * @return
	 */
	public boolean isEvolutionable(){
		final int position=getPositionOfEvolution();
		
		//進化系が存在するポケモンで
		if(evolutions.length>position+1){
			//次のポケモンがフォルムでない場合
			if(!condition_evolutions[position+1].equals("フォルム")){
				return true;
			}		
		}
		return false;
	}
	/**
	 * 進化系列無し
	 * @param no
	 * @return
	 */
	public boolean isNotEvolution(){
		//進化系列が一匹の場合
		if(evolutions.length==1){
			return true;
		}
		//フォルムでかつフォルムでないポケが一種類の場合
		if(condition_evolutions[1].equals("フォルム")){
			return true;
		}
		return false;
	}
	/**
	 * たねポケモンであるかどうかを返す
	 * @param no
	 * @return
	 */
	public boolean isSeedPokemon(){
		final int no=getNo();
		//Evolutions配列の最初
		if(evolutions[0]==no){
			return true;
		}
		//フォルムでかつフォルムでない場合が一種類
		if(condition_evolutions[1].equals("フォルム")){
			return true;
		}
		return false;
	}
	
}
