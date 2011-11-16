package kuro075.poke.pokedatabase.type_book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.search.type.TypeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.menu.book.TypeBookMenuActivity;
import kuro075.poke.pokedatabase.type_book.search_result.TypeSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

/**
 * タイプ図鑑アクティビティー
 * @author sanogenma
 *
 */
public class TypeBookActivity extends TypeBookMenuActivity{
	private static final String TAG="TypeBookActivity";

	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,TypeBookActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_book_layout);
		Utility.log(TAG, "onCreate");
		final Context context=this;
		//全タイプ
		((Button)findViewById(R.id.button_all_types)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TypeSearchResultActivity.startThisActivity(context);
			}
		});
		//単タイプ
		((Button)findViewById(R.id.button_single_type)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TypeSearchResultActivity.startThisActivity(context, "単タイプ", TypeSearchableInformations.KIND.getDefaultSearchIf("単タイプ"));
			}
		});
		//複合タイプ
		((Button)findViewById(R.id.button_multi_type)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TypeSearchResultActivity.startThisActivity(context, "複合タイプ", TypeSearchableInformations.KIND.getDefaultSearchIf("複合タイプ"));
			}
		});
		//タイプ相性表
		((Button)findViewById(R.id.button_type_relation_table)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TypeRelationTableActivity.startThisActivity(context);
			}
		});
		//お気に入り
		((Button)findViewById(R.id.button_star)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getDataType().openStarDialog(context);
			}
		});
		//ショートカット
		((Button)findViewById(R.id.button_short_cut)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getDataType().openShortCutDialog(context);
			}
		});
		//履歴
		((Button)findViewById(R.id.button_history)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getDataType().openHistoryDialog(context);
			}
		});
	}
	
}
