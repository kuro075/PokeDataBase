package kuro075.poke.pokedatabase.menu.poke_book;

import kuro075.poke.pokedatabase.menu.DefaultMenuActivity;
import kuro075.poke.pokedatabase.menu.MenuItems;
import android.view.Menu;

public class PokeBookMenuActivity extends DefaultMenuActivity{

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItems.POKE_HISTORY.addMenuItem(menu);
		MenuItems.POKE_STAR.addMenuItem(menu);
		MenuItems.POKE_SHORT_CUT.addMenuItem(menu);
		return super.onCreateOptionsMenu(menu);
	}
}
