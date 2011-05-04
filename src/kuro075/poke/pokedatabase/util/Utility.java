package kuro075.poke.pokedatabase.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 汎用クラス
 * @author sanogenma
 *
 */
public class Utility {
	//デバッグ用
	public static final boolean DEBUG=true;
	
	//パッケージの名前
	public static final String PACKAGENAME="kuro075.poke.pokedatabase";
	public static final String DATAPATH="/data/data/"+PACKAGENAME+"/files/";
	
	/**
	 * データファイル名
	 * @author sanogenma
	 *
	 */
	public enum FileNames{
		CHARACTER("charadata.txt"),PERSONALITY("personality.txt"),POKEMON("pokedata.txt"),SKILL("skilldata.txt"),ITEM("item_data.txt");
		private final String name;
		FileNames(String name){this.name=name;}
		@Override
		public String toString(){return name;}
	}
	
	/**
	 * 設定保存用キー
	 * @author sanogenma
	 *
	 */
	public enum PreferenceKeys{
		VERSION("key_version");
		private final String key;
		PreferenceKeys(String key){this.key=key;}
		@Override
		public String toString(){return key;}
	}
	
	/**
	 * ログ
	 * @param tag
	 * @param msg
	 */
	public static final void log(String tag,String msg){
		if(DEBUG) Log.v(tag, msg);
	}

	/**
	 * トーストを表示
	 * @param context
	 * @param text
	 */
	public static void popToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
