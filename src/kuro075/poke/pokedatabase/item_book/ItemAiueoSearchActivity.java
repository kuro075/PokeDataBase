package kuro075.poke.pokedatabase.item_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.AiueoSearchActivity;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.item_book.ItemAiueoSearchActivity;
import kuro075.poke.pokedatabase.item_book.search_result.ItemSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemAiueoSearchActivity extends AiueoSearchActivity{
	private static final String TAG="ItemAiueoSearchActivity";
	/**
	 * このアクティビティーを開始する
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,ItemAiueoSearchActivity.class);
		context.startActivity(intent);
	}
	@Override
	protected void startSearchResultActivity(String title, String[] search_ifs) {
		// TODO Auto-generated method stub
		ItemSearchResultActivity.startThisActivity(this, title, search_ifs);
	}
	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.ITEM;
	}

}
