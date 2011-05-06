package kuro075.poke.pokedatabase.data_base.skill;

import java.util.HashMap;
import java.util.Map;

import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;

public class SkillData extends BasicData{

	/**
	 * 攻撃対象
	 * @author sanogenma
	 *
	 */
	public enum AttackTargets{
		ALL_ENEMY("相手全体",0),MYSELF("自分",1),OTHERS("自分以外",2),ALL("全体の場",3),
		NORMAL("通常",4),INDEFINITE("不定",5),OURS("味方の場",6),RANDOM("ランダム1体",7),
		NON("-",-1);
		
		private final String name;
		private final int index;
		private static final Map<Integer,AttackTargets> 
			integerToEnum = new HashMap<Integer, AttackTargets>();//数値からenumへ
		private static final Map<String,AttackTargets>
			stringToEnum = new HashMap<String,AttackTargets>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(AttackTargets at : values()){
				integerToEnum.put(at.index, at);
				stringToEnum.put(at.toString(), at);
			}
		}
		/**
		 * 数値（インデックスか孵化歩数）からAttackTargetsを取得
		 * @param integer
		 * @return
		 */
		public static AttackTargets fromInteger(int integer){
			return integerToEnum.get(integer);
		}
		/**
		 * 文字列からAttackTargetsを取得
		 * @param step
		 * @return
		 */
		public static AttackTargets fromString(String string){
			return stringToEnum.get(string);
		}
		AttackTargets(String name,int index){this.name=name;this.index=index;}
		public int getIndex(){return index;}
		@Override
		public String toString(){return name;}
	}
	protected static class Builder{

		private String name="-";//名前
		private int no=-1;//管理ナンバー
		private TypeData type=null;//タイプ
		private SkillClasses skill_class=null;//分類
		private int power=0;//威力
		private int hit=0;//命中
		private int pp=0;//PP
		private WhetherDirect direct=null;//直接攻撃かどうか
		private AttackTargets target=null;//攻撃対象
		private String effect="-";//効果
		private int priority=0;//優先度
		
		protected Builder(){}
		
		public SkillData build(){
			return new SkillData(name,no,type,skill_class,power,hit,pp,direct,target,effect,priority);
		}
		
		public void setDirect(WhetherDirect direct) {
			this.direct = direct;
		}

		public void setEffect(String effect) {
			this.effect = effect;
		}
		
		public void setHit(int hit) {
			this.hit = hit;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public void setPower(int power) {
			this.power = power;
		}

		public void setPp(int pp) {
			this.pp = pp;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public void setSkillClass(SkillClasses skill_class) {
			this.skill_class = skill_class;
		}

		public void setTarget(AttackTargets target) {
			this.target = target;
		}

		public void setType(TypeData type) {
			this.type = type;
		}
		
		
	}
	
	
	/**
	 * 技マシンの種類
	 * @author sanogenma
	 *
	 */
	public enum MachineTypes{
		MACHINE("わざマシン"),HIDEN("ひでんマシン"),OLD_MACHINE("旧わざマシン");
		final private String name;
		MachineTypes(String name){this.name=name;}
		@Override
		public String toString(){return name;}
	}

	//=========================================================
	/*
	　・タイプ：TypeData type;
	　・分類：SkillClasses skill_class;
	　・威力：int power;
	　・命中率：int hit;
	　・PP：int pp;
	　・直接攻撃かどうか：WhetherDirect direct;
	　・攻撃対象：AttackTargets target;
	　・効果：String effect;
	　・優先度：int priority;
	*/

	/**
	 * 分類
	 */
	public enum SkillClasses{
		PHYSICAL("物理",0),SPECIAL("特殊",1),CHANGE("変化",2);
		
		private final String name;
		private final int index;
		private static final Map<Integer,SkillClasses> 
			integerToEnum = new HashMap<Integer, SkillClasses>();//数値からenumへ
		private static final Map<String,SkillClasses>
			stringToEnum = new HashMap<String,SkillClasses>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(SkillClasses sc : values()){
				integerToEnum.put(sc.index, sc);
				stringToEnum.put(sc.toString(), sc);
			}
		}
		/**
		 * 数値（インデックスか孵化歩数）からSkillClassesを取得
		 * @param integer
		 * @return
		 */
		public static SkillClasses fromInteger(int integer){
			return integerToEnum.get(integer);
		}
		/**
		 * 文字列からSkillClassesを取得
		 * @param step
		 * @return
		 */
		public static SkillClasses fromString(String string){
			return stringToEnum.get(string);
		}
		SkillClasses(String name,int index){this.name=name;this.index=index;}
		public int getIndex(){return index;}
		@Override
		public String toString(){return name;}
	}
	/**
	 * 直接攻撃
	 * @author sanogenma
	 *
	 */
	public enum WhetherDirect{
		DIRECT("直接攻撃",0,true),INDIRECT("非直接攻撃",1,false);
		
		private final String name;
		private final int index;
		private final boolean direct;
		private static final Map<Integer,WhetherDirect> 
			integerToEnum = new HashMap<Integer, WhetherDirect>();//数値からenumへ
		private static final Map<String,WhetherDirect>
			stringToEnum = new HashMap<String,WhetherDirect>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(WhetherDirect wd : values()){
				integerToEnum.put(wd.index, wd);
				stringToEnum.put(wd.toString(), wd);
			}
		}
		/**
		 * 数値（インデックスか孵化歩数）からWhetherDirectを取得
		 * @param integer
		 * @return
		 */
		public static WhetherDirect fromInteger(int integer){
			return integerToEnum.get(integer);
		}
		/**
		 * 文字列からWhetherDirectを取得
		 * @param step
		 * @return
		 */
		public static WhetherDirect fromString(String string){
			return stringToEnum.get(string);
		}
		
		public static WhetherDirect getWhetherDirectFromBoolean(boolean direct){
			if(direct){
				return DIRECT;
			}
			return INDIRECT;
		}
		WhetherDirect(String name,int index,boolean direct){this.name=name;this.index=index;this.direct=direct;}
		public int getIndex(){return index;}
		public boolean isDirect(){return direct;}
		@Override
		public String toString(){return name;}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TypeData type;//タイプ
	private final SkillClasses skill_class;//分類
	private final int power;//威力
	private final int hit;//命中
	private final int pp;//PP
	private final WhetherDirect direct;//直接攻撃かどうか
	
	
	private final AttackTargets target;//攻撃対象
	
	//=========================================================
	//enum
	

	private final String effect;//効果
	
	private final int priority;//優先度
	
	private SkillData(String name,int no,TypeData type,SkillClasses skill_class,int power,int hit,
					  int pp,WhetherDirect direct,AttackTargets target,String effect,int priority) {
		super(name,no);
		this.type=type;
		this.skill_class=skill_class;
		this.power=power;
		this.hit=hit;
		this.pp=pp;
		this.direct=direct;
		this.target=target;
		this.effect=effect;
		this.priority=priority;
	}
	
	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return getNo()-another.getNo();
	}
	/**
	 * 直接攻撃かどうかを取得
	 * @return 直接攻撃かどうか
	 */
	public WhetherDirect getDirect() {
		return direct;
	}

	/**
	 * 効果を取得
	 * @return　わざの効果
	 */
	public String getEffect() {
		return effect;
	}

	/**
	 * 命中率を取得
	 * @return　わざの命中率
	 */
	public int getHit() {
		return hit;
	}

	/**
	 * 効果の概要を取得（未完成）
	 * @return
	 */
	public String getOutlineEffect(){
		return getEffect();
	}

	/**
	 * 威力を取得
	 * @return わざの威力
	 */
	public int getPower() {
		return power;
	}

	/**
	 * PPを取得
	 * @return　わざのPP
	 */
	public int getPp() {
		return pp;
	}

	/**
	 * 優先度を取得
	 * @return　わざの優先度
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * 分類を取得
	 * @return　わざの分類
	 */
	public SkillClasses getSkillClass() {
		return skill_class;
	}

	
	/**
	 * 攻撃対象を取得
	 * @return　わざの攻撃対象
	 */
	public AttackTargets getTarget() {
		return target;
	}
	//=========================================================
	//ゲッター系
	/**
	 * タイプを取得
	 * @return わざのタイプ
	 */
	public TypeData getType() {
		return type;
	}

	//=========================================================


}
