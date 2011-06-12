package kuro075.poke.pokedatabase.data_base.search;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;

public interface PokeSearchIfCategory extends SearchIfCategory{
	
	/**
	 * poke_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param poke_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	public PokeData[] search(PokeData[] poke_array,String category,String _case);

}
