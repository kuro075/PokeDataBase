package kuro075.poke.pokedatabase.poke_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.DetailSearchActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.util.Utility;

public class PokeDetailSearchActivity extends DetailSearchActivity{
	private static final String TAG="PokeDetailSearchActivity";

	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,PokeDetailSearchActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.POKEMON;
	}

	@Override
	protected int getNumOfSearchableInformations() {
		// TODO Auto-generated method stub
		return PokeSearchableInformations.values().length;
	}

	@Override
	protected String getSearchableInformationTitle(int index) {
		// TODO Auto-generated method stub
		return PokeSearchableInformations.values()[index].toString();
	}

	@Override
	protected void openFilterDialog(int index, SearchIfListener listener) {
		// TODO Auto-generated method stub
		PokeSearchableInformations.values()[index].openDialog(this, SearchTypes.FILTER, listener);
	}
	
	
	@Override
	protected void startSearchResultActivity(String[] search_ifs) {
		// TODO Auto-generated method stub
		PokeSearchResultActivity.startThisActivity(this, getString(R.string.detail_search), search_ifs);
	}

}
