package kuro075.poke.pokedatabase.data_base.poke;

/**
 * 検索されたポケモン配列を受け取るリスナー
 * @author sanogenma
 *
 */
public interface SearchedPokeListener{
	/**
	 * 検索後のポケモン配列と検索条件を受け取るメソッド
	 * @param poke_array　検索後のポケモン配列
	 * @param search_condition　検索条件
	 */
	public void receivePokeArray(PokeData[] poke_array,String search_if);
}