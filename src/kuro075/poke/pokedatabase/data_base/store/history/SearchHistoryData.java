package kuro075.poke.pokedatabase.data_base.store.history;

import java.util.Arrays;

import kuro075.poke.pokedatabase.data_base.BasicData;

/**
 * 検索条件の履歴データクラス
 * @author sanogenma
 *
 */
public class SearchHistoryData extends BasicData{
	private final String[] search_ifs;
	
	public SearchHistoryData(int no,String title,String[] search_ifs){
		super(title,no);
		this.search_ifs=search_ifs;
	}

	public String getTitle(){
		return toString();
	}
	public String[] getSearchIfs(){
		return search_ifs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(search_ifs);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchHistoryData other = (SearchHistoryData) obj;
		if (!Arrays.equals(search_ifs, other.search_ifs))
			return false;
		return Arrays.deepEquals(search_ifs, other.search_ifs);
	}
	
	/**
	 * 引数がこのデータのsearch_ifsと同じかどうか
	 * @param search_ifs
	 * @return
	 */
	public boolean equalsSearchIfs(String[] search_ifs){
		return Arrays.deepEquals(this.search_ifs, search_ifs);
	}
}
