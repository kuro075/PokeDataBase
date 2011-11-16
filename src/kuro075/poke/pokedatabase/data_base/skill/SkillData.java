package kuro075.poke.pokedatabase.data_base.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.data_base.viewable_informations.SkillViewableInformations;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.skill_book.skill_page.SkillPageActivity;

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
	
	/**
	 * ビルダー
	 * @author sanogenma
	 *
	 */
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
		private List<SkillKind> kind_list=new ArrayList<SkillKind>();
		
		protected Builder(){}
		
		public SkillData build(){
			return new SkillData(name,no,type,skill_class,power,hit,pp,direct,target,effect,priority,kind_list.toArray(new SkillKind[0]));
		}
		
		public void addSkillKind(SkillKind kind){
			kind_list.add(kind);
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

	//=========================================================

	/**
	 * わざの種類
	 * 先制,能力ランク(急所),一撃必殺,後攻,反射,
	 * 状態異常（メロメロ)、ひるみ,通常,反動,連続(一ターン中)、
	 * 持続(げきりん系ころがる系),溜め,回復,必中,天候（ウェザボ、光合成系、雨乞い系、）,
	 * 威力変動（きしかいせい系、ふんか系、すばやさ系、重さ系、なつき系）,固定ダメージ,条件下威力二倍,誓い,ダブルトリプル用,
	 * 設置（全体の場、自分の場、相手の場）,交代(自分、相手),特性変化,タイプ変化,技禁止,
	 * 技変化,吸収,束縛,もちもの操作,効果不定(ゆびをふる・ねこのて),その他
	 * @author sanogenma
	 *
	 */
	public enum SkillKind{
		UNUSUAL("状態異常"),KILL("一撃必殺"),DOUBLE_POW("威力2倍"),POW_CHANGE("威力変動"),RECOVERY("回復"),
		ABSORPTION("吸収"),CRITICAL("急所"),INDEFINITE("効果不定"),LAST("後攻"),CHANGE("交代"),
		FIXED_DAMAGE("固定ダメージ"),KEEP("持続"),BOMB("自爆"),FIELD("設置"),FIRST("先制"),RESTRAINTS("束縛"),
		TYPE("タイプ変化"),DOUBLES("ダブルトリプル用"),CHAGE("溜め"),VOW("誓い"),NORMAL("通常"),
		WEATHER("天候"),CHARACTER("特性変化"),STATUS("能力値"),STATUS_RANK("能力ランク"),REFLECT("反射"),REACTION("反動"),
		HIT("必中"),PP("PPダメージ"),SHRINK("ひるみ"),DEFENSE("防ぐ"),ITEM("もちもの操作"),CONTINUE("連続"),BAN_SKILL("わざ制限"),
		CHANGE_SKILL("わざ変化"),OTHER("その他");
		
		private final String name;
		SkillKind(String name){this.name=name;}
		public static SkillKind fromName(String name){
			for(SkillKind kind:values()){
				if(kind.toString().equals(name)){
					return kind;
				}
			}
			return null;
		}
		
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
	　・覚えるポケモンリスト List<PokeData> poke_list,lv_poke_list,machine_poke_list,egg_poke_list,teach_poke_list
	*/
	private static final long serialVersionUID = 1L;
	private final TypeData type;//タイプ
	private final SkillClasses skill_class;//分類
	private final int power;//威力
	private final int hit;//命中
	private final int pp;//PP
	private final WhetherDirect direct;//直接攻撃かどうか
	private final AttackTargets target;//攻撃対象
	private final String effect;//効果
	private final int priority;//優先度
	private final SkillKind[] skill_kind;//わざの種類
	
	//覚えるポケモンリスト lv,machine,egg,teach
	private final List<PokeData> poke_list=new ArrayList<PokeData>(),lv_poke_list=new ArrayList<PokeData>(),machine_poke_list=new ArrayList<PokeData>(),egg_poke_list=new ArrayList<PokeData>(),teach_poke_list=new ArrayList<PokeData>();
	private boolean flag_poke_list=false;
	
	private SkillData(String name,int no,TypeData type,SkillClasses skill_class,int power,int hit,
					  int pp,WhetherDirect direct,AttackTargets target,String effect,int priority,SkillKind[] skill_kind) {
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
		this.skill_kind=skill_kind;
	}
	
	@Override
	public int compareTo(BasicData another) {
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
	 * 覚えるポケモンの数を取得
	 * @return
	 */
	public int getNumPoke(){
		initPokeList();
		return poke_list.size();
	}

	/**
	 * タマゴわざで覚えるポケモンの数を取得
	 * @return
	 */
	public int getNumPokeByEgg(){
		initPokeList();
		return egg_poke_list.size();
	}

	/**
	 * Lvで覚えるポケモンの数を取得
	 * @return
	 */
	public int getNumPokeByLv(){
		initPokeList();
		return lv_poke_list.size();
	}

	/**
	 * わざマシンで覚えるポケモンの数を取得
	 * @return
	 */
	public int getNumPokeByMachine(){
		initPokeList();
		return machine_poke_list.size();
	}

	/**
	 * 教えわざで覚えるポケモンの数を取得
	 * @return
	 */
	public int getNumPokeByTeach(){
		initPokeList();
		return teach_poke_list.size();
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
	/**
	 * タイプを取得
	 * @return わざのタイプ
	 */
	public TypeData getType() {
		return type;
	}
	
	/**
	 * 種類を全て取得
	 * @return
	 */
	public SkillKind[] getAllSkillKind(){
		return skill_kind.clone();
	}
	/**
	 * このわざを覚えるポケモンのリストを初期化
	 */
	public void initPokeList(){
		if(!flag_poke_list){
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasSkill(this)){
					poke_list.add(poke);
					if(poke.hasSkillByLvSkill(this)){
						lv_poke_list.add(poke);
					}
					if(poke.hasSkillByMachine(this)){
						machine_poke_list.add(poke);
					}
					if(poke.hasSkillByEggSkill(this)){
						egg_poke_list.add(poke);
					}
					if(poke.hasSkillByTeachSkill(this)){
						teach_poke_list.add(poke);
					}
				}
			}
			flag_poke_list=true;
		}		
	}
	
	//=========================================================

	/**
	 * 技のデータを表示するダイアログを開く
	 * @param skillname
	 */
	public void openDialog(final Context context){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.dialog_skilldata,null);
		((TableLayout)layout.findViewById(R.id.tablelayout)).setStretchAllColumns(true);
		//一段目
		//タイプ
		final TextView type=(TextView)layout.findViewById(R.id.text_type);
		type.setText(getType().toString());
		type.setTextColor(Color.WHITE);
		type.setBackgroundColor(getType().getColor());
		type.setGravity(Gravity.CENTER);
		//優先度
		final TextView text_priority=(TextView)layout.findViewById(R.id.text_priority);
		text_priority.setText(SkillViewableInformations.PRIORITY.getInformation(this));
		//二段目
		//威力
		final TextView text_pow=(TextView)layout.findViewById(R.id.text_pow);
		text_pow.setText(SkillViewableInformations.POWER.getInformation(this));
		//命中率
		final TextView text_hit=(TextView)layout.findViewById(R.id.text_hit);
		text_hit.setText(SkillViewableInformations.HIT.getInformation(this));
		//PP
		final TextView text_pp=(TextView)layout.findViewById(R.id.text_pp);
		text_pp.setText(SkillViewableInformations.PP.getInformation(this));
		
		//三段目
		//分類
		final TextView text_class=(TextView)layout.findViewById(R.id.text_class);
		text_class.setText(SkillViewableInformations.SKILL_CLASS.getInformation(this));
		//対象
		final TextView text_target=(TextView)layout.findViewById(R.id.text_target);
		text_target.setText(SkillViewableInformations.TARGET.getInformation(this));
		//直接攻撃
		final TextView text_direct=(TextView)layout.findViewById(R.id.text_direct);
		text_direct.setText(SkillViewableInformations.DIRECT.getInformation(this));
		//効果
		final TextView effect=(TextView)layout.findViewById(R.id.text_effect);
		effect.setText(SkillViewableInformations.EFFECT.getInformation(this));
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(this.toString());
		builder.setView(layout);
		final SkillData skill=this;
		builder.setPositiveButton("表示", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SkillPageActivity.startThisActivity(context, skill);
				dialog.dismiss();
			}
		});
		builder.setNeutralButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	/**
	 * タマゴ技用　技表示ダイアログ
	 * @param skillname
	 * @param egggroup1
	 * @param egggroup2
	 */
	public void openEggDialog(final Context context,final EggGroups group1,final EggGroups group2){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.dialog_skilldata,null);
		((TableLayout)layout.findViewById(R.id.tablelayout)).setStretchAllColumns(true);
		//一段目
		//タイプ
		final TextView type=(TextView)layout.findViewById(R.id.text_type);
		type.setText(getType().toString());
		type.setTextColor(Color.WHITE);
		type.setBackgroundColor(getType().getColor());
		type.setGravity(Gravity.CENTER);
		//優先度
		final TextView text_priority=(TextView)layout.findViewById(R.id.text_priority);
		text_priority.setText(SkillViewableInformations.PRIORITY.getInformation(this));
		//二段目
		//威力
		final TextView text_pow=(TextView)layout.findViewById(R.id.text_pow);
		text_pow.setText(SkillViewableInformations.POWER.getInformation(this));
		//命中率
		final TextView text_hit=(TextView)layout.findViewById(R.id.text_hit);
		text_hit.setText(SkillViewableInformations.HIT.getInformation(this));
		//PP
		final TextView text_pp=(TextView)layout.findViewById(R.id.text_pp);
		text_pp.setText(SkillViewableInformations.PP.getInformation(this));
		
		//三段目
		//分類
		final TextView text_class=(TextView)layout.findViewById(R.id.text_class);
		text_class.setText(SkillViewableInformations.SKILL_CLASS.getInformation(this));
		//対象
		final TextView text_target=(TextView)layout.findViewById(R.id.text_target);
		text_target.setText(SkillViewableInformations.TARGET.getInformation(this));
		//直接攻撃
		final TextView text_direct=(TextView)layout.findViewById(R.id.text_direct);
		text_direct.setText(SkillViewableInformations.DIRECT.getInformation(this));
		//効果
		final TextView effect=(TextView)layout.findViewById(R.id.text_effect);
		effect.setText(SkillViewableInformations.EFFECT.getInformation(this));
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(this.toString());
		builder.setView(layout);
		final SkillData skill=this;
		builder.setPositiveButton("表示", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SkillPageActivity.startThisActivity(context, skill);
				dialog.dismiss();
			}
		});
		StringBuilder sb=new StringBuilder();
		sb.append(group1.toString());
		if(group2!=null){
			sb.append("\n");
			sb.append(group2.toString());
		}
		builder.setNeutralButton(new String(sb), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				List<String> search_ifs=new ArrayList<String>();
				search_ifs.add(PokeSearchableInformations.EGG_GROUP.getDefaultSearchIf(group1.toString()));
				if(group2!=null){
					search_ifs.add(SearchIf.createSearchIf(PokeSearchableInformations.EGG_GROUP, group2.toString(), SearchTypes.ADD));
				}
				search_ifs.add(PokeSearchableInformations.SKILL.getDefaultSearchIf(skill.toString()));
				PokeSearchResultActivity.startThisActivity(context, "タマゴわざ検索", search_ifs.toArray(new String[0]));
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
    
	
}
