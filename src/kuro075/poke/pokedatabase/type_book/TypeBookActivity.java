package kuro075.poke.pokedatabase.type_book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.menu.book.TypeBookMenuActivity;
import kuro075.poke.pokedatabase.util.Utility;

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
		//タイプ相性表
		((Button)findViewById(R.id.button_type_relation_table)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO タイプ相性表アクティビティーを表示
				TypeRelationTableActivity.startThisActivity(context);
			}
		});
		//単タイプ
		((Button)findViewById(R.id.button_single_type)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 単タイプ選択アクティビティーを表示
				
			}
		});
		//複合タイプ
		((Button)findViewById(R.id.button_multi_type)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 複合タイプ選択アクティビティーを表示
				
			}
		});
	}
	
	
}
