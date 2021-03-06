package kuro075.poke.pokedatabase.item_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.CheckFreeWordActivity;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.item_book.ItemCheckFreeWordActivity;
import kuro075.poke.pokedatabase.item_book.search_result.ItemSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemCheckFreeWordActivity extends CheckFreeWordActivity{
	private static final String TAG="ItemCheckFreeWordActivity";
	/**
	 * このアクティビティーをスタートさせる
	 * @param context
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String[] search_ifs){
		Utility.log(TAG, "startThisActivity:");
		Intent intent = new Intent(context,ItemCheckFreeWordActivity.class);
		intent.putExtra(KEY_SEARCH_IFS, search_ifs);
		context.startActivity(intent);
	}
	
	@Override
	protected void startSearchResultActivity(String title, String[] search_ifs) {
		// TODO Auto-generated method stub
		ItemSearchResultActivity.startThisActivity(this,title,search_ifs);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.ITEM;
	}

}
