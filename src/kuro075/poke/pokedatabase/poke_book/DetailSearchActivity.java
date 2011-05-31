package kuro075.poke.pokedatabase.poke_book;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.searchable_informations.SearchableInformations;
import kuro075.poke.pokedatabase.menu.book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.search_result.SearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 詳細検索アクティビティー
 * @author sanogenma
 *
 */
public class DetailSearchActivity extends PokeBookMenuActivity{
	/**
	 * 検索条件を取得した時のリスナー
	 * @author sanogenma
	 *
	 */
	private class MySearchIfListener implements SearchIfListener{
		final int index;
		final Context context;
		MySearchIfListener(Context context,int index){
			this.context=context;
			this.index=index;
		}
		@Override
		public void receiveSearchIf(String search_if) {
			if(search_if!=null){
				StringBuilder sb=new StringBuilder();
				if(search_ifs_list[index].indexOf(search_if)<0){
					search_ifs_list[index].add(search_if);
					setTextIf(index);
					refreshIfName(index);
					sb.append(search_if);
					sb.append(" を追加しました");
				}else{
					sb.append(search_if);
					sb.append(" は既に登録されています");
				}
				Utility.popToast(context,new String(sb));
			}
		}
		
	}
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book";
	
	private static final String TAG="DetailSearchActivity";
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,DetailSearchActivity.class);
		context.startActivity(intent);
	}
	private LinearLayout linear_layout;
	//条件名
	private TextView[] text_if_name;
	//条件表示トグルボタン
	private ToggleButton[] toggle_view_if;
	
	//条件追加
	private Button[] button_add_if;
	
	//条件
	private TextView[] text_if;	

	//画面下部ボタン　検索　条件表示　履歴
	private Button button_search,button_view_if,button_history;
	//検索条件
	private List<String>[] search_ifs_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_search_layout);
		Utility.log(TAG, "onCreate");
		final Context context=this;
		//画面上部
		final int length=SearchableInformations.values().length;
		linear_layout=(LinearLayout)findViewById(R.id.linear_layout);
		text_if_name=new TextView[length];
		toggle_view_if=new ToggleButton[length];
		button_add_if=new Button[length];
		text_if=new TextView[length];
		search_ifs_list = new List[length];
		
		for(int i=0;i<length;i++){
			final int index=i;
			LayoutInflater factory=LayoutInflater.from(linear_layout.getContext());
			final View layout = factory.inflate(R.layout.detail_search_item_layout, null);
			linear_layout.addView(layout);
			//条件
			search_ifs_list[i]=new ArrayList<String>();
			//条件名
			text_if_name[i]=((TextView)layout.findViewById(R.id.text));
			refreshIfName(i);
			//条件表示
			toggle_view_if[i]=(ToggleButton)layout.findViewById(R.id.toggle_button);
			toggle_view_if[i].setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked){
						setTextIf(index);
						text_if[index].setVisibility(View.VISIBLE);
					}else{
						text_if[index].setVisibility(View.GONE);
					}
				}
			});
			//条件追加
			button_add_if[i]=(Button)layout.findViewById(R.id.button);
			button_add_if[i].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					SearchableInformations.values()[index].openDialog(context, SearchTypes.FILTER, new MySearchIfListener(context,index));
				}
			});
			//条件テキスト
			text_if[i]=(TextView)layout.findViewById(R.id.text_search_ifs);
			
		}
		
		//画面下部
		//検索ボタン
		button_search=(Button)findViewById(R.id.button_search);
		button_search.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				List<String> list=new ArrayList<String>();
				for(List<String> al:search_ifs_list){
					list.addAll(al);
				}
				SearchResultActivity.startThisActivity(context, getString(R.string.searched_result), list.toArray(new String[0]));
			}
		});
		//条件表示ボタン
		button_view_if=(Button)findViewById(R.id.button_view_if);
		button_view_if.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				openSearchIfsDialog();
			}
		});
		//履歴ボタン
		button_history=(Button)findViewById(R.id.button_history);
		button_history.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 条件名を更新
	 * @param index
	 */
	private void refreshIfName(int index){
		StringBuilder sb=new StringBuilder();
		sb.append(SearchableInformations.values()[index].toString());
		int size=search_ifs_list[index].size();
		if(size>0){
			sb.append(" (");
			sb.append(size);
			sb.append("個)");
		}
		text_if_name[index].setText(new String(sb));
	}
	
	/**
	 * 条件テキストをセット
	 * @param index
	 */
	private void setTextIf(int index){
		StringBuilder sb=new StringBuilder();
		int size=search_ifs_list[index].size();
		if(size>0){
			sb.append(search_ifs_list[index].get(0));
			for(int i=1;i<size;i++){
				sb.append("\n");
				sb.append(search_ifs_list[index].get(i));
			}
		}else{
			sb.append("なし");
		}
		text_if[index].setText(new String(sb));
	}
	
	/**
	 * 検索条件を全て取得
	 * @return
	 */
	private String[] getAllSearchIfs(){
		List<String> list=new ArrayList<String>();
		for(List<String> al:search_ifs_list){
			list.addAll(al);
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * 検索条件を削除
	 * @param search_if
	 */
	private boolean removeSearchIf(String search_if){
		for(int i=0,n=search_ifs_list.length;i<n;i++){
			if(search_ifs_list[i].remove(search_if)){
				refreshIfName(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 条件ダイアログを表示
	 * 条件と削除ボタンを並べる
	 */
	private void openSearchIfsDialog(){
		Utility.log(TAG, "openSearchIfsDialog");
		final Context context=this;
		String[] search_ifs=getAllSearchIfs();
		
		AlertDialog.Builder builder;
		LayoutInflater factory=LayoutInflater.from(this);
		
		final View layout = factory.inflate(R.layout.detail_if_dialog, null);
		builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.view_if));
		builder.setView(layout);
		
		LinearLayout linear_layout=(LinearLayout)layout.findViewById(R.id.layout);
		
		builder.setNegativeButton("閉じる",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		final AlertDialog dialog=builder.create();
		
		View[] view=new View[search_ifs.length];
		for(int i=0,n=view.length;i<n;i++){
			LayoutInflater factory2=LayoutInflater.from(linear_layout.getContext());
			view[i]=factory2.inflate(R.layout.text_button_item, null);
			final TextView text=(TextView)view[i].findViewById(R.id.text);
			text.setText(search_ifs[i]);
			Button remove_button=(Button)view[i].findViewById(R.id.button);
			remove_button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					final String search_if=text.getText().toString();
					StringBuilder sb=new StringBuilder();
					sb.append(search_if);
					sb.append(" を削除しますか？");
					Utility.openCheckDialog(context, new String(sb), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeSearchIf(search_if);
							StringBuilder sb=new StringBuilder();
							sb.append(search_if);
							sb.append(" を削除しました");
							Utility.popToast(context, new String(sb));
							openSearchIfsDialog();
						}
					},
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							openSearchIfsDialog();
						}
					});
					dialog.dismiss();
				}
			});
			linear_layout.addView(view[i]);
		}

		
		dialog.show();
	}
}
