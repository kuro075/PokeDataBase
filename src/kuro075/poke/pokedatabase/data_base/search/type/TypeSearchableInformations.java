package kuro075.poke.pokedatabase.data_base.search.type;

import android.content.Context;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.TypeSearchIfCategory;
import kuro075.poke.pokedatabase.util.Utility;

public enum TypeSearchableInformations implements TypeSearchIfCategory{
	KIND("種類"){

		@Override
		public CharacterData[] search(CharacterData[] chara_array,
				String category, String _case) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}

		
	};
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
