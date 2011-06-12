package kuro075.poke.pokedatabase.item_book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kuro075.poke.pokedatabase.BookActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.search.item.ItemSearchableInformations;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.item_book.ItemAiueoSearchActivity;
import kuro075.poke.pokedatabase.item_book.ItemBookActivity;
import kuro075.poke.pokedatabase.item_book.search_result.ItemSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemBookActivity extends BookActivity{
	private static final String TAG="ItemBookActivity";
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,ItemBookActivity.class);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setButtonAllText(getString(R.string.all_items));
		
	}

	@Override
	protected void clickButtonAll() {
		ItemSearchResultActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonAiueo() {
		ItemAiueoSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonDetailSearch() {
		ItemDetailSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonFreeWord() {
		Utility.log(TAG, "clickButtonFreeWord");
		//フリーワードを取得
		String free_word=getFreeWord();
		//フリーワードから検索条件を取得
		String[] search_ifs=ItemSearchableInformations.getSearchIfByFreeWord(free_word);
		//検索条件がある場合
		if(search_ifs.length>0){	
			//検索条件が一つのとき
			if(search_ifs.length==1){
				//わざの名前のみの場合、そのポケモンのページを開く
				ItemData item=ItemDataManager.INSTANCE.getItemData(free_word);
				if(item!=ItemDataManager.NullData){
					//TODO ItemPageActivity.start
				}
				//ポケモンの名前でないなら検索結果アクティビティーを開始
				else{
					ItemSearchResultActivity.startThisActivity(this, getString(R.string.free_word_search), search_ifs);
				}
			}
			//検索条件が複数なら　検索条件確認画面を表示
			else ItemCheckFreeWordActivity.startThisActivity(this, search_ifs);
		}
		else{
			Utility.popToast(this, "検索できません");
		}
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataStore.DataTypes.ITEM;
	}

}
