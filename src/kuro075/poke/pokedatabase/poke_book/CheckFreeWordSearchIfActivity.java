package kuro075.poke.pokedatabase.poke_book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.menu.poke_book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.search_result.SearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class CheckFreeWordSearchIfActivity extends PokeBookMenuActivity{
	private static final String TAG="CheckFreeWordSearchIfActivity";
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book";
	
	private static final String KEY_SEARCH_IFS=PACKAGE+"."+TAG+"."+"search_ifs";
	
	/**
	 * このアクティビティーをスタートさせる
	 * @param context
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String[] search_ifs){
		Utility.log(TAG, "startThisActivity:");
		Intent intent = new Intent(context,CheckFreeWordSearchIfActivity.class);
		intent.putExtra(KEY_SEARCH_IFS, search_ifs);
		context.startActivity(intent);
	}
	
	List<String> search_if_list;
	List<View> view_list;
	int num_visible;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_search_if_layout);
		Utility.log(TAG,"onCreate");
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			search_if_list=Arrays.asList(extras.getStringArray(KEY_SEARCH_IFS));
			num_visible=search_if_list.size();
		}
		
		initSearchIf();
		final Context context=this;
		Button button_search=(Button)findViewById(R.id.button_search);
		button_search.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<String> list=new ArrayList<String>();
				for(View view:view_list){
					if(view.getVisibility()!=View.GONE){
						list.add(((TextView)view.findViewById(R.id.text)).getText().toString());
					}
				}
				SearchResultActivity.startThisActivity(context, "フリーワード検索", list.toArray(new String[0]));
			}
		});
	}

	
	/**
	 * 検索条件を削除する
	 * @param search_if
	 */
	private void removeSearchIf(View view){
		String search_if=((TextView)view.findViewById(R.id.text)).getText().toString();
		view.setVisibility(View.GONE);
		num_visible--;
		StringBuilder sb=new StringBuilder();
		if(num_visible>0){
			sb.append(search_if);
			sb.append(" を削除しました");
			Utility.popToast(this, new String(sb));
		}else{
			sb.append("検索がキャンセルされました");
			Utility.popToast(this,new String(sb));
			finish();
		}
	}
	
	/**
	 * 検索条件を初期化
	 */
	private void initSearchIf(){
		final Context context=this;
		LinearLayout linear_layout=(LinearLayout)findViewById(R.id.linear_layout);
		
		view_list=new ArrayList<View>();
		for(final String search_if:search_if_list){
			LayoutInflater factory2=LayoutInflater.from(linear_layout.getContext());
			final View view=factory2.inflate(R.layout.text_button_item, null);
			view_list.add(view);
			TextView text=(TextView)view.findViewById(R.id.text);
			text.setText(search_if);
			Button remove_button=(Button)view.findViewById(R.id.button);
			remove_button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					StringBuilder sb=new StringBuilder();
					sb.append(search_if);
					sb.append(" を削除しますか？");
					Utility.openCheckDialog(context, new String(sb), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeSearchIf(view);
						}
					});
				}
			});
			linear_layout.addView(view);
		}
	}
}
