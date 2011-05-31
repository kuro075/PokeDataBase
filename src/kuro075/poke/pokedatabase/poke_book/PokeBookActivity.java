package kuro075.poke.pokedatabase.poke_book;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.searchable_informations.SearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.menu.poke_book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.poke_book.search_result.SearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * ポケモン図鑑トップページアクティビティー
 * @author sanogenma
 *
 */
public class PokeBookActivity extends PokeBookMenuActivity{
	
	/*=========/
	/  static  /
	/=========*/
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book";
	private static final String TAG="PokeBookActivity";
	private static final String KEY_FREE_WORD=PACKAGE+"."+TAG+".free_word";

	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,PokeBookActivity.class);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	private Button button_all_pokes;//全ポケモン
	private Button button_aiueo;//あいうえお順
	private Button button_detail_search;//詳細検索
	private Button button_history;//履歴
	private Button button_star;//お気に入り
	private Button button_short_cut;//ショートカット
	private Button button_free_word;//フリーワード検索
	private EditText edit_free_word;//フリーワード検索のワード
	
	/**
	 * button_all_pokesがクリックされたとき
	 * @author sanogenma
	 *
	 */
	private void clickButtonAllPokes(){
		Utility.log(TAG, "clickButtonAllPokes");
		SearchResultActivity.startThisActivity(this);
	}
	
	/**
	 * button_aiueoがクリックされたとき
	 */
	private void clickButtonAiueo(){
		Utility.log(TAG,"clickButtonAiueo");
		AiueoSearchActivity.startThisActivity(this);
	}
	/**
	 * button_detail_searchがクリックされた時
	 * @author sanogenma
	 *
	 */
	private void clickButtonDetailSearch(){
		Utility.log(TAG, "clickButtonDetailSearch");
		DetailSearchActivity.startThisActivity(this);
	}
	
	/**
	 * button_free_wordがクリックされた時
	 * @author sanogenma
	 *
	 */
	private void clickButtonFreeWord(){
		Utility.log(TAG, "clickButtonFreeWord");
		//フリーワードを取得
		String free_word=this.edit_free_word.getText().toString();
		//図鑑Noの場合
		try{
			int no=Integer.parseInt(free_word);
			PokePageActivity.startThisActivity(this, no);
		}catch(NumberFormatException e){
			//フリーワードから検索条件を取得
			String[] search_ifs=SearchableInformations.getSearchIfByFreeWord(free_word);
			//検索条件がある場合
			if(search_ifs.length>0){	
				//検索条件が一つのとき
				if(search_ifs.length==1){
					//ポケモンの名前のみの場合、そのポケモンのページを開く
					PokeData poke=PokeDataManager.INSTANCE.getPokeData(free_word);
					if(poke!=PokeDataManager.NullData){
						PokePageActivity.startThisActivity(this, poke);
					}
					//ポケモンの名前でないなら検索結果アクティビティーを開始
					else{
						SearchResultActivity.startThisActivity(this, "フリーワード検索", search_ifs);
					}
				}
				//検索条件が複数なら　検索条件確認画面を表示
				else CheckFreeWordSearchIfActivity.startThisActivity(this, search_ifs);
			}
			else{
				Utility.popToast(this, "検索できません");
			}
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  
		setContentView(R.layout.poke_book_layout);
		Utility.log(TAG, "onCreate");
		
		/*=================/
		/  ウィジェットの登録  /
		/=================*/
		//全ポケモン
		button_all_pokes=(Button)findViewById(R.id.button_all_pokes);
		button_all_pokes.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickButtonAllPokes();
			}
		});
		//あいうえお順
		button_aiueo=(Button)findViewById(R.id.button_aiueo);
		button_aiueo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickButtonAiueo();
			}
		});
		//詳細検索
		button_detail_search=(Button)findViewById(R.id.button_detail_search);
		button_detail_search.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickButtonDetailSearch();
			}
		});
		final Context context=this;
		//履歴
		button_history=(Button)findViewById(R.id.button_history);
		button_history.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataStore.DataTypes.POKEMON.openHistoryDialog(context);
			}
		});
		//お気に入り
		button_star=(Button)findViewById(R.id.button_star);
		button_star.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataStore.DataTypes.POKEMON.openStarDialog(context);
			}
		});
		//ショートカット
		button_short_cut=(Button)findViewById(R.id.button_short_cut);
		button_short_cut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataStore.DataTypes.POKEMON.openShortCutDialog(context);
			}
		});
		//フリーワード検索
		edit_free_word=(EditText)findViewById(R.id.edit_free_word);
		button_free_word=(Button)findViewById(R.id.button_search);
		button_free_word.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
