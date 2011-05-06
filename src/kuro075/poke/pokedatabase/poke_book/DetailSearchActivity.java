package kuro075.poke.pokedatabase.poke_book;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.menu.poke_book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 詳細検索アクティビティー
 * @author sanogenma
 *
 */
public class DetailSearchActivity extends PokeBookMenuActivity{
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book";
	private static final String TAG="DetailSearchActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_search_layout);
		Utility.log(TAG, "onCreate");
		
		
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,DetailSearchActivity.class);
		context.startActivity(intent);
	}
}
