package kuro075.poke.pokedatabase.util;

import java.util.Arrays;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.searchable_informations.SearchableInformations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

	/**
	 * Listを逆順にする
	 * @param list
	 */
	public static void reverseList(List list){
		for(int i=list.size()-2;i>=0;i--){
    		list.add(list.get(i));
    		list.remove(i);
    	}
	}
	/**
	 * 配列を逆順にする
	 * @param array
	 */
	public static void reverseArray(Object[] array){
		final int last_index=array.length-1;
		for(int i=0,n=array.length/2;i<n;i++){
			Object tmp=array[i];
			array[i]=array[last_index-i];
			array[last_index-i]=tmp;
		}
	}

	/**
	 * Object配列をString配列に変換して返す
	 * @param array
	 * @return
	 */
	public static String[] changeToStringArray(Object[] array){
		String[] string_array=new String[array.length];
		for(int i=0,n=array.length;i<n;i++){
			string_array[i]=array[i].toString();
		}
		return string_array;
	}

	
	/**
	 * OK・Cancelボタンのついた確認ダイアログを開く
	 * @param context
	 * @param title
	 * @param listener
	 */
	public static void openCheckDialog(Context context,String title,DialogInterface.OnClickListener listener){
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setPositiveButton("OK",listener);
		builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * テキストを表示するダイアログ
	 * @param context
	 * @param title
	 * @param text
	 * @param ok_listener OKボタンを押した時のリスナー
	 */
	public static void openSimpleTextDialog(Context context,String title,String text,DialogInterface.OnClickListener ok_listener){
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(title);

		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.simple_text_dialog,null);
		builder.setView(layout);
		((TextView)layout.findViewById(R.id.text)).setText(text);
		
		builder.setPositiveButton("OK",ok_listener);
		builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
