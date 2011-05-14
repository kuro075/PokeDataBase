package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.poke.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 絞り込み、追加、除外を行える情報のenum
 * ・検索を行うメソッド（渡されたPokeData[]から条件に合うポケを検索して返す）
 * ・それぞれのダイアログを取得するメソッド(選ばれた検索条件から検索されたPokeData[]をSearchIfListenerに投げる)
 * ・詳細条件に登録する文字列を取得するメソッド
 * ・詳細条件から検索を行うメソッド
 * @author sanogenma
 *
 */
public enum SearchableInformations implements SearchIfCategory{
	NAME("名前"){
		private final String HIRA="あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽぁぃぅぇぉゃゅょっ-";
		private final String KATA="アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポァィゥェォャュョッーｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｧｨｩｪｫｬｭｮｯ";
		
		/**
		 * 平仮名を含む文字列を全てカタカナの文字列に変換して返す
		 * 平仮名、カタカナ以外を含む場合は空の文字列("")を返す
		 * @param text
		 * @return
		 */
		public String changeHiraToKata(String text){
			StringBuilder sb=new StringBuilder();
			for(int i=0,n=text.length();i<n;i++){
				char c=text.charAt(i);
				//カタカナの場合
				if(KATA.indexOf(c)>=0){
					sb.append(c);
					continue;
				}
				
				int index=HIRA.indexOf(c);
				//平仮名の場合
				if(index>=0){
					sb.append(KATA.charAt(index));
					continue;
				}
				//それ以外
				return "";
			}
			return new String(sb);
		}
		@Override
		public void openDialog(final Context context, final SearchTypes search_type,
				final SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.string_input_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(createDialogTitle(this, search_type));
			builder.setView(layout);
			
			
			final EditText edit=(EditText)layout.findViewById(R.id.edit);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(NameSearchOptions.values()));
			final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
			spinner.setAdapter(adapter);
			((TextView)layout.findViewById(R.id.text)).setText("※ひらがな、カタカナのみ有効(ひらがなは、カタカナに自動変換されます)");

			builder.setPositiveButton("検索",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String text=changeHiraToKata(edit.getText().toString());
					if(text.equals("")){
						Utility.popToast(context, "無効な文字列です");
						listener.receiveSearchIf(null);
					}else{
						StringBuilder sb=new StringBuilder();
						sb.append(text);
						sb.append(" ");
						sb.append(NameSearchOptions.values()[spinner.getSelectedItemPosition()]);
						listener.receiveSearchIf(createSearchIf(NAME,new String(sb),search_type));
					}
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					listener.receiveSearchIf(null);
				}
			});
			
			builder.create().show();
		}

		/**
		 * @param poke_array
		 * @param category
		 * @param _case : "カタカナの文字列 NameSearchOption"
		 * 
		 */
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
					String[] text_option=_case.split(" ");
				NameSearchOptions option=NameSearchOptions.fromString(text_option[1]);
				List<PokeData> poke_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(option.compareOf(poke, text_option[0])){
						poke_list.add(poke);
					}
				}
				return poke_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	REGION("地方") {
		/*===================================/
		 * 地方で検索
		/==================================*/
		private final String[] REGION_NAMES={"カントー","ジョウト","ホウエン","シンオウ","イッシュ"};
		private final int[] FIRST_NO={1,152,252,387,494,650};
		
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,REGION_NAMES);
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			
			final SearchIfListener final_listener=listener;
			final SearchTypes final_search_type=search_type;

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					final_listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(toString(),"onItemClick");
					final_listener.receiveSearchIf(createSearchIf(REGION,REGION_NAMES[position],final_search_type));
					dialog.dismiss();
				}
			});
			dialog.show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String region) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				int first=0,last=0;
				for(int i=0,n=REGION_NAMES.length;i<n;i++){
					if(REGION_NAMES[i].equals(region)){
						first=FIRST_NO[i];
						last=FIRST_NO[i+1];
						break;
					}
				}
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(first<=poke.getNo() && poke.getNo()<last){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	TYPE("タイプ"){
		/*=======================/
		 * タイプで検索
		 * ・MYSELF(タイプ) default
		 * ・RELATION(タイプ相性)
		/=======================*/
		@Override
		public String getDefaultSearchIf(String type) {
			// TODO Auto-generated method stub
			return createSearchIf(TypeCategories.MYSELF,type,SearchTypes.FILTER);
		}
		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			for(TypeCategories type:TypeCategories.values()){
				if(type.toString().equals(category)){
					return true;
				}
			}
			return false;
		}
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			TypeCategories.MYSELF.openDialog(context, search_type, listener);
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type) {
			// TODO Auto-generated method stub
			return TypeCategories.fromString(category).search(poke_array, category, type);
		}
	},
	CHARACTER("特性"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type,SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context,search_type,listener,this,CharacterDataManager.INSTANCE.getAllCharaName()).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String chara_name) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				CharacterData chara=CharacterDataManager.INSTANCE.getCharacterData(chara_name);
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasCharacter(chara)) list.add(poke);
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	SPEC("種族値"){
		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			for(SpecCategories spec:SpecCategories.values()){
				if(spec.toString().equals(category))
					return true;
			}
			return false;
		}

		@Override
		public void openDialog(final Context context,
				final SearchTypes search_type,final SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(SpecCategories.values()));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(toString(),"onItemClick");
					SpecCategories.values()[position].openDialog(context, search_type, listener);
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			for(SpecCategories spec:SpecCategories.values()){
				if(spec.toString().equals(category)){
					return spec.search(poke_array, category, _case);
				}
			}
			return new PokeData[0];
		}
	},
	EFF("努力値"){
		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			for(EffCategories eff:EffCategories.values()){
				if(eff.toString().equals(category))
					return true;
			}
			return false;
		}

		@Override
		public void openDialog(final Context context,
				final SearchTypes search_type,final SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(EffCategories.values()));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(toString(),"onItemClick");
					EffCategories.values()[position].openDialog(context, search_type, listener);
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			for(EffCategories eff:EffCategories.values()){
				if(eff.toString().equals(category)){
					return eff.search(poke_array, category, _case);
				}
			}
			return new PokeData[0];
		}
	},
	SKILL("わざ"){
		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context, search_type, listener, this, SkillDataManager.INSTANCE.getAllSkillName()).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String skill_name) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasSkill(SkillDataManager.INSTANCE.getSkillData(skill_name))){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	EGG_GROUP("タマゴグループ"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context,search_type,listener,this,Utility.changeToStringArray(PokeData.EggGroups.values())).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String egg_group) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasEggGroup(EggGroups.fromString(egg_group))){
						searched_list.add(poke);
					}
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
		
	},
	HATCHING_STEP("孵化歩数"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context,search_type,listener,this,PokeData.HatchingSteps.toStringArray()).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String step) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.getHatchingStep().toString().equals(step)) 
						searched_list.add(poke);
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
		
	},
	ITEM("アイテム"){
		@Override
		public String getDefaultTitle(String item_name) {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			sb.append("「");
			sb.append(item_name);
			sb.append("」を持つポケモン");
			return new String(sb);
		}
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context,search_type,listener,this,ItemDataManager.INSTANCE.getAllItemName()).create().show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String item_name) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasItem(ItemDataManager.INSTANCE.getItemData(item_name)))
						list.add(poke);
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	KETAGURI_KUSAMUSUBI("草結びの威力"){
		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			createIntInputDialogBuilder(context, search_type, listener, this, 20, 120).create().show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				String[] pow_option=_case.split(" ");
				int pow=Integer.valueOf(pow_option[0]);
				OneCompareOptions option=OneCompareOptions.fromString(pow_option[1]);
				
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					int tmp=120;
					final int weight=poke.getWeight();
					if(weight<=100)		  tmp=20;
					else if(weight<=250)  tmp=40;
					else if(weight<=500)  tmp=60;
					else if(weight<=1000) tmp=80;
					else if(weight<=2000) tmp=100;
					if(option.compareOf(tmp, pow)){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	FINAL_EX("最終経験値"){
		@Override
		public void openDialog(Context context,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			createSimpleSpinnerDialogBuilder(context,search_type,listener,this,PokeData.FinalExperiences.toStringArray()).create().show();
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String final_ex) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.getFinalEx().toString().equals(final_ex))
						list.add(poke);
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	KIND("種類"){
		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			for(KindCategories kind:KindCategories.values()){
				if(kind.toString().equals(category)){
					return true;
				}
			}
			return false;
		}

		@Override
		public void openDialog(Context context,
				SearchTypes search_type,SearchIfListener listener) {
			// TODO Auto-generated method stub
			KindCategories.EVOLUTION.openDialog(context,search_type, listener);
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String kind) {
			// TODO Auto-generated method stub
			return KindCategories.fromString(category).search(poke_array,category,kind);
		}
	};
	
	
	private static final String TAG="SeachableInformations for poke";
	
	/**
	 * 検索条件を作成
	 * @param category　検索項目
	 * @param _case　検索条件
	 * @param type　検索方法
	 * @return category:_case(type)
	 */
	public static String createSearchIf(SearchIfCategory category,String _case,SearchTypes search_type){
		StringBuilder sb=new StringBuilder();
		sb.append(category.toString());
		sb.append(":");
		sb.append(_case);
		sb.append("(");
		sb.append(search_type.toString());
		sb.append(")");
		return new String(sb);
	}
	
	/**
	 * ダイアログのタイトルを作成
	 * @param category
	 * @param type
	 * @return
	 */
	public static String createDialogTitle(SearchIfCategory category,SearchTypes type){
		StringBuilder sb=new StringBuilder();
		sb.append(category.toString());
		sb.append("(");
		sb.append(type.toString());
		sb.append(")");
		return new String(sb);
	}
	
	/**
	 * 除外が一匹の時の検索条件を取得
	 * @param remove_poke
	 * @return
	 */
	public static String getRemoveIf(PokeData remove_poke){
		return SearchTypes.REMOVE+":"+remove_poke.getName();
	}
	
	/**
	 * 除外が複数の時の検索条件を取得
	 * @param remove_pokes
	 * @return
	 */
	public static String getRemoveIf(PokeData[] remove_pokes){
		StringBuilder sb=new StringBuilder();
		sb.append(SearchTypes.REMOVE.toString());
		sb.append(":");
		for(int i=0,n=remove_pokes.length;i<n;i++){
			sb.append(remove_pokes[i].getName());
			if(i<n-1){
				sb.append("/");
			}
		}
		return new String(sb);
	}
	/**
	 * search_if=category:case(SearchTypes)
	 * @param poke_array　現在のポケモンリスト
	 * @param condtion　検索条件
	 * @return　検索後のポケモンリスト
	 */
	public static PokeData[] searchBySearchIf(PokeData[] poke_array,String search_if){
		Utility.log(TAG, "searchBySearchIf");
		String[] tmp=search_if.split("[:¥(¥)/]");//category , case(SearchTypes)に分けられる
		Utility.log(TAG, tmp.length+":"+Arrays.deepToString(tmp));
		PokeData[] searched_array=null;
		Set<PokeData> searched_set=new HashSet<PokeData>();
		SearchableInformations si=fromCategory(tmp[0]);
		if(si!=null){//検索であった場合
			switch(SearchTypes.fromString(tmp[2])){
				case FILTER:
					searched_array=si.search(poke_array,tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				case REMOVE:
					searched_array=si.search(poke_array,tmp[0], tmp[1]);
					for(PokeData poke:poke_array){
						boolean flag=true;
						for(PokeData target:searched_array){
							if(poke.equals(target)){ 
								flag=false;
								break;
							}
						}
						if(flag)
							searched_set.add(poke);
					}
					break;
				case ADD:
					searched_array=si.search(PokeDataManager.INSTANCE.getAllPokeData(),tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(poke_array));
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				default:
					searched_set.addAll(Arrays.asList(poke_array));
					break;
			}
		}
		else{//検索以外(除外、追加)
			SearchTypes st=SearchTypes.fromString(tmp[0]);
			searched_set.addAll(Arrays.asList(poke_array));
			switch(st){
				case REMOVE:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.remove(PokeDataManager.INSTANCE.getPokeData(tmp[i]));
					}
					break;
				case ADD:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.add(PokeDataManager.INSTANCE.getPokeData(tmp[i]));
					}
					break;
				default:
			}
		}
		return searched_set.toArray(new PokeData[0]);
	}
	private final String name;
	private static final Map<String,SearchableInformations>
		stringToEnum = new HashMap<String,SearchableInformations>();//文字列からenumへ

	static { //定数名からenum定数へのマップを初期化
		for(SearchableInformations si : values()){
			stringToEnum.put(si.toString(), si);
		}
	}
	/**
	 * 検索条件の種類から項目を取得
	 * @param category
	 * @return
	 */
	public static SearchableInformations fromCategory(String category){
		for(SearchableInformations info:values()){
			if(info.isCategory(category)) return info;
		}
		return null;
	}
	/**
	 * 文字列からSearchableInformationsを取得
	 * @param step
	 * @return
	 */
	public static SearchableInformations fromString(String name){
		return stringToEnum.get(name);
	}
	/**
	 * 追加が一匹の時の検索条件を取得
	 * @param add_poke
	 * @return
	 */
	public static String getAddIf(PokeData add_poke){
		return SearchTypes.ADD.toString()+":"+add_poke;
	}
	/**
	 * 追加が複数の時の検索条件を取得
	 * @param add_pokes
	 * @return
	 */
	public static String getAddIf(PokeData[] add_pokes){
		StringBuilder sb=new StringBuilder();
		sb.append(SearchTypes.ADD.toString());
		sb.append(":");
		for(int i=0,n=add_pokes.length;i<n;i++){
			sb.append(add_pokes[i].getName());
			if(i<n-1){
				sb.append("/");
			}
		}
		return new String(sb);
	}
	
	/**
	 * スピナーのみの検索ダイアログのビルダーを取得
	 * @param context
	 * @param search_type
	 * @param listener
	 * @param category
	 * @param datas
	 * @return
	 */
	public static AlertDialog.Builder createSimpleSpinnerDialogBuilder(Context context,final SearchTypes search_type,final SearchIfListener listener,final SearchIfCategory category,final String[] datas){
		Utility.log(TAG, "createSimpleSpinnerDialogBuilder");
		AlertDialog.Builder builder;
		LayoutInflater factory=LayoutInflater.from(context);
		
		final View layout = factory.inflate(R.layout.simple_spinner_dialog, null);
		builder = new AlertDialog.Builder(context);
		builder.setTitle(createDialogTitle(category,search_type));
		builder.setView(layout);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,datas);
		final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Utility.log(toString(), "onClickSearchButton");
				listener.receiveSearchIf(SearchableInformations.createSearchIf(category,spinner.getSelectedItem().toString(),search_type));
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				listener.receiveSearchIf(null);
			}
		});
		return builder;
	}
	
	/**
	 * 数値をひとつ入力して比較を行うダイアログ
	 * @param context
	 * @param search_type
	 * @param listener
	 * @param category
	 * @param min
	 * @param max
	 * @return
	 */
	public static AlertDialog.Builder createIntInputDialogBuilder(final Context context,final SearchTypes search_type,final SearchIfListener listener,final SearchIfCategory category,final int min,final int max){
		Utility.log(TAG, "createIntInputDialogBuilder");
		AlertDialog.Builder builder;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.int_input_dialog,null);
		builder = new AlertDialog.Builder(context);
		builder.setTitle(SearchableInformations.createDialogTitle(category, search_type));
		builder.setView(layout);
		final EditText edit=(EditText)layout.findViewById(R.id.edit);
		edit.setHint(min+"~"+max);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(OneCompareOptions.values()));
		final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try{
					int input=Integer.valueOf(edit.getText().toString());
					if(min<=input && input<=max){
						StringBuilder sb=new StringBuilder();
						sb.append(input);
						sb.append(" ");
						sb.append(OneCompareOptions.fromIndex(spinner.getSelectedItemPosition()).toString());
						listener.receiveSearchIf(SearchableInformations.createSearchIf(category, new String(sb), search_type));
					}else{
						Utility.popToast(context,"入力値が不正です");
					}
				}catch(NumberFormatException e){
					Utility.popToast(context,"入力がありません");
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("戻る", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				listener.receiveSearchIf(null);
				dialog.dismiss();
			}
		});
		return builder;
	}

	
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
	SearchableInformations(String name){this.name=name;}

	
	/**
	 * ポケモンのページでタイプをクリックしたときなど
	 * デフォルトの検索条件を返す
	 * @param search_if
	 * @return
	 */
	public String getDefaultSearchIf(String _case){
		return createSearchIf(this,_case,SearchTypes.FILTER);
	}
	/**
	 * デフォルトの検索結果タイトルを返す
	 * @param _case
	 * @return
	 */
	public String getDefaultTitle(String _case){
		Utility.log(TAG, "getDefaultTitle");
		StringBuilder sb = new StringBuilder();
		sb.append("「");
		sb.append(_case);
		sb.append("」のポケモン");
		return new String(sb);
	}
	/**
	 * 検索条件文字列の種類が一致するかどうか
	 * @param category
	 * @return
	 */
	public boolean isCategory(String category){
		return toString().equals(category);
	}
	
	@Override
	public String toString(){return name;}



}