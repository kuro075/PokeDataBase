package kuro075.poke.pokedatabase.menu.poke_book;

import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.menu.DefaultMenuActivity;
import kuro075.poke.pokedatabase.menu.MenuItems;
import kuro075.poke.pokedatabase.util.Utility;
import android.view.Menu;
import android.view.MenuItem;

public class PokeBookMenuActivity extends DefaultMenuActivity{
	private static final String TAG="PokeBookMenuActivity";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItems.HISTORY.addMenuItem(menu);//履歴
		MenuItems.STAR.addMenuItem(menu);//お気に入り
		MenuItems.SHORT_CUT.addMenuItem(menu);//ショートカット
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Utility.log(TAG, "onOptionsItemSelected");
		switch(MenuItems.fromId(item.getItemId())){
			case HISTORY:
				DataStore.DataTypes.POKEMON.openHistoryDialog(this);
				break;
			case STAR:
				DataStore.DataTypes.POKEMON.openStarDialog(this);
				break;
			case SHORT_CUT:
				DataStore.DataTypes.POKEMON.openShortCutDialog(this);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
