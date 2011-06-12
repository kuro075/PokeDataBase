package kuro075.poke.pokedatabase.poke_book;

import java.util.Comparator;

import android.content.Context;
import kuro075.poke.pokedatabase.SearchResultActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.data_base.viewable_informations.PokeViewableInformations;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class PokeSearchResultActivity extends SearchResultActivity{
	private static final String TAG="PokeSearchResultActivity";
	@Override
	protected void clickListItem(BasicData data) {
		Utility.log(TAG, "clickPokeListItem");
		PokePageActivity.startThisActivity(this, (PokeData)data);
	}

	@Override
	protected BasicData[] getAllDatas() {
		return PokeDataManager.INSTANCE.getAllPokeData();
	}

	@Override
	protected Comparator getComparatorByViewableInformation(int index) {
		return PokeViewableInformations.values()[index].getComparator();
	}

	@Override
	protected int getMaxLengthOfName() {
		return 6;
	}

	@Override
	protected boolean getNoVisible() {
		return true;
	}

	@Override
	protected String[] getSearchableInformationTitles() {
		return Utility.changeToStringArray(PokeSearchableInformations.values());
	}

	@Override
	protected String getViewableInformation(int index, BasicData data) {
		return PokeViewableInformations.values()[index].getInformation((PokeData)data);
	}

	@Override
	protected String getViewableInformationTitle(int index) {
		return PokeViewableInformations.values()[index].toString();
	}

	@Override
	protected String[] getViewableInformationTitles() {
		// TODO Auto-generated method stub
		return Utility.changeToStringArray(PokeViewableInformations.values());
	}

	@Override
	protected void openSearchDialog(int index, Context context,
			SearchTypes search_type, SearchIfListener listener) {
		// TODO Auto-generated method stub
		PokeSearchableInformations.values()[index].openDialog(context, search_type, listener);
	}

	@Override
	protected BasicData[] search(BasicData[] data_array, String search_if) {
		// TODO Auto-generated method stub
		return PokeSearchableInformations.searchBySearchIf((PokeData[])data_array, search_if);
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataTypes.POKEMON;
	}

}
