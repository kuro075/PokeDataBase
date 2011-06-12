package kuro075.poke.pokedatabase.poke_book;

import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.data_base.viewable_informations.PokeViewableInformations;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class PokeSearchResultActivity extends SearchResultActivity{
	private static final String TAG="PokeSearchResultActivity";
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件なし
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity with no search_if");
		startThisActivity(context,"全ポケモン",new String[0]);
	}
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件が一つのとき
	 * @param context
	 * @param title
	 * @param search_if
	 */
	public static void startThisActivity(Context context,String title,String search_if){
		Utility.log(TAG, "startThisActivity with a search_if");
		String[] ifs=new String[1];
		ifs[0]=search_if;
		startThisActivity(context,title,ifs);
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件が複数のとき
	 * @param context
	 * @param title
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String title,String[] search_ifs){
		Utility.log(TAG, "startThisActivity with search_ifs");
		Intent intent = new Intent(context,PokeSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
		if(search_ifs.length>0){
			DataStore.DataTypes.POKEMON.getHistoryStore().addSearchDataWithoutTitle(search_ifs);
		}
	}
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件がデフォルトのとき
	 * @param context　
	 * @param info　検索項目
	 * @param _case 検索条件
	 */
	public static void startThisActivityWithDefaultSearch(Context context,PokeSearchableInformations info,String _case){
		Utility.log(TAG, "startThisActivityWithDefaultSearch");
		String[] ifs=new String[1];
		ifs[0]=info.getDefaultSearchIf(_case);
		startThisActivity(context,info.getDefaultTitle(_case),ifs);
	}
	/**
	 * このアクティビティーをstartさせる
	 * 履歴を保存しない
	 * @param context
	 * @param title
	 * @param search_ifs
	 */
	public static void startThisActivityWithoutHistory(Context context,String title,String[] search_ifs){
		Utility.log(TAG, "startThisActivityWithoutHistory");
		Intent intent = new Intent(context,PokeSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
	
	/*
	 * 
	 * 
	 * 
	 * */
	@Override
	protected void clickListItem(BasicData data) {
		Utility.log(TAG, "clickPokeListItem");
		PokePageActivity.startThisActivity(this, (PokeData)data);
	}

	@Override
	protected BasicData[] getAllDatas() {
		return PokeDataManager.INSTANCE.getAllPokeData();
	}

	@Override
	protected Comparator getComparatorByViewableInformation(int index) {
		return PokeViewableInformations.values()[index].getComparator();
	}

	@Override
	protected int getMaxLengthOfName() {
		return 6;
	}

	@Override
	protected boolean getNoVisible() {
		return true;
	}

	@Override
	protected String[] getSearchableInformationTitles() {
		return Utility.changeToStringArray(PokeSearchableInformations.values());
	}

	@Override
	protected String getViewableInformation(int index, BasicData data) {
		return PokeViewableInformations.values()[index].getInformation((PokeData)data);
	}

	@Override
	protected String getViewableInformationTitle(int index) {
		return PokeViewableInformations.values()[index].toString();
	}

	@Override
	protected String[] getViewableInformationTitles() {
		// TODO Auto-generated method stub
		return Utility.changeToStringArray(PokeViewableInformations.values());
	}

	@Override
	protected void openSearchDialog(int index, Context context,
			SearchTypes search_type, SearchIfListener listener) {
		// TODO Auto-generated method stub
		PokeSearchableInformations.values()[index].openDialog(context, search_type, listener);
	}

	@Override
	protected BasicData[] search(BasicData[] data_array, String search_if) {
		// TODO Auto-generated method stub
		return PokeSearchableInformations.searchBySearchIf((PokeData[])data_array, search_if);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.POKEMON;
	}

}
