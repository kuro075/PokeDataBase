package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.EggGroups;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;

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
	REGION("地方") {
		/*===================================/
		 * 地方で検索
		/==================================*/
		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String region) {
			// TODO Auto-generated method stub
			return null;
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
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type) {
			// TODO Auto-generated method stub
			return TypeCategories.fromString(category).search(poke_array, category, type);
		}
	}
	
	/*,
	CHARACTER("特性"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	KIND("種類"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	SPEC("種族値"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	EFF("努力値"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	SKILL("わざ"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	MAX_PHYSICAL_DEFENSE("最大物理耐久"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	MAX_SPECIAL_DEFENSE("最大特殊耐久"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	HEIGHT("高さ"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	},
	WEIGHT("重さ"){

		@Override
		public String getDefaultSearchIf(String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCategory(String category) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}*/,
	EGG_GROUP("タマゴグループ"){
		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
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
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
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
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
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
	FINAL_EX("最終経験値"){
		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			
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
	};
	
	
	private static final String TAG="SeachableInformations for poke";
	
	/**
	 * 検索条件を作成
	 * @param category　検索項目
	 * @param _case　検索条件
	 * @param type　検索方法
	 * @return category:_case(type)
	 */
	public static String createSearchIf(SearchIfCategory category,String _case,SearchTypes type){
		StringBuilder sb=new StringBuilder();
		sb.append(category.toString());
		sb.append(":");
		sb.append(_case);
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
						if(Arrays.binarySearch(searched_array, poke)<0){
							searched_set.add(poke);
						}
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
			if(info.toString().equals(category)) return info;
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
	
	@Override
	public SearchableInformations getBelongedInformation() {
		// TODO Auto-generated method stub
		return this;
	}
	
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