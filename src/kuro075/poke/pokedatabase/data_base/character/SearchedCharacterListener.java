package kuro075.poke.pokedatabase.data_base.character;

/**
 * 検索されたとくせい配列を受け取るリスナー
 * @author sanogenma
 *
 */
public interface SearchedCharacterListener {
	/**
	 * 検索後のとくせい配列と検索条件を受け取るメソッド
	 * @param chara_array　検索後のポケモン配列
	 * @param search_condition　検索条件
	 */
	public void receiveCharacterArray(CharacterData[] chara_array,String search_condition);
}
