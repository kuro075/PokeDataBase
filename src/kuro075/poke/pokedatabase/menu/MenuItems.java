package kuro075.poke.pokedatabase.menu;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

public enum MenuItems {
	/*======================/
	 * DefaultMenuActivity  
	/======================*/
	MODE("Mode",0,android.R.drawable.ic_menu_view),
	/*=======================/
	 * PokeBookMenuActivity
	/=======================*/
	POKE_HISTORY("履歴",1,android.R.drawable.ic_menu_recent_history),
	POKE_STAR("お気に入り",2,android.R.drawable.star_big_off),
	POKE_SHORT_CUT("ShortCut",3,android.R.drawable.ic_menu_set_as),
	/*===========================/
	 * SearchResultMenuActivity
	/===========================*/
	POKE_SEARCH_RESULT_VIEW_CHANGE("表示切替",4,android.R.drawable.ic_menu_manage),
	POKE_SEARCH_RESULT_OPERATE("操作",5,android.R.drawable.ic_menu_zoom),
	POKE_SEARCH_RESULT_SAVE("条件を保存",6,android.R.drawable.ic_menu_save),
	/*POKE_SEARCH_RESULT_SELECT("複数選択",7,android.R.drawable.){
	 *}
	 */
	UNDO("元に戻す",98,android.R.drawable.ic_menu_revert),
	PREFERENCE("設定",99,android.R.drawable.ic_menu_preferences);
	
	private final String name;
	private final int id;
	private final int icon;
	MenuItems(String name,int id,int icon){this.name=name;this.id=id;this.icon=icon;}
	@Override
	public String toString(){return name;}
	public int getId(){return id;}
	/**
	 * メニューに項目を追加する
	 * @param menu
	 */
	public MenuItem addMenuItem(Menu menu){
		return menu.add(Menu.NONE,getId(),Menu.NONE,toString()).setIcon(icon);
	}

	/**
	 * IDからMenuItemを取得
	 * @param id
	 * @return
	 */
	public static MenuItems fromId(int id){
		for(MenuItems item:values()){
			if(item.id==id) return item;
		}
		return null;
	}
	public static void addDefaultMenuItems(Menu menu){
		
	}
}
