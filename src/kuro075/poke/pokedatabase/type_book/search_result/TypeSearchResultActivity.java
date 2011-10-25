package kuro075.poke.pokedatabase.type_book.search_result;

import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.type.TypeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.type_book.type_page.TypePageActivity;
import kuro075.poke.pokedatabase.util.Utility;
import kuro075.poke.pokedatabase.data_base.type.TypeDataForSearch;
import kuro075.poke.pokedatabase.data_base.viewable_informations.TypeViewableInformations;

public class TypeSearchResultActivity extends SearchResultActivity{

	private static final String TAG="TypeSearchResultActivity";
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件なし
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity with no search_if");
		startThisActivity(context,"全タイプ",new String[0]);
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
		Intent intent = new Intent(context,TypeSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
		if(search_ifs.length>0){
			DataStore.DataTypes.TYPE.getHistoryStore().addSearchDataWithoutTitle(search_ifs);
		}
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件がデフォルトのとき
	 * @param context　
	 * @param info　検索項目
	 * @param _case 検索条件
	 */
	public static void startThisActivityWithDefaultSearch(Context context,TypeSearchableInformations info,String _case){
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
		Intent intent = new Intent(context,TypeSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
	
	@Override
	protected void clickListItem(BasicData data) {
		// TODO Auto-generated method stub
		TypePageActivity.startThisActivity(this, data.toString());
	}
	@Override
	protected BasicData[] getAllDatas() {
		// TODO Auto-generated method stub
		return TypeDataManager.INSTANCE.getAllData();
	}
	@Override
	protected Comparator getComparatorByViewableInformation(int index) {
		// TODO Auto-generated method stub
		return TypeViewableInformations.values()[index].getComparator();
	}
	@Override
	protected int getMaxLengthOfName() {
		// TODO Auto-generated method stub
		return 8;
	}
	@Override
	protected boolean getNoVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected String[] getSearchableInformationTitles() {
		// TODO Auto-generated method stub
		return Utility.changeToStringArray(TypeSearchableInformations.values());
	}
	@Override
	protected String getViewableInformation(int index, BasicData data) {
		// TODO Auto-generated method stub
		return TypeViewableInformations.values()[index].getInformation((TypeDataForSearch)data);
	}
	@Override
	protected String getViewableInformationTitle(int index) {
		// TODO Auto-generated method stub
		return TypeViewableInformations.values()[index].toString();
	}
	@Override
	protected String[] getViewableInformationTitles() {
		// TODO Auto-generated method stub
		return Utility.changeToStringArray(TypeViewableInformations.values());
	}
	@Override
	protected void openSearchDialog(int index, Context context,
			SearchTypes search_type, SearchIfListener listener) {
		// TODO Auto-generated method stub
		TypeSearchableInformations.values()[index].openDialog(context, search_type, listener);
	}
	@Override
	protected BasicData[] search(BasicData[] data_array, String search_if) {
		// TODO Auto-generated method stub
		return TypeSearchableInformations.searchBySearchIf((TypeDataForSearch[])data_array,search_if);
	}
}
