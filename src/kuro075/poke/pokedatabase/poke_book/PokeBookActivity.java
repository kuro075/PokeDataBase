package kuro075.poke.pokedatabase.poke_book;

import kuro075.poke.pokedatabase.BookActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.menu.book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
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
public class PokeBookActivity extends BookActivity{
	
	/*=========/
	/  static  /
	/=========*/
	private static final String TAG="PokeBookActivity";

	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,PokeBookActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	protected void clickButtonAll(){
		Utility.log(TAG, "clickButtonAllPokes");
		PokeSearchResultActivity.startThisActivity(this);
	}
	
	/**
	 * button_aiueoがクリックされたとき
	 */
	@Override
	protected void clickButtonAiueo(){
		Utility.log(TAG,"clickButtonAiueo");
		PokeAiueoSearchActivity.startThisActivity(this);
	}
	/**
	 * button_detail_searchがクリックされた時
	 * @author sanogenma
	 *
	 */
	@Override
	protected void clickButtonDetailSearch(){
		Utility.log(TAG, "clickButtonDetailSearch");
		PokeDetailSearchActivity.startThisActivity(this);
	}
	
	/**
	 * button_free_wordがクリックされた時
	 * @author sanogenma
	 *
	 */
	@Override
	protected void clickButtonFreeWord(){
		Utility.log(TAG, "clickButtonFreeWord");
		//フリーワードを取得
		String free_word=getFreeWord();
		//図鑑Noの場合
		try{
			int no=Integer.parseInt(free_word);
			PokePageActivity.startThisActivity(this, no);
		}catch(NumberFormatException e){
			//フリーワードから検索条件を取得
			String[] search_ifs=PokeSearchableInformations.getSearchIfByFreeWord(free_word);
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
						PokeSearchResultActivity.startThisActivity(this, "フリーワード検索", search_ifs);
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
}
