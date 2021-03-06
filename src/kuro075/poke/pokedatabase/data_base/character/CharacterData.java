package kuro075.poke.pokedatabase.data_base.character;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.character_book.character_page.CharacterPageActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.viewable_informations.CharacterViewableInformations;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;

/**
 * 特性のデータクラス
 * @author sanogenma
 *
 */
public class CharacterData extends BasicData{

	
	/**
	 * ・発動タイミング
	 * 登場時、交代時、常時、攻撃時、防御時、ターン終了時
	 * @author sanogenma
	 *
	 */
	public enum Timings{
		ATTACK("攻撃時"),CHANGE("交代時"),ALWAYS("常時"),TURN_END("ターン終了時"),APPEAR("登場時"),DEFENSE("防御時");
		private final String name;
		Timings(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		
		/**
		 * 名前からTimingを取得
		 * @param name
		 * @return
		 */
		public static Timings fromName(String name){
			for(Timings timing:Timings.values()){
				if(timing.toString().equals(name)){
					return timing;
				}
			}
			return null;
		}
	}
	
	/**
	 * 効果の対象
	 * 自分、相手、味方、全員、場、その他
	 * @author sanogenma
	 *
	 */
	public enum Targets{
		MYSELF("自分"),ENEMY("相手"),FRIEND("味方"),ALL("全員"),FIELD("場"),OTHER("その他");
		private final String name;
		Targets(String name){this.name=name;}
		@Override
		public String toString(){
			return name;
		}
		
		public static Targets fromName(String name){
			for(Targets target:Targets.values()){
				if(target.toString().equals(name)){
					return target;
				}
			}
			return null;
		}
	}
	/**
	 * ・効果の種類
	 * 回復、状態異常、制限、ダメージ、天候、とくせい、能力ランク、持ち物、わざ、その他
	 * @author sanogenma
	 *
	 */
	public enum Kinds{
		RECOVERY("回復"),UP("強化"),REDUCTION("軽減"),DOWN("弱化"),UNUSUAL("状態異常"),BAN("制限"),DAMAGE("ダメージ"),WHETHER("天候"),CHARACTER("とくせい"),
		STATUS_RANK("能力ランク"),TRANSFORM("変身"),INVALID("無効"),ITEM("もちもの"),SKILL("わざ"),
		OTHER("その他");
		private final String name;
		Kinds(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		
		public static Kinds fromName(String name){
			for(Kinds kind:Kinds.values()){
				if(kind.toString().equals(name)){
					return kind;
				}
			}
			return null;
		}
	}
	
	

	protected static class Builder{
		private String name="-";//名前
		private int no=9999;//管理ナンバー
		private String battle_effect="-";//戦闘中の効果
		private String field_effect="-";//フィールド上の効果
		private List<Timings> timing_list=new ArrayList<Timings>();//発動タイミング
		private List<Targets> target_list=new ArrayList<Targets>();//対象
		private List<Kinds> kind_list=new ArrayList<Kinds>();//効果の種類
		
		protected Builder(){}

		public CharacterData build(){
			return new CharacterData(name,no,battle_effect,field_effect,timing_list.toArray(new Timings[0]),target_list.toArray(new Targets[0]),kind_list.toArray(new Kinds[0]));
		}

		public void setBattle_effect(String battle_effect) {
			this.battle_effect = battle_effect;
		}

		public void setField_effect(String field_effect) {
			this.field_effect = field_effect;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setNo(int no) {
			this.no = no;
		}
		
		public void addTiming(Timings timing){
			timing_list.add(timing);
		}
		
		public void addTarget(Targets target){
			target_list.add(target);
		}
		
		public void addKind(Kinds kind){
			kind_list.add(kind);
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String battle_effect;//戦闘中の効果
	private final String field_effect;//フィールド上の効果
	private final Timings[] timings;//発動タイミング
	private final Targets[] targets;//対象
	private final Kinds[] kinds;//効果の種類
	
	//持っているポケモンリスト
	private final List<PokeData> poke_list=new ArrayList<PokeData>(),dream_poke_list=new ArrayList<PokeData>();
	private boolean flag_poke_list=false;
	
	private CharacterData(String name,int no,String battle_effect,String field_effect,Timings[] timings,Targets[] targets,Kinds[] kinds) {
		super(name,no);
		// TODO Auto-generated constructor stub
		this.battle_effect=battle_effect;
		this.field_effect=field_effect;
		this.timings=timings;
		this.targets=targets;
		this.kinds=kinds;
	}

	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return getNo()-another.getNo();
	}

	/**
	 * 戦闘中の効果を取得
	 * @return　戦闘中の効果
	 */
	public String getBattleEffect() {
		return battle_effect;
	}

	/**
	 * フィールド上の効果を取得
	 * @return　フィールド上の効果
	 */
	public String getFieldEffect() {
		return field_effect;
	}

	/**
	 * 夢特性で持っているポケモンの数を取得
	 * @return
	 */
	public int getNumDreamPoke(){
		initPokeList();
		return dream_poke_list.size();
	}
	
	/**
	 * 持っているポケモンの数を取得
	 * @return
	 */
	public int getNumPoke(){
		initPokeList();
		return poke_list.size();
	}
	
	/**
	 * 発動タイミングを全て取得
	 * @return
	 */
	public Timings[] getAllTimings(){
		return timings.clone();
	}
	
	/**
	 * 対象を全て取得
	 * @return
	 */
	public Targets[] getAllTargets(){
		return targets.clone();
	}
	
	/**
	 * 効果の種類を全て取得
	 * @return
	 */
	public Kinds[] getAllKinds(){
		return kinds.clone();
	}
	
	/**
	 * 発動タイミングtimingがあるかどうか
	 * @param timing
	 * @return
	 */
	public boolean hasTiming(Timings timing){
		for(Timings t:timings){
			if(t.equals(timing)) return true;
		}
		return false;
	}
	
	/**
	 * 対象targetがあるかどうか
	 * @param target
	 * @return
	 */
	public boolean hasTarget(Targets target){
		for(Targets t:targets){
			if(t.equals(target)) return true;
		}
		return false;
	}
	
	/**
	 * 効果の種類kindがあるかどうか
	 * @param kind
	 * @return
	 */
	public boolean hasKind(Kinds kind){
		for(Kinds k:kinds){
			if(k.equals(kind)) return true;
		}
		return false;
	}
	
	/**
	 * この特性を持っているポケモンリストを初期化
	 */
	public void initPokeList(){
		if(!flag_poke_list){
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasCharacter(this)){
					poke_list.add(poke);
					CharacterData chara=poke.getCharacter(PokeData.CharacterTypes.DREAM);
					if(chara!=null && chara.equals(this)) dream_poke_list.add(poke);
				}
			}
			flag_poke_list=true;
		}
	}
	/**
	 * 特性詳細表示ダイアログを開く
	 * @param charaname
	 */
	public void openDialog(final Context context){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.dialog_charadata,null);
		//戦闘中の効果
		final TextView battleeffect=(TextView)layout.findViewById(R.id.text_battleeffect);
		battleeffect.setText(CharacterViewableInformations.BATTLE_EFFECT.getInformation(this));
		//フィールド上の効果
		final TextView fieldeffect=(TextView)layout.findViewById(R.id.text_fieldeffect);
		fieldeffect.setText(CharacterViewableInformations.FIELD_EFFECT.getInformation(this));
		
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(toString());
		builder.setView(layout);
		final CharacterData chara=this;

		builder.setPositiveButton("表示", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CharacterPageActivity.startThisActivity(context, chara);
				dialog.dismiss();
			}
		});
		builder.setNeutralButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.CHARACTER, getName());
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
}
