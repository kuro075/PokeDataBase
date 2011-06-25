package kuro075.poke.pokedatabase.character_book.search_result;

import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.character.CharacterSearchableInformations;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.data_base.viewable_informations.CharacterViewableInformations;
import kuro075.poke.pokedatabase.character_book.character_page.CharacterPageActivity;
import kuro075.poke.pokedatabase.character_book.search_result.CharacterSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class CharacterSearchResultActivity extends SearchResultActivity{
	private static final String TAG="CharacterSearchResultActivity";

	/**
	 * このアクティビティーをstartさせる
	 * 検索条件なし
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity with no search_if");
		startThisActivity(context,"全わざ",new String[0]);
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
		Intent intent = new Intent(context,CharacterSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
		if(search_ifs.length>0){
			DataStore.DataTypes.CHARACTER.getHistoryStore().addSearchDataWithoutTitle(search_ifs);
		}
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件がデフォルトのとき
	 * @param context　
	 * @param info　検索項目
	 * @param _case 検索条件
	 */
	public static void startThisActivityWithDefaultSearch(Context context,CharacterSearchableInformations info,String _case){
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
		Intent intent = new Intent(context,CharacterSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
	@Override
	protected void clickListItem(BasicData data) {
		CharacterPageActivity.startThisActivity(this, (CharacterData)data);
	}

	@Override
	protected BasicData[] getAllDatas() {
		return CharacterDataManager.INSTANCE.getAllData();
	}
	@Override
	protected Comparator getComparatorByViewableInformation(int index) {
		return CharacterViewableInformations.values()[index].getComparator();
	}
	@Override
	protected DataTypes getDataType() {
		return DataStore.DataTypes.CHARACTER;
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
		return Utility.changeToStringArray(CharacterSearchableInformations.values());
	}

	@Override
	protected String getViewableInformation(int index, BasicData data) {
		return CharacterViewableInformations.values()[index].getInformation((CharacterData)data);
	}

	@Override
	protected String getViewableInformationTitle(int index) {
		return CharacterViewableInformations.values()[index].toString();
	}
	
	@Override
	protected String[] getViewableInformationTitles() {
		return Utility.changeToStringArray(CharacterViewableInformations.values());
	}

	@Override
	protected void openSearchDialog(int index, Context context,
			SearchTypes search_type, SearchIfListener listener) {
		CharacterSearchableInformations.values()[index].openDialog(context, search_type, listener);
	}

	@Override
	protected BasicData[] search(BasicData[] data_array, String search_if) {
		return CharacterSearchableInformations.searchBySearchIf((CharacterData[])data_array, search_if);
	}

}
