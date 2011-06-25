package kuro075.poke.pokedatabase.character_book.character_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.character.CharacterData;
import kuro075.poke.pokedatabase.data_base.character.CharacterDataManager;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.viewable_informations.CharacterViewableInformations;
import kuro075.poke.pokedatabase.menu.book.CharacterBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class CharacterPageActivity extends CharacterBookMenuActivity{
	private static final String TAG="CharacterPageActivity";
	private static final String PACKAGE="kuro075.poke.pokedatabase.character_book.character_page";
	private static final String KEY_CHARACTER_NAME=PACKAGE+"."+TAG;
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param name
	 */
	public static void startThisActivity(Context context,String character_name){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,CharacterPageActivity.class);
		intent.putExtra(KEY_CHARACTER_NAME, character_name);
		DataStore.DataTypes.CHARACTER.getHistoryStore().addPageData(character_name);//履歴に保存
		context.startActivity(intent);
	}
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param character
	 */
	public static void startThisActivity(Context context,CharacterData character){
		startThisActivity(context,character.toString());
	}
	
	/**
	 * このアクティビティーを履歴に保存せずにstartさせる
	 * @param context
	 * @param character_name
	 */
	public static void startThisActivityWithoutHistory(Context context,String character_name){
		Utility.log(TAG,"startThisActivity without history");
		Intent intent = new Intent(context,CharacterPageActivity.class);
		intent.putExtra(KEY_CHARACTER_NAME, character_name);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	private CharacterData character=CharacterDataManager.INSTANCE.NullData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_page_layout);
		Utility.log(TAG,"onCreate");
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			character=CharacterDataManager.INSTANCE.getCharacterData(extras.getString(KEY_CHARACTER_NAME));
		}
		
		/* レイアウト初期化 */
		
		/*================/
		/  画面上部を初期化  /
		/================*/
		initTopInfoBar();
		
		/* 効果 */
		initBasicInfo();
		/* 種類 */
		initKind();
		/* この特性のポケモン */
		initNumLearningPoke();
		/* 似ている特性 */
		
	}
	
	/**
	 * この特性のポケモン
	 */
	private void initNumLearningPoke(){
		//この特性のポケモン
		TextView tv=(TextView)findViewById(R.id.character_learning_poke);
		tv.setText(CharacterViewableInformations.NUM_POKE.getInformation(character));
		final Context context=this;
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.CHARACTER, character.toString());
			}
		});
		
		//夢特性
		tv=(TextView)findViewById(R.id.text_dream_character_num);
		tv.setText(CharacterViewableInformations.NUM_DREAM_POKE.getInformation(character));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 夢特性のみに変更
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.CHARACTER, character.toString());
			}
		});
	}
	
	
	/**
	 * 種類を初期化
	 */
	private void initKind(){
		//TODO 種類を初期化
	}
	
	
	/**
	 * 効果を初期化
	 */
	private void initBasicInfo(){
		//戦闘中の効果
		((TextView)findViewById(R.id.text_battle_effect)).setText(CharacterViewableInformations.BATTLE_EFFECT.getInformation(character));
		//フィールド上の効果
		((TextView)findViewById(R.id.text_field_effect)).setText(CharacterViewableInformations.FIELD_EFFECT.getInformation(character));

	}
	
	
	/**
	 * 画面上部を初期化
	 */
	private void initTopInfoBar(){
		Utility.log(TAG,"initTopInfoBar");
		
		//名前
		((TextView)findViewById(R.id.text_name)).setText(character.getName());
		
		//prevボタン
		TextView text_prev=(TextView)findViewById(R.id.prev);
		text_prev.setText("<<");
		text_prev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickPrev();
			}
		});
		//nextボタン
		((TextView)findViewById(R.id.next)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickNext();
			}
		});
	}
	
	/**
	 * <<ボタンを押したときの動作
	 */
	private void clickPrev(){
		Utility.log(TAG,"clickPrev");
		int no=character.getNo()-1;
		if(no<0){
			no=CharacterDataManager.INSTANCE.getNum()-1;
		}
		startThisActivity(this,CharacterDataManager.INSTANCE.getCharacterData(no));
	}
	/**
	 * >>ボタンを押した時の動作
	 */
	private void clickNext(){
		Utility.log(TAG,"clickNext");
		int no=character.getNo()+1;
		if(no==CharacterDataManager.INSTANCE.getNum()){
			no=0;
		}
		startThisActivity(this,CharacterDataManager.INSTANCE.getCharacterData(no));
	}

}
