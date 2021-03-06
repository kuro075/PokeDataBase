package kuro075.poke.pokedatabase.skill_book.skill_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.search.skill.SkillSearchableInformations;
import kuro075.poke.pokedatabase.data_base.skill.HidenMachines;
import kuro075.poke.pokedatabase.data_base.skill.OldSkillMachines;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillMachines;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.viewable_informations.SkillViewableInformations;
import kuro075.poke.pokedatabase.menu.book.SkillBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.skill_book.search_result.SkillSearchResultActivity;
import kuro075.poke.pokedatabase.type_book.type_page.TypePageActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class SkillPageActivity extends SkillBookMenuActivity{
	private static final String TAG="SkillPageActivity";
	private static final String PACKAGE="kuro075.poke.pokedatabase.skill_book.skill_page";
	private static final String KEY_SKILL_NAME=PACKAGE+"."+TAG;
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param skill_name
	 */
	public static void startThisActivity(Context context,String skill_name){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,SkillPageActivity.class);
		intent.putExtra(KEY_SKILL_NAME, skill_name);
		DataStore.DataTypes.SKILL.getHistoryStore().addPageData(skill_name);//履歴に保存
		context.startActivity(intent);
	}
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param skill
	 */
	public static void startThisActivity(Context context,SkillData skill){
		startThisActivity(context,skill.toString());
	}
	
	/**
	 * このアクティビティーを履歴に保存せずにstartさせる
	 * @param context
	 * @param skill_name
	 */
	public static void startThisActivityWithoutHistory(Context context,String skill_name){
		Utility.log(TAG,"startThisActivity without history");
		Intent intent = new Intent(context,SkillPageActivity.class);
		intent.putExtra(KEY_SKILL_NAME, skill_name);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	private SkillData skill=SkillDataManager.INSTANCE.NullData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skill_page_layout);
		Utility.log(TAG,"onCreate");
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			skill=SkillDataManager.INSTANCE.getSkillData(extras.getString(KEY_SKILL_NAME));
		}
		
		/* レイアウト初期化 */
		
		/*================/
		/  画面上部を初期化  /
		/================*/
		initTopInfoBar();
		
		/* 威力から効果まで */
		initBasicInfo();
		/* 威力期待値 */
		initExpectedPower();
		/* このわざを覚えるポケモン */
		initNumLearningPoke();
		/* 似ているわざ */
		
	}
	
	/**
	 * 覚えるポケモン
	 */
	private void initNumLearningPoke(){
		//覚えるポケモン
		TextView tv=(TextView)findViewById(R.id.skill_learning_poke);
		tv.setText(SkillViewableInformations.NUM_LEARNING_POKE.getInformation(skill));
		final Context context=this;
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString());
			}
		});
		
		//LV
		tv=(TextView)findViewById(R.id.text_lv_num);
		tv.setText(SkillViewableInformations.NUM_LV_LEARNING_POKE.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString()+" Lv");
			}
		});
		
		//技マシン
		SkillMachines machine=SkillMachines.fromString(skill.getName());
		HidenMachines hiden=HidenMachines.fromString(skill.getName());
		OldSkillMachines old_machine=OldSkillMachines.fromString(skill.getName());
		StringBuilder sb=new StringBuilder();
		if(machine!=null){//わざマシンのとき
			sb.append(getString(R.string.machine));
			sb.append(machine.toNo());
		}else
		if(hiden!=null){//ひでんマシンのとき
			sb.append(getString(R.string.hiden));
			sb.append(hiden.toNo());
		}else
		if(old_machine!=null){//旧わざマシンのとき
			sb.append(getString(R.string.old_machine));
			sb.append(old_machine.toNo());
		}else{//どれでもないとき
			sb.append(getString(R.string.machine));
			sb.append("無し");
		}
		((TextView)findViewById(R.id.text_machine_no)).setText(new String(sb));
		//覚えるポケモンの数をセット
		tv=(TextView)findViewById(R.id.text_machine_num);
		tv.setText(SkillViewableInformations.NUM_MACHINE_LEARNING_POKE.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString()+" マ");
			}
		});
		
		//タマゴわざ
		tv=(TextView)findViewById(R.id.text_egg_skill_num);
		tv.setText(SkillViewableInformations.NUM_EGG_LEARNING_POKE.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString()+" 卵");
			}
		});
		
		//教え技
		tv=(TextView)findViewById(R.id.text_teach_skill_num);
		tv.setText(SkillViewableInformations.NUM_TEACH_LEARNING_POKE.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.SKILL, skill.toString()+" 教");
			}
		});
	}
	
	
	/**
	 * 威力期待値を初期化
	 */
	private void initExpectedPower(){
		//命中込
		((TextView)findViewById(R.id.skill_expected_pow_hit)).setText(SkillViewableInformations.EXPECTED_POWER_HIT.getInformation(skill));
		(findViewById(R.id.text_skill_expected_pow_hit)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//TODO ヘルプを表示（威力期待値　命中込みとは何か）
			}
		});
		/*
		//命中・急所込
		((TextView)findViewById(R.id.skill_expected_pow_hit_critical)).setText(SkillViewableInformations.EXPECTED_POWER_HIT_CRITICAL.getInformation(skill));
		(findViewById(R.id.text_skill_expected_pow_hit_critical)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//TODO ヘルプを表示（威力期待値　命,急所込みとは何か）
			}
		});
		
		//命中・急所・効果込
		((TextView)findViewById(R.id.skill_expected_pow_hit_critical_effect)).setText(SkillViewableInformations.EXPECTED_POWER_HIT_CRITICAL_EFFECT.getInformation(skill));
		(findViewById(R.id.text_skill_expected_pow_hit_critical_effect)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//TODO ヘルプを表示（威力期待値　命,急,効果込み(2回平均)とは何か）
			}
		});
		*/
	}
	
	
	/**
	 * 威力から効果までの基本データを初期化
	 */
	private void initBasicInfo(){
		final Context context=this;
		
		
		((TableLayout)findViewById(R.id.tablelayout)).setStretchAllColumns(true);
		//威力
		TextView tv=((TextView)findViewById(R.id.text_pow));
		tv.setText(SkillViewableInformations.POWER.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 威力がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.POW, String.valueOf(skill.getPower()));
			}
		});
		//命中
		tv=((TextView)findViewById(R.id.text_hit));
		tv.setText(SkillViewableInformations.HIT.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 命中率がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.HIT, String.valueOf(skill.getHit()));
			}
		});
		//PP
		tv=((TextView)findViewById(R.id.text_pp));
		tv.setText(SkillViewableInformations.PP.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// PPがクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.PP, String.valueOf(skill.getPp()));
			}
		});
		//優先度
		tv=((TextView)findViewById(R.id.text_priority));
		tv.setText(SkillViewableInformations.PRIORITY.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 優先度がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.PRIORITY, String.valueOf(skill.getPriority()));
			}
		});
		//分類
		tv=((TextView)findViewById(R.id.text_class));
		tv.setText(SkillViewableInformations.SKILL_CLASS.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 分類がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.SKILL_CLASS, skill.getSkillClass().toString());
			}
		});
		//対象
		tv=((TextView)findViewById(R.id.text_target));
		tv.setText(SkillViewableInformations.TARGET.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 対象がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.TARGET, skill.getTarget().toString());
			}
		});
		//直接攻撃
		tv=((TextView)findViewById(R.id.text_direct));
		tv.setText(SkillViewableInformations.DIRECT.getInformation(skill));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 直接攻撃がクリックされた時の動作
				SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.DIRECT, skill.getDirect().toString());
			}
		});
		//種類
		SkillData.SkillKind[] kinds=skill.getAllSkillKind();
		LinearLayout layout=(LinearLayout)findViewById(R.id.layout_kind);
		for(final SkillData.SkillKind kind:kinds){
			tv=new TextView(layout.getContext());
			tv.setText(kind.toString());
			tv.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// 種類がクリックされた時の動作
					SkillSearchResultActivity.startThisActivityWithDefaultSearch(context, SkillSearchableInformations.KIND, kind.toString());
				}
			});
			layout.addView(tv);
		}
		/*
		StringBuilder sb=new StringBuilder();
		SkillData.SkillKind[] kinds=skill.getAllSkillKind();
		for(int i=0,n=kinds.length;i<n;i++){
			sb.append(kinds[i]);
			if(i!=n-1){
				sb.append("\n");
			}
		}
		tv=((TextView)findViewById(R.id.text_kind));
		tv.setText(new String(sb));
		*/
		
		
		
		//効果
		((TextView)findViewById(R.id.text_effect)).setText(SkillViewableInformations.EFFECT.getInformation(skill));
	}
	
	
	/**
	 * 画面上部を初期化
	 */
	private void initTopInfoBar(){
		Utility.log(TAG,"initTopInfoBar");
		
		//名前
		((TextView)findViewById(R.id.text_name)).setText(skill.getName());
		//タイプ
		TextView text_type=(TextView)findViewById(R.id.text_type);
		text_type.setText(skill.getType().toString());
		text_type.setBackgroundColor(skill.getType().getColor());
		text_type.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickType();
			}
		});
		//prevボタン
		TextView text_prev=(TextView)findViewById(R.id.prev);
		text_prev.setText("<<");
		text_prev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickPrev();
			}
		});
		//nextボタン
		((TextView)findViewById(R.id.next)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickNext();
			}
		});
	}
	
	/**
	 * <<ボタンを押したときの動作
	 */
	private void clickPrev(){
		Utility.log(TAG,"clickPrev");
		int no=skill.getNo()-1;
		if(no<0){
			no=SkillDataManager.INSTANCE.getNum()-1;
		}
		startThisActivity(this,SkillDataManager.INSTANCE.getSkillData(no));
	}
	/**
	 * >>ボタンを押した時の動作
	 */
	private void clickNext(){
		Utility.log(TAG,"clickNext");
		int no=skill.getNo()+1;
		if(no==SkillDataManager.INSTANCE.getNum()){
			no=0;
		}
		startThisActivity(this,SkillDataManager.INSTANCE.getSkillData(no));
	}
	
	/**
	 * タイプを押した時の動作
	 */
	private void clickType(){
		// TODO TypePageを開く
		TypePageActivity.startThisActivity(this, skill.getType());
	}
}
