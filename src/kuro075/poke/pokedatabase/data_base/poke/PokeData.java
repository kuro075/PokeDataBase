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
		private int[] characters=new int[3];//とくせい
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
		private int[] items=new int[2];//持っている道具
		//==その他==
		private int final_ex=0;//最終経験値
		private int ease_get=0;//つかまえやすさ
		private int basic_ex=0;//基礎経験値
		private int initial_natsuki=0;//初期なつき
		//===覚えるわざ==
		private List<Integer> lv_skills=new ArrayList<Integer>();//レベルアップで覚えるわざ
		private List<Integer[]> learning_lvs=new ArrayList<Integer[]>();//レベルアップで覚えるわざの覚えるレベル
		private List<Integer> machines=new ArrayList<Integer>(),hidens=new ArrayList<Integer>(),old_machines=new ArrayList<Integer>();//技マシン、秘伝マシン、旧技マシン
		private List<Integer> egg_skills=new ArrayList<Integer>();//タマゴ技
		private List<Integer> teach_skills_Pt=new ArrayList<Integer>(),teach_skills_HS=new ArrayList<Integer>(),teach_skills_BW=new ArrayList<Integer>();//教え技Pt,HS,bw
		//==進化系列==
		private List<Integer> evolutions=new ArrayList<Integer>();//進化系列
		private List<String> condition_evolutions=new ArrayList<String>();//進化条件

		protected Builder(){
			Arrays.fill(types, -1);
			Arrays.fill(characters, -1);
			Arrays.fill(egg_groups, -1);
			Arrays.fill(specs,0);
			Arrays.fill(effs, 0);
			Arrays.fill(sex_ratios, 0);
			Arrays.fill(items, -1);
			
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
					   (Integer[])lv_skills.toArray(new Integer[0]),(Integer[][])learning_lvs.toArray(new Integer[0][]),
					   (Integer[])machines.toArray(new Integer[0]),(Integer[])hidens.toArray(new Integer[0]),(Integer[])old_machines.toArray(new Integer[0]),
					   (Integer[])egg_skills.toArray(new Integer[0]),
					   (Integer[])teach_skills_Pt.toArray(new Integer[0]),(Integer[])teach_skills_HS.toArray(new Integer[0]),(Integer[])teach_skills_BW.toArray(new Integer[0]),
					   (Integer[])evolutions.toArray(new Integer[0]),(String[])condition_evolutions.toArray(new String[0])); 
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
			this.characters[index] = character;
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

		public void setItem(int index,int item) {
			this.items[index] = item;
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
			this.lv_skills.add(lv_skill);
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
			this.machines.add(machine);
		}

		public void addHiden(int hiden) {
			this.hidens.add(hiden);
		}

		public void addOldMachine(int old_machine) {
			this.old_machines.add(old_machine);
		}

		public void addEggSkill(int egg_skill) {
			this.egg_skills.add(egg_skill);
		}

		public void addTeachSkillPt(int teach_skill_Pt) {
			this.teach_skills_Pt.add(teach_skill_Pt);
		}

		public void addTeachSkillHS(int teach_skill_HS) {
			this.teach_skills_HS.add(teach_skill_HS);
		}

		public void addTeachSkillBW(int teach_skill_BW) {
			this.teach_skills_BW.add(teach_skill_BW);
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
	private final int no;//図鑑No
	private final TypeData[] types;//タイプ
	private final int[] characters;//とくせい
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
	private final int[] items;//持っている道具
	//==その他==
	private final FinalExperiences final_ex;//最終経験値
	private final int ease_get;//つかまえやすさ
	private final int basic_ex;//基礎経験値
	private final int initial_natsuki;//初期なつき
	//===覚えるわざ==
	private final Integer[] lv_skills;//レベルアップで覚えるわざ
	private final Integer[][] learning_lvs;//レベルアップで覚えるわざの覚えるレベル
	private final Integer[] machines,hidens,old_machines;//技マシン、秘伝マシン、旧技マシン
	private final Integer[] egg_skills;//タマゴ技
	private final Integer[] teach_skills_Pt,teach_skills_HS,teach_skills_BW;//教え技Pt,HS,bw
	//==進化系列==
	private final Integer[] evolutions;//進化系列
	private final String[] condition_evolutions;//進化条件
	

	
	
	private PokeData(String name,int no,TypeData[] types,int[] characters,int[] specs,int[] effs,
					   int[] egg_groups,int hatching_step,
					   int height,int weight,
					   int[] sex_ratios,
					   int[] items,
					   int final_ex,int ease_get,int basic_ex,int initial_natsuki,
					   Integer[] lv_skills,Integer[][] learning_lvs,
					   Integer[] machines,Integer[] hidens,Integer[] old_machines,
					   Integer[] egg_skills,
					   Integer[] teach_skills_Pt,Integer[] teach_skills_HS,Integer[] teach_skills_BW,
					   Integer[] evolutions,String[] condition_evolutions) 
	{
		super(name);
		// TODO Auto-generated constructor stub
		this.no=no;
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
		H("HP",0),A("攻撃",1),B("防御",2),C("特攻",3),D("特防",4),S("素早",5);
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
	 * 図鑑Noを取得
	 * @return
	 */
	public int getNo() {
		return no;
	}

	/**
	 * タイプを取得
	 * @param index:タイプ1or2
	 * @return
	 */
	public TypeData getType(int index) {
		if(index>=types.length) return types[0];
		return types[index];
	}
	
	/**
	 * とくせいを取得
	 * @param index:とくせい1or2or夢特性
	 * @return
	 */
	public int getCharacter(int index) {
		return characters[index];
	}
	/**
	 * とくせいを取得
	 * @param ctype:FIRST,SECOND,DREAM
	 * @return
	 */
	public int getCharacter(CharacterTypes ctype){
		return characters[ctype.getIndex()];
	}

	/**
	 * 種族値を取得
	 * @param index:H=0,A=1,B=2,C=3,D=4,S=5
	 * @return
	 */
	public int getSpec(int index) {
		if(index<0 || Statuses.values().length<=index){
			return getTotalSpec();
		}
		return specs[index];
	}
	/**
	 * 種族値を取得
	 * @param status:H,A,B,C,D,S
	 * @return
	 */
	public int getSpec(Statuses status){
		return specs[status.getIndex()];
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
	public int getEff(int index) {
		if(index<0 || Statuses.values().length<=index){
			return getTotalEff();
		}
		return effs[index];
	}
	/**
	 * 努力値を取得
	 * @param status:H,A,B,C,D,S
	 * @return
	 */
	public int getEff(Statuses status){
		return effs[status.index];
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
	public int getItem(int index) {
		return items[index];
	}
	/**
	 * 持っている道具を取得
	 * @param ir:COMMON,RARE
	 * @return
	 */
	public int getItem(ItemRarities ir){
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
	public Integer[] getLvSkill() {
		return lv_skills.clone();
	}

	/**
	 * レベルアップで覚える技の覚えるレベルを全て取得
	 * @return
	 */
	public Integer[][] getLearning_lv() {
		return learning_lvs.clone();
	}

	/**
	 * 覚える技マシンNoを取得を全て取得
	 * @return
	 */
	public Integer[] getMachine() {
		return machines.clone();
	}
	
	/**
	 * 覚える秘伝マシンを全て取得
	 * @return
	 */
	public Integer[] getHiden() {
		return hidens.clone();
	}

	/**
	 * 覚える旧技マシンを全て取得
	 * @return
	 */
	public Integer[] getOldMachine() {
		return old_machines.clone();
	}

	/**
	 * タマゴ技を全て取得
	 * @return
	 */
	public Integer[] getEggSkill() {
		return egg_skills.clone();
	}

	/**
	 * 教え技(Pt)を全て取得
	 * @return
	 */
	public Integer[] getTeachSkillPt() {
		return teach_skills_Pt.clone();
	}

	/**
	 * 教え技(HS)を全て取得
	 * @return
	 */
	public Integer[] getTeachSkillHS() {
		return teach_skills_HS.clone();
	}

	/**
	 * 教え技(BW)を全て取得
	 * @return
	 */
	public Integer[] getTeachSkillBW() {
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
	 * 進化条件を全て取得
	 * @return
	 */
	public String[] getCondition_evolutions() {
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
	public Integer[] getAllSkill(){
		Set<Integer> all_skills=new HashSet<Integer>();
		//レベルアップで覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.lv_skills));
		//技マシンで覚える技をall_skillsに追加
		  //マシンNoで登録されているのでskills番号に変換してからall_skillsに追加
		//タマゴ技で覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.egg_skills));
		//教え技で覚える技をall_skillsに追加
		all_skills.addAll(Arrays.asList(this.teach_skills_Pt));
		all_skills.addAll(Arrays.asList(this.teach_skills_HS));
		all_skills.addAll(Arrays.asList(this.teach_skills_BW));
		//all_skillsをint型配列にして返す
		final Integer[] array_all_skills=(Integer[])all_skills.toArray(new Integer[0]);
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
	　・技マシン、秘伝マシン、旧技マシン
	　済・タマゴ技
	　済・教え技(Pt,HS,BW) 
	 */
	/**
	 * とくせいにcharaが含まれているかどうか
	 * @param chara
	 * @return
	 */
	public boolean hasCharacter(int chara){
		return Arrays.binarySearch(characters,chara)>=0;
	}
	/**
	 * タマゴグループにgroupが含まれているかどうか
	 * @param group
	 * @return
	 */
	public boolean hasEggGroup(int group){
		return Arrays.binarySearch(egg_groups, group)>=0;
	}
	
	/**
	 * 持っている道具にitemがブ組まれているかどうか
	 * @param item
	 * @return
	 */
	public boolean hasItem(int item){
		//this.itemsにitemがあるかチェックしてあればtrue
		return Arrays.binarySearch(this.items, item)>=0;
	}
	/**
	 * skillをレベルアップで覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByLvSkill(int skill){
		return Arrays.binarySearch(lv_skills, skill)>=0;
	}
	/**
	 * skillを技マシンで覚えるかどうか(未実装)
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByMachine(int skill){
		//skillが技マシンかどうかをチェックしマシンNo(machine_no)に変換
		//this.machineの中にmachine_noがあるかをチェックしあればtrue
		return false;
	}
	/**
	 * skillを秘伝マシンで覚えるかどうか(未実装)
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByHiden(int skill){
		//skillが秘伝マシンかどうかをチェックし秘伝No(hiden_no)に変換
		//this.hidensの中にhiden_noがあるかをチェックしあればtrue
		return false;
	}
	/**
	 * skillを旧技マシンで覚えるかどうか(未実装)
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByOldMachine(int skill){
		//skillが旧わざマシンかどうかをチェックし旧技マシンNo(old_machine_no)に変換
		//this.old_machineにold_machine_noがあるかをチェックしあればtrue
		return false;
	}
	/**
	 * skillをタマゴ技で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByEggSkill(int skill){
		//this.egg_skillにskillがあるかをチェックしあればtrue
		return Arrays.binarySearch(egg_skills, skill)>=0;
	}
	/**
	 * skillを教え技で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkill(int skill){
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
	public boolean hasSkillByTeachSkillPt(int skill){
		//this.teach_skill_Ptにskillがあるかをチェックしあればtrue
		return Arrays.binarySearch(teach_skills_Pt, skill)>=0;
	}
	/**
	 * skillを教え技(HS)で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkillHS(int skill){
		//this.teach_skills_HSにskillがあるかをチェックしあればtrue
		return Arrays.binarySearch(teach_skills_HS, skill)>=0;
	}
	/**
	 * skillを教え技(BW)で覚えるかどうか
	 * @param skill
	 * @return
	 */
	public boolean hasSkillByTeachSkillBW(int skill){
		//this.teach_skill_BWにskillがあるかをチェックしあればtrue
		return Arrays.binarySearch(teach_skills_BW, skill)>=0;
	}
	/**
	 * タイプにtypeが含まれているかどうか
	 * @param type
	 * @return
	 */
	public boolean hasType(int type){
		return Arrays.binarySearch(this.types, type)>=0;
	}
	//=============================================================
	//is系
	/**
	 * 最終進化系かどうかを返す
	 * @return
	 */
	public boolean isFinalEvolution(){
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
	 * 未進化かどうかを返す(進化しないポケモンは含まない)
	 * @param no
	 * @return
	 */
	public boolean isFirstEvolution(){
		//進化系が存在するポケモンで
		if(evolutions.length>1){
			//二番目がフォルムでなく、進化系列の最初の場合
			if(!condition_evolutions[1].equals("フォルム") && evolutions[0]==no){
				return true;
			}		
		}
		return false;
	}
	/**
	 * 無進化かどうかを返す
	 * @param no
	 * @return
	 */
	public boolean isnotEvolution(){
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
	
	/**
	 * テストコード
	 */
	public String getTestString(){
		return "No."+no+":"+toString()+","+types[0]+"/"+types[1]+
				" ,"+characters[0]+"/"+characters[1]+"/"+characters[2]+" ,種族値:"+specs+",努力値:"+effs+
				" ,タマゴグループ:"+Arrays.deepToString(egg_groups)+" ,孵化歩数:"+hatching_step+",高さ:"+height+",重さ:"+weight+
				",性別比率:"+sex_ratios[0]+"/"+sex_ratios[1]+",持っている道具:"+items[0]+"/"+items[1]+",最終経験値:"+final_ex+",初期なつき:"+initial_natsuki+
				",レベルアップで覚えるわざ:"+lv_skills+",覚えるレベル:"+learning_lvs+",技マシン:"+machines+",ひでん:"+hidens+",旧マシン:"+old_machines+
				",タマゴ技:"+egg_skills+",教え技："+teach_skills_Pt+","+teach_skills_HS+","+teach_skills_BW+
				",進化系列:"+evolutions+"/"+Arrays.deepToString(condition_evolutions);
	}
}
