package kuro075.poke.pokedatabase.data_base.search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.search.poke.OneCompareOptions;
import kuro075.poke.pokedatabase.util.Utility;

public class SearchIf {
	private static final String TAG="SearchIf";
	
	private SearchIf(){}
	/**
	 * 検索条件を作成
	 * @param category　検索項目
	 * @param _case　検索条件
	 * @param type　検索方法
	 * @return category:_case(type)
	 */
	public static String createSearchIf(SearchIfCategory category,String _case,SearchTypes search_type){
		StringBuilder sb=new StringBuilder();
		sb.append(category.toString());
		sb.append(":");
		sb.append(_case);
		sb.append("(");
		sb.append(search_type.toString());
		sb.append(")");
		return new String(sb);
	}
	
	/**
	 * ダイアログのタイトルを作成
	 * @param category
	 * @param type
	 * @return
	 */
	public static String createDialogTitle(SearchIfCategory category,SearchTypes type){
		StringBuilder sb=new StringBuilder();
		sb.append(category.toString());
		sb.append("(");
		sb.append(type.toString());
		sb.append(")");
		return new String(sb);
	}
	
	/**
	 * 除外が一匹の時の検索条件を取得
	 * @param remove_poke
	 * @return
	 */
	public static String getRemoveIf(PokeData remove_poke){
		return SearchTypes.REMOVE+":"+remove_poke.getName();
	}
	
	/**
	 * 除外が複数の時の検索条件を取得
	 * @param remove_pokes
	 * @return
	 */
	public static String getRemoveIf(PokeData[] remove_pokes){
		StringBuilder sb=new StringBuilder();
		sb.append(SearchTypes.REMOVE.toString());
		sb.append(":");
		for(int i=0,n=remove_pokes.length;i<n;i++){
			sb.append(remove_pokes[i].getName());
			if(i<n-1){
				sb.append("/");
			}
		}
		return new String(sb);
	}
	/**
	 * スピナーのみの検索ダイアログのビルダーを取得
	 * @param context
	 * @param search_type
	 * @param listener
	 * @param category
	 * @param datas
	 * @return
	 */
	public static AlertDialog.Builder createSimpleSpinnerDialogBuilder(Context context,final SearchTypes search_type,final SearchIfListener listener,final SearchIfCategory category,final String[] datas){
		Utility.log(TAG, "createSimpleSpinnerDialogBuilder");
		AlertDialog.Builder builder;
		LayoutInflater factory=LayoutInflater.from(context);
		
		final View layout = factory.inflate(R.layout.simple_spinner_dialog, null);
		builder = new AlertDialog.Builder(context);
		builder.setTitle(SearchIf.createDialogTitle(category,search_type));
		builder.setView(layout);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,datas);
		final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Utility.log(toString(), "onClickSearchButton");
				listener.receiveSearchIf(SearchIf.createSearchIf(category,spinner.getSelectedItem().toString(),search_type));
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.receiveSearchIf(null);
			}
		});
		return builder;
	}
	
	/**
	 * 数値をひとつ入力して比較を行うダイアログ
	 * @param context
	 * @param search_type
	 * @param listener
	 * @param category
	 * @param min
	 * @param max
	 * @return
	 */
	public static AlertDialog.Builder createIntInputDialogBuilder(final Context context,final SearchTypes search_type,final SearchIfListener listener,final SearchIfCategory category,final int min,final int max){
		Utility.log(TAG, "createIntInputDialogBuilder");
		AlertDialog.Builder builder;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.int_input_dialog,null);
		builder = new AlertDialog.Builder(context);
		builder.setTitle(SearchIf.createDialogTitle(category, search_type));
		builder.setView(layout);
		final EditText edit=(EditText)layout.findViewById(R.id.edit);
		edit.setHint(min+"~"+max);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(OneCompareOptions.values()));
		final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					int input=Integer.valueOf(edit.getText().toString());
					if(min<=input && input<=max){
						StringBuilder sb=new StringBuilder();
						sb.append(input);
						sb.append(" ");
						sb.append(OneCompareOptions.fromIndex(spinner.getSelectedItemPosition()).toString());
						listener.receiveSearchIf(SearchIf.createSearchIf(category, new String(sb), search_type));
					}else{
						Utility.popToast(context,"入力値が不正です");
					}
				}catch(NumberFormatException e){
					Utility.popToast(context,"入力がありません");
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("戻る", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.receiveSearchIf(null);
				dialog.dismiss();
			}
		});
		return builder;
	}

	
	
}
