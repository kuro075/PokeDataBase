package kuro075.poke.pokedatabase.data_base.search;

import kuro075.poke.pokedatabase.data_base.item.ItemData;

public interface ItemSearchIfCategory extends SearchIfCategory{
	/**
	 * item_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param item_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	public ItemData[] search(ItemData[] item_array,String category,String _case);

}
