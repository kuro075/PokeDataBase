package kuro075.poke.pokedatabase.menu;

import android.view.Menu;
import android.view.MenuItem;

public enum MenuItems {
	/*======================/
	 * DefaultMenuActivity  
	/======================*/
	MODE("モード選択",android.R.drawable.ic_menu_view),
	PREFERENCE("設定",android.R.drawable.ic_menu_preferences),
	/*=======================/
	 * BookMenuActivity
	/=======================*/
	HISTORY("履歴",android.R.drawable.ic_menu_recent_history),
	STAR("お気に入り",android.R.drawable.star_big_off),
	SHORT_CUT("ショートカット",android.R.drawable.ic_menu_set_as),
	/*===========================/
	 * SearchResultMenuActivity
	/===========================*/
	SEARCH_RESULT_VIEW_CHANGE("表示切替",android.R.drawable.ic_menu_manage),
	SEARCH_RESULT_OPERATE("操作",android.R.drawable.ic_menu_zoom),
	//SEARCH_RESULT_SELECT("複数選択",android.R.drawable.),
	/*
	 * 汎用
	 */
	SAVE("登録",android.R.drawable.ic_menu_save),
	UNDO("元に戻す",android.R.drawable.ic_menu_revert);
	
	
	private final String name;
	private final int icon;
	MenuItems(String name,int icon){this.name=name;this.icon=icon;}
	@Override
	public String toString(){return name;}
	public int getId(){return this.ordinal();}
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
		return values()[id];
	}

}
