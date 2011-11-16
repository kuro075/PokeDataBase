package kuro075.poke.pokedatabase.menu;

import android.view.Menu;
import android.view.MenuItem;

public enum MenuItems {
	/*======================/
	 * DefaultMenuActivity  
	/======================*/
	MODE("図鑑選択",android.R.drawable.ic_menu_view),
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
	SEARCH_RESULT_MULTIPLE_SELECT("複数選択",android.R.drawable.ic_menu_agenda),
	SEARCH_RESULT_SINGLE_SELECT("複数選択 解除",android.R.drawable.ic_menu_close_clear_cancel),
	SEARCH_RESULT_REMOVE("除外",android.R.drawable.ic_menu_delete),
	SEARCH_RESULT_SAVE("ショートカットに登録",android.R.drawable.ic_menu_save),

	/*===============/
 	/  PageActivity  /
	/===============*/
	PAGE_SAVE("お気に入りに登録",android.R.drawable.ic_menu_save),
	/*===============/
 	/  PokePageActivity  /
	/===============*/
	VIEW_ALL_SKILL("覚えるわざ",android.R.drawable.ic_menu_compass),
	/*
	 * 汎用
	 */
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
