package kuro075.poke.pokedatabase.menu.book;

import android.view.Menu;
import android.view.MenuItem;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.menu.DefaultMenuActivity;
import kuro075.poke.pokedatabase.menu.MenuItems;
import kuro075.poke.pokedatabase.util.Utility;

/**
 * 各ブック共通のメニュー項目を搭載したアクティビティー
 * サブクラスはgetDataType()メソッドをオーバーライドすることで自身のデータタイプに対応
 * @author sanogenma
 *
 */
public class BookMenuActivity extends DefaultMenuActivity{
	private static final DataStore.DataTypes DEFAULT_DATA_TYPE=DataStore.DataTypes.POKEMON;
	private static final String TAG="BookMenuActivity";
	
	
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
				getDataType().openHistoryDialog(this);
				break;
			case STAR:
				getDataType().openStarDialog(this);
				break;
			case SHORT_CUT:
				getDataType().openShortCutDialog(this);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * データタイプを取得
	 * サブクラスは、このメソッドをオーバーライドすることで各ブックに対応
	 * @return
	 */
	protected DataStore.DataTypes getDataType(){
		return DEFAULT_DATA_TYPE;
	}
}
