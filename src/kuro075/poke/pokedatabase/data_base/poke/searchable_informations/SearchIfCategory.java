package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import android.content.Context;

public interface SearchIfCategory {
	/**
	 * 所属しているSearchableInformationsを取得
	 * @return
	 */
	public SearchableInformations getBelongedInformation();
	/**
	 * 検索ダイアログを表示するメソッド
	 * @param context
	 * @param pokes ポケモンリスト
	 * @param search_type 検索の種類
	 * @param listener 検索後ポケを送るリスナー
	 */
	public void openDialog(Context context,PokeData[] pokes,SearchTypes search_type,SearchIfListener listener);
	/**
	 * poke_arrayから検索条件(文字列)似合うポケモンを検索して返すメソッド
	 * @param poke_array ポケモンリスト
	 * @param condition　検索条件
	 * @return　条件に合ったポケモンのリスト
	 */
	
	/**
	 * poke_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param poke_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	public PokeData[] search(PokeData[] poke_array,String category,String _case);
}
