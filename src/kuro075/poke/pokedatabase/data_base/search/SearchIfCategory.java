package kuro075.poke.pokedatabase.data_base.search;

import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import android.content.Context;

/**
 * ポケモン図鑑の検索条件カテゴリーインターフェース
 * @author sanogenma
 *
 */
public interface SearchIfCategory {
	/**
	 * 検索ダイアログを表示するメソッド
	 * @param context
	 * @param search_type 検索の種類
	 * @param listener 検索後ポケを送るリスナー
	 */
	public void openDialog(final Context context,final SearchTypes search_type,final SearchIfListener listener);

	
	/**
	 * フリーワードから検索条件(_case)を取得
	 * 検索不可の場合,""を返す
	 * @param free_word
	 * @return
	 */
	public String getCaseByFreeWord(String free_word);
	/**
	 * data_arrayから検索条件(_case)に合うポケモンを検索して返すメソッド
	 * @param data_array
	 * @param category 検索カテゴリー
	 * @param _case 検索条件
	 * @return 検索後のポケモンリスト
	 */
	//public BasicData[] search(BasicData[] data_array,String category,String _case);

}
