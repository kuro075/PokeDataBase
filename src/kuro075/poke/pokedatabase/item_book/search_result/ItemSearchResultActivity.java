package kuro075.poke.pokedatabase.item_book.search_result;

import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.item.ItemSearchableInformations;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.data_base.viewable_informations.ItemViewableInformations;
import kuro075.poke.pokedatabase.item_book.item_page.ItemPageActivity;
import kuro075.poke.pokedatabase.item_book.search_result.ItemSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemSearchResultActivity extends SearchResultActivity{
	private static final String TAG="ItemSearchResultActivity";

	/**
	 * このアクティビティーをstartさせる
	 * 検索条件なし
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity with no search_if");
		startThisActivity(context,"全アイテム",new String[0]);
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
		Intent intent = new Intent(context,ItemSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
		if(search_ifs.length>0){
			DataStore.DataTypes.ITEM.getHistoryStore().addSearchDataWithoutTitle(search_ifs);
		}
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件がデフォルトのとき
	 * @param context　
	 * @param info　検索項目
	 * @param _case 検索条件
	 */
	public static void startThisActivityWithDefaultSearch(Context context,ItemSearchableInformations info,String _case){
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
		Intent intent = new Intent(context,ItemSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
	@Override
	protected void clickListItem(BasicData data) {
		ItemPageActivity.startThisActivity(this, (ItemData)data);
	}

	@Override
	protected BasicData[] getAllDatas() {
		return ItemDataManager.INSTANCE.getAllData();
	}
	@Override
	protected Comparator getComparatorByViewableInformation(int index) {
		return ItemViewableInformations.values()[index].getComparator();
	}
	@Override
	protected DataTypes getDataType() {
		return DataStore.DataTypes.ITEM;
	}
	@Override
	protected int getMaxLengthOfName() {
		return 7;
	}

	@Override
	protected boolean getNoVisible() {
		return false;
	}

	@Override
	protected String[] getSearchableInformationTitles() {
		return Utility.changeToStringArray(ItemSearchableInformations.values());
	}

	@Override
	protected String getViewableInformation(int index, BasicData data) {
		return ItemViewableInformations.values()[index].getInformation((ItemData)data);
	}

	@Override
	protected String getViewableInformationTitle(int index) {
		return ItemViewableInformations.values()[index].toString();
	}
	
	@Override
	protected String[] getViewableInformationTitles() {
		return Utility.changeToStringArray(ItemViewableInformations.values());
	}

	@Override
	protected void openSearchDialog(int index, Context context,
			SearchTypes search_type, SearchIfListener listener) {
		ItemSearchableInformations.values()[index].openDialog(context, search_type, listener);
	}

	@Override
	protected BasicData[] search(BasicData[] data_array, String search_if) {
		return ItemSearchableInformations.searchBySearchIf((ItemData[])data_array, search_if);
	}

}
