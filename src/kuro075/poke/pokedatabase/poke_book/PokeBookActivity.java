package kuro075.poke.pokedatabase.poke_book;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * ポケモン図鑑トップページアクティビティー
 * @author sanogenma
 *
 */
public class PokeBookActivity extends Activity{
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book";
	private static final String TAG="PokeBookActivity";
	private static final String KEY_FREE_WORD=PACKAGE+"."+TAG+".free_word";
	
	
	private Button button_all_pokes;//全ポケモン
	private Button button_detail_search;//詳細検索
	private Button button_free_word;//フリーワード検索
	private EditText edit_free_word;//フリーワード検索のワード
	
	/**
	 * button_all_pokesがクリックされたときのリスナー
	 * @author sanogenma
	 *
	 */
	private class ButtonAllPokesClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//検索結果アクティビティーを開く
		}
	}
	/**
	 * button_detail_searchがクリックされた時のリスナー
	 * @author sanogenma
	 *
	 */
	private class ButtonDetailSearchClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//詳細検索アクティビティーを開く
		}
	}
	/**
	 * button_free_wordがクリックされた時のリスナー
	 * @author sanogenma
	 *
	 */
	private class ButtonFreeWordClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//フリーワード検索を行い、検索結果アクティビティーを開く
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poke_book_layout);
		Utility.log(TAG, "onCreate");
		
		/*=================/
		/  ウィジェットの登録  /
		/=================*/
		//全ポケモン
		button_all_pokes=(Button)findViewById(R.id.button_all_pokes);
		button_all_pokes.setOnClickListener(new ButtonAllPokesClickListener());
		//詳細検索
		button_detail_search=(Button)findViewById(R.id.button_detail_research);
		button_detail_search.setOnClickListener(new ButtonDetailSearchClickListener());
		//フリーワード検索
		edit_free_word=(EditText)findViewById(R.id.edit_free_word);
		button_free_word=(Button)findViewById(R.id.button_search);
		button_free_word.setOnClickListener(new ButtonFreeWordClickListener());
		
	}

	/**
	 * 状態復元
	 * edit_free_word
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		edit_free_word.setText(savedInstanceState.getString(KEY_FREE_WORD));
	}

	/**
	 * 状態保存
	 * edit_free_word
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(KEY_FREE_WORD, edit_free_word.getText().toString());
	}

}
