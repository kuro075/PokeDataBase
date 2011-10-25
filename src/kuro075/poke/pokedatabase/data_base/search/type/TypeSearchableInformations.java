package kuro075.poke.pokedatabase.data_base.search.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.TypeSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.type.TypeDataForSearch;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.util.Utility;

/**
 * タイプ検索項目
 * @author sanogenma
 *
 */
public enum TypeSearchableInformations implements TypeSearchIfCategory{
	KIND("種類"){
		private final String[] kinds={"単タイプ","複合タイプ"};
		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO 検索ダイアログを開く
			
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO フリーワード検索時の動作
			return null;
		}

		@Override
		public TypeDataForSearch[] search(TypeDataForSearch[] type_array,
				String category, String _case) {
			List<TypeDataForSearch> type_list=new ArrayList<TypeDataForSearch>();
			for(int i=0;i<kinds.length;i++){
				if(kinds[i].equals(_case)){
					switch(i){
						case 0://単タイプ
							for(TypeDataForSearch type:type_array){
								if(type.isSingleType()){
									type_list.add(type);
								}
							}
							break;
						case 1://複合タイプ
							for(TypeDataForSearch type:type_array){
								if(type.isMultipleType()){
									type_list.add(type);
								}
							}
							break;
					}
					break;
				}
			}
			return type_list.toArray(new TypeDataForSearch[0]);
		}
	};
	
	public static TypeSearchableInformations fromCategory(String category){
		for(TypeSearchableInformations info:values()){
			if(info.isCategory(category)) return info;
		}
		return null;
	}

	
	
	/**
	 * フリーワード検索
	 * @param free_word
	 * @return
	 */
	public static String[] getSearchIfByFreeWord(String free_word){
		List<String> search_ifs=new ArrayList<String>();
		
		final String PERTITION="[,¥(¥)¥/ ]";
		//free_wordをパーティションで配列に分割
		String[] words=free_word.split(PERTITION);
		for(String word:words){
			//TODO 語尾の"のわざ","なわざ","技"を取り除く
			for(TypeSearchableInformations si:values()){
				String _case=si.getCaseByFreeWord(word);
				if(!_case.equals("")){
					search_ifs.add(SearchIf.createSearchIf(si, _case, SearchTypes.FILTER));
				}
			}
		}
		
		return search_ifs.toArray(new String[0]);
	}
	
	public static TypeDataForSearch[] searchBySearchIf(TypeDataForSearch[] chara_array,String search_if){
		Utility.log(TAG, "searchBySearchIf");
		String[] tmp=search_if.split("[:¥(¥)/]");//category , case(SearchTypes)に分けられる
		Utility.log(TAG, tmp.length+":"+Arrays.deepToString(tmp));
		TypeDataForSearch[] searched_array=null;
		Set<TypeDataForSearch> searched_set=new HashSet<TypeDataForSearch>();
		TypeSearchableInformations si=fromCategory(tmp[0]);
		if(si!=null){//検索であった場合
			switch(SearchTypes.fromString(tmp[2])){
				case FILTER:
					searched_array=si.search(chara_array,tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				case REMOVE:
					searched_array=si.search(chara_array,tmp[0], tmp[1]);
					for(TypeDataForSearch poke:chara_array){
						boolean flag=true;
						for(TypeDataForSearch target:searched_array){
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
					searched_array=si.search(TypeDataManager.INSTANCE.getAllData(),tmp[0], tmp[1]);
					searched_set.addAll(Arrays.asList(chara_array));
					searched_set.addAll(Arrays.asList(searched_array));
					break;
				default:
					searched_set.addAll(Arrays.asList(chara_array));
					break;
			}
		}
		else{//検索以外(除外、追加)
			SearchTypes st=SearchTypes.fromString(tmp[0]);
			searched_set.addAll(Arrays.asList(chara_array));
			switch(st){
				case REMOVE:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.remove(TypeDataManager.INSTANCE.getTypeData(tmp[i]));
					}
					break;
				case ADD:
					for(int i=1,n=tmp.length;i<n;i++){
						searched_set.add(TypeDataManager.INSTANCE.getTypeData(tmp[i]));
					}
					break;
				default:
			}
		}
		return searched_set.toArray(new TypeDataForSearch[0]);
	}
	
	private static final String TAG="TypeSearchableInformations";
	
	private final String name;

	TypeSearchableInformations(String name){this.name=name;}
	
	/**
	 * 特性のページでタイプをクリックしたときなど
	 * デフォルトの検索条件を返す
	 * @param search_if
	 * @return
	 */
	public String getDefaultSearchIf(String _case){
		return SearchIf.createSearchIf(this, _case, SearchTypes.FILTER);
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
		sb.append("」のタイプ");
		return new String(sb);
	}
	
	@Override
	public boolean isCategory(String category) {
		return toString().equals(category);
	}

	@Override
	public String toString(){return name;}
}
