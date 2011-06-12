package kuro075.poke.pokedatabase.poke_book;

import kuro075.poke.pokedatabase.AiueoSearchActivity;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;

public class PokeAiueoSearchActivity extends AiueoSearchActivity{
	private static final String TAG="PokeAiueoSearchActivity";
	/**
	 * このアクティビティーを開始する
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,PokeAiueoSearchActivity.class);
		context.startActivity(intent);
	}
	@Override
	protected void startSearchResultActivity(String title, String[] search_ifs) {
		// TODO Auto-generated method stub
		PokeSearchResultActivity.startThisActivity(this, title, search_ifs);
	}
	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.POKEMON;
	}
	
}
