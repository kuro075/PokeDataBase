package kuro075.poke.pokedatabase.skill_book;

import kuro075.poke.pokedatabase.CheckFreeWordActivity;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.skill_book.search_result.SkillSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;

public class SkillCheckFreeWordActivity extends CheckFreeWordActivity{
	private static final String TAG="SkillCheckFreeWordActivity";
	/**
	 * このアクティビティーをスタートさせる
	 * @param context
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String[] search_ifs){
		Utility.log(TAG, "startThisActivity:");
		Intent intent = new Intent(context,SkillCheckFreeWordActivity.class);
		intent.putExtra(KEY_SEARCH_IFS, search_ifs);
		context.startActivity(intent);
	}
	
	@Override
	protected void startSearchResultActivity(String title, String[] search_ifs) {
		// TODO Auto-generated method stub
		SkillSearchResultActivity.startThisActivity(this,title,search_ifs);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.POKEMON;
	}

}
