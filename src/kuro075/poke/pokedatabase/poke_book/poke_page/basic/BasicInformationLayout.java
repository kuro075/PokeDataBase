package kuro075.poke.pokedatabase.poke_book.poke_page.basic;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Sexes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Statuses;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.poke.SearchableInformations;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.data_base.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.poke_book.search_result.SearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasicInformationLayout extends LinearLayout{
	private static final String TAG="BasicInformationLayout";
	//種族値ID
	private static final int[] TEXT_SPEC_ID={R.id.text_specH,R.id.text_specA,R.id.text_specB,R.id.text_specC,R.id.text_specD,R.id.text_specS};
	//努力値ID
	private static final int[] TEXT_EFF_ID={R.id.text_effH,R.id.text_effA,R.id.text_effB,R.id.text_effC,R.id.text_effD,R.id.text_effS};
	//特性
	private static final int[] TEXT_CHARACTER_ID={R.id.text_character1,R.id.text_character2,R.id.text_character3};
	//タイプ相性
	private static final int[] LAYOUT_TYPERELATION_ID={R.id.layout_typex4,R.id.layout_typex2,R.id.layout_typex1,R.id.layout_typex1_2,R.id.layout_typex1_4,R.id.layout_typex0};
	//タイプ相性の個数
	private static final int[] TEXT_NUM_TYPERELATION_ID={R.id.text_num_typex4,R.id.text_num_typex2,R.id.text_num_typex1,R.id.text_num_typex1_2,R.id.text_num_typex1_4,R.id.text_num_typex0};
	//持っている道具
	private static final int[] TEXT_ITEM_ID={R.id.text_commonitem,R.id.text_rareitem};
	//タマゴグループ
	private static final int[] TEXT_EGGGROUP_ID={R.id.text_egggroup1,R.id.text_egggroup2};

	
	private LinearLayout[] TypeRelation=new LinearLayout[LAYOUT_TYPERELATION_ID.length];
	private TextView[] Spec=new TextView[6],Eff=new TextView[6],
					   Character=new TextView[3],Item=new TextView[2],
					   EggGroup=new TextView[2];
	private TextView SpecTotal,EffTotal;
	private TextView Height,Weight,Ketaguri,//高さ、重さ、けたぐり・草結びの威力
			 HatchingStep,//孵化歩数、
			 Male,Female,//性別比率♂・♀、
			 FinalEx,EGet,BasicEx,Natsuki;//最終経験値、捕まえやすさ、基礎経験値、初期なつき
	
	private View layout;
	private PokeData poke;
	
	public BasicInformationLayout(Context context,OnTouchListener listener,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
		initLayout(listener);
		setAllData();
	}
	
	/**
	 * 特性をクリックした時の動作
	 * @param chara
	 */
	private void clickCharacter(CharacterData chara){
		Utility.log(TAG, "clickCharacter");
		//特性図鑑を開く
	}
	
	/**
	 * タマゴグループをクリックした時の動作
	 * @param egg_group
	 */
	private void clickEggGroup(PokeData.EggGroups egg_group){
		Utility.log(TAG, "clickEggGroup");
		//タマゴグループで検索結果を開く
		SearchResultActivity.startThisActivityWithDefaultSearch(getContext(), SearchableInformations.EGG_GROUP, egg_group.toString());
	}
	/**
	 * 最終経験値をクリックした時の動作
	 */
	private void clickFinalEx(){
		Utility.log(TAG, "clickFinalEx");
		//検索結果を開く
		SearchResultActivity.startThisActivityWithDefaultSearch(getContext(), SearchableInformations.FINAL_EX, poke.getFinalEx().toString());
	}
	/*
	 * 孵化歩数を押した時の動作
	 */
	private void clickHatchingStep(){
		Utility.log(TAG, "clickHatchingStep");
		//検索結果を開く
		SearchResultActivity.startThisActivityWithDefaultSearch(getContext(), SearchableInformations.HATCHING_STEP, poke.getHatchingStep().toString());
	}
	private void clickTextItem(PokeData.ItemRarities rarity){
		Utility.log(TAG, "clickTextItem");
		//検索結果を開く
		SearchResultActivity.startThisActivityWithDefaultSearch(getContext(), SearchableInformations.ITEM, poke.getItem(rarity).toString());
	}
	/**
	 * layout初期化
	 * @param listener
	 */
	private void initLayout(OnTouchListener listener){
		LayoutInflater factory=LayoutInflater.from(this.getContext());
		layout=factory.inflate(R.layout.basic_info_layout, null);
		this.addView(layout);
		/*==============/
		/  widgetの登録  / 
		/==============*/
		for(int i=0;i<TEXT_SPEC_ID.length;i++){
			Spec[i]=(TextView)layout.findViewById(TEXT_SPEC_ID[i]);
			Eff[i]=(TextView)layout.findViewById(TEXT_EFF_ID[i]);
			if(i<TEXT_CHARACTER_ID.length){
				Character[i]=(TextView)layout.findViewById(TEXT_CHARACTER_ID[i]);
			}
			if(i<LAYOUT_TYPERELATION_ID.length){
				TypeRelation[i]=(LinearLayout)layout.findViewById(LAYOUT_TYPERELATION_ID[i]);
			}
			if(i<TEXT_ITEM_ID.length){
				Item[i]=(TextView)layout.findViewById(TEXT_ITEM_ID[i]);
			}
			if(i<TEXT_EGGGROUP_ID.length){
				EggGroup[i]=(TextView)layout.findViewById(TEXT_EGGGROUP_ID[i]);
			}
		}
		SpecTotal=(TextView)layout.findViewById(R.id.text_specTotal);
		EffTotal=(TextView)layout.findViewById(R.id.text_effTotal);
		Height=(TextView)layout.findViewById(R.id.text_height);//高さ
		Weight=(TextView)layout.findViewById(R.id.text_weight);//重さ
		Ketaguri=(TextView)layout.findViewById(R.id.text_ketaguri);//けたぐり・草結びの威力
		HatchingStep=(TextView)layout.findViewById(R.id.text_eggstep);//孵化歩数
		Male=(TextView)layout.findViewById(R.id.text_sex_m);//♂
		Female=(TextView)layout.findViewById(R.id.text_sex_f);//♀
		FinalEx=(TextView)layout.findViewById(R.id.text_finalex);//最終経験値
		EGet=(TextView)layout.findViewById(R.id.text_eget);//捕まえやすさ
		BasicEx=(TextView)layout.findViewById(R.id.text_basicex);//初期経験値
		Natsuki=(TextView)layout.findViewById(R.id.text_natsuki);//初期なつき

		((LinearLayout)layout.findViewById(R.id.linearlayout)).setOnTouchListener(listener);
	}
	
	/**
	 * widgetの設定
	 */
	private void setAllData(){
    	//タイプ相性
    	final TypeData defense_type1=poke.getType(0),
    				   defense_type2=poke.getType(1);
    	final TypeData[][] weaktype=new TypeData[6][];
    	weaktype[0]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._400);
    	weaktype[1]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._200);
    	weaktype[2]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._100);
    	weaktype[3]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._50);
    	weaktype[4]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._25);
    	weaktype[5]=TypeDataManager.getWeakTypes(defense_type1, defense_type2, TypeDataManager.TypeRelations._0);
    	
    	//種族値//努力値
    	for(int i=0,n=6;i<n;i++){
    		Spec[i].setText(String.valueOf(poke.getSpec(Statuses.values()[i])));
    		final int eff=poke.getEff(Statuses.values()[i]);
    		if(eff>0){
        		Eff[i].setText("+"+eff);
    		}else{
    			Eff[i].setText(String.valueOf(eff));
    		}
    	}
    	SpecTotal.setText(String.valueOf(poke.getTotalSpec()));
    	EffTotal.setText("+"+poke.getTotalEff());
    	//特性
    	for(int i=0,n=3;i<n;i++){
    		final CharacterData character=poke.getCharacter(i);
        	Character[i].setText(character.toString());
        	if(character.getNo()>=0){
	    		Character[i].setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clickCharacter(character);
					}
				});
        	}
    	}
    	//タイプ相性
    	for(int i=0;i<TypeRelation.length;i++){
    		if(weaktype[i].length>13){//設定で変更できるように
    			TextView tv=new TextView(TypeRelation[i].getContext());
    			tv.setText("その他");
    			TypeRelation[i].addView(tv);
    		}else{
	    		for(int j=0;j<weaktype[i].length;j++){
	    			TextView tv=new TextView(TypeRelation[i].getContext());
	    			tv.setText(weaktype[i][j].getShortName());
	    			tv.setTextColor(weaktype[i][j].getColor());
	    			TypeRelation[i].addView(tv);
	    			if(j<weaktype[i].length-1){
	    				tv=new TextView(TypeRelation[i].getContext());
	        			tv.setText("/");
	        			TypeRelation[i].addView(tv);
	    			}
	    		}
    		}
    		((TextView)layout.findViewById(TEXT_NUM_TYPERELATION_ID[i])).setText(String.valueOf(weaktype[i].length));
    	}
    	//持っている道具
    	final String common_item=ViewableInformations.ITEM_COMMON.getInformation(poke);
    	Item[0].setText(common_item);
    	if(!common_item.equals("-")){
    		Item[0].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clickTextItem(PokeData.ItemRarities.COMMON);
				}
    		});
    	}
    	final String rare_item=ViewableInformations.ITEM_RARE.getInformation(poke);
    	Item[1].setText(rare_item);
    	if(!rare_item.equals("-")){
    		Item[1].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clickTextItem(PokeData.ItemRarities.RARE);
				}
    		});
    	}
    	//タマゴグループ
    	final PokeData.EggGroups egg_group1=poke.getEggGroup(0);
    	EggGroup[0].setText(egg_group1.toString());
    	EggGroup[0].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickEggGroup(egg_group1);
			}
		});
    	final PokeData.EggGroups egg_group2=poke.getEggGroup(1);
    	if(egg_group2==null){
    		EggGroup[1].setVisibility(View.INVISIBLE);
    		layout.findViewById(R.id.text_betweenegggroup).setVisibility(View.INVISIBLE);
    	}else{
        	EggGroup[1].setText(egg_group2.toString());
    		EggGroup[1].setVisibility(View.VISIBLE);
        	EggGroup[1].setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				clickEggGroup(egg_group2);
    			}
    		});
    		layout.findViewById(R.id.text_betweenegggroup).setVisibility(View.VISIBLE);
    	}
    	
    	
    	
    	//高さ重さ
		Height.setText(ViewableInformations.HEIGHT.getInformation(poke));
    	Weight.setText(ViewableInformations.WEIGHT.getInformation(poke));
    	//けたぐりの威力
    	StringBuilder sb=new StringBuilder();
    	sb.append("(けたぐり:");
    	sb.append(ViewableInformations.KETAGURI_KUSAMUSUBI.getInformation(poke));
    	sb.append(")");
    	Ketaguri.setText(new String(sb));
    	//孵化歩数
    	HatchingStep.setText(ViewableInformations.HATCHING_STEP.getInformation(poke));
    	HatchingStep.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickHatchingStep();
			}
		});
    	
    	//性別比率
    	final int male=poke.getSexRatio(Sexes.MALE),
				  female=poke.getSexRatio(Sexes.FEMALE);
    	if(male<0){
    		Male.setText("-");
    	}else{
    		Male.setText(String.valueOf(male));
    	}
    	if(female<0){
    		Female.setText("-");
    	}else{
    		Female.setText(String.valueOf(female));
    	}
    	//その他データ
    	FinalEx.setText(ViewableInformations.FINAL_EX.getInformation(poke));
    	FinalEx.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickFinalEx();
			}
		});
    	EGet.setText(ViewableInformations.EASE_GET.getInformation(poke));
    	BasicEx.setText(ViewableInformations.BASIC_EX.getInformation(poke));
    	Natsuki.setText(ViewableInformations.INITIAL_NATSUKI.getInformation(poke));
    }
}
