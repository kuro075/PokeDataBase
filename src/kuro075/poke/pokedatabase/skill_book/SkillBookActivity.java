package kuro075.poke.pokedatabase.skill_book;

import kuro075.poke.pokedatabase.BookActivity;
import kuro075.poke.pokedatabase.R;
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
		// TODO Auto-generated method stub
	}

	@Override
	protected DataTypes getDataType() {
		// TODO Auto-generated method stub
		return DataStore.DataTypes.SKILL;
	}
}
