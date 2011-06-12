package kuro075.poke.pokedatabase.character_book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kuro075.poke.pokedatabase.BookActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.character_book.search_result.CharacterSearchResultActivity;
import kuro075.poke.pokedatabase.data_base.search.character.CharacterSearchableInformations;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.util.Utility;

public class CharacterBookActivity extends BookActivity{
	private static final String TAG="CharacterBookActivity";
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,CharacterBookActivity.class);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setButtonAllText(getString(R.string.all_characters));
		
	}

	@Override
	protected void clickButtonAll() {
		CharacterSearchResultActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonAiueo() {
		CharacterAiueoSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonDetailSearch() {
		CharacterDetailSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonFreeWord() {
		Utility.log(TAG, "clickButtonFreeWord");
		//フリーワードを取得
		String free_word=getFreeWord();
		//フリーワードから検索条件を取得
		String[] search_ifs=CharacterSearchableInformations.getSearchIfByFreeWord(free_word);
		//検索条件がある場合
		if(search_ifs.length>0){	
			//検索条件が一つのとき
			if(search_ifs.length==1){
				//わざの名前のみの場合、そのポケモンのページを開く
				CharacterData character=CharacterDataManager.INSTANCE.getCharacterData(free_word);
				if(character!=CharacterDataManager.NullData){
					//TODO CharacterPageActivity.start
				}
				//ポケモンの名前でないなら検索結果アクティビティーを開始
				else{
					CharacterSearchResultActivity.startThisActivity(this, getString(R.string.free_word_search), search_ifs);
				}
			}
			//検索条件が複数なら　検索条件確認画面を表示
			else CharacterCheckFreeWordActivity.startThisActivity(this, search_ifs);
		}
		else{
			Utility.popToast(this, "検索できません");
		}
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataStore.DataTypes.CHARACTER;
	}

}
