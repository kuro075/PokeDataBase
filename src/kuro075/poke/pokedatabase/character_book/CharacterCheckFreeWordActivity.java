package kuro075.poke.pokedatabase.character_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.CheckFreeWordActivity;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.character_book.CharacterCheckFreeWordActivity;
import kuro075.poke.pokedatabase.character_book.search_result.CharacterSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class CharacterCheckFreeWordActivity extends CheckFreeWordActivity{
	private static final String TAG="CharacterCheckFreeWordActivity";
	/**
	 * このアクティビティーをスタートさせる
	 * @param context
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String[] search_ifs){
		Utility.log(TAG, "startThisActivity:");
		Intent intent = new Intent(context,CharacterCheckFreeWordActivity.class);
		intent.putExtra(KEY_SEARCH_IFS, search_ifs);
		context.startActivity(intent);
	}
	
	@Override
	protected void startSearchResultActivity(String title, String[] search_ifs) {
		// TODO Auto-generated method stub
		CharacterSearchResultActivity.startThisActivity(this,title,search_ifs);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.CHARACTER;
	}

}
