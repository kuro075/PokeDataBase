package kuro075.poke.pokedatabase.type_book.search_result;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.search.skill.SkillSearchableInformations;
import kuro075.poke.pokedatabase.data_base.search.type.TypeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.skill_book.search_result.SkillSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

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
		Intent intent = new Intent(context,SkillSearchResultActivity.class);
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
		Intent intent = new Intent(context,SkillSearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
}
