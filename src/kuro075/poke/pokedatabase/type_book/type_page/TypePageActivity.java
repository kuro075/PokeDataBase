package kuro075.poke.pokedatabase.type_book.type_page;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Statuses;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.OneCompareOptions;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.search.poke.SpecCategories;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData.SkillClasses;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeRelations;
import kuro075.poke.pokedatabase.menu.book.TypeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.type_book.type_page.TypePageActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class TypePageActivity extends TypeBookMenuActivity{
	private static final String TAG="TypePageActivity";
	private static final String PACKAGE="kuro075.poke.pokedatabase.type_book.type_page";
	private static final String KEY_TYPE1_NAME=PACKAGE+"."+TAG+"type1",KEY_TYPE2_NAME=PACKAGE+"."+TAG+"type2";
	private static final String NONE="none";

	//タイプ相性
	private static final int[] LAYOUT_ATTACK_ID={R.id.layout_attack_typex2,R.id.layout_attack_typex1,R.id.layout_attack_typex1_2,R.id.layout_attack_typex0};
	private static final int[] LAYOUT_DEFENSE_ID={R.id.layout_defense_typex4,R.id.layout_defense_typex2,R.id.layout_defense_typex1,R.id.layout_defense_typex1_2,R.id.layout_defense_typex1_4,R.id.layout_defense_typex0};

	//タイプ相性の個数
	private static final int[] TEXT_NUM_ATTACK_ID={R.id.text_num_attack_typex2,R.id.text_num_attack_typex1,R.id.text_num_attack_typex1_2,R.id.text_num_attack_typex0};
	private static final int[] TEXT_NUM_DEFENSE_ID={R.id.text_num_defense_typex4,R.id.text_num_defense_typex2,R.id.text_num_defense_typex1,R.id.text_num_defense_typex1_2,R.id.text_num_defense_typex1_4,R.id.text_num_defense_typex0};

	//種族値表
	private static final int[] MAX_SPEC_ID={R.id.text_max_hp,R.id.text_max_A,R.id.text_max_B,R.id.text_max_C,R.id.text_max_D,R.id.text_max_S,R.id.text_max_T};
	private static final int[] MIN_SPEC_ID={R.id.text_min_hp,R.id.text_min_A,R.id.text_min_B,R.id.text_min_C,R.id.text_min_D,R.id.text_min_S,R.id.text_min_T};
	private static final int[] AVE_SPEC_ID={R.id.text_ave_hp,R.id.text_ave_A,R.id.text_ave_B,R.id.text_ave_C,R.id.text_ave_D,R.id.text_ave_S,R.id.text_ave_T};
	
	/**
	 * このアクティビティーをstartさせる(単タイプ)
	 * @param context
	 * @param type_name
	 */
	public static void startThisActivity(Context context,String type_name){
		startThisActivity(context,type_name,NONE);
	}
	/**
	 * このアクティビティーをstartさせる(複合タイプ)
	 * @param context
	 * @param type1
	 * @param type2
	 */
	public static void startThisActivity(Context context,String type1,String type2){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,TypePageActivity.class);
		intent.putExtra(KEY_TYPE1_NAME, type1);
		intent.putExtra(KEY_TYPE2_NAME, type2);
		context.startActivity(intent);
	}
	/**
	 * このアクティビティーをstartさせる(単タイプ)
	 * @param context
	 * @param type
	 */
	public static void startThisActivity(Context context,TypeData type){
		startThisActivity(context,type.toString(),NONE);
	}
	public static void startThisActivity(Context context,TypeData type1,TypeData type2){
		startThisActivity(context,type1.toString(),type2.toString());
	}

	/*================/
	/  インスタンス変数  /
	/================*/
	private TypeData type1=TypeData.N,type2=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_page_layout);
		Utility.log(TAG,"onCreate");

		Bundle extras = getIntent().getExtras();
		if(extras != null){
			type1=TypeData.fromString(extras.getString(KEY_TYPE1_NAME));
			type2=TypeData.fromString(extras.getString(KEY_TYPE2_NAME));
		}

		/* レイアウト初期化 */

		/*================/
		/  画面上部を初期化  /
		/================*/
		initTopInfoBar();
		/* 特徴 */
		initFeature();
		/* タイプ相性 */
		initTypeRelation();
		/* このタイプのポケモン、このタイプのわざ */
		initThisType();
		/* 最高・最低種族値表 */
		initSpecTable();

	}

	private void initFeature(){
		if(type2==null){
			((TextView)findViewById(R.id.text_poke_feature)).setText(type1.getPokeFeature());
			((TextView)findViewById(R.id.text_skill_feature)).setText(type1.getSkillFeature());
		}else{
			findViewById(R.id.layout_feature).setVisibility(View.GONE);
		}
	}
	
	/**
	 * タイプ相性
	 */
	private void initTypeRelation(){
		final int MAX=13;//TODO 設定で変更できるように
		//攻撃時
		//タイプ相性
		if(type2==null){//単タイプの場合
			((LinearLayout)findViewById(R.id.layout_attack_relation)).setVisibility(View.VISIBLE);
			final TypeData[][] advantage_type=new TypeData[6][];
			advantage_type[0]=type1.getDefenseTypesByRelationEquals(TypeRelations._200).toArray(new TypeData[0]);
			advantage_type[1]=type1.getDefenseTypesByRelationEquals(TypeRelations._100).toArray(new TypeData[0]);
			advantage_type[2]=type1.getDefenseTypesByRelationEquals(TypeRelations._50).toArray(new TypeData[0]);
			advantage_type[3]=type1.getDefenseTypesByRelationEquals(TypeRelations._0).toArray(new TypeData[0]);
			//タイプ相性
			for(int i=0;i<LAYOUT_ATTACK_ID.length;i++){
				LinearLayout ll=(LinearLayout)findViewById(LAYOUT_ATTACK_ID[i]);
				if(advantage_type[i].length>MAX){
					TextView tv=new TextView(ll.getContext());
					tv.setText("その他");
					ll.addView(tv);
				}else{
					for(int j=0;j<advantage_type[i].length;j++){
						TextView tv=new TextView(ll.getContext());
						tv.setText(advantage_type[i][j].getShortName());
						tv.setTextColor(advantage_type[i][j].getColor());
						ll.addView(tv);
						if(j<advantage_type[i].length-1){
							tv=new TextView(ll.getContext());
							tv.setText("/");
							ll.addView(tv);
						}
					}
				}
				((TextView)findViewById(TEXT_NUM_ATTACK_ID[i])).setText(String.valueOf(advantage_type[i].length));
			}
		}




		//防御時
		final TypeData[][] weaktype=new TypeData[6][];
		weaktype[0]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._400);
		weaktype[1]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._200);
		weaktype[2]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._100);
		weaktype[3]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._50);
		weaktype[4]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._25);
		weaktype[5]=TypeDataManager.getWeakTypes(type1,type2, TypeDataManager.TypeRelations._0);


		//タイプ相性
		for(int i=0;i<LAYOUT_DEFENSE_ID.length;i++){
			LinearLayout ll=(LinearLayout)findViewById(LAYOUT_DEFENSE_ID[i]);
			if(weaktype[i].length>MAX){
				TextView tv=new TextView(ll.getContext());
				tv.setText("その他");
				ll.addView(tv);
			}else{
				for(int j=0;j<weaktype[i].length;j++){
					TextView tv=new TextView(ll.getContext());
					tv.setText(weaktype[i][j].getShortName());
					tv.setTextColor(weaktype[i][j].getColor());
					ll.addView(tv);
					if(j<weaktype[i].length-1){
						tv=new TextView(ll.getContext());
						tv.setText("/");
						ll.addView(tv);
					}
				}
			}
			((TextView)findViewById(TEXT_NUM_DEFENSE_ID[i])).setText(String.valueOf(weaktype[i].length));
		}


	}


	/**
	 * このタイプのポケモン・わざ
	 */
	private void initThisType(){
		final Context context=this;
		
		//このタイプのポケモン
		List<PokeData> poke_list=new ArrayList<PokeData>();
		if(type2!=null){//複合タイプのとき
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasType(type1) && poke.hasType(type2)){
					poke_list.add(poke);
				}
			}
			StringBuilder sb=new StringBuilder();
			sb.append(poke_list.size());
			sb.append("匹");
			((TextView)findViewById(R.id.text_num_poke)).setText(new String(sb));
		}else{//単タイプのとき
			for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
				if(poke.hasType(type1)){
					poke_list.add(poke);
				}
			}
			StringBuilder sb=new StringBuilder();
			sb.append(poke_list.size());
			sb.append("匹");
			((TextView)findViewById(R.id.text_num_poke)).setText(new String(sb));
			
			//内訳
			TableLayout tl=(TableLayout)findViewById(R.id.table_num_poke);
			//単タイプ
			TableRow tr=new TableRow(tl.getContext());
			TextView text=new TextView(tr.getContext());
			text.setText(type1.toString());
			text.setPadding(5,0,0,0);
			text.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					TypePageActivity.startThisActivity(context, type1);
				}
			});
			tr.addView(text);
			
			TextView text_num=new TextView(tr.getContext());
			int num=0;
			for(PokeData poke:poke_list){
				if(poke.isSingleType()) num++;
			}
			sb=new StringBuilder();
			sb.append(num);
			sb.append("匹");
			text_num.setText(new String(sb));
			text_num.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.TYPE, type1.toString());
				}
			});
			text_num.setGravity(Gravity.RIGHT);
			text_num.setPadding(0,0,5,0);
			tr.addView(text_num);
			tl.addView(tr);
			
			//複合タイプを計算
			for(final TypeData type:TypeData.values()){
				if(type1==type) continue;
				num=0;
				for(PokeData poke:poke_list){
					if(poke.hasType(type)) num++;
				}
				if(num>0){//複合タイプのポケモンがゼロでないとき 登録
					tr=new TableRow(tl.getContext());
					text=new TextView(tr.getContext());
					sb=new StringBuilder();
					sb.append(type1.toString());
					sb.append("・");
					sb.append(type.toString());
					text.setText(new String(sb));
					text.setPadding(5,0,0,0);
					text.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							TypePageActivity.startThisActivity(context, type1,type);
						}
					});
					tr.addView(text);
					
					text_num=new TextView(tr.getContext());
					sb=new StringBuilder();
					sb.append(num);
					sb.append("匹");
					text_num.setText(new String(sb));
					text_num.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							StringBuilder sb=new StringBuilder();
							sb.append(type1.toString());
							sb.append("・");
							sb.append(type.toString());
							sb.append("のポケモン");
							List<String> search_ifs=new ArrayList<String>();
							search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type1.toString()));
							search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type.toString()));
							PokeSearchResultActivity.startThisActivity(context, new String(sb), search_ifs.toArray(new String[0]));
						}
					});
					text_num.setGravity(Gravity.RIGHT);
					text_num.setPadding(0,0,5,0);
					tr.addView(text_num);
					
					tl.addView(tr);
				}
			}
		}
		((TextView)findViewById(R.id.text_num_poke)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.TYPE, type1.toString());
			}
		});
		
		
		//このタイプのわざ
		List<SkillData> skill_list=new ArrayList<SkillData>();
		for(SkillData skill:SkillDataManager.INSTANCE.getAllData()){
			if(skill.getType().equals(type1)){
				skill_list.add(skill);
			}else
			if(type2!=null && skill.getType().equals(type2)){
				skill_list.add(skill);
			}
		}
		((TextView)findViewById(R.id.text_num_skill)).setText(getTextNum(skill_list.size()));
		
		int num=0;
		//物理
		for(SkillData skill:skill_list){
			if(skill.getSkillClass().equals(SkillClasses.PHYSICAL)){
				num++;
			}
		}
		((TextView)findViewById(R.id.text_num_physical_skill)).setText(getTextNum(num));
		
		//特殊
		num=0;
		for(SkillData skill:skill_list){
			if(skill.getSkillClass().equals(SkillClasses.SPECIAL)){
				num++;
			}
		}
		((TextView)findViewById(R.id.text_num_special_skill)).setText(getTextNum(num));
	
		//変化
		num=0;
		for(SkillData skill:skill_list){
			if(skill.getSkillClass().equals(SkillClasses.CHANGE)){
				num++;
			}
		}
		((TextView)findViewById(R.id.text_num_change_skill)).setText(getTextNum(num));
	
		
	}

	/**
	 * ○匹　を取得
	 * @param num
	 * @return
	 */
	private String getTextNum(int num){
		StringBuilder sb=new StringBuilder();
		sb.append(num);
		sb.append("匹");
		return new String(sb);
	}
	
	/**
	 * 種族値表を初期化
	 */
	private void initSpecTable(){
		final Context context=this;
		List<PokeData> poke_list=new ArrayList<PokeData>();
		for(PokeData poke:PokeDataManager.INSTANCE.getAllData()){
			if(poke.hasType(type1)){
				if(type2==null || (poke.hasType(type2))){
					poke_list.add(poke);
				}
			}
		}
		((TableLayout)findViewById(R.id.table_max_min_spec)).setStretchAllColumns(true);
		
		//最高種族値
		for(int i=0;i<MAX_SPEC_ID.length;i++){
			int max=0;
			int num=0;
			PokeData max_poke=PokeDataManager.NullData;
			for(PokeData poke:poke_list){
				int spec=poke.getSpec(Statuses.values()[i]);
				if(spec>max){
					max=spec;
					max_poke=poke;
					num=0;
				}else if(spec==max){
					num++;
				}
			}
			TextView tv=(TextView)findViewById(MAX_SPEC_ID[i]);
			tv.setText(getTextSpec(max_poke.getName(),max));
			final int no=max_poke.getNo();
			final int num_max_poke=num;
			final int index=i;
			final int max_spec=max;
			tv.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(num_max_poke>0){
						List<String> search_ifs=new ArrayList<String>();
						search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type1.toString()));
						if(type2!=null) search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type2.toString()));
						search_ifs.add(SpecCategories.values()[index].createDefaultSearchIf(max_spec, OneCompareOptions.EQUAL, SearchTypes.FILTER));
						String search_if=search_ifs.get(search_ifs.size()-1);
						PokeSearchResultActivity.startThisActivity(context, SearchIf.getCategory(search_if)+":"+SearchIf.getCase(search_if), search_ifs.toArray(new String[0]));
					}else
						PokePageActivity.startThisActivity(context, no);
				}
			});
		}
		//最低種族値
		for(int i=0;i<MIN_SPEC_ID.length;i++){
			int min=720;
			int num=0;
			PokeData min_poke=PokeDataManager.NullData;
			for(PokeData poke:poke_list){
				int spec=poke.getSpec(Statuses.values()[i]);
				if(spec<min){
					min=spec;
					min_poke=poke;
					num=0;
				}else
				if(spec==min){
					num++;
				}
			}
			TextView tv=(TextView)findViewById(MIN_SPEC_ID[i]);
			tv.setText(getTextSpec(min_poke.getName(),min));
			final int no=min_poke.getNo();
			final int num_min_poke=num;
			final int index=i;
			final int min_spec=min;
			tv.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(num_min_poke>0){
						List<String> search_ifs=new ArrayList<String>();
						search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type1.toString()));
						if(type2!=null) search_ifs.add(PokeSearchableInformations.TYPE.getDefaultSearchIf(type2.toString()));
						search_ifs.add(SpecCategories.values()[index].createDefaultSearchIf(min_spec, OneCompareOptions.EQUAL, SearchTypes.FILTER));
						String search_if=search_ifs.get(search_ifs.size()-1);
						PokeSearchResultActivity.startThisActivity(context, SearchIf.getCategory(search_if)+":"+SearchIf.getCase(search_if), search_ifs.toArray(new String[0]));
					}else
						PokePageActivity.startThisActivity(context, no);
				}
			});
		}
		//平均値
		for(int i=0;i<AVE_SPEC_ID.length;i++){
			int sum=0;
			for(PokeData poke:poke_list){
				sum+=poke.getSpec(Statuses.values()[i]);
			}
			TextView tv=(TextView)findViewById(AVE_SPEC_ID[i]);
			sum/=poke_list.size();
			tv.setText(String.valueOf(sum));
		}
	}
	
	private String getTextSpec(String name,int spec){
		StringBuilder sb=new StringBuilder();
		sb.append(name);
		sb.append("(");
		sb.append(spec);
		sb.append(")");
		return new String(sb);
	}


	/**
	 * 画面上部を初期化
	 */
	private void initTopInfoBar(){
		Utility.log(TAG,"initTopInfoBar");

		//名前
		//タイプ1
		TextView text_type1=((TextView)findViewById(R.id.text_type1));
		text_type1.setText(type1.toString());
		text_type1.setBackgroundColor(type1.getColor());
		if(type2!=null){
			((TextView)findViewById(R.id.text_pertition)).setVisibility(View.VISIBLE);
			TextView text_type2=((TextView)findViewById(R.id.text_type2));
			text_type2.setText(type2.toString());
			text_type2.setBackgroundColor(type2.getColor());
			text_type2.setVisibility(View.VISIBLE);
		}

		//prevボタン
		TextView text_prev=(TextView)findViewById(R.id.prev);
		TextView text_next=(TextView)findViewById(R.id.next);
		if(type2!=null){
			text_prev.setVisibility(View.INVISIBLE);
			text_next.setVisibility(View.INVISIBLE);
		}else{
			text_prev.setText("<<");
			text_prev.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickPrev();
				}
			});
			//nextボタン
			text_next.setText(">>");
			text_next.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickNext();
				}
			});
		}
	}

	/**
	 * <<ボタンを押したときの動作
	 */
	private void clickPrev(){
		Utility.log(TAG,"clickPrev");
		int no=type1.ordinal()-1;
		if(no<0){
			no=TypeData.values().length-1;
		}
		startThisActivity(this,TypeData.values()[no]);
	}
	/**
	 * >>ボタンを押した時の動作
	 */
	private void clickNext(){
		Utility.log(TAG,"clickNext");
		int no=type1.ordinal()+1;
		if(no==TypeData.values().length){
			no=0;
		}
		startThisActivity(this,TypeData.values()[no]);
	}

}
