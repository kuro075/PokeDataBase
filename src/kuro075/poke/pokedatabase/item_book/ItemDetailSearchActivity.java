package kuro075.poke.pokedatabase.item_book;

import android.content.Context;
import android.content.Intent;
import kuro075.poke.pokedatabase.DetailSearchActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.item.ItemSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.item_book.ItemDetailSearchActivity;
import kuro075.poke.pokedatabase.item_book.search_result.ItemSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemDetailSearchActivity extends DetailSearchActivity{
	private static final String TAG="ItemDetailSearchActivity";

	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,ItemDetailSearchActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.ITEM;
	}

	@Override
	protected int getNumOfSearchableInformations() {
		// TODO Auto-generated method stub
		return ItemSearchableInformations.values().length;
	}

	@Override
	protected String getSearchableInformationTitle(int index) {
		// TODO Auto-generated method stub
		return ItemSearchableInformations.values()[index].toString();
	}

	@Override
	protected void openFilterDialog(int index, SearchIfListener listener) {
		// TODO Auto-generated method stub
		ItemSearchableInformations.values()[index].openDialog(this, SearchTypes.FILTER, listener);
	}
	
	@Override
	protected void startSearchResultActivity(String[] search_ifs) {
		// TODO Auto-generated method stub
		ItemSearchResultActivity.startThisActivity(this, getString(R.string.detail_search), search_ifs);
	}

}
