package kuro075.poke.pokedatabase.menu;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class DefaultMenuActivity extends Activity{
	private static final String TAG="DefaultMenuActivity";
	
	/**
	 * メニューを作成
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItems.MODE.addMenuItem(menu);
		MenuItems.PREFERENCE.addMenuItem(menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * メニュー項目が選択された時のメソッド
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
}
