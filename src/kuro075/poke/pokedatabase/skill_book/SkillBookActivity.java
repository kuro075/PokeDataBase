package kuro075.poke.pokedatabase.skill_book;

import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SkillBookActivity extends Activity{
	private static final String PACKAGE="kuro075.poke.pokedatabase.skill_book";
	private static final String TAG="SkillBookActivity";
	private static final String KEY_FREE_WORD=PACKAGE+"."+TAG+".free_word";
	
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,SkillBookActivity.class);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	private Button button_all_skills;//全わざ
	private Button button_aiueo;//あいうえお順
	private Button button_detail_search;//詳細検索
	private Button button_history;//履歴
	private Button button_star;//お気に入り
	private Button button_short_cut;//ショートカット
	private Button button_free_word;//フリーワード検索
	private EditText edit_free_word;//フリーワード検索のワード
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

}
