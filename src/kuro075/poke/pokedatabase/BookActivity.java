package kuro075.poke.pokedatabase;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import kuro075.poke.pokedatabase.menu.book.BookMenuActivity;
import kuro075.poke.pokedatabase.util.Utility;

/**
 * 各ブックアクティビティー共通アクティビティー
 * 
 * サブクラスはclickButtonHogeメソッドをオーバーライドする
 * @author sanogenma
 *
 */
public class BookActivity extends BookMenuActivity{
		/*=========/
		/  static  /
		/=========*/
		private static final String PACKAGE="kuro075.poke.pokedatabase";
		private static final String TAG="BookActivity";
		private static final String KEY_FREE_WORD=PACKAGE+"."+TAG+".free_word";


		/*================/
		/  インスタンス変数  /
		/================*/
		private Button button_all;//全表示
		private Button button_aiueo;//あいうえお順
		private Button button_detail_search;//詳細検索
		private Button button_history;//履歴
		private Button button_star;//お気に入り
		private Button button_short_cut;//ショートカット
		private Button button_free_word;//フリーワード検索
		private EditText edit_free_word;//フリーワード検索のワード
		
		/**
		 * button_allがクリックされたとき
		 * @author sanogenma
		 *
		 */
		protected void clickButtonAll(){
			Utility.log(TAG, "should override");
		}
		
		/**
		 * button_aiueoがクリックされたとき
		 */
		protected void clickButtonAiueo(){
			Utility.log(TAG,"should override");
		}
		
		/**
		 * button_detail_searchがクリックされた時
		 * @author sanogenma
		 *
		 */
		protected void clickButtonDetailSearch(){
			Utility.log(TAG, "should override");
		}
		
		/**
		 * button_free_wordがクリックされた時
		 * @author sanogenma
		 *
		 */
		protected void clickButtonFreeWord(){
			Utility.log(TAG, "should override");
		}
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  
			setContentView(R.layout.poke_book_layout);
			Utility.log(TAG, "onCreate");
			
			/*=================/
			/  ウィジェットの登録  /
			/=================*/
			//全表示
			button_all=(Button)findViewById(R.id.button_all_pokes);
			button_all.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickButtonAll();
				}
			});
			//あいうえお順
			button_aiueo=(Button)findViewById(R.id.button_aiueo);
			button_aiueo.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickButtonAiueo();
				}
			});
			//詳細検索
			button_detail_search=(Button)findViewById(R.id.button_detail_search);
			button_detail_search.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickButtonDetailSearch();
				}
			});
			final Context context=this;
			//履歴
			button_history=(Button)findViewById(R.id.button_history);
			button_history.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					getDataType().openHistoryDialog(context);
				}
			});
			//お気に入り
			button_star=(Button)findViewById(R.id.button_star);
			button_star.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					getDataType().openStarDialog(context);
				}
			});
			//ショートカット
			button_short_cut=(Button)findViewById(R.id.button_short_cut);
			button_short_cut.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					getDataType().openShortCutDialog(context);
				}
			});
			//フリーワード検索
			edit_free_word=(EditText)findViewById(R.id.edit_free_word);
			button_free_word=(Button)findViewById(R.id.button_search);
			button_free_word.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					clickButtonFreeWord();
				}
			});
		}

		/**
		 * 状態復元
		 * edit_free_word
		 */
		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);
			edit_free_word.setText(savedInstanceState.getString(KEY_FREE_WORD));
		}

		/**
		 * 状態保存
		 * edit_free_word
		 */
		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putString(KEY_FREE_WORD, edit_free_word.getText().toString());
		}
		
		/**
		 * 全○○の○○を登録(全ポケ、全わざ、全とくせい、)
		 * @param name
		 */
		protected void setButtonAllText(String name){
			button_all.setText(name);
		}
		
		/**
		 * フリーワードを取得
		 * @return
		 */
		protected String getFreeWord(){
			return edit_free_word.getText().toString();
		}
}