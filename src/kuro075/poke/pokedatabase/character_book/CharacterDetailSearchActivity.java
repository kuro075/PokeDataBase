package kuro075.poke.pokedatabase.character_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.DetailSearchActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.character.CharacterSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.character_book.CharacterDetailSearchActivity;
import kuro075.poke.pokedatabase.character_book.search_result.CharacterSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class CharacterDetailSearchActivity extends DetailSearchActivity{
	private static final String TAG="CharacterDetailSearchActivity";

	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,CharacterDetailSearchActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.CHARACTER;
	}

	@Override
	protected int getNumOfSearchableInformations() {
		// TODO Auto-generated method stub
		return CharacterSearchableInformations.values().length;
	}

	@Override
	protected String getSearchableInformationTitle(int index) {
		// TODO Auto-generated method stub
		return CharacterSearchableInformations.values()[index].toString();
	}

	@Override
	protected void openFilterDialog(int index, SearchIfListener listener) {
		// TODO Auto-generated method stub
		CharacterSearchableInformations.values()[index].openDialog(this, SearchTypes.FILTER, listener);
	}
	
	@Override
	protected void startSearchResultActivity(String[] search_ifs) {
		// TODO Auto-generated method stub
		CharacterSearchResultActivity.startThisActivity(this, getString(R.string.detail_search), search_ifs);
	}

}
