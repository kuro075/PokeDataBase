package kuro075.poke.pokedatabase.skill_book;

import kuro075.poke.pokedatabase.BookActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.search.skill.SkillSearchableInformations;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;
import kuro075.poke.pokedatabase.skill_book.search_result.SkillSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SkillBookActivity extends BookActivity{
	private static final String PACKAGE="kuro075.poke.pokedatabase.skill_book";
	private static final String TAG="SkillBookActivity";
	private static final String KEY_FREE_WORD=PACKAGE+"."+TAG+".free_word";
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,SkillBookActivity.class);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setButtonAllText(getString(R.string.all_skills));
		
	}

	@Override
	protected void clickButtonAll() {
		SkillSearchResultActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonAiueo() {
		SkillAiueoSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonDetailSearch() {
		SkillDetailSearchActivity.startThisActivity(this);
	}

	@Override
	protected void clickButtonFreeWord() {
		Utility.log(TAG, "clickButtonFreeWord");
		//フリーワードを取得
		String free_word=getFreeWord();
		//フリーワードから検索条件を取得
		String[] search_ifs=SkillSearchableInformations.getSearchIfByFreeWord(free_word);
		//検索条件がある場合
		if(search_ifs.length>0){	
			//検索条件が一つのとき
			if(search_ifs.length==1){
				//わざの名前のみの場合、そのポケモンのページを開く
				SkillData skill=SkillDataManager.INSTANCE.getSkillData(free_word);
				if(skill!=SkillDataManager.NullData){
					//TODO SkillPageActivity.start
				}
				//ポケモンの名前でないなら検索結果アクティビティーを開始
				else{
					SkillSearchResultActivity.startThisActivity(this, getString(R.string.free_word_search), search_ifs);
				}
			}
			//検索条件が複数なら　検索条件確認画面を表示
			else SkillCheckFreeWordActivity.startThisActivity(this, search_ifs);
		}
		else{
			Utility.popToast(this, "検索できません");
		}
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataStore.DataTypes.SKILL;
	}
}
