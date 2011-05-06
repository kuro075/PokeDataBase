package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;

/**
 * Typeのカテゴリー
 * @author sanogenma
 *
 */
enum TypeCategories implements SearchIfCategory{
	MYSELF("タイプ"){
		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * 完成
		 */
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				Utility.log(TAG, toString()+".search");
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasType(TypeData.fromString(type))){
						searched_list.add(poke);
					}
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	RELATION("タイプ相性"){
		@Override
		public void openDialog(Context context, PokeData[] pokes,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String search_if) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	private static final String TAG="TypeCategories";
	private final String name;
	TypeCategories(String name){
		this.name=name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public SearchableInformations getBelongedInformation() {
		// TODO Auto-generated method stub
		return SearchableInformations.TYPE;
	}
	
	/**
	 * 文字列から取得
	 * @param name
	 * @return
	 */
	public static TypeCategories fromString(String name){
		for(TypeCategories category:values()){
			if(category.toString().equals(name)){
				return category;
			}
		}
		return null;
	}

}
