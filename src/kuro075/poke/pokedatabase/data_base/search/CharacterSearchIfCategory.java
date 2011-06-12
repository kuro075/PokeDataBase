package kuro075.poke.pokedatabase.data_base.search;

import kuro075.poke.pokedatabase.data_base.character.CharacterData;

public interface CharacterSearchIfCategory extends SearchIfCategory{
	/**
	 * chara_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param chara_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	public CharacterData[] search(CharacterData[] chara_array,String category,String _case);

}
