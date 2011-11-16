package kuro075.poke.pokedatabase.util;

import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.OneCompareOptions;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.SearchIfCategory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
	public static final boolean DEBUG=false;
	
	//パッケージの名前
	public static final String PACKAGENAME="kuro075.poke.pokedatabase";
	public static final String DATAPATH="/data/data/"+PACKAGENAME+"/files/";

	/* 文字列操作 */
	private static final String HIRA="あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽぁぃぅぇぉゃゅょっ-1234567890";
	private static final String KATA="アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポァィゥェォャュョッー1234567890ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｧｨｩｪｫｬｭｮｯ";

	/* すべてのひらがな*/
	private static final String[][] ALL_HIRAGANA={{"あ","い","う","え","お"},
												 {"か","き","く","け","こ","が","ぎ","ぐ","げ","ご"},
												 {"さ","し","す","せ","そ","ざ","じ","ず","ぜ","ぞ"},
												 {"た","ち","つ","て","と","だ","ぢ","づ","で","ど"},
												 {"な","に","ぬ","ね","の"},
												 {"は","ひ","ふ","へ","ほ","ば","び","ぶ","べ","ぼ","ぱ","ぴ","ぷ","ぺ","ぽ"},
												 {"ま","み","む","め","も"},
												 {"や","ゆ","よ"},
												 {"ら","り","る","れ","ろ"},
												 {"わ","を","ん"}};
	
	/**
	 * ひらがなの行を取得
	 * @param index
	 * @return
	 */
	public static String[] getAiueoLine(int index){
		return ALL_HIRAGANA[index].clone();
	}
	
	/**
	 * 平仮名を含む文字列を全てカタカナの文字列に変換して返す
	 * 平仮名、カタカナ以外を含む場合は空の文字列("")を返す
	 * @param text
	 * @return
	 */
	public static String changeHiraToKata(String text){
		StringBuilder sb=new StringBuilder();
		for(int i=0,n=text.length();i<n;i++){
			char c=text.charAt(i);
			//カタカナの場合
			if(KATA.indexOf(c)>=0){
				sb.append(c);
				continue;
			}
			
			int index=HIRA.indexOf(c);
			//平仮名の場合
			if(index>=0){
				sb.append(KATA.charAt(index));
				continue;
			}
			//それ以外
			return "";
		}
		return new String(sb);
	}
	/**
	 * カタカナを含む文字列を全てひらがなの文字列に変換して返す
	 * 平仮名、カタカナ以外を含む場合は空の文字列("")を返す
	 * @param text
	 * @return
	 */
	public static String changeKataToHira(String text){
		StringBuilder sb=new StringBuilder();
		for(int i=0,n=text.length();i<n;i++){
			char c=text.charAt(i);
			//カタカナの場合
			if(HIRA.indexOf(c)>=0){
				sb.append(c);
				continue;
			}
			
			int index=KATA.indexOf(c);
			//平仮名の場合
			if(index>=0){
				sb.append(HIRA.charAt(index));
				continue;
			}
			//それ以外
			return "";
		}
		return new String(sb);
	}
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
	public static void openCheckDialog(Context context,String title,DialogInterface.OnClickListener ok_listener){
		openCheckDialog(context,title,ok_listener,new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}

	public static void openCheckDialog(Context context,String title,DialogInterface.OnClickListener ok_listener,DialogInterface.OnClickListener cancel_listener){
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setPositiveButton("OK",ok_listener);
		builder.setNegativeButton("Cancel",cancel_listener);
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
		openSimpleTextDialog(context,title,text,ok_listener,new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	/**
	 * テキストを表示するダイアログ
	 * @param context
	 * @param title
	 * @param text
	 * @param ok_listener OKボタンを押した時のリスナー
	 * @param cancel_listener Cancelボタンを押した時のリスナー
	 */
	public static void openSimpleTextDialog(Context context,String title,String text,DialogInterface.OnClickListener ok_listener,DialogInterface.OnClickListener cancel_listener){
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(title);

		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.simple_text_dialog,null);
		builder.setView(layout);
		((TextView)layout.findViewById(R.id.text)).setText(text);
		
		builder.setPositiveButton("OK",ok_listener);
		builder.setNegativeButton("Cancel",cancel_listener);
		builder.create().show();
	}


	

}
