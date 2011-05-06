package kuro075.poke.pokedatabase.data_base;

/**
 * 検索条件を受信するリスナー
 * @author sanogenma
 *
 */
public interface SearchIfListener {
	/**
	 * 検索条件を受信
	 * @param search_if
	 */
	public void receiveSearchIf(String search_if);
}
