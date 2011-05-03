package kuro075.poke.pokedatabase.data_base.item;

/**
 * 検索されたアイテム配列を受け取るリスナー
 * @author sanogenma
 *
 */
public interface SearchedItemListener {
	/**
	 * 検索後のポケモン配列と検索条件を受け取るメソッド
	 * @param poke_array　検索後のポケモン配列
	 * @param search_condition　検索条件
	 */
	public void receiveItemArray(ItemData[] item_array,String search_condition);

}
